package org.devathon.contest2016.machine;

import com.google.common.base.Preconditions;
import org.bukkit.entity.Player;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menus.SimpleMachineMenu;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SimpleMachine implements Machine {

    private final String title;
    private final Set<Process> processes;
    private Process currentProcess;
    private long currentProgress;
    private long fuelLevel;

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

    @Override public long getFuelLevel() {
        return this.fuelLevel;
    }

    @Override public void open(Player player) {
        new SimpleMachineMenu(this, player, Rows.SIX, this.title).open();
    }

}
