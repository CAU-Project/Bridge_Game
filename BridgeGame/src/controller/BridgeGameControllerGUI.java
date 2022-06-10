package controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.server.RemoteStub;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import model.Board;
import model.Cell;
import model.Die;
import model.Player;
import view.BoardPanel;
import view.PlayerPanel;


public class BridgeGameControllerGUI extends JFrame {
	/* Model */
	Die die;
	private int faceValue;
	private int numPlayer;
	private int turn;
	private int leftPlayer;
	private int availableCount;
	private int[] availablePath;
	private boolean isAnyoneFinished;
	
	private String mapName;
	private Board board;
	private Player players[];
	private Player currentPlayer;
	
	private boolean finish;
	private String infoString;
	
	/* View */
	private BoardPanel gameBoard;
	private PlayerPanel[] playerPanels;
	private JPanel mainPanel;
	private JButton btnRollDie;
	private JButton btnStay;
	private JButton btnGo;
	private JButton btnYes;
	private JButton btnNo;
	private JButton btnValue0;
	private JButton btnValue1;
	private JButton btnValue2;
	private JButton btnValue3;
	private JButton btnValue4;
	private JButton btnValue5;
	private JButton btnValue6;
	private JButton btnValueMinus1;
	private JButton btnValueMinus2;
	private JButton btnValueMinus3;
	private JButton btnValueMinus4;
	private JButton btnValueMinus5;
	private JButton btnValueMinus6;
	private JButton btnNextTurn;
	private JTextArea infoConsole;
	private JPanel playerInfoPanel;
	private JTextArea panelPlayerTextArea;
	private JLabel panelPlayerTitle;
	

