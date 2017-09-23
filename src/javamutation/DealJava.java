package javamutation;

import java.io.PrintWriter;
import openjava.ptree.*;

public abstract class DealJava {
	protected PrintWriter writer;
	
	public DealJava()
	{
	}
	protected static void run(DealJava deal, String[] args)
	{
		try
		{
			deal.run(args);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    }

	
	
	public void run(String[] args) throws Exception
	{
		
		if (args.length < 1)
		{
			printUsage("no java CompilationUnit URL specified");
			return;
		}
		else if(args.length==1)
		{
			//如果只有文件传进来，执行所有变异操作
			writer = createPrintWriter();
			CompilationUnit compilationUnit = parse(args[0]);
			process(compilationUnit);
			getPrintWriter().println(args[0]);
			getPrintWriter().flush();
		}
		else if(args.length>=2)
		{//如果提供变异的类型就去执行相应操作只需两个参数
			writer = createPrintWriter();
			CompilationUnit compilationUnit = parse(args[0]);
			process(compilationUnit);
			getPrintWriter().println(args[0]);
			getPrintWriter().println(args[1]);
			getPrintWriter().flush();
		
		}
	}
	protected CompilationUnit parse(String javaFile) throws Exception 
	{
		
		getPrintWriter().println("parse");
		return null;
	}
	
	protected void process(CompilationUnit compilationUnit) throws Exception 
	{
		getPrintWriter().println("deal compilationUnit");
		
	}
	
	protected void print(String text) 
	{
		System.out.print(text);
	}
	protected void println(String text)
	{
		System.out.println(text);
	}
	protected void printUsage(String text) 
	{
		println("Usage: java " + getClass().getName() + " " + text);
	}
	protected PrintWriter getPrintWriter() throws Exception 
	{
		if (writer == null)
		{
			writer = createPrintWriter();
		}
		return writer;
	}
	protected PrintWriter createPrintWriter() throws Exception
	{
		return new PrintWriter(System.out);
	}

}
