package com.rpg.core;

public abstract class Role {
    private String name;
    protected int health;
    private int attackPower;

    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // 具體方法 (Getters and Setters)
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) this.health = 0;
    }
    public boolean isAlive() { return health > 0; }

    // 具體方法：演算法骨架 (Template Method)
    public void takeDamage(int damage) {
        // 1. 呼叫子類別實作的防禦值
        int defense = calculateDefense();

        // 2. 計算實際傷害
        int actualDamage = Math.max(0, damage - defense);

        this.health -= actualDamage;

        System.out.println("🛡️ " + name + " 的防禦值為: " + defense + "。");
        System.out.println("💥 " + name + " 受到 " + actualDamage + " 點傷害！目前生命值：" + health);

        if (!isAlive()) {
            onDeath();
        }
    }

    // 抽象方法 (必須實作)
    public abstract int calculateDefense();
    public abstract void attack(Role opponent);
    public abstract void showSpecialSkill();
    public abstract void onDeath();
    public abstract void prepareBattle();
    public abstract void afterBattle();

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}