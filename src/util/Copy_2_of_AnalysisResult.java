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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class Copy_2_of_AnalysisResult {

	static int buildfailed = 0;
	static int buildsuccess = 0;
	static int number = 0;
	static float time = 0;
	static boolean start = false;
	static boolean findend=true;
	static List<Map<String, String>> datalist = new ArrayList();
	static Map<String, String> data =null;

	public static void main(String[] args) {
		try {
			// read file content from file

			FileReader reader = new FileReader(
					"F:/EspressoExamples-master/EspressoExamples-master/runTest.log");
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			String[] info = null;

			while ((str = br.readLine()) != null) {

				
				if (str.startsWith(":app")) {
					skip();
				} else {
					if (str.startsWith("#")) {
						number++;
						if (!findend) {
							data.put("time", null);
							datalist.add(data);
							findend = true;
						}
						data= new HashMap<String,String>();
						findend = false;
						data.put("id", number+"");
						info = str.substring(1).split(" ");
						data.put("name", info[0]);
						data.put("op", info[1]);
						// System.out.println(info[2]);
						if (info[2].indexOf("androidOp") >= 0) {
							data.put("optype", "androidOp");
						}
						if (info[2].indexOf("classOp") >= 0) {
							data.put("optype", "classOp");
						}
						if (info[2].indexOf("traditionalOp") >= 0) {
							data.put("optype", "traditionalOp");
						}
						if (info[2].indexOf("exceptionOp") >= 0) {
							data.put("optype", "exceptionOp");
						}
						if (info[2].indexOf("xmlOp") >= 0) {
							data.put("optype", "xmlOp");
						}
						data.put("condition", "0");
						
					}

					Pattern p1 = Pattern.compile("Total time: (.*?) secs");
					Matcher m1 = p1.matcher(str);
					if (m1.find()) {
						String t = m1.group(1);
						data.put("time", t);
						datalist.add(data);
						
						findend = true;
						
						if (t.indexOf(" mins ") > 0) {
							float min = Float.parseFloat(t.substring(0,
									t.indexOf(" mins ")));
							float sec = Float.parseFloat(t.substring(t
									.indexOf("mins ") + 5));
							// s System.out.print(t+"mmmm"+min+"mmm"+sec);
							time += min * 60 + sec;
						} else {
							time += Float.parseFloat(t);
						}
					}

					Pattern pattern = Pattern.compile("Starting.*tests on",
							Pattern.CASE_INSENSITIVE);

					Matcher matcher = pattern.matcher(str);
					if (matcher.find()) {
						start = true;

					} else {
						
					}
					if (start) {
						if (str.startsWith("BUILD")) {
							if (str.equals("BUILD FAILED")) {
								data.put("condition", "1");
								buildfailed++;
							} else if (str.equals("BUILD SUCCESSFUL")) {
								data.put("condition", "2");
								buildsuccess++;
							}
							start = false;
						

						} 

					}
					
					
				}
				
			}
			br.close();
			reader.close();

			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class",
					ClasspathResourceLoader.class.getName());

			ve.init();
			Template t = ve.getTemplate("index2.vm");
			VelocityContext ctx = new VelocityContext();

			System.out.println();
			ctx.put("datalist", datalist);
			 ctx.put("s1", "���Ա���");
			 ctx.put("s2", "�������ϵͳ");
			 ctx.put("s3", "������Խ��");
			 ctx.put("s4", "���������־");
			 ctx.put("s5", "������Ϣ");
			 ctx.put("s6", "��ϸ��Ϣ");
			 ctx.put("s7", "��־ԭ��");
			 ctx.put("s8", "���Ա���");
			 
			 
			 
			 ctx.put("ss1", "�ļ���");
			 ctx.put("ss2", "������������");
			 ctx.put("ss3", "��������");
			 ctx.put("ss4", "ʱ��");
			 ctx.put("ss5", "�Ƿ�ִ�гɹ�");
			 ctx.put("ss6", "����ʧ��");
			 ctx.put("ss7", "���Լ���");
			 ctx.put("ss8", "����δ����");
			 
			StringWriter sw = new StringWriter();

			t.merge(ctx, sw);

			System.out.println(sw.toString());
			System.out.println(getEncoding(sw.toString()));
			;

			File indexfile = new File(
					"F:/workspace/testgetdir/html/index2.html");
			indexfile.delete();
			indexfile.createNewFile();

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(indexfile), "UTF-8"));

			bw.write(sw.toString());

			bw.close();

			float hour = time / 3600;
			float min = time / 60 % 60;
			float sec = (float) (int) ((time % 60) * 100) / 100;

			// System.out.println(time);
			float a = (float) (buildfailed + buildsuccess) / number;
			float b = (float) buildfailed / (buildfailed + buildsuccess);

		
			System.out.println("һ��������" + number + "��������������й����ɹ������Ե���"
					+ (buildfailed + buildsuccess) + "��,����Ϊ"
					+ (float) (int) (a * 10000) / 100 + "%");
			System.out.print("����������������" + buildfailed + "��");
			System.out.print("δ��������" + buildsuccess + "��");
			System.out.println("����÷�Ϊ" + (float) (int) (b * 10000) / 100);

			System.out.println("��ʱ��" + (int) hour + " h " + (int) min + " min "
					+ sec + " sec . ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getEncoding(String str) {
		String encode = "GB2312";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���GB2312
				String s = encode;
				return s; // �ǵĻ������ء�GB2312�������´���ͬ��
			}
		} catch (Exception exception) {
		}
		encode = "ISO-8859-1";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���ISO-8859-1
				String s1 = encode;
				return s1;
			}
		} catch (Exception exception1) {
		}
		encode = "UTF-8";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���UTF-8
				String s2 = encode;
				return s2;
			}
		} catch (Exception exception2) {
		}
		encode = "GBK";
		try {
			if (str.equals(new String(str.getBytes(encode), encode))) { // �ж��ǲ���GBK
				String s3 = encode;
				return s3;
			}
		} catch (Exception exception3) {
		}
		return ""; // ��������ǣ�˵����������ݲ����ڳ����ı����ʽ��
	}

	private static void skip() {
		// TODO Auto-generated method stub

	}

}
