/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午7:07:32 
 */
package com.yizutiyu.brightbeacon.face;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: UserList.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午7:07:32 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class UserList extends BaseFace{

	private String score; //用户的匹配得分
	private String group_id;//组iD
	private String user_id; //用户Id
	private String user_info;
	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * @return the group_id
	 */
	public String getGroup_id() {
		return group_id;
	}
	/**
	 * @param group_id the group_id to set
	 */
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	/**
	 * @return the user_id
	 */
	public String getUser_id() {
		return user_id;
	}
	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	/**
	 * @return the user_info
	 */
	public String getUser_info() {
		return user_info;
	}
	/**
	 * @param user_info the user_info to set
	 */
	public void setUser_info(String user_info) {
		this.user_info = user_info;
	}
	
	
	
}
