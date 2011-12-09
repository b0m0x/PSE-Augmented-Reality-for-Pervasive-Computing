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

}
