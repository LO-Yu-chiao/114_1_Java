public interface Healable {

    /**
     * 對目標進行治療 (抽象方法)
     * @param target 被治療的目標
     */
    void heal(Role target);

    /**
     * 取得治療力 (抽象方法)
     */
    int getHealPower();

    /**
     * 檢查是否可治療 (預設方法)
     * @return true 如果治療力大於 0
     */
    default boolean canHeal() {
        return getHealPower() > 0;
    }

    /**
     * 顯示治療資訊 (預設方法)
     */
    default void showHealInfo() {
        System.out.println("💚 治療力：" + getHealPower() + " | " +
                (canHeal() ? "狀態：可用" : "狀態：無效"));
    }
}