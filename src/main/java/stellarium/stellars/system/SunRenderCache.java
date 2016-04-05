package stellarium.stellars.system;

import sciapi.api.value.euclidian.EVector;
import stellarium.client.ClientSettings;
import stellarium.stellars.layer.IRenderCache;
import stellarium.stellars.view.IStellarViewpoint;
import stellarium.util.math.SpCoord;
import stellarium.util.math.Spmath;
import stellarium.util.math.VecMath;

public class SunRenderCache implements IRenderCache<Sun> {
	
	protected SpCoord appCoord = new SpCoord();
	protected double size;

	@Override
	public void initialize(ClientSettings settings) { }

	@Override
	public void updateCache(ClientSettings settings, Sun object, IStellarViewpoint viewpoint) {
		EVector ref = new EVector(3);
		ref.set(viewpoint.getProjection().transform(object.earthPos));
		appCoord.setWithVec(ref);
		viewpoint.applyAtmRefraction(this.appCoord);
		
		this.size = object.radius / Spmath.getD(VecMath.size(object.earthPos))*99.0*20;
	}

}