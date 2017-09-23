package javamutation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import mujava.tradtionalOp.AODS;
import mujava.tradtionalOp.AODU;
import mujava.tradtionalOp.AOIS;
import mujava.tradtionalOp.AOIU;
import mujava.tradtionalOp.AORB;
import mujava.tradtionalOp.AORS;
import mujava.tradtionalOp.ASRS;
import mujava.tradtionalOp.CDL;
import mujava.tradtionalOp.COD;
import mujava.tradtionalOp.COI;
import mujava.tradtionalOp.COR;
import mujava.tradtionalOp.CreateDirForEachMethod;
import mujava.tradtionalOp.LOD;
import mujava.tradtionalOp.LOI;
import mujava.tradtionalOp.LOR;
import mujava.tradtionalOp.ODL;
import mujava.tradtionalOp.ROR;
import mujava.tradtionalOp.SDL;
import mujava.tradtionalOp.SOR;
import mujava.tradtionalOp.VDL;
import mujava.op.util.CodeChangeLog;
import mujava.op.util.Mutator;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ParseTreeException;


public class DealJavaOJMutantTradtional extends DealJavaOJMutant{
	
	public DealJavaOJMutantTradtional()
	{
		mutantType="traditionalOp";
		mutantOp=traditionalOp;
	}
	public void runOP(String args) throws Exception
	{
		if(!traditionalOp[0].equals("null")&&traditionalOp!=null)
		{
			System.out.println(traditionalOp.length+"Ö´ÐÐt");
			mutantType="traditionalOp";
			mutantOp=traditionalOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length+"Ö´ÐÐt");
		}
	}
	void genMutants()
	   {
		
	      if (compilationUnit == null)
	      {
	         System.err.println (file + " is skipped.");
	      }
	      
	      ClassDeclarationList cdecls = compilationUnit.getClassDeclarations();
	      
	      if (cdecls == null || cdecls.size() == 0) 
	         return;

	      if (traditionalOp != null && traditionalOp.length > 0)
	      {
		     System.out.println("* Generating traditional mutants");
	       //  AndroidMutationSystem.clearPreviousTraditionalMutants();

	        // mutantPath = AndroidMutationSystem.TRADITIONAL_MUTANT_PATH;
		     CodeChangeLog.setMutantPath(mutantPath);
	         CodeChangeLog.openLogFile();
	         
	         genTraditionalMutants(cdecls);

	         CodeChangeLog.closeLogFile();
	      }
	   }
	
