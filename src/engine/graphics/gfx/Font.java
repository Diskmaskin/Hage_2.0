package engine.graphics.gfx;

import java.util.HashMap;

public class Font {
	
	private HashMap<Character, Image> chars;
	private char[] supportedChars;
	
	public static final Font PIXFONT7 = new Font("PixFont_7pt");
	
	public Font(String font) {
		loadFont(font);
	}
	
	public void loadFont(String font) {
		chars = new HashMap<Character, Image>();
		if (font.equals("PixFont_7pt")) {
			supportedChars = new char[] {' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.',
										'/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', 
										'?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
										'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
			for (int i = 0; i < supportedChars.length; i++) {
				String path = null;
				if (supportedChars[i] == ' ') {
					path = "/fonts/" + font + "/space.png";
				} else if (supportedChars[i] == '/') {
					path = "/fonts/" + font + "/" + "forwardslash.png";
				} else if (supportedChars[i] == '.') {
					path ="/fonts/" + font + "/" + "dot.png";
				} else {
					path = "/fonts/" + font + "/" + supportedChars[i] + ".png";
				}
				try {
					chars.put(supportedChars[i], new Image(path));
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public HashMap<Character, Image> getChars() {
		return chars;
	}
	
	public Image getCharImage(char c) {
		return chars.get(c);
	}
	
}
