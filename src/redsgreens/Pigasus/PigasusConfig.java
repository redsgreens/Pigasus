package redsgreens.Pigasus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.yaml.snakeyaml.Yaml;

public class PigasusConfig {
    private final Pigasus plugin;

    public Material WandItem = Material.FEATHER;
	public Boolean ShowErrorsInClient = true;
	public HashMap<String, HashMap<String, Integer>> WorldConfigs = new HashMap<String, HashMap<String, Integer>>();
	
    public PigasusConfig(Pigasus instance) {
        plugin = instance;
        
        LoadConfig();
    }

    public Integer getHoveringChance(Entity entity)
    {
    	String world = entity.getWorld().getName();
    	if(WorldConfigs.containsKey(world))
    	{
    		HashMap<String, Integer> worldconf = WorldConfigs.get(world);
    		String entitytype = PigasusFlyingEntity.getType(entity).toString();
    		if(worldconf.containsKey(entitytype))
    			return worldconf.get(entitytype);
    		else 
    			return -1;
    	}
    	else
    		return -1;
    }
    
	@SuppressWarnings("unchecked")
	public void LoadConfig()
	{
		try
		{
			// create the data folder if it doesn't exist
			File folder = plugin.getDataFolder();
	    	if(!folder.exists())
	    		folder.mkdirs();
    	
	    	// create a stock config file if it doesn't exist
	    	File configFile = new File(folder, "config.yml");
			if (!configFile.exists()){
				configFile.createNewFile();
				InputStream res = Pigasus.class.getResourceAsStream("/config.yml");
				FileWriter tx = new FileWriter(configFile);
				for (int i = 0; (i = res.read()) > 0;) tx.write(i);
				tx.flush();
				tx.close();
				res.close();
			}

			// create an empty config
			HashMap<String, Object> configMap = new HashMap<String, Object>();
			
			BufferedReader rx = new BufferedReader(new FileReader(configFile));
			Yaml yaml = new Yaml();
			
			try{
				configMap = (HashMap<String,Object>)yaml.load(rx);
			}
			catch (Exception ex){
				System.out.println(ex.getMessage());
			}
			finally
			{
				rx.close();
			}

			if(configMap.containsKey("Worlds"))
				WorldConfigs = (HashMap<String, HashMap<String, Integer>>)configMap.get("Worlds");
			
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

}
