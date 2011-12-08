package vision;

import java.util.List;

public abstract class Plugin {
	/** 
	 * @uml.property name="tags"
	 */
	private List<String> tags;
	/**
	 * @uml.property  name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * @return  Returns the id.
	 * @uml.property  name="id"
	 */
	public int getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * @param id  The id to set.
	 * @uml.property  name="id"
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** 
	 * @uml.property  name="tags"
	 */
	public List<String> getTags() {
		return tags;
	}

	/** 
	 * @uml.property  name="tags"
	 */
	public void setTags(String tags) {
		this.tags.add(tags);
	}
}