//	 void genTraditionalMutants(ClassDeclarationList cdecls,String op) 
//			 throws ParseTreeException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException 
//		
//	 {
//		  Mutator mutant_op=new Mutator(file_env,compilationUnit,mutantPath,className);
//		 Constructor<?> c;
//		
//			c = Class.forName("mujava.tradtionalOp.AORB").getConstructor(file_env.getClass(),ClassDeclaration.class,compilationUnit.getClass());
//		
//		 mutant_op=(Mutator)c.newInstance(file_env, null,compilationUnit);
//		 compilationUnit.accept(mutant_op);
//	 }
	 void genTraditionalMutants(ClassDeclarationList cdecls)
	   {

	      for (int j=0; j<cdecls.size(); ++j)
	      {
	         ClassDeclaration cdecl = cdecls.get(j);
	         String tempName = cdecl.getName();
	         if(tempName.indexOf("<") != -1 && tempName.indexOf(">")!= -1)
	        	 tempName = tempName.substring(0, tempName.indexOf("<")) + tempName.substring(tempName.lastIndexOf(">") + 1, tempName.length());

	         if (tempName.equals(className))
	         {
	            try
	            {
	            Mutator mutant_op=new Mutator(file_env,compilationUnit,mutantPath,className);
	               boolean AOR_FLAG = false;
	     
	               try
	               {
	                  //generate a list of methods from the original java class
	            	  //System.out.println("mutantPath: " + mutantPath);
	                  File f = new File(mutantPath, "method.list");
	                  FileOutputStream fout = new FileOutputStream(f);
	                  PrintWriter out = new PrintWriter(fout);

	                  mutant_op = new CreateDirForEachMethod(file_env, cdecl, compilationUnit, out);

	                  compilationUnit.accept(mutant_op);
	                  out.flush();  
	                  out.close();
	               } catch (Exception e)
	               {
	                  System.err.println("Error in writing method list");
	                  return;
	               }

	               if (hasOperator (traditionalOp, "AORB") )
	               {
	                  System.out.println("  Applying AOR-Binary ... ... ");
	                  AOR_FLAG = true;
	                  mutant_op = new AORB(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               
	               if (hasOperator (traditionalOp, "AORS") )
	               {
	                  System.out.println("  Applying AOR-Short-Cut ... ... ");
	                  AOR_FLAG = true;
	                  mutant_op = new AORS(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "AODU") ) 
	               {
	                  System.out.println("  Applying AOD-Normal-Unary ... ... ");
	                  mutant_op = new AODU(file_env, cdecl, compilationUnit);
	                  ((AODU)mutant_op).setAORflag(AOR_FLAG);
	                  compilationUnit.accept(mutant_op);
	               }
	          
	               if (hasOperator (traditionalOp, "AODS") )
	               {
	                  System.out.println("  Applying AOD-Short-Cut ... ... ");
	                  mutant_op = new AODS(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               
	               if (hasOperator (traditionalOp, "AOIU") )
	               {
	                  System.out.println("  Applying AOI-Normal-Unary ... ... ");
	                  mutant_op = new AOIU(file_env,cdecl,compilationUnit);
	                  ((AOIU)mutant_op).setAORflag(AOR_FLAG);
	                  compilationUnit.accept(mutant_op);
	               }
	               
	               if (hasOperator (traditionalOp, "AOIS") )
	               {
	                  System.out.println("  Applying AOI-Short-Cut ... ... ");
	                  mutant_op = new AOIS(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               
	               if (hasOperator (traditionalOp, "ROR") )
	               {
	                  System.out.println("  Applying ROR ... ... ");
	                  mutant_op = new ROR(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "COR") )
	               {
	                  System.out.println("  Applying COR ... ... ");
	                  mutant_op = new COR(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "COD") ) 
	               {
	                  System.out.println("  Applying COD ... ... ");
	                  mutant_op = new COD(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "COI") )
	               {
	                  System.out.println("  Applying COI ... ... ");
	                  mutant_op = new COI(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "SOR") )
	               {
	                  System.out.println("  Applying SOR ... ... ");
	                  mutant_op = new SOR(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "LOR") )
	               {
	                  System.out.println("  Applying LOR ... ... ");
	                  mutant_op = new LOR(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "LOI") )
	               {
	                  System.out.println("  Applying LOI ... ... ");
	                  mutant_op = new LOI(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "LOD") )
	               { 
	                  System.out.println("  Applying LOD ... ... ");
	                  mutant_op = new LOD(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }

	               if (hasOperator (traditionalOp, "ASRS") )
	               {
	                  System.out.println("  Applying ASR-Short-Cut ... ... ");
	                  mutant_op = new ASRS(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	     
	               if (hasOperator (traditionalOp, "SDL") )
	               {
	                  System.out.println("  Applying SDL ... ... ");
	                  mutant_op = new SDL(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               if (hasOperator (traditionalOp, "VDL") )
	               {
	                  System.out.println("  Applying VDL ... ... ");
	                  mutant_op = new VDL(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               if (hasOperator (traditionalOp, "ODL") )
	               {
	                  System.out.println("  Applying ODL ... ... ");
	                  mutant_op = new ODL(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	               if (hasOperator (traditionalOp, "CDL") )
	               {
	                  System.out.println("  Applying CDL ... ... ");
	                  mutant_op = new CDL(file_env, cdecl, compilationUnit);
	                  compilationUnit.accept(mutant_op);
	               }
	
	          
	            } catch (ParseTreeException e)
	            {
	               System.err.println( "Exception, during generating traditional mutants for the class "
	                              + className);
	               e.printStackTrace();
	            }
	         }
	      }}}