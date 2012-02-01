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
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
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
	
	/**
	 * Creates a new GuiAppState
	 * @param controller the controller
	 * @param model the model
	 */
	public GuiAppState(Controller controller, Model model) {
		this.controller = controller;
		this.model = model;
	}

	/**
	 * 
	 */
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
		
		pluginButtons();
		

	
	}

	private boolean loaded;
	
	/**
	 * This Method manages the PluginsPopupMenue.
	 */
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
	
	private boolean buttonloaded;
	/**
	 * Shows the Buttons of the several Plugins.
	 */
	public void pluginButtons() {
		Element niftyElement = nifty.getCurrentScreen().findElementByName("panel_bottom_right");
		if (buttonloaded == false) {
			for (PluginController p : model.getPluginControllerList()) {
				 java.util.Map<String, String> m;
				
				m = p.createButtons();
				
				for( String id : m.keySet()) {
					String text = m.get(id);
					ButtonBuilder bb = new ButtonBuilder("ButtonOf_"+p.getClass().getName()+id, text) {{
						 	alignCenter();
				            valignCenter();
				            width("100%");
				            height("25%");
				            
					}};;
					//bb.text(m.get(id));
					bb.build(nifty, nifty.getCurrentScreen(), niftyElement);
					buttonloaded = true;
					
				}
			}	
		}
		
	}
	
	
}
