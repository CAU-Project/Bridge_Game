package controller;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame{
	static JPanel page1=new JPanel() {
		Image background=new ImageIcon("src/images/main.png").getImage();
		public void paint(Graphics g) {//�׸��� �Լ�
				g.drawImage(background, 0, 0, null);//background�� �׷���		
		}
	};
	public Main() {
		homeframe();
	}
	public void homeframe() {
		setTitle("1");
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
	public static void main(String[] args){
		new Main();
	}
}