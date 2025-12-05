public class Archer extends RangedRole { // ← 繼承 RangedRole
    // ========== 特有屬性 ==========
    private int arrowCount;    // 箭矢數量
    private int maxArrows;     // 最大箭矢數

    // ========== 建構子 ==========
    public Archer(String name, int health, int attackPower,
                  int range, int maxEnergy, int maxArrows) {
        super(name, health, attackPower, range, maxEnergy);
        this.maxArrows = maxArrows;
        this.arrowCount = maxArrows;
    }

    public int getArrowCount() {
        return arrowCount;
    }

    public void reloadArrows(int amount) {
        int oldCount = arrowCount;
        arrowCount = Math.min(arrowCount + amount, maxArrows);
        System.out.println("🏹 補充箭矢 " + (arrowCount - oldCount) +
                " 支 (" + oldCount + " → " + arrowCount + "/" + maxArrows + ")");
    }

    // ========== 實作 RangedRole 的抽象方法 ==========
    @Override
    public String getRangedAttackType() {
        return "精準箭矢";
    }

    @Override
    protected void onRangedPrepare() {
        System.out.println("🏹 檢查弓弦的張力和箭矢的狀態...");
        System.out.println("🎯 調整呼吸，進入射擊姿態。");
    }

    @Override
    protected void onRangedRecover() {
        System.out.println("💪 " + this.getName() + " 放鬆手臂肌肉，恢復體力。");
        if (arrowCount < maxArrows) {
            reloadArrows(5); // 戰後補充 5 支箭
        }
    }

    // ========== 覆寫 Role 的抽象方法 (加入箭矢與能量系統) ==========
    @Override
    public void attack(Role opponent) {
        if (arrowCount <= 0) {
            System.out.println("❌ " + getName() + " 箭矢用盡，無法攻擊！");
            return;
        }

        if (!consumeEnergy(10)) { // 拉弓需要 10 點體力
            System.out.println("❌ " + getName() + " 體力不足，無法拉弓！");
            return;
        }

        arrowCount--;
        System.out.println("🏹 " + getName() + " 射出 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        System.out.println("📊 剩餘箭矢：" + arrowCount + "/" + maxArrows);
        opponent.takeDamage(this.getAttackPower());
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        // ... (省略部分文字，只保留變更重點)
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("║ 箭矢：" + arrowCount + "/" + maxArrows + "               ║");
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 倒下了...");
        System.out.println("🏹 弓掉落在地上，弓弦斷裂。");
        System.out.println("🎯 箭囊散落一地，箭矢四散。");
        System.out.println("---");
    }

    @Override
    public String toString() {
        return super.toString() + ", 箭矢: " + arrowCount + "/" + maxArrows;
    }
}