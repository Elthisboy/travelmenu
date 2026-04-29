package com.elthisboy.travelmenu.item;

import com.elthisboy.travelmenu.Travelmenu;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TRAVEL_COMPASS = Registry.register(
            Registries.ITEM,
            Identifier.of(Travelmenu.MOD_ID, "travel_compass"),
            new TravelCompassItem(new Item.Settings().maxCount(1))
    );

    public static void load() {

    }
}