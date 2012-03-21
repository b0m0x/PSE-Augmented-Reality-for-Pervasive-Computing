package vision.controller.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import vision.controller.Controller;
import vision.model.Model;
import vision.view.HeaterPlugin;
import vision.view.Plugin;
import vision.view.View;
import de.lessvoid.nifty.controls.CheckBoxStateChangedEvent;

public class ControllerTest extends TestCase {

	private class MockModel extends Model {

		@Override
		public List<Plugin> getPluginList() {
			List<Plugin> r = new ArrayList<Plugin>();
			r.add(new HeaterPlugin(null, null));
			return r;
		}

	}

	private class MockView extends View {
		boolean[] called = new boolean[9];



		@Override
		public void userPick() {
			called[0] = true;
			called[1] = true;
		}

		@Override
		public void toggleMouse() {
			called[2] = true;
		}

		@Override
		public void toggleOverviewCam() {
			called[3] = true;
		}

		@Override
		public void userMoveAction(String name, boolean keyPressed) {
			called[4] = true;
		}

		@Override
		public void enablePlugin(Plugin p) {
			called[5] = true;
		}

		@Override
		public void disablePlugin(Plugin p) {
			called[6] = true;
		}

		@Override
		public void enablePostProcessingEffects(boolean enable) {
			called[7] = true;
		}

		@Override
		public void setDisplayStatView(boolean show) {
			called[8] = true;
		}

		public boolean allStubsCalled() {
			for (boolean b : called) {
				if (!b)
					return false;
			}
			return true;
		}

	}

	@Test
	public void testController() {

		MockView view = new MockView();
		MockModel model = new MockModel();
		model = new MockModel();

		Controller controller = new Controller(view, model);

		controller.onAction("toggleMouse", false, 20);
		controller.onAction("zoom", true, 20);
		controller.onAction("Left", false, 20);
		controller.onAnalog("userPick", 20, 20);
		controller.plugincheckboxPressed(
				"Pluginchecbox_vision.view.HeaterPlugin",
				new CheckBoxStateChangedEvent(null, false));
		controller.plugincheckboxPressed(
				"Pluginchecbox_vision.view.HeaterPlugin",
				new CheckBoxStateChangedEvent(null, true));
		controller.toggleOverview(null, null);
		controller.showEffects(null, new CheckBoxStateChangedEvent(null, true));
		assertFalse(view.allStubsCalled());
		controller.showDebugInformation("", new CheckBoxStateChangedEvent(null, false));

		assertTrue(view.allStubsCalled());
	}

}
