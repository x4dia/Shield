package me.xadia.shield;

import co.aikar.commands.PaperCommandManager;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import me.xadia.shield.profile.ProfileHandler;
import me.xadia.shield.utils.StringUtil;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ShieldPlugin extends JavaPlugin {

    @Getter private static ShieldPlugin instance;

    private MongoDatabase mongoDatabase;
    private PaperCommandManager commandManager;
    private ProfileHandler profileHandler;

    public static String prefix(String message, Object... arguments) {
        return StringUtil.color("&3&lShield &8» ") + StringUtil.colorFormat(message, arguments);
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        mongoDatabase = new MongoClient("localhost", 27017).getDatabase("shield");
        commandManager = new PaperCommandManager(this);
        profileHandler = new ProfileHandler();

    }

    @Override
    public void onDisable() {
        profileHandler.onDisable();
    }
}
