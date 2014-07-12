/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenjava.entries.UnrealDesign2.t1.players;

import com.tenjava.entries.UnrealDesign2.t1.database.DBManager;
import com.tenjava.entries.UnrealDesign2.t1.database.DBTable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

/**
 *
 * @author Wil
 */
public class UOfflinePlayer
{
    //UUID of player
    private UUID uuid;
    
    //Current connection statement
    private Statement stmt;
    
    //Level of the player
    private int level;
    
    //Balance of the player
    private int bal;
    
    //Kill streak for the player
    private int killstreak;
    
    /**
     * Create a UOfflinePlayer instance for a player with the given name
     * @param uuid uuid of the player
     */
    public UOfflinePlayer(UUID uuid)
    {
        this.uuid = uuid;
        this.stmt = DBManager.getInstance().getStatement();
        
        createPlayer();
        
        loadInformation();
    }
    
    /**
     * @return true if player is in database
     */
    public final boolean playerExists()
    {
        try
        {
            ResultSet rs = stmt.executeQuery("SELECT id FROM "+DBTable.players+" WHERE uuid='"+uuid+"';");
            
            while(rs.next())
            {
                return true;
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Create database information for player if needed
     */
    public final boolean createPlayer()
    {
        if(!playerExists())
        {
            try
            {
                stmt.executeUpdate("INSERT INTO "+DBTable.players+"(uuid) "
                        + "VALUES('"+uuid+"');");
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    
    /**
     * Load the level variable from the database
     */
    public final void loadInformation()
    {
        try
        {
            ResultSet rs = stmt.executeQuery("SELECT * FROM "+DBTable.players+" WHERE uuid='"+uuid+"';");
            
            while(rs.next())
            {
                level = rs.getInt("level");
                bal = rs.getInt("bal");
                killstreak = rs.getInt("kill-streak");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * @return uuid of the player
     */
    public UUID getUUID()
    {
        return uuid;
    }
    
    /**
     * @return level of the player
     */
    public int getLevel()
    {
        return level;
    }
    
    /**
     * @return balance of the player
     */
    public int getBalance()
    {
        return bal;
    }
    
    /**
     * @return kill streak for the player
     */
    public int getKillStreak()
    {
        return killstreak;
    }
}
