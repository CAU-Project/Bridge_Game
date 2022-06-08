package model;

public class Die {
	private int faceValue;
	
	public Die() {
		
	}
	
	public void rollDie() {
		faceValue = (int)(Math.random()*6)+1;
		System.out.printf("주사위 결과 : %d\n",faceValue);
	}

	public int getFaceValue() {
		return faceValue;
	}
	
	

}
