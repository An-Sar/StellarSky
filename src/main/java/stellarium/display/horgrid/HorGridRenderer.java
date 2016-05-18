package stellarium.display.horgrid;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import stellarium.display.DisplayRenderInfo;
import stellarium.display.IDisplayRenderer;
import stellarium.render.celesital.EnumRenderPass;
import stellarium.render.celesital.StellarRenderInfo;

@SideOnly(Side.CLIENT)
public class HorGridRenderer implements IDisplayRenderer<HorGridCache> {

	@Override
	public void render(DisplayRenderInfo info, HorGridCache cache) {
		GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
		
		if(!cache.enabled || info.isPostCelesitals)
			return;
		
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		
		if(cache.gridEnabled) {
			GL11.glLineWidth(2.0f);

			info.tessellator.startDrawingQuads();

			for(int longc=0; longc<cache.longn; longc++){
				for(int latc=0; latc<cache.latn; latc++){
					int longcd=(longc+1)%cache.longn;

					info.tessellator.setColorRGBA_F((float)cache.colorvec[longc][latc].getX(),
							(float)cache.colorvec[longc][latc].getY(),
							(float)cache.colorvec[longc][latc].getZ(), cache.brightness);
					info.tessellator.addVertex(cache.displayvec[longc][latc].getX(), cache.displayvec[longc][latc].getY(), cache.displayvec[longc][latc].getZ());
					info.tessellator.addVertex(cache.displayvec[longcd][latc].getX(), cache.displayvec[longcd][latc].getY(), cache.displayvec[longcd][latc].getZ());
					info.tessellator.addVertex(cache.displayvec[longcd][latc+1].getX(), cache.displayvec[longcd][latc+1].getY(), cache.displayvec[longcd][latc+1].getZ());
					info.tessellator.addVertex(cache.displayvec[longc][latc+1].getX(), cache.displayvec[longc][latc+1].getY(), cache.displayvec[longc][latc+1].getZ());
				}
			}

			info.tessellator.draw();

			GL11.glLineWidth(1.0f);
		}

		if(cache.horizonEnabled) {
			GL11.glShadeModel(GL11.GL_SMOOTH);
			
			GL11.glLineWidth(5.0f);

			info.tessellator.startDrawing(GL11.GL_LINES);
			info.tessellator.setColorRGBA_F(0.3f, 0.7f, 1.0f, 2.0f * cache.brightness);

			for(int longc=0; longc<cache.longn; longc++){
				int longcd=(longc+1)%cache.longn;
				info.tessellator.addVertex(cache.horizon[longc].getX(), cache.horizon[longc].getY(), cache.horizon[longc].getZ());
				info.tessellator.addVertex(cache.horizon[longcd].getX(), cache.horizon[longcd].getY(), cache.horizon[longcd].getZ());
			}

			info.tessellator.draw();
			
			GL11.glLineWidth(1.0f);

			GL11.glShadeModel(GL11.GL_FLAT);
		}
		
		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
