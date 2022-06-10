package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Board;
import model.Cell;
import model.Player;

public class PlayerPanel extends JPanel {
	public static final int PLAYER_IMG_WIDTH = 20;
	public static final int PLAYER_IMG_HEIGHT = 33;
	
	public final static Image PLAYER1_IMG = new ImageIcon("src/images/player1.png").getImage();
	public final static Image PLAYER2_IMG = new ImageIcon("src/images/player2.png").getImage();
	public final static Image PLAYER3_IMG = new ImageIcon("src/images/player3.png").getImage();
	public final static Image PLAYER4_IMG = new ImageIcon("src/images/player4.png").getImage();

	private Player player;
	
	public PlayerPanel(Player player) {
		this.player = player;
		setBorder(new LineBorder(new Color(0,0,0)));
		setBounds((player.getY()+player.getPlayerId())*40,player.getX()*40,20,33);
		this.setLayout(null);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch(player.getPlayerId()) {
		case 1:
			this.setLocation(40*player.getY()+11,40*player.getX()+6);
			g.drawImage(PLAYER1_IMG,0,0,null);
			break;
		case 2:
			this.setLocation(40*player.getY()+16,40*player.getX()+6);
			g.drawImage(PLAYER2_IMG,0,0,null);
			break;
		case 3:
			this.setLocation(40*player.getY()+21,40*player.getX()+6);
			g.drawImage(PLAYER3_IMG,0,0,null);
			break;
		case 4:
			this.setLocation(40*player.getY()+26,40*player.getX()+6);
			g.drawImage(PLAYER4_IMG,0,0,null);
			break;

		}
	}
}
