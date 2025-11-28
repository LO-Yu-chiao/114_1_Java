// RPG.java
public class RPG {

    public static void displaySeparator(String title) {
        System.out.println("\n════════════════════════════════════════");
        System.out.println("          " + title);
        System.out.println("════════════════════════════════════════\n");
    }

    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第四階段");
        System.out.println("          展示：介面的應用");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // 建立角色（參數變更）
        Role sm1 = new SwordsMan("光明劍士", 100, 20, 5);
        ShieldSwordsMan ssm = new ShieldSwordsMan("持盾劍士", 120, 18, 8, 10); // 實作 Defendable
        Magician m1 = new Magician("光明法師", 80, 15, 10, 8, 100); // 實作 Healable
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30);

        // 核心新增：聖騎士（同時實作兩個介面）
        Paladin paladin = new Paladin("聖騎士", 130, 22, 10, 12, 12, 100);

        Role[] allRoles = {sm1, ssm, m1, archer, paladin};

        // ========== 顯示類別與介面結構 ==========
        System.out.println("📋 類別與介面結構：");
        System.out.println("Role (抽象類別)");
        System.out.println("├─ MeleeRole");
        System.out.println("│  ├─ SwordsMan");
        System.out.println("│  ├─ ShieldSwordsMan (實作 Defendable)");
        System.out.println("│  └─ Paladin (實作 Defendable + Healable) ⭐");
        System.out.println("└─ RangedRole");
        System.out.println("   ├─ Magician (實作 Healable)");
        System.out.println("   └─ Archer");
        System.out.println();
        System.out.println("介面 (Interface)：");
        System.out.println("├─ Defendable：防禦能力");
        System.out.println("└─ Healable：治療能力");


        displaySeparator("🔍 介面能力展示 (多型應用)");

        System.out.println("【可防禦角色 (Defendable)】");
        for (Role role : allRoles) {
            if (role instanceof Defendable) { // 檢查是否擁有 Defendable 能力
                Defendable d = (Defendable) role;
                System.out.printf("✅ %s - 防禦力：%d (可防禦：%s)\n",
                        role.getName(), d.getDefenseCapacity(), d.canDefend()); // 呼叫介面方法和預設方法
            }
        }
        System.out.println();

        System.out.println("【可治療角色 (Healable)】");
        for (Role role : allRoles) {
            if (role instanceof Healable) { // 檢查是否擁有 Healable 能力
                Healable h = (Healable) role;
                System.out.printf("✅ %s - ", role.getName());
                h.showHealInfo(); // 呼叫介面預設方法
            }
        }
        System.out.println("\n⭐ 聖騎士 - 同時擁有防禦和治療能力！\n");

        displaySeparator("⚔️  聖騎士的核心能力測試");

        // 1. Paladin 戰前準備 (MeleeRole prepareBattle)
        paladin.prepareBattle();

        // 2. Paladin 治療 (Healable 介面)
        System.out.println("\n--- 聖騎士治療 ---");
        paladin.heal(sm1);

        // 3. Paladin 防禦 (Defendable 介面)
        System.out.println("\n--- 聖騎士防禦 ---");
        paladin.defend();

        // 4. Magician 治療 (Healable 介面)
        System.out.println("\n--- 法師治療 ---");
        m1.heal(paladin);

        // 5. ShieldSwordsMan 防禦 (Defendable 介面)
        System.out.println("\n--- 持盾劍士防禦 ---");
        ssm.defend();

        displaySeparator("🏆 戰鬥結束與介面預設方法應用");

        // 戰後行為 (Role afterBattle)
        paladin.afterBattle();
        m1.afterBattle();

        // 6. 聖騎士 canHeal 測試 (展示覆寫後的預設方法)
        System.out.println("\n--- 聖騎士 canHeal 測試 ---");
        System.out.println("聖騎士當前聖能：" + paladin.getHolyPower());
        System.out.println("聖騎士 canHeal(): " + paladin.canHeal()); // 聖能低於 15 點會回傳 false

        System.out.println("\n--- 最終狀態 ---");
        System.out.println(sm1);
        System.out.println(ssm);
        System.out.println(m1);
        System.out.println(paladin);
    }
}