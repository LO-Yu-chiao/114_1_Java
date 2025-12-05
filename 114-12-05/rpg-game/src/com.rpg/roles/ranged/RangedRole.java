package com.rpg.roles.ranged;

import com.rpg.core.Role;

public abstract class RangedRole extends Role {

    private int range; // 射程屬性
    protected int currentEnergy; // 當前能量/魔力
    private int maxEnergy; // 最大能量/魔力

    public RangedRole(String name, int health, int attackPower, int range, int maxEnergy) {
        super(name, health, attackPower);
        this.range = range;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = maxEnergy; // 初始能量滿
    }

    // Getter 和 Setter
    public int getRange() { return range; }
    public int getCurrentEnergy() { return currentEnergy; }
    public int getMaxEnergy() { return maxEnergy; }

    public void restoreEnergy(int amount) {
        this.currentEnergy = Math.min(this.currentEnergy + amount, this.maxEnergy);
        System.out.println("✨ " + getName() + " 回復 " + amount + " 點能量。");
    }

    // 實作繼承自 Role 的抽象方法：calculateDefense()
    @Override
    public int calculateDefense() {
        // 遠程角色通常基礎防禦很低 (例如：只用射程的一小部分)
        return (int) (this.range * 0.5);
    }

    // RangedRole 特有的抽象方法 (子類必須實作)
    protected abstract void onRangedAttack(); // 遠程攻擊前的特殊行為

    // 覆寫 Role 的方法
    @Override
    public void prepareBattle() {
        System.out.println("🏹 " + getName() + " 調整至最佳射程 (" + range + "m)...");
        System.out.println("🔋 目前能量：" + currentEnergy + "/" + maxEnergy);
    }

    @Override
    public void afterBattle() {
        restoreEnergy(10); // 戰後自動恢復少量能量
        System.out.println("☁️ " + getName() + " 結束戰鬥，心神放鬆。");
    }

    @Override
    public String toString() {
        return super.toString() + ", 射程: " + range + ", 能量: " + currentEnergy + "/" + maxEnergy;
    }
}