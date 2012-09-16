package redsgreens.Pigasus;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class PigasusFlyingEntity {
	private Entity entity = null;
	private PigasusEntityType Type = PigasusEntityType.Unknown;
	private Boolean Landing = false;
    private static Random rand = new Random();
    private Integer flightLevel = 64;
    private Integer flightHeight = 12 + rand.nextInt(24);

	public PigasusFlyingEntity(Entity e)
	{
		entity = e;
		Type = getType(entity);
		if(rand.nextInt(3) == 0)
			Landing = true;
	}
	
	public PigasusEntityType getType()
	{
		return Type;
	}

	public Boolean isDead()
	{
		return entity.isDead();
	}
	
	public void Fly()
	{
		Vector v = entity.getVelocity();
		Location loc = entity.getLocation();
		
		if(!Landing)
		{

			if(rand.nextInt(400) == 0)
				Landing = true;
			else
			{
				updateFlightLevel();
				
				if(loc.getY() > 120 && v.getY() > 0)
					v.setY(0);
				if(rand.nextInt(20) == 0 && v.getY() < 0)
					v.setY(rand.nextDouble()/3);
				if(loc.getY() - flightLevel < (flightHeight * rand.nextDouble() + 5))
					v.setY(rand.nextDouble()/3);
				if(rand.nextInt(5) == 0)
					v.setX(loc.getDirection().getX()/3);
				if(rand.nextInt(5) == 0)
					v.setZ(loc.getDirection().getZ()/3);
				
				entity.setVelocity(v);
				entity.setFallDistance(0);
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
		entity.setVelocity(new Vector(0,rand.nextDouble()/3,0));
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
	
	private void updateFlightLevel()
	{
		Location loc = entity.getLocation();
		Integer f = loc.getWorld().getHighestBlockYAt(loc);
		if(f > 0)
		{
			if(flightLevel - f > 12)
				flightLevel = flightLevel - 1;
			else
				flightLevel = f;
		}
	}
}
