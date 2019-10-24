package hage.assets.pieces;

import java.util.ArrayList;

import com.sun.tools.javac.launcher.Main;

import engine.graphics.gfx.Image;

public class Piece {

	protected String path;
	protected Image img;
	protected int pieceNr;
	protected int team;
	protected long id;
	protected int xPos;
	protected int yPos;

	protected String name;
	protected int health;
	protected int maxHealth;
	protected int attack;

	protected boolean moveable = true;

	protected ArrayList<int[]> movementHighlight = null;

	public Piece(int pieceNr, long id, int xPos, int yPos) {
		initPiece(pieceNr);
		this.pieceNr = pieceNr;
		this.id = id;
		this.xPos = xPos;
		this.yPos = yPos;
		img = new Image(path);
	}

	

	private void initPiece(int pieceNr) {
		if (pieceNr == 1) {
			this.path = "/pieces/bonde_dark.png";
			team = 0;
			name = "Bonde";
			health = 10;
			attack = 3;
		} else if (pieceNr == 2) {
			this.path = "/pieces/springare_dark.png";
			team = 0;
			name = "Springare";
			health = 10;
			attack = 7;
		} else if (pieceNr == 3) {
			this.path = "/pieces/lopare_dark.png";
			team = 0;
			name = "Lopare";
			health = 10;
			attack = 5;
		} else if (pieceNr == 4) {
			this.path = "/pieces/drottning_dark.png";
			team = 0;
			name = "Drottning";
			health = 15;
			attack = 10;
		} else if (pieceNr == 5) {
			this.path = "/pieces/kung_dark.png";
			team = 0;
			name = "Kung";
			health = 15;
			attack = 0;
		} else if (pieceNr == 6) {
			this.path = "/pieces/torn_dark.png";
			team = 0;
			name = "Torn";
			health = 20;
			attack = 3;
			moveable = false;
		} else if (pieceNr == 7) {
			this.path = "/pieces/bonde_light.png";
			team = 1;
			name = "Bonde";
			health = 10;
			attack = 3;
		} else if (pieceNr == 8) {
			this.path = "/pieces/springare_light.png";
			team = 1;
			name = "Springare";
			health = 10;
			attack = 7;
		} else if (pieceNr == 9) {
			this.path = "/pieces/lopare_light.png";
			team = 1;
			name = "Lopare";
			health = 10;
			attack = 5;
		} else if (pieceNr == 10) {
			this.path = "/pieces/drottning_light.png";
			team = 1;
			name = "Drottning";
			health = 15;
			attack = 10;
		} else if (pieceNr == 11) {
			this.path = "/pieces/kung_light.png";
			team = 1;
			name = "Kung";
			health = 15;
			attack = 0;
		} else if (pieceNr == 12) {
			this.path = "/pieces/torn_light.png";
			team = 1;
			team = 1;
			name = "Torn";
			health = 20;
			attack = 3;
			moveable = false;
		}

		maxHealth = health;
	}

	private boolean isPosAvailable(int x, int y) {
		if (x < img.getWidth() || y < img.getHeight() || x > 8 * img.getWidth() || y > 8 * img.getHeight()) {
			return false;
		} else {
			return true;
		}
	}

	public void setPosition(int newX, int newY) {
		setXPos(newX);
		setYPos(newY);
	}

	public int getXPos() {
		return xPos;
	}

	private void setXPos(int xPos) {
		this.xPos = xPos;
	}

	public int getYPos() {
		return yPos;
	}

	private void setYPos(int yPos) {
		this.yPos = yPos;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int damage) {
		this.attack = damage;
	}

	public ArrayList<int[]> getMovementHighlight() {
		return movementHighlight;
	}

	public void addToMovementHighlight(int[] movementHighlight) {
		this.movementHighlight.add(movementHighlight);
	}

	public void clearMovementHighlight() {
		movementHighlight = null;
	}

	public String getPath() {
		return path;
	}

	public Image getImg() {
		return img;
	}

	public int getPieceNr() {
		return pieceNr;
	}

	public int getTeam() {
		return team;
	}

	public long getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isMoveable() {
		return moveable;
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}



	public int getMaxHealth() {
		return maxHealth;
	}



	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}


}
