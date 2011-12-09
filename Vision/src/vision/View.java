/**
 * 
 */
package vision;

import com.jme3.app.SimpleApplication;

/** 
 * @author Samurai
 */
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

}
