package me.xadia.shield.profile;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import me.xadia.shield.ShieldPlugin;
import me.xadia.shield.profile.punishment.Punishment;
import me.xadia.shield.profile.punishment.type.PunishmentType;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class ProfileHandler {

    private Map<UUID, Profile> profiles = new HashMap<>();
    private MongoCollection<Document> collection;

    public ProfileHandler() {
        collection = ShieldPlugin.getInstance().getMongoDatabase().getCollection("profiles");
        collection.createIndex(new Document("uuid", 1));
    }

    public final Map.Entry<UUID, Punishment> findActivePunishment(UUID uuid) {
        FindIterable<Document> query = collection.find(new Document("uuid", uuid.toString()));
        for (Document document : query) {
            List<Punishment> punishments = ((List<Document>) document.get("punishments")).stream()
                    .map(Punishment::new)
                    .collect(Collectors.toList());

            for (Punishment punishment : punishments) {
                if ((punishment.getType() == PunishmentType.BLACKLIST || punishment.getType() == PunishmentType.BAN) && punishment.isActive()) {
                    return new AbstractMap.SimpleEntry<>(UUID.fromString(document.getString("uuid")), punishment);
                }
            }
        }
        return null;
    }

    public void onDisable() {
        this.profiles.forEach((uuid, profile) -> saveProfile(profile));
    }

    public Profile createProfile(UUID uuid, String name) {
        return new Profile(uuid, name);
    }

    public void persist(Profile profile, boolean load) {
        profiles.put(profile.getUuid(), profile);
        if (load) {
            Document document = collection.find(new Document("uuid", profile.getUuid().toString())).first();
            if (document != null) {
                profile.update(document);
            }
        }
    }

    public Profile loadProfile(UUID uuid, String name) {
        if (this.profiles.containsKey(uuid)) {
            return this.profiles.get(uuid);
        }

        Profile profile = this.createProfile(uuid, name);
        boolean save = false;

        Document document = collection.find(new Document("uuid", uuid.toString())).first();
        if (document != null) {
            profile.update(document);
        } else {
            save = true;
        }

        if (save) {
            this.saveProfile(profile);
        }

        return profile;
    }

    public void saveProfile(Profile profile) {
        collection.replaceOne(new Document("uuid", profile.getUuid().toString()), profile.serialize(), new ReplaceOptions().upsert(true));
    }
}
