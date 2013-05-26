package com.zb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools implements Serializable{

    public static String firstUpcase(String str){
    	char[] ar=str.toCharArray();
    	int f=(int)ar[0];
    	if(f>=97&&f<=122)
    	ar[0]=(char) (ar[0]-32);
		return new String(ar);
    	
    }
    public static String firstLowcase(String str){
    	char[] ar=str.toCharArray();
    	int f=(int)ar[0];
    	if(f>=65&&f<=90)
    	ar[0]=(char) (ar[0]+32);
		return new String(ar);
    	
    }

	public static String getData() {
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = formater.format(date);
		return now;
	}

	public static String getDataByMD(String data) {
		String now = data.substring(5, 10);
		return now;
	}

	public static String separator(String url) {
		url += File.separator;
		return url;
	}

	public static String minPic(String basepic) {
		String minpic = "";
		int num = basepic.lastIndexOf(".");
		String temp = basepic.substring(num);
		String temp2 = basepic.substring(0, num);
		minpic += temp2 + "_min" + temp;
		return minpic;
	}

	public static String filterHtml(String s) {
		char c[] = s.toCharArray();
		char ch;
		StringBuffer buf = new StringBuffer();
		for (int i = 0, size = c.length; i < size; i++) {
			ch = c[i];
			if (ch == '"') {
				buf.append("&quot;");
			} else if (ch == '&') {
				buf.append("&amp;");
			} else if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else if (ch == '\n') {
				buf.append("<br>");
			} else if (ch == ' ') {
				buf.append("&nbsp;");
			} else {
				buf.append(ch);
			}
		}
		c = null;
		return buf.toString();
	}

	public static String md5(String str) {
		String result = "";
		if (null != str) {
			try {
				MessageDigest m = MessageDigest.getInstance("MD5");
				m.update(str.getBytes("UTF8"));
				byte s[] = m.digest();

				for (int i = 0; i < s.length; i++) {
					result += Integer.toHexString(
							(0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
				}
			} catch (Exception e) {
				e.printStackTrace();
				result = null;
			}
		}
		return result;
	}


	public static void main(String[] args) throws Exception {
//		File newFile=new File("test.txt");
//		System.out.println(newFile.getAbsolutePath());
//		ObjectOutputStream ooStream=new ObjectOutputStream(new FileOutputStream(newFile));
//		Object obj1=new Tools(1);
//		Object obj2=new Tools(2);
//		ooStream.writeObject(obj1);
//		ooStream.writeObject(obj2);
//		File newFile=new File("test.txt");
//		ObjectInputStream inStream=new ObjectInputStream(new FileInputStream(newFile));
//		boolean found=false;
//		Tools nextTool;
//		while((nextTool=(Tools)inStream.readObject())!=null) {
//			System.out.println(nextTool.a);
//		}
	}
}
