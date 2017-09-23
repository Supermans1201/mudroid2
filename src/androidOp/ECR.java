package androidOp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import openjava.mop.*;
import openjava.ptree.*;

public class ECR extends mujava.op.util.PolymorphicMutator
{
  
   int i=0;
   List <MethodDeclaration> listmd=new ArrayList<MethodDeclaration>();
   MethodDeclaration mA;
   MethodDeclaration mutant;
   public ECR(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
   {
	  super( file_env, comp_unit );
   }

   public void visit(MethodDeclaration p) throws ParseTreeException 
   {
	   System.out.println(p.getName());
	  if (p.getName().equals("onClick")) 
	  {
		  listmd.add(p);
		  int index=listmd.size()-1;
		  while(index>=1)
		  {
			  mA=p;
			  mutant=(MethodDeclaration) listmd.get(index-1).makeRecursiveCopy();
			  generateMutant(mA,mutant);
			  mA=listmd.get(index-1);
			  mutant=(MethodDeclaration) p.makeRecursiveCopy();
			  generateMutant(mA,mutant);
			  index--;
		  }
		  i++;
		  System.out.println(p.getGenericsTypeParameters());
		  System.out.println(p.getParameters());
		  System.out.println(p.getBody());
		  System.out.println(p.getParent().getParent());
	  }
      if (p.getBody() != null)
         super.visit(p);
   }

   private void generateMutant(MethodDeclaration p, MethodDeclaration mutant) {
	// TODO Auto-generated method stub
	   outputToFile(p,mutant);
}
   private void outputToFile(MethodDeclaration p, MethodDeclaration mutant) {
	// TODO Auto-generated method stub
	   if (comp_unit == null) 
	    	 return;

	      String f_name;
	      num++;
	      f_name = getSourceName(this);
	      String mutant_dir = getMuantID();

	      try 
	      {
			 PrintWriter out = getPrintWriter(f_name);
			 ECR_Writer writer = new ECR_Writer( mutant_dir, out );
			 writer.setMutant(p, mutant);
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
