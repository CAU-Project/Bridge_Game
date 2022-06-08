package view;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainView extends JFrame {
	/*버튼*/
	static JButton b1=new JButton(new ImageIcon("src/images/btn2.jpg"));
	/*메인화면 패널*/
	static JPanel page1=new JPanel() {
		Image background=new ImageIcon("src/images/main.png").getImage();
		public void paint(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
				
		}
	};
	
	static JPanel page2=new JPanel() {
		/*이미지*/
		Image background=new ImageIcon(("src/images/gameImage.jpg")).getImage();
		public void paint(Graphics g) {//그리는 함수
				g.drawImage(background, 0, 0, null);//background를 그려줌		
		}
	};
	
	public MainView() {
		homeframe();
		setbtn();
		setpanel();
		
	}
	public void homeframe() {
		System.out.println("homeframe");
		setTitle("Bridge Game");
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

	public void setpanel() {
		System.out.println("setpanel");
		/*위치 설정*/
		page1.setBounds(0, 0, 1280, 720);//패널1의 위치 설정
		page2.setBounds(0, 0, 1280, 720);//패널2의 위치 설정
		/*레이아웃 지정*/	
		page2.setLayout(null);//레이아웃 설정
		page1.setLayout(null);//레이아웃 설정
		/*visible*/
		page2.setVisible(false);//창이 보이지 않게
		/*패널이나 프레임에 추가*/
		add(page1);//프레임에 패널을 추가
		add(page2);//프레임에 패널을 추가
		page1.add(b1);//패널1에 버튼을 추가
	}
	
	public void setbtn() {
		b1.setBounds(150, 500, 250, 78);//버튼1의 위치 설정
		b1.setBorderPainted(false);
//		b1.setFocusPainted(false);
//		b1.setContentAreaFilled(false);
		b1.addMouseListener(new MouseAdapter() { // 마우스 이벤트 
			@Override public void mouseEntered(MouseEvent e) { // 마우스 들어왔을때 
			} 
			@Override public void mouseExited(MouseEvent e) { // 마우스 나왔을때 	
			}
			@Override public void mousePressed(MouseEvent e) { // 클릭했을때 
				page1.setVisible(false);//창이 보이게
				page2.setVisible(true);//창이 보이게
				System.out.println("눌렀엉");//눌렸는지 확인하려고 넣음.
			} 
		});
	}

}
