package serialzation;

import java.io.*;

public abstract class ToXml
{
	protected String rootProjectDir;
	protected String settingXmlName;
	protected PrintWriter writer;
	public ToXml()
	{}
	public static void run(ToXml rpd, String[] args) 
	{
		try 
		{
			rpd.run(args);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public String run(String[] args) throws Exception 
	{
		if (args.length < 1) 
		{
			printUsage("args[0]:项目根目录");
			
		}
		writer = createPrintWriter(null);
		process(args[0]);
		
		return null;
	}
	
	protected void process(String rootProject) throws Exception 
	{
		getPrintWriter(null).println(rootProject);
		getPrintWriter(null).flush();
	}
	
	protected String checkFile(String arg)
	{
		return arg;
	}
	protected PrintWriter getPrintWriter(String xmlPath) throws Exception
	{
		 if (writer == null) 
		 {
			 writer = createPrintWriter(xmlPath);	 
		 }
		 return writer;
	 }
	 
	 protected PrintWriter createPrintWriter(String xmlPath) throws Exception
	 {
		 return new PrintWriter(System.out);	 
	 }
	 protected void printUsage(String text)
	 {
	        println("Usage: java " + getClass().getName() + " " + text);
	 }
	 protected void print(String text) 
	 {
	        System.out.print(text);
	 }
	 protected void println(String text)
	 {
	        System.out.println(text);
	 }
	public static void main(String[] args) {
	
	} 
}
