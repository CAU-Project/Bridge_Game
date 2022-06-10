package controller;

import java.util.Scanner;

public class MainController {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("콘솔 모드 : 1\nGUI 모드 : 2\n입력: ");
		int select = sc.nextInt();
		switch (select) {
		case 1:
			new BridgeGameControllerConsole();
			break;
		case 2:
			System.out.println("맵 이름을 입력하세요.\n default.map  another.map");
			String mapName = sc.next();
			System.out.println("사용자 수를 입력하세요");
			int numPlayer = sc.nextInt();
			new BridgeGameControllerGUI(mapName,numPlayer);
			break;
		default:
			break;
		}

	}

}
