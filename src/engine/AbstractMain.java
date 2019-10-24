package engine;

import engine.graphics.Renderer;

public abstract class AbstractMain {
	
	public abstract void update(Container container, float deltaTime);
	public abstract void render(Container container, Renderer renderer);
	
}
