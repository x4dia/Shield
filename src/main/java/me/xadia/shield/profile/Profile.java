package me.xadia.shield.profile;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import me.xadia.shield.ShieldPlugin;
import me.xadia.shield.profile.punishment.Punishment;
import me.xadia.shield.profile.punishment.type.PunishmentType;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class Profile {

    private final UUID uuid;
    private final String name;

    private List<Punishment> punishments = new ArrayList<>();

    private Date dateJoined;

    private boolean locked = false;

    public Punishment getActivePunishment(PunishmentType type) {
        for (Punishment punishment : this.punishments) {
            if (punishment.getType() == type && punishment.isActive()) {
                return punishment;
            }
        }
        return null;
    }

    public void update(Document document) {
        deserialize(document);
    }

    public void save() {
        ShieldPlugin.getInstance().getProfileHandler().saveProfile(this);
    }

    public Document serialize() {
        return new Document("uuid", uuid.toString())
                .append("name", name)
                .append("punishments", punishments.stream().map(Punishment::serialize).collect(Collectors.toList()))
                .append("firstJoinDate", dateJoined == null ? null : dateJoined.getTime())
                .append("locked", locked);
    }

    public void deserialize(Document document) {
        punishments = ((List<Document>)document.get("punishments")).stream().map(Punishment::new).collect(Collectors.toList());
        locked = document.getBoolean("locked", false);
        dateJoined = document.containsKey("firstJoinDate") && document.get("firstJoinDate") != null ? new Date(document.getLong("firstJoinDate")) : null;
    }



}
