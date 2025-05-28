package com.whatsthatglyph;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum Glyph {
    SPECIAL_ATTACK(6400, "Special Attack"),
    BIND(6401, "Bind"),
    SPELLS(6402, "Spells"),
    HEAVY_RANGED(6403, "Heavy Ranged"),
    TWO_HANDED(6404, "Two-Handed Melee");

    @Getter
    private final int spriteId;

    @Getter
    private final String attackMethod;

    private static final Map<Integer, Glyph> GLYPH_MAP = new HashMap<>();

    static {
        for (Glyph method : values()) {
            GLYPH_MAP.put(method.spriteId, method);
        }
    }

    Glyph(int spriteId, String attackMethod) {
        this.spriteId = spriteId;
        this.attackMethod = attackMethod;
    }

    public static Glyph fromSpriteId(int spriteId) {
        return GLYPH_MAP.get(spriteId);
    }
}
