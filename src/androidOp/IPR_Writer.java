package androidOp;

import java.io.PrintWriter;
import mujava.op.util.MutantCodeWriter;
import openjava.ptree.ArrayAccess;
import openjava.ptree.Expression;
import openjava.ptree.ExpressionList;
import openjava.ptree.FieldAccess;
import openjava.ptree.Leaf;
import openjava.ptree.MethodCall;
import openjava.ptree.ParseTreeException;
import openjava.ptree.TypeName;
import openjava.ptree.Variable;

public class IPR_Writer extends MutantCodeWriter
{
   MethodCall original = null;
   MethodCall mutant = null;
   public void setMutant(MethodCall original, MethodCall mutant)
   {
	  this.mutant = mutant;
      this.original = original;
   }

   public IPR_Writer( String file_name, PrintWriter out ) 
   {
	  super(file_name, out);
   }
   
   public void visit( MethodCall p ) throws ParseTreeException
   {
      if (isSameObject(p, original))
      {
		 // -------------------------------------------------------------
		 mutated_line = line_num;
		 visit(mutant);
		 writeLog(removeNewline(original.toString()+" => "+mutant.toString()));
		 // -------------------------------------------------------------
      } 
      else 
      {
         Expression expr = p.getReferenceExpr();
         TypeName reftype = p.getReferenceType();

         if (expr != null) 
         {
            if (expr instanceof Leaf  ||
                expr instanceof ArrayAccess ||
                expr instanceof FieldAccess ||
                expr instanceof MethodCall ||
                expr instanceof Variable) 
            {
               expr.accept( this );
            } 
            else 
            {
		       writeParenthesis( expr );
            }
            out.print( "." );
         } 
         else if (reftype != null) 
         {
  	        reftype.accept( this );
	        out.print( "." );
	     }
         String name = p.getName();
         out.print( name );
         ExpressionList args = p.getArguments();
	     writeArguments( args );
      }
   }
}
