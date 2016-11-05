package org.devathon.contest2016.machine;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.utils.ItemStackBuilder;

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

    long getFuelLevel();

    void addFuel(long amount);

    void decrementFuel();

    void open(Player player);

}
