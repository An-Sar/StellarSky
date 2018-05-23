package stellarium.render.stellars;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import stellarium.render.SkyRI;
import stellarium.render.shader.IShaderObject;
import stellarium.view.ViewerInfo;

public class StellarRI implements Supplier<IShaderObject> {
	public final Minecraft minecraft;
	public final WorldClient world;
	public final float partialTicks;
	public final float deepDepth;

	public final ViewerInfo info;
	public final double screenSize;

	public StellarRI(SkyRI info) {
		this.minecraft = info.minecraft;
		this.world = info.world;
		this.partialTicks = info.partialTicks;
		this.deepDepth = info.deepDepth;

		this.info = info.info;
		this.screenSize = info.screenSize;
	}
	
	private IShaderObject activeShader;
	private int callList;
	
	public void setActiveShader(IShaderObject activeShader) {
		this.activeShader = activeShader;
	}

	@Override
	public IShaderObject get() {
		return this.activeShader;
	}
	
	public int getAtmCallList() {
		return this.callList;
	}

	public void setAtmCallList(int list) {
		this.callList = list;
	}
}