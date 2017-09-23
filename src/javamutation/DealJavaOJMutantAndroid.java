package javamutation;

import androidOp.ECR;
import androidOp.ETR;
import androidOp.IPR;
import androidOp.ISR;
import androidOp.ITR;
import mujava.op.util.CodeChangeLog;
import mujava.op.util.Mutator;
import openjava.ptree.ClassDeclarationList;

public class DealJavaOJMutantAndroid extends DealJavaOJMutant {
	boolean existIHD = false;

	DealJavaOJMutantAndroid() {
		mutantType = "androidOp";
		mutantOp = androidOp;
	}

	public void runOP(String args) throws Exception {
		if (androidOp != null && androidOp.length > 0) {
			System.out.println(classOp.length + "Ö´ÐÐc");
			mutantType = "androidOp";
			mutantOp = androidOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "Ö´ÐÐc");
		}

	}

	void genMutants() {
		System.out.println("class");
		if (compilationUnit == null) {
			System.err.println(file + " is skipped.");
		}
		ClassDeclarationList cdecls = compilationUnit.getClassDeclarations();

		if (cdecls == null || cdecls.size() == 0)
			return;

		if (classOp != null && classOp.length > 0) {
			try {
				getPrintWriter().println("* Generating class mutants");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// AndroidMutationSystem.clearPreviousClassMutants();
			// AndroidMutationSystem.MUTANT_PATH = "";
			CodeChangeLog.setMutantPath(mutantPath);
			CodeChangeLog.openLogFile();
			try {

				Mutator mutant_op = new Mutator(file_env, compilationUnit,
						mutantPath, className);

				if (hasOperator(androidOp, "ECR")) {
					System.out.println("  Applying ECR ... ... ");
					mutant_op = new ECR(file_env, null, compilationUnit);
					compilationUnit.accept(mutant_op);
				}

				if (hasOperator(androidOp, "ETR")) {
					System.out.println("  Applying ETR ... ... ");
					mutant_op = new ETR(file_env, null, compilationUnit);
					compilationUnit.accept(mutant_op);
				}
				if (hasOperator(androidOp, "IPR")) {
					System.out.println("  Applying IPR ... ... ");
					mutant_op = new IPR(file_env, null, compilationUnit);
					compilationUnit.accept(mutant_op);
				}
				if (hasOperator(androidOp, "ISR")) {
					System.out.println("  Applying ISR ... ... ");
					mutant_op = new ISR(file_env, null, compilationUnit);
					compilationUnit.accept(mutant_op);
				}
				if (hasOperator(androidOp, "ITR")) {
					System.out.println("  Applying ITR ... ... ");
					mutant_op = new ITR(file_env, null, compilationUnit);
					compilationUnit.accept(mutant_op);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CodeChangeLog.closeLogFile();
		}
	}

}
