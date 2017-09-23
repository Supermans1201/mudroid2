package androidOp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import mujava.classOp.PNC_Writer;
import mujava.util.InheritanceINFO;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.mop.OJSystem;
import openjava.ptree.AllocationExpression;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassLiteral;
import openjava.ptree.CompilationUnit;
import openjava.ptree.ExpressionList;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Expression;
import singleton.InheritanceRelation;


public class ITR extends mujava.op.util.Mutator {
	 public ITR(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	   {
	  	  super( file_env, comp_unit );
	   }
	 public static boolean isPrimitive(OJClass type)
     {
        if (type.equals(OJSystem.BOOLEAN))  return true;
        if (type.equals(OJSystem.BYTE))     return true;
        if (type.equals(OJSystem.CHAR))     return true;
        if (type.equals(OJSystem.SHORT))    return true;
        if (type.equals(OJSystem.INT))      return true;
        if (type.equals(OJSystem.LONG))     return true;
        if (type.equals(OJSystem.DOUBLE))   return true;
        if (type.equals(OJSystem.VOID))     return true;
        return false;
     }
	   public void visit( AllocationExpression p ) throws ParseTreeException 
	   {
	      String original_name = p.getClassType().getName();
	      System.out.println("original_name:"+original_name);
	      OJClass type = getType(p.getClassType());
	      System.out.println("type:"+type);
	      Boolean isString=false;
	      String intentclass="";
	      if(getType(p.getClassType()).toString().equals("class android.content.ComponentName"))
	      {
	    	  if( p.getArguments().size()==2)
	    	  {
	    		  intentclass=p.getArguments().get(1).toString();
	    		  try {
	    			  System.out.println("type :"+p.getArguments().get(1).getClass());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		  if(p.getArguments().get(1).getClass().toString().equals("class openjava.ptree.Literal"))
	    		  {
	    			  intentclass=intentclass.substring(intentclass.indexOf('"')+1, intentclass.lastIndexOf('"'))+".class";
	    			  isString=true;
	    		  }
	    		  System.out.println("p1:"+intentclass);
	    		  List<InheritanceINFO> InheritanceINFOlist=InheritanceRelation.getInstance().getInheritanceInfoByPackage(intentclass);
	    		  for(int i=0;i<InheritanceINFOlist.size();i++)
	    		  {
	    				generateMutants(p,InheritanceINFOlist.get(i).getClassName(),1,isString);
	    				System.out.println(InheritanceINFOlist.get(i).getClassName());
	    		  }
	    		  System.out.println("p1:"+intentclass);
	    	  }
	      }
	      if(getType(p.getClassType()).toString().equals("class android.content.Intent"))
	      {
	    	  if( p.getArguments().size()==2)
				{
	    		//  intentclass= env.toQualifiedName(p.getArguments().get(1)toQualifiedName);
	    		  try {
					intentclass=p.getArguments().get(1).getType(env).toString();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		  try {
	    			  System.out.println("type :"+p.getArguments().get(1).getClass());
	    			  System.out.println("type :"+intentclass);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		  List<InheritanceINFO> InheritanceINFOlist=InheritanceRelation.getInstance().getInheritanceInfoByPackage(intentclass);
	    		 
	    		  for(int i=0;i<InheritanceINFOlist.size();i++)
	    		  {
	    				generateMutants(p,InheritanceINFOlist.get(i).getClassName(),1,isString);
	    				System.out.println(InheritanceINFOlist.get(i).getClassName());
	    			}
				}
		    	 if( p.getArguments().size()==4)
		    	 {
		    		 intentclass=p.getArguments().get(3).toString();
		    		 List<InheritanceINFO> InheritanceINFOlist=InheritanceRelation.getInstance().getInheritanceInfoByPackage(intentclass);
		    			for(int i=0;i<InheritanceINFOlist.size();i++)
		    				{
		    				generateMutants(p,InheritanceINFOlist.get(i).getClassName(),3,isString);
		    				System.out.println(InheritanceINFOlist.get(i).getClassName());
		    				}
		    		 System.out.println("p1:"+p.getArguments().get(3));
		    	 }
	      }
	    //  System.out.println("p0:"+p.getArguments().get(0));
	    //  System.out.println("p1:"+p.getArguments().get(1));
	      if (isPrimitive(type))
	      {
	         super.visit(p);
	      }
	      else
	      {
	         //InheritanceINFO inf = InheritanceRelation.getInstance().getInheritanceInfo(intentclass);
//	         if (inf != null) 
//	        	generateMutants(type, p, inf.getChilds());
	         super.visit(p);
	      }
	   }
	   //为了简便处理为都转换为.class类型的函数进行调用
	   private void generateMutants(AllocationExpression p,String className,int type,boolean isString)
	   {
		// TODO Auto-generated method stub
		   AllocationExpression mutant = (AllocationExpression)p.makeRecursiveCopy();
		   ExpressionList args = mutant.getArguments();
		   Expression arg1 = new ClassLiteral(env.lookupClass(className)) ;
		   System.out.println("arg1:"+env.lookupClass(className));
		   System.out.println("arg1:"+arg1);
		   args.set(type, arg1);
		   mutant.setArguments(args);
           //mutant.setClassType(new TypeName(info.getClassName()));
           outputToFile(p, mutant);
		
	}

	public void outputToFile(AllocationExpression original, AllocationExpression mutant)
	   {
	      String f_name;
	      num++;
	      f_name = getSourceName(this);
	      String mutant_dir = getMuantID();

	      try 
	      {
			 PrintWriter out = getPrintWriter(f_name);
			 PNC_Writer writer = new PNC_Writer(  mutant_dir, out  );
			 writer.setMutant(original, mutant);
			 comp_unit.accept( writer );
			 out.flush();  
			 out.close();
	      } catch ( IOException e ) {
			 System.err.println( "fails to create " + f_name );
	      } catch ( ParseTreeException e ) {
			 System.err.println( "errors during printing " + f_name );
			 e.printStackTrace();
	      }
	   }
}
