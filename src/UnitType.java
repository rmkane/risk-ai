import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public enum UnitType {
	INFANTRY(1, "Infantry"),
	CAVALRY(5, "Cavalry"),
	ARTILLERY(10, "Artillery"),
	WILD(0, "Wild");

	private final int armies;
	private final String name;
	private BufferedImage image = null;

	UnitType(int armies, String name) {
		this.armies = armies;
		this.name = name;
		try {
			image = ImageIO.read(new File("img" + armies));
		} catch (IOException ex) {
		}
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