package hage.assets.pieces;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import static hage.assets.pieces.Piece.*;

public class PieceManager {
	
	private HashMap<Long, Piece> pieces;
	private HashMap<Dimension, Long> positionGetter;
	private boolean pieceHighlighted = false;
	private long highlightedPiece;
	
	private long lastID = 0;
	
	private String statusMsgTop = "";
	private String statusMsgBot = "";
	
	private Piece lastTargetedPiece = null;
	
	public PieceManager() {
		pieces = new HashMap<Long, Piece>();
		positionGetter = new HashMap<Dimension, Long>();
	}
	
	public void addPiece(int pieceNr, int xPos, int yPos) {
		long id = buildID();
		pieces.put(id, new Piece(pieceNr, id, xPos, yPos));
		positionGetter.put(new Dimension(xPos, yPos), id);
	}
	
	public void removePiece(long id) {
		positionGetter.remove(new Dimension(pieces.get(id).getXPos(), pieces.get(id).getYPos()));
		pieces.remove(id);
	}
	
	public void movePiece(Piece p, int newX, int newY) {
		if (p.isMoveable()) {
			positionGetter.remove(new Dimension(p.getXPos(), p.getYPos()));
			p.setPosition(newX, newY);
			positionGetter.put(new Dimension(newX, newY), p.getID());			
		}
	}
	
	public void attackPiece(Piece attacker, Piece reciever) {
		statusMsgBot = "";
		if (attacker.pieceNr == 3 || attacker.pieceNr == 9 || attacker.pieceNr == 4 || attacker.pieceNr == 10) {
			if (attacker.xPos < reciever.xPos && attacker.yPos < reciever.yPos) {
				movePiece(attacker, reciever.xPos - reciever.img.getWidth(), reciever.yPos - reciever.img.getHeight());
			} else if (attacker.xPos > reciever.xPos && attacker.yPos > reciever.yPos) {
				movePiece(attacker, reciever.xPos + reciever.img.getWidth(), reciever.yPos + reciever.img.getHeight());
			} else if (attacker.xPos < reciever.xPos && attacker.yPos > reciever.yPos) {
				movePiece(attacker, reciever.xPos - reciever.img.getWidth(), reciever.yPos + reciever.img.getHeight());
			} else if (attacker.xPos > reciever.xPos && attacker.yPos < reciever.yPos) {
				movePiece(attacker, reciever.xPos + reciever.img.getWidth(), reciever.yPos - reciever.img.getHeight());
			} else if (attacker.xPos > reciever.xPos && attacker.yPos == reciever.yPos) {
				movePiece(attacker, reciever.xPos + reciever.img.getWidth(), reciever.yPos);
			} else if (attacker.xPos < reciever.xPos && attacker.yPos == reciever.yPos) {
				movePiece(attacker, reciever.xPos - reciever.img.getWidth(), reciever.yPos);
			} else if (attacker.xPos == reciever.xPos && attacker.yPos > reciever.yPos) {
				movePiece(attacker, reciever.xPos, reciever.yPos + reciever.img.getHeight());
			} else if (attacker.xPos == reciever.xPos && attacker.yPos < reciever.yPos) {
				movePiece(attacker, reciever.xPos, reciever.yPos - reciever.img.getHeight());
			} 
		}
		reciever.health -= attacker.attack;
		statusMsgTop = attacker.name + " did " + attacker.attack + " damage";
		lastTargetedPiece = reciever;
		if (reciever.health <= 0) {
			statusMsgBot = reciever.name + " was defeated";
			removePiece(reciever.id);
		}
	}
	
