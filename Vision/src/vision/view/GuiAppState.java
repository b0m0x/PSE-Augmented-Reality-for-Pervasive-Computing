package vision.view;

import vision.controller.Controller;
import vision.controller.PluginController;
import vision.model.Model;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventAnnotationProcessor;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.checkbox.builder.CheckboxBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;

/**
 * renders the user interface
 */
public class GuiAppState extends AbstractAppState  {
	
	private Nifty nifty;
	private Controller controller;
	private Model model;
	private View view;
	
	
	public GuiAppState(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}

	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		// TODO Auto-generated method stub
		super.initialize(stateManager, app);
		this.view = (View) app;
		NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(app.getAssetManager(),
                app.getInputManager(),
                app.getAudioRenderer(),
                app.getGuiViewPort());
        nifty = niftyDisplay.getNifty();
		nifty.fromXml("gui.xml", "start", controller);

		//NiftyEventAnnotationProcessor.process(controller);
		app.getGuiViewPort().addProcessor(niftyDisplay);

	
	}

	private boolean loaded;

	public void managePluginsPopupMenu() {
		
		nifty.gotoScreen("managePlugins");
		Element niftyElement = nifty.getCurrentScreen().findElementByName("x");
		
		if(loaded == false) {
			
		
		for(Plugin p: model.getPluginList()) {
			
			PanelBuilder pb = new PanelBuilder() {{
				 alignCenter();
		            valignCenter();
		            width("100%");
		            height("25%");
			}};
			pb.childLayoutHorizontal();
			Element panel = pb.build(nifty, nifty.getCurrentScreen(), niftyElement);
			
			
			
			
			LabelBuilder lb= new LabelBuilder(){{
	            alignLeft();
	            width("75%");
	            color("#ff0000");
			}};
			lb.text(p.getClass().getSimpleName());
			Element el = lb.build(nifty, nifty.getCurrentScreen(), panel);
			
			
			CheckboxBuilder chb= new CheckboxBuilder();
			chb.id("Pluginchecbox_"+p.getClass().getName());
			chb.checked(view.getStateManager().hasState(p));
			Element b = chb.build(nifty, nifty.getCurrentScreen(), panel);
			
			loaded = true;
		}
	  }
			
		
	}
	
	
	public void pluginButtons() {
		//for (PluginController p :)
	}
	
	
}
