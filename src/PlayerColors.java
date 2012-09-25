import java.awt.Color;

public enum PlayerColors {
	RED(0x8D261F),
	YELLOW(0xEFD159),
	GREEN(0x617369),
	BLUE(0x394C7B),
	GRAY(0xA5A5A3),
	BLACK(0x000000);

	private final Color color;

	PlayerColors(int hexVal) {
		this.color = new Color(hexVal);
	}

	public Color color() {
		return this.color;
	}
}