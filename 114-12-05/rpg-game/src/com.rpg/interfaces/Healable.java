package com.rpg.interfaces;

import com.rpg.core.Role;

public interface Healable {

    int getHealPower(); // 獲取治療能力值

    void heal(Role target); // 對目標進行治療

    // 介面預設方法 (Default Method)
    default void showHealInfo() {
        System.out.println("治療力: " + getHealPower() + " (可治療)");
    }

    // 介面預設方法，可被 Paladin 覆寫
    default boolean canHeal() {
        return getHealPower() > 0;
    }
}