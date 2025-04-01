package me.xadia.shield.profile.punishment.type;

import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public enum PunishmentType {

    BLACKLIST("Blacklist", "Blacklisted", ChatColor.DARK_RED, 14),
    BAN("Ban", "Unbanned", ChatColor.RED, 14),
    KICK("Kick", "Kicked", ChatColor.GOLD, 1),
    MUTE("Mute", "Muted", ChatColor.YELLOW, 4),
    WARN("Warn", "Warned", ChatColor.GREEN, 5);

    private final String name, pardoned;
    private final ChatColor color;
    private final int durability;

    PunishmentType(String name, String pardoned, ChatColor color, int durability) {
        this.name = name;
        this.pardoned = pardoned;
        this.color = color;
        this.durability = durability;
    }

}

