package me.xadia.shield.profile;

import com.mongodb.client.MongoCollection;
import me.xadia.shield.ShieldPlugin;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProfileHandler {

    private ShieldPlugin plugin;

    private Map<UUID, Profile> cache = new HashMap<>();
    private MongoCollection<Document> collection;

    public ProfileHandler(ShieldPlugin plugin) {
        this.plugin = plugin;
        this.collection =
    }
}
