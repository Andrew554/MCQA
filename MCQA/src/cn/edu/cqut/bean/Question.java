package cn.edu.cqut.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 问题实体
 */
//@JsonIgnoreProperties({"updated_at", "avatar_file_name","avatar_content_type","avatar_file_size", "avatar_updated_at"})
public class Question implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id; //问题的id
//    @JsonProperty("user_id")
    private int user_id; //提问者的id
//    @JsonProperty("username")
    private String username;//提问者的名字
//    @JsonProperty("head_image")
    private String head_image; //提问者的头像
    private String title;
    private String content;
    private String label; //问题标签 各个标签按逗号隔开
//    @JsonProperty("read_num")
    private String read_num; //阅读量
//    @JsonProperty("owner_id")
    private int owner_id;//上一级id ，为0表示没有追问
//    @JsonProperty("created_at")
    private Date created_at;
//    private Date updated_at;

    
    public Question() {
    	
	}
    
	public Question(int id, int user_id, String username, String head_image,
			String title, String content, String label, String read_num,
			int owner_id, Date created_at) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.username = username;
		this.head_image = head_image;
		this.title = title;
		this.content = content;
		this.label = label;
		this.read_num = read_num;
		this.owner_id = owner_id;
		this.created_at = created_at;
//		this.updated_at = updated_at;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getReadNum() {
        return read_num;
    }

    public void setReadNum(String readNum) {
        this.read_num = readNum;
    }

    public int getOwnerId() {
        return owner_id;
    }

    public void setOwnerId(int ownerId) {
        this.owner_id = ownerId;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getHeadImage() {
        return head_image;
    }

    public void setHeadImage(String headImage) {
        this.head_image = headImage;
    }

    public Date getTime() {
        return created_at;
    }

    public void setTime(Date time) {
        this.created_at = time;
    }
}
