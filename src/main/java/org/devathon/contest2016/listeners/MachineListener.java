package org.devathon.contest2016.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.devathon.contest2016.machine.Machine;
import org.devathon.contest2016.machines.MachineFactory;

import java.util.Optional;

public class MachineListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Optional<Machine> optMachine = MachineFactory.createMachine(event.getItemInHand());
        if(!optMachine.isPresent()) {
            System.out.println("not present");
            return;
        }

        Machine machine = optMachine.get();
        System.out.println("Is: " + machine.getFormattedName());
        MachineFactory.addMachine(event.getBlockPlaced().getLocation(), machine);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Optional<Machine> prev = MachineFactory.removeMachine(event.getBlock().getLocation());
        System.out.println(prev.isPresent() + " " + (prev.isPresent() ? prev.get().getFormattedName() : ":("));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            System.out.println("not interact");
            return;
        }

        Optional<Machine> optMachine = MachineFactory.getMachine(event.getClickedBlock().getLocation());
        if(optMachine.isPresent()) {
            System.out.println("is " + optMachine.get().getFormattedName());
            event.setCancelled(true);
            optMachine.get().open(event.getPlayer());
        } else {
            System.out.println("not machine interact");
        }
    }

}
