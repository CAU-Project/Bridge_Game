package controller;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class BridgeGameControllerGUI extends JFrame {
	private boolean isMain;
	private boolean isGame;
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 720;
	
	
	
	
	private JButton startButton;
	private JPanel mainPanel;
	private JPanel mainPanel2;
	private JPanel gamePanel;

	public BridgeGameControllerGUI() {
		setTitle("Bridge Game");
		setSize(1080,720);//프레임의 크기
		setResizable(false);//창의 크기를 변경하지 못하게
		setLocationRelativeTo(null);//창이 가운데 나오게
		setLayout(new BorderLayout());
		setVisible(true);//창이 보이게	
		
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		JLayeredPane layeredPane = new JLayeredPane();

	}

	public void init() {
		isMain = true;
		isGame = false;
		
		mainPanel = new JPanel();
		
		setContentPane(mainPanel);
		mainPanel.setBounds(0,0,480,730);
		mainPanel.setLayout(new BorderLayout());
		
		ImageIcon image = new ImageIcon("images/apple.jpg");
		JLabel label = new JLabel(image);
		mainPanel.add(BorderLayout.CENTER,label);
		
		mainPanel2 = new JPanel();
		mainPanel2.setBackground(Color.RED);
		mainPanel2.setSize(480,200);
		
		startButton = new JButton("Start Game");
		startButton.setBounds(0,580,50,50);
		mainPanel2.add(startButton);
		mainPanel.add(BorderLayout.SOUTH,mainPanel2);

		
		
	}
	
	

}
