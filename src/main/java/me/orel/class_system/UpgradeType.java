package me.orel.class_system;

public enum UpgradeType {
    ABILITY("Ability"),
    KIT("KitUpgrade");

    private String name;

    private UpgradeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
