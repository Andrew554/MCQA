package cn.edu.cqut.bean;

import java.io.Serializable;
import java.util.Date;

public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String user_id;
	private String content;
	private String praise_num;
	private String user_role; 
	private String question_id;
	private String head_image;
	private String user_sign;
	private Date created_at;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getuser_id() {
		return user_id;
	}

	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getContext() {
		return content;
	}

	public void setContext(String context) {
		this.content = context;
	}

	public String getpraise_num() {
		return praise_num;
	}

	public void setpraise_num(String praise_num) {
		this.praise_num = praise_num;
	}

	public String getuser_role() {
		return user_role;
	}

	public void setuser_role(String user_role) {
		this.user_role = user_role;
	}

	public String getquestion_id() {
		return question_id;
	}

	public void setquestion_id(String question_id) {
		this.question_id = question_id;
	}

	public String gethead_image() {
		return head_image;
	}

	public void sethead_image(String head_image) {
		this.head_image = head_image;
	}

	public String getuser_sign() {
		return user_sign;
	}

	public void setuser_sign(String user_sign) {
		this.user_sign = user_sign;
	}

	public Date getcreated_at() {
		return created_at;
	}

	public void setcreated_at(Date created_at) {
		this.created_at = created_at;
	}
}
