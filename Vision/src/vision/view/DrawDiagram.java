package vision.view;

import java.util.logging.Logger;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.StandardControl;
import de.lessvoid.nifty.effects.EffectImpl;
import de.lessvoid.nifty.effects.EffectProperties;
import de.lessvoid.nifty.effects.Falloff;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.render.NiftyImage;
import de.lessvoid.nifty.render.NiftyRenderEngine;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.spi.render.RenderImage;

public class DrawDiagram implements EffectImpl {

	Logger log = Logger.getLogger(" ");

	@Override
	public void activate(Nifty niftyy, Element element, EffectProperties parameter) {
		
			
	}
	

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}
	
	private Nifty nifty;
	@Override
	public void execute(Element element, float normalizedTime, Falloff falloff,
			NiftyRenderEngine r) {

			}

}
