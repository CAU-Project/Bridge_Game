package model;

public class Cell {
	
	/*타입 상수*/
	public static final int BLOCKED_CELL = -1;
	public static final int CELL = 0;
	public static final int START = 1;
	public static final int END = 2;
	public static final int BRIDGE_START = 3;
	public static final int BRIDGE_END = 4;
	public static final int HAMMER = 5;
	public static final int SAW = 6;
	public static final int PHILIPS_DRIVER = 7;
	public static final int BRIDGE = 8;

	/*방향 상수*/
	public static final int BLANK = -1;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;
	
	private int x,y;
	private int type;
	private int backwardDirection;
	private int forwardDirection;
	
	public Cell(int x,int y,int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	

	public void setBackwardDirection(int backwardDirection) {
		this.backwardDirection = backwardDirection;
	}
	public void setForwardDirection(int forwardDirection) {
		this.forwardDirection = forwardDirection;
	}
	public void setType(int type) {
		this.type = type;
	}

	
	public int getBackwardDirection() {
		return backwardDirection;
	}
	public int getForwardDirection() {
		return forwardDirection;
	}
	public int getType() {
		return type;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

}
