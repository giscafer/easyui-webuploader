package com.upload;

/**
 *  附件表pojo
 */
public class FileDocument {

	/**
	 * 文档大小
	 */
	public  String D_UPSIZE = "";
	
	/**
	 * 文档名称
	 */
	public String S_NAME = "";
	
	/**
	 * 上传时间
	 */
	public String S_UPTIME = "";
	
	/**
	 * 文档类型
	 */
	public String S_TYPE = "";
	
	/**
	 * 上传IP
	 */
	public String S_UPIP = "";
	
	/**
	 * 上传人
	 */
	public String S_UPUSER = "";
	
	/**
	 * 文档URL
	 */
	public String S_URL = "";
	
	/**
	 * 逻辑目录
	 */
	public String S_LPATH = "";
	
	/**
	 * 父目录
	 */
	public String S_PLPATH = "";
	
	/**
	 * 机构级别
	 */
	public String I_JB = "";
	
	
	/**
	 * 图片缩略图路径
	 */
	public String S_THUMBNAIL = "";
	
	public FileDocument() {
		super();
	}

	public FileDocument(String d_UPSIZE, String s_NAME, String s_UPTIME,
			String s_TYPE, String s_UPIP, String s_UPUSER, String s_URL,
			String s_LPATH, String s_PLPATH, 
			String s_THUMBNAIL) {
		D_UPSIZE = d_UPSIZE;
		S_NAME = s_NAME;
		S_UPTIME = s_UPTIME;
		S_TYPE = s_TYPE;
		S_UPIP = s_UPIP;
		S_UPUSER = s_UPUSER;
		S_URL = s_URL;
		S_LPATH = s_LPATH;
		S_PLPATH = s_PLPATH;
		S_THUMBNAIL = s_THUMBNAIL;
	}

	public String getD_UPSIZE() {
		return D_UPSIZE;
	}

	public void setD_UPSIZE(String d_UPSIZE) {
		D_UPSIZE = d_UPSIZE;
	}

	public String getS_NAME() {
		return S_NAME;
	}

	public void setS_NAME(String s_NAME) {
		S_NAME = s_NAME;
	}

	public String getS_UPTIME() {
		return S_UPTIME;
	}

	public void setS_UPTIME(String s_UPTIME) {
		S_UPTIME = s_UPTIME;
	}

	public String getS_TYPE() {
		return S_TYPE;
	}

	public void setS_TYPE(String s_TYPE) {
		S_TYPE = s_TYPE;
	}

	public String getS_UPIP() {
		return S_UPIP;
	}

	public void setS_UPIP(String s_UPIP) {
		S_UPIP = s_UPIP;
	}

	public String getS_UPUSER() {
		return S_UPUSER;
	}

	public void setS_UPUSER(String s_UPUSER) {
		S_UPUSER = s_UPUSER;
	}

	public String getS_URL() {
		return S_URL;
	}

	public void setS_URL(String s_URL) {
		S_URL = s_URL;
	}

	public String getS_LPATH() {
		return S_LPATH;
	}

	public void setS_LPATH(String s_LPATH) {
		S_LPATH = s_LPATH;
	}

	public String getS_PLPATH() {
		return S_PLPATH;
	}

	public void setS_PLPATH(String s_PLPATH) {
		S_PLPATH = s_PLPATH;
	}

	public String getI_JB() {
		return I_JB;
	}

	public void setI_JB(String i_JB) {
		I_JB = i_JB;
	}

	public String getS_THUMBNAIL() {
		return S_THUMBNAIL;
	}

	public void setS_THUMBNAIL(String s_THUMBNAIL) {
		S_THUMBNAIL = s_THUMBNAIL;
	}
}