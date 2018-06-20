package io.realm;


public interface ChatRealmProxyInterface {
    public RealmList<com.nazpowerchat.models.Message> realmGet$messages();
    public void realmSet$messages(RealmList<com.nazpowerchat.models.Message> value);
    public String realmGet$lastMessage();
    public void realmSet$lastMessage(String value);
    public String realmGet$myId();
    public void realmSet$myId(String value);
    public String realmGet$userId();
    public void realmSet$userId(String value);
    public String realmGet$groupId();
    public void realmSet$groupId(String value);
    public long realmGet$timeUpdated();
    public void realmSet$timeUpdated(long value);
    public com.nazpowerchat.models.User realmGet$user();
    public void realmSet$user(com.nazpowerchat.models.User value);
    public com.nazpowerchat.models.Group realmGet$group();
    public void realmSet$group(com.nazpowerchat.models.Group value);
    public boolean realmGet$read();
    public void realmSet$read(boolean value);
}
