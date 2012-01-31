package vision.model;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;

public class MaterialHelper {
	
	Material wallMaterial;
	static MaterialHelper instance;
	
	private MaterialHelper() {
		
	}
	
	public static MaterialHelper getInstance() {
		if (instance == null) {
			instance = new MaterialHelper();
		}
		return instance;
	}
	
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
		m.setFloat("Shininess", 3);
		wallMaterial = m;
	}
	
	public Material getWallMaterial(AssetManager am) {
		if (wallMaterial == null) {
			createWallMaterial(am);
		}
		return wallMaterial;
	}
}
