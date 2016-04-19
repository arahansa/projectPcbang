package model;
/**Model의 필드는 DB의 칼럼과 똑같아야 한다.
 * 
 * 회원 페이지.. 
 * 회원에 들어갈 것들 순번(num), 아이디(id), 이름(name), 전화번호(tel), 비밀번호(password), 마일리지(mileage), 나이(age)
 */

//num, id, password, tel, mileage, age
@SuppressWarnings("unused")
public class Member {
	private int num;
	private String id;
	private String password;
	private String tel;
	private int mileage;
	private int age;
	
	
	//Getter&Setter
	public int getNum() {
		return num;
	}



	public void setNum(int num) {
		this.num = num;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getTel() {
		return tel;
	}



	public void setTel(String tel) {
		this.tel = tel;
	}



	public int getMileage() {
		return mileage;
	}



	public void setMileage(int mileage) {
		this.mileage = mileage;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public static void main(String[] args) {
		

	}

}
