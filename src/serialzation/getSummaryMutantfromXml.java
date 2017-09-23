package serialzation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Visitor;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;

import singleton.FileList;
import singleton.Op;


public class getSummaryMutantfromXml extends DealXmlSax{
	
	List<String> classOp=new ArrayList <String>();
	List<String> traditionalOp=new ArrayList <String>();
	List<String> exceptionOp=new ArrayList <String>();
	List<String> androidOp=new ArrayList <String>();
	List<String> xmlOp=new ArrayList <String>();
	
	String path=null;
	String path2=null;
	String name=null;
	String name0=null;
	String op=null;
	String opType=null;
	String method=null;
	
	String orginalName=null;
	String targetMethod=null;
	String targetOpType=null;
	String targetOp=null;
	
	List<String> mutentedJava=new ArrayList<String>();
	
	private boolean allName=false;
	private boolean allOp=false;
	private boolean allMethod=false;
	private boolean allOpType=false;
	
	public  Set<String> nameSet=new HashSet<String>();
	public Set<String> opTypeSet=new HashSet<String>();
	public  Set<String> cmopSet=new HashSet<String>();
	public  Set<String> tmopSet=new HashSet<String>();
	public  Set<String> emopSet=new HashSet<String>();
	public  Set<String> amopSet=new HashSet<String>();
	public  Set<String> xmopSet=new HashSet<String>();
	public  Set<String> opSet=new HashSet<String>();
	public  TreeMap<String,Integer> nameMap=new TreeMap<String,Integer>();
	static TreeMap<String,Integer> opTypeMap=new TreeMap<String,Integer>();
	static TreeMap<String,Integer> name_opTypeMap=new TreeMap<String,Integer>();
	static TreeMap<String,Integer> name_tm_methodMap=new TreeMap<String,Integer>();
	public  TreeMap<String,Integer> opType_opMap=new TreeMap<String,Integer>();
	public  TreeMap<String,Integer> name_opMap=new TreeMap<String,Integer>();
	
public static void main2(String[] args) {
		
	
		run(new getSummaryMutantfromXml(), args);
		//mutantJavaAndXmlList.getInstance().readJavaList();
	}
	public static void main(String[] args) {
		
		args=new String[]{"F:/muandroid3/AndroidApp-master/app/mutant/main/.projectToXMLs/projectToXMLs_java.xml"};
		run(new getSummaryMutantfromXml(), args);
	//	mutantJavaAndXmlList.getInstance().readJavaList();
		//	System.out.println(nameSet);
		//	System.out.println(opTypeSet);
		//	System.out.println(cmopSet);
		//	System.out.println(tmopSet);
		//	System.out.println(emopSet);
		//	System.out.println(amopSet);
		//	System.out.println(xmopSet);
		//		System.out.println(nameMap);
		//		System.out.println(name_opMap);
		//		System.out.println(opTypeMap);
		//		System.out.println(opType_opMap);
		//		System.out.println(name_tm_methodMap);
		//		System.out.println(name_opTypeMap);
	}

