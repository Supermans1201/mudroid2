package util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import serialzation.ToXml;
import singleton.FileList;
import singleton.InheritanceRelation;
import mujava.util.InheritanceINFO;

public class RecordInheritanceRelationToXml extends ToXml {
	int level = 13;
	final String projectToXML = "InheritanceRelation";

	final String classesDebugPath = "/app/build/intermediates/classes/debug";
	String class_Debug;

	List<String> list = new ArrayList<String>();
	InheritanceINFO[] classInfo = null;

	public List<String> getList() {
		return this.list;
	}

	public void setClassesDubug(String root) {
		this.class_Debug = root + classesDebugPath;
	}

	public InheritanceINFO[] getInheritanceInfo() {
		return this.classInfo;
	}

	public String run(String[] args) throws Exception {
		if (args.length < 1) {
			printUsage("\nargs[0]:项目根目录 args[1]:xml文件地址");
			return settingXmlName;// 没有参数的话什么也不用做了
		}
		if (args.length >= 1) {// 可以扩展为多个文件同时获取。目前不需要
			if (checkFile(args[0]).toString().equals("null")) {
				System.out.println("目录不存在");
				// System.out.println(settingXmlName);
				return settingXmlName;// 目录不存在就什么也不用做了
			}

			setClassesDubug(args[0]);
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// 设置日期格式

			settingXmlName = args[0] + "/." + projectToXML + "/" + projectToXML
					+ "_" + df.format(new Date()) + ".xml";
			File file = new File(settingXmlName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			// //不需要创建文件
			// file.createNewFile();

			writer = createPrintWriter(settingXmlName);
			process(args[0]);
			// return "成功";
		}
		// 此处可以添加日志记录进行了什么操作
		System.out.println(settingXmlName);
		return settingXmlName;

	}

	protected void process(String rootProject) throws Exception {

		recordInheritanceRelation();
		if (writer.equals(null))
			writer = createPrintWriter(null);
		File projectDir = new File(rootProject);
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		writer.println("<!-- Date:" + df.format(new Date()) + " -->");// new
																		// Date()为获取当前系统时间
		writer.println("<!-- Created by Supermans1201 -->\n");
		writer.println("<!-- rootPath:" + rootProject + " -->");
		writer.println("<" + projectDir.getName() + " path=\""
				+ projectDir.getAbsolutePath() + "\"" + " name=\""
				+ projectDir.getName() + "\" depth=\"0\">");
		tree(rootProject, 0);

		for (int j = 1; j <= lastcount - count - 1; j++) {
			for (int i = lastcount - j; i > 0; i--) {
				writer.print("\t");
			}
			writer.println("</Dir>");
		}
		;

		writer.println("</" + projectDir.getName() + ">");
		writer.flush();
		InheritanceRelation.getInstance().setInheritanceInfo(classInfo);
	}

	protected PrintWriter createPrintWriter(String xmlPath) throws Exception {
		// 默认以及创建好路径
		if (level < 0)
			level = 0;
		if (level > 13)
			level = 13;
		xmlPath = xmlPath.substring(0, xmlPath.length() - ".xml".length()
				- level)
				+ ".xml";
		// 默认以及创建好路径
		File file = new File(xmlPath);
		return new PrintWriter(file);
	}

	protected String checkFile(String arg) {
		File file = new File(arg);
		if (file.isDirectory()) {

			if (file.exists())
				return "Directory";
			// 是目录并且存在 才返回Directory
		}
		return "null";
	}

	int count = 0;
	int lastcount = 0;

	public int tree(String path, int c) throws IOException {
		count++;
		lastcount = c;
		File f = new File(path);
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File fileIndex : files) {
				// System.out.println((lastcount+0)+":"+count);//引入count与lastcount的关键调试步骤
				if (lastcount + 1 == count) {
					;
				} else {
					for (int j = 1; j <= lastcount - count; j++) {
						for (int i = lastcount - j; i > 0; i--) {
							writer.print("\t");
						}
						writer.println("</Dir>");
					}
				}

				if (fileIndex.isDirectory()) {
					for (int i = count; i > 0; i--) {
						writer.print("\t");
					}
					writer.print("<Dir" + " path=\""
							+ fileIndex.getAbsolutePath() + "\"" + " name=\""
							+ fileIndex.getName() + "\" " + "depth=\"" + count
							+ "\">");
					boolean b = false;// b为false表示不存在子目录
					for (@SuppressWarnings("unused") File subfile : fileIndex.listFiles()) {
						b = true;
					}
					if (!b) {
						writer.println("</Dir>");
					} else {
						writer.println("");
					}
					tree(fileIndex.getPath(), count);
				} else {
					for (int i = count; i > 0; i--) {
						writer.print("\t");
					}
					writer.println("<File" + " path=\""
							+ fileIndex.getAbsolutePath() + "\"" + " name=\""
							+ fileIndex.getName() + "\" " + "depth=\"" + count
							+ "\">" + "</File>");
					lastcount = count;
				}
			}
		} else {

		}
		count--;
		return 0;
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

		for (int i = 0; i < list.size(); i++) {
			// System.err.println("MMMMMM"+list.get(i));
			addURL(list.get(i));
		}
		// addURL("F:/muandroid3/AndroidApp-master/app/build/intermediates/exploded-aar/com.android.support");
		if (classes == null) {
			System.err.println("[ERROR] There are no classes to mutate.");
			System.err
					.println(" Please check the directory  "
							+ this.class_Debug
							+ " and be sure that MuJava_HOME is set correctly (without a trailing slash) in mujava.config.");
			//Runtime.getRuntime().exit(0);
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

				JarFile jarFile = new JarFile(temp);
				Enumeration<JarEntry> entrys = jarFile.entries();
				int length = 0;
				//System.out.println("\ni start:" + length);
				while (entrys.hasMoreElements()) {
					JarEntry jarEntry = entrys.nextElement();
					String temp2 = jarEntry.getName();
					if (temp2.indexOf(".class") > 0) {
						length++;
					} else {
						// System.err.println("qqq"+temp2);
					}
				}
				//System.out.println("\ni end:" + length);
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
		}

		return result;

	}
}
