package me.xadia.shield.profile;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import me.xadia.shield.profile.punishment.Punishment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Profile {

    @SerializedName("_id")
    private final UUID uuid;
    private String name;

    private List<Punishment> punishments = new ArrayList<>();
    private boolean locked = false;

    public Profile(UUID uuid) {
        this.uuid = uuid;
    }

    public Profile(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
