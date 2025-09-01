package me.orel.class_system;

import java.util.ArrayList;
import java.util.List;

public class ClassManager {

    private final List<Class> classes = new ArrayList<>();

    public void registerClass(Class c) {
        classes.add(c);
    }

    public Class getClass(String name) {
        for (Class c : classes) {
            if (c.getName().equalsIgnoreCase(name)) {
                return c;
            }
        }
        return null;
    }

    public List<Class> getClasses() {
        return classes;
    }
}
