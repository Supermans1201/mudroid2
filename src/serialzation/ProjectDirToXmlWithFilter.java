package serialzation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import singleton.Project;

public class ProjectDirToXmlWithFilter extends ToXml {
	int level = 13;
	String projectToXML = "projectToXMLs";
	String filterType = "file";

	List<String> list = new ArrayList<String>();

	public List<String> getList() {
		return this.list;
	}

	public void setFileType(String filterType) {
		this.filterType = filterType;
	}

	public String run(String[] args) throws Exception {
		
		//每次执行时至空
		settingXmlName = null;
		list = new ArrayList<String>();
		
		if (args.length < 1) {
			printUsage("\nargs[0]:项目根目录 args[1]:xml文件地址");
			return settingXmlName;// 没有参数的话什么也不用做了
		}

		if (args.length >= 1) {// 可以扩展为多个文件同时获取。目前不需要

			if (checkFile(args[0]).toString().equals("null")) {

				// System.out.println(settingXmlName);
				return settingXmlName;// 目录不存在就什么也不用做了
			}
			// 如果存在，就在该目录下新建一个文件
			if (args.length == 2) {
				projectToXML = args[1];
			}
			if (args.length == 3) {
				projectToXML = args[1];
				setFileType(args[2]);
			}

			// 分析的结果输出到目录中
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// 设置日期格式

			settingXmlName = args[0] + "/." + projectToXML + "/" + projectToXML
					+ "_" + df.format(new Date()) + ".xml";
			File file = new File(settingXmlName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();

			writer = createPrintWriter(settingXmlName);
			process(args[0]);
			// return "成功";
		}

		// 此处可以添加日志记录进行了什么操作
		settingXmlName = settingXmlName.substring(0, settingXmlName.length()
				- ".xml".length() - level)
				+ "_" + filterType + ".xml";
		// System.out.println(settingXmlName);
		return settingXmlName;

	}

	protected void process(String rootProject) throws Exception {
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

		writer.println("</" + projectDir.getName() + ">");
		writer.flush();
	}

	protected PrintWriter createPrintWriter(String xmlPath) throws Exception {
		// 默认以及创建好路径
		if (level < 0)
			level = 0;
		if (level > 13)
			level = 13;
		xmlPath = xmlPath.substring(0, xmlPath.length() - ".xml".length()
				- level)
				+ "_" + filterType + ".xml";
		// 默认以及创建好路径
		File file = new File(xmlPath);
		return new PrintWriter(file);
	}

	protected Boolean hasString(String file, String type) {
		// System.out.println(file);
		if (type == "" || type == "file")
			return true;
		if (file.lastIndexOf('.') > 0) {
			// System.out.println(file.indexOf('.'));
			// System.out.println(file.substring(file.indexOf('.')+1)+":"+type);
			if (file.substring(file.lastIndexOf('.') + 1).equals(type)) {
				// System.out.println(file.substring(file.indexOf('.')));
				return true;
			}
		}
		return false;
	}

	protected String checkFile(String arg) {
		if (arg == null) {
			return "null";
		}

		File file = new File(arg);
		if (!file.exists())
			return "null";

		if (file.isDirectory()) {
			return "Directory";
		} else {
			return "File";
		}

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
				if (fileIndex.isDirectory()) {
					tree(fileIndex.getPath(), count);
				} else {
					
					
					if (hasString(fileIndex.getName(), filterType)) {
						
						
						for (int i = count; i > 0; i--) {
							writer.print("\t");
						}
						if(fileIndex.getName().startsWith(Project.FilterName))
						{
							//System.out.println(fileIndex.getAbsolutePath());;
						}
						else
						{	
							list.add(fileIndex.getAbsolutePath());
						
						 writer.println("<File" + " path=\""
									+ fileIndex.getAbsolutePath() + "\""
									+ " name=\"" + fileIndex.getName() + "\" "
									+ "depth=\"" + count + "\"/>");
							lastcount = count;
							
						}
						
						
					}
				}
			}
		}
		count--;
		return 0;
	}

}
