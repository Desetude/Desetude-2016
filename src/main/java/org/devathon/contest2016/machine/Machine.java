package org.devathon.contest2016.machine;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;

public interface Machine {

    String getTitle();

    String getFormattedName();

    Set<Process> getProcesses();

    Optional<Process> getCurrentProcess();

    void setCurrentProcess(Process process);

    long getCurrentProgress();

    default int getCurrentProgressPercentage() {
        if (!this.getCurrentProcess().isPresent()) {
            return 0;
        }

        return (int) (((double) this.getCurrentProgress() / this.getCurrentProcess().get().getProcessingPower()) * 100);
    }

    void incrementProgress();

    void resetProcess();

    double getFuelLevel();

    void addFuel(double amount);

    void decrementFuel(double amount);

    void open(Player player);

    void onBreak(Location location);

}
