package com.rpg.interfaces;

public interface Defendable {

    int getDefenseCapacity(); // 獲取防禦潛力值

    void defend(); // 施展主動防禦技能

    // 介面預設方法 (Default Method)，讓 Paladin 可以在 RPG.java 中呼叫
    default boolean canDefend() {
        return getDefenseCapacity() > 0;
    }
}