	public int clicked(int x, int y) {
		while (x % 24 != 0) {
			x--;
		}
		while (y % 24 != 0) {
			y--;
		}
		if (getByPosition(x, y) != null && !pieceHighlighted && highlightedPiece == 0) {
			pieceHighlighted = true;
			highlightedPiece = getByPosition(x, y).getID();
			loadAvailiableTargets(getByPosition(x, y));
			return 1;
		} else if (pieceHighlighted) {
			for (int[] i : pieces.get(highlightedPiece).getMovementHighlight()) {
				if (i != null && x == i[0] && y == i[1]) {
					if (getByPosition(x, y) != null && getByPosition(x, y).getTeam() != pieces.get(highlightedPiece).getTeam()) {
						attackPiece(pieces.get(highlightedPiece), getByPosition(x, y));
						pieces.get(highlightedPiece).clearMovementHighlight();
						pieceHighlighted = false;
						highlightedPiece = 0;
						return 2;
					} else if (getByPosition(x, y) == null) {
						movePiece(pieces.get(highlightedPiece), x, y);
					}
				}
			}
			pieces.get(highlightedPiece).clearMovementHighlight();
			pieceHighlighted = false;
			highlightedPiece = 0;
			return 1;
		} else {
			try {
				pieces.get(highlightedPiece).clearMovementHighlight();				
			} catch (NullPointerException e) {
//				e.printStackTrace();
			}
			pieceHighlighted = false;
			highlightedPiece = 0;
			return 0;
		}
	}
	
