package org.devathon.contest2016.listeners;

import org.bukkit.Location;
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
        Optional<Machine> optMachine = MachineFactory.createMachine(event.getItemInHand(), event.getBlockPlaced().getLocation());
        if (!optMachine.isPresent()) {
            return;
        }

        Machine machine = optMachine.get();
        MachineFactory.addMachine(event.getBlockPlaced().getLocation(), machine);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        Location location = event.getBlock().getLocation();
        Optional<Machine> optMachine = MachineFactory.getMachine(location);
        if(!optMachine.isPresent()) {
            return;
        }

        optMachine.get().onBreak(location);
        MachineFactory.removeMachine(location);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK || event.getClickedBlock() == null) {
            return;
        }

        Optional<Machine> optMachine = MachineFactory.getMachine(event.getClickedBlock().getLocation());
        if (optMachine.isPresent()) {
            event.setCancelled(true);
            optMachine.get().open(event.getPlayer());
        } else {
        }
    }

}
