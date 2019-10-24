package engine.graphics.gfx;

public class Font {
	
	private Image fontImage;
	private int[] offsets;
	private int[] widths;
	
	public static final Font STANDARD = new Font("/fonts/PixFont_7pt_NOALPHA.png");
	
	private int startC = 0xff0000ff;
	private int endC = 0xffffff00;
	
	public Font(String path) {
		fontImage = new Image(path);
		
		offsets = new int[59];
		widths = new int[59];
		
		int unicode = 0;
		
		for (int i = 0; i < fontImage.getWidth(); i++) {
			if (fontImage.getP()[i] == startC) {
				offsets[unicode] = i;
			}
			if (fontImage.getP()[i] == endC) {
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}

	public Image getFontImage() {
		return fontImage;
	}

	public int[] getOffsets() {
		return offsets;
	}

	public int[] getWidths() {
		return widths;
	}
}
