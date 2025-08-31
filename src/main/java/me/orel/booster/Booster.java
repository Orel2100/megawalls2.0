package me.orel.booster;

import java.util.UUID;

public class Booster {

    private final UUID activator;
    private final double multiplier;
    private final long endTime;

    public Booster(UUID activator, double multiplier, long durationMillis) {
        this.activator = activator;
        this.multiplier = multiplier;
        this.endTime = System.currentTimeMillis() + durationMillis;
    }

    public UUID getActivator() {
        return activator;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public long getEndTime() {
        return endTime;
    }

    public boolean isActive() {
        return System.currentTimeMillis() < endTime;
    }
}
