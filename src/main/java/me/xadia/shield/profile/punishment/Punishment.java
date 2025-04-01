package me.xadia.shield.profile.punishment;

import lombok.Data;
import me.xadia.shield.profile.punishment.type.PunishmentType;
import me.xadia.shield.utils.SaltUtil;

import java.util.UUID;

@Data
public class Punishment {

    private final String id = SaltUtil.getRandomSaltedString(7);

    private UUID player;
    private UUID issuer;

    private String reason;
    private String removedReason;

    private PunishmentType type;

    private long issuedOn;
    private long removedOn;
    private long duration;

    private boolean active = true;
    private boolean removed = false;
    private UUID removedBy;

    public Punishment(UUID player, UUID issuer, String reason, long duration, PunishmentType type) {
        this.player = player;
        this.issuer = issuer;
        this.reason = reason != null ? reason : "No reason provided";
        this.duration = duration;
        this.issuedOn = System.currentTimeMillis();
        this.type = type;
    }

    public boolean checkActive() {
        return !removed && (isPermanent() || System.currentTimeMillis() < issuedOn + duration);
    }

    public boolean isPermanent() {
        return duration == -1L;
    }

}