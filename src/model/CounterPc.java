package model;



public class CounterPc {
	private int money;
	
	
	private static CounterPc instance = new CounterPc();
	public static CounterPc getInstance(){
		return instance;
	}

	public synchronized void plusMoney(int money){
		this.money += money;
	}
	
	public synchronized void minusMoney(int money){
		this.money -= money;
	}

	
	
	
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}


	public static void main(String[] args) {
		

	}

}
