/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午4:03:45 
 */
package com.yizutiyu.brightbeacon.face;

import java.io.Serializable;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: BaseFace.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午4:03:45 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class BaseFace  implements Serializable{

	private String probability;
	private String  type;
	/**
	 * @return the probability
	 */
	public String getProbability() {
		return probability;
	}
	/**
	 * @param probability the probability to set
	 */
	public void setProbability(String probability) {
		this.probability = probability;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}
