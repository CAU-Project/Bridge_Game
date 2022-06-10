package model;

public class Player {
	private int x,y;
	private int score;
	private int playerId;
	private int bridgeCard;
	private int availableCount;
	private int[] availablePath;
	
	private boolean isAnyoneFinished;
	private boolean isFinish;
	private boolean willCrossBridge;
	
	private Cell currentCell;
	private Board board;

	public Player(int x, int y, int playerId) {
		this.x = x;
		this.y = y;
		this.playerId = playerId;
		
		score = 0;
		bridgeCard = 0;
		isFinish = false;
		isAnyoneFinished = false;
		availablePath = new int[10];
	}
	
	
	/* 주사위 값을 인자로 받아, 현재 다리 카드 갯수를 고려하여 해당 사용자가
	 * 이동 가능한 칸을 availablePath배열에 담아서 반환 해준다.
	 * availablePath[0] -> 해당 int 배열에 몇개의 원소가 있는지 저장
	 * availablePath[1~] -> 이동 가능한 수 저장 */
	public int[] calcAvailablePath(int faceValue) {
		availableCount = faceValue - bridgeCard;
		
		// 만약 이동 가능한 칸이 0 이면 끝.
		if(availableCount <= 0 ) {
			availablePath[0] = 0;
			return availablePath;
		}
		
		// 1. 어떤 플레이어가 END에 도착했다면 무조건 주사위 수 만큼 이동
		if(isAnyoneFinished) {
			availablePath[0] = 1;
			availablePath[1] = availableCount;
			return availablePath;
			
		}
		
		// 2. 평상시에 이동 
		// 2.1 다리를 건너는 경우
		if(willCrossBridge) {
//			System.out.println("[calcAvalablePath] ");
//			System.out.println("available Count : " +availableCount);
			if (availableCount % 2 == 0) {
//				System.out.println("available Count %2 == 0 in");
				availablePath[0] = availableCount / 2;
//				System.out.println("availablePath[0] : " + availablePath[0]);
				for(int i =0; i<availableCount/2 ; i ++) {
					availablePath[i+1] = availableCount - 2*i;
//					System.out.println("availablePath" + (i+1) + ":" +(availableCount-2*i));

				}
			}
			else {
//				System.out.println("available Count %2 == 1 in");
				availablePath[0] = availableCount / 2 + 1;
//				System.out.println("availablePath[0] : " + availablePath[0]);
				for(int i =0; i<availableCount/2 + 1 ; i ++) {
					availablePath[i+1] = availableCount - 2*i;
//					System.out.println("availablePath" + (i+1) + ":" +(availableCount-2*i));
				}
			}
			
			return availablePath;
		}
		
		// 맨 처음 시작시에 뒤로 못가도록
		if(currentCell.getType() == Cell.START) {
			if (availableCount % 2 == 0) {
				availablePath[0] = availableCount / 2 + 1;
				for(int i =0; i<=availableCount/2 ; i ++) {
					availablePath[i+1] = availableCount - 2*i;
				}
			}
			else {
				availablePath[0] = availableCount / 2 + 1;
				for(int i =0; i<=availableCount/2 ; i ++) {
					availablePath[i+1] = availableCount - 2*i;
					//System.out.println(availablePath[i+1]);
				}
			}

			return availablePath;
		}
		
		
		// 2.1 다리를 건너지 않는 경우
		availablePath[0] = availableCount + 1;
		for(int i = 0 ; i< availableCount +1; i++) {
			availablePath[i+1] = availableCount - 2*i;
		}
		
		return availablePath;
	}
	
	/* 인자로 주어진 수 만큼 앞으로 이동 */
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
	
	/* 인자로 주어진 수 만큼 뒤로 이동 */
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

	/*다리를 건너는 경우 호출하는 함수*/
	public void crossBridge(int moveCnt) {
		// 오른쪽으로 한칸 이동하여 다리 위로 진입
		this.y++;
		currentCell = board.getCell(x, y);
		
		// 나머지 카운트들은 정상적인 Forward 움직임으로 처리하면 된다.
		moveForward(moveCnt-1);
	}

	/*사용자의 입력값에 해당하는 만큼 이동하는 함수*/
	public boolean move(int userInput) {
		if(!checkUserInput(userInput)) { return false; }
		
		if(isAnyoneFinished) {
			if(userInput != availableCount) {
				return false;
			}
			if(willCrossBridge) {
				bridgeCard++;
				crossBridge(userInput);
				willCrossBridge = false;
			}else {
				moveForward(userInput);
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
			
		}
		
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
	/*사용자의 입력값이 정상적인 값인지 판단하는 함수*/
	public boolean checkUserInput(int userInput) {
		boolean result = false;
		for(int i=0; i< availablePath[0]; i++) {
			if(userInput == availablePath[i+1]) {
				result = true;
			}
		}
		if(availablePath[0] == 0) {
			result = true;
		}
		return result;
	}
	/*현재 플레이어의 위치가 다리 시작점인지 판단하는 함수 */
	public boolean checkBridge() {
		if(currentCell.getType() == Cell.BRIDGE_START) { return true; }
		return false;
	}
	
	/*다리 카드를 하나 줄이는 함수. 만약 다리 카드가 0 이면 값을 줄이지 않고 false 반환*/
	public boolean decreaseBridgeCard() {
		if(this.bridgeCard > 0) {
			this.bridgeCard -= 1;
			return true;
		}else {
			return false;
		}
	}
	
	/* getters */
	public int getAvailableCount() {
		return availableCount;
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
	
	
	/* setters */
	public void setWillCrossBridge(boolean willCrossBridge) {
		this.willCrossBridge = willCrossBridge;
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
	public void setAnyoneFinished(boolean isAnyoneFinished) {
		this.isAnyoneFinished = isAnyoneFinished;
	}
	
	
	
	

}
