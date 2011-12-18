package vision.model;

import java.util.Collection;

/**
 * contains all static models, building architecture.
 *
 */
public class Groundplan {

		
		/**
		 */
		public void load(){
		}

		/** 
		 * @uml.property name="wall"
		 * @uml.associationEnd multiplicity="(0 -1)" inverse="groundplan:vision.model.Wall"
		 */
		private Collection<Wall> wall;

		/** 
		 * Getter of the property <tt>wall</tt>
		 * @return  Returns the wall.
		 * @uml.property  name="wall"
		 */
		public Collection<Wall> getWall() {
			return wall;
		}

		/** 
		 * Setter of the property <tt>wall</tt>
		 * @param wall  The wall to set.
		 * @uml.property  name="wall"
		 */
		public void setWall(Collection<Wall> wall) {
			this.wall = wall;
		}

}

