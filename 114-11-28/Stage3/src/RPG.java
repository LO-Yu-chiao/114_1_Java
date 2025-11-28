public class RPG {

    public static void displaySeparator(String title) {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("          " + title);
        System.out.println("════════════════════════════════════════\n");
    }

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第三階段");
        System.out.println("      展示：多層繼承結構設計");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 顯示類別繼承結構 ==========
        System.out.println("📋 類別繼承結構：");
        System.out.println("Role (最高層)");
        System.out.println("├─ MeleeRole (近戰角色)");
        System.out.println("│  ├─ SwordsMan (劍士)");
        System.out.println("│  └─ ShieldSwordsMan (持盾劍士)");
        System.out.println("└─ RangedRole (遠程角色)");
        System.out.println("   ├─ Magician (魔法師)");
        System.out.println("   └─ Archer (弓箭手)");
        System.out.println();

        // ========== 建立角色（參數變更） ==========

        // MeleeRole: (name, health, attack, armor)
        Role sm1 = new SwordsMan("光明劍士", 100, 20, 5);
        Role ssm = new ShieldSwordsMan("持盾劍士", 120, 18, 8, 10); // (..., armor, defense)

        // RangedRole: (name, health, attack, heal/arrows, range, maxEnergy)
        Magician m1 = new Magician("光明法師", 80, 15, 10, 8, 100);
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30); // (..., arrows, range, maxEnergy, maxArrows)

        Role opponent = new SwordsMan("邪惡劍士", 40, 15, 2);

        Role[] gameRoles = {sm1, ssm, m1, archer};

        // ========== 展示類別特性與戰鬥流程 ==========
        displaySeparator("🔍 角色類別特性與戰前準備");

        // 顯示 Melee/Ranged 特性
        for (Role role : gameRoles) {
            System.out.print("【" + role.getName() + "】");
            if (role instanceof MeleeRole) {
                MeleeRole melee = (MeleeRole) role;
                System.out.println(" 近戰: 武器=" + melee.getWeaponType() + " | " + melee);
            } else if (role instanceof RangedRole) {
                RangedRole ranged = (RangedRole) role;
                System.out.println(" 遠程: 攻擊=" + ranged.getRangedAttackType() + " | " + ranged);
            }
            role.prepareBattle();
        }

        displaySeparator("💥 戰鬥示範：護甲、能量與死亡流程");

        // 1. Melee 攻擊 Ranged (展示 Melee attack 與 Ranged takeDamage 的共通性)
        sm1.attack(m1); // 劍士攻擊法師 (法師無護甲，全額傷害 20)

        // 2. Ranged 攻擊 Melee (展示 Ranged attack 消耗能量，Melee takeDamage 護甲減免)
        m1.attack(sm1); // 法師攻擊劍士 (消耗 15 能量，傷害 15)
        // 劍士護甲 5: 實際傷害 = 15 - 5 = 10。

        // 3. Archer 攻擊 (展示雙重資源消耗)
        archer.attack(opponent); // 弓箭手攻擊邪惡劍士 (消耗 10 能量, 1 箭矢)

        // 4. Melee 攻擊 Melee，觸發死亡 (展示 takeDamage 呼叫 onDeath)
        ssm.attack(opponent); // 持盾劍士攻擊邪惡劍士 (傷害 18 - 5 = 13)
        ssm.attack(opponent); // 再次攻擊，邪惡劍士生命值剩餘 40 - 13 - 13 = 14
        ssm.attack(opponent); // 再次攻擊，邪惡劍士生命值剩餘 14 - 13 = 1 -> 死亡 (生命值變為 0)

        // 5. Ranged 能量不足測試
        System.out.println("\n--- 能量不足測試 ---");
        // 強制消耗法師能量，準備測試不足
        m1.setEnergy(10); // 剩餘 10 點能量
        m1.attack(sm1); // 攻擊消耗 15 -> 失敗
        m1.attack(sm1); // 攻擊消耗 15 -> 失敗

        displaySeparator("🏆 戰鬥結束與戰後行為");

        for (Role role : gameRoles) {
            role.afterBattle(); // 遠程角色會自動恢復能量/箭矢
        }

        System.out.println("\n--- 最終狀態 ---");
        for (Role role : gameRoles) {
            System.out.println(role);
        }
        System.out.println(opponent); // 邪惡劍士已陣亡
    }
}
