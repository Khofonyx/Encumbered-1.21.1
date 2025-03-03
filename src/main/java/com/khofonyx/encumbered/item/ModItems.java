package com.khofonyx.encumbered.item;

import com.khofonyx.encumbered.Encumbered;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Encumbered.MOD_ID);

    public static final DeferredItem<Item> MONGUS = ITEMS.register("mongus",
            () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
