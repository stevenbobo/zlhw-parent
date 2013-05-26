package com.zb.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

/** */
/**
 * 读取properties文件
 * 
 * @author Qutr
 * 
 */
public class propertiesOperator {
	private Properties propertie;
	private FileInputStream inputFile;
	private FileOutputStream outputFile;
	private String filePath;// 属性文件路径

	/**
	 * 设置路径
	 * 
	 * @param path
	 */
	public void setPath(String filePath) {
		this.filePath = filePath;
	}

	/** */
	/**
	 * 初始化Configuration类
	 */
	public propertiesOperator() {
		propertie = new Properties();
	}

	/** */
	/**
	 * 初始化Configuration类
	 * 
	 * @param filePath
	 *            要读取的配置文件的路径+名称
	 */
	public propertiesOperator(String filePath) {
		this.filePath = filePath;
		propertie = new Properties();
		try {
			File file=new File(filePath);
			if(!file.exists()){//如果找不到路径，尝试在src目录下查找
				file=new File("src/beans.properties");
			}
			inputFile = new FileInputStream(file);
		} catch (FileNotFoundException ex) {
			System.out.println("读取属性文件--->失败！- 原因：文件路径错误或者文件不存在");
			ex.printStackTrace();
		}
		try {
			propertie.load(inputFile);
			inputFile.close();
		} catch (IOException ex) {
			System.out.println("装载文件--->失败!");
			ex.printStackTrace();
		}
		

	}// end ReadConfigInfo()

	/** */
	/**
	 * 重载函数，得到key的值
	 * 
	 * @param key
	 *            取得其值的键
	 * @return key的值
	 */
	public String getValue(String key) {
		if (propertie.containsKey(key)) {
			String value = propertie.getProperty(key);// 得到某一属性的值
			return value;
		} else
			return "";
	}// end getValue()

	public Set<Entry<Object, Object>> getAll() {
		Set<Entry<Object, Object>> set = propertie.entrySet();
		return set;
	}

	/** */
	/**
	 * 清除properties文件中所有的key和其值
	 */
	public void clear() {
		propertie.clear();
	}// end clear();

	/** */
	/**
	 * 改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
	 * 
	 * @param key
	 *            要存入的键
	 * @param value
	 *            要存入的值
	 */
	public void setValue(String key, String value) {
		propertie.setProperty(key, value);
	}// end setValue()

	/** */
	/**
	 * 将更改后的文件数据存入指定的文件中，该文件可以事先不存在。
	 * 
	 * @param fileName
	 *            文件路径+文件名称
	 * @param description
	 *            对该文件的描述
	 */
	public void saveFile() {
		try {
			outputFile = new FileOutputStream(filePath);
			propertie.store(outputFile, "save2");
			outputFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}// end saveFile()

	public static void main(String[] args) {
		propertiesOperator rc = new propertiesOperator(
				"./WebRoot/WEB-INF/classes/beans.properties");// 相对路径
		rc.setValue("name", "zb2");
		rc.setValue("dsfd", "fdd");
		rc.saveFile();
		//        
		//        
		// Configuration saveCf = new Configuration();
		// saveCf.setValue("min", "10");
		// saveCf.setValue("max", "1000");
		// saveCf.saveFile(".\config\save.perperties");

	}// end main()

}// end class ReadConfigInfo

