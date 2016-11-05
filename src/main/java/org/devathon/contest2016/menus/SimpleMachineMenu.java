package org.devathon.contest2016.menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machine.Process;
import org.devathon.contest2016.menu.MenuItemClickEvent;
import org.devathon.contest2016.menu.Rows;
import org.devathon.contest2016.menu.items.MenuItem;
import org.devathon.contest2016.menu.menu.Menu;
import org.devathon.contest2016.utils.ItemStackBuilder;

import java.util.Optional;
import java.util.UUID;

public class SimpleMachineMenu extends Menu {

    private final Machine machine;

    public SimpleMachineMenu(Machine machine, Player player, Rows rows, String title) {
        super(player, rows, title);

        this.machine = machine;
        this.addMoveableSlot(11);
        this.addMenuItems();
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

        this.addMenuItem(13, MenuItem.create(() -> new ItemStackBuilder()
                .material(Material.SIGN)
                .name((this.machine.getCurrentProgressPercentage() > 50 ? ChatColor.YELLOW : ChatColor.RED) +
                        Integer.toString(this.machine.getCurrentProgressPercentage()) + "%")
                .build()));
    }

    @Override public void onItemClick(MenuItemClickEvent event) {
        super.onItemClick(event);
    }

    @Override
    public void open() {
        super.open();

        new UpdateRunnable().runTaskTimer(DevathonPlugin.get(), 1L, 1L);
    }

    private class UpdateRunnable extends BukkitRunnable {

        @Override
        public void run() {
            SimpleMachineMenu menu = SimpleMachineMenu.this;
            Machine machine = menu.machine;
            Inventory inventory = menu.inventory;

            Optional<Menu> openMenu = Menu.getFromInventory(player.getOpenInventory().getTopInventory());
            if(!openMenu.isPresent() || openMenu.get() != menu) {
                this.cancel();
                return;
            }

            ItemStack input = inventory.getItem(11);
            if (input == null) {
                machine.resetProcess();
                menu.updateItem(13);
                return;
            }

            Optional<Process> optProcess = machine.getCurrentProcess();
            if(!optProcess.isPresent()) {
                optProcess = machine.getProcesses().stream().filter(process -> process.getAmountRequired(input) >= 1).findAny();
                if(!optProcess.isPresent()) {
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
                menu.updateItem(13);
                return;
            }

            ItemStack output = inventory.getItem(15);
            if(output != null && !output.isSimilar(process.getOutput())) {
                return;
            }

            machine.incrementProgress();
            menu.updateItem(13);
            if(machine.getCurrentProgress() < process.getProcessingPower()) {
                return;
            }

            if(output != null) {
                int outputAmount = output.getAmount() + process.getOutput().getAmount();
                if (outputAmount > output.getMaxStackSize()) {
                    return;
                }

                output.setAmount(outputAmount);
            } else {
                output = process.getOutput();
            }

            inventory.setItem(15, output);

            int inputAmount = input.getAmount() - amountRequired;
            if(inputAmount <= 0) {
                inventory.remove(input);
            } else {
                input.setAmount(inputAmount);
            }

            machine.resetProcess();
            menu.updateItem(13);
        }

    }

}
