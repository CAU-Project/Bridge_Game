package view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import model.Cell;
import model.Board;
public class BoardPanel extends JPanel {
	
	private Board board;
	private CellPanel[][] cellPanelArray;
	
	public BoardPanel(int xCoord, int yCoord, int width, int height,Board board) {
		setBorder(new LineBorder(new Color(0,0,0)));
		setBounds(xCoord,yCoord,width,width);
		this.setLayout(null);
		this.board = board;
		initializeCells();
	}
	
	private void initializeCells() {
		// CellPanelµé »ý¼º
		cellPanelArray = new CellPanel[Board.MAX_MAP_SIZE][];
		for(int i=0;i<Board.MAX_MAP_SIZE;i++) {
			cellPanelArray[i] = new CellPanel[Board.MAX_MAP_SIZE];
			for(int j=0;j<Board.MAX_MAP_SIZE;j++) {
				cellPanelArray[i][j] = new CellPanel(j*40, i*40,40,40);
				cellPanelArray[i][j].setCell(board.getCell(i, j));
				this.add(cellPanelArray[i][j]);
			}
		}

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

}
