package vision.model;


public class Update {

	/**
	 * @uml.property  name="url"
	 */
	private String url;

	/**
	 * Getter of the property <tt>url</tt>
	 * @return  Returns the url.
	 * @uml.property  name="url"
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Setter of the property <tt>url</tt>
	 * @param url  The url to set.
	 * @uml.property  name="url"
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @uml.property   name="daten"
	 * @uml.associationEnd   inverse="update:vision.model.Model"
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
