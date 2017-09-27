package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import singleton.FileList;
import singleton.InheritanceRelation;
import singleton.Project;
import mujava.util.InheritanceINFO;

public class GetInheritanceRelation {

	final String classesDebugPath = "/app/build/intermediates/classes/debug";
	String class_Debug;

	List<String> list = new ArrayList<String>();
	InheritanceINFO[] classInfo = null;

	public List<String> getList() {
		return this.list;
	}

	public void setClassesDebug(String root) {
		this.class_Debug = root + classesDebugPath;
	}

	public InheritanceINFO[] getInheritanceInfo() {
		return this.classInfo;
	}

	public void run(String []args) {
		
		this.setClassesDebug(args[0]);
		try {
			recordInheritanceRelation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InheritanceRelation.getInstance().setInheritanceInfo(classInfo);
	}

	private final String[] addClassNames(String[] list1, String[] list2) {
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
	public String[] getAllClassNames(String[] result, String dir) {
		String[] classes;// 为何又用string[]去存？
		String temp;
		File dirF = new File(dir);

		File[] classF = dirF.listFiles(new ExtensionFilter("class"));
		if (classF != null) {
			classes = new String[classF.length];
			for (int k = 0; k < classF.length; k++) {
				temp = classF[k].getAbsolutePath();
				// System.out.println(temp);
				classes[k] = temp.substring(this.class_Debug.length() + 1,
						temp.length() - ".class".length());
				classes[k] = classes[k].replace('\\', '.');
				classes[k] = classes[k].replace('/', '.');
				// System.out.println(classes[k]);
				// example:mujava.op.exception.ETC_Writer
			}
			result = addClassNames(result, classes);
		}

		File[] sub_dir = dirF.listFiles(new DirFileFilter());
		if (sub_dir == null)
			return result;

		for (int i = 0; i < sub_dir.length; i++) {
			result = getAllClassNames(result, sub_dir[i].getAbsolutePath());
		}
		return result;
	}

	public void recordInheritanceRelation() throws Exception {
		String[] classes = null;
		classes = getAllClassNames(classes, this.class_Debug);

		list = FileList.getInstance().getJarList();
		classes = getAllClassNamesInJARSlist(classes, list);

	
		// addURL("F:/muandroid3/AndroidApp-master/app/build/intermediates/exploded-aar/com.android.support");
		if (classes == null) {
			System.err.println("[ERROR] There are no classes to mutate.");
			System.err
					.println(" Please check the directory  "
							+ this.class_Debug
							+ " and be sure that MuJava_HOME is set correctly (without a trailing slash) in mujava.config.");
			// Runtime.getRuntime().exit(0);
		}
		classInfo = new InheritanceINFO[classes.length];
		// System.out.println(""+classInfo.length);
		boolean[] bad = new boolean[classes.length];
		
		addURL(this.class_Debug);
		System.err.println(class_Debug);
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
				System.err.println("[ERROR] Can't find the class: "
						+ classes[i]);
				System.err
						.println("Please check that the compiled class for the code you want to mutate is in the classes/ directory. Also check that the MuJava_HOME variable in mujava.config does not end with a trailing slash. ");
				bad[i] = true;
				classInfo[i] = new InheritanceINFO(classes[i], "");

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

	public void addURL(String classPath) throws Exception {
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

	public String[] getAllClassNamesInJARSlist(String[] result,
			List<String> list) throws IOException {
		String[] classes;// 为何又用string[]去存？
		String temp;
		String[] classF = null;

		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if (!s.endsWith(".jar")) {
				list.remove(list.get(i));
			}
		}
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
				
				String newcopyjar=Project.getInstance().getSelectProject()+"/jar/"+new File(temp).getName();
				
				try
				{
					CopyFiles.copyFile(new File(temp), new File(newcopyjar), true);
					try {
						addURL(newcopyjar);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					JarFile jarFile = new JarFile(newcopyjar);
					Enumeration<JarEntry> entrys = jarFile.entries();
					int length = 0;
					//System.out.print("start:" + length+"   ");
					while (entrys.hasMoreElements()) {
						JarEntry jarEntry = entrys.nextElement();
						String temp2 = jarEntry.getName();
						if (temp2.indexOf(".class") > 0) {
							length++;
						} else {
							// System.err.println("qqq"+temp2);
						}
					}
					//System.out.println("end:" + length);
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
					jarFile.close();
				}
				catch(FileNotFoundException e)
				{
					
				}
				
				
			
			}
		}
		return result;
	}
}
