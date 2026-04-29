package com.elthisboy.travelmenu.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TravelConfigManager {

    public static List<TravelDestination> destinations = new ArrayList<>();

    public static void load() {

        try {

            Path configDir = Path.of("config/travelmenu");
            Path file = configDir.resolve("destinations.json");

            if (!configDir.toFile().exists()) {
                configDir.toFile().mkdirs();
            }

            if (!file.toFile().exists()) {

                String defaultJson = """
            {
              "destinations": [
                {
                  "id": "market",
                  "icon": "minecraft:emerald",
                  "x": 100,
                  "y": 70,
                  "z": -50,
                  "cost": 0
                },
                {
                  "id": "forest",
                  "icon": "minecraft:oak_sapling",
                  "x": 250,
                  "y": 70,
                  "z": 120,
                  "cost": 5
                }
              ]
            }
            """;

                            java.nio.file.Files.writeString(file, defaultJson);

            }

            Gson gson = new Gson();

            JsonObject obj = gson.fromJson(
                    new FileReader(file.toFile()),
                    JsonObject.class
            );

            destinations = gson.fromJson(
                    obj.get("destinations"),
                    new TypeToken<List<TravelDestination>>(){}.getType()
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static TravelDestination getDestination(String id) {

        for (TravelDestination dest : destinations) {

            if (dest.id.equals(id)) {
                return dest;
            }

        }

        return null;

    }
}