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
			 * Getter of the property <tt>heaters</tt>
			 * @return  Returns the heaters1.
			 * @uml.property  name="heaters"
			 */
			public List<Geometry> getHeaters() {
				return heaters;
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


			/**
			 * @uml.property  name="heaters"
			 */
			private List<Geometry> heaters;

			/** 
			 * Setter of the property <tt>Heizungen</tt>
			 * @param Heizungen  The heizungen to set.
			 * @uml.property  name="heaters"
			 */
			public void setHeaters(List<Geometry> heaters) {
				this.heaters = heaters;
			}

}
