package controller;

import java.util.Scanner;

import model.Board;
import model.Cell;
import model.Die;
import model.Player;

public class BridgeGameControllerConsole {
	Scanner sc = new Scanner(System.in);
	
	Die die;
	private int faceValue;
	private int numPlayer;
	private int turn;
	private int selection;
	private int leftPlayer;
	private int availableCount;
	private int[] availablePath;
	
	private boolean isAnyoneFinished;
	private String mapName;
	private Board board;
	private Player players[];
	private Player currentPlayer;
	
	
	public BridgeGameControllerConsole() {
		init();
		playGame();
		finishGame();
	}
	
	public void init() {
		
		/* player��, map ���� �Լ� */
		setPlayerCount();
		selectMap();
				
		/*Board, Die, Player ��ü ����*/
		board = new Board(mapName);
		die = new Die();
		players = new Player[numPlayer];
		for(int i=1; i<numPlayer + 1; i++ ) {
			players[i-1] = new Player(board.getStartCell().getX(), board.getStartCell().getY(), i);
			players[i-1].setCurrentCell(board.getStartCell());
			players[i-1].setBoard(board);
		}
		
		// player1���� ����
		turn = 0;
		isAnyoneFinished = false;
	}
	
	public void playGame() {
		while(leftPlayer > 1) {
			currentPlayer = players[turn];
			if(isPlayerFinished(currentPlayer)) { continue; }
			
			// ���� ���� ���� ���
			printBoard();
			
			// ���� �÷��̾� ���� ���
			printCurrentPlayerInfo(currentPlayer);
			

			// 1. �ش� �Ͽ� �ֻ����� ������, ���߰� �ٸ�ī�带 ������ ����
			if(selectStayOrGo(currentPlayer)) { continue; }
			
			// 2. ���� ĭ�� �ٸ� ���������̸� �ٸ��� �ǳ��� ������ �Ǵ�.
			if(currentPlayer.checkBridge()) { determineBridge(currentPlayer); }
			
			// 3. �ֻ��� ������
			faceValue = rollDie();
			
			// 4. ���� �÷��̾ �� �� �ִ� ��� ��� �� ���	
			availablePath = currentPlayer.calcAvailablePath(faceValue);
			printAvailablePath(availablePath, currentPlayer);
			if(currentPlayer.getAvailableCount() == 0) {
				turn = (turn + 1) % numPlayer;
				continue;
			}
			
			// 5. ����ڰ� ������ ���� ��ŭ �̵�
			movePlayer(currentPlayer);
			

			// 6. ���� �÷��̾ �������� Ȯ�� ��, 1-5�� �ݺ�
			if(currentPlayer.getIsFinish()) {
				finishPlayer(currentPlayer);
			}
			
			turn = (turn + 1) % numPlayer;

		}
		/*������ �Ѹ��� ������ �÷��̾���� ���/������ ȭ�鿡 ����ϸ鼭 ����*/	
	}
	
	public void printCurrentPlayerInfo(Player player) {
		System.out.println("================== PlayerInfo() ==========================");
		System.out.printf("���� �÷��̾� : player%d\n",player.getPlayerId());
		System.out.printf("�ٸ� ī�� ���� : %d\n",player.getBridgeCard());
		System.out.printf("���� ���� : %d\n",player.getScore());
	}
	
