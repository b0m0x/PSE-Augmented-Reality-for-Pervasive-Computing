/**
 * 
 */
package vision;

/** 
 * @author Samurai
 */
public class View {

		
		/**
		 */
		public void simpleUpdate(){
		}

			
			/**
			 */
			public void simpleInitApp(){
			}


			/**
			 * @uml.property  name="daten"
			 * @uml.associationEnd  inverse="view:vision.Daten"
			 */
			private Daten daten;


			/**
			 * Getter of the property <tt>daten</tt>
			 * @return  Returns the daten.
			 * @uml.property  name="daten"
			 */
			public Daten getDaten() {
				return daten;
			}


			/**
			 * Setter of the property <tt>daten</tt>
			 * @param daten  The daten to set.
			 * @uml.property  name="daten"
			 */
			public void setDaten(Daten daten) {
				this.daten = daten;
			}

}
