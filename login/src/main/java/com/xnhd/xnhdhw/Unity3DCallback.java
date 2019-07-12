
package com.xnhd.xnhdhw;

import org.json.JSONObject;

import com.unity3d.player.UnityPlayer;

public class Unity3DCallback {
	
	public static final String JSON_Zone_ID = "zoneId";
	public static final String JSON_Zone_Name = "zoneName";
	
	public static final String JSON_Role_ID = "roleId";	
	public static final String JSON_Role_Name = "roleName";
	public static final String JSON_Role_CreateTime = "roleCTime";	
	public static final String JSON_Role_Level = "roleLevel";
	
    public static final String JSON_Role_IsNew = "roleIsNew";

    public static final String JSON_Role_VIPLevel = "roleVIPLevel";    

    public static final String JSON_Role_Balance = "roleBalance";

    public static final String JSON_Guild_Name = "guildName";
	
	public static final String JSON_Pay_Amount = "payAmount";
	public static final String JSON_Product_ID = "productID";
	public static final String JSON_Product_Name = "productName";
	
	public static final String CALLBACKTYPE_Login = "login";
	public static final String CALLBACKTYPE_Logout = "logout";
	public static final String CALLBACKTYPE_SwitchAccount = "switch_account";
	public static final String CALLBACKTYPE_Pay = "pay";
	
	public static final String CALLBACKTYPE_UnityFunc = "unityfunc";
	
	public static final String CALLBACKTYPE_IpChanged = "ipchanged";
	public static final String CALLBACKTYPE_BatteryChanged = "batterychanged";
	
	public static final String CALLBACKTYPE_CropPhoto = "cropphoto";
	public static final String CALLBACKTYPE_UploadPhoto = "uploadphoto";
	public static final String CALLBACKTYPE_DeletePhoto = "deletephoto";
	public static final String CALLBACKTYPE_QueryPhoto = "queryphoto";	
	public static final String CALLBACKTYPE_DownloadPhoto = "downloadphoto";
	
	public static final String CALLBACKTYPE_UploadFile = "uploadfile";
	public static final String CALLBACKTYPE_DeleteFile = "deletefile";
	public static final String CALLBACKTYPE_QueryFile = "queryfile";	
	public static final String CALLBACKTYPE_DownloadFile = "downloadfile";
	
	public static final int BATTERY_LOW = 0;
	public static final int BATTERY_OK = 1;
	
	public static void unity3dSendMessage(String json)
	{
		
		UnityPlayer.UnitySendMessage("SDKManager", "Callback", json);
	}
	
	public static void callback(String callbackType, ErrorCode code, Object data)
	{
		try {
			JSONObject jobj = new JSONObject();
			jobj.put("callbackType", callbackType);
			jobj.put("code", code.getIndex());
			jobj.put("data", data);
			
			unity3dSendMessage(jobj.toString());
		} catch (Throwable e) {
			e.printStackTrace();
			
		}		
	}	
	
	public static void doLoginResultCallback(ErrorCode code, String token, String openId, String displayName, String email)
	{
		try {
			JSONObject jdata = new JSONObject();
			jdata.put("token", token);
			jdata.put("openId", openId);
			jdata.put("displayName", displayName);
			jdata.put("email", email);
			
			callback(CALLBACKTYPE_Login, code, jdata);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}
	
	public static void doSwtichAccountResultCallback(ErrorCode code, String token, String publisher, String platformId, String openId)	
	{
		try {
			JSONObject jdata = new JSONObject();
			jdata.put("token", token);
			jdata.put("publisher", publisher);
			jdata.put("platformId", platformId);
			jdata.put("openId", openId);
			
			callback(CALLBACKTYPE_SwitchAccount, code, jdata);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}
	
	public static void doLogoutResultCallback(ErrorCode code)	
	{
		try {

			callback(CALLBACKTYPE_Logout, code, "");
			
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}

	public static void doUnityfuncCallback(ErrorCode code,JSONObject jdata)

	{
		try {
		
			callback(CALLBACKTYPE_UnityFunc, code, jdata);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public static void notifyIPChanged(String preIP, String currentIP) {
		try {

			JSONObject jdata = new JSONObject();
			jdata.put("preIP", preIP);
			jdata.put("currentIP", currentIP);
			
			callback(CALLBACKTYPE_IpChanged, ErrorCode.SUCCESS, jdata);
			
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
	public static void notifyBatteryChanged(int state) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("state", state);
			
			callback(CALLBACKTYPE_IpChanged, ErrorCode.SUCCESS, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}	
		
	public static void cropPhoto(ErrorCode code, final String mediaName, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("name", mediaName);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_CropPhoto, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
	public static void uploadPhoto(ErrorCode code, final String fileId, final String fileUrl, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("fileUrl", fileUrl);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_UploadPhoto, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}

	public static void deletePhoto(ErrorCode code, final String fileId, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_DeletePhoto, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
	public static void queryPhoto(ErrorCode code, final String fileId, final String content, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("content", content);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_QueryPhoto, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
	public static void downloadPhoto(ErrorCode code, final String fileId, final String path, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("filePath", path);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_DownloadPhoto, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	/**
	 * �ļ�
	 */
	public static void uploadFile(ErrorCode code, final String fileId, final String fileUrl, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("fileUrl", fileUrl);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_UploadFile, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	public static void deleteFile(ErrorCode code, final String fileId, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_DeleteFile, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	
	public static void queryFile(ErrorCode code, final String fileId, final String content, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("content", content);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_QueryFile, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
	public static void downloadFile(ErrorCode code, final String fileId, final String path, final String extraData) {
		try {
			
			JSONObject jdata = new JSONObject();
			jdata.put("fileId", fileId);
			jdata.put("filePath", path);
			jdata.put("extraData", extraData);
			
			callback(CALLBACKTYPE_DownloadFile, code, jdata);
		} catch (Throwable e) {
			e.printStackTrace();
		}	
	}
}
