# Prime-Media Messenger — руководство программиста

## Назначение документа

Настоящее руководство предназначено для **разработчиков и сопровождающих** проекта **Prime-Media Messenger** (пакет `com.example.messenger`). В документе описаны структура репозитория, сетевой протокол, сборка клиента и обновление доверия к TLS-сертификату сервера.

---

## Структура проекта (основные модули)

```
CorporateMessenger/
├── app/                              # Android-приложение
│   └── src/main/java/com/example/messenger/
│       ├── data/                     # Room, DAO, SessionStore, репозитории
│       ├── domain/                   # UserRole, контракты репозиториев, permissions
│       ├── di/                       # AppContainer (DI вручную)
│       ├── network/                  # SocketManager, FileUploader
│       ├── ui/                       # Login, Contacts, Chat (XML)
│       └── ui/compose/               # Splash, Prime hub, Admin, навигация
├── server/                           # Python TLS-сервер
│   ├── main.py
│   ├── schema.sql
│   ├── generate_certs.sh
│   └── backup_db.py
└── README.md                         # Обзорная документация продукта
```

| Модуль | Назначение |
|--------|------------|
| `data/` | Единая БД Room `messenger.db`: сообщения, пользователи, профили Prime, новости, опросы, чаты |
| `domain/` | Роли, интерфейсы репозиториев, правила доступа к профилю |
| `ui/compose/navigation/` | `PrimeNavHost`, маршруты `PrimeRoutes` |
| `network/SocketManager.kt` | TLS-сокет, построчный протокол команд |
| `server/main.py` | Аутентификация, сообщения, файлы, список пользователей |

---

## API для подключения бэкенда

Серверное взаимодействие клиента мессенджера (личные чаты, контакты, файлы) выполняется через **текстовый протокол поверх TLS-сокета** на порту **12345** (настраивается в `integers.xml`).

### Подключение

1. Установить TLS-соединение с хостом из `strings.xml` → `server_host`.
2. Доверие к серверу — встроенный PEM в `app/src/main/res/raw/messenger_server_cert`.
3. Одна команда — одна строка; ответ — одна строка (кроме бинарной передачи файла после `READY_FOR_FILE`).

### Команды (клиент → сервер)

| Команда | Формат | Ответ (успех) |
|---------|--------|-----------------|
| Авторизация | `AUTH:username:password` | `AUTH_OK:{userId}` |
| Список пользователей | `LIST_USERS` | `USERS:[{id,username,isOnline},…]` |
| Отправка сообщения | `SEND_MSG:{"recipient_id":N,"text":"..."}` | `MSG_SENT:{messageId}` |
| История | `GET_MESSAGES:{otherUserId}` | `MESSAGES:[…]` |
| Загрузка файла | `UPLOAD_FILE:{"recipient_id":N,"filename":"…","size":N}` | `READY_FOR_FILE` → байты → `FILE_UPLOADED:{id}` |

### Ответы об ошибках

- `AUTH_FAIL` — неверные учётные данные.
- `ERROR:…` — не аутентифицирован, неверный JSON, неизвестная команда.

### Push с сервера (при активном сокете получателя)

- `NEW_MSG:{json}` — новое сообщение в реальном времени (если получатель онлайн).

Контент Prime (новости, опросы, админ-профили) в текущей версии хранится в **Room** и сидируется через `MockPrimeBackend`; отдельного REST API для этих сущностей в `main.py` нет.

---

## Порядок сборки в Android Studio

1. Откройте корень репозитория в **Android Studio**.

2. Выполните **File → Sync Project with Gradle Files** и дождитесь успешного завершения.

3. Подключите Android-устройство с включённой **отладкой по USB** или запустите эмулятор (рекомендуется API 34).

4. Проверьте `server_host` в `app/src/main/res/values/strings.xml` (для эмулятора на ПК: `10.0.2.2`).

5. Запустите сервер на машине разработки:
   ```bash
   cd server
   ./generate_certs.sh   # при первом запуске
   python3 main.py -d
   ```

6. Нажмите **Run → Run 'app'** (зелёный треугольник) или **Shift+F10**.

7. Для release-сборки: **Build → Generate Signed Bundle / APK** → APK → `app-release.apk`.

---

## Пересборка APK после смены сертификата сервера

При замене TLS-сертификата на сервере клиент перестанет доверять старому PEM. Выполните:

1. Сгенерируйте новую пару на сервере:
   ```bash
   cd server
   ./generate_certs.sh
   ```

2. Скопируйте файл сертификата в ресурсы Android:
   - Источник: `server/certs/cert.pem`
   - Назначение: `app/src/main/res/raw/messenger_server_cert`  
     (имя ресурса без расширения: `messenger_server_cert`)

3. Убедитесь, что формат файла — **PEM X.509** (как выдаёт `generate_certs.sh`).

4. В Android Studio выполните **Build → Clean Project**, затем **Rebuild Project**.

5. Установите новый APK на устройства или запустите debug-сборку заново.

**Примечание:** файл сертификата должен лежать в `app/src/main/res/raw/messenger_server_cert`. Без этого `SocketManager` не сможет построить `SSLContext` для подключения к серверу.

---

## Полезные точки входа в коде

| Задача | Файл |
|--------|------|
| Изменить хост/порт | `res/values/strings.xml`, `res/values/integers.xml` |
| Протокол сокета | `network/SocketManager.kt`, `ui/LoginActivity.kt`, `ui/ChatActivity.kt` |
| Навигация Compose | `ui/compose/navigation/PrimeNavHost.kt` |
| Миграции Room | `data/PrimeMigrations.kt`, версия в `AppDatabase.kt` |
| Роли | `domain/model/UserRole.kt`, `domain/model/ProfilePermissions.kt` |

---

## См. также

- `INSTALL_AND_TEST.md` — установка и тестовые сценарии  
- `ADMIN_AND_USER_GUIDE.md` — администрирование сервера и работа пользователя  
- `README.md` — полное описание продукта
