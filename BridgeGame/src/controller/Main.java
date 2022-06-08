package controller;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{
	static JPanel page1=new JPanel() {
		Image background=new ImageIcon("src/images/main.png").getImage();
		public void paint(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
		}
	};
	public Main() {
		homeframe();
	}
	public void homeframe() {
		setTitle("1");
		setSize(1280,720);//프레임의 크기
		setResizable(false);//창의 크기를 변경하지 못하게
		setLocationRelativeTo(null);//창이 가운데 나오게
		setLayout(null);
		setVisible(true);//창이 보이게	
		page1.setLayout(null);
		page1.setBounds(0, 0, 1280, 720);
		add(page1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
	}
	public static void main(String[] args){
		new Main();
	}
}