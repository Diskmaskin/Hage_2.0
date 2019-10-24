package hage;

import java.util.Map;

import engine.AbstractMain;
import engine.Container;
import engine.graphics.Renderer;
import engine.graphics.gfx.Image;
import engine.graphics.gfx.ImageTile;
import hage.assets.pieces.Piece;
import hage.assets.pieces.PieceManager;

public class Main extends AbstractMain {
	
	private PieceManager pMan;
	
	private Image boardImg;
	
	//Animation
	private boolean animation = false;
	private int maxAnimationTick = 5;
	private int animationSpeed = 5;
	private int animationTick = 0;
	private int reversableAnimationTick = 0;
	private boolean reverseAnimation = false;
	
	// 0: Highlight was reset   1: Something was highlighted    2: Something attacked
	private int action;
	
	// Turn
	private int playerTurn = 1;
	
	
	public Main() {
		pMan = new PieceManager();
		boardImg = new Image("/board_3.png");
		pMan.addPiece(1, 1*24, 1*24);
		pMan.addPiece(2, 2*24, 1*24);
		pMan.addPiece(3, 3*24, 1*24);
		pMan.addPiece(4, 4*24, 1*24);
		pMan.addPiece(5, 5*24, 1*24);
		pMan.addPiece(6, 6*24, 1*24);
		
		pMan.addPiece(7, 1*24, 8*24);
		pMan.addPiece(8, 2*24, 8*24);
		pMan.addPiece(9, 3*24, 8*24);
		pMan.addPiece(10, 4*24, 8*24);
		pMan.addPiece(11, 5*24, 8*24);
		pMan.addPiece(12, 6*24, 8*24);
	}
	
	@Override
	public void update(Container container, float deltaTime) {
		if (container.getInput().isMouseButtonDown(1)) {
			action = pMan.clicked(container.getInput().getMouseX(), container.getInput().getMouseY());
			if (action == 2) {
				animationTick = 0;
				animation = true;
			}
		}
		if (animation) animationTicker();
	}

	@Override
	public void render(Container container, Renderer renderer) {
		renderer.drawImage(boardImg, 0, 0);
		for (Map.Entry<Long, Piece> entry : pMan.getPieces().entrySet()) {
			renderer.drawImage(entry.getValue().getImg(), entry.getValue().getXPos(), entry.getValue().getYPos());
		}
		renderer.drawText(pMan.getStatusMsgTop(), 36, 220);
		renderer.drawText(pMan.getStatusMsgBot(), 36, 228);
		highlightPiece(renderer);
		if (action == 2 && animation) {
			attackAnimation(renderer, pMan.getLastTargetedPiece().getXPos(), pMan.getLastTargetedPiece().getYPos());
		}
		renderer.drawText("FPS: " + Integer.toString(container.getFps()), 0, 0);
	}
	
	public static void main(String[] args) {
		Container container = new Container(new Main());
		container.start();
	}
	
	private void highlightPiece(Renderer renderer) {
		if (pMan.isPieceHighlighted()) {
			Piece p = pMan.getPieces().get(pMan.getHighlightedPiece());
			//Draw info
			renderer.drawText(p.getName() + "  ID: " + p.getID(), 223, 27);
			String teamString = "";
			switch (p.getTeam()) {
				case 0: teamString = "Dark"; break;
				case 1: teamString = "Light"; break;
			}
			renderer.drawText(teamString + " team", 223, 37);
			renderer.drawText("Health: " + p.getHealth() + " / " + p.getMaxHealth(), 223, 45);
			renderer.drawText("Damage: " + p.getAttack(), 223, 53);
			char xPosAlpha = ' ';
			switch (p.getXPos() / 24) {
				case 1: xPosAlpha = 'A'; break;
				case 2: xPosAlpha = 'B'; break;
				case 3: xPosAlpha = 'C'; break;
				case 4: xPosAlpha = 'D'; break;
				case 5: xPosAlpha = 'E'; break;
				case 6: xPosAlpha = 'F'; break;
				case 7: xPosAlpha = 'G'; break;
				case 8: xPosAlpha = 'H'; break;
			}
			renderer.drawText("X: " + p.getXPos() + ", " + xPosAlpha, 223, 61);
			renderer.drawText("Y: " + p.getYPos() + ", " + p.getYPos() / 24, 223, 69);
			//Draw highlights
			if (p.getMovementHighlight() != null) {
				for (int[] j : p.getMovementHighlight()) {
					if (j != null) {
						int color = 0xff42f465;
						if (pMan.getByPosition(j[0], j[1]) != null) {
							if (pMan.getByPosition(j[0], j[1]).getTeam() == p.getTeam()) continue;
							color = 0xffe81717;
						}
						renderer.drawRect(j[0], j[1], 23, 23, color);
					}
				}
			}
		}
	}
	
	private void attackAnimation(Renderer renderer, int x, int y) {
		try {
			renderer.drawImageTile(new ImageTile("/damage_animation.png", 24, 24), x, y, animationTick, 0);
		} catch (Exception e) {}
	}
	
	private void animationTicker() {
		if (animationTick < maxAnimationTick) {
			animationTick++;
		} else {
			animation = false;
			animationTick = 0;
		}
	}
	
	private void reversableAnimationTicker() {

	}
	

}