	public void printBoard() {
		
		// ���� �������� ��Ȳ ���
		System.out.println("================== printBoard() ==========================");
		Cell cellArray[][] = board.getCellArray();
		for(int i=0; i<Board.MAX_MAP_SIZE;i++) {
			for(int j=0 ; j<Board.MAX_MAP_SIZE; j++) {
				Boolean isPlayer = false;
				int idx=0;
				for(int p=0; p<numPlayer; p++) {
					if(players[p].getX() == i && players[p].getY() == j) {
						isPlayer = true;
						idx = p;
					}
				}
				if(isPlayer) {
					switch (cellArray[i][j].getType()) {
					case Cell.START:
						System.out.printf("\u001B[33m%2d%1c\u001B[0m",players[idx].getPlayerId(),'S');
						break;
					case Cell.END:
						System.out.printf("\u001B[33m%2d%1c\u001B[0m",players[idx].getPlayerId(),'E');
						break;
					case Cell.BLOCKED_CELL:
						System.out.printf("%3c",' ');
						break;
					case Cell.CELL:
						System.out.printf("%2d%1c",players[idx].getPlayerId(),'C');
						break;				
					case Cell.HAMMER:
						System.out.printf("\u001B[35m%2d%1c\u001B[0m",players[idx].getPlayerId(),'H');
						break;
					case Cell.SAW:
						System.out.printf("\u001B[36m%2d%1c\u001B[0m",players[idx].getPlayerId(),'S');
						break;
					case Cell.PHILIPS_DRIVER:
						System.out.printf("\u001B[32m%2d%1c\u001B[0m",players[idx].getPlayerId(),'P');
						break;
					case Cell.BRIDGE_START:
						System.out.printf("\u001B[31m%2d%1c\u001B[0m",players[idx].getPlayerId(),'B');
						break;
					case Cell.BRIDGE_END:
						System.out.printf("%2d%1c",players[idx].getPlayerId(),'b');
						break;
					case Cell.BRIDGE:
						System.out.printf("\u001B[34m%2d%1c\u001B[0m",players[idx].getPlayerId(),'=');
						break;
					
					default:
						break;
					}
					
				}else {
					switch (cellArray[i][j].getType()) {
					case Cell.START:
						System.out.printf("\u001B[33m%3c\u001B[0m",'S');
						break;
					case Cell.END:
						System.out.printf("\u001B[33m%3c\u001B[0m",'E');
						break;
					case Cell.BLOCKED_CELL:
						System.out.printf("%3c",' ');
						break;
					case Cell.CELL:
						System.out.printf("%3c",'C');
						break;				
					case Cell.HAMMER:
						System.out.printf("\u001B[35m%3c\u001B[0m",'H');
						break;
					case Cell.SAW:
						System.out.printf("\u001B[36m%3c\u001B[0m",'S');
						break;
					case Cell.PHILIPS_DRIVER:
						System.out.printf("\u001B[32m%3c\u001B[0m",'P');
						break;
					case Cell.BRIDGE_START:
						System.out.printf("\u001B[31m%3c\u001B[0m",'B');
						break;
					case Cell.BRIDGE_END:
						System.out.printf("%3c",'b');
						break;
					case Cell.BRIDGE:
						System.out.printf("%3c",'=');
						break;
					default:
						break;
					}
				}
			}
			
			System.out.println();
		}
		System.out.println("==========================================================");
	}

	public void printSelection() {
		System.out.println("======= �����ϱ� ========");
		System.out.println("1. �̵��ϱ�");
		System.out.println("2. �̹����� ������ �ٸ� ī�� ���� ����");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		selection = sc.nextInt();
	}

