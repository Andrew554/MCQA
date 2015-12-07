package cn.edu.cqut.bean;

/**
 * 个人信息
 */
public class UserMessage {

	/**
	 * 用户id 判断是否登陆
	 */
	public String user_id;

	/**
	 * 用户昵称
	 */
	public String name;

	/**
	 * 用户的真名
	 */
	public String realName;

	/**
	 * 用户邮箱
	 */
	public String email;

	/**
	 * 学号
	 */
	public String stuNumber;

	/**
	 * 是否认证
	 */
	public String attest;

	/**
	 * 用户登录令牌
	 * 
	 * @return
	 */

	public String token;

	/**
	 * 是否被认证
	 */
	public String approve;


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStuNumber() {
		return stuNumber;
	}

	public void setStuNumber(String stuNumber) {
		this.stuNumber = stuNumber;
	}

	public String getAttest() {
		return attest;
	}

	public void setAttest(String attest) {
		this.attest = attest;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getApprove() {
		return approve;
	}

	public void setApprove(String approve) {
		this.approve = approve;
	}

	@Override
	public String toString() {
		return "UserMessage [user_id=" + user_id + ", name=" + name
				+ ", realName=" + realName + ", email=" + email
				+ ", stuNumber=" + stuNumber + ", attest=" + attest
				+ ", token=" + token + ", approve=" + approve +  "]";
	}
}
