package me.xadia.shield.profile.punishment;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.xadia.shield.profile.punishment.type.PunishmentType;
import me.xadia.shield.utils.SaltUtil;
import org.bson.Document;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class Punishment {

    private String id = SaltUtil.getRandomSaltedString(7);
    private PunishmentType type;

    private UUID receiver;

    private String reason;

    private UUID issuedBy;
    private long issuedAt = -1;

    private Long expiresAt;

    private String removalReason;
    private UUID removedBy;
    private Long removedAt;

    public Punishment(Document document) {
        deserialize(document);
    }

    public final boolean isActive() {
        if (removedAt == null) {
            if (expiresAt != null) {
                if (System.currentTimeMillis() >= expiresAt) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isPermanent() {
        return type == PunishmentType.BLACKLIST || expiresAt == null;
    }

    public Document serialize() {
        return new Document("id", id)
                .append("type", type.name())
                .append("receiver", receiver == null ? null : receiver.toString())
                .append("reason", reason == null ? "" : reason)
                .append("issuedAt", issuedAt)
                .append("expiresAt", expiresAt)
                .append("removalReason", removalReason)
                .append("removedBy", removedBy == null ? null : removedBy.toString())
                .append("removedAt", removedAt);
    }

    public void deserialize(Document document) {
        id = document.getString("id");
        type = PunishmentType.valueOf(document.getString("type"));
        reason = document.getString("reason");

        receiver = document.containsKey(document.getString("receiver")) && document.get("receiver") != null ? UUID.fromString(document.getString("receiver")) : null;

        issuedBy = document.containsKey(document.getString("issuedBy")) && document.get("issuedBy") != null ? UUID.fromString(document.getString("issuedBy")) : null;
        issuedAt = document.getLong("issuedAt");

        expiresAt = document.containsKey("expiresAt") && document.get("expiresAt") != null ? document.getLong("expiresAt") : null;

        removalReason = document.containsKey("removalReason") && document.get("removalReason") != null ? document.getString("removalReason") : null;
        removedBy = document.containsKey("removedBy") && document.get("removedBy") != null ? UUID.fromString(document.getString("removedBy")) : null;
        removedAt = document.containsKey("removedAt") && document.get("removedAt") != null ? document.getLong("removedAt") : null;

    }

}