	public boolean selectStayOrGo(Player currentPlayer) {
		System.out.println("======= �����ϱ� ========");
		System.out.println("1. �̵��ϱ�");
		System.out.println("2. �̹����� ������ �ٸ� ī�� ���� ����");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		
		selection = sc.nextInt();
		if(selection == 2) {
			// �ٸ� ī�� �ϳ� ���̰� ���� ����
			if(currentPlayer.decreaseBridgeCard()) {
				System.out.printf("�ٸ� ī�尡 1�� �پ� ���� %d�� ���ҽ��ϴ�.\n",currentPlayer.getBridgeCard());
			}
			else {
				System.out.println("�ٸ� ī�尡 0�� �̹Ƿ� �ϸ� �����մϴ�.");
			}
			
			turn = (turn + 1) % numPlayer;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void determineBridge(Player currentPlayer) {
		if(currentPlayer.getCurrentCell().getType() == Cell.BRIDGE_START) {
			System.out.println("���� ��ġ���� �ٸ��� �ǳ� �� �ֽ��ϴ�. �ٸ��� �ǳʽðڽ��ϱ�? [Y/N]");
			String answer = sc.next();
			if(answer.equals("Y") || answer.equals("y")) {
				currentPlayer.setWillCrossBridge(true);
			}
			else {
				currentPlayer.setWillCrossBridge(false);
			}
			
		}
	}
		
	public int rollDie() {
		faceValue = die.rollDie();
		System.out.printf("�ֻ��� ��� : %d\n",faceValue);
		return faceValue;
	}
	
	public boolean isPlayerFinished(Player player) {
		if(player.getIsFinish()) {
			turn = (turn + 1) % numPlayer;
			return true;
		}
		return false;
		
	}
	
	public void printAvailablePath(int[] availablePath,Player currentPlayer) {
		availableCount = currentPlayer.getAvailableCount();
		
		System.out.printf("�ٸ� ī�� ���� : %d\n",currentPlayer.getBridgeCard());
		System.out.printf("���� �÷��̾ ���� �ٸ�ī�� ������ �����Ͽ� �� \u001B[33m%dĭ\u001B[0m �̵� �����մϴ�.\n",availableCount);
		if(availableCount <= 0) {
			System.out.println("�̵� ������ ĭ ���� 0 ���� �۱� ������ ���� �����մϴ�.");
			System.out.println();	
			return;
		}
		
		if(isAnyoneFinished) {
			System.out.println("�Ѹ� �̻��� �÷��̾ �������� �����߽��ϴ�. �ڷ� ���Ⱑ ���� �˴ϴ�.");
			System.out.println("�̵� ������ ĭ ���� ������ �����ϴ�. �̸� �Է��ϼ���.");
			System.out.printf("%3d",availableCount);
			System.out.println();	
			return;
		}
		
		System.out.println("�̵� ������ ĭ ���� ������ �����ϴ�. ���� �ϳ��� �Է��ϼ���.");
		for(int i=0; i<availablePath[0]; i++) {
			System.out.printf("%3d",availablePath[i+1]);
		}
		System.out.println();	
			
	}

	public void movePlayer(Player currentPlayer) {
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		int userInput =  sc.nextInt();
		
		while(!currentPlayer.move(userInput)) {
			System.out.println("��ȿ���� ���� ��θ� �Է��߽��ϴ�. �ٽ� �Է��ϼ���.");
			System.out.printf("\u001B[36m>>>\u001B[0m ");
			userInput =  sc.nextInt();
		}
	}
	
	public void finishPlayer(Player player) {
		int rank = numPlayer - leftPlayer + 1;
		leftPlayer--;
		
		if(rank == 1) {
			player.setScore(player.getScore() + 7);
		}else if(rank == 2) {
			player.setScore(player.getScore() + 3);
		}else {
			player.setScore(player.getScore() + 1);
		}
		
		for(int i=0; i<numPlayer; i++) {
			players[i].setAnyoneFinished(true);
		}
		
		System.out.printf("player%d�� %d������ �����߽��ϴ�.\n",player.getPlayerId(),rank);
		System.out.printf("player%d�� �� ȹ�� ���� : %d\n",player.getPlayerId(),player.getScore());
		isAnyoneFinished = true;
		
	}

	public void finishGame() {
		System.out.println("================ ���� ���� ================ ");
		System.out.println("=============== �÷��̾� ���� =============== ");
		
		for(int i=0; i < numPlayer; i++) {
			System.out.printf("player%d : %d\n",i+1,players[i].getScore());
		}
		
		
	}
	
	public void selectMap() {
		System.out.println("�÷��� �� ���� �̸��� �Է��� �ּ���.");
		System.out.println("[default.map  another.map]");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		mapName = sc.next();
	}
	
	public void setPlayerCount(){
		System.out.println("������ �÷��� �� ����� ���� �Է��� �ּ���.");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		numPlayer = sc.nextInt();
		leftPlayer = numPlayer;
	}

}
