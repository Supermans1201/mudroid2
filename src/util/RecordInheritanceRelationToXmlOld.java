package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import singleton.FileList;
import singleton.InheritanceRelation;
import singleton.Project;
import mujava.util.InheritanceINFO;

public class RecordInheritanceRelationToXmlOld {

	String class_Debug;

	static List<String> list = new ArrayList<String>();
	static InheritanceINFO[] classInfo = null;

	
	public static void main(String[] args) {

		Project.getInstance().setSelectProject(
				"F:/coolweather-master/Android_APP");
		GetFiles.getJarClassFiles();

		FileList.getInstance().readClassList();
		FileList.getInstance().readJarList();
		
		try {
			recordInheritanceRelation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// try {
		// ReadFile.main(args);
		//
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// args=new String[]{"F:/muandroid3/AndroidApp-master"};
		// new RecordInheritanceRelationToXml();
		// InheritanceRelation.getInstance().setInheritanceInfo(classInfo);
		// InheritanceRelation.getInstance().setJarsList(list);;
		// InheritanceRelation.getInstance().readJarsList();
	}
//String incremental="app.build.intermediates.incremental-verifier\debug";
	protected void process(String rootProject) throws Exception {

		recordInheritanceRelation();

		InheritanceRelation.getInstance().setInheritanceInfo(classInfo);
	}

	private final static String[] addClassNames(String[] list1, String[] list2) {
		if (list1 == null)
			list1 = list2;
		else {
			int num = list1.length;
			String[] temp = new String[num];

			for (int i = 0; i < temp.length; i++) {
				temp[i] = list1[i];
			}

			num = num + list2.length;
			list1 = new String[num];

			for (int i = 0; i < temp.length; i++) {
				list1[i] = temp[i];
			}

			for (int i = temp.length; i < num; i++) {
				list1[i] = list2[i - temp.length];
			}
		}
		return list1;
	}

	// 其输入为class所在的目录
	
	public static String[] getAllClassNames(List<String> classlist ) {

		String[] classes = new String[classlist.size()];// 为何又用string[]去存？

		for(int i=0;i<classlist.size();i++)
		{
			String temp="";
			if(classlist.get(i).lastIndexOf("\\debug\\")>0&&classlist.get(i).lastIndexOf(".class")>classlist.get(i).lastIndexOf("\\debug\\"))
			{
				temp=classlist.get(i).substring(classlist.get(i).lastIndexOf("\\debug\\")+7,classlist.get(i).lastIndexOf(".class"));
				temp=temp.replace("\\", ".");
			//	System.out.println(temp);
			}
			
			classes[i]=temp;
		}	
		return classes;
	}

	public static void recordInheritanceRelation() throws Exception {
		String[] classes = null;
		classes = getAllClassNames(FileList.getInstance().getClassList());
		for (int i = 0; i < classes.length; i++) {
			// System.err.println("MMMMMM"+list.get(i));
			//System.out.println(classes[i]);
		}
		list = FileList.getInstance().getJarList();
		classes = getAllClassNamesInJARSlist(classes, list);

		for (int i = 0; i < list.size(); i++) {
			// System.err.println("MMMMMM"+list.get(i));
			addURL(list.get(i));
		}
		for (int i = 0; i < FileList.getInstance().getClassList().size(); i++) {
			// System.err.println("MMMMMM"+list.get(i));
			addURL(new File(FileList.getInstance().getClassList().get(i)).getParent());
		}
		if (classes == null) {
			System.err.println("[ERROR] There are no classes to mutate.");
			System.err
					.println(" Please check the directory  "
							+ " and be sure that MuJava_HOME is set correctly (without a trailing slash) in mujava.config.");
			Runtime.getRuntime().exit(0);
		}
		
	
		classInfo = new InheritanceINFO[classes.length];
		// System.out.println(""+classInfo.length);
		boolean[] bad = new boolean[classes.length];
		
		for (int i = 0; i < classes.length; i++) {
			bad[i] = false;
			try {
				// System.out.println(this.class_Debug);
				// System.err.println(classes[i]);
				classes[i] = classes[i].replace('\\', '.');
				classes[i] = classes[i].replace('/', '.');
				Class<?> c = Class.forName(classes[i]);
				Class<?> parent = c.getSuperclass();
				if ((parent == null)
						|| (parent.getName().equals("java.lang.Object"))) {
					classInfo[i] = new InheritanceINFO(classes[i], "");
				} else {
					classInfo[i] = new InheritanceINFO(classes[i],
							parent.getName());
				}
			} catch (Exception e) {
				//System.err.println("[ERROR] Can't find the class: "
					//	+ classes[i]);
				//System.err
				//		.println("Please check that the compiled class for the code you want to mutate is in the classes/ directory. Also check that the MuJava_HOME variable in mujava.config does not end with a trailing slash. ");
				bad[i] = true;
				classInfo[i] = new InheritanceINFO(classes[i], "");
			Runtime.getRuntime().exit(0);
			} catch (Error er) {
				System.out.println("[WARNING] for class " + classes[i]
						+ " \n>>> " + er);
				bad[i] = true;
				classInfo[i] = new InheritanceINFO(classes[i], "");
			}
		}
		for (int i = 0; i < classes.length; i++) {
			if (bad[i])
				continue;
			String parent_name = classInfo[i].getParentName();
			for (int j = 0; j < classes.length; j++) {
				if (bad[j])
					continue;
				if (classInfo[j].getClassName().equals(parent_name)) {
					classInfo[i].setParent(classInfo[j]);
					classInfo[j].addChild(classInfo[i]);
					break;
				}
			}
		}
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
	@SuppressWarnings("resource")
	public static String[] getAllClassNamesInJARSlist(String[] result,
			List<String> list) throws IOException {
		String[] classes;// 为何又用string[]去存？
		String temp;
		String[] classF = null;
		int x = list.size();
		if (x > 0)
			classF = new String[x];
		for (int i = 0; i < list.size(); i++) {
			classF[i] = list.get(i);
		}
		if (classF != null) {
			for (int k = 0; k < classF.length; k++) {
				temp = classF[k];
				System.out.println("temp : " + temp);

				JarFile jarFile = new JarFile(temp);
				Enumeration<JarEntry> entrys = jarFile.entries();
				int length = 0;
				System.out.print("i start:" + length+".....");
				while (entrys.hasMoreElements()) {
					JarEntry jarEntry = entrys.nextElement();
					String temp2 = jarEntry.getName();
					if (temp2.indexOf(".class") > 0) {
						length++;
					} else {
						// System.err.println("qqq"+temp2);
					}
				}
				System.out.println("i end:" + length);
				classes = new String[length];

				entrys = jarFile.entries();
				while (entrys.hasMoreElements()) {

					JarEntry jarEntry = entrys.nextElement();
					String temp2 = jarEntry.getName();
					// System.out.println(""+temp2.indexOf(".class"));
					if (temp2.indexOf(".class") > 0) {

						length--;

						classes[length] = temp2.substring(0,
								temp2.indexOf(".class"));
						classes[length] = classes[length].replace('\\', '.');
						classes[length] = classes[length].replace('/', '.');
						// classes[length]=classes[length].substring(classes[length].indexOf("."));
					}

				}
				result = addClassNames(result, classes);
			}
		}
		return result;

	}
}
