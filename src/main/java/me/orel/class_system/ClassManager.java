package me.orel.class_system;

import me.orel.class_system.classes.Golem;
import me.orel.class_system.classes.Herobrine;

import java.util.ArrayList;
import java.util.List;

public class ClassManager {

    private final List<Class> classes = new ArrayList<>();

    public ClassManager() {
        registerClasses();
    }

    private void registerClasses() {
        classes.add(new Golem());
        classes.add(new Herobrine());
    }

    public List<Class> getClasses() {
        return classes;
    }

    public Class getClass(String name) {
        for (Class c : classes) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }
}
