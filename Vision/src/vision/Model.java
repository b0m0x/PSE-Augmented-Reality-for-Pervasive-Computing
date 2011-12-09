package vision;

import java.util.List;
import java.util.Collection;


public class Model {

		
		/**
		 */
		public void ladePlugin(){
		}

			
			/**
			 */
			public void get(){
			}


				
				/**
				 */
				public void set(){
				}


					
					/**
					 */
					public void getSensordata(String id, int zeitpunkt){
					}


						
						/**
						 */
						public void getTaggedSensors(List<String> tags){
						}



						/**
						 * @uml.property  name="view"
						 * @uml.associationEnd  inverse="daten:vision.View"
						 */
						private View view;



						/**
						 * Getter of the property <tt>view</tt>
						 * @return  Returns the view.
						 * @uml.property  name="view"
						 */
						public View getView() {
							return view;
						}


						/**
						 * Setter of the property <tt>view</tt>
						 * @param view  The view to set.
						 * @uml.property  name="view"
						 */
						public void setView(View view) {
							this.view = view;
						}



						/** 
						 * @uml.property name="sensor"
						 * @uml.associationEnd multiplicity="(0 -1)" inverse="daten:vision.Sensor"
						 */
						private Collection<Sensor> sensor;



						/** 
						 * Getter of the property <tt>sensor</tt>
						 * @return  Returns the sensor.
						 * @uml.property  name="sensor"
						 */
						public Collection<Sensor> getSensor() {
							return sensor;
						}


						/**
						 * @uml.property   name="datenbank"
						 * @uml.associationEnd   inverse="daten:vision.Database"
						 */
						private Database datenbank;



						/**
						 * Getter of the property <tt>datenbank</tt>
						 * @return  Returns the datenbank.
						 * @uml.property  name="datenbank"
						 */
						public Database getDatenbank() {
							return datenbank;
						}


						/**
						 * Setter of the property <tt>datenbank</tt>
						 * @param datenbank  The datenbank to set.
						 * @uml.property  name="datenbank"
						 */
						public void setDatenbank(Database datenbank) {
							this.datenbank = datenbank;
						}



						/**
						 * @uml.property  name="update"
						 * @uml.associationEnd  inverse="daten:vision.Update"
						 */
						private Update update;



						/**
						 * Getter of the property <tt>update</tt>
						 * @return  Returns the update.
						 * @uml.property  name="update"
						 */
						public Update getUpdate() {
							return update;
						}


						/**
						 * Setter of the property <tt>update</tt>
						 * @param update  The update to set.
						 * @uml.property  name="update"
						 */
						public void setUpdate(Update update) {
							this.update = update;
						}


						/** 
						 * Setter of the property <tt>sensor</tt>
						 * @param sensor  The sensor to set.
						 * @uml.property  name="sensor"
						 */
						public void setSensor(Collection<Sensor> sensor) {
							this.sensor = sensor;
						}

}
