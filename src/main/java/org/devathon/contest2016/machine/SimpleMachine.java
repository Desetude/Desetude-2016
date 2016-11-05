package org.devathon.contest2016.machine;

import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.menu.Menu;
import org.devathon.contest2016.menus.SimpleMachineMenu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleMachine implements Machine {

    private final String title;
    private final String formattedName;
    private final Set<Process> processes;
    private Process currentProcess;
    private long currentProgress;
    private long fuelLevel;

    private Menu menu;

    public SimpleMachine(String title, String formattedName) {
        this.title = title;
        this.formattedName = formattedName;

        this.processes = new HashSet<>();
        this.menu = new SimpleMachineMenu(this, Rows.SIX, this.title);
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
    public long getFuelLevel() {
        return this.fuelLevel;
    }

    @Override
    public void addFuel(long amount) {
        this.fuelLevel += amount;
    }

    @Override
    public void decrementFuel() {
        this.fuelLevel--;
    }

    @Override
    public void open(Player player) {
        this.menu.open(player);
    }

    @Override
    public void onBreak(Location location) {
        Inventory inventory = this.menu.getInventory().get();
        location.getWorld().dropItemNaturally(location, inventory.getItem(SimpleMachineMenu.INPUT_SLOT));
        location.getWorld().dropItemNaturally(location, inventory.getItem(SimpleMachineMenu.FUEL_SLOT));
        location.getWorld().dropItemNaturally(location, inventory.getItem(SimpleMachineMenu.OUTPUT_SLOT));
    }
}
