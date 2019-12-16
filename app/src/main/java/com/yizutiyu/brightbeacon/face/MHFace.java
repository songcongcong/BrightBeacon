/**   
 * Copyright © 2019 mh615 Info.  All rights reserved.
 * 
 * 功能描述：
 * @Package: com.mh.face 
 * @author: cmj   
 * @date: 2019年7月5日 下午2:47:58 
 */
package com.yizutiyu.brightbeacon.face;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.FaceVerifyRequest;
import com.baidu.aip.face.MatchRequest;
import com.baidu.aip.util.Base64Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**   
* Copyright: Copyright (c) 2019 LanRu-Caifu
* 
* @ClassName: MHFace.java
* @Description: 该类的功能描述: 明辉人脸识别，利用百度技术
*
* @version: v1.0.0
* @author: 曹明杰
* @date: 2019年7月5日 下午2:47:58 
*
* Modification History:
* Date         Author          Version            Description
*---------------------------------------------------------*
* 2019年7月5日     cmj           v1.0.0               修改原因
*/
public class MHFace {
	
	
	
//	//设置APPID/AK/SK
//    public static final String APP_ID = "16706708";
//    public static final String API_KEY = "34fjYKE8WX6IsGeW8vtHoDU2";
//    public static final String SECRET_KEY = "dq0lzmC6FcjUyiEL2NYZGORPGAH6yF4v";

    public static final String imageType = "BASE64";


	//设置APPID/AK/SK
	private final static  String APP_ID = "17023169";
	private final static  String API_KEY = "RLMflwnbOY0F0ihYdXgnGKSe";
	private final  static  String SECRET_KEY = "0XCoRQcfSvawEaNi3OCrOlm1FNEeZHk0";


	static AipFace client = null;
	static {
		client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(200000);
		client.setSocketTimeoutInMillis(6000000);

	}

	private static JSONObject search;

	//	public static void main(String[] args) throws IOException {
//		 String filePath = "F:\\caomingjie.jpg";
//		 byte[] imgData = FileToByte(new File(filePath));
//		 
//		 System.out.println(detectFace(imgData,"1"));
////		String filePath1 = "F:/3.jpg";
////		String filePath2 = "F:/7.jpg";
////		byte[] imgData1 = FileUtil.readFileByBytes(filePath1);
////		byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
////		System.out.println(faceverify(imgData1));
//	}
 
