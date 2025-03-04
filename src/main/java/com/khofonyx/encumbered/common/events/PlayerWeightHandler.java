package com.khofonyx.encumbered.common.events;

import com.khofonyx.encumbered.common.config.WeightConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = "encumbered")
public class PlayerWeightHandler {
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event){
        Player player = event.getEntity();
        if (!player.level().isClientSide() || !player.isCreative() || !player.isSpectator()) {
            float totalWeight = calculateWeight(player);
            applyEncumbrance(player, totalWeight);
        }
    }

    public static float calculateWeight(Player player){
        float totalWeight = 0;
        for (ItemStack stack : player.getInventory().items){
            ResourceLocation itemID = BuiltInRegistries.ITEM.getKey(stack.getItem());
            totalWeight += WeightConfig.getWeight(itemID) * stack.getCount();
        }
        return totalWeight;
    }

    private static void applyEncumbrance(Player player, float weight){
        if (weight >= WeightConfig.THRESHOLD_2){
            player.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(0);
            player.setSprinting(false);
        } else if (weight >= WeightConfig.THRESHOLD_1){
            player.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
            player.setSprinting(false);
        }else{
            player.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(0.41999998688697815);
        }
    }
}