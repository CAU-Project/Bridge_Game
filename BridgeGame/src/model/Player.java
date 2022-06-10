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
	
	
	/* �ֻ��� ���� ���ڷ� �޾�, ���� �ٸ� ī�� ������ ����Ͽ� �ش� ����ڰ�
	 * �̵� ������ ĭ�� availablePath�迭�� ��Ƽ� ��ȯ ���ش�.
	 * availablePath[0] -> �ش� int �迭�� ��� ���Ұ� �ִ��� ����
	 * availablePath[1~] -> �̵� ������ �� ���� */
	public int[] calcAvailablePath(int faceValue) {
		availableCount = faceValue - bridgeCard;
		
		// ���� �̵� ������ ĭ�� 0 �̸� ��.
		if(availableCount <= 0 ) {
			availablePath[0] = 0;
			return availablePath;
		}
		
		// 1. � �÷��̾ END�� �����ߴٸ� ������ �ֻ��� �� ��ŭ �̵�
		if(isAnyoneFinished) {
			availablePath[0] = 1;
			availablePath[1] = availableCount;
			return availablePath;
			
		}
		
		// 2. ���ÿ� �̵� 
		// 2.1 �ٸ��� �ǳʴ� ���
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
		
		// �� ó�� ���۽ÿ� �ڷ� ��������
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
		
		
		// 2.1 �ٸ��� �ǳ��� �ʴ� ���
		availablePath[0] = availableCount + 1;
		for(int i = 0 ; i< availableCount +1; i++) {
			availablePath[i+1] = availableCount - 2*i;
		}
		
		return availablePath;
	}
	
	/* ���ڷ� �־��� �� ��ŭ ������ �̵� */
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
	
	/* ���ڷ� �־��� �� ��ŭ �ڷ� �̵� */
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

	/*�ٸ��� �ǳʴ� ��� ȣ���ϴ� �Լ�*/
	public void crossBridge(int moveCnt) {
		// ���������� ��ĭ �̵��Ͽ� �ٸ� ���� ����
		this.y++;
		currentCell = board.getCell(x, y);
		
		// ������ ī��Ʈ���� �������� Forward ���������� ó���ϸ� �ȴ�.
		moveForward(moveCnt-1);
	}

	/*������� �Է°��� �ش��ϴ� ��ŭ �̵��ϴ� �Լ�*/
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
			
		}
		
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
	/*������� �Է°��� �������� ������ �Ǵ��ϴ� �Լ�*/
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
	/*���� �÷��̾��� ��ġ�� �ٸ� ���������� �Ǵ��ϴ� �Լ� */
	public boolean checkBridge() {
		if(currentCell.getType() == Cell.BRIDGE_START) { return true; }
		return false;
	}
	
	/*�ٸ� ī�带 �ϳ� ���̴� �Լ�. ���� �ٸ� ī�尡 0 �̸� ���� ������ �ʰ� false ��ȯ*/
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
