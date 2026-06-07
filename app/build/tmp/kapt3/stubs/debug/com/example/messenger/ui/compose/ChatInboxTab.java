package com.example.messenger.ui.compose;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\b\u0005\b\u0082\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B#\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\u0002\u0010\bJ\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001d\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012\u00a8\u0006\u0013"}, d2 = {"Lcom/example/messenger/ui/compose/ChatInboxTab;", "", "label", "", "predicate", "Lkotlin/Function1;", "Lcom/example/messenger/data/entities/ChatThreadEntity;", "", "(Ljava/lang/String;ILjava/lang/String;Lkotlin/jvm/functions/Function1;)V", "getLabel", "()Ljava/lang/String;", "getPredicate", "()Lkotlin/jvm/functions/Function1;", "filter", "", "list", "DIRECT", "GROUP", "CHANNEL", "app_debug"})
enum ChatInboxTab {
    /*public static final*/ DIRECT /* = new DIRECT(null, null) */,
    /*public static final*/ GROUP /* = new GROUP(null, null) */,
    /*public static final*/ CHANNEL /* = new CHANNEL(null, null) */;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String label = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.example.messenger.data.entities.ChatThreadEntity, java.lang.Boolean> predicate = null;
    
    ChatInboxTab(java.lang.String label, kotlin.jvm.functions.Function1<? super com.example.messenger.data.entities.ChatThreadEntity, java.lang.Boolean> predicate) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getLabel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlin.jvm.functions.Function1<com.example.messenger.data.entities.ChatThreadEntity, java.lang.Boolean> getPredicate() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.messenger.data.entities.ChatThreadEntity> filter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.example.messenger.data.entities.ChatThreadEntity> list) {
        return null;
    }
}