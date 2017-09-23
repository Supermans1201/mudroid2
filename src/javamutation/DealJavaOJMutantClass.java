package javamutation;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import mujava.classOp.AMC;
import mujava.classOp.EAM;
import mujava.classOp.EMM;
import mujava.classOp.EOA;
import mujava.classOp.EOC;
import mujava.classOp.IHD;
import mujava.classOp.IHI;
import mujava.classOp.IOD;
import mujava.classOp.IOP;
import mujava.classOp.IPC;
import mujava.classOp.JDC;
import mujava.classOp.JID;
import mujava.classOp.JSD;
import mujava.classOp.JSI;
import mujava.classOp.JTD;
import mujava.classOp.JTI;
import mujava.classOp.OAN;
import mujava.classOp.OMD;
import mujava.classOp.OMR;
import mujava.classOp.PCI;
import mujava.classOp.PMD;
import mujava.classOp.PNC;
import mujava.classOp.PPD;
import mujava.classOp.PRV;
import mujava.op.util.CodeChangeLog;
import mujava.op.util.DeclAnalyzer;
import mujava.op.util.Mutator;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.ClassDeclarationList;
import openjava.ptree.ParseTreeException;

public class DealJavaOJMutantClass extends DealJavaOJMutant {
	boolean existIHD = false;

	DealJavaOJMutantClass() {
		mutantType = "classOp";
		mutantOp = classOp;
	}

	public static void addURL(String classPath) throws Exception {
		Method addClass = null;
		ClassLoader cl = null;
		File f = null;
		addClass = URLClassLoader.class.getDeclaredMethod("addURL",
				new Class[] { URL.class });
		addClass.setAccessible(true);
		f = new File(classPath);
		cl = ClassLoader.getSystemClassLoader();
		addClass.invoke(cl, new Object[] { f.toURI().toURL() });
	}

