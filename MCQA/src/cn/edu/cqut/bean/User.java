package cn.edu.cqut.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 */
//@JsonIgnoreProperties({"name", "created_at", "updated_at", "enable", "remark", "organization_id", "parent_id", "society_id", "fellow_id"})
//@DatabaseTable(tableName = "users")
public class User implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String COLUMN_ID = "id";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_REALNAME = "realname";
    public static final String COLUMN_PINGYIN = "pingyin";
    public static final String COLUMN_STU_NUM = "stu_num"; //学号
    public static final String COLUMN_USER_SIGN = "user_sign";//个性签名
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_BIRTH = "birth";
    public static final String COLUMN_HOBBY = "hobby";//兴趣爱好
    public static final String COLUMN_HEAD_IMAGE = "head_image";
    public static final String COLUMN_SCHOOL_EMAIL = "school_email";
    public static final String COLUMN_FELLOW = "fellow_name";//老乡会
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_DEFAULT_ORG = "default_org";//默认组织身份
    public static final String COLUMN_MAJOR = "major";//专业
    public static final String COLUMN_SUB_COLLAGE = "sub_collage";//学院
    public static final String COLUMN_TOKEN = "token";//登陆令牌
    public static final String COLUMN_RANK = "rank";//排名


    private int id;
    private String phone;
    private String email;
    private String password;
    private String username;
    private String real_name;
    private String pingyin;
    private String stu_num;
    private String user_sign;
    private int sex;
    private Date birth;
    private String hobby;
    private String head_image;
    private String school_email;
    private String fellow_name;
    private String clazz;
    private String default_org;
    private String major_id;
    private String sub_college_id;
    private String authentication_token;
    private String rank;
    private String is_approve;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return real_name;
    }

    public void setRealname(String realname) {
        this.real_name = realname;
    }

    public String getPingyin() {
        return pingyin;
    }

    public void setPingyin(String pingyin) {
        this.pingyin = pingyin;
    }

    public String getStuNum() {
        return stu_num;
    }

    public void setStuNum(String stuNum) {
        this.stu_num = stuNum;
    }

    public String getUserSign() {
        return user_sign;
    }

    public void setUserSign(String userSign) {
        this.user_sign = userSign;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getHeadImage() {
        return head_image;
    }

    public void setHeadImage(String headImage) {
        this.head_image = headImage;
    }

    public String getSchoolEmail() {
        return school_email;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.school_email = schoolEmail;
    }

    public String getFellow() {
        return fellow_name;
    }

    public void setFellow(String fellow) {
        this.fellow_name = fellow;
    }

    public String getClasses() {
        return clazz;
    }

    public void setClasses(String classes) {
        this.clazz = classes;
    }

    public String getDefaultOrg() {
        return default_org;
    }

    public void setDefaultOrg(String defaultOrg) {
        this.default_org = defaultOrg;
    }

    public String getMajor() {
        return major_id;
    }

    public void setMajor(String major) {
        this.major_id = major;
    }

    public String getSubCollage() {
        return sub_college_id;
    }

    public void setSubCollage(String subCollage) {
        this.sub_college_id = subCollage;
    }

    public String getToken() {
        return authentication_token;
    }

    public void setToken(String token) {
        this.authentication_token = token;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getApprove() {
        return is_approve;
    }

    public void setApprove(String approve) {
        this.is_approve = approve;
    }
}
