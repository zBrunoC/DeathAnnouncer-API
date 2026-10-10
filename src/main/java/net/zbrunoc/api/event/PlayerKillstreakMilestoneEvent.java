package net.zbrunoc.api.event;

import net.zbrunoc.api.user.IUser;

public class PlayerKillstreakMilestoneEvent implements DeathAnnouncerEvent {

    private final IUser user;
    private final int streak;

    public PlayerKillstreakMilestoneEvent(IUser user, int streak) {
        this.user = user;
        this.streak = streak;
    }

    public IUser getUser() { return user; }
    public int getStreak() { return streak; }
}