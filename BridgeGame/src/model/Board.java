package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Board {
	public static final int MAX_MAP_SIZE = 16;

	private char[][] bufferMap;
	private int cellCount;
	private Cell[][] cellArray;
	private Cell startCell;
	
	public Board(String s) {
		// 경로 설정
		String defaultPath = "src/map/";
		String path = defaultPath + s;
		System.out.println(path);
		
		// 초기화
		bufferMap = new char[100][3];
		
		// map파일에서 char읽고, buffer에 저장
		loadMap(path);
		parsingMap();
		
		System.out.println("Pasing End");
	}
	
	public void loadMap(String path) {
		cellCount = 0;
		try {
			File file = new File(path);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(fileReader);
			
			String bufferString;
			char[] charArray;
			
			while((bufferString = bufReader.readLine())!= null ) {
				bufferString = bufferString.replace(" ","");
				charArray = bufferString.toCharArray().clone();
				bufferMap[cellCount++] = charArray;						
			}
		}catch(FileNotFoundException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}
		System.out.println("=== loadMap===");
		System.out.println("cellCount : " + cellCount);
		System.out.println("loadmap Finish");

		
	}
	
	public void parsingMap() {
		// map 파일 파싱해서 cellArray에 전부 맞게 집어 넣어라!
		System.out.println("parsingMap");
		int result[] = calcMapBoundary();
		int currentX=0;
		int currentY=0;
		// minRow가 -인 경우 보정해준다.
		if(result[0] < 0) {
			currentX -=result[0];
		}
		
		cellArray = new Cell[MAX_MAP_SIZE][];
		for(int i=0;i<MAX_MAP_SIZE;i++) {
			cellArray[i] = new Cell[MAX_MAP_SIZE];
			for(int j=0;j<MAX_MAP_SIZE;j++) {
				cellArray[i][j] = new Cell(i, j, Cell.BLOCKED_CELL);
			}
		}
		
		
		int currentType;
		
		// 처음 시작만 수작업
		startCell = cellArray[currentX][currentY];
		cellArray[currentX][currentY].setType(Cell.START);
		cellArray[currentX][currentY].setBackwardDirection(Cell.BLANK);
		switch (bufferMap[0][1]) {
		case 'R':
			cellArray[currentX][currentY].setForwardDirection(Cell.RIGHT);
			currentY++;
			break;
		case 'D':
			cellArray[currentX][currentY].setForwardDirection(Cell.DOWN);
			currentX++;
			break;
		case 'U':
			cellArray[currentX][currentY].setForwardDirection(Cell.UP);
			currentX--;
			break;
		}

		
		for(int i=1; i<cellCount -1 ; i++) {
			
			switch(bufferMap[i][0]) {
			case 'S':
				currentType = Cell.SAW;
				break;
			case 'C':
				currentType = Cell.CELL;
				break;
			case 'B':
				currentType = Cell.BRIDGE_START;
				/*오른쪽 칸은 BRIDE 수작업을 해준다.*/
				cellArray[currentX][currentY+1].setType(Cell.BRIDGE);
				cellArray[currentX][currentY+1].setBackwardDirection(Cell.LEFT);
				cellArray[currentX][currentY+1].setForwardDirection(Cell.RIGHT);
				break;
			case 'H':
				currentType = Cell.HAMMER;
				break;
			case 'P':
				currentType = Cell.PHILIPS_DRIVER;
				break;
			case 'b':
				currentType = Cell.BRIDGE_END;
				break;
			default:
				currentType = 0;
				break;
									
			}

			cellArray[currentX][currentY].setType(currentType);
			
			switch (bufferMap[i][1]) {
			// backward 방향
			case 'R':
				cellArray[currentX][currentY].setBackwardDirection(Cell.RIGHT);
				break;
			case 'L':
				cellArray[currentX][currentY].setBackwardDirection(Cell.LEFT);
				break;
			case 'D':
				cellArray[currentX][currentY].setBackwardDirection(Cell.DOWN);
				break;
			case 'U':
				cellArray[currentX][currentY].setBackwardDirection(Cell.UP);
				break;
			
			}
			
			switch (bufferMap[i][2]) {
			// forward 방향
			case 'R':
				cellArray[currentX][currentY].setForwardDirection(Cell.RIGHT);
				currentY++;
				break;
			case 'D':
				cellArray[currentX][currentY].setForwardDirection(Cell.DOWN);
				currentX++;
				break;
			case 'U':
				cellArray[currentX][currentY].setForwardDirection(Cell.UP);
				currentX--;
				break;
			
			}
				
		}
		
		// End 지점
		cellArray[currentX][currentY].setType(Cell.END);
		
	}
	
	public int[] calcMapBoundary() {
		int minRow=0;
		int minCol=0;
		int maxRow=0;
		int maxCol=0;
		int currentX=0;
		int currentY=0;
		int idx=0;
		char buf;
		
		while(idx < cellCount-1) {
			if(idx == 0) {
				buf = bufferMap[idx++][1];
			}
			else {
				buf = bufferMap[idx++][2];
			}
			
			switch (buf) {
			case 'R':
				currentY++;
				maxCol = Math.max(maxCol, currentY);				
				break;
			case 'D':
				currentX++;
				maxRow = Math.max(maxRow, currentX);
				break;
			case 'U':
				currentX--;
				minRow = Math.min(minRow, currentX);
				break;
			
			default:
				break;
			}
		}
		
		return new int[] {minRow,minCol,maxRow,maxCol};
	}
	

	public Cell[][] getCellArray() {
		return cellArray;
	}

	public Cell getStartCell() {
		return startCell;
	}
	
	public Cell getCell(int x, int y) {
		return cellArray[x][y];
	}
	
}
