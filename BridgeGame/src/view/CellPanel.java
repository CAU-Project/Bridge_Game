package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Cell;

public class CellPanel extends JPanel {
	
	Cell cell;
	private int x;
	private int y;
	
	public final static Image START_IMG = new ImageIcon("src/images/start.png").getImage();
	public final static Image BRIDGE_IMG = new ImageIcon("src/images/bridge.png").getImage();
	public final static Image CELL_IMG = new ImageIcon("src/images/cell.png").getImage();
	public final static Image DRIVER_IMG = new ImageIcon("src/images/driver.png").getImage();
	public final static Image END_IMG = new ImageIcon("src/images/end.png").getImage();
	public final static Image HAMMER_IMG = new ImageIcon("src/images/hammer.png").getImage();
	public final static Image INTERSECTION_IMG = new ImageIcon("src/images/intersection.png").getImage();
	public final static Image SAW_IMG = new ImageIcon("src/images/saw.png").getImage();
	
	public CellPanel(int x, int y,int width, int height) {
		setBounds(x,y,width,height);
		setBorder(new LineBorder(new Color(0,0,0)));
		this.setLayout(null);
		
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		switch(cell.getType()) {
		case Cell.START:
			g.drawImage(START_IMG,x,y,null);
			break;
		case Cell.END:
			g.drawImage(END_IMG,x,y,null);
			break;
		case Cell.HAMMER:
			g.drawImage(HAMMER_IMG,x,y,null);
			break;
		case Cell.BRIDGE_START:
			g.drawImage(INTERSECTION_IMG,x,y,null);
			break;
		case Cell.SAW:
			g.drawImage(SAW_IMG,x,y,null);
			break;
		case Cell.PHILIPS_DRIVER:
			g.drawImage(DRIVER_IMG,x,y,null);
			break;
		case Cell.CELL:
			g.drawImage(CELL_IMG,x,y,null);
			break;
		case Cell.BRIDGE:
			g.drawImage(BRIDGE_IMG,x,y,null);
			break;
		case Cell.BRIDGE_END:
			g.drawImage(CELL_IMG,x,y,null);
			break;
		}
	
		
	}
}
