/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午4:02:59 
 */
package com.yizutiyu.brightbeacon.face;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: FaceList.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午4:02:59 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class FaceList {
	
	private String  beauty;
	private String face_token;
	private String face_probability;
	private String age;
	
	private Glasses glasses;
	
	private Expression expression;
	
	private FaceShape face_shape;
	
	private Gender gender;
	
	private Race race;
	
	private Angle angle;
	
	private Location location;

	/**
	 * @return the beauty
	 */
	public String getBeauty() {
		return beauty;
	}

	/**
	 * @param beauty the beauty to set
	 */
	public void setBeauty(String beauty) {
		this.beauty = beauty;
	}

	/**
	 * @return the face_token
	 */
	public String getFace_token() {
		return face_token;
	}

	/**
	 * @param face_token the face_token to set
	 */
	public void setFace_token(String face_token) {
		this.face_token = face_token;
	}

	/**
	 * @return the face_probability
	 */
	public String getFace_probability() {
		return face_probability;
	}

	/**
	 * @param face_probability the face_probability to set
	 */
	public void setFace_probability(String face_probability) {
		this.face_probability = face_probability;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the glasses
	 */
	public Glasses getGlasses() {
		return glasses;
	}

	/**
	 * @param glasses the glasses to set
	 */
	public void setGlasses(Glasses glasses) {
		this.glasses = glasses;
	}

	/**
	 * @return the expression
	 */
	public Expression getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(Expression expression) {
		this.expression = expression;
	}

	/**
	 * @return the face_shape
	 */
	public FaceShape getFace_shape() {
		return face_shape;
	}

	/**
	 * @param face_shape the face_shape to set
	 */
	public void setFace_shape(FaceShape face_shape) {
		this.face_shape = face_shape;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the race
	 */
	public Race getRace() {
		return race;
	}

	/**
	 * @param race the race to set
	 */
	public void setRace(Race race) {
		this.race = race;
	}

	/**
	 * @return the angle
	 */
	public Angle getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(Angle angle) {
		this.angle = angle;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	
}
