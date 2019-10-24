package engine;

import engine.graphics.Renderer;
import engine.graphics.Window;
import engine.input.Input;

public class Container implements Runnable {
	
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractMain main;
	
	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	private int width = 320, height = 240;
	private float scale = 4f;
	private String title = "";
	
	private int fps;
	
	public Container(AbstractMain main) {
		this.main = main;
	}
	
	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this, "Main");
		thread.run();
	}
	
	public void stop() {
		
	}
	
	public void run() {
		
		running = true;
		
		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = 0;
		double unprocessedTime = 0;
		
		double frameTime = 0;
		int frames = 0;
		fps = 0;
		
		while (running) {
			
			render = false;
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;

			
			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				
				//FPS
				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					System.out.println("FPS: " + fps);
				}
				//update logic
				main.update(this, (float) UPDATE_CAP);
				input.update();
			}
			
			if (render) {
				//render
				renderer.clear();
				main.render(this, renderer);
				window.update();
				frames++;
				} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					System.err.println("ERROR: Container: Could not put thread to sleep on lagging renderer");
					e.printStackTrace();
				}
			}
			dispose();
		}
	}
	
	private void dispose() {
//		window.dispose();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public int getFps() {
		return fps;
	}
	
}
