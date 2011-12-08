package vision;

import java.util.List;


public class Daten {

		
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
						 * @uml.property  name="plugin"
						 * @uml.associationEnd  inverse="daten:vision.Plugin"
						 */
						private Plugin plugin;



						/**
						 * Getter of the property <tt>plugin</tt>
						 * @return  Returns the plugin.
						 * @uml.property  name="plugin"
						 */
						public Plugin getPlugin() {
							return plugin;
						}


						/**
						 * Setter of the property <tt>plugin</tt>
						 * @param plugin  The plugin to set.
						 * @uml.property  name="plugin"
						 */
						public void setPlugin(Plugin plugin) {
							this.plugin = plugin;
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
						 * @uml.property  name="sensor"
						 * @uml.associationEnd  inverse="daten:vision.Sensor"
						 */
						private Sensor sensor;



						/**
						 * Getter of the property <tt>sensor</tt>
						 * @return  Returns the sensor.
						 * @uml.property  name="sensor"
						 */
						public Sensor getSensor() {
							return sensor;
						}


						/**
						 * Setter of the property <tt>sensor</tt>
						 * @param sensor  The sensor to set.
						 * @uml.property  name="sensor"
						 */
						public void setSensor(Sensor sensor) {
							this.sensor = sensor;
						}



						/**
						 * @uml.property  name="datenbank"
						 * @uml.associationEnd  inverse="daten:vision.Datenbank"
						 */
						private Datenbank datenbank;



						/**
						 * Getter of the property <tt>datenbank</tt>
						 * @return  Returns the datenbank.
						 * @uml.property  name="datenbank"
						 */
						public Datenbank getDatenbank() {
							return datenbank;
						}


						/**
						 * Setter of the property <tt>datenbank</tt>
						 * @param datenbank  The datenbank to set.
						 * @uml.property  name="datenbank"
						 */
						public void setDatenbank(Datenbank datenbank) {
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

}
