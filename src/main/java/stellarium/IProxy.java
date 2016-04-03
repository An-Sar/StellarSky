package stellarium;

import java.io.IOException;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.world.World;
import stellarium.client.ClientSettings;
import stellarium.stellars.layer.CelestialManager;

public interface IProxy {
	
	public void preInit(FMLPreInitializationEvent event);
	
    public void load(FMLInitializationEvent event) throws IOException;

    public void postInit(FMLPostInitializationEvent event);
    
    public World getDefWorld();
    public World getDefWorld(boolean isRemote);
    
    public ClientSettings getClientSettings();
    
    public CelestialManager getClientCelestialManager();

}
