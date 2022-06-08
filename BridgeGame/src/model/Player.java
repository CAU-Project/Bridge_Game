package model;

import java.nio.channels.ScatteringByteChannel;
import java.util.Scanner;

public class Player {
	private int x,y;
	private int score;
	private int playerId;
	private int bridgeCard;
	private int availableCount;

	private boolean isFinish;
	private boolean willCrossBridge;
	
	private Cell currentCell;
	private Board board;
	
	private Scanner sc;
	
	public Player(int x, int y, int playerId) {
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		this.score = 0;
		this.bridgeCard = 0;
		this.isFinish = false;
		
		sc = new Scanner(System.in);
	}
	
	
	
	public int calcAvailablePath(int faceValue) {
		availableCount = faceValue - bridgeCard;
		return availableCount;
	}
	
	
	public void printAvailablePath() {
		System.out.printf("�ٸ� ī�� ���� : %d\n",bridgeCard);
		System.out.printf("���� �÷��̾ ���� �ٸ�ī�� ������ �����Ͽ� �� \u001B[33m%dĭ\u001B[0m �̵� �����մϴ�.\n",availableCount);
		if(availableCount <= 0) {
			System.out.println("�̵� ������ ĭ ���� 0 ���� �۱� ������ ���� �����մϴ�.");
		}else {
			System.out.println("�̵� ������ ĭ ���� ������ �����ϴ�. ���� �ϳ��� �Է��ϼ���.");
			if(willCrossBridge) {
				for(int i=availableCount; i > 0; i = i-2) {
					System.out.printf("%3d",i);
				}
			}else {
				for(int i=availableCount; i >= -availableCount; i = i-2) {
					if(currentCell.getType() == Cell.START) {
						if (i < 0) {
							break;
						}
					}
					System.out.printf("%3d",i);
				}
			}
			System.out.println();			
		}
	}
	
	public void moveForward(int moveCnt) {
		for(int i = 0; i< moveCnt ; i++) {
			switch (currentCell.getForwardDirection()) {
			case Cell.RIGHT:
				this.y++;
				break;
			case Cell.UP:
				this.x--;
				break;
			case Cell.DOWN:
				this.x++;
				break;
			default:
				break;
			}
			currentCell =  board.getCell(x, y);
			if(currentCell.getType() == Cell.END) {
				isFinish = true;
				break;
			}
		}
	}
	
	public void moveBackward(int moveCnt) {
		
		for(int i =0; i<moveCnt; i++) {
			switch (currentCell.getBackwardDirection()) {
			case Cell.LEFT:
				this.y--;
				break;
			case Cell.RIGHT:
				this.y++;
				break;
			case Cell.UP:
				this.x--;
				break;
			case Cell.DOWN:
				this.x++;
				break;
			default:
				break;
			}
			currentCell =  board.getCell(x, y);
		}

	}

	public boolean move() {
		System.out.print(">>> ");
		int userInput =  sc.nextInt();
		if( (Math.abs(userInput) <= availableCount ) && ( (availableCount - userInput) % 2 == 0) ) {
			// ��ȿ�� ������ �Ǹ� �̵�.
			if(willCrossBridge) {
				/*�ٸ��� �ǳʴ� ��� ó��*/
				if(userInput <= 0) {
					return false;
				}else {
					bridgeCard++;
					crossBridge(userInput);
					willCrossBridge = false;
				}
			
			}else {
				if(userInput < 0) {
					moveBackward((-1)*userInput);
				}else {
					moveForward(userInput);
				}
				
			}
			
			/*�������� �������� ������ ���� ȹ��*/
			switch (currentCell.getType()) {
			case Cell.SAW:
				score = score + 3; 
				break;
			case Cell.HAMMER:
				score = score + 2; 
				break;
			case Cell.PHILIPS_DRIVER:
				score = score + 1; 
				break;
			default:
				break;
			}
			
			return true;
		}else {
			return false;
		}
		
	}
	
	public void crossBridge(int moveCnt) {
		// ���������� ��ĭ �̵��Ͽ� �ٸ� ���� ����
		this.y++;
		currentCell = board.getCell(x, y);
		
		// ������ ī��Ʈ���� �������� Forward ���������� ó���ϸ� �ȴ�.
		moveForward(moveCnt-1);
	}

	public void checkBridge() {
		if(currentCell.getType() == Cell.BRIDGE_START) {
			System.out.println("���� ��ġ���� �ٸ��� �ǳ� �� �ֽ��ϴ�. �ٸ��� �ǳʽðڽ��ϱ�? [Y/N]");
			String answer = sc.next();
			if(answer.equals("Y") || answer.equals("y")) {
				willCrossBridge = true;
			}
			else {
				willCrossBridge = false;
			}
			
		}
	}
	
	public void decreaseBridgeCard() {
		if(this.bridgeCard > 0) {
			this.bridgeCard -= 1;
			System.out.printf("�ٸ� ī�尡 1�� �پ� ���� %d�� ���ҽ��ϴ�.\n",bridgeCard);
		}else {
			System.out.println("�ٸ� ī�尡 0�� �̹Ƿ� �ϸ� �����մϴ�.");
		}
	}
	
	public int getBridgeCard() {
		return bridgeCard;
	}
	public boolean getIsFinish() {
		return isFinish;
	}
	public int getScore() {
		return score;
	}
	public int getPlayerId() {
		return playerId;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}	
	public Cell getCurrentCell() {
		return currentCell;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	public void setBridgeCard(int bridgeCard) {
		this.bridgeCard = bridgeCard;
	}
	public void setCurrentCell(Cell currentCell) {
		this.currentCell = currentCell;
	}
	public void setBoard(Board board) {
		this.board = board;
	}

	
	
	

}
