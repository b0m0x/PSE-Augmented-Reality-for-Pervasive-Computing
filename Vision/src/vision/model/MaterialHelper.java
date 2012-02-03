package vision.model;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class MaterialHelper {
	
	private Material wallMaterial;
	private Material heaterMaterial;
	private Material floorMaterial;
	
	static MaterialHelper instance;
	
	private MaterialHelper() {
		
	}
	
	/**
	 * gets the instance 
	 * @return returns the instance
	 */
	public static MaterialHelper getInstance() {
		if (instance == null) {
			instance = new MaterialHelper();
		}
		return instance;
	}
	/**
	 * creates the material of the wall.
	 * @param am
	 */
	private void createWallMaterial(AssetManager am) {
		Material m = new Material(am,
				"Common/MatDefs/Light/Lighting.j3md");
		m.setBoolean("UseMaterialColors", true);
		m.setColor("Ambient", ColorRGBA.Gray);
		m.setColor("Diffuse", ColorRGBA.Gray);
		m.setColor("Specular", ColorRGBA.White);

		Texture tex = am.loadTexture(
				"Texture/walltexture.jpg");
		tex.setWrap(WrapMode.Repeat);
		m.setTexture("DiffuseMap", tex);
		m.setFloat("Shininess", 1);
		wallMaterial = m;
	}
	
	/**
	 * gets the material of the wall.
	 * @param am
	 * @return returns the wallmaterial
	 */
	public Material getWallMaterial(AssetManager am) {
		if (wallMaterial == null) {
			createWallMaterial(am);
		}
		return wallMaterial;
	}
 
	private void createFloorMaterial(AssetManager am) {
		Material m = new Material(am, "Common/MatDefs/Light/Lighting.j3md");
		m.setBoolean("UseMaterialColors", true);
		m.setColor("Ambient", ColorRGBA.Gray);
		m.setColor("Diffuse", ColorRGBA.Gray);
		m.setColor("Specular", ColorRGBA.White);
		
		Texture tex = am.loadTexture("Texture/carpet-copy.png");
		tex.setWrap(WrapMode.Repeat);
		m.setTexture("DiffuseMap", tex);
		m.setFloat("Shininess", 3);
		floorMaterial = m;
	}
	/**
	 * gets the material of the floor.
	 * @param am
	 * @return floormaterial
	 */
	public Material getFloorMaterial(AssetManager am) {
		if (floorMaterial == null) {
			createFloorMaterial(am);
		}
		return floorMaterial;
	}
	/**
	 * gets the coloredMaterial.
	 * @param am
	 * @param temperature
	 * @return
	 */
	public Material getColoredMaterial(AssetManager am, float temperature) {
		if (heaterMaterial == null) {
			createColoredMaterial(am);
		}
		ColorRGBA col = new ColorRGBA(temperature / 50f, 0, 1f - temperature / 50f, 1f);
		Material m = heaterMaterial.clone();
		//m.setColor("Diffuse", col);
		m.setColor("Ambient", col);
		return m;
	}

	private void createColoredMaterial(AssetManager am) {
		heaterMaterial = new Material(am, "Common/MatDefs/Light/Lighting.j3md");
		heaterMaterial.setBoolean("UseMaterialColors", true);
		heaterMaterial.setColor("Ambient",  ColorRGBA.Gray);
		heaterMaterial.setColor("Diffuse",  ColorRGBA.Gray);
		heaterMaterial.setColor("Specular", ColorRGBA.White);
		heaterMaterial.setFloat("Shininess", 3);
		
	}
}
