package javamutation;

import mujava.exceptionOp.EFD;
import mujava.exceptionOp.EHC;
import mujava.exceptionOp.EHD;
import mujava.exceptionOp.EHI;
import mujava.exceptionOp.ETC;
import mujava.exceptionOp.ETD;
import mujava.op.util.CodeChangeLog;
import mujava.op.util.Mutator;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ParseTreeException;

public class DealJavaOJMutantException extends DealJavaOJMutant{

	public DealJavaOJMutantException()
	{
		mutantType="exceptionOp";
		mutantOp=exceptionOp;
	}
	
	public void runOP(String args) throws Exception
	{
		
		if(!exceptionOp[0].equals("null")&&exceptionOp!=null)
		{
			System.out.println(exceptionOp.length+"Ö´ÐÐe");
			mutantType="exceptionOp";
			mutantOp=exceptionOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length+"Ö´ÐÐe");
		}
	}
	void genMutants()
	   {
		
		System.out.println("exception");
	      if (compilationUnit == null)
	      {
	         System.err.println(file + " is skipped.");
	      }
	    
	      ClassDeclarationList cdecls = compilationUnit.getClassDeclarations();
	      if (cdecls == null || cdecls.size() == 0) 
	    	 return;

	      if (exceptionOp != null && exceptionOp.length > 0)
	      {
	    	  
	       //  AndroidMutationSystem.clearPreviousMutants();
	 //        AndroidMutationSystem.MUTANT_PATH = AndroidMutationSystem.EXCEPTION_MUTANT_PATH;
	    	 CodeChangeLog.setMutantPath(mutantPath);
	    	  CodeChangeLog.openLogFile();
	        genExceptionMutants(cdecls);
//	    	  {  
//	    	  Mutator mutant_op=new Mutator(file_env,compilationUnit,mutantPath,className);
//	 		mutant_op = new EHC(file_env, null, compilationUnit);
//	          try {
//	 			compilationUnit.accept(mutant_op);
//	 		} catch (ParseTreeException e) {
//	 			// TODO Auto-generated catch block
//	 			e.printStackTrace();
//	 		}
//	      }
	    	  
	         CodeChangeLog.closeLogFile();
	      }
	   }
	
	void genExceptionMutants(ClassDeclarationList cdecls)
	   {
	      for (int j=0; j<cdecls.size(); ++j)
	      {
	         ClassDeclaration cdecl = cdecls.get(j);

	         if (cdecl.getName().equals(className))
	         {
	            try
	            {
	            	  Mutator mutant_op=new Mutator(file_env,compilationUnit,mutantPath,className);
	               if (hasOperator(exceptionOp, "EFD"))
	               {
	                  System.out.println("  Applying EFD ... ... ");
	                  mutant_op = new EFD(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator(exceptionOp, "EHC"))
	               {
	                  System.out.println("  Applying EHC ... ... ");
	                  mutant_op = new EHC(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator(exceptionOp, "EHD"))
	               {
	                  System.out.println("  Applying EHD ... ... ");
	                  mutant_op = new EHD(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator(exceptionOp, "EHI"))
	               {
	                  System.out.println("  Applying EHI ... ... ");
	                  mutant_op = new EHI(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator(exceptionOp, "ETC"))
	               {
	                  System.out.println("  Applying ETC ... ... ");
	                  mutant_op = new ETC(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator(exceptionOp, "ETD"))
	               {
	                  System.out.println("  Applying ETD ... ... ");
	                  mutant_op = new ETD(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	            } catch (ParseTreeException e)
	            {
	               System.err.println( "Exception, during generating traditional mutants for the class "
	                               + className);
	               e.printStackTrace();
	            }
	         }
	      }
	   }
}
