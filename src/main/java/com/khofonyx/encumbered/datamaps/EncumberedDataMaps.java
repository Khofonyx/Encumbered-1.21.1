package com.khofonyx.encumbered.datamaps;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

public class EncumberedDataMaps {
    public static final DataMapType<Item, ItemWeight> ITEM_WEIGHTS = DataMapType.builder(
            ResourceLocation.fromNamespaceAndPath("encumbered","data/item_weights"),
                    Registries.ITEM,
                    ItemWeight.CODEC).
            synced(ItemWeight.CODEC, true)
            .build();

    @SubscribeEvent
    public static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ITEM_WEIGHTS);
    }

    public static float getWeight(Holder<Item> item) {
        ItemWeight weight = item.getData(ITEM_WEIGHTS);
        String itemName = item.unwrapKey().toString();

        if (weight == null) {
            System.out.println("No weight found for item: " + itemName);
            return 1.0f; // Default weight
        }

        System.out.println("Weight for " + itemName + ": " + weight.weight());
        return weight.weight();
    }

}
