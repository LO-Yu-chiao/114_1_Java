public abstract class Role {
    private String name;
    protected int health;
    private int attackPower;

    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // 具體方法
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getAttackPower() { return attackPower; }
    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) this.health = 0;
    }
    public boolean isAlive() { return health > 0; }

    // 具體方法：演算法骨架
    public void takeDamage(int damage) {
        this.health -= damage;
        System.out.println("💥 " + name + " 受到 " + damage + " 點傷害！目前生命值：" + health);
        if (!isAlive()) {
            onDeath(); // 呼叫抽象方法
        }
    }

    // 抽象方法 (必須實作)
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