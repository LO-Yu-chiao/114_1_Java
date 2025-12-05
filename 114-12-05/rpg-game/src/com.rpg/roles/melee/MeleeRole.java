package com.rpg.roles.melee;

import com.rpg.core.Role;

public abstract class MeleeRole extends Role {

    private int armor; // 護甲值：近戰角色特有

    public MeleeRole(String name, int health, int attackPower, int armor) {
        super(name, health, attackPower); // 呼叫 Role 建構子
        this.armor = armor;
    }

    // Getter 和 Setter (修正了中文註解和方法名的格式)
    public int getArmor() { return armor; }
    public void setArmor(int armor) { this.armor = armor; }

    // 【核心修正】實作繼承自 Role 的抽象方法 calculateDefense()
    @Override
    public int calculateDefense() {
        // 近戰角色的基礎防禦即為其護甲值
        return this.armor;
    }

    // MeleeRole 特有的抽象方法 (保留您原本的設計)
    public abstract String getWeaponType();

    protected abstract void onMeleePrepare();

    // 覆寫 Role 的方法
    @Override
    public void prepareBattle() {
        System.out.println("⚔️  " + getName() + " 檢查 " + getWeaponType() + " 的狀態...");
        System.out.println("🛡️  目前總防禦值：" + calculateDefense());
        onMeleePrepare();
    }

    @Override
    public String toString() {
        return super.toString() + ", 護甲值: " + armor;
    }

    // ⚠️ 注意：已移除衝突的 takeDamage 覆寫！
}