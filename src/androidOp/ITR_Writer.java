package androidOp;

import java.io.PrintWriter;
import mujava.op.util.MutantCodeWriter;
import openjava.ptree.AllocationExpression;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.MemberDeclarationList;

import openjava.ptree.ParseTreeException;
import openjava.ptree.TypeName;


public class ITR_Writer extends MutantCodeWriter
{
	 AllocationExpression original = null;
	 AllocationExpression mutant = null;
	   public void setMutant(AllocationExpression original, AllocationExpression mutant)
	   {
	      this.original = original;
	      this.mutant = mutant;
	   }

	   public ITR_Writer( String file_name, PrintWriter out ) 
	   {
		  super(file_name, out);
	   }

	   public void visit( AllocationExpression p ) throws ParseTreeException
	   {
	      if (isSameObject(p, original))
	      {
		     mutated_line = line_num;
		     
		     writeLog(removeNewline(original.toString() + " => " + mutant.toString()));
		     
		     visit(mutant);
	      }
	      else
	      {
	         Expression encloser = p.getEncloser();
	         if (encloser != null) 
	         {
	            encloser.accept( this );
	            out.print( " . " );
	         }
	         out.print( "new " );
	         TypeName tn = p.getClassType();
	         tn.accept( this );
	         ExpressionList args = p.getArguments();
	         writeArguments( args );
	         MemberDeclarationList mdlst = p.getClassBody();
	         if (mdlst != null) 
	         {
	            out.println( "{" );
	            pushNest();  
	            mdlst.accept( this );  
	            popNest();
	            writeTab();  
	            out.print( "}" );
	         }
	      }
	   }
}