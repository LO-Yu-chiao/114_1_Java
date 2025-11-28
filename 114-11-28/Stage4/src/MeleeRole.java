/**
 * MeleeRole - 近戰角色抽象類別
 */
public abstract class MeleeRole extends Role {
    // ========== 新增屬性 ==========
    private int armor; // 護甲值：近戰角色特有

    // ========== 建構子 ==========
    public MeleeRole(String name, int health, int attackPower, int armor) {
        super(name, health, attackPower); // 呼叫 Role 建構子
        this.armor = armor;
    }

    // ========== 新增方法：護甲相關 (具體方法) ==========
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * 計算防禦後的實際傷害 (具體方法)
     */
    public int calculateDefense(int incomingDamage) {
        int actualDamage = Math.max(0, incomingDamage - armor);
        if (armor > 0 && incomingDamage > 0) {
            System.out.println("🛡️  護甲減免 " + Math.min(armor, incomingDamage) + " 點傷害！");
        }
        return actualDamage;
    }

    /**
     * 覆寫 takeDamage 方法，加入護甲計算 (擴展父類方法)
     */
    @Override
    public void takeDamage(int damage) {
        int actualDamage = calculateDefense(damage);
        super.takeDamage(actualDamage);
    }

    // ========== 新增抽象方法 (子類 SwordsMan/ShieldSwordsMan 必須實作) ==========
    public abstract String getWeaponType();

    protected abstract void onMeleePrepare(); // 近戰特殊準備

    // ========== 覆寫 Role 的方法 (Template Method) ==========

    /**
     * 近戰角色的共通戰前準備 (具體方法呼叫抽象方法)
     */
    @Override
    public void prepareBattle() {
        System.out.println("⚔️  " + getName() + " 檢查 " + getWeaponType() + " 的狀態...");
        System.out.println("🛡️  目前護甲值：" + armor);
        onMeleePrepare();
    }

    @Override
    public String toString() {
        return super.toString() + ", 護甲值: " + armor;
    }
}