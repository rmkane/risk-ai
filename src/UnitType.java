import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum UnitType {
	INFANTRY(0, 1, "Infantry"),
	CAVALRY(1, 5, "Cavalry"),
	ARTILLERY(2, 10, "Artillery"),
	WILD(3, 0, "Wild");

	private final int id;
	private final int armies;
	private final String name;
	private BufferedImage image = null;

	UnitType(int id, int armies, String name) {
		this.id = id;
		this.armies = armies;
		this.name = name;
		try {
			image = ImageIO.read(new File("img" + armies));
		} catch (IOException ex) {
		}
	}
	
	public int id() {
		return this.id;
	}

	public int armies() {
		return this.armies;
	}
	
	public String getName() {
		return name;
	}

	public BufferedImage image() {
		return this.image;
	}
}