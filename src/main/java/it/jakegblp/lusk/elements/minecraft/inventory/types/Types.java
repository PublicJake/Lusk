package it.jakegblp.lusk.elements.minecraft.inventory.types;

import ch.njol.skript.Skript;
import ch.njol.skript.registrations.Classes;
import it.jakegblp.lusk.wrappers.EnumWrapper;
import org.bukkit.inventory.EquipmentSlot;

public class Types {
    static {
        if (Skript.classExists("org.bukkit.inventory.EquipmentSlot") && Classes.getExactClassInfo(EquipmentSlot.class) == null) {
            EnumWrapper<EquipmentSlot> EQUIPMENTSLOT_ENUM = new EnumWrapper<>(EquipmentSlot.class);
            Classes.registerClass(EQUIPMENTSLOT_ENUM.getClassInfo("equipmentslot")
                    .user("equipment ?slots?")
                    .name("Equipment Slot")
                    .description("All the Equipment Slots.")
                    .since("1.0.0"));
        }
    }
}
