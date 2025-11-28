public class Magician extends RangedRole implements Healable {
    private int healPower;

    public Magician(String name, int health, int attackPower, int healPower,
                    int range, int maxEnergy) {
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
        if (!consumeEnergy(10)) { // 治療消耗 10 點能量
            System.out.println("❌ " + getName() + " 能量不足，無法施放治療！");
            return;
        }

        int oldHealth = target.getHealth();
        target.setHealth(target.getHealth() + getHealPower());
        System.out.println("💚 " + this.getName() + " 治療 " + target.getName() +
                " 回復 " + getHealPower() + " 點生命值 (" +
                oldHealth + " → " + target.getHealth() + ")");
    }

    // ==============================================

    // ========== 實作 RangedRole 的抽象方法 (修復編譯錯誤) ==========

    /**
     * 實作 RangedRole: 取得遠程攻擊類型 (第三階段內容)
     */
    @Override
    public String getRangedAttackType() {
        return "魔法彈";
    }

    /**
     * 實作 RangedRole: 遠程特殊準備 (第三階段內容)
     */
    @Override
    protected void onRangedPrepare() {
        System.out.println("📖 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    /**
     * 實作 RangedRole: 遠程特殊恢復 (第三階段內容)
     */
    @Override
    protected void onRangedRecover() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，深度恢復魔力。");
    }

    // ==============================================

    // ========== 覆寫 Role 的抽象方法 (attack/showSpecialSkill/onDeath) ==========

    /**
     * 覆寫 Role: 攻擊方法 (來自第三階段，加入能量消耗)
     */
    @Override
    public void attack(Role opponent) {
        if (!consumeEnergy(15)) { // 魔法攻擊消耗 15 點能量
            System.out.println("❌ " + getName() + " 能量不足，無法施放魔法！");
            return;
        }

        System.out.println("✨ " + getName() + " 施放 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    /**
     * 覆寫 Role: 顯示特殊技能 (來自第三階段)
     */
    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：元素爆發          ║");
        System.out.println("║ 技能描述：召喚強大魔法攻擊  ║");
        System.out.println("║ 技能效果：範圍魔法傷害      ║");
        System.out.println("║ 額外效果：恢復自身魔力      ║");
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("╚═════════════════════════════╝");
    }

    /**
     * 覆寫 Role: 死亡處理 (來自第三階段)
     */
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    // RangedRole 已經實作 prepareBattle() 和 afterBattle() 的骨架

    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + getHealPower();
    }
}