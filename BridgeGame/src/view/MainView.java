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
	/*��ư*/
	static JButton b1=new JButton(new ImageIcon("src/images/btn2.jpg"));
	/*����ȭ�� �г�*/
	static JPanel page1=new JPanel() {
		Image background=new ImageIcon("src/images/main.png").getImage();
		public void paint(Graphics g) {//�׸��� �Լ�
				g.drawImage(background, 0, 0, null);//background�� �׷���		
				
		}
	};
	
	static JPanel page2=new JPanel() {
		/*�̹���*/
		Image background=new ImageIcon(("src/images/gameImage.jpg")).getImage();
		public void paint(Graphics g) {//�׸��� �Լ�
				g.drawImage(background, 0, 0, null);//background�� �׷���		
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
		setSize(1280,720);//�������� ũ��
		setResizable(false);//â�� ũ�⸦ �������� ���ϰ�
		setLocationRelativeTo(null);//â�� ��� ������
		setLayout(null);
		setVisible(true);//â�� ���̰�	
		page1.setLayout(null);
		page1.setBounds(0, 0, 1280, 720);
		add(page1);	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame�� ���������� ����ǰ�
	}

	public void setpanel() {
		System.out.println("setpanel");
		/*��ġ ����*/
		page1.setBounds(0, 0, 1280, 720);//�г�1�� ��ġ ����
		page2.setBounds(0, 0, 1280, 720);//�г�2�� ��ġ ����
		/*���̾ƿ� ����*/	
		page2.setLayout(null);//���̾ƿ� ����
		page1.setLayout(null);//���̾ƿ� ����
		/*visible*/
		page2.setVisible(false);//â�� ������ �ʰ�
		/*�г��̳� �����ӿ� �߰�*/
		add(page1);//�����ӿ� �г��� �߰�
		add(page2);//�����ӿ� �г��� �߰�
		page1.add(b1);//�г�1�� ��ư�� �߰�
	}
	
	public void setbtn() {
		b1.setBounds(150, 500, 250, 78);//��ư1�� ��ġ ����
		b1.setBorderPainted(false);
//		b1.setFocusPainted(false);
//		b1.setContentAreaFilled(false);
		b1.addMouseListener(new MouseAdapter() { // ���콺 �̺�Ʈ 
			@Override public void mouseEntered(MouseEvent e) { // ���콺 �������� 
			} 
			@Override public void mouseExited(MouseEvent e) { // ���콺 �������� 	
			}
			@Override public void mousePressed(MouseEvent e) { // Ŭ�������� 
				page1.setVisible(false);//â�� ���̰�
				page2.setVisible(true);//â�� ���̰�
				System.out.println("������");//���ȴ��� Ȯ���Ϸ��� ����.
			} 
		});
	}

}
