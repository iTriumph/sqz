/*
 * ............................................. 
 * 
 * 				    _ooOoo_ 
 * 		  	       o8888888o 
 * 	  	  	       88" . "88 
 *                 (| -_- |) 
 *                  O\ = /O 
 *              ____/`---*\____ 
 *               . * \\| |// `. 
 *             / \\||| : |||// \ 
 *           / _||||| -:- |||||- \ 
 *             | | \\\ - /// | | 
 *            | \_| **\---/** | | 
 *           \  .-\__ `-` ___/-. / 
 *            ___`. .* /--.--\ `. . __ 
 *        ."" *< `.___\_<|>_/___.* >*"". 
 *      | | : `- \`.;`\ _ /`;.`/ - ` : | | 
 *         \ \ `-. \_ __\ /__ _/ .-` / / 
 *======`-.____`-.___\_____/___.-`____.-*====== 
 * 
 * ............................................. 
 *              佛祖保佑 永无BUG 
 *
 * 佛曰: 
 * 写字楼里写字间，写字间里程序员； 
 * 程序人员写程序，又拿程序换酒钱。 
 * 酒醒只在网上坐，酒醉还来网下眠； 
 * 酒醉酒醒日复日，网上网下年复年。 
 * 但愿老死电脑间，不愿鞠躬老板前； 
 * 奔驰宝马贵者趣，公交自行程序员。 
 * 别人笑我忒疯癫，我笑自己命太贱； 
 * 不见满街漂亮妹，哪个归得程序员？
 *
 * 北纬30.√  154518484@qq.com
 */
package com.github.model;

import org.apache.commons.lang.builder.*;

import java.io.Serializable;
import java.util.Date;

public class PassportUser implements Serializable {

	private static final long serialVersionUID = -6996277893795640836L;
	
	//alias
	public static final String TABLE_ALIAS = "Passport用户表";
	public static final String ALIAS_UID = "用户uid";
	public static final String ALIAS_FID = "机构fid";
	public static final String ALIAS_USERNAME = "用户名";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_REAL_NAME = "真实姓名";
	public static final String ALIAS_ROLE = "角色";
	public static final String ALIAS_PHONE = "手机号码";
	public static final String ALIAS_EMAIL = "邮箱地址";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_CREATE_DATE = "创建时间";
	public static final String ALIAS_STATUS = "状态";

	public static final String ROLE_TEACHER = "1";
	public static final String ROLE_STUDENT = "3";

	
	//columns START
	/** 用户uid   db_column: uid */ 	
	private Integer uid;
	/** 机构fid   db_column: fid */ 	
	private Integer fid;
	/** 用户名   db_column: username */ 	
	private String username;
	/** 昵称   db_column: nick_name */ 	
	private String nickName;
	/** 真实姓名   db_column: real_name */ 	
	private String realName;
	/** 角色   db_column: role */ 	
	private String role;
	/** 手机号码   db_column: phone */ 	
	private String phone;
	/** 邮箱地址   db_column: email */ 	
	private String email;
	/** 性别   db_column: sex */ 	
	private Integer sex;
	/** 创建时间   db_column: create_date */ 	
	private java.util.Date createDate;
	/** 状态   db_column: status */ 	
	private Integer status;
	//columns END

	public PassportUser(){
	}

	public PassportUser(Integer uid, Integer fid, String username, String nickName, String realName, String role, String phone, String email, Integer sex, Date createDate, Integer status) {
		this.uid = uid;
		this.fid = fid;
		this.username = username;
		this.nickName = nickName;
		this.realName = realName;
		this.role = role;
		this.phone = phone;
		this.email = email;
		this.sex = sex;
		this.createDate = createDate;
		this.status = status;
	}

	public void setUid(Integer value) {
		this.uid = value;
	}
	public Integer getUid() {
		return this.uid;
	}
	public void setFid(Integer value) {
		this.fid = value;
	}
	public Integer getFid() {
		return this.fid;
	}
	public void setUsername(String value) {
		this.username = value;
	}
	public String getUsername() {
		return this.username;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setRole(String value) {
		this.role = value;
	}
	public String getRole() {
		return this.role;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}
	public Integer getSex() {
		return this.sex;
	}
	public void setCreateDate(java.util.Date value) {
		this.createDate = value;
	}
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	public Integer getStatus() {
		return this.status;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PassportUser == false) return false;
		if(this == obj) return true;
		PassportUser other = (PassportUser)obj;
		return new EqualsBuilder()
			.isEquals();
	}
}

