package androidOp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import mujava.classOp.EAM_Writer;
import mujava.util.InheritanceINFO;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassLiteral;
import openjava.ptree.CompilationUnit;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Variable;
import singleton.InheritanceRelation;


public class ISR extends mujava.op.util.Mutator {

	 public ISR(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	   {
		  super( file_env, comp_unit );
	   }

	   public void visit( MethodCall p ) throws ParseTreeException 
	   {
	     
	      Boolean isString=false;
	      String intentclass="";
	      String method_name = p.getName();
	      System.out.println("method_name : "+method_name);
	      if(method_name.equals("setClass")) 
	      { 
	    	Expression ref = p.getReferenceExpr();
	      	OJClass bindedtype = null;
	         if (ref == null)
	         {
				bindedtype = env.lookupClass(env.currentClassName());
	         }
	         else if (ref instanceof Variable)
	         {
			    bindedtype = env.lookupBind(ref.toString());
	         } 
	         System.out.println("Exprssion : "+bindedtype);
	         if (bindedtype.toString().equals("class android.content.Intent"))
	         {
	        	 if( p.getArguments().size()==2)
		    	  {
		    		  intentclass = p.getArguments().get(1).toString();
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
				System.out.println(p.getArguments());
	        }
	        
	      }
	      
	      Expression newp = this.evaluateDown( p );
	      if (newp != p) 
	      {
	         p.replace( newp );
	         return;
	      }
	      p.childrenAccept( this );
	      newp = this.evaluateUp( p );
	      if (newp != p)  
	    	 p.replace( newp );
 
	   }

	   private void generateMutants(MethodCall p,String className,int type,boolean isString)
	   {
		// TODO Auto-generated method stub
		   MethodCall mutant = (MethodCall)p.makeRecursiveCopy();
		   ExpressionList args = mutant.getArguments();
		   Expression arg1 = new ClassLiteral(env.lookupClass(className)) ;
		   System.out.println("arg1:"+env.lookupClass(className));
		   System.out.println("arg1:"+arg1);
		   args.set(type, arg1);
		   mutant.setArguments(args);
           //mutant.setClassType(new TypeName(info.getClassName()));
		   outputToFile(p, mutant);
		}
	   public void outputToFile(MethodCall original, MethodCall mutant)
	   {
	      if (comp_unit == null) 
	    	 return;

	      String f_name;
	      num++;
	      f_name = getSourceName(this);
	      String mutant_dir = getMuantID();

	      try 
	      {
			 PrintWriter out = getPrintWriter(f_name);
		 	 EAM_Writer writer = new EAM_Writer( mutant_dir, out );
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
