package redsgreens.Pigasus;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * Handle events for all Player related events
 * @author redsgreens
 */
public class PigasusPlayerListener implements Listener {
    private final Pigasus plugin;

    public PigasusPlayerListener(Pigasus instance) {
        plugin = instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    // catch player+entity events, looking for wand usage on an entity
    {
    	Entity entity = event.getRightClicked(); 
    	
    	// return if something not allowed was clicked
    	if(plugin.Config.getHoveringChance(entity) == -1) return;
    	
    	Player player = event.getPlayer();

    	// return if the click was with something other than the wand
    	if(player.getItemInHand().getType() != plugin.Config.WandItem) return;
    	
    	// check for permission
    	if(!plugin.isAuthorized(player, "wand")) 
    	{
    		if(plugin.Config.ShowErrorsInClient)
				player.sendMessage("§cErr: " + plugin.Name + ": you don't have permission.");
    		return;
    	}
    	
    	// checks passed, make this pig fly!
    	plugin.Manager.addEntity(entity);
    }
}

