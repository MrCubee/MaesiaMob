package fr.maesia.mob.maesiamob;

import fr.maesia.mob.maesiamob.MaesiaMobFiles.MaesiaMobFiles;
import fr.maesia.mob.maesiamob.commands.Commands;

import fr.maesia.mob.maesiamob.listener.CombatsMobs;
import fr.maesia.mob.maesiamob.listener.DeathMob;
import fr.maesia.mob.maesiamob.listener.InteractMenu;
import fr.maesia.mob.maesiamob.listener.SpawnMob;
import fr.maesia.mob.maesiamob.utils.LoadUnload.LoadUnLoad;
import fr.maesia.mob.maesiamob.utils.TchatInteract.TchatInteract;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Objects;

public final class MaesiaMob extends JavaPlugin {


    private static MaesiaMob instance;
    private final PluginManager listener = getServer().getPluginManager();

    public static MaesiaMob getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("=========================================");
        getLogger().info("Plugin initialization in progress ...");
        MaesiaMobFiles messages = MaesiaMobFiles.Messages;
        messages.Create(getLogger());
        getLogger().info("=========================================");

        //commands
        Objects.requireNonNull(this.getCommand("MoreMob")).setExecutor(new Commands());
        Objects.requireNonNull(this.getCommand("Bestiaire")).setExecutor(new Commands());


        //Listener
        listener.registerEvents(new InteractMenu(), this);
        listener.registerEvents(new TchatInteract(), this);
        listener.registerEvents(new SpawnMob(), this);
        listener.registerEvents(new DeathMob(), this);
        listener.registerEvents(new CombatsMobs(), this);


        // Plugin startup logic

        LoadUnLoad.onLoad();
        LoadUnLoad.onLoadEntityCustom();


    }

    @Override
    public void onDisable() {
        try {
            LoadUnLoad.onSaveEntityCustom();
            LoadUnLoad.onSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
