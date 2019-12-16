/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午4:09:31 
 */
package com.yizutiyu.brightbeacon.face;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: Angle.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午4:09:31 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class Angle extends BaseFace {

	
     private String roll;
     private String pitch;
     private String yaw;
	/**
	 * @return the roll
	 */
	public String getRoll() {
		return roll;
	}
	/**
	 * @param roll the roll to set
	 */
	public void setRoll(String roll) {
		this.roll = roll;
	}
	/**
	 * @return the pitch
	 */
	public String getPitch() {
		return pitch;
	}
	/**
	 * @param pitch the pitch to set
	 */
	public void setPitch(String pitch) {
		this.pitch = pitch;
	}
	/**
	 * @return the yaw
	 */
	public String getYaw() {
		return yaw;
	}
	/**
	 * @param yaw the yaw to set
	 */
	public void setYaw(String yaw) {
		this.yaw = yaw;
	}
     
}
