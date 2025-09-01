package me.orel.class_system.classes;

import me.orel.MegaWallzFFA;
import me.orel.api.ItemStackCreator;
import me.orel.class_system.Class;
import me.orel.class_system.ClassType;
import me.orel.class_system.HitType;
import me.orel.class_system.UpgradeType;
import me.orel.class_system.abilities.Ability;
import me.orel.class_system.abilities.IronPunch;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Golem extends Class {

    private final MegaWallzFFA plugin;

    public Golem(MegaWallzFFA plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "Golem";
    }

    @Override
    public String getDescription() {
        return "A tanky class with high defense.";
    }

    @Override
    public Material getIcon() {
        return Material.IRON_CHESTPLATE;
    }

    @Override
    public ClassType getType() {
        return ClassType.HERO;
    }

    @Override
    public HashMap<Integer, ItemStack> getStartingItems(int upgrade) {
        HashMap<Integer, ItemStack> items = new HashMap<>();
		if(upgrade == 1){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 2){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 3){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 4){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 5){
			ItemStack Sword = new ItemStack(Material.STONE_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 6){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 7){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 8){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
		}
		if(upgrade == 9){
			ItemStack Sword = new ItemStack(Material.IRON_SWORD);
			Sword.addEnchantment(Enchantment.UNBREAKING, 3);
			items.put(0, ItemStackCreator.createItem(Sword, "§bGolem Sword"));
        }
        return items;
    }

    @Override
    public ItemStack[] getArmor() {
        return new ItemStack[]{
                new ItemStack(Material.IRON_BOOTS),
                new ItemStack(Material.IRON_LEGGINGS),
                new ItemStack(Material.IRON_CHESTPLATE),
                new ItemStack(Material.IRON_HELMET)
        };
    }

    @Override
    public Ability getAbility() {
        return new IronPunch(plugin);
    }

    @Override
    public int getEnergyPerHit() {
        return 10;
    }

    @Override
    public int getMaxEnergy() {
        return 100;
    }

    @Override
    public int getUpgradePrice(UpgradeType type, int level) {
        return 100 * level;
    }
}
