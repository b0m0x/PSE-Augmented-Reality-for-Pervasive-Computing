package vision;

import java.util.List;
import com.jme3.app.Application;
import com.jme3.scene.Geometry;


public class HeaterPlugin extends Plugin {

	/**
		 */
		protected void clientUpdate(Application application){
			return;
		}

			
			/**
			 */
			public HeaterPlugin(){
			}


			/**
			 * @uml.property  name="heizungen"
			 */
			private List<Geometry> heizungen;

			/** 
			 * Setter of the property <tt>Heizungen</tt>
			 * @param Heizungen  The heizungen to set.
			 * @uml.property  name="heizungen"
			 */
			public void setHeizungen(List<Geometry> heizungen) {
				this.heizungen = heizungen;
			}

			
			/**
			 * Getter of the property <tt>Heizungen</tt>
			 * @return    Returns the heizungen.
			 * @uml.property    name="heizungen"
			 */
			public List<Geometry> getHeizungen() {
				return heizungen;
			}


			/**
			 * @uml.property  name="heaterController"
			 * @uml.associationEnd  multiplicity="(1 1)" inverse="heaterPlugin:vision.HeaterController"
			 */
			private HeaterController heaterController = new vision.HeaterController();

			/**
			 * Getter of the property <tt>heaterController</tt>
			 * @return  Returns the heaterController.
			 * @uml.property  name="heaterController"
			 */
			public HeaterController getHeaterController() {
				return heaterController;
			}


			/**
			 * Setter of the property <tt>heaterController</tt>
			 * @param heaterController  The heaterController to set.
			 * @uml.property  name="heaterController"
			 */
			public void setHeaterController(HeaterController heaterController) {
				this.heaterController = heaterController;
			}

}
