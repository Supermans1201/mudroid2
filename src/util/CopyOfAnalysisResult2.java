package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class CopyOfAnalysisResult2 {

	
	static int number = 0;
	
	static boolean start = false;
	static boolean findend=true;
	public static void main(String[] args) {
		try {
			// read file content from file
			StringBuffer sb = new StringBuffer("");

			FileReader reader = new FileReader(
					"F:/EspressoExamples-master/EspressoExamples-master/runTest.log");
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while ((str = br.readLine()) != null) {

				Pattern p1 = Pattern.compile("Total time: (.*?) secs");
				Matcher m1 = p1.matcher(str);
				if (str.startsWith("#")) {
					start = true;
					number++;
					if(!findend)
					{
						sb.append("<p>时间："+ null + "secs </p>\n");
						
						sb.append("</pre></div></div></tr></div>\n");
						findend=true;
					}
					// sb.append("<h3>"+str + "</h3>");
					sb.append("<div class=\"card-box\">"
							+ "<tr><div class=\"am-panel-hd\"> \n\t<h4 class=\"am-panel-title\" data-am-collapse=\"{parent: '#accordion', target: '#do-not-say-"
							+ number
							+ "'}\">\n\t\t"
							+ "<div><span class=\"badge  pull-left m-t-20  am-round\" style=\"color: #fff; background: #0e90d2;\">"
							+ number

							+ "</span>"
							+ "\t"
							+ "<Button type=\"button\" class=\"am-btn am-btn-success am-round am-text-secondary am-active\" >"
							
							+ str.substring(1).replace("\\", "/").split(" ")[0]
									+ "</Button>"
							
							+ "<Button type=\"button\" class=\"am-btn am-btn-default am-round am-text-secondary am-active\" >"
							+ str.substring(1).replace("\\", "/").split(" ")[1]
									+ "</button>"
									
									+"<p class=\"text-muted m-b-25\">"
							+ str.substring(1).replace("\\", "/").split(" ")[2]
									+"</p>"
							+ "</div>"
							+ "\n\t</h4>\n</div>\n<div id=\"do-not-say-"
							+ number
							+ "\" class=\"am-panel-collapse am-collapse\"> \n<div class=\"am-panel-bd\">");
					sb.append("<pre class\">");
					// System.out.print(str);
				}

				if (m1.find()) {
					String t = m1.group(1);
					sb.append("</p>时间：" + t + "secs </p>\n");
					sb.append("</pre></div></div></tr></div>\n");
					findend=true;
				}
				
				if (start) {
					if (str.startsWith("BUILD")) {
						
						start = false;
						sb.append(str+"\n" );
						
					} else {
						sb.append(str+"\n");

					}

				}

			}
			br.close();
			reader.close();
			// write string to file

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());

			ve.init();
			Template t = ve.getTemplate("index45.vm");
			VelocityContext ctx = new VelocityContext();
			String gbk = sb.toString();
			System.out.println();
			ctx.put("s1", "日志原文");
			ctx.put("s2", "变异测试系统");
			ctx.put("s3", "变异测试结果");
			ctx.put("s4", "变异测试日志");
			ctx.put("s5", "总览信息");
			ctx.put("s6", "详细信息");
			ctx.put("s7", "日志原文");
			ctx.put("s8", "测试报告");
			ctx.put("stringbuffer", (gbk));
			StringWriter sw = new StringWriter();
			t.merge(ctx, sw);
			System.out.println(sw.toString());
			System.out.println(getEncoding(sw.toString()));
			File file = new File("F:/workspace/testgetdir/html/index4.html");
			file.delete();
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "UTF-8"));
			bw.write(sw.toString());
			bw.close();

			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void skip() {
		// TODO Auto-generated method stub

	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GB2312
				String s = encode;
				return s; // 是的话，返回“GB2312“，以下代码同理
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // 判断是不是GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // 如果都不是，说明输入的内容不属于常见的编码格式。
	}
}
