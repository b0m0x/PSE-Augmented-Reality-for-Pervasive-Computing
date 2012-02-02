package vision.view;

import java.util.logging.Logger;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;

public class DrawDiagram implements EffectImpl {

	private Nifty nifty;
	Logger log = Logger.getLogger(" ");

	@Override
	public void activate(Nifty nifty, Element element, EffectProperties parameter) {
		 		
		
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Element element, float normalizedTime, Falloff falloff,
			NiftyRenderEngine r) {
		element.findElementByName("panel-draw-diagram");
		r.createImage("Texture/walltexture.jpg", true);
		r.moveTo(300, 100);
	}

}
