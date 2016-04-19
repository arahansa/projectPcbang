package model;

@SuppressWarnings("serial")
public class People implements java.io.Serializable{
	int num;
	String nick;
	String hour;
	int money;
	
	public People(int num, String nick, String hour, int money) {
		this.num = num;
		this.nick = nick;
		this.hour = hour;
		this.money = money;
	}

	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String toString() {
		return "num :" + num + " , nick :" + nick + " , hour :" + hour
				+ " , money :" + money;
	}
}