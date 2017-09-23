/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package mujava.tradtionalOp;

import openjava.mop.*;
import openjava.ptree.*;

import java.io.*;

/**
 * <p>Generate VDL (Variable DeLetion) mutants
 * </p>
 * @author Lin Deng
 * @version 1.0
  */

public class VDL extends Arithmetic_OP
{
   public VDL(FileEnvironment file_env, ClassDeclaration cdecl, CompilationUnit comp_unit)
   {
      super( file_env, comp_unit );
   }
 
   public void visit( BinaryExpression p ) throws ParseTreeException 
   {
      Expression left = p.getLeft();
      left.accept(this);

      Expression right = p.getRight();
      right.accept(this);
      aorMutantGen(p);
   }
   
   public void visit(UnaryExpression p) throws ParseTreeException
   {

	   Expression expression = p.getExpression();
	   expression.accept(this);
	   aorMutantGen(p);
   }
   


private void aorMutantGen(UnaryExpression exp) {
    Expression mutant = exp.getExpression();
    Variable mutant2 = new Variable(" ");
    //System.out.println(exp+" => "+mutant2);
    if(mutant instanceof Variable || mutant instanceof ArrayAccess)  // if it is var or an array access
    {
    	//System.out.println("u "+exp);
  	  aor_outputToFile(exp, mutant2);
    }
}


private void aorMutantGen(BinaryExpression exp)
{
   Expression mutantLeft = exp.getLeft();
   Expression mutantRight = exp.getRight();
   if(mutantLeft instanceof Variable || mutantLeft instanceof ArrayAccess) // if left is  variable or an array access
   {//System.out.println("b "+exp);
 	  aor_outputToFile(exp, mutantRight); // delete it, only keep right
   }
   if(mutantRight instanceof Variable || mutantRight instanceof ArrayAccess) // if right is variable or an array access
   {//System.out.println("b "+exp);
 	  aor_outputToFile(exp, mutantLeft);  // delete it, only keep left 
   }
   
   
}






   /**
    * Output ODL mutants to file
    * @param original
    * @param mutant
    */
   public void aor_outputToFile(BinaryExpression original, BinaryExpression mutant)
   {
      if (comp_unit == null) 
         return;

      String f_name;
      num++;
      f_name = getSourceName("VDL");
      String mutant_dir = getMuantID("VDL");

      try 
      {
		 PrintWriter out = getPrintWriter(f_name);
		 ODL_Writer writer = new ODL_Writer(mutant_dir, out);
		 writer.setMutant(original, mutant);
		// System.err.println(">>>>>>>"+currentMethodSignature);
         writer.setMethodSignature(currentMethodSignature);
		 comp_unit.accept( writer );
		 out.flush();  out.close();
      } catch ( IOException e ) 
      {
		 System.err.println( "1fails to create " + f_name );
		 System.err.println(currentMethodSignature );
      } catch ( ParseTreeException e ) 
      {
		 System.err.println( "errors during printing " + f_name );
		 e.printStackTrace();
      }
   }
   
   public void aor_outputToFile(BinaryExpression original, Expression mutant)
   {
	      if (comp_unit == null) 
	          return;

	       String f_name;
	       num++;
	       f_name = getSourceName("VDL");
	       String mutant_dir = getMuantID("VDL");

	       try 
	       {
	 		 PrintWriter out = getPrintWriter(f_name);
	 		 VDL_Writer writer = new VDL_Writer(mutant_dir, out);
	 		 writer.setMutant(original, mutant);
	 	//	System.err.println(">>>>>>>"+currentMethodSignature);
	 		  writer.setMethodSignature(currentMethodSignature);
	 		 comp_unit.accept( writer );
	 		 out.flush();  out.close();
	       } catch ( IOException e ) 
	       {
	 		 System.err.println( "2fails to create " + f_name );
	 		 System.err.println( "2fails to create " +currentMethodSignature );
	 		 System.err.println( "2fails to create " +num );
	 		 System.err.println( "2fails to create " +original);
	 		System.err.println( "2fails to create " +mutant);
	       } catch ( ParseTreeException e ) 
	       {
	 		 System.err.println( "errors during printing " + f_name );
	 		 e.printStackTrace();
	       }
   }

private void aor_outputToFile(UnaryExpression original, Expression mutant) {
	   if (comp_unit == null) 
	          return;

	       String f_name;
	       num++;
	       f_name = getSourceName("VDL");
	       String mutant_dir = getMuantID("VDL");

	       try 
	       {
	 		 PrintWriter out = getPrintWriter(f_name);
	 		 ODL_Writer writer = new ODL_Writer(mutant_dir, out);
	 		 writer.setMutant(original, mutant);
	 //		System.err.println(">>>>>>>"+currentMethodSignature);
	          writer.setMethodSignature(currentMethodSignature);
	 		 comp_unit.accept( writer );
	 		 out.flush();  out.close();
	       }
	       catch ( IOException e)
	       {
	 		 System.err.println( "3fails to create " + f_name );
	       } catch ( ParseTreeException e ) 
	       {
	 		 System.err.println( "errors during printing " + f_name );
	 		 e.printStackTrace();
	       }
	
}
}
