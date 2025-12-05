package com.rpg;

import com.rpg.core.Role;
import com.rpg.interfaces.*;
import com.rpg.roles.classes.*; // 包含 Paladin, Swordsman 等具體職業
import com.rpg.roles.melee.*;
import com.rpg.roles.ranged.*;

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

        // 建立角色 (請確保類別名稱和建構子參數匹配)
        // 假設 Swordsman(name, health, attack, armor)
        Role sm1 = new Swordsman("光明劍士", 100, 20, 5);
        // 假設 ShieldSwordsman(name, health, attack, armor, defenseCapacity)
        ShieldSwordsman ssm = new ShieldSwordsman("持盾劍士", 120, 18, 8, 10);
        // 假設 Magician(name, health, attack, healPower, range, maxEnergy)
        Magician m1 = new Magician("光明法師", 80, 15, 10, 8, 100);
        // 假設 Archer(name, health, attack, range, maxEnergy)
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30);

        // Paladin(name, health, attack, armor, defenseCapacity, healPower, maxHolyPower)
        Paladin paladin = new Paladin("聖騎士", 130, 22, 10, 12, 12, 100);

        Role[] allRoles = {sm1, ssm, m1, archer, paladin};

        // ========== 顯示類別與介面結構 (保持原樣) ==========
        System.out.println("📋 類別與介面結構：");
        System.out.println("Role (抽象類別)");
        System.out.println("├─ MeleeRole");
        System.out.println("│  ├─ Swordsman"); // 修正類別名稱為 Java 慣例
        System.out.println("│  ├─ ShieldSwordsman (實作 Defendable)"); // 修正類別名稱
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
            if (role instanceof Defendable) {
                Defendable d = (Defendable) role;
                System.out.printf("✅ %s - 防禦力：%d (可防禦：%s)\n",
                        role.getName(), d.getDefenseCapacity(), d.canDefend());
            }
        }
        System.out.println();

        System.out.println("【可治療角色 (Healable)】");
        for (Role role : allRoles) {
            if (role instanceof Healable) {
                Healable h = (Healable) role;
                System.out.printf("✅ %s - ", role.getName());
                h.showHealInfo();
            }
        }
        System.out.println("\n⭐ 聖騎士 - 同時擁有防禦和治療能力！\n");

        displaySeparator("⚔️  聖騎士的核心能力測試");

        paladin.prepareBattle();

        System.out.println("\n--- 聖騎士治療 ---");
        paladin.heal(sm1);

        System.out.println("\n--- 聖騎士防禦 ---");
        paladin.defend();

        System.out.println("\n--- 法師治療 ---");
        // 這裡需要 Magician 實作 Healable 介面
        Magician healerM1 = (Magician) m1;
        healerM1.heal(paladin);

        System.out.println("\n--- 持盾劍士防禦 ---");
        // 這裡需要 ShieldSwordsman 實作 Defendable 介面
        ShieldSwordsman defenderSsm = (ShieldSwordsman) ssm;
        defenderSsm.defend();

        displaySeparator("🏆 戰鬥結束與介面預設方法應用");

        paladin.afterBattle();
        m1.afterBattle();

        System.out.println("\n--- 聖騎士 canHeal 測試 ---");
        System.out.println("聖騎士當前聖能：" + paladin.getHolyPower());
        System.out.println("聖騎士 canHeal(): " + paladin.canHeal());

        System.out.println("\n--- 最終狀態 ---");
        System.out.println(sm1);
        System.out.println(ssm);
        System.out.println(m1);
        System.out.println(paladin);
    }
}