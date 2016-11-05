package org.devathon.contest2016.machine;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface Machine {

    String getTitle();

    Set<Process> getProcesses();

    Optional<Process> getCurrentProcess();

    void setCurrentProcess(Process process);

    long getCurrentProgress();

    default int getCurrentProgressPercentage() {
        if(!this.getCurrentProcess().isPresent()) {
            return 0;
        }

        return (int) ((this.getCurrentProgress() / (double) this.getCurrentProcess().get().getProcessingPower()) * 100);
    }

    void incrementProgress();

    void resetProcess();

    long getFuelLevel();

    void open(Player player);

}
