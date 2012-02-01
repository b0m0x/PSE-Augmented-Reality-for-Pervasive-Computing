package vision.view;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyRenderEngine;

public class DrawDiagram implements EffectImpl {

	private Nifty nifty;

	@Override
	public void activate(Nifty nifty, Element element, EffectProperties parameter) {
		 
		
		nifty.gotoScreen("draw");
		element = nifty.getCurrentScreen().findElementByName("panel-draw-diagram");
		

		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Element element, float normalizedTime, Falloff falloff,
			NiftyRenderEngine r) {
		// TODO Auto-generated method stub
		
	}

}
