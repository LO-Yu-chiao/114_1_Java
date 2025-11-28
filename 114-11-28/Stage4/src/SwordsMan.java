public class SwordsMan extends MeleeRole { // ← 繼承 MeleeRole

    // ========== 建構子變更 ==========
    public SwordsMan(String name, int health, int attackPower, int armor) { // ← 新增 armor 參數
        super(name, health, attackPower, armor); // 呼叫 MeleeRole 建構子
    }

    // ========== 實作 MeleeRole 的抽象方法 ==========
    @Override
    public String getWeaponType() {
        return "雙手劍";
    }

    @Override
    protected void onMeleePrepare() {
        System.out.println("✨ 擦拭劍刃，劍身反射出凜冽的寒光...");
    }

    // ========== 覆寫 Role 的抽象方法 (原有的) ==========

    @Override
    public void attack(Role opponent) {
        System.out.println("⚔️  " + this.getName() + " 揮動 " + getWeaponType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower()); // 傷害傳遞給 takeDamage，讓護甲系統處理
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("┌─────────────────────────────┐");
        System.out.println("│ " + this.getName() + " 的特殊技能        │");
        System.out.println("├─────────────────────────────┤");
        System.out.println("│ 技能名稱：連續斬擊          │");
        System.out.println("│ 技能描述：快速揮劍三次      │");
        System.out.println("│ 技能效果：造成 150% 傷害    │");
        System.out.println("│ 護甲加成：+" + getArmor() + " 點防禦         │");
        System.out.println("└─────────────────────────────┘");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 倒下了...");
        System.out.println("⚔️  " + getWeaponType() + " 掉落在地上，發出清脆的聲響。");
        System.out.println("🛡️  護甲碎裂散落一地。");
        System.out.println("---");
    }

    // prepareBattle() 由 MeleeRole 提供，afterBattle() 沿用 Role 的空實作或 SwordsMan 原有實作

    @Override
    public void afterBattle() {
        System.out.println("🗡️  " + this.getName() + " 將 " + getWeaponType() + " 收入劍鞘。");
    }
}

