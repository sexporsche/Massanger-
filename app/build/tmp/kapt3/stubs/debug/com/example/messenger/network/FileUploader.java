package com.example.messenger.network;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\u000b\u00a8\u0006\r"}, d2 = {"Lcom/example/messenger/network/FileUploader;", "", "()V", "upload", "", "recipient", "", "filename", "bytes", "", "progress", "Lkotlin/Function1;", "", "app_debug"})
public final class FileUploader {
    @org.jetbrains.annotations.NotNull()
    public static final com.example.messenger.network.FileUploader INSTANCE = null;
    
    private FileUploader() {
        super();
    }
    
    public final void upload(@org.jetbrains.annotations.NotNull()
    java.lang.String recipient, @org.jetbrains.annotations.NotNull()
    java.lang.String filename, @org.jetbrains.annotations.NotNull()
    byte[] bytes, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> progress) {
    }
}