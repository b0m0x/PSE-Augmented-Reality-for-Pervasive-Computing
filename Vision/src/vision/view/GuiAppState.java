package vision.view;

import vision.controller.Controller;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventAnnotationProcessor;

/**
 * renders the user interface
 */
public class GuiAppState extends AbstractAppState  {
	
	private Nifty nifty;
	private Controller controller;
	
	
	public GuiAppState(Controller controller) {
		this.controller = controller;
	}

	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		// TODO Auto-generated method stub
		super.initialize(stateManager, app);
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(),
                app.getInputManager(),
                app.getAudioRenderer(),
                app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
		nifty.fromXml("gui.xml", "start", controller);

		//NiftyEventAnnotationProcessor.process(controller);
		app.getGuiViewPort().addProcessor(niftyDisplay);

	
	}




	
}
