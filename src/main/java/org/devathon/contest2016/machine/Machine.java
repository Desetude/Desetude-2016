package org.devathon.contest2016.machine;

import org.bukkit.entity.Player;

import java.util.Set;

public interface Machine {

    String getTitle();

    Set<Process> getProcesses();

    int getFuelLevel();

    void open(Player player);

}
