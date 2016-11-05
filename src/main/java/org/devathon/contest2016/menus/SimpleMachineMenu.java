package org.devathon.contest2016.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.machine.SimpleMachine;
import org.devathon.contest2016.menu.MenuItemClickEvent;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.items.MenuItem;
import org.devathon.contest2016.menu.menu.Menu;
import org.devathon.contest2016.utils.FuelUtils;
import org.devathon.contest2016.utils.ItemStackBuilder;

import java.util.Optional;

public class SimpleMachineMenu extends Menu {

    private static final int INPUT_SLOT = 11;
    private static final int PERCENTAGE_SLOT = 13;
    private static final int FUEL_STATUS_SLOT = 22;
    private static final int OUTPUT_SLOT = 15;
    private static final int FUEL_SLOT = 49;

    private final SimpleMachine machine;

    public SimpleMachineMenu(SimpleMachine machine, Rows rows, String title) {
        super(rows, title);

        this.machine = machine;
        this.addMoveableSlot(INPUT_SLOT);
        this.addMoveableSlot(FUEL_SLOT);
        this.addMoveableSlot(OUTPUT_SLOT);
        this.addMenuItems();

        this.createInventory();
        new UpdateRunnable().runTaskTimer(DevathonPlugin.get(), 1L, 1L);
    }

    public void addMenuItems() {
        for (int i : new int[]{1, 2, 3, 10, 12, 19, 20, 21}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                    .data(14)
                    .name(ChatColor.YELLOW + "Input")
                    .build()));
        }

        for (int i : new int[]{5, 6, 7, 14, 16, 23, 24, 25}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                    .data(5)
                    .name(ChatColor.YELLOW + "Output")
                    .build()));
        }

        for (int i : new int[]{39, 40, 41, 48, 50}) {
            this.addMenuItem(i, MenuItem.create(new ItemStackBuilder().material(Material.STAINED_GLASS_PANE)
                    .data(7)
                    .name(ChatColor.YELLOW + "Fuel")
                    .build()));
        }

        this.addMenuItem(PERCENTAGE_SLOT, MenuItem.create(() -> new ItemStackBuilder()
                .material(Material.SIGN)
                .name((this.machine.getCurrentProgressPercentage() > 50 ? ChatColor.YELLOW : ChatColor.RED) +
                        Integer.toString(this.machine.getCurrentProgressPercentage()) + "%")
                .build()));

        this.addMenuItem(FUEL_STATUS_SLOT, MenuItem.create(() ->
                new ItemStackBuilder()
                        .material(Material.STAINED_GLASS)
                        .data(this.machine.getFuelLevel() > 0 ? 5 : 14)
                        .name(ChatColor.GOLD + "Fuel status")
                        .build()));
    }

    @Override public void onItemClick(MenuItemClickEvent event) {
        super.onItemClick(event);
    }

    @Override
    public void open(Player player) {
        super.open(player);
    }

    private class UpdateRunnable extends BukkitRunnable {

        @Override
        public void run() {
            SimpleMachineMenu menu = SimpleMachineMenu.this;
            Machine machine = menu.machine;
            Inventory inventory = menu.inventory;

            ItemStack input = inventory.getItem(INPUT_SLOT);
            if (input == null) {
                machine.resetProcess();
                menu.updateItem(PERCENTAGE_SLOT);
                return;
            }

            Optional<Process> optProcess = machine.getCurrentProcess();
            if (!optProcess.isPresent()) {
                optProcess = machine.getProcesses().stream().filter(process -> process.getAmountRequired(input) >= 1).findAny();
                if (!optProcess.isPresent()) {
                    return;
                }

                Process process = optProcess.get();
                machine.setCurrentProcess(process);
                return;
            }

            Process process = optProcess.get();
            int amountRequired = process.getAmountRequired(input);
            if (amountRequired < 1 || amountRequired > input.getAmount()) {
                machine.resetProcess();
                menu.updateItem(PERCENTAGE_SLOT);
                return;
            }

            ItemStack output = inventory.getItem(OUTPUT_SLOT);
            if (output != null && !output.isSimilar(process.getOutput())) {
                return;
            }

            if (machine.getFuelLevel() <= 0) {
                ItemStack fuel = inventory.getItem(FUEL_SLOT);
                if (!FuelUtils.isFuel(fuel)) {
                    menu.updateItem(FUEL_STATUS_SLOT);
                    return;
                }

                int fuelAmount = fuel.getAmount() - 1;
                if (fuelAmount <= 0) {
                    inventory.remove(fuel);
                } else {
                    fuel.setAmount(fuelAmount);
                }

                machine.addFuel(FuelUtils.getFuelTime(fuel));
            }

            machine.incrementProgress();
            machine.decrementFuel();

            menu.updateItem(FUEL_STATUS_SLOT);
            menu.updateItem(PERCENTAGE_SLOT);

            if (machine.getCurrentProgress() < process.getProcessingPower()) {
                return;
            }

            if (output != null) {
                int outputAmount = output.getAmount() + process.getOutput().getAmount();
                if (outputAmount > output.getMaxStackSize()) {
                    return;
                }

                output.setAmount(outputAmount);
            } else {
                output = process.getOutput();
            }

            inventory.setItem(OUTPUT_SLOT, output);

            int inputAmount = input.getAmount() - amountRequired;
            if (inputAmount <= 0) {
                inventory.remove(input);
            } else {
                input.setAmount(inputAmount);
            }

            machine.resetProcess();
            menu.updateItem(PERCENTAGE_SLOT);
        }
    }

}
