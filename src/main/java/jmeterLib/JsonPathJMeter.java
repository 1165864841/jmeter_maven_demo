package jmeterLib;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;


/**
 * 
 * TODO:解析json文件，类似jmeter中是json提取器
 *
 * @author Joe-Tester
 * @time 2021年5月28日
 * @file JsonPathJMeter.java
 */
public class JsonPathJMeter {

	/**
	 * 读取json格式的文本文件转成String
	 * 
	 * @param json_file_path
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readtxt(String json_file_path) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		try {
			FileReader fr = new FileReader(json_file_path);
			BufferedReader bfd = new BufferedReader(fr);
			String s = "";
			while ((s = bfd.readLine()) != null) {
				sb.append(s);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 读取json的一种写法，得到匹配表达式的所有值
	 * 
	 * @param json
	 * @param jsonExp
	 * @return
	 */
	public static List<String> getListJsonValue(String json, String jsonExp) {
		// TODO Auto-generated method stub
		List<String> authors = JsonPath.read(json, jsonExp);
		// System.out.println(authors.size());
		print("**************得到匹配表达式:" + jsonExp + "的所有值**************");
		// print(authors);
		return authors;
	}

	/**
	 * 读取json的一种写法，得到某个具体值
	 * 
	 * @param json
	 * @param jsonExp
	 * @return
	 */
	public static String getOnlyOneJsonValue(String json, String jsonExp) {
		Object document = Configuration.defaultConfiguration().jsonProvider()
				.parse(json);
		try {
			String author0 = JsonPath.read(document, jsonExp);
			print("**************得到匹配表达式:" + jsonExp + "的唯一值**************");
			return author0;
		} catch (Exception e) {
			print(e.getMessage());
		}
		return jsonExp + "表达式正确";

	}

	/**
	 * 封装一个syso,笑cry
	 * 
	 * @param s
	 */
	public static void print(String s) {
		System.out.println(s);
	}
}