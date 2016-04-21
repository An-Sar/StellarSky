package stellarium;

import net.minecraft.util.ResourceLocation;
import stellarapi.api.perdimres.PerDimensionResource;
import stellarapi.feature.perdimres.PerDimensionResourceRegistry;
import stellarium.api.StellarSkyAPI;

public class StellarSkyResources {
	
	public static final PerDimensionResource resourceEndSky =
			new PerDimensionResource("End_Sky", new ResourceLocation("textures/environment/end_sky.png"));
	
	
	public static final PerDimensionResource resourceMilkyway =
			new PerDimensionResource("Milkyway", new ResourceLocation("stellarium", "stellar/milkyway.png"));
	
	public static final PerDimensionResource resourceStar =
			new PerDimensionResource("Star", new ResourceLocation("stellarium", "stellar/star.png"));

	public static final PerDimensionResource resourceSunHalo =
			new PerDimensionResource("Sun_Halo", new ResourceLocation("stellarium", "stellar/halo.png"));
	
	public static final PerDimensionResource resourceMoonSurface = 
			new PerDimensionResource("Moon_Surface", new ResourceLocation("stellarium", "stellar/lune.png"));
	
	public static final PerDimensionResource resourceMoonHalo =
			new PerDimensionResource("Moon_Halo", new ResourceLocation("stellarium", "stellar/haloLune.png"));
	
	public static final PerDimensionResource resourcePlanetSmall =
			new PerDimensionResource("Planet_Small", new ResourceLocation("stellarium", "stellar/star.png"));
	
	public static void init() {
		PerDimensionResourceRegistry.getInstance().registerResourceId("End_Sky");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Milkyway");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Star");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Sun_Halo");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Moon_Surface");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Moon_Halo");
		PerDimensionResourceRegistry.getInstance().registerResourceId("Planet_Small");
	}
}