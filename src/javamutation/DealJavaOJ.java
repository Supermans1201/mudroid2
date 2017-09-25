package javamutation;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import mujava.openjava.extension.MemberAccessCorrector;
import openjava.mop.Environment;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.mop.OJSystem;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ParseTreeException;
import openjava.ptree.util.TypeNameQualifier;
import openjava.tools.parser.Parser;
import singleton.Op;


public class DealJavaOJ extends DealJava{
	String[] classOp=null;
	String[] traditionalOp=null;
	String[] exceptionOp=null;
	String[] androidOp=null;
	String op[];
	File file;
	
	List<String> jarList=new ArrayList<String>();
	CompilationUnit compilationUnit=null;
	FileEnvironment file_env = null;
	
	public void setJarsList(List<String> jarList)
	{
		this.jarList=jarList;
	}
	
	public DealJavaOJ() 
	{
	}
	
	public void run(String[] args) throws Exception
	{
		getPrintWriter().println(""+args[0]);
		if (args.length < 1)
		{
			printUsage("no java CompilationUnit URL specified");
			return;
		}
		else if(args.length>=1)
		{
			//如果只有文件传进来，执行所有变异操作
			if(Op.getInstance().getClassOp()!=null)
			classOp= (String [])Op.getInstance().getClassOp().toArray();
			if(Op.getInstance().getTradtionalOp()!=null)
			traditionalOp=(String[]) Op.getInstance().getTradtionalOp().toArray();
			if(Op.getInstance().getExceptionOp()!=null)
			exceptionOp=(String[]) Op.getInstance().getExceptionOp().toArray();
			if(Op.getInstance().getAndroidOp()!=null)
			androidOp=(String[]) Op.getInstance().getAndroidOp().toArray();
		}
		writer = createPrintWriter();
		file=new File(args[0]);
		CompilationUnit compilationUnit = parse(args[0]);
		if(compilationUnit==null)
			return;
		initParseTree();
		//System.out.println(compilationUnit);
		runOP(args[0]);
		process(compilationUnit);
		//对文档树整个处理
		getPrintWriter().flush();
		
	}
	
	protected void process(CompilationUnit compilationUnit) throws Exception 
	{
		
		getPrintWriter().println("deal compilationUnit2");
		
	}
	protected CompilationUnit parse(String javaFile) throws FileNotFoundException, ParseTreeException 
	{
		OJSystem.initConstants();
		Parser parser;	
		parser = new Parser(new FileInputStream(javaFile ));
		try {
			compilationUnit = parser.CompilationUnit(OJSystem.env );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.err.println("parse exception"+javaFile);
			return null;
			//e.printStackTrace();
			
		}
		//getPrintWriter().println(getPublicClassName(compilationUnit,javaFile));
	
		file_env = new FileEnvironment(OJSystem.env, compilationUnit, getPublicClassName(compilationUnit,javaFile));
		println("@@@@@@@@@@@@Sfile_env ："+file_env);
		ClassDeclarationList typedecls = compilationUnit.getClassDeclarations(); 
         for (int j = 0; j < typedecls.size(); ++j)
         {
            ClassDeclaration class_decl = typedecls.get(j);
            OJClass c = makeOJClass(file_env, class_decl);
            OJSystem.env.record(c.getName(), c);
            recordInnerClasses(c);
         }
	    return compilationUnit;
	}

	protected void initParseTree() throws Exception
	{
		try
	      {
	    	compilationUnit.accept(new TypeNameQualifier (file_env));
	        MemberAccessCorrector corrector = new MemberAccessCorrector(file_env);
	        compilationUnit.accept(corrector);
	      } 
	      catch (ParseTreeException e)
	      {
	         throw new Exception("can't initialize parse tree");
	     }
	}
	void initParseTree(CompilationUnit comp_unit,FileEnvironment file_env)
	{
	      try
	      {
	         comp_unit.accept(new TypeNameQualifier(file_env));
	         MemberAccessCorrector corrector = new MemberAccessCorrector(file_env);
	         comp_unit.accept(corrector);
	      } 
	      catch (ParseTreeException e)
	      {
	         System.err.println("Encountered errors during analysis.");
	         e.printStackTrace();
	      }
	}
	protected String arrangeOriginal(String args) throws Exception
	{
		 getPrintWriter().println("arrangeOriginal2");
		 getPrintWriter().println(args);
		 return args;
	}
	protected String arrangeMutants(String args,String mutantType,String mutantOPType) throws Exception
	{
		getPrintWriter().println("arrangeMutants2");
		getPrintWriter().println(args);
		return args;
		
	}
	public static void recordInnerClasses( OJClass c ) 
	{
	      OJClass[] inners = c.getDeclaredClasses();
	      for (int i = 0; i < inners.length; ++i) 
	      {
	         OJSystem.env.record( inners[i].getName(), inners[i] );
	         recordInnerClasses( inners[i] );
	      }
	   }
	public OJClass makeOJClass( Environment env, ClassDeclaration cdecl ) 
	   {
	      OJClass result;
	      System.out.println("*()*"+cdecl.getName());
	      String qname ;
	      if(env.toQualifiedName( cdecl.getName())==null)
	      { qname=cdecl.getName().replace("/", ".");}
	      else
	      {
	    	  qname=env.toQualifiedName( cdecl.getName());
	      }
	      System.out.println("*()*"+qname);
	      Class<?> meta = OJSystem.getMetabind( qname );
	      try 
	      {
	         Constructor<?> constr = meta.getConstructor( new Class[]{
	             Environment . class,OJClass . class, ClassDeclaration . class } );
	         Object[] args = new Object[]{env, null, cdecl};
	         result = (OJClass) constr.newInstance(args);
	      } 
	      catch (Exception ex) 
	      {
	         System.err.println("errors during gererating a metaobject for " + qname);
	         ex.printStackTrace();
	         result = new OJClass( env, null, cdecl );
	      }
	      return result;
	   }
	public String getPublicClassName(CompilationUnit comp_unit) throws ParseTreeException
	   {

	      ClassDeclaration cd = comp_unit.getPublicClass();
	      if (cd != null) 
	      {
	         return cd.getName();
	      }
	      else 
	    	  
	    	 return null;
	   }
	//增加其是否为java文件的判断模块
	public String getPublicClassName(CompilationUnit comp_unit,String javaFile) throws ParseTreeException
	   {
		
		 if (getPublicClassName(comp_unit) == null)
         {
        	 File jFile=new File(javaFile);
            int len = jFile.getName().length();
            return jFile.getName().substring(0, len-5);
            //??这是干嘛的去掉.java
         }
		 return getPublicClassName(comp_unit);
	   }
	
	public void runOP(String args) throws Exception
	{
	}
	

	
}
