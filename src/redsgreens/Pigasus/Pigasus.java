package redsgreens.Pigasus;

import java.util.HashMap;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

/**
 * Pigasus for Bukkit
 *
 * @author redsgreens
 */
public class Pigasus extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    private final PigasusPlayerListener playerListener = new PigasusPlayerListener(this);
    private final PigasusEntityListener entityListener = new PigasusEntityListener(this);

    public PigasusEntityManager Manager;
    
    public String Name;
    public String Version;
    public PigasusConfig Config;  
    
	public PermissionHandler Permissions = null;

    public void onEnable() {
        Name = getDescription().getName();
        Version = getDescription().getVersion();

        Config = new PigasusConfig(this);
        Manager = new PigasusEntityManager(this);
        
      	setupPermissions();

      	PluginManager pm = getServer().getPluginManager();
      	pm.registerEvents(playerListener, this);
        pm.registerEvents(entityListener, this);
        pm.registerEvents(entityListener, this);
      	
        System.out.println(this.Name + " v" + this.Version + " is enabled for worlds: " + Config.WorldConfigs.keySet().toString() );
    }
/*
    // return true if Player p has the permission perm
    public boolean isAuthorized(Player p, String perm){
    	boolean retval = p.isOp();

    	try{
    		if(Permissions != null)
    			  if (Permissions.has(p, "pigasus." + perm))
    			      retval = true;
    	}
    	catch (Exception ex){}
    	
    	return retval;	
    }

    private void setupPermissions() {
    	try{
            Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

            if (Permissions == null) {
                if (test != null) {
                    Permissions = ((Permissions)test).getHandler();
                	System.out.println(this.Name + ": " + test.getDescription().getName() + " " + test.getDescription().getVersion() + " found");
                }
            }
    	}
    	catch (Exception ex){}
    }
*/
    
	// return true if Player p has the permission perm
    public boolean isAuthorized(Player p, String perm){
    	boolean retval = p.isOp();

    	if(Permissions == null && retval == false)
    	{
    		try
    		{
    			return p.hasPermission("pigasus." + perm);
    		}
    		catch (Exception ex){}
    	}
    	else
    	{
        	try{
        		if(Permissions != null)
        			  if (Permissions.has(p, "pigasus." + perm))
        			      retval = true;
        	}
        	catch (Exception ex){}
    	}

    	return retval;	
    }
    
    public void setupPermissions() {
    	try{
            Plugin test = getServer().getPluginManager().getPlugin("Permissions");

            if (Permissions == null) {
                if (test != null) {
                    Permissions = ((Permissions)test).getHandler();
                   	getLogger().log(Level.INFO, "Found permissions handler " + test.getDescription().getName() + " " + test.getDescription().getVersion());
                }
            }
    	}
    	catch (Exception ex){}
    }
    public void onDisable() {
        System.out.println(this.Name + " v" + this.Version + " is disabled." );
    }
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

