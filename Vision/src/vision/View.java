/**
 * 
 */
package vision;

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
			 * @uml.associationEnd   inverse="view:vision.Model"
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
			 * @uml.property  name="controller"
			 * @uml.associationEnd  multiplicity="(1 1)" inverse="view:vision.Controller"
			 */
			private Controller controller = new vision.Controller();


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

}
