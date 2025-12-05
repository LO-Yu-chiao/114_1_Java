package com.rpg.roles.classes;

import com.rpg.core.Role;
import com.rpg.interfaces.Defendable;
import com.rpg.interfaces.Healable;
import com.rpg.roles.melee.MeleeRole;

public class Paladin extends MeleeRole implements Defendable, Healable {

    private int defenseCapacity;
    private int healPower;
    private int holyPower;
    private int maxHolyPower;

    public Paladin(String name, int health, int attackPower, int armor,
                   int defenseCapacity, int healPower, int maxHolyPower) {
        super(name, health, attackPower, armor); // 呼叫 MeleeRole 建構子
        this.defenseCapacity = defenseCapacity;
        this.healPower = healPower;
        this.maxHolyPower = maxHolyPower;
        this.holyPower = maxHolyPower;
    }

    public int getHolyPower() {
        return holyPower;
    }

    // 【核心修正】實作繼承自 Role 的抽象方法 calculateDefense()
    @Override
    public int calculateDefense() {
        // Paladin 的總防禦 = 基礎護甲值 (來自 MeleeRole) + 防禦潛力值 (來自 Defendable)
        return this.getArmor() + this.defenseCapacity;
    }

    // ========== 實作 Defendable 介面方法 ==========
    @Override
    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    @Override
    public void defend() {
        if (holyPower < 10) {
            System.out.println("❌ " + getName() + " 聖能不足 (剩餘 " + holyPower + ")，無法施展神聖防禦！");
            return;
        }
        holyPower -= 10;
        int oldHealth = this.getHealth();
        // 這裡使用 calculateDefense() 替換原本的 getDefenseCapacity() * 2，更貼合邏輯
        this.setHealth(this.getHealth() + calculateDefense());
        System.out.println("🛡️✨ " + this.getName() + " 消耗 10 點聖能施展神聖防禦！生命值大幅提升 " +
                (calculateDefense()) + " 點。(" + oldHealth + " → " + this.getHealth() + ")");
    }

    // ========== 實作 Healable 介面方法 ==========
    @Override
    public int getHealPower() {
        return healPower;
    }

    @Override
    public void heal(Role target) {
        if (holyPower < 15) {
            System.out.println("❌ " + getName() + " 聖能不足 (剩餘 " + holyPower + ")，無法施展聖光治療！");
            return;
        }
        holyPower -= 15;
        int oldHealth = target.getHealth();
        target.setHealth(target.getHealth() + getHealPower());
        System.out.println("💚✨ " + this.getName() + " 消耗 15 點聖能施放聖光治療 " + target.getName() +
                "！回復 " + getHealPower() + " 點生命值 (" +
                oldHealth + " → " + target.getHealth() + ")");
    }

    // Paladin 覆寫 canHeal 預設方法 (展示預設方法可被覆寫)
    @Override
    public boolean canHeal() {
        return getHealPower() > 0 && holyPower >= 15;
    }

    // ========== 覆寫 MeleeRole/Role 的抽象方法 ==========
    @Override
    public void attack(Role opponent) {
        System.out.println("⚔️✨ " + this.getName() + " 揮舞 " + getWeaponType() +
                " 進行神聖攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能：制裁  ║");
        System.out.println("║ 總防禦力：+" + calculateDefense() + "              ║");
        System.out.println("║ 護甲值：+" + getArmor() + "                 ║");
        System.out.println("║ 防禦力：+" + getDefenseCapacity() + "               ║");
        System.out.println("║ 治療力：+" + getHealPower() + "               ║");
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 倒下，身上的聖光消散...");
        System.out.println("🙏 聖盾和聖劍化為塵土。");
        System.out.println("---");
    }

    @Override
    public String getWeaponType() {
        return "聖劍+聖盾";
    }

    @Override
    protected void onMeleePrepare() {
        System.out.println("🙏 " + getName() + " 低聲祈禱，聖光開始聚集...");
        System.out.println("✨ 聖劍和聖盾都散發出神聖的光芒。");
        System.out.println("📊 聖能值：" + holyPower + "/" + maxHolyPower);
    }

    @Override
    public void afterBattle() {
        int recoverAmount = 10;
        holyPower = Math.min(holyPower + recoverAmount, maxHolyPower);
        System.out.println("🙏 " + getName() + " 感謝聖光的庇護。");
        System.out.println("🌟 恢復 " + recoverAmount + " 點聖能 (剩餘 " + holyPower + "/" + maxHolyPower + ")");
    }

    @Override
    public String toString() {
        return super.toString() + ", 聖能: " + holyPower + "/" + maxHolyPower +
                ", 總防禦: " + calculateDefense() + ", 治療力: " + getHealPower();
    }
}