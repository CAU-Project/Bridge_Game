package controller;

import java.util.Scanner;

public class MainController {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("�ܼ� ��� : 1\nGUI ��� : 2\n�Է�: ");
		int select = sc.nextInt();
		switch (select) {
		case 1:
			new BridgeGameControllerConsole();
			break;
		case 2:
			System.out.println("�� �̸��� �Է��ϼ���.\n default.map  another.map");
			String mapName = sc.next();
			System.out.println("����� ���� �Է��ϼ���");
			int numPlayer = sc.nextInt();
			new BridgeGameControllerGUI(mapName,numPlayer);
			break;
		default:
			break;
		}

	}

}
