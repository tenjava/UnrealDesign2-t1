/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenjava.entries.UnrealDesign2.t1.configs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Wil
 */
public class SettingsConfig
{
    private JavaPlugin plugin;
    
    private File file;
    private FileConfiguration config;
    
    /**
     * Baisc and only constructor for this plugin
     * 
     * @param plugin JavaPlugin instance
     */
    public SettingsConfig(JavaPlugin plugin)
    {        
        file = new File(plugin.getDataFolder(), "settings.yml");
        this.plugin = plugin;
        
        if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
        
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
        
        config = YamlConfiguration.loadConfiguration(file);
        
        initialize();
    }
    
    /**
     * Initialize everything that needs to be initialized
     */
    private void initialize()
    {
        
    }
    
    /**
     * @return FileConfiguration for this config
     */
    public FileConfiguration getConfig()
    {
        return config;
    }
    
    /**
     * First run checks
     * @throws IOException should never be thrown
     */
    public void firstRun() throws IOException
    {
        if(!file.exists())
        {
            file.getParentFile().mkdirs();
            copy(plugin.getResource("settings.yml"), file);
        }
    }

    /**
     * Copy resources from the file and load it to the file
     * 
     * @param in resource to load from
     * @param file file to put into
     */
    private void copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0)
            {
                out.write(buf,0,len);
            }
            out.close();
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void save()
    {
        try
        {
            config.save(file);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void load()
    {
        try
        {
            config.load(file);
        }
        catch (IOException ex)
        {
            Logger.getLogger(SettingsConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InvalidConfigurationException ex)
        {
            Logger.getLogger(SettingsConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
