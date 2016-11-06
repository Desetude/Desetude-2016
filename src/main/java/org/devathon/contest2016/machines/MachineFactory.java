package org.devathon.contest2016.machines;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.utils.NBTUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

public class MachineFactory {

    private static Map<String, Function<Location, Machine>> machines;
    private static Map<Location, Machine> machineLocations;

    static {
        machines = new HashMap<>();
        machineLocations = new HashMap<>();
    }

    public static Set<Map.Entry<Location, Machine>> getMachines() {
        return machineLocations.entrySet();
    }

    public static void registerMachine(String key, Function<Location, Machine> function) {
        machines.put(key, function);
    }

    public static void addMachine(Location location, Machine machine) {
        machineLocations.put(location, machine);
    }

    public static Optional<Machine> removeMachine(Location location) {
        return Optional.ofNullable(machineLocations.remove(location));
    }

    public static Optional<Machine> getMachine(Location location) {
        return Optional.ofNullable(machineLocations.get(location));
    }

    public static Optional<Machine> createMachine(ItemStack itemStack, Location location) {
        Optional<String> optKey = NBTUtils.getString(itemStack, "machine");
        if (!optKey.isPresent() || !machines.containsKey(optKey.get())) {
            return Optional.empty();
        }

        return Optional.ofNullable(machines.get(optKey.get()).apply(location));
    }

}
