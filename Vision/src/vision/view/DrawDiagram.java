package vision.view;

import java.util.List;
import java.util.logging.Logger;

import vision.model.Sample;


import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;


import de.lessvoid.nifty.render.NiftyRenderEngine;

/**
 * This class draws a diagram with the values.
 *
 */
public class DrawDiagram implements EffectImpl {

	Logger log = Logger.getLogger(" ");
	/**
	 * list of samples.
	 */
	public static List<Sample> samples;
	
	/**
	 * 
	 */
	@Override
	public void activate(Nifty niftyy, Element element, EffectProperties parameter) {

	}
	
	/**
	 * 
	 */
	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
	}
	/**
	 * 
	 */
	@Override
	public void execute(Element element, float normalizedTime, Falloff falloff,
			NiftyRenderEngine r) {

		//element.findElementByName("panel-draw-diagram");
		//r.createImage("Texture/walltexture.jpg", true);
		r.moveTo(300, 100);
		long time = System.currentTimeMillis();
		long firstTime = samples.get(0).getUpdate();
		for (Sample s : samples) {
			r.renderQuad((int) (300*(s.getUpdate() - firstTime) / (float) ( time - firstTime)) , 300 - 10 * (int)s.getValue(), 5, 5);
		}
	}

}
