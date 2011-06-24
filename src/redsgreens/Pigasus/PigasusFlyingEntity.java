package redsgreens.Pigasus;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class PigasusFlyingEntity {
	private Entity Entity = null;
	private PigasusEntityType Type = PigasusEntityType.Unknown;
	private Boolean Landing = false;
    private static Random rand = new Random();

	public PigasusFlyingEntity(Entity entity)
	{
		Entity = entity;
		Type = getType(Entity);
	}
	
	public PigasusEntityType getType()
	{
		return Type;
	}

	public Boolean isDead()
	{
		return Entity.isDead();
	}
	
	public void Fly()
	{
		Vector v = Entity.getVelocity();
		Location loc = Entity.getLocation();
		
		if(!Landing)
		{

			if(rand.nextInt(200) == 0)
				Landing = true;
			else
			{
				if(loc.getY() > 120 && v.getY() > 0)
					v.setY(0);
				if(rand.nextInt(20) == 0 && v.getY() < 0)
					v.setY(rand.nextDouble()/3);
				if(loc.getY() - loc.getWorld().getHighestBlockYAt(loc) < (12 * rand.nextDouble() + 5))
					v.setY(rand.nextDouble()/3);
				if(rand.nextInt(5) == 0)
					v.setX(loc.getDirection().getX()/3);
				if(rand.nextInt(5) == 0)
					v.setZ(loc.getDirection().getZ()/3);
				
				Entity.setVelocity(v);
				Entity.setFallDistance(0);
			}
		}
		else if(rand.nextInt(200) == 0)
		{
			Landing = false;
			TakeOff();
		}

	}
	
	public void TakeOff()
	{
		// launch it into the air
		Entity.setVelocity(new Vector(0,1,0));
	}

	public static PigasusEntityType getType(Entity e)
	{
		if(e instanceof Pig) return PigasusEntityType.Pig;
		else if(e instanceof Chicken) return PigasusEntityType.Chicken;
		else if(e instanceof Cow) return PigasusEntityType.Cow;
		else if(e instanceof Sheep) return PigasusEntityType.Sheep;
		else if(e instanceof Wolf) return PigasusEntityType.Wolf;
		else if(e instanceof Squid) return PigasusEntityType.Squid;
		else if(e instanceof Zombie) return PigasusEntityType.Zombie;
		else if(e instanceof Skeleton) return PigasusEntityType.Skeleton;
		else if(e instanceof Spider) return PigasusEntityType.Spider;
		else if(e instanceof Creeper) return PigasusEntityType.Creeper;
		else if(e instanceof Slime) return PigasusEntityType.Slime;
		else if(e instanceof PigZombie) return PigasusEntityType.PigZombie;
		else return PigasusEntityType.Unknown;
	}
}
