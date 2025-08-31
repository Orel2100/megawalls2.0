package me.orel.booster;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoosterManager {

    private final List<Booster> activeBoosters = new ArrayList<>();

    public void activateBooster(UUID activator, double multiplier, long durationMillis) {
        // For simplicity, we'll clear any existing boosters and add the new one.
        // A more complex system could queue or stack boosters.
        activeBoosters.clear();
        activeBoosters.add(new Booster(activator, multiplier, durationMillis));
    }

    public double getCurrentMultiplier() {
        activeBoosters.removeIf(booster -> !booster.isActive());
        if (activeBoosters.isEmpty()) {
            return 1.0; // No active booster
        }
        // Return the multiplier of the first active booster
        return activeBoosters.get(0).getMultiplier();
    }

    public Booster getActiveBooster() {
        activeBoosters.removeIf(booster -> !booster.isActive());
        if (activeBoosters.isEmpty()) {
            return null;
        }
        return activeBoosters.get(0);
    }
}
