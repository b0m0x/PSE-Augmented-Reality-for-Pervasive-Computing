/**
 * 
 */
package vision.view;

import vision.controller.Controller;
import vision.model.Model;

import com.jme3.app.SimpleApplication;


public class View extends SimpleApplication {

		
		/**
		 */
		public void simpleUpdate(){
		}

			
			/**
			 */
			public void simpleInitApp(){
			}


			/**
			 * @uml.property   name="daten"
			 * @uml.associationEnd   inverse="view:vision.model.Model"
			 */
			private Model daten;


			/**
			 * Getter of the property <tt>daten</tt>
			 * @return  Returns the daten.
			 * @uml.property  name="daten"
			 */
			public Model getDaten() {
				return daten;
			}


			/**
			 * Setter of the property <tt>daten</tt>
			 * @param daten  The daten to set.
			 * @uml.property  name="daten"
			 */
			public void setDaten(Model daten) {
				this.daten = daten;
			}


			/**
			 * @uml.property   name="controller"
			 * @uml.associationEnd   multiplicity="(1 1)" inverse="view:vision.controller.Controller"
			 */
			private Controller controller = new vision.controller.Controller();


			/**
			 * Getter of the property <tt>controller</tt>
			 * @return  Returns the controller.
			 * @uml.property  name="controller"
			 */
			public Controller getController() {
				return controller;
			}


			/**
			 * Setter of the property <tt>controller</tt>
			 * @param controller  The controller to set.
			 * @uml.property  name="controller"
			 */
			public void setController(Controller controller) {
				this.controller = controller;
			}


			/**
			 * @uml.property  name="mainAppState"
			 * @uml.associationEnd  multiplicity="(1 1)" inverse="view:vision.view.MainAppState"
			 */
			private MainAppState mainAppState = new vision.view.MainAppState();


			/**
			 * Getter of the property <tt>mainAppState</tt>
			 * @return  Returns the mainAppState.
			 * @uml.property  name="mainAppState"
			 */
			public MainAppState getMainAppState() {
				return mainAppState;
			}


			/**
			 * Setter of the property <tt>mainAppState</tt>
			 * @param mainAppState  The mainAppState to set.
			 * @uml.property  name="mainAppState"
			 */
			public void setMainAppState(MainAppState mainAppState) {
				this.mainAppState = mainAppState;
			}


			/**
			 * @uml.property  name="guiAppState"
			 * @uml.associationEnd  multiplicity="(1 1)" inverse="view:vision.view.GuiAppState"
			 */
			private GuiAppState guiAppState = new vision.view.GuiAppState();


			/**
			 * Getter of the property <tt>guiAppState</tt>
			 * @return  Returns the guiAppState.
			 * @uml.property  name="guiAppState"
			 */
			public GuiAppState getGuiAppState() {
				return guiAppState;
			}


			/**
			 * Setter of the property <tt>guiAppState</tt>
			 * @param guiAppState  The guiAppState to set.
			 * @uml.property  name="guiAppState"
			 */
			public void setGuiAppState(GuiAppState guiAppState) {
				this.guiAppState = guiAppState;
			}

}
