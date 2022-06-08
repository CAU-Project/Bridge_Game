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
		System.out.printf("다리 카드 갯수 : %d\n",bridgeCard);
		System.out.printf("현재 플레이어가 가진 다리카드 갯수를 제외하여 총 \u001B[33m%d칸\u001B[0m 이동 가능합니다.\n",availableCount);
		if(availableCount <= 0) {
			System.out.println("이동 가능한 칸 수가 0 보다 작기 때문에 턴을 종료합니다.");
		}else {
			System.out.println("이동 가능한 칸 수는 다음과 같습니다. 이중 하나를 입력하세요.");
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
			// 유효성 검증이 되면 이동.
			if(willCrossBridge) {
				/*다리를 건너는 경우 처리*/
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
			
			/*목적지에 아이템이 있으면 점수 획득*/
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
		// 오른쪽으로 한칸 이동하여 다리 위로 진입
		this.y++;
		currentCell = board.getCell(x, y);
		
		// 나머지 카운트들은 정상적인 Forward 움직임으로 처리하면 된다.
		moveForward(moveCnt-1);
	}

	public void checkBridge() {
		if(currentCell.getType() == Cell.BRIDGE_START) {
			System.out.println("현재 위치에서 다리를 건널 수 있습니다. 다리를 건너시겠습니까? [Y/N]");
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
			System.out.printf("다리 카드가 1개 줄어 현재 %d개 남았습니다.\n",bridgeCard);
		}else {
			System.out.println("다리 카드가 0개 이므로 턴만 종료합니다.");
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
