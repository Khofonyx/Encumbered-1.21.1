package com.khofonyx.encumbered.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/*
 * this class is responsible for creating, updating, and managing the weights.json file
 * It's also responsible for defining the weight thresholds.
 */
public class WeightConfig {
    // Saves the path to the config directory
    private static final File CONFIG_DIR = new File(FMLPaths.CONFIGDIR.get().toFile(), "encumbered");

    // Saves the path to the weights.json file
    private static final File WEIGHT_CONFIG_FILE = new File(CONFIG_DIR, "weights.json");

    // Creates a new GSON to create the json file later
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    // Instantiates a new in memory hash map for accessing weight values of items
    public static Map<String, Float> itemWeights = new HashMap<>();

    public static void load() {
        loadWeights();
        loadThresholds();
    }

    /*
     * 1. Check if the config directory exists, if not make the config folder
     * 2. Check if the weights.json file exists, if not create a default one
     * 3. If weights.json exists, try to read it into the itemWeights map.
     */
    private static void loadWeights(){
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        if (!WEIGHT_CONFIG_FILE.exists()) {
            generateDefaultConfig();
        }

        try (FileReader reader = new FileReader(WEIGHT_CONFIG_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            json.entrySet().forEach(entry -> itemWeights.put(entry.getKey(), entry.getValue().getAsFloat()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * 1. Iterate through every item in the registry, assigning a default weight of 1.0 to everything
     * 2. Create the json file and write it to weights.json
     */
    private static void generateDefaultConfig() {
        JsonObject json = new JsonObject();

        for (ResourceLocation itemID : BuiltInRegistries.ITEM.keySet()) {
            json.addProperty(itemID.toString(), 1.0f); // Default weight is 1.0
        }

        try (FileWriter writer = new FileWriter(WEIGHT_CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float getWeight(ResourceLocation itemID) {
        return itemWeights.getOrDefault(itemID.toString(), 1.0f);
    }

    public static void updateWeight(String itemID, float weight) {
        itemWeights.put(itemID, weight);
        saveConfig();
    }

    /*
     * 1. regenerate the weights.json file by going through the in memory map and writing it to json.
     */
    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(WEIGHT_CONFIG_FILE)) {
            JsonObject json = new JsonObject();
            itemWeights.forEach(json::addProperty);
            GSON.toJson(json, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* =================================   WEIGHT THRESHOLD CODE  =========================================================*/
    private static final File THRESHOLD_CONFIG_FILE = new File(CONFIG_DIR, "thresholds.json");
    public static float THRESHOLD_1 = 50.0f;
    public static float THRESHOLD_2 = 100.0f;

    private static void loadThresholds() {
        if (!THRESHOLD_CONFIG_FILE.exists()) {
            createDefaultThresholds();
        }

        try (FileReader reader = new FileReader(THRESHOLD_CONFIG_FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            THRESHOLD_1 = json.get("threshold_1").getAsFloat();
            THRESHOLD_2 = json.get("threshold_2").getAsFloat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createDefaultThresholds() {
        JsonObject json = new JsonObject();
        json.addProperty("threshold_1", 50.0f);
        json.addProperty("threshold_2", 100.0f);

        try (FileWriter writer = new FileWriter(THRESHOLD_CONFIG_FILE)) {
            GSON.toJson(json, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
