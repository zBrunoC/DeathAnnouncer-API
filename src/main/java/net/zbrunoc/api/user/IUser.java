package net.zbrunoc.api.user;

import java.util.UUID;

public interface IUser {
    UUID getUniqueId();

    String getName();

    void sendMessage(String message);

    boolean hasPermission(String permission);

    Object getKiller();

    Object getPlayer();
}