package net.zbrunoc.api.model;

/**
 * One recent fight involving the player asked about in {@link PvpStats}.
 */
public record PvpFight(long timestamp, String victim, String killer, String weapon, String cause) {
}
