package vision;

import java.util.List;
import com.jme3.app.Application;


public class HeizungsPlugin extends Plugin {

	/**
	 * @uml.property  name="Heizungen"
	 */
	private List<Geometry> heizungen;

	/**
	 * Getter of the property <tt>Heizungen</tt>
	 * @return  Returns the heizungen.
	 * @uml.property  name="Heizungen"
	 */
	public List<Geometry> getHeizungen() {
		return heizungen;
	}

	/**
	 * Setter of the property <tt>Heizungen</tt>
	 * @param Heizungen  The heizungen to set.
	 * @uml.property  name="Heizungen"
	 */
	public void setHeizungen(List<Geometry> heizungen) {
		this.heizungen = heizungen;
	}

		
		/**
		 */
		protected void clientUpdate(Object application){
			return;
		}

			
			/**
			 */
			public HeizungsPlugin(){
			}

}
