package org.devathon.contest2016.machine;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Function;

public class Process {

    private final Function<ItemStack, Integer> inputAmountRequired;
    private final ItemStack output;
    private final long processingPower;

    private Process(Function<ItemStack, Integer> inputAmountRequired, ItemStack output, long processingPower) {
        this.inputAmountRequired = inputAmountRequired;
        this.output = output;
        this.processingPower = processingPower;
    }

    public static Process create(Function<ItemStack, Integer> inputAmountRequired, ItemStack output, long processingPower) {
        return new Process(inputAmountRequired, output, processingPower);
    }

    public static Process create(Material material, ItemStack output, long processingPower) {
        return create(item -> {
            if (item.getType().equals(material)) {
                return 1;
            }

            return -1;
        }, output, processingPower);
    }

    public int getAmountRequired(ItemStack itemStack) {
        return this.inputAmountRequired.apply(itemStack);
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public long getProcessingPower() {
        return this.processingPower;
    }

}
