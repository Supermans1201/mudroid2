package androidOp;

import java.io.*;
import openjava.ptree.*;
import mujava.op.util.MutantCodeWriter;

public class ETR_Writer extends MutantCodeWriter
{
   
   MethodDeclaration original = null;
   MethodDeclaration mutant = null;
   public void setMutant(MethodDeclaration p,MethodDeclaration mutant)
   {
      this.original = p;
      
      this.mutant=mutant;
   }
   public ETR_Writer( String file_name, PrintWriter out ) 
   {
	  super(file_name,out);
   }
   public void visit(MethodDeclaration p) throws ParseTreeException 
   {
	   System.out.println(p.getName());
	  if (p.getName().equals("onTouch")) 
	  {
		  
		  if(p==this.original)
		  { mutant.accept(this);
		  writeLog(removeNewline(original.toString()+" => "+mutant.toString()));
		  return;
		  }
		  System.out.println(p.getGenericsTypeParameters());
		  System.out.println(p.getParameters());
		  System.out.println(p.getBody());
		  System.out.println(p.getParent().getParent());
	  }
      if (p.getBody() != null)
         super.visit(p);
   }
}

