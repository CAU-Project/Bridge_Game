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
	private String mapName;
	private Board board;
	private Player players[];
	
	
	public BridgeGameControllerConsole() {
		init();
		playGame();
	}
	
	public void init() {
		System.out.println("������ �÷��� �� ����� ���� �Է��� �ּ���.");
		System.out.print(">>>");
		numPlayer = sc.nextInt();
		leftPlayer = numPlayer;
		
		System.out.println("�÷��� �� ���� �̸��� �Է��� �ּ���.");
		System.out.print(">>>");
		mapName = sc.next();
		//mapName = new String("default");
				
		/*Board, Die, Player ��ü ����*/
		board = new Board(mapName);
		die = new Die();
		players = new Player[numPlayer];
		for(int i=1; i<numPlayer + 1; i++ ) {
			players[i-1] = new Player(board.getStartCell().getX(), board.getStartCell().getY(), i);
			players[i-1].setCurrentCell(board.getStartCell());
			players[i-1].setBoard(board);
		}
		
		
		turn = 0;

		
	}
	
	public void playGame() {
		while(leftPlayer > 1) {
			if(players[turn].getIsFinish()) {
				turn = (turn + 1) % numPlayer;
				continue;
			}
			
			printBoard();
			printCurrentPlayerInfo();
			// 0. �ֻ����� ������ �ƴϸ� ���� �ѱ��� ����
			printSelection();
			System.out.print(">>> ");
			selection = sc.nextInt();
			
			// 1.a. �ش� ���� ���Ḧ ����
			if(selection == 2) {
				// �ٸ� ī�� �ϳ� ���̰� ���� ����
				players[turn].decreaseBridgeCard();
				turn = (turn + 1) % numPlayer;
				continue;
			}
			
			// ���� ĭ�� �ٸ��� �ǳʱ� ���̸� �ǳ��� ���� �Ǵ�
			players[turn].checkBridge();
			// 1. �ֻ��� ������
			die.rollDie();
			faceValue = die.getFaceValue();
			
			// 2. ���� �÷��̾ �� �� �ִ� ��� ����� �ֱ�.
			int availableCount = players[turn].calcAvailablePath(faceValue);
			players[turn].printAvailablePath();
			if (availableCount <= 0) {
				turn = (turn + 1) % numPlayer;
				continue;
			}
			while(!players[turn].move()) {
				System.out.println("��ȿ���� ���� ��θ� �Է��߽��ϴ�. �ٽ� �Է��ϼ���.");
				players[turn].printAvailablePath();
			}
			
			// 3. ���� �÷��̾ �������� Ȯ�� ��, 1,2�� �ݺ�
			if(players[turn].getIsFinish()) {
				finishPlayer(players[turn]);
			}
			
			turn = (turn + 1) % numPlayer;

		}
		/*������ �Ѹ��� ������ �÷��̾���� ���/������ ȭ�鿡 ����ϸ鼭 ����*/
		finishGame();	
	}
	
	public void printCurrentPlayerInfo() {
		System.out.println("================== PlayerInfo() ==========================");
		System.out.printf("���� �÷��̾� : player%d\n",players[turn].getPlayerId());
		System.out.printf("�ٸ� ī�� ���� : %d\n",players[turn].getBridgeCard());
		System.out.printf("���� ���� : %d\n",players[turn].getScore());
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
		
		System.out.printf("player%d�� %d������ �����߽��ϴ�.\n",player.getPlayerId(),rank);
		System.out.printf("player%d�� �� ȹ�� ���� : %d\n",player.getPlayerId(),player.getScore());
		
	}

	public void finishGame() {
		System.out.println("================ ���� ���� ================ ");
		System.out.println("=============== �÷��̾� ���� =============== ");
		
		for(int i=0; i < numPlayer; i++) {
			System.out.printf("player%d : %d\n",i+1,players[i].getScore());
		}
		
		
	}
	

}
