package org.devathon.contest2016.machine;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menus.SimpleMachineMenu;

import java.util.HashSet;
import java.util.Set;

public class SimpleMachine implements Machine {

    private final String title;
    private final Set<Process> processes;
    private int fuelLevel;

    public SimpleMachine(String title) {
        this.title = title;

        this.processes = new HashSet<>();
    }

    protected void addProcess(Process process) {
        this.processes.add(Preconditions.checkNotNull(process));
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public Set<Process> getProcesses() {
        return this.processes;
    }

    @Override public int getFuelLevel() {
        return this.fuelLevel;
    }

    @Override public void open(Player player) {
        new SimpleMachineMenu(player, Rows.SIX, this.title).open();
    }

}