	public getSummaryMutantfromXml() {
	}
	int i=0;
	public void run(String[] args) throws Exception
	{		
		
		File file=new File(args[0]);
		if(!file.exists())
		{
			System.out.println("文件不存在");
			return ;
		}
		Document document = parse(args[0]);
		runOP(args[0]);
		if(args.length>1)
		{
			orginalName=args[1];
			targetMethod=args[2];
			targetOpType=args[3];
			targetOp=args[4];
			System.out.println(targetMethod);
			System.out.println(targetOp);
			if(targetOp.equals("all"))
				allOp=true;
			if(targetMethod.equals("all"))
				allMethod=true;
			if(targetOpType.equals("all"))
				allOpType=true;
			if(orginalName.equals("all"))
				allName=true;
		}
		else 
		{
			allOp=true;
			allMethod=true;
			allOpType=true;
			allName=true;
		}
		process(document);
	}
	protected void process(Document document) throws Exception 
	{
		//getXMLWriter().write(document);
		//getXMLWriter().flush();
		path=null;
		name=null;
		opType=null;
		method=null;
		op=null;
		System.out.println(allOp);
		System.out.println(allMethod);
		Visitor visit=new VisitorSupport()
		{
			
			public void visit(Element e)
			{
				if(e.getName().equals("File"))
				{
					path=e.attributeValue("path");
					path2=path.replace("\\", "/");
					name0=e.attributeValue("name");
					
					
			//		System.out.println(name0);
					name=name0.substring(0,name0.lastIndexOf("."));
					String s="null";
					if(path2.indexOf("mutant/main/res")>=0)
					{
						s="mutant/main/res";
					}
					else if(path2.indexOf("mutant/main/java")>=0)
					{
						s="mutant/main/java";
					}
					else if(path2.indexOf("mutant/main/AndroidManifest")>=0)
					{
						s="mutant/main/AndroidManifest";
					}
					if(s!="null")
					{
						path2=path2.substring(path2.indexOf(s), path2.lastIndexOf(name)-1);
						op=path2.substring(path2.indexOf(name));
						path2=path2.substring(path2.indexOf(""),path2.lastIndexOf(name));
						//System.out.println(path2);
						//System.out.println(name);
						op=op.substring(name.length()+1);
						if(op.indexOf("/")>0)
						{
							opType=op.substring(0,op.indexOf("/"));
							op=op.substring(op.indexOf("/")+1);
							if(op.indexOf("/")>0||opType=="traditionalOp")
							{
								method=op.substring(0,op.indexOf("/"));
								op=op.substring(op.indexOf("/")+1);	
							}
							else
							{
								method="null";
							}
						}
						else 
						{
							opType=op;
								//System.out.println("*******"+op);
						}
						if(op.indexOf("_")>0)
						{
							op=op.substring(0, op.indexOf("_"));
						}
			//			System.out.println(name0.equals(orginalName)&&(method.equals(targetMethod)||allMethod)&&opType.equals(targetOpType)&&(op.equals(targetOp)||allOp));
						
						if(!nameMap.containsKey(name0))
						{
							nameMap.put(name0, 1);
						}
						else
						{
							int count=	nameMap.get(name0)+1;
							nameMap.put(name0, count);
						}
						if(!name_opMap.containsKey("("+name0+","+opType+","+op+")"))
						{
							name_opMap.put("("+name0+","+opType+","+op+")", 1);
						}
						else
						{
							int count=	name_opMap.get("("+name0+","+opType+","+op+")")+1;
							name_opMap.put("("+name0+","+opType+","+op+")", count);
						}
						if(!opTypeMap.containsKey(opType))
						{
							opTypeMap.put(opType, 1);
						}
						else
						{
							int count=	opTypeMap.get(opType)+1;
							opTypeMap.put(opType, count);
						}
						if(!name_opTypeMap.containsKey("("+name0+","+opType+")"))
						{
							name_opTypeMap.put("("+name0+","+opType+")", 1);
						}
						else
						{
							int count=	name_opTypeMap.get("("+name0+","+opType+")")+1;
							name_opTypeMap.put("("+name0+","+opType+")", count);
						}
						if(!name_tm_methodMap.containsKey("("+name0+","+opType+","+method+")"))
						{
							name_tm_methodMap.put("("+name0+","+opType+","+method+")", 1);
						}
						else
						{
							int count=	name_tm_methodMap.get("("+name0+","+opType+","+method+")")+1;
							name_tm_methodMap.put("("+name0+","+opType+","+method+")", count);
						}
						if(!opType_opMap.containsKey("("+opType+","+op+")"))
						{
							opType_opMap.put("("+opType+","+op+")", 1);
						}
						else
						{
							int count=	opType_opMap.get("("+opType+","+op+")")+1;
							opType_opMap.put("("+opType+","+op+")", count);
						}
//						if(name0.equals(orginalName))
//						{
//							opTypeSet.add(opType);
//						}
//						System.out.println("("+name+","+opType+","+method+","+op+")");
				//		System.out.println("path : "+opType);
						nameSet.add(name0);
						opTypeSet.add(opType);
						//System.out.println(op);
						//System.out.println(opType.indexOf("classOp"));
						if(opType.indexOf("classOp")>=0)
						{
							cmopSet.add(op);
						}
						else if(opType.indexOf("traditionalOp")>=0)
						{
							tmopSet.add(op);
						}
						else if(opType.indexOf("exceptionOp")>=0)
						{
							emopSet.add(op);
						}
						else if(opType.indexOf("androidOp")>=0)
						{
							amopSet.add(op);
						}
						else if(opType.indexOf("xmlOp")>=0)
						{
							xmopSet.add(op);
						}
						opSet.add(op);
						if(allName||(name0.equals(orginalName))&&(allMethod||method.equals(targetMethod))&&(allOpType||opType.equals(targetOpType))&&(allOp||op.equals(targetOp)))
						{
							
							//	System.out.println(path);
							mutentedJava.add(path);
						}//map.put(name, i);
						i++;
					}
				}
			}
		};
		document.accept(visit);
		FileList.getInstance().removeJavaMList();
		FileList.getInstance().setJavaMList(mutentedJava);
	}
	 protected void setFormat()
	{
    	format = OutputFormat.createPrettyPrint();
	}
	protected void setOP()
	{
		Op.getInstance().setClassOp(classOp);
		Op.getInstance().setTraditionalOp(traditionalOp);
		Op.getInstance().setExceptionOp(exceptionOp);
		Op.getInstance().setAndroidOp(androidOp);
		Op.getInstance().setXmlOp(xmlOp);
	}
}
