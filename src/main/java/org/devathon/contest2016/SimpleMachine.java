package org.devathon.contest2016;

import org.bukkit.entity.Player;

import java.util.Set;

public class SimpleMachine implements Machine {

    @Override public String getTitle() {
        return null;
    }

    @Override public Set<Process> getProcesses() {
        return null;
    }

    @Override public int getFuelLevel() {
        return 0;
    }

    @Override public void open(Player player) {

    }

}
