package org.devathon.contest2016;

import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class Process {

    private final Predicate<ItemStack> input;
    private final ItemStack output;
    private final long processingPower;

    private Process(Predicate<ItemStack> input, ItemStack output, long processingPower) {
        this.input = input;
        this.output = output;
        this.processingPower = processingPower;
    }

    public static Process create(Predicate<ItemStack> input, ItemStack output, long processingPower) {
        return new Process(input, output, processingPower);
    }

    public static Process create(ItemStack input, ItemStack output, long processingPower) {
        return create(item -> item.equals(input), output, processingPower);
    }

    public Predicate<ItemStack> getInput() {
        return this.input;
    }

    public ItemStack getOutput() {
        return this.output;
    }

    public long getProcessingPower() {
        return this.processingPower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Process process = (Process) o;

        if (processingPower != process.processingPower) {
            return false;
        }
        if (!input.equals(process.input)) {
            return false;
        }
        return output.equals(process.output);

    }

    @Override
    public int hashCode() {
        int result = input.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + (int) (processingPower ^ (processingPower >>> 32));
        return result;
    }

}
