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
		
		/* player수, map 지정 함수 */
		setPlayerCount();
		selectMap();
				
		/*Board, Die, Player 객체 생성*/
		board = new Board(mapName);
		die = new Die();
		players = new Player[numPlayer];
		for(int i=1; i<numPlayer + 1; i++ ) {
			players[i-1] = new Player(board.getStartCell().getX(), board.getStartCell().getY(), i);
			players[i-1].setCurrentCell(board.getStartCell());
			players[i-1].setBoard(board);
		}
		
		// player1부터 시작
		turn = 0;
		isAnyoneFinished = false;
	}
	
	public void playGame() {
		while(leftPlayer > 1) {
			currentPlayer = players[turn];
			if(isPlayerFinished(currentPlayer)) { continue; }
			
			// 현재 보드 상태 출력
			printBoard();
			
			// 현재 플레이어 정보 출력
			printCurrentPlayerInfo(currentPlayer);
			

			// 1. 해당 턴에 주사위를 굴릴지, 멈추고 다리카드를 지울지 선택
			if(selectStayOrGo(currentPlayer)) { continue; }
			
			// 2. 현재 칸이 다리 시작지점이면 다리를 건널지 유무를 판단.
			if(currentPlayer.checkBridge()) { determineBridge(currentPlayer); }
			
			// 3. 주사위 굴리기
			faceValue = rollDie();
			
			// 4. 현재 플레이어가 갈 수 있는 경로 계산 및 출력	
			availablePath = currentPlayer.calcAvailablePath(faceValue);
			printAvailablePath(availablePath, currentPlayer);
			if(currentPlayer.getAvailableCount() == 0) {
				turn = (turn + 1) % numPlayer;
				continue;
			}
			
			// 5. 사용자가 선택한 숫자 만큼 이동
			movePlayer(currentPlayer);
			

			// 6. 현재 플레이어가 끝났는지 확인 후, 1-5번 반복
			if(currentPlayer.getIsFinish()) {
				finishPlayer(currentPlayer);
			}
			
			turn = (turn + 1) % numPlayer;

		}
		/*마지막 한명이 남으면 플레이어들의 등수/점수를 화면에 출력하면서 종료*/	
	}
	
	public void printCurrentPlayerInfo(Player player) {
		System.out.println("================== PlayerInfo() ==========================");
		System.out.printf("현재 플레이어 : player%d\n",player.getPlayerId());
		System.out.printf("다리 카드 갯수 : %d\n",player.getBridgeCard());
		System.out.printf("현재 점수 : %d\n",player.getScore());
	}
	
	public void printBoard() {
		
		// 현재 보드위의 상황 출력
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
		System.out.println("======= 선택하기 ========");
		System.out.println("1. 이동하기");
		System.out.println("2. 이번턴을 끝내고 다리 카드 한장 제거");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		selection = sc.nextInt();
	}

	public boolean selectStayOrGo(Player currentPlayer) {
		System.out.println("======= 선택하기 ========");
		System.out.println("1. 이동하기");
		System.out.println("2. 이번턴을 끝내고 다리 카드 한장 제거");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		
		selection = sc.nextInt();
		if(selection == 2) {
			// 다리 카드 하나 줄이고 턴을 종료
			if(currentPlayer.decreaseBridgeCard()) {
				System.out.printf("다리 카드가 1개 줄어 현재 %d개 남았습니다.\n",currentPlayer.getBridgeCard());
			}
			else {
				System.out.println("다리 카드가 0개 이므로 턴만 종료합니다.");
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
			System.out.println("현재 위치에서 다리를 건널 수 있습니다. 다리를 건너시겠습니까? [Y/N]");
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
		System.out.printf("주사위 결과 : %d\n",faceValue);
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
		
		System.out.printf("다리 카드 갯수 : %d\n",currentPlayer.getBridgeCard());
		System.out.printf("현재 플레이어가 가진 다리카드 갯수를 제외하여 총 \u001B[33m%d칸\u001B[0m 이동 가능합니다.\n",availableCount);
		if(availableCount <= 0) {
			System.out.println("이동 가능한 칸 수가 0 보다 작기 때문에 턴을 종료합니다.");
			System.out.println();	
			return;
		}
		
		if(isAnyoneFinished) {
			System.out.println("한명 이상의 플레이어가 목적지에 도착했습니다. 뒤로 가기가 제한 됩니다.");
			System.out.println("이동 가능한 칸 수는 다음과 같습니다. 이를 입력하세요.");
			System.out.printf("%3d",availableCount);
			System.out.println();	
			return;
		}
		
		System.out.println("이동 가능한 칸 수는 다음과 같습니다. 이중 하나를 입력하세요.");
		for(int i=0; i<availablePath[0]; i++) {
			System.out.printf("%3d",availablePath[i+1]);
		}
		System.out.println();	
			
	}

	public void movePlayer(Player currentPlayer) {
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		int userInput =  sc.nextInt();
		
		while(!currentPlayer.move(userInput)) {
			System.out.println("유효하지 않은 경로를 입력했습니다. 다시 입력하세요.");
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
		
		System.out.printf("player%d가 %d등으로 도착했습니다.\n",player.getPlayerId(),rank);
		System.out.printf("player%d의 총 획득 점수 : %d\n",player.getPlayerId(),player.getScore());
		isAnyoneFinished = true;
		
	}

	public void finishGame() {
		System.out.println("================ 게임 종료 ================ ");
		System.out.println("=============== 플레이어 점수 =============== ");
		
		for(int i=0; i < numPlayer; i++) {
			System.out.printf("player%d : %d\n",i+1,players[i].getScore());
		}
		
		
	}
	
	public void selectMap() {
		System.out.println("플레이 할 맵의 이름을 입력해 주세요.");
		System.out.println("[default.map  another.map]");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		mapName = sc.next();
	}
	
	public void setPlayerCount(){
		System.out.println("게임을 플레이 할 사용자 수를 입력해 주세요.");
		System.out.printf("\u001B[36m>>>\u001B[0m ");
		numPlayer = sc.nextInt();
		leftPlayer = numPlayer;
	}

}
