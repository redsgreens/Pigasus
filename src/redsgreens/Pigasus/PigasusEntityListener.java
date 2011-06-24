package redsgreens.Pigasus;

import java.util.Random;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityListener;

public class PigasusEntityListener extends EntityListener {
    private final Pigasus plugin;
    Random rand = new Random();

    public PigasusEntityListener(Pigasus instance) {
        plugin = instance;
    }

    @Override
	public void onCreatureSpawn(CreatureSpawnEvent event) 
	{
    	// return if the event is already cancelled
    	if(event.isCancelled()) return;

    	Entity entity = event.getEntity();
    	Integer chance = plugin.Config.getHoveringChance(entity);
		if(chance > 0)
		{
			if(rand.nextInt((Integer)(100 / chance)) == 0)
				plugin.Manager.addEntity(entity);
		}
	}

    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {
    	// return if the event is already cancelled
    	if(event.isCancelled()) return;

    	// return if the damage wasn't caused by falling 
    	if(event.getCause() != DamageCause.FALL) return;

    	Entity entity = event.getEntity();

    	// return if the entity isn't known to be flying
    	if(!plugin.Manager.isFlying(entity)) return;

    	// cancel the event to prevent damage
    	event.setCancelled(true);
    }

}
