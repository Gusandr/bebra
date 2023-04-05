package me.gusandr.bebra.bebra;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Bebra extends JavaPlugin implements Listener {
    private ArmorStand pet;

    public ArmorStand spawnPet(Player player, Material material) {
        ArmorStand armorStand = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStand.setGravity(false);

        ItemStack item = new ItemStack(material);
        armorStand.setHelmet(item);

        return armorStand;

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(pet != null && player.equals(pet.getPassenger())) {
            pet.teleport(player.getLocation().add(0, 1.5, 0));
        }
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);

        getCommand("pet").setExecutor((sender, command, label, args) -> {
            Player player = (Player) sender;
            pet = spawnPet(player, Material.DIAMOND_SWORD);
            pet.setPassenger(player);
            return true;
        });
    }
}
