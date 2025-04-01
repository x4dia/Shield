package me.xadia.shield;

import lombok.Getter;
import me.xadia.shield.profile.ProfileHandler;
import me.xadia.shield.utils.StringUtil;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class ShieldPlugin extends JavaPlugin {

    @Getter private static ShieldPlugin instance;

    private ProfileHandler profileHandler;

    public static String prefix(String message, Object... arguments) {
        return StringUtil.color("&3&lShield &8» ") + StringUtil.colorFormat(message, arguments);
    }

    @Override
    public void onEnable() {
        instance = this;

        profileHandler = new ProfileHandler(this);

    }
}
