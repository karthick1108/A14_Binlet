package com.gloriousfour.binlet;

enum GarbageType {
    GLASS,
    ORGANIC,
    PAPER,
    PLASTICS
}

public class GarbageItem {
    int drawable;
    GarbageType garbageType;

    public GarbageItem(int drawable, GarbageType garbageType) {
        this.drawable = drawable;
        this.garbageType = garbageType;
    }
}
