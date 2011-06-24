package redsgreens.Pigasus;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.entity.Entity;

public class PigasusEntityManager {
	private Pigasus plugin;
	private HashMap<Entity, PigasusFlyingEntity> Entities = new HashMap<Entity, PigasusFlyingEntity>();

	public PigasusEntityManager(Pigasus instance)
	{
		plugin = instance;

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
		    public void run() {
		    		runLoop();
		    }
		}, 1, 3);

	}
	
	public void runLoop()
	{
		Iterator<PigasusFlyingEntity> itr = Entities.values().iterator();
		while(itr.hasNext())
		{
			PigasusFlyingEntity entity = itr.next();
			
			if(entity.isDead())
			{
				itr.remove();
				continue;
			}
			
			entity.Fly();
		}
	}
	
	public void addEntity(Entity entity)
	{
		// add the entity to the list
		if(!isFlying(entity))
		{
			PigasusFlyingEntity flyer = new PigasusFlyingEntity(entity); 
			Entities.put(entity, flyer);

			// launch it into the air
			flyer.TakeOff();
		}
	}
	
	public void removeEntity(Entity entity)
	{
		if(isFlying(entity))
			Entities.remove(entity);
	}
	
	public Boolean isFlying(Entity entity)
	{
		if(Entities.containsKey(entity))
			return true;
		else
			return false;
	}
	
}
