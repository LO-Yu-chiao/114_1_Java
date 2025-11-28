public class ShieldSwordsMan extends SwordsMan {
    private int defenseCapacity;

    // ========== 建構子變更 ==========
    public ShieldSwordsMan(String name, int health, int attackPower, int armor, int defenseCapacity) {
        super(name, health, attackPower, armor); // 呼叫 SwordsMan(MeleeRole) 建構子
        this.defenseCapacity = defenseCapacity;
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    // 實作特有方法
    public void defence() {
        int oldHealth = this.getHealth();
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println("🛡️  " + this.getName() + " 舉起盾牌防禦！恢復 " + defenseCapacity +
                " 點生命值。(" + oldHealth + " → " + this.getHealth() + ")");
    }

    // ========== 覆寫 MeleeRole 的抽象方法 (經由 SwordsMan 繼承) ==========

    /**
     * 覆寫武器類型
     */
    @Override
    public String getWeaponType() {
        return "單手劍+盾牌";
    }

    /**
     * 覆寫近戰特殊準備
     */
    @Override
    protected void onMeleePrepare() {
        System.out.println("🛡️  檢查盾牌的牢固程度，準備防禦姿態...");
        System.out.println("⚔️  同時確認 " + getWeaponType() + " 的配合度。");
    }

    // ========== 覆寫 Role 的抽象方法 (原有的) ==========

    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5;
        System.out.println("🛡️⚔️  " + this.getName() + " 單手揮動 " + getWeaponType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(reducedDamage);
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能      ║");
        System.out.println("╠═════════════════════════════╣");
        // ... (省略部分文字，只保留變更重點)
        System.out.println("║ 防禦力：+" + defenseCapacity + " 點              ║");
        System.out.println("║ 護甲值：+" + getArmor() + " 點              ║");
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 力竭倒下...");
        System.out.println("🛡️  厚重的盾牌砸在地上，揚起一陣塵土。");
        System.out.println("⚔️  " + getWeaponType() + " 也隨之掉落。");
        System.out.println("---");
    }

    @Override
    public void afterBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢視盾牌上的新傷痕，並進行簡單修補。");
    }
}