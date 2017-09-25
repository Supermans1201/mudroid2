package javamutation;

import java.io.File;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import mujava.op.util.MutantCodeWriter;
import mujava.op.util.Mutator;
import openjava.ptree.ClassDeclaration;
import openjava.ptree.CompilationUnit;

public class DealJavaOJMutant extends DealJavaOJ {

	public final String originalName = "original";
	public final String mutantName = "mutant";
	public String mutantType = null;
	public String mutantOp[] = null;

	public String mutantPath = null;
	public String className = null;
	public int num = 0;

	public static void main(String[] args) {
		run(new DealJavaOJMutant(),
				new String[] { "F:/coolweather-master/Android_APP/app/src/main/java/hk/ust/cse/hunkim/questionroom/timemanager/timemanager.java" });
	}

	public void run(String[] args) throws Exception {
		super.run(args);
		if(this.compilationUnit==null)
			return;
		arrangeOriginal(args[0]);
		setMutantPathAndClassName(args[0]);
		getPrintWriter().flush();

	}

	protected void process(CompilationUnit compilationUnit) throws Exception {
		super.process(compilationUnit);
	}

	void genMutants(String mutantOp) throws Exception {
		String className = "mujava." + mutantType + "." + mutantOp;
		Mutator mutant_op;
		Constructor<?> c = Class.forName(className).getConstructor(
				file_env.getClass(), ClassDeclaration.class,
				compilationUnit.getClass());
		mutant_op = (Mutator) c.newInstance(file_env, null, compilationUnit);
		compilationUnit.accept(mutant_op);
		// ?������ģʽ
	}

	void genMutants() {

	}

	protected boolean hasOperator(String[] list, String item) {
		for (int i = 0; i < list.length; i++) {
			if (list[i].equals(item))
				return true;
		}
		return false;
	}

	protected String arrangeOriginal(String args) throws Exception {
		System.out.println("arrangeOriginal:args:" + args);
		// ������"\\"����"/"
		args = args.replace('\\', '/');
		System.out.println("arrangeOriginal:args:" + args);

		String s1 = args.substring(0, args.indexOf("src")) + mutantName;// ���build/output
		String s2 = args.substring(args.indexOf("src") + 3);// ȥ��src

		String s3 = s2.substring(0, s2.length() - 5) + "/" + originalName;// /main/java/com/example/administrator/dataconnect/X99/original
		String s4 = s2.substring(s2.lastIndexOf("/"));// /X99.java

		args = s1 + s3 + s4;
		getPrintWriter().println(args);

		file = new File(args);
		if (!file.getParentFile().exists())
			file.getParentFile().mkdirs();
		file.createNewFile();
		PrintWriter out = new PrintWriter(file, "utf-8");

		MutantCodeWriter writer = new MutantCodeWriter(out);
		// ?
		writer.setClassName("setClassName");
		compilationUnit.accept(writer);
		out.flush();
		out.close();
		return args;
	}

	protected String setMutantPathAndClassName(String args) {
		args = args.replace('\\', '/');
		String s1 = args.substring(0, args.indexOf("src")) + mutantName;// ���build/output
		String s2 = args.substring(args.indexOf("src") + 3);// ȥ��src
		String s3 = s2.substring(0, s2.length() - 5) + "/" + mutantType;// /main/java/com/example/administrator/dataconnect/X99/original
		System.out.println(s1 + s3);
		mutantPath = s1 + s3;
		System.out.println("setCLASSNAME+++++++++++++++++" + mutantPath);
		String s4 = s2.substring(s2.lastIndexOf("/") + 1, s2.length() - 5);
		System.out.println("setCLASSNAME+++++++++++++++++" + s4);
		className = s4;
		return s1 + s3;
	}

	public void runOP(String args) throws Exception {
		if (!classOp[0].equals("null") && classOp != null) {
			System.out.println(classOp.length + "ִ��c");
			mutantType = "classOp";
			mutantOp = classOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "ִ��c");
		}
		if (!traditionalOp[0].equals("null") && traditionalOp != null) {
			System.out.println(traditionalOp.length + "ִ��t");
			mutantType = "traditionalOp";
			mutantOp = traditionalOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "ִ��t");
		}
		if (!exceptionOp[0].equals("null") && exceptionOp != null) {
			System.out.println(exceptionOp.length + "ִ��e");
			mutantType = "exceptionOp";
			mutantOp = exceptionOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "ִ��e");
		}
		if (androidOp != null && !androidOp[0].equals("null")) {
			System.out.println(androidOp.length + "ִ��a");
			mutantType = "androidOp";
			mutantOp = androidOp;
			setMutantPathAndClassName(args);
			genMutants();
			System.out.println(classOp.length + "ִ��a");
		}

	}

}
