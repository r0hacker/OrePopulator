package me.tacos.OrePopulator;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class OrePopulator extends JavaPlugin implements Listener {

    private boolean ignore = false;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e){
        if(ignore || !getConfig().contains(e.getWorld().getName()))
            return;

        List<Block> blocks = new ArrayList<>();
        ConfigurationSection sec = getConfig().getConfigurationSection(e.getWorld().getName());
        List<Integer> ores = sec.getIntegerList("ores");
        int replace = sec.getInt("replace");

        Chunk chunk = e.getChunk();
        for (int y = 0; y < e.getWorld().getMaxHeight(); y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block b = chunk.getBlock(x, y, z);
                    if (ores.contains(b.getTypeId())) {
                        blocks.add(b);
                    }
                }
            }
        }

        ignore = true;

        for(Block b : blocks)
            b.setTypeId(replace);

        ignore = false;
    }

}
