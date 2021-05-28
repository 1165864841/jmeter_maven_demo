package jmeterLib;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * TODO:后置处理器，追加文件
 *
 * @author Joe-Tester
 * @time 2021年5月28日
 * @file AppendFile.java
 */
public class AppendFile {

	/**
	 * @param filePath
	 *            :文件路徑
	 * @param content
	 *            ：写入文件的内容
	 * @throws IOException
	 */
	public void appendFile(String filePath, String content) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(filePath, true); // true 换行追加记录,false每次执行替换
			content = content + "\n";
			// if (content.contains(",")) {
			// content = "\"" + content + "\"";
			// }
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}