	public void runOP(String args) throws Exception {
		if (!classOp[0].equals("null") && classOp != null) {
			System.out.println(classOp.length + "执行c");
			mutantType = "classOp";
			mutantOp = classOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "执行c");
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
				genClassMutants(cdecls);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CodeChangeLog.closeLogFile();
		}
	}

	void genClassMutants(ClassDeclarationList cdecls) throws Exception {
		System.out.println("* Generating class mutants1");
		genClassMutants1(cdecls);
		System.out.println("* Generating class mutants2");
		genClassMutants2(cdecls);
	}

	void genClassMutants1(ClassDeclarationList cdecls) throws Exception {
		// 对类列表的每一个进行变异。
		System.out.println("cdecls.size  " + cdecls.size());
		for (int j = 0; j < cdecls.size(); ++j) {
			ClassDeclaration cdecl = cdecls.get(j);

			System.out.println("cdecl.getName()  " + cdecl.getName());
			System.out.println("cdecl.className  " + className);
			if (cdecl.getName().equals(className)) {
				String qname = file_env.toQualifiedName(cdecl.getName());
				System.out.println("qname  " + qname);
				try {
					Mutator mutant_op = new Mutator(file_env, compilationUnit,
							mutantPath, className);

					if (hasOperator(classOp, "AMC")) {
						System.out.println("  Applying AMC ... ... ");
						mutant_op = new AMC(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);

					}

					if (hasOperator(classOp, "IOP")) {
						System.out.println("  Applying IOP ... ... ");
						mutant_op = new IOP(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "IPC")) {
						System.out.println("  Applying IPC ... ... ");
						mutant_op = new IPC(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "PNC")) {
						System.out.println("  Applying PNC ... ... ");
						mutant_op = new PNC(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "PMD")) {
						System.out.println("  Applying PMD ... ... ");
						if (existIHD) {
							mutant_op = new PMD(file_env, cdecl,
									compilationUnit);
							compilationUnit.accept(mutant_op);
						}
					}

					if (hasOperator(classOp, "PPD")) {
						System.out.println("  Applying PPD ... ... ");
						if (existIHD) {
							mutant_op = new PPD(file_env, cdecl,
									compilationUnit);
							compilationUnit.accept(mutant_op);
						}
					}

					if (hasOperator(classOp, "PRV")) {
						System.out.println("  Applying PRV ... ... ");
						mutant_op = new PRV(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "PCI")) {
						System.out.println("  Applying PCI ... ... ");
						mutant_op = new PCI(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					// if (hasOperator(classOp, "PCC"))
					// {
					// System.out.println("  Applying PCC ... ... ");
					// mutant_op = new PCC( file_env, cdecl, compilationUnit );
					// compilationUnit.accept(mutant_op);
					// }

					// if (hasOperator(classOp, "PCD"))
					// {
					// System.out.println("  Applying PCD ... ... ");
					// mutant_op = new PCD( file_env, cdecl, compilationUnit );
					// compilationUnit.accept(mutant_op);
					// }

					if (hasOperator(classOp, "JSD")) {
						System.out.println("  Applying JSC ... ... ");
						mutant_op = new JSD(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "JSI")) {
						System.out.println("  Applying JSI ... ... ");
						mutant_op = new JSI(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "JTD")) {
						System.out.println("  Applying JTD ... ... ");
						mutant_op = new JTD(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "JTI")) {
						System.out.println("  Applying JTI ... ... ");
						mutant_op = new JTI(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "JID")) {
						System.out.println("  Applying JID ... ... ");
						mutant_op = new JID(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "OAN")) {
						System.out.println("  Applying OAN ... ... ");
						mutant_op = new OAN(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "EOA")) {
						System.out.println("  Applying EOA ... ... ");
						mutant_op = new EOA(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "EOC")) {
						System.out.println("  Applying EOC ... ... ");
						mutant_op = new EOC(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "EAM")) {
						System.out.println("  Applying EAM ... ... ");
						mutant_op = new EAM(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

					if (hasOperator(classOp, "EMM")) {
						System.out.println("  Applying EMM ... ... ");
						mutant_op = new EMM(file_env, cdecl, compilationUnit);
						compilationUnit.accept(mutant_op);
					}

				} catch (ParseTreeException e) {
					System.err
							.println("Encountered errors during generating mutants.");
					e.printStackTrace();
				}
			}
		}
	}

	void generateMutant(DeclAnalyzer mutant_op) {
		try {
			mutant_op.translateDefinition(compilationUnit);
		} catch (Exception ex) {
			System.err.println("fail to translate " + mutant_op.getName()
					+ " : " + ex);
			ex.printStackTrace();
		}
	}

	void genClassMutants2(ClassDeclarationList cdecls) throws Exception {
		System.out.println("cdecls.size  " + cdecls.size());
		for (int j = 0; j < cdecls.size(); ++j) {
			ClassDeclaration cdecl = cdecls.get(j);
			System.out.println("cdecl.getName()  " + cdecl.getName());
			System.out.println("cdecl.getName()  " + className);
			if (cdecl.getName().equals(className)) {

				DeclAnalyzer mutant_op = new DeclAnalyzer(file_env, null,
						cdecl, mutantPath, className);

				if (hasOperator(classOp, "IHD")) {
					System.out.println("  Applying IHD ... ... ");
					mutant_op = new IHD(file_env, null, cdecl);
					generateMutant(mutant_op);

					if (((IHD) mutant_op).getTotal() > 0)
						existIHD = true;
				}

				if (hasOperator(classOp, "IHI")) {
					System.out.println("  Applying IHI ... ... ");
					mutant_op = new IHI(file_env, null, cdecl);
					generateMutant(mutant_op);
				}

				if (hasOperator(classOp, "IOD")) {
					System.out.println("  Applying IOD ... ... ");
					mutant_op = new IOD(file_env, null, cdecl);
					generateMutant(mutant_op);
				}

				if (hasOperator(classOp, "OMR")) {
					System.out.println("  Applying OMR ... ... ");
					mutant_op = new OMR(file_env, null, cdecl);
					generateMutant(mutant_op);
				}

				if (hasOperator(classOp, "OMD")) {
					System.out.println("  Applying OMD ... ... ");
					mutant_op = new OMD(file_env, null, cdecl);
					generateMutant(mutant_op);
				}

				if (hasOperator(classOp, "JDC")) {
					System.out.println("  Applying JDC ... ... ");
					mutant_op = new JDC(file_env, null, cdecl);
					generateMutant(mutant_op);
				}
			}
		}
	}

}
