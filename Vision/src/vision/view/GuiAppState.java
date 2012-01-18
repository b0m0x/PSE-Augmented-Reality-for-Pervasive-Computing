package vision.view;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * renders the user interface
 */
public class GuiAppState extends AbstractAppState {

	ScreenController controller;
	private Nifty nifty;

	Screen screen = new ScreenBuilder("start") {
		{
			layer(new LayerBuilder("layer") {
				{
					backgroundColor("#003f");
					childLayoutCenter();
					panel(new PanelBuilder() {
						{
							width("50%");
							height("50%");
							backgroundImage("");
							imageMode("repeat:0,0,150,150");
							backgroundColor("#0f08");
						}
					});
				}
			});
			// content of the screen
		}
	}.build(nifty);

	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		// TODO Auto-generated method stub
		super.initialize(stateManager, app);
	}
}
