import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class BridgeGame extends JFrame {
	
	private Image mainScreenImage = new ImageIcon("src/images/main.png").getImage();
	private boolean isMainScreen, isGameScreen;
	
	
	public BridgeGame() {
		// 프레임 설정
		setTitle("Bridge Game");
		//setUndecorated(true);
		setSize(main.SCREEN_WIDTH,main.SCREEN_HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);		

		// 게임 시작시 관련 변수 설정
		init();
	}
	
	private void init() {
		isMainScreen = true;
		isGameScreen = false;
	}
	
	@Override
	public void paint(Graphics g) {
		
	}

}