	public BridgeGameControllerGUI(String mapName,int num) {
		setTitle("Bridge Game");
		
		setSize(1080,720);
		setResizable(false);//â�� ũ�⸦ �������� ���ϰ�
		setLocationRelativeTo(null);//â�� ��� ������
		setLayout(new BorderLayout());
		
		/*initialize Model */
		initializeModel(mapName,num);
		
		
		/*Initialize View */
		// ������ ���� ���� ���� ū �г�
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		// Board �� �׸� �г��� LayeredPanel�� ����� �ش�.
		/*1. ���� ���� �г� */
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBorder(new LineBorder(new Color(0,0,0)));
		layeredPane.setBounds(6,6,650,650);
		mainPanel.add(layeredPane);
		
		/*1.1 ���� �г� */
		gameBoard = new BoardPanel(6, 6, 640, 640, board);
		layeredPane.add(gameBoard,0);
		
		/*1.2 �÷��̾� �г�*/
		playerPanels = new PlayerPanel[numPlayer];
		for(int i=0; i<numPlayer;i++) {
			playerPanels[i] = new PlayerPanel(players[i]);
			layeredPane.add(playerPanels[i],JLayeredPane.PALETTE_LAYER);
		}
		
		
		/*2. ������ ��ư�� �г� */
		JPanel rightPanel = new JPanel();
		rightPanel.setBackground(Color.LIGHT_GRAY);
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		rightPanel.setBounds(656, 6, 400, 650);
		mainPanel.add(rightPanel);
		rightPanel.setLayout(null);
		
		
		
		
		/*2.1 ����� ������ ����� �г�*/
		playerInfoPanel = new JPanel();
		playerInfoPanel.setBounds(81, 28, 246, 100);
		playerInfoPanel.setBackground(Color.RED);
		rightPanel.add(playerInfoPanel);
		playerInfoPanel.setLayout(null);
		

		panelPlayerTitle = new JLabel("Player 1");
		panelPlayerTitle.setForeground(Color.WHITE);
		panelPlayerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayerTitle.setBounds(0, 6, 240, 16);
		playerInfoPanel.add(panelPlayerTitle);

		panelPlayerTextArea = new JTextArea();
		panelPlayerTextArea.setBounds(10, 34, 230, 60);
		playerInfoPanel.add(panelPlayerTextArea);

		
		/*2.2 �ܼ� ������ ����� �г�*/
		JPanel infoConsolePanel = new JPanel();
		infoConsolePanel.setBounds(81,170,246,150);
		rightPanel.add(infoConsolePanel);
		infoConsolePanel.setLayout(null);
		
		infoConsole = new JTextArea();
		infoConsole.setColumns(20);
		infoConsole.setRows(5);
		infoConsole.setBounds(6, 6, 234, 138);
		infoConsole.setText("�̵��ϱ� : Go\n�̹� ���� ������ �ٸ� ī�� ���� : Stay\n");
		infoConsolePanel.add(infoConsole);
		
		
		/*2.3 ��ư�� ���� ���� �г�*/
		JPanel btnPanel = new JPanel();
		btnPanel.setBounds(81,350,234,280);
		rightPanel.add(btnPanel);
		btnPanel.setLayout(new GridLayout(10,2));
		
		btnRollDie = new JButton("Roll Die");
		btnStay = new JButton("Stay");
		btnGo = new JButton("Go");
	    btnYes = new JButton("Yes");
		btnNo = new JButton("No");
		btnValue0 = new JButton("0");
		btnValue1 = new JButton("1");
		btnValue2 = new JButton("2");
		btnValue3 = new JButton("3");
		btnValue4 = new JButton("4");
		btnValue5 = new JButton("5");
		btnValue6 = new JButton("6");
		btnValueMinus1 = new JButton("-1");
		btnValueMinus2 = new JButton("-2");
		btnValueMinus3 = new JButton("-3");
		btnValueMinus4 = new JButton("-4");
		btnValueMinus5 = new JButton("-5");
		btnValueMinus6 = new JButton("-6");
		btnNextTurn = new JButton("NextTurn");
		
		
		btnPanel.add(btnStay);
		btnPanel.add(btnGo);
		btnPanel.add(btnYes);
		btnPanel.add(btnNo);
		btnPanel.add(btnRollDie);
		btnPanel.add(btnValue0);
		btnPanel.add(btnValue1);
		btnPanel.add(btnValue2);
		btnPanel.add(btnValue3);
		btnPanel.add(btnValue4);
		btnPanel.add(btnValue5);
		btnPanel.add(btnValue6);
		btnPanel.add(btnValue1);
		btnPanel.add(btnValueMinus1);
		btnPanel.add(btnValueMinus2);
		btnPanel.add(btnValueMinus3);
		btnPanel.add(btnValueMinus4);
		btnPanel.add(btnValueMinus5);
		btnPanel.add(btnValueMinus6);
		btnPanel.add(btnNextTurn);
		
		disableAllButtons();
		btnStay.setEnabled(true);
		btnGo.setEnabled(true);

		// btnStay : ���� ���� ������.
		btnStay.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				infoString = "";
				if(currentPlayer.decreaseBridgeCard()) {
					infoString="�ٸ� ī�尡 1�� �پ� ���� " + currentPlayer.getBridgeCard()+ "�� ���ҽ��ϴ�.\n";
				}
				else {
					infoString="�ٸ� ī�尡 0�� �̹Ƿ� �ϸ� �����մϴ�.";
				}
				
				infoConsole.setText(infoString);
				disableAllButtons();
				btnNextTurn.setEnabled(true);
				
			}
		});
		
		btnGo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				if(currentPlayer.checkBridge()) { determineBridge(currentPlayer); }
				else {
					btnRollDie.setEnabled(true);
				}
		
			}
		});
		
		btnYes.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.setWillCrossBridge(true);
				btnRollDie.setEnabled(true);
			}
		});
		
		btnNo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.setWillCrossBridge(false);
				btnRollDie.setEnabled(true);
			}
		});
		
		btnNextTurn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				nextTurn();
		
			}
		});
		
		btnRollDie.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				faceValue = die.rollDie();
				availablePath = currentPlayer.calcAvailablePath(faceValue);
				printAvailablePath(availablePath, currentPlayer);
				setAvailablePathButton();
				
				
				
			}
		});

		btnValue0.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				btnNextTurn.setEnabled(true);
			}
		});

		btnValue1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(1);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValue2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(2);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValue3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(3);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValue4.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(4);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValue5.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(5);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValue6.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(6);
				if(currentPlayer.getIsFinish()) {
					finishPlayer(currentPlayer);
				}
				btnNextTurn.setEnabled(true);
			}
		});
	
		btnValueMinus1.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-1);
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValueMinus2.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-2);
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValueMinus3.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-3);
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValueMinus4.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-4);
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValueMinus5.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-5);
				btnNextTurn.setEnabled(true);
			}
		});
		
		btnValueMinus6.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				disableAllButtons();
				currentPlayer.move(-6);
				btnNextTurn.setEnabled(true);
			}
		});
		
		
		setVisible(true);//â�� ���̰�	
			
	}
	

	
	public void initializeModel(String mapName, int num) {
		/* �ð� ������ �� 2���� ���� �Է� �޵��� ����*/
		//mapName = "another.map";
		numPlayer = num;
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
		leftPlayer = numPlayer;
		isAnyoneFinished = false;
		currentPlayer = players[turn];
		finish = false;
	}

	public void printAvailablePath(int[] availablePath,Player currentPlayer) {
		availableCount = currentPlayer.getAvailableCount();
		
		String result="";
		result +="�ֻ��� ��� : " + die.getFaceValue() +"\n";
		result +="�ٸ� ī�带 �����Ͽ� �� "+ availableCount +"ĭ �̵� ����\n";
		if(availableCount <= 0) {
			result +="�̵� ������ ĭ ���� 0 ���� �۱� ������ ���� �����մϴ�.\n";
			infoConsole.setText(result);	
			return;
		}
		
		if(isAnyoneFinished) {
			result+="�Ѹ� �̻��� �÷��̾ �������� �����߽��ϴ�. �ڷ� ���Ⱑ ���� �˴ϴ�.\n";
			result+="�̵� ������ ĭ ���� ������ �����ϴ�. �̸� �Է��ϼ���.\n";
			result+=availableCount;
			infoConsole.setText(result);	
			return;
		}
		
		result+= "�̵� ������ ĭ ���� ������ �����ϴ�. \n���� �ϳ��� �Է��ϼ���.\n";
		for(int i=0; i<availablePath[0]; i++) {
			result+= availablePath[i+1] + " ";
		}
		infoConsole.setText(result);
	}

	public void setAvailablePathButton() {
		if (availableCount == 0) {
			btnValue0.setEnabled(true);
		}
		for(int i=1; i<availablePath[0]+1 ; i++) {
			switch(availablePath[i]) {
			case 0:
				btnValue0.setEnabled(true);
				break;
			case 1:
				btnValue1.setEnabled(true);
				break;
			case 2:
				btnValue2.setEnabled(true);
				break;
			case 3:
				btnValue3.setEnabled(true);
				break;
			case 4:
				btnValue4.setEnabled(true);
				break;
			case 5:
				btnValue5.setEnabled(true);
				break;
			case 6:
				btnValue6.setEnabled(true);
				break;
			case -1:
				btnValueMinus1.setEnabled(true);
				break;
			case -2:
				btnValueMinus2.setEnabled(true);
				break;
			case -3:
				btnValueMinus3.setEnabled(true);
				break;
			case -4:
				btnValueMinus4.setEnabled(true);
				break;
			case -5:
				btnValueMinus5.setEnabled(true);
				break;
			case -6:
				btnValueMinus6.setEnabled(true);
				break;

			}
		}
	}

	public void nextTurn() {
		// Mode ���� ������Ʈ 
		turn = (turn + 1) % numPlayer;
		currentPlayer = players[turn];
		
		while(currentPlayer.getIsFinish()) {
			turn = (turn + 1) % numPlayer;
			currentPlayer = players[turn];
		}
		
		if(leftPlayer == 1) { finishGame();}
		
		// PlayerPanel ���� ������Ʈ
		switch (currentPlayer.getPlayerId()) {
		case 1:
			playerInfoPanel.setBackground(Color.RED);
			panelPlayerTitle.setText("Player 1");
			break;
		case 2:
			playerInfoPanel.setBackground(Color.GREEN);
			panelPlayerTitle.setText("Player 2");
			break;
		case 3:
			playerInfoPanel.setBackground(Color.BLUE);
			panelPlayerTitle.setText("Player 3");
			break;
		case 4:
			playerInfoPanel.setBackground(Color.DARK_GRAY);
			panelPlayerTitle.setText("Player 4");
			break;

		default:
			break;
		}
		panelPlayerTextArea.setText("�ٸ� ī�� ���� :" + currentPlayer.getBridgeCard() + "\n���� ���� : " + currentPlayer.getScore() +"\n");
		
		// InfoConsole ���� ������Ʈ
		if(!finish) {
			String result = "�̵��ϱ� : Go\n�̹� ���� ������ �ٸ� ī�� ���� : Stay\n";
			infoConsole.setText(result);
			
			// ��ư ������Ʈ
			disableAllButtons();
			btnStay.setEnabled(true);
			btnGo.setEnabled(true);
			this.repaint();
		}else {
			disableAllButtons();
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
		
		infoString = "";
		infoString +="player"+player.getPlayerId()+"�� " + rank + "������ �����߽��ϴ�.\n";
		infoString +="player"+player.getPlayerId()+"�� �� ȹ�� ���� : " + player.getScore();
		infoConsole.setText(infoString);
		isAnyoneFinished = true;
		
	}
	
	public void finishGame() {
		for(int i =0; i<numPlayer; i++) {
			if(!players[i].getIsFinish()) {
				int currentScore = players[i].getScore();
				switch (numPlayer) {
				case 3:
					players[i].setScore(currentScore+1);
					break;
				case 2:
					players[i].setScore(currentScore+1);
					break;
				default:
					break;
				}
			}
		}
		
		infoString = "== ���� ����\n";
		infoString += "== �÷��̾� ���� ==\n";
		
		for(int i=0; i < numPlayer; i++) {
			infoString += "player" +(i+1)+ " : " +  players[i].getScore() +"\n" ;
		}
		
		infoConsole.setText(infoString);
		finish = true;
		this.repaint();
		
	}
	
	public void determineBridge(Player currentPlayer) {
		if(currentPlayer.getCurrentCell().getType() == Cell.BRIDGE_START) {
			infoString = "���� ��ġ���� �ٸ��� �ǳ� �� �ֽ��ϴ�.\n �ٸ��� �ǳʽðڽ��ϱ�?[Yes/No]";
			infoConsole.setText(infoString);
			
			// ��ư ����
			btnYes.setEnabled(true);
			btnNo.setEnabled(true);
			
		}
	}

	public void disableAllButtons() {
		btnGo.setEnabled(false);
		btnStay.setEnabled(false);
		btnYes.setEnabled(false);
		btnNo.setEnabled(false);
		btnRollDie.setEnabled(false);
		btnValue0.setEnabled(false);
		btnValue1.setEnabled(false);
		btnValue2.setEnabled(false);
		btnValue3.setEnabled(false);
		btnValue4.setEnabled(false);
		btnValue5.setEnabled(false);
		btnValue6.setEnabled(false);
		btnValueMinus1.setEnabled(false);
		btnValueMinus2.setEnabled(false);
		btnValueMinus3.setEnabled(false);
		btnValueMinus4.setEnabled(false);
		btnValueMinus5.setEnabled(false);
		btnValueMinus6.setEnabled(false);
		btnNextTurn.setEnabled(false);

	}
}
