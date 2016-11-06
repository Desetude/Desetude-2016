package org.devathon.contest2016.machine;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.menu.Menu;
import org.devathon.contest2016.menus.SimpleMachineMenu;
import org.devathon.contest2016.utils.Hologram;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleMachine implements Machine {

    private final String title;
    private final String formattedName;
    private final int fuelMultiplier;
    private final Set<Process> processes;
    private final Menu menu;
    private final Hologram hologram;
    private Process currentProcess;
    private long currentProgress;
    private long fuelLevel;

    public SimpleMachine(String title, String formattedName, int fuelMultiplier, Location location) {
        this.title = title;
        this.formattedName = formattedName;
        this.fuelMultiplier = fuelMultiplier;

        this.processes = new HashSet<>();
        this.menu = new SimpleMachineMenu(this, Rows.SIX, this.title, this.fuelMultiplier);
        this.hologram = new Hologram(location.add(0.5, -0.6, 0.5), this.getFormattedName());
    }

    public SimpleMachine(String title, String formattedName, Location location) {
        this(title, formattedName, 1, location);
    }

    protected void addProcesses(Process... process) {
        this.processes.addAll(Preconditions.checkNotNull(Arrays.asList(process)));
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getFormattedName() {
        return this.formattedName;
    }

    @Override
    public Set<Process> getProcesses() {
        return this.processes;
    }

    @Override
    public Optional<Process> getCurrentProcess() {
        return Optional.ofNullable(this.currentProcess);
    }

    @Override
    public void setCurrentProcess(Process process) {
        this.currentProcess = process;
    }

    @Override
    public long getCurrentProgress() {
        return this.currentProgress;
    }

    @Override
    public void incrementProgress() {
        this.currentProgress++;
    }

    @Override
    public void resetProcess() {
        this.currentProcess = null;
        this.currentProgress = 0;
    }

    @Override
    public double getFuelLevel() {
        return this.fuelLevel;
    }

    @Override
    public void addFuel(double amount) {
        this.fuelLevel += amount;
    }

    @Override
    public void decrementFuel(double amount) {
        this.fuelLevel -= amount;
    }

    @Override
    public void open(Player player) {
        this.menu.open(player);
    }

    @Override
    public void onBreak(Location location) {
        Inventory inventory = this.menu.getInventory().get();
        ItemStack input = inventory.getItem(SimpleMachineMenu.INPUT_SLOT);
        ItemStack output = inventory.getItem(SimpleMachineMenu.OUTPUT_SLOT);
        ItemStack fuel = inventory.getItem(SimpleMachineMenu.FUEL_SLOT);

        if (input != null && input.getType() != Material.AIR) {
            location.getWorld().dropItemNaturally(location, input);
        }

        if (fuel != null && fuel.getType() != Material.AIR) {
            location.getWorld().dropItemNaturally(location, fuel);
        }

        if (output != null && output.getType() != Material.AIR) {
            location.getWorld().dropItemNaturally(location, output);
        }

        this.hologram.remove();
    }

}
