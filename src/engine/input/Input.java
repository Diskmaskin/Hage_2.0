package engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import engine.Container;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private Container container;
	
	private final int TOTAL_KEYS = 256;
	private boolean[] keys = new boolean[TOTAL_KEYS];
	private boolean[] keysLast = new boolean[TOTAL_KEYS];
	
	private final int TOTAL_MOUSE_BUTTONS = 5;
	private boolean [] mouseButtons = new boolean[TOTAL_MOUSE_BUTTONS];
	private boolean[] mouseButtonsLast = new boolean[TOTAL_MOUSE_BUTTONS];
	
	private int mouseX, mouseY;
	private int scroll;
	
	public Input(Container container) {
		this.container = container;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		container.getWindow().getCanvas().addKeyListener(this);
		container.getWindow().getCanvas().addMouseListener(this);
		container.getWindow().getCanvas().addMouseMotionListener(this);
		container.getWindow().getCanvas().addMouseWheelListener(this);
	}
	
	public void update() {
		
		scroll = 0;
		
		for (int i = 0; i < TOTAL_KEYS; i++) {
			keysLast[i] = keys[i];
		}
		for (int i = 0; i < TOTAL_MOUSE_BUTTONS; i++) {
			mouseButtonsLast[i] = mouseButtons[i];
		}
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyUp(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isMouseButton(int buttonCode) {
		return mouseButtons[buttonCode];
	}
	
	public boolean isMouseButtonUp(int buttonCode) {
		return !mouseButtons[buttonCode] && mouseButtonsLast[buttonCode];
	}
	
	public boolean isMouseButtonDown(int buttonCode) {
		return mouseButtons[buttonCode] && !mouseButtonsLast[buttonCode];
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scroll = e.getWheelRotation(); 
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int) (e.getX() / container.getScale());
		mouseY= (int) (e.getY() / container.getScale());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int) (e.getX() / container.getScale());
		mouseY= (int) (e.getY() / container.getScale());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseButtons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseButtons[e.getButton()] = false;

	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public int getScroll() {
		return scroll;
	}

}
