package in.dapai.common.model;

import java.util.HashMap;
import java.util.Map;

public class User {
    protected String id;
    protected long score;// 总共
    protected String userName;
    protected String userPwd;
    protected String nickName;//
    protected String avatarUrl;// 头像
    protected int level;
    protected String sex;
    protected int diamond;
    protected String mood;
    protected String area;// 区域
    protected Map<String, Object> map=new HashMap<String, Object>();//更多的属性
    public User(String id, long score, String userName, String userPwd, String nickName, String avatarUrl, int level, String sex, int diamond, String mood) {
	this.id = id;
	this.score = score;
	this.userName = userName;
	this.userPwd = userPwd;
	this.nickName = nickName;
	this.avatarUrl = avatarUrl;
	this.level = level;
	this.sex = sex;
	this.diamond = diamond;
	this.mood = mood;
    }

    public User() {
    }



    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public long getScore() {
	return score;
    }

    public void setScore(long score) {
	this.score = score;
    }

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public String getUserPwd() {
	return userPwd;
    }

    public void setUserPwd(String userPwd) {
	this.userPwd = userPwd;
    }

    public String getNickName() {
	return nickName;
    }

    public void setNickName(String nickName) {
	this.nickName = nickName;
    }

    public String getAvatarUrl() {
	return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
	this.avatarUrl = avatarUrl;
    }

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

    public String getSex() {
	return sex;
    }

    public void setSex(String sex) {
	this.sex = sex;
    }

    public int getDiamond() {
	return diamond;
    }

    public void setDiamond(int diamond) {
	this.diamond = diamond;
    }

    public String getMood() {
	return mood;
    }

    public void setMood(String mood) {
	this.mood = mood;
    }

    public String getArea() {
	return area;
    }

    public void setArea(String area) {
	this.area = area;
    }

    public Map<String, Object> getMap() {
	return map;
    }

    public void setMap(Map<String, Object> map) {
	this.map = map;
    }

}
