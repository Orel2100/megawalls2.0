package me.orel.class_system;

public class Upgrade {

    private final UpgradeType type;
    private int level;

    public Upgrade(UpgradeType type) {
        this.type = type;
        this.level = 1;
    }

    public UpgradeType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void levelUp() {
        this.level++;
    }
}
