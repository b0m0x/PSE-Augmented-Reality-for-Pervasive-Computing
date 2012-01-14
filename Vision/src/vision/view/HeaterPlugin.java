package vision.view;

import java.util.List;

import vision.controller.HeaterController;
import vision.model.Model;

import com.jme3.app.Application;
import com.jme3.scene.Geometry;


/**
 * @author idle
 *	This class represents the plugins of the heater
 */
public class HeaterPlugin extends Plugin {

	/**
	 * updates the client 
		 */
		protected void clientUpdate(Application application){
			return;
		}

			
			/**
			 */
			public HeaterPlugin(Model model){
				super(model);
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
