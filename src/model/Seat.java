package model;

import java.util.Calendar;
import control.Vcontrol;


/**
 * 좌석 모델~ 좌석에서 로그인하면 이걸로 받는다.
 */
public class Seat extends Thread {
	
	Vcontrol vc = Vcontrol.getInstance("모델:시트"); //싱글톤불러오기
	private int num_seat; // 좌석번호
	private String username; // 이름
	private int money; // 현재 사용요금
	private String time; // 사용시간
	private Calendar date; // 시작시간 - 요금 계산할때 쓸 것이다.
	private boolean isFirst=false;
		
	private boolean isLogin = false; // 접속여부
	private boolean isTurn = false;
	private boolean isMember = false;
	
	
	public Seat(int i) {
		this(i, "비회원");
	}

	// 회원으로 로그인 한 경우
	public Seat(int i, String nick) {
		num_seat = i;
		username = nick;
		money = 0;
		time = "00:00";
		

		if (!nick.equals("비회원")) {
			isMember = true;
		}
	}

	// Thread
	public void run() {
		try {
			money=0;
			while (true) {
				// 좌석에서 6분이 지날때마다 100원씩 늘린다. 비회원인 경우
				// 지금은 시험을 위해 10초가 지날때마다 100원씩 늘린다.
				Thread.sleep(10000);
				money += 100;
				
				//Vcontrol 호출해서 거기서 값 올려주게~
				vc.continueMoney(num_seat, date);
			}

		} catch (InterruptedException e) {
			System.out.println("Seat : 로그아웃을 한듯하네요~!");
			return;
		}
	}

	// Getter&Setter
	public void setNum_seat(int num_seat) {
		this.num_seat = num_seat;
	}

	public int getNum_seat() {
		return num_seat;
	}

	public String getUserame() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public boolean isLogin() {
		return isLogin;
	}

	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
		if(isLogin){
			// 로그인이 트루인 경우 현재 시간 초기화
			date = Calendar.getInstance();
			date.setTimeInMillis(System.currentTimeMillis());
		}
		
	}

	public boolean isMember() {
		return isMember;
	}

	public void setMember(boolean isMember) {
		this.isMember = isMember;
	}

	public boolean isTurn() {
		return isTurn;
	}

	public void setTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	
}
