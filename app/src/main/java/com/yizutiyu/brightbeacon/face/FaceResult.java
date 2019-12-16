/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午3:58:45 
 */
package com.yizutiyu.brightbeacon.face;

import java.util.List;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: FaceResult.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午3:58:45 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class FaceResult {

	private String face_num;
	
	private List<FaceList> face_list;
	
	private List<UserList> user_list;
	

	/**
	 * @return the user_list
	 */
	public List<UserList> getUser_list() {
		return user_list;
	}

	/**
	 * @param user_list the user_list to set
	 */
	public void setUser_list(List<UserList> user_list) {
		this.user_list = user_list;
	}

	/**
	 * @return the face_num
	 */
	public String getFace_num() {
		return face_num;
	}

	/**
	 * @param face_num the face_num to set
	 */
	public void setFace_num(String face_num) {
		this.face_num = face_num;
	}

	/**
	 * @return the face_list
	 */
	public List<FaceList> getFace_list() {
		return face_list;
	}

	/**
	 * @param face_list the face_list to set
	 */
	public void setFace_list(List<FaceList> face_list) {
		this.face_list = face_list;
	}
	
	
}
