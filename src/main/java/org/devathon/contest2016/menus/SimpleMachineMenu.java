package org.devathon.contest2016.menus;

import com.google.common.collect.Lists;
import com.google.common.collect.Range;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleMachineMenu extends Menu {

    public static final int INPUT_SLOT = 10;
    public static final int OUTPUT_SLOT = 16;
    public static final int FUEL_SLOT = 40;
    private static final List<Integer> PROGRESS_SLOTS = Lists.newArrayList(12, 13, 14);

    private final SimpleMachine machine;
    private final int fuelMultiplier;

    private String noProgressionReason;

    public SimpleMachineMenu(SimpleMachine machine, Rows rows, String title, int fuelMultiplier) {
        super(rows, title);

        this.machine = machine;
        this.addMoveableSlot(INPUT_SLOT);
        this.addMoveableSlot(FUEL_SLOT);
        this.addMoveableSlot(OUTPUT_SLOT);
        this.addMenuItems();

        this.createInventory();
        new UpdateRunnable().runTaskTimer(DevathonPlugin.get(), 1L, 1L);
        this.fuelMultiplier = fuelMultiplier;
    }

    public void addMenuItems() {
        IntStream.concat(
                IntStream.concat(IntStream.range(0, 9), IntStream.range(27, 36)),
                IntStream.of(9, 11, 15, 17, 18, 20, 21, 22, 23, 24, 26, 36, 37, 38, 39, 41, 42, 43, 44, 45, 46, 47, 48, 50, 51, 52, 53)
        ).forEach(index -> this.addMenuItem(index, MenuItem.create(new ItemStackBuilder()
                .material(Material.STAINED_GLASS_PANE)
                .data(7)
                .name(" ")
                .build())));

        this.addMenuItem(12, MenuItem.create(() -> new ItemStackBuilder()
                .material(Material.STAINED_GLASS)
                .name(this.getName(0))
                .lore(getProgressLore())
                .data(this.getProgressData(0))
                .build()));

        this.addMenuItem(13, MenuItem.create(() -> new ItemStackBuilder()
                .material(Material.STAINED_GLASS)
                .name(this.getName(11))
                .lore(getProgressLore())
                .data(this.getProgressData(11))
                .build()));

        this.addMenuItem(14, MenuItem.create(() -> new ItemStackBuilder()
                .material(Material.STAINED_GLASS)
                .name(this.getName(22))
                .lore(getProgressLore())
                .data(this.getProgressData(22))
                .build()));

        this.addMenuItem(19, MenuItem.create(new ItemStackBuilder()
                .material(Material.SIGN)
                .name(ChatColor.GREEN + "Input")
                .build()));

        this.addMenuItem(25, MenuItem.create(new ItemStackBuilder()
                .material(Material.SIGN)
                .name(ChatColor.YELLOW + "Output")
                .build()));

        this.addMenuItem(19, MenuItem.create(new ItemStackBuilder()
                .material(Material.SIGN)
                .name(ChatColor.GREEN + "Input")
                .build()));

        this.addMenuItem(49, MenuItem.create(new ItemStackBuilder()
                .material(Material.SIGN)
                .name(ChatColor.GRAY + "Fuel")
                .build()));
    }

    private List<String> getProgressLore() {
        return this.noProgressionReason != null ? Collections.singletonList(this.noProgressionReason) : new ArrayList<>();
    }

    private String getName(int offset) {
        int data = this.getProgressData(offset);

        ChatColor color;

        switch (data) {
            case 5:
                color = ChatColor.GREEN;
                break;
            case 4:
                color = ChatColor.YELLOW;
                break;
            case 1:
                color = ChatColor.GOLD;
                break;
            default:
                color = ChatColor.RED;
                break;
        }

        return color.toString() + this.machine.getCurrentProgressPercentage() + "%";
    }

    private int getProgressData(int offset) {
        int data = 14;

        int percentageProgress = this.machine.getCurrentProgressPercentage();

        if(percentageProgress > 77 + offset) {
            data = 5;
        } else if(percentageProgress > 44 + offset) {
            data = 4;
        } else if(percentageProgress > 11 + offset) {
            data = 1;
        }

        return data;
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
            this.process();
            PROGRESS_SLOTS.forEach(SimpleMachineMenu.this::updateItem);
        }

        private void process() {
            SimpleMachineMenu menu = SimpleMachineMenu.this;
            Machine machine = menu.machine;
            Inventory inventory = menu.inventory;

            ItemStack input = inventory.getItem(INPUT_SLOT);
            if (input == null) {
                machine.resetProcess();
                menu.noProgressionReason = ChatColor.YELLOW + "No input item.";
                return;
            }

            Optional<Process> optProcess = machine.getCurrentProcess();
            if (!optProcess.isPresent()) {
                optProcess = machine.getProcesses().stream().filter(process -> process.getAmountRequired(input) >= 1).findAny();
                if (!optProcess.isPresent()) {
                    menu.noProgressionReason = ChatColor.RED + "Invalid input.";
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

                menu.noProgressionReason = ChatColor.YELLOW + "Not enough of the input item.";
                return;
            }

            ItemStack output = inventory.getItem(OUTPUT_SLOT);
            if (output != null && !output.isSimilar(process.getOutput())) {
                menu.noProgressionReason = ChatColor.RED + "Disparate output item.";
                return;
            }

            if (machine.getFuelLevel() < menu.fuelMultiplier) {
                ItemStack fuel = inventory.getItem(FUEL_SLOT);
                if(fuel == null) {
                    menu.noProgressionReason = ChatColor.RED + "No fuel left.";
                    return;
                }

                if (!FuelUtils.isFuel(fuel)) {
                    return;
                }

                if(fuel.getType() == Material.LAVA_BUCKET) {
                    inventory.setItem(FUEL_SLOT, new ItemStack(Material.BUCKET));
                } else {
                    int fuelAmount = fuel.getAmount() - 1;
                    if (fuelAmount <= 0) {
                        inventory.remove(fuel);
                    } else {
                        fuel.setAmount(fuelAmount);
                    }
                }

                machine.addFuel(FuelUtils.getFuelTime(fuel));
            }

            if(machine.getCurrentProgress() < process.getProcessingPower()) {
                menu.noProgressionReason = null;

                machine.incrementProgress();
                machine.decrementFuel(menu.fuelMultiplier);
            }

            if (machine.getCurrentProgress() < process.getProcessingPower()) {
                return;
            }

            if (output != null) {
                int outputAmount = output.getAmount() + process.getOutput().getAmount();
                if (outputAmount > output.getMaxStackSize()) {
                    menu.noProgressionReason = ChatColor.RED + "No more output room.";
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
        }

    }

}
