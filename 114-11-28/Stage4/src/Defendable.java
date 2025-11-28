public interface Defendable {

    /**
     * 執行防禦動作 (抽象方法)
     */
    void defend();

    /**
     * 取得防禦值 (抽象方法)
     */
    int getDefenseCapacity();

    /**
     * 檢查是否可防禦 (預設方法)
     * @return true 如果防禦值大於 0
     */
    default boolean canDefend() {
        return getDefenseCapacity() > 0;
    }
}