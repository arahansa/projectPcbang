package model.manage_member;

public class MemberInfo {

	private String id;
	private String tel;
	private String mileage;
	private String age;

	public MemberInfo(String id, String tel, String mileage, String age) {
		this.id = id;
		this.tel = tel;
		this.mileage = mileage;
		this.age = age;

	}

	public String getId() {
		return id;
	}

	public String getTel() {
		return tel;
	}

	public String getMileage() {
		return mileage;
	}

	public String getAge() {
		return age;
	}

}
