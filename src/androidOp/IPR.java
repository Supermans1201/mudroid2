package androidOp;

import java.io.IOException;
import java.io.PrintWriter;

import mujava.classOp.EAM_Writer;
import openjava.mop.FileEnvironment;
import openjava.mop.OJClass;
import openjava.mop.OJSystem;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.Literal;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.Variable;

public class IPR extends mujava.op.util.Mutator {

	 public IPR(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
	   {
		  super( file_env, comp_unit );
	   }

	   public void visit( MethodCall p ) throws ParseTreeException 
	   {
	      String method_name = p.getName();
	      System.out.println("method_name : "+method_name);
	      if(method_name.equals("putExtra"))
	    	 
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
	       //  if (bindedtype.toString().equals("class android.content.Intent"))
	         {
				System.out.println(p.getArguments());
				generateMutants(p);
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
	   public  boolean isPrimitive(OJClass type)
	     {
		   	if (type.equals(OJSystem.FLOAT))     return true;
	        if (type.equals(OJSystem.BYTE))     return true;
	        if (type.equals(OJSystem.CHAR))     return true;
	        if (type.equals(OJSystem.SHORT))    return true;
	        if (type.equals(OJSystem.INT))      return true;
	        if (type.equals(OJSystem.LONG))     return true;
	        if (type.equals(OJSystem.DOUBLE))   return true;
	        if (type.equals(OJSystem.VOID))     return true;
	        return false;
	     }
	   private void generateMutants(MethodCall p)
	   {
		// TODO Auto-generated method stub
		   MethodCall mutant = (MethodCall)p.makeRecursiveCopy();
		   ExpressionList args = mutant.getArguments();
		   Expression arg1 = null;
		   try {
			   System.out.println(args.get(1).getType(env));
			if(isPrimitive(args.get(1).getType(env)))
			{
				System.out.println(args.get(1).getType(env));
				arg1 = new Literal(1, "0") ;
				System.out.println("arg1:"+arg1);
			}
			else if(args.get(1).getType(env).equals(OJSystem.STRING))
			{
			   System.out.println(args.get(1).getType(env));
			   arg1 = new Literal(6, "\"\"") ;
				System.out.println("arg1:"+arg1);
			}
			else if(args.get(1).getType(env).equals(OJSystem.BOOLEAN))
			{
				   System.out.println(args.get(1).getType(env));
				   if(args.get(1).toString().equals("true"))
					   arg1 = new Literal(1, "false") ;
				   else
					   arg1 = new Literal(1, "true") ;
				   System.out.println("arg1:"+arg1);
			}
			else 
			{
				System.out.println(args.get(1).getType(env));
				   arg1 = new Literal(7, "null") ;
				   System.out.println("arg1:"+arg1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   //Expression arg1 = new ClassLiteral(env.lookupClass(args.get(1).toString())) ;
		 //  System.out.println("arg1:"+env.lookupClass(className));
		 //.out.println("arg1:"+arg1);
		   args.set(1, arg1);
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
