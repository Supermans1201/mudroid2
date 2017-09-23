package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import singleton.FileList;
import singleton.InheritanceRelation;


public class ReadFile  {
	 FileInputStream fileInputStream;
	 InputStream inputStream;
	 InputStreamReader ir ;
	 BufferedReader in ;
	 
	 File fcopy;
	 PrintWriter pw;
	 
	 List<String> list=new ArrayList<String>();
	 
	 
	 public ReadFile(){
	 }
	 public static void main(String[] args) throws FileNotFoundException
	 {
		 args=new String[]{"F:/muandroid3/AndroidApp-master"};
		 File f=new File(args[0]+"/app/out.txt");
		 ReadFile rf=new ReadFile(f);
		 System.out.println("run");
		 rf.run();
		 System.out.println("down");
		 
		 FileList.getInstance().setJarList(rf.list);
		 FileList.getInstance().readJarList();
	 }
	 public ReadFile(File f) throws FileNotFoundException{
		  fileInputStream = new  FileInputStream(f);
		  inputStream = fileInputStream;
		  ir = new InputStreamReader(inputStream); 
	     in = new BufferedReader(ir); 
	      
	     fcopy=new File(f.getAbsolutePath()+".copy");
	     pw=new PrintWriter(fcopy);
	  
	 }
	 
	 public void run() {
	 
	    try {
	    	pw.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			String s=in.readLine();
		//	if(s!=null&&s.indexOf(":app")>0)
			while(s!=null)
			{
				s=in.readLine();
				
				if(s!=null&&s.indexOf("Cached result for getItem")>=0)
				{
					String temp=s.substring(s.indexOf("(")+1,s.indexOf(")"));
					System.out.println(temp);
					listAddURL(temp);
				}
				if(s!=null&&s.indexOf("processing archive")>=0)
				{
					String temp=s.substring(s.lastIndexOf(":")-1,s.lastIndexOf("..."));
					System.out.println(temp);
					listAddURL(temp);
				}
				if(s!=null&&s.indexOf("Starting process")>=0)
				{
					//System.out.println(">>>>:"+s);
					if(s.indexOf("output")>=0)
						if(s.lastIndexOf("dx.jar")>=0)
					{
						String temp=s.substring(s.lastIndexOf(":")-1);
					///	System.out.println(">>>>:"+temp);
						if(temp.indexOf("pre-dexed")<0)
						{
					//	System.out.println(">>>>:222"+temp);
							listAddURL(temp);
						}
					}
//				{
//					System.out.println(">>>>:"+s.substring(s.indexOf("output")));
//					pw.flush();
//				}
				}
				if(s!=null&&s.indexOf("BUILD")>0)
					return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public  void addURL(String classPath) throws Exception 
	 {
	 	Method addClass = null;
	 	ClassLoader cl = null;
	 	File f = null;
	 	addClass = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] {URL.class});
	 	addClass.setAccessible(true);
	 	f = new File(classPath);
	 	cl = ClassLoader.getSystemClassLoader();
	 	addClass.invoke(cl, new Object[] { f.toURI().toURL() });
	 }
	 private void listAddURL(String temp) {
		//	System.out.println(">>>>>>>>>>>"+temp);
			list.add(temp);
			try {
				addURL(temp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
		}
}
