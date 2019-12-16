/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.project.model 
 * @author: cmj   
 * @date: 2019年7月5日 下午3:42:11 
 */
package com.yizutiyu.brightbeacon.face;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: FaceV3DetectBean.java
* @Description: 该类的功能描述
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午3:42:11 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class MHFaceModel {
	
	  private String log_id;
	  private String error_msg;
	  private String cached;
	  private String error_code;
	  private String timestamp;
	  private FaceResult result;
	  
	  
	/**
	 * @return the log_id
	 */
	public String getLog_id() {
		return log_id;
	}
	/**
	 * @param log_id the log_id to set
	 */
	public void setLog_id(String log_id) {
		this.log_id = log_id;
	}
	/**
	 * @return the error_msg
	 */
	public String getError_msg() {
		return error_msg;
	}
	/**
	 * @param error_msg the error_msg to set
	 */
	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	/**
	 * @return the cached
	 */
	public String getCached() {
		return cached;
	}
	/**
	 * @param cached the cached to set
	 */
	public void setCached(String cached) {
		this.cached = cached;
	}
	/**
	 * @return the error_code
	 */
	public String getError_code() {
		return error_code;
	}
	/**
	 * @param error_code the error_code to set
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/**
	 * @return the result
	 */
	public FaceResult getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(FaceResult result) {
		this.result = result;
	}
	  
	  
	
	/**
	 * {
  "result": {
    "face_num": 1,
    "face_list": [{
      "glasses": {
        "probability": 1,
        "type": "none"
      },
      "expression": {
        "probability": 1,
        "type": "none"
      },
      "face_shape": {
        "probability": 0.49,
        "type": "round"
      },
      "beauty": 43.87,
      "gender": {
        "probability": 1,
        "type": "male"
      },
      "race": {
        "probability": 1,
        "type": "yellow"
      },
      "angle": {
        "roll": -17.7,
        "pitch": 24.32,
        "yaw": -2.63
      },
      "face_token": "d9cbb293c57622d44b5aa33f9164de89",
      "location": {
        "top": 190.47,
        "left": 129.31,
        "rotation": -13,
        "width": 82,
        "height": 73
      },
      "face_probability": 0.98,
      "age": 23
    }]
  },
  "log_id": 305486823133035141,
  "error_msg": "SUCCESS",
  "cached": 0,
  "error_code": 0,
  "timestamp": 1562313303
}
	 */

}
