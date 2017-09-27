package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import singleton.Project;

public class AnalysisResult {

	static int buildfailed=0;
	static int buildsuccess=0;
	static int number=0;
	static float time=0;
	static boolean start=false;
	public static void main(String[] args) {
		Project.getInstance().setSelectProject(
				"F:/EspressoExamples-master/EspressoExamples-master");

		try {
			// read file content from file
			StringBuffer sb = new StringBuffer("");

			FileReader reader = new FileReader(Project.getInstance()
					.getRunTestLogPath());
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			while ((str = br.readLine()) != null) {
				if(str.startsWith(":app"))
				{
					skip();
				}
				else 
				{
					if(str.startsWith("#"))
					{
						number++;
						System.out.print(str);
					}
					
					Pattern p1=Pattern.compile("Total time: (.*?) secs");
					Matcher m1 = p1.matcher(str);
					if(m1.find())
					{
						String t=m1.group(1);
						if(t.indexOf(" mins ")>0)
						{
							float min= Float.parseFloat(t.substring(0,t.indexOf(" mins ")));
							float sec=Float.parseFloat(t.substring(t.indexOf("mins ")+5));
//						s	System.out.print(t+"mmmm"+min+"mmm"+sec);
							time+=min*60+sec;
						}
						else
						{
							time+=Float.parseFloat(t);
						}
						
					}
					
					Pattern pattern = Pattern.compile("Starting.*tests on",Pattern.CASE_INSENSITIVE);
				
					Matcher matcher = pattern.matcher(str);
					if(matcher.find())
					{
						start=true;
						System.out.println("start-----------------------------------------------------------------\n");
					}
					if(start)
					{
						if(str.startsWith("BUILD"))
						{
							if(str.equals("BUILD FAILED"))
							{
								buildfailed++;
							}
							else if(str.equals("BUILD SUCCESSFUL"))
							{
								buildsuccess++;
							}
							start=false;
							sb.append(str + "/n");
							System.out.println(str);
							System.out.println("-----------------------------------------------------------------end\n");
						}
						else
						{
							sb.append(str + "/n");
							System.out.println(str);
							
						}
						
						
					}
					
				}
				
			}
			br.close();
			reader.close();
			// write string to file
			FileWriter writer = new FileWriter("F://test2.txt");
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(sb.toString());

			bw.close();
			writer.close();
			float hour= time/3600;
			float min=time/60%60;
			float sec=(float)(int)((time%60)*100)/100;
			
			
//			System.out.println(time);
			float a=(float)(buildfailed+buildsuccess)/number;
			float b=(float)buildfailed/(buildfailed+buildsuccess);
			
			
			System.out.println("一共测试了"+number+"个变异体程序，其中构建成功并测试的有"+(buildfailed+buildsuccess)+"个,比例为"+(float)(int)(a*10000)/100+"%");
			System.out.print("测试用例检测出来："+buildfailed+"个");
			System.out.print("未检测出来："+buildsuccess+"个");
			System.out.println("变异得分为"+(float)(int)(b*10000)/100);
	
			System.out.println("用时："+(int)hour+" h "+(int)min+" min "+sec+" sec . ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static void skip() {
		// TODO Auto-generated method stub
		
	}

}