	/**   
	 * @Title: face2Bytes   
	 * @Description: TODO(作用：)   
	 * @param: @param file
	 * @param: @return
	 * @param: @throws IOException      
	 * @return: byte []  
	 * @Auther: cmj
	 * @throws   
	 */  
	public static  byte[] face2Bytes(ByteArrayOutputStream file){
		try {
//			//将数据转为流
//            FileInputStream content = new FileInputStream(file);
//            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
//            Log.d("song","数据转化为流："+file.getAbsolutePath());
//			byte[] buff = new byte[100];
//			int rc = 0;
//			while ((rc = content.read(buff, 0, 100)) > 0) {
//				swapStream.write(buff, 0, rc);
//			}
			//获得二进制数组
			byte[] in2b = file.toByteArray();
			return in2b;
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 人脸检测
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String detectFace(File file, String max_face_num) {
		try {
			return detectFace(FileToByte(file), max_face_num);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 人脸检测
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String detectFace(byte[] arg0, String max_face_num) {
		try {
 
			HashMap<String, String> options = new HashMap<String, String>();
			options.put("face_field", "age,beauty,expression,faceshape,gender,glasses,race,qualities");
			options.put("max_face_num", max_face_num);
			options.put("face_type", "LIVE");
 
			// 图片数据
			String imgStr = Base64Util.encode(arg0);
			String imageType = "BASE64";
			JSONObject res = client.detect(imgStr, imageType, options);
			System.out.println(res.toString(2));
			return res.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
    /**
     * 人脸比对
     * @param file1
     * @param file2
     * @return
     */
	public static String matchFace(File file1, File file2) {
		try {
			return matchFace(FileToByte(file1), FileToByte(file2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *
	 * @Title: detectUserIdByFace   
	 * @Description: TODO(作用：根据人脸查询用户Id)   
	 * @param: @return      
	 * @return: String  
	 * @Auther: cmj
	 * @throws
	 */
	public static JSONObject detectUserIdByFace(ByteArrayOutputStream image,String groupIdList) throws Exception {
		
		HashMap<String, String> options = new HashMap<String, String>();
	    options.put("max_face_num", "1");
	    options.put("match_threshold", "80");
	    options.put("quality_control", "NORMAL");
	    options.put("liveness_control", "LOW");
	    options.put("max_user_num", "1");
	    byte[] face2Bytes = face2Bytes(image);
		try {
			search = client.search(Base64Util.encode(face2Bytes)   , imageType, groupIdList, options);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return search;
	}
 
	/**
	 * 人脸比对
	 * 
	 * @param arg0
	 *            人脸1
	 * @param arg1
	 *            人脸2
	 * @return
	 */
	public static String matchFace(byte[] arg0, byte[] arg1) {
		String imgStr1 = Base64Util.encode(arg0);
		String imgStr2 = Base64Util.encode(arg1);
		MatchRequest req1 = new MatchRequest(imgStr1, "BASE64");
		MatchRequest req2 = new MatchRequest(imgStr2, "BASE64");
		ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		requests.add(req1);
		requests.add(req2);
		JSONObject res = client.match(requests);
		return res.toString();
	}
    
	/**
	 * 人脸搜索
	 * @param file
	 * @param groupIdList
	 * @param userId
	 * @return
	 */
	public static String searchFace(File file, String groupIdList, String userId) {
		try {
			return searchFace(FileToByte(file), groupIdList, userId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 人脸搜索
	 * 
	 * @param arg0
	 * @param groupIdList
	 * @return
	 */
	public static String searchFace(byte[] arg0, String groupIdList, String userId) {
		String imgStr = Base64Util.encode(arg0);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
		if (userId != null) {
			options.put("user_id", userId);
		}
		options.put("max_user_num", "1");
		JSONObject res = client.search(imgStr, imageType, groupIdList, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 增加用户
     * @param file
     * @param userInfo
     * @param userId
     * @param groupId
     * @return
     */
	public static String addUser(File file, String userInfo, String userId, String groupId) {
		try {
			return addUser(FileToByte(file), userInfo, userId, groupId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 增加用户
	 * 
	 * @param arg0
	 * @param userInfo
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public static String addUser(byte[] arg0, String userInfo, String userId, String groupId) {
		String imgStr = Base64Util.encode(arg0);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", userInfo);
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
 
		JSONObject res = client.addUser(imgStr, imageType, groupId, userId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 增加用户
	 *
	 * @param file
	 * @param userInfo
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public static String addUser(ByteArrayOutputStream file, String userInfo, String userId, String groupId) {
		byte[] bytes = MHFace.face2Bytes(file);
		String imgStr = Base64Util.encode(bytes);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", userInfo);
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");

		JSONObject res = client.addUser(imgStr, imageType, groupId, userId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 增加用户: 自动生成userid
	 *
	 * @param file
	 * @param userInfo
	 * @param groupId
	 * @return
	 */
	public static String addUser(ByteArrayOutputStream file, String userInfo, String groupId) {
		byte[] bytes = MHFace.face2Bytes(file);
		String imgStr = Base64Util.encode(bytes);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("user_info", userInfo);
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
		String userId = getUniqueSeq();
		JSONObject res = client.addUser(imgStr, imageType, groupId, userId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}



	public static String updateUser(File file, String userInfo, String userId, String groupId) {
		try {
			return updateUser(FileToByte(file), userInfo, userId, groupId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 
	/**
	 * 更新用户
	 * 
	 * @param arg0
	 * @param userInfo
	 * @param userId
	 * @param groupId
	 * @return
	 */
	public static String updateUser(byte[] arg0, String userInfo, String userId, String groupId) {
		String imgStr = Base64Util.encode(arg0);
		String imageType = "BASE64";
		HashMap<String, String> options = new HashMap<String, String>();
		if (userInfo != null) {
			options.put("user_info", userInfo);
		}
		options.put("quality_control", "NORMAL");
		options.put("liveness_control", "LOW");
 
		JSONObject res = client.updateUser(imgStr, imageType, groupId, userId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 删除用户人脸信息
     * @param userId
     * @param groupId
     * @param faceToken
     * @return
     */
	public static String deleteUserFace(String userId, String groupId, String faceToken) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 人脸删除
		JSONObject res = client.faceDelete(userId, groupId, faceToken, options);
		return res.toString();
	}
    /**
     * 查询用户信息
     * @param userId
     * @param groupId
     * @return
     */
	public static String searchUserInfo(String userId, String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 用户信息查询
		JSONObject res = client.getUser(userId, groupId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 获取用户人脸列表
     * @param userId
     * @param groupId
     * @return
     */
	public static String getUserFaceList(String userId, String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 获取用户人脸列表
		JSONObject res = client.faceGetlist(userId, groupId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 获取一组用户
     * @param groupId
     * @param returnNum
     * @return
     */
	public static String getGroupUsers(String groupId, String returnNum) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("start", "0");
		if (returnNum != null) {
			options.put("length", returnNum);
		}
		// 获取用户列表
		JSONObject res = client.getGroupUsers(groupId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 组用户复制
     * @param userId
     * @param srcGroupId
     * @param dstGroupId
     * @return
     */
	public static String userCopy(String userId, String srcGroupId, String dstGroupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("src_group_id", srcGroupId);
		options.put("dst_group_id", dstGroupId);
		// 复制用户
		JSONObject res = client.userCopy(userId, options);
		try {
			return res.toString(2);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
	}
    /**
     * 删除用户
     * @param userId
     * @param groupId
     * @return
     */
	public static String deleteUser(String userId, String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 人脸删除
		JSONObject res = client.deleteUser(groupId, userId, options);
		return res.toString();
	}
    /**
     * 增加组信息
     * @param groupId
     * @return
     */
	public static String addGroup(String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 创建用户组
		JSONObject res = client.groupAdd(groupId, options);
		return res.toString();
	}
    /**
     * 删除
     * @param groupId
     * @return
     */
	public static String deleteGroup(String groupId) {
		HashMap<String, String> options = new HashMap<String, String>();
		// 创建用户组
		JSONObject res = client.groupDelete(groupId, options);
		return res.toString();
	}
    /**
     * 获取组列表
     * @param length
     * @return
     */
	public static String getGroupList(String length) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("start", "0");
		options.put("length", length);
		// 组列表查询
		JSONObject res = client.getGroupList(options);
		return res.toString();
	}
	/**
	 * 活体检测
	 * @param arg0
	 * @return
	 */
	public static String faceverify(byte[] arg0){
		String imgStr = Base64Util.encode(arg0);
        String imageType = "BASE64";
        FaceVerifyRequest req = new FaceVerifyRequest(imgStr, imageType);
        ArrayList<FaceVerifyRequest> list = new ArrayList<FaceVerifyRequest>();
        list.add(req);
        JSONObject res = client.faceverify(list);
        return res.toString();
	}

	/**
	 * 检查活动分数
	 * @param file
	 * @return 直接返回活体检测分数
	 */
	public static double faceverifyDouble(ByteArrayOutputStream file){
		try{
			byte[] in2b = MHFace.face2Bytes(file);
			String faceverify = faceverify(in2b);
			JSONObject job = new JSONObject(faceverify);
			JSONObject result = job.getJSONObject("result");
			if(result==null){
				return 0.0;
			}
			String liveness = result.get("face_liveness").toString();
			double v = Double.parseDouble(liveness);
			return  v;
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0.00;
	}
 
	private static byte[] FileToByte(File file) throws IOException {
		// 将数据转为流
		InputStream content = new FileInputStream(file);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int rc = 0;
		while ((rc = content.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
		}
		// 获得二进制数组
		return swapStream.toByteArray();
	}

	/**
	 * 获取唯一序列
	 * @return
	 */
	public static String getUniqueSeq(){
		long nanoTimeStart = System.nanoTime();
		long nanoTimeEnd = System.nanoTime();
		nanoTimeStart = nanoTimeStart + nanoTimeEnd;
		StringBuffer sb = new StringBuffer();
		int i = UUID.randomUUID().toString().hashCode();
		int abs = Math.abs(i);
		sb.append(nanoTimeStart+"_");
		sb.append(abs);
        return sb.toString();
	}


}
