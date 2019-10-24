package engine.graphics;

import java.awt.image.DataBufferInt;

import engine.Container;
import engine.graphics.gfx.Font;
import engine.graphics.gfx.Image;
import engine.graphics.gfx.ImageTile;

public class Renderer {
	
	private int pWidth, pHeight;
	private int[] p;
	
	private Font font;
	private int spacing = 1;
	
	public Renderer(Container container) {
		pWidth = container.getWidth();
		pHeight = container.getHeight();
		p = ((DataBufferInt) container.getWindow().getImage().getRaster().getDataBuffer()).getData();
		
		font = Font.PIXFONT7;
	}
	
	public void clear() {
		for (int i = 0; i < p.length; i++) {
			p[i] = 0xff000000;
		}
	}
	
	public void setPixel(int x, int y, int value) {
		//Check if pixel in image is out of bounds (Deprecated: or is skipcolor (skipcolor = 0xffff00ff))
		if ((x < 0 || x >= pWidth || y < 0 || y >= pHeight) || ((value >> 24) & 0xff) == 0) return;
		//Set pixel value
		p[x + y * pWidth] = value;
	}
	
	public void drawText(String text, int offX, int offY) {
		text = text.toUpperCase();
		int offset = 0;
		for (char c : text.toCharArray()) {
			if (offset == 0) {
//				drawColorPickedImage(font.getCharImage(c), offX, offY, color);
				drawImage(font.getCharImage(c), offX, offY);
				offset += font.getCharImage(c).getWidth() + spacing;
			} else {
//				drawColorPickedImage(font.getCharImage(c), offX + lastImage.getWidth() + spacing, offY, color);
				drawImage(font.getCharImage(c), offX + offset, offY);
				offset += font.getCharImage(c).getWidth() + spacing;
			}
			
		}
	}
	
//	Font_old draw method
//	public void drawText(String text, int offX, int offY, int color) {
//		text = text.toUpperCase();
//		int offset = 0;
//		for (int i = 0; i < text.length(); i++) {
//			int unicode = text.codePointAt(i) - 32;
//			for (int y = 0; y < font.getFontImage().getHeight(); y++) {
//				for (int x = 0; x < font.getWidths()[unicode]; x++) {
//					if (font.getFontImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xff000000) {
//						setPixel(x + offX + offset, y + offY, color);
//					}
//				}
//			}
//		}
//	}
	
	public void drawImage(Image image, int offX, int offY) {
		
		if (offX < -image.getWidth()) return;
		if (offY < -image.getHeight()) return;
		if (offX >= pWidth) return;
		if (offY >= pHeight) return;
		
		int tmpX = 0;
		int tmpY = 0;
		int tmpWidth = image.getWidth();
		int tmpHeight = image.getHeight();
		
		//Check if off screen (don't render if it is)

		if (tmpWidth + offX > pWidth) tmpWidth -= tmpWidth + offX - pWidth;
		if (tmpHeight + offY > pHeight) tmpHeight -= tmpHeight + offY - pHeight;
		if (tmpX + offX < 0) tmpX -= offX;
		if (tmpY + offY < 0) tmpY -= offY;
		
		for (int y = tmpY; y < tmpHeight; y++) {
			for (int x = tmpX; x < tmpWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[x + y * image.getWidth()]);
			}
		}
	}
	
	public void drawColorPickedImage(Image image, int offX, int offY, int color) {

		if (offX < -image.getWidth()) return;
		if (offY < -image.getHeight()) return;
		if (offX >= pWidth) return;
		if (offY >= pHeight) return;
		
		int tmpX = 0;
		int tmpY = 0;
		int tmpWidth = image.getWidth();
		int tmpHeight = image.getHeight();
		
		//Check if off screen (don't render if it is)

		if (tmpWidth + offX > pWidth) tmpWidth -= tmpWidth + offX - pWidth;
		if (tmpHeight + offY > pHeight) tmpHeight -= tmpHeight + offY - pHeight;
		if (tmpX + offX < 0) tmpX -= offX;
		if (tmpY + offY < 0) tmpY -= offY;
		
		for (int y = tmpY; y < tmpHeight; y++) {
			for (int x = tmpX; x < tmpWidth; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}
	
	public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY) {
//		Check if off screen (don't render if it is)
		if (offX < -image.getTileWidth()) return;
		if (offY < -image.getTileHeight()) return;
		if (offX >= pWidth) return;
		if (offY >= pHeight) return;
		
		int tmpX = 0;
		int tmpY = 0;
		int tmpWidth = image.getTileWidth();
		int tmpHeight = image.getTileHeight();
		
//		Clipping: Check if off screen (don't render if it is)
		if (tmpWidth + offX > pWidth) tmpWidth -= tmpWidth + offX - pWidth;
		if (tmpHeight + offY > pHeight) tmpHeight -= tmpHeight + offY - pHeight;
		if (tmpX + offX < 0) tmpX -= offX;
		if (tmpY + offY < 0) tmpY -= offY;
		
		for (int y = tmpY; y < tmpHeight; y++) {
			for (int x = tmpX; x < tmpWidth; x++) {
				setPixel(x + offX, y + offY, image.getP()[(x + tileX * image.getTileWidth()) + (y + tileY * image.getTileHeight()) * image.getWidth()]);
			}
		}	
	}
	
	public void drawRect(int offX, int offY, int width, int height, int color) {
		for (int y = 0; y <= height; y++) {
			setPixel(offX, y + offY, color);
			setPixel(offX + width, y + offY, color);
		}
		for (int x = 0; x <= width; x++) {
			setPixel(x + offX, offY, color);
			setPixel(x + offX, offY + height, color);
		}
	}
	
	public void fillRect(int offX, int offY, int width, int height, int color) {
//		Check if off screen (don't render if it is)
		if (offX < -width) return;
		if (offY < -height) return;
		if (offX >= pWidth) return;
		if (offY >= pHeight) return;
		
		int tmpX = 0;
		int tmpY = 0;
		int tmpWidth = width;
		int tmpHeight = height;
		
//		Clipping: Check if off screen (don't render if it is)
		if (tmpWidth + offX > pWidth) tmpWidth -= tmpWidth + offX - pWidth;
		if (tmpHeight + offY > pHeight) tmpHeight -= tmpHeight + offY - pHeight;
		if (tmpX + offX < 0) tmpX -= offX;
		if (tmpY + offY < 0) tmpY -= offY;
		
		for (int y = tmpY; y <= tmpHeight; y++) {
			for (int x = tmpX; x <= tmpWidth; x++) {
				setPixel(x + offX, y + offY, color);
			}
		}
	}
	
	//Not Working
	public void drawLine(int x0, int y0, int x1, int y1, int color) {
		for (int y = 0; y <= y1 - y0; y++) {
			for (int x = 0; x <= x1 - x0; x++) {
				setPixel(x / y + x0, y / x + y0, color);
			}
		}
		
		setPixel(x0, y0, 0xffff00ff);
		setPixel(x1, y1, 0xffff00ff);
	}
	
}
