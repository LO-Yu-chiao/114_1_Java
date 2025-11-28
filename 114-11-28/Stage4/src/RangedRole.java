/**
 * RangedRole - 遠程角色抽象類別
 */
public abstract class RangedRole extends Role {
    // ========== 新增屬性 ==========
    private int range;        // 攻擊範圍
    private int energy;       // 當前能量值
    private int maxEnergy;    // 最大能量值

    // ========== 建構子 ==========
    public RangedRole(String name, int health, int attackPower, int range, int maxEnergy) {
        super(name, health, attackPower);
        this.range = range;
        this.maxEnergy = maxEnergy;
        this.energy = maxEnergy; // 初始能量為最大值
    }

    // ========== 新增方法：能量/射程相關 (具體方法) ==========
    public int getRange() { return range; }
    public int getEnergy() { return energy; }
    public int getMaxEnergy() { return maxEnergy; }

    public void setEnergy(int energy) {
        this.energy = Math.min(Math.max(0, energy), maxEnergy);
    }

    public boolean isInRange(int distance) {
        boolean inRange = distance <= range;
        if (!inRange) {
            System.out.println("❌ 目標距離 " + distance + " 超出射程 " + range + "！");
        }
        return inRange;
    }

    public boolean consumeEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            System.out.println("💫 消耗 " + amount + " 點能量，剩餘：" + energy + "/" + maxEnergy);
            return true;
        } else {
            System.out.println("❌ 能量不足！需要 " + amount + "，目前只有 " + energy);
            return false;
        }
    }

    public void restoreEnergy(int amount) {
        int oldEnergy = energy;
        energy = Math.min(energy + amount, maxEnergy);
        System.out.println("✨ 恢復 " + (energy - oldEnergy) + " 點能量 (" +
                oldEnergy + " → " + energy + "/" + maxEnergy + ")");
    }

    // ========== 新增抽象方法 (子類 Magician/Archer 必須實作) ==========
    public abstract String getRangedAttackType();
    protected abstract void onRangedPrepare(); // 遠程特殊準備
    protected abstract void onRangedRecover(); // 遠程特殊恢復

    // ========== 覆寫 Role 的方法 (Template Method) ==========

    /**
     * 遠程角色的共通戰前準備
     */
    @Override
    public void prepareBattle() {
        System.out.println("🎯 " + getName() + " 準備 " + getRangedAttackType() + " 攻擊...");
        System.out.println("📊 能量值：" + energy + "/" + maxEnergy + "，射程：" + range);
        onRangedPrepare();
    }

    /**
     * 遠程角色的戰後行為：恢復能量
     */
    @Override
    public void afterBattle() {
        System.out.print("💤 " + getName() + " 戰後休息...");
        restoreEnergy(10); // 每次戰鬥後恢復 10 點能量
        onRangedRecover();
    }

    @Override
    public String toString() {
        return super.toString() + ", 能量: " + energy + "/" + maxEnergy + ", 射程: " + range;
    }
}
