package com.rpg.roles.ranged;

import com.rpg.core.Role;
import com.rpg.interfaces.Healable;

public class Magician extends RangedRole implements Healable {

    private int healPower;
    private static final int HEAL_COST = 20; // 魔法師施展治療的能量消耗

    // 建構子參數：name, health, attackPower, healPower, range, maxEnergy (共 6 個參數)
    public Magician(String name, int health, int attackPower, int healPower, int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }

    // ========== 實作 Healable 介面方法 ==========
    @Override
    public int getHealPower() {
        return healPower;
    }

    @Override
    public void heal(Role target) {
        if (this.currentEnergy < HEAL_COST) {
            System.out.println("❌ " + getName() + " 魔力不足 (剩餘 " + currentEnergy + ")，無法施展治療術！");
            return;
        }

        this.currentEnergy -= HEAL_COST;
        int oldHealth = target.getHealth();
        target.setHealth(target.getHealth() + getHealPower());
        System.out.println("🟢 " + this.getName() + " 消耗 " + HEAL_COST + " 點魔力對 " + target.getName() +
                " 施放治療術！回復 " + getHealPower() + " 點生命值 (" +
                oldHealth + " → " + target.getHealth() + ")");
    }

    // ========== 實作 RangedRole/Role 的抽象方法 ==========
    @Override
    public void attack(Role opponent) {
        if (this.currentEnergy < 5) {
            System.out.println("❌ " + getName() + " 魔力不足，無法施展魔法攻擊！");
            return;
        }
        this.currentEnergy -= 5;
        onRangedAttack();
        System.out.println("🔥 " + this.getName() + " 發射火球攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("🔮 " + this.getName() + " 的特殊技能：奧術掌控 (治療力: " + healPower + ")。");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 法師 " + this.getName() + " 的魔杖碎裂了...");
    }

    @Override
    protected void onRangedAttack() {
        // 魔法師攻擊前的特殊準備：唸咒語
        System.out.println("⚡️ " + getName() + " 開始吟唱咒語...");
    }
}