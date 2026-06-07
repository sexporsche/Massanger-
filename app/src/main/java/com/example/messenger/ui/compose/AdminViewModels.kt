package com.example.messenger.ui.compose

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.messenger.MessengerApplication
import com.example.messenger.data.entities.DepartmentEntity
import com.example.messenger.data.entities.UserProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AdminUsersViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    private val _query = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _query
    fun setQuery(q: String) {
        _query.value = q
    }

    val users: StateFlow<List<UserProfileEntity>> =
        combine(c.profileRepository.observeAllProfiles(), _query) { list, q ->
            val t = q.trim().lowercase()
            if (t.isEmpty()) list
            else list.filter { p ->
                sequenceOf(p.firstName, p.lastName, p.email, p.phone, p.position, p.role, "${p.userId}")
                    .any { it.lowercase().contains(t) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())
}

class AdminEditUserViewModel(app: Application, private val userId: Int) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val profile: StateFlow<UserProfileEntity?> =
        c.profileRepository.observeProfile(userId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)
    val departments: StateFlow<List<DepartmentEntity>> =
        c.database.departmentDao().observeDepartments()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun save(profile: UserProfileEntity) {
        viewModelScope.launch { c.profileRepository.upsert(profile) }
    }

    companion object {
        fun factory(app: Application, userId: Int): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    AdminEditUserViewModel(app, userId) as T
            }
    }
}

class AdminCreateUserViewModel(app: Application) : AndroidViewModel(app) {
    private val c = (app as MessengerApplication).container
    val departments: StateFlow<List<DepartmentEntity>> =
        c.database.departmentDao().observeDepartments()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun create(profileTemplate: UserProfileEntity, onCreated: (Int) -> Unit) {
        viewModelScope.launch {
            val id = c.profileRepository.nextUserIdForCreate()
            c.profileRepository.upsert(profileTemplate.copy(userId = id))
            onCreated(id)
        }
    }
}
