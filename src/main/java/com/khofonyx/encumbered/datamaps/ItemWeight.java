package com.khofonyx.encumbered.datamaps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record ItemWeight(float weight) {
    public static final Codec<ItemWeight> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("weight").forGetter(ItemWeight::weight)
    ).apply(instance, ItemWeight::new));
}

