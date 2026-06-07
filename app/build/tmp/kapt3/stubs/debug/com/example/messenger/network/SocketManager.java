package com.example.messenger.network;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\nJ\b\u0010\u0012\u001a\u0004\u0018\u00010\u0006J\u0006\u0010\u0013\u001a\u00020\u0014J\b\u0010\u0015\u001a\u0004\u0018\u00010\u000eJ\u000e\u0010\u0016\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u000eJ\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\fH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/example/messenger/network/SocketManager;", "", "()V", "reader", "Ljava/io/BufferedReader;", "socket", "Ljava/net/Socket;", "writer", "Ljava/io/PrintWriter;", "connect", "", "context", "Landroid/content/Context;", "host", "", "port", "", "disconnect", "getSocket", "isConnected", "", "readLine", "sendLine", "line", "sslContextForMessengerServer", "Ljavax/net/ssl/SSLContext;", "app_debug"})
public final class SocketManager {
    @org.jetbrains.annotations.Nullable()
    private static java.net.Socket socket;
    @org.jetbrains.annotations.Nullable()
    private static java.io.PrintWriter writer;
    @org.jetbrains.annotations.Nullable()
    private static java.io.BufferedReader reader;
    @org.jetbrains.annotations.NotNull()
    public static final com.example.messenger.network.SocketManager INSTANCE = null;
    
    private SocketManager() {
        super();
    }
    
    /**
     * Uses TLS with the bundled dev server certificate (matches server/certs/cert.pem).
     * Endpoint identification is disabled so connections by LAN IP work with CN=test.
     */
    public final void connect(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String host, int port) {
    }
    
    private final javax.net.ssl.SSLContext sslContextForMessengerServer(android.content.Context context) {
        return null;
    }
    
    public final void sendLine(@org.jetbrains.annotations.NotNull()
    java.lang.String line) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String readLine() {
        return null;
    }
    
    public final void disconnect() {
    }
    
    public final boolean isConnected() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.net.Socket getSocket() {
        return null;
    }
}