	public void loadAvailiableTargets(Piece p) {
		p.movementHighlight = new ArrayList<int[]>();
		int x = 0;
		int y = 0;
		if (p.pieceNr == 1 || p.pieceNr == 7 || p.pieceNr == 5 || p.pieceNr == 11) {
			// BONDE & KUNG
			if (isPosAvailable(p.xPos - p.img.getWidth(), p.yPos))
				p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth(), p.yPos });
			if (isPosAvailable(p.xPos, p.yPos - p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos, p.yPos - p.img.getHeight() });
			if (isPosAvailable(p.xPos + p.img.getWidth(), p.yPos))
				p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth(), p.yPos });
			if (isPosAvailable(p.xPos, p.yPos + p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos, p.yPos + p.img.getHeight() });
		} else if (p.pieceNr == 2 || p.pieceNr == 8) {
			// SPRINGARE
			if (isPosAvailable(p.xPos - p.img.getWidth() * 2, p.yPos - p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth() * 2, p.yPos - p.img.getHeight() });
			if (isPosAvailable(p.xPos - p.img.getWidth() * 2, p.yPos + p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth() * 2, p.yPos + p.img.getHeight() });
			if (isPosAvailable(p.xPos + p.img.getWidth() * 2, p.yPos - p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth() * 2, p.yPos - p.img.getHeight() });
			if (isPosAvailable(p.xPos + p.img.getWidth() * 2, p.yPos + p.img.getHeight()))
				p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth() * 2, p.yPos + p.img.getHeight() });
			if (isPosAvailable(p.xPos - p.img.getWidth(), p.yPos + p.img.getHeight() * 2))
				p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth(), p.yPos + p.img.getHeight() * 2 });
			if (isPosAvailable(p.xPos + p.img.getWidth(), p.yPos + p.img.getHeight() * 2))
				p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth(), p.yPos + p.img.getHeight() * 2 });
			if (isPosAvailable(p.xPos - p.img.getWidth(), p.yPos - p.img.getHeight() * 2))
				p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth(), p.yPos - p.img.getHeight() * 2 });
			if (isPosAvailable(p.xPos + p.img.getWidth(), p.yPos - p.img.getHeight() * 2))
				p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth(), p.yPos - p.img.getHeight() * 2 });
		} else if (p.pieceNr == 3 || p.pieceNr == 9) {
			// LOPARE
			for (int i = 1; i < 8; i++) {
				x = p.xPos - p.img.getWidth() * i;
				y = p.yPos - p.img.getHeight() * i;
				if (isPosAvailable(x, y)) {
					p.movementHighlight.add(new int[] { x, y });
					if (getByPosition(x, y) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				x = p.xPos + p.img.getWidth() * i;
				y = p.yPos + p.img.getHeight() * i;
				if (isPosAvailable(x, y)) {
					p.movementHighlight.add(new int[] { x, y });
					if (getByPosition(x, y) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				x = p.xPos - p.img.getWidth() * i;
				y = p.yPos + p.img.getHeight() * i;
				if (isPosAvailable(x, y)) {
					p.movementHighlight.add(new int[] { x, y });
					if (getByPosition(x, y) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				x = p.xPos + p.img.getWidth() * i;
				y = p.yPos - p.img.getHeight() * i;
				if (isPosAvailable(x, y)) {
					p.movementHighlight.add(new int[] { x, y });
					if (getByPosition(x, y) != null) break;
				} else {
					break;
				}
			}
		} else if (p.pieceNr == 4 || p.pieceNr == 10) {
			// DROTTNING
			for (int i = 1; i < 8; i++) {
				x = p.xPos - p.img.getWidth() * i;
				if (isPosAvailable(x, p.yPos)) {
					p.movementHighlight.add(new int[] { x, p.yPos });
					if (getByPosition(x, p.yPos) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				x = p.xPos + p.img.getWidth() * i;
				if (isPosAvailable(x, p.yPos)) {
					p.movementHighlight.add(new int[] { x, p.yPos });
					if (getByPosition(x, p.yPos) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				y = p.yPos - p.img.getHeight() * i;
				if (isPosAvailable(p.xPos, y)) {
					p.movementHighlight.add(new int[] { p.xPos, y });
					if (getByPosition(p.xPos, y) != null) break;
				} else {
					break;
				}
			}
			for (int i = 1; i < 8; i++) {
				y = p.yPos + p.img.getHeight() * i;
				if (isPosAvailable(p.xPos, y)) {
					p.movementHighlight.add(new int[] { p.xPos, y });
					if (getByPosition(p.xPos, y) != null) break;
				} else {
					break;
				}
			}
		} else if (p.pieceNr == 6 || p.pieceNr == 12) {
			// TORN
			for (int i = 1; i <= 2; i++) {
				if (isPosAvailable(p.xPos - p.img.getWidth() * i, p.yPos))
					p.movementHighlight.add(new int[] { p.xPos - p.img.getWidth() * i, p.yPos });
			}
			for (int i = 1; i <= 2; i++) {
				if (isPosAvailable(p.xPos, p.yPos - p.img.getHeight() * i))
					p.movementHighlight.add(new int[] { p.xPos, p.yPos - p.img.getHeight() * i });
			}
			for (int i = 1; i <= 2; i++) {
				if (isPosAvailable(p.xPos + p.img.getWidth() * i, p.yPos))
					p.movementHighlight.add(new int[] { p.xPos + p.img.getWidth() * i, p.yPos });
			}
			for (int i = 1; i <= 2; i++) {
				if (isPosAvailable(p.xPos, p.yPos + p.img.getHeight() * i))
					p.movementHighlight.add(new int[] { p.xPos, p.yPos + p.img.getHeight() * i });
			}
		} else {
		}
	}
	
	public Piece getByPosition(int x, int y) {
		if (pieces.containsKey(positionGetter.get(new Dimension(x, y)))) {
			return pieces.get(positionGetter.get(new Dimension(x, y)));
		} else {
			return null;
		}
	}

	public boolean isPosAvailable(int x, int y) {
		int tmpX = x;
		int tmpY = y;
		while (tmpX % 24 != 0) {
			tmpX--;
		}
		while (tmpY % 24 != 0) {
			tmpY--;
		}
		if (tmpX < 24 || tmpY < 24 || tmpX > 8 * 24 || tmpY > 8 * 24) {
			return false;
		} else {
			return true;
		}
	}
	
	private long buildID() {
		return ++lastID;
	}
	
	public HashMap<Long, Piece> getPieces() {
		return pieces;
	}

	public boolean isPieceHighlighted() {
		return pieceHighlighted;
	}

	public long getHighlightedPiece() {
		return highlightedPiece;
	}

	public String getStatusMsgTop() {
		return statusMsgTop;
	}

	public String getStatusMsgBot() {
		return statusMsgBot;
	}

	public void setStatusMsgBot(String statusMsgBot) {
		this.statusMsgBot = statusMsgBot;
	}

	public void setStatusMsgTop(String statusMsgTop) {
		this.statusMsgTop = statusMsgTop;
	}

	public Piece getLastTargetedPiece() {
		return lastTargetedPiece;
	}

	public void setLastTargetedPiece(Piece lastRemovedPiece) {
		this.lastTargetedPiece = lastRemovedPiece;
	}

	public long getLastID() {
		return lastID;
	}
	
	
}