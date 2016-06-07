package com.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.htmlparser.Node;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.ParserException;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class StringUtils {

	/**
	 * 判断一个字符串是否为null 或""或"  "
	 */
	public static boolean IsNullOrSpace(String value) {
		if (value == null || "".equals(value)) {
			return true;
		}
		if (value.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 字符串是否有长度
	 */
	public static boolean HasLength(String value){
		if(null == value || "".equals(value.trim())){
			return false;
		}else{
			return true;
		}
	}
	
	/*
	 * 对象转换成int型
	 */
	public static int objectToInt(Object obj){
		int result = 0;
		if(null == obj || "".equals(obj)){
			return 0;
		}
		try{
			result = Integer.parseInt(obj.toString());
		}catch(Exception e){
			
		}
		return result;
	}
	
	/*
	 * 从字符串转换为bool值
	 */
	public static boolean getBool(String strBool)
	{
		if (StringUtils.IsNullOrSpace(strBool))
			return false;
		else
		{
			strBool = strBool.trim().toUpperCase();
			if (strBool.equals("1") || strBool.equals("TRUE") || strBool.equals("是") || strBool.equals("YES"))
				return true;
			else
				return false;
		}
	}

	/*
	 * 判断一个字符串是否为null 或""或"  "
	 */
	public static boolean IsNullOrEmpty(String value) {
		if (value == null || "".equals(value)) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 判断一个字符串是否为null 或""或"  "
	 */
	public static boolean IsNullOrEmpty(Object value) {
		if (value == null || "".equals(value.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 向左边添加指定的字符
	 */
	public static String PadLeft(String str, int padLength, char padLeftStr) {
		if(str.length() < padLength){
			int padLengthIng = padLength - str.length();
			for(int i=0;i<padLengthIng;i++){
				str = padLeftStr + str;
			}
		}
		return str;
	}

	/*
	 * 向左边添加指定的字符
	 */
	public static String PadRight(String str, int padLength, char padLeftStr) {
		if(str.length() < padLength){
			int padLengthIng = padLength - str.length();
			for(int i=0;i<padLengthIng;i++){
				str = str+padLeftStr;
			}
		}
		return str;
	}
	
	/**
	 * 处理url
	 * 
	 * url为null返回null，url为空串或以http://或https://开头，则加上http://，其他情况返回原参数。
	 * 
	 * @param url
	 * @return
	 */
	public static String handelUrl(String url) {
		if (url == null) {
			return null;
		}
		url = url.trim();
		if (url.equals("") || url.startsWith("http://")
				|| url.startsWith("https://")) {
			return url;
		} else {
			return "http://" + url.trim();
		}
	}

	/**
	 * 分割并且去除空格
	 * 
	 * @param str
	 *            待分割字符串
	 * @param sep
	 *            分割符
	 * @param sep2
	 *            第二个分隔符
	 * @return 如果str为空，则返回null。
	 */
	public static String[] splitAndTrim(String str, String sep, String sep2) {
		if (org.apache.commons.lang.StringUtils.isBlank(str)) {
			return null;
		}
		if (!org.apache.commons.lang.StringUtils.isBlank(sep2)) {
			str = org.apache.commons.lang.StringUtils.replace(str, sep2, sep);
		}
		String[] arr = org.apache.commons.lang.StringUtils.split(str, sep);
		// trim
		for (int i = 0, len = arr.length; i < len; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}

	/**
	 * 文本转html
	 * 
	 * @param txt
	 * @return
	 */
	public static String txt2htm(String txt) {
		if (org.apache.commons.lang.StringUtils.isBlank(txt)) {
			return txt;
		}
		StringBuilder sb = new StringBuilder((int) (txt.length() * 1.2));
		char c;
		boolean doub = false;
		for (int i = 0; i < txt.length(); i++) {
			c = txt.charAt(i);
			if (c == ' ') {
				if (doub) {
					sb.append(' ');
					doub = false;
				} else {
					sb.append("&nbsp;");
					doub = true;
				}
			} else {
				doub = false;
				switch (c) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					sb.append("&quot;");
					break;
				case '\n':
					sb.append("<br/>");
					break;
				default:
					sb.append(c);
					break;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 剪切文本。如果进行了剪切，则在文本后加上"..."
	 * 
	 * @param s
	 *            剪切对象。
	 * @param len
	 *            编码小于256的作为一个字符，大于256的作为两个字符。
	 * @return
	 */
	public static String textCut(String s, int len, String append) {
		if (s == null) {
			return null;
		}
		int slen = s.length();
		if (slen <= len) {
			return s;
		}
		// 最大计数（如果全是英文）
		int maxCount = len * 2;
		int count = 0;
		int i = 0;
		for (; count < maxCount && i < slen; i++) {
			if (s.codePointAt(i) < 256) {
				count++;
			} else {
				count += 2;
			}
		}
		if (i < slen) {
			if (count > maxCount) {
				i--;
			}
			if (!org.apache.commons.lang.StringUtils.isBlank(append)) {
				if (s.codePointAt(i - 1) < 256) {
					i -= 2;
				} else {
					i--;
				}
				return s.substring(0, i) + append;
			} else {
				return s.substring(0, i);
			}
		} else {
			return s;
		}
	}

	public static String htmlCut(String s, int len, String append) {
		String text = html2Text(s, len * 2);
		return textCut(text, len, append);
	}

	public static String html2Text(String html, int len) {
		try {
			Lexer lexer = new Lexer(html);
			Node node;
			StringBuilder sb = new StringBuilder(html.length());
			while ((node = lexer.nextNode()) != null) {
				if (node instanceof TextNode) {
					sb.append(node.toHtml());
				}
				if (sb.length() > len) {
					break;
				}
			}
			return sb.toString();
		} catch (ParserException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param keyword 源词汇
	 * @param smart 是否智能分词
	 * @return 分词词组(,拼接)
	 */
	public static String getKeywords(String keyword, boolean smart) {
		StringReader reader = new StringReader(keyword);
		IKSegmenter iks = new IKSegmenter(reader, smart);
		StringBuilder buffer = new StringBuilder();
		try {
			Lexeme lexeme;
			while ((lexeme = iks.next()) != null) {
				buffer.append(lexeme.getLexemeText()).append(',');
			}
		} catch (IOException e) {
		}
		//去除最后一个,
		if (buffer.length() > 0) {
			buffer.setLength(buffer.length() - 1);
		}
		return buffer.toString();
	}

	/**
	 * 检查字符串中是否包含被搜索的字符串。被搜索的字符串可以使用通配符'*'。
	 * 
	 * @param str
	 * @param search
	 * @return
	 */
	public static boolean contains(String str, String search) {
		if (org.apache.commons.lang.StringUtils.isBlank(str) || org.apache.commons.lang.StringUtils.isBlank(search)) {
			return false;
		}
		String reg = org.apache.commons.lang.StringUtils.replace(search, "*", ".*");
		Pattern p = Pattern.compile(reg);
		return p.matcher(str).matches();
	}

	public static boolean containsKeyString(String str) {
		if (org.apache.commons.lang.StringUtils.isBlank(str)) {
			return false;
		}
		if (str.contains("'") || str.contains("\"") || str.contains("\r")
				|| str.contains("\n") || str.contains("\t")
				|| str.contains("\b") || str.contains("\f")) {
			return true;
		}
		return false;
	}
	/**
	 * 统计子串出现次数
	 * @param str
	 * @param search
	 * @return
	 */
	public static int subCounter(String str, String search) {
		 
        int counter = 0;
        for (int i = 0; i <= str.length() - search.length(); i++) {
            if (str.substring(i, i + search.length()).equalsIgnoreCase(search)) {
                counter++;
            }
        }
        return counter;
    }
	/**
	 * 将""和'转义
	 * @param str
	 * @return
	 */
	public static String replaceKeyString(String str) {
		if (containsKeyString(str)) {
			return str.replace("'", "\\'").replace("\"", "\\\"").replace("\r",
					"\\r").replace("\n", "\\n").replace("\t", "\\t").replace(
					"\b", "\\b").replace("\f", "\\f");
		} else {
			return str;
		}
	}
	
	public static String getSuffix(String str) {
		int splitIndex = str.lastIndexOf(".");
		return str.substring(splitIndex + 1);
	}
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		if(request==null){
			return "";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}