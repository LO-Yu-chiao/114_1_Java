// ShieldSwordsMan.java
public class ShieldSwordsMan extends SwordsMan implements Defendable { // ← 實作 Defendable
    private int defenseCapacity;

    public ShieldSwordsMan(String name, int health, int attackPower, int armor, int defenseCapacity) {
        super(name, health, attackPower, armor);
        this.defenseCapacity = defenseCapacity;
    }

    // ========== 實作 Defendable 介面方法 ==========

    /**
     * 實作 Defendable: 取得防禦值
     */
    @Override
    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    /**
     * 實作 Defendable: 執行防禦動作
     */
    @Override
    public void defend() {
        int oldHealth = this.getHealth();
        // 舉盾防禦時，獲得生命值恢復或臨時護盾
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println("🛡️  " + this.getName() + " 舉起盾牌防禦！生命值增加 " + defenseCapacity +
                " 點。(" + oldHealth + " → " + this.getHealth() + ")");
    }
    // ==============================================

    // ... (其他方法保持不變，或進行小幅調整以配合新介面)

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能      ║");
        // ...
        System.out.println("║ 防禦力：+" + getDefenseCapacity() + " 點              ║"); // ← 使用介面方法
        System.out.println("║ 護甲值：+" + getArmor() + " 點              ║");
        System.out.println("╚═════════════════════════════╝");
    }

    // ... (其他繼承和覆寫的方法與第三階段相同)
}