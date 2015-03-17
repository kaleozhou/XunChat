package com.cn.ouan.xunchat.bean; 

import java.io.Serializable;

public class UserBean implements Serializable{
	private String 	name;//用户名
	private String	password;//密码
	private String 	uid;//用户账户
	private String 	gender;//性别
	private String 	age;//年龄 
	private String 	clientId;//用户设备id
	private String 	country;//国家
	private String	provide;//省	
	private String	city;//城市
	private String	address;//地址
	private String	portrait_1;//用户头像
	private String	portrait_2;//用户头像高清
	private String	interest;//兴趣
	private String	height;//身高
	private String	vocation;//职业
	private String	marriage;//婚姻	
	private String email;//邮箱
	private String description;//备注
	private String flag;
	public UserBean(){
		
	}
	 

	public UserBean(String name, String password, String uid, String gender,
			String age, String clientId, String country, String provide,
			String city, String address, String portrait_1, String portrait_2,
			String interest, String height, String vocation, String marriage,String flag) {
		super();
		this.name = name;
		this.password = password;
		this.uid = uid;
		this.gender = gender;
		this.age = age;
		this.clientId = clientId;
		this.country = country;
		this.provide = provide;
		this.city = city;
		this.address = address;
		this.portrait_1 = portrait_1;
		this.portrait_2 = portrait_2;
		this.interest = interest;
		this.height = height;
		this.vocation = vocation;
		this.marriage = marriage;
		this.flag=flag;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvide() {
		return provide;
	}
	public void setProvide(String provide) {
		this.provide = provide;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPortrait_1() {
		return portrait_1;
	}
	public void setPortrait_1(String portrait_1) {
		this.portrait_1 = portrait_1;
	}
	public String getPortrait_2() {
		return portrait_2;
	}
	public void setPortrait_2(String portrait_2) {
		this.portrait_2 = portrait_2;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getVocation() {
		return vocation;
	}
	public void setVocation(String vocation) {
		this.vocation = vocation;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}


	@Override
	public String toString() {
		return "UserBean [name=" + name + ", password=" + password + ", uid="
				+ uid + ", gender=" + gender + ", age=" + age + ", clientId="
				+ clientId + ", country=" + country + ", provide=" + provide
				+ ", city=" + city + ", address=" + address + ", portrait_1="
				+ portrait_1 + ", portrait_2=" + portrait_2 + ", interest="
				+ interest + ", height=" + height + ", vocation=" + vocation
				+ ", marriage=" + marriage + ", email=" + email
				+ ", description=" + description + ", flag=" + flag + "]";
	}


 


 
 
	
}
