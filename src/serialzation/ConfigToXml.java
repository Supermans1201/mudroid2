package serialzation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import singleton.Project;

public class ConfigToXml extends ToXml {
	int level = 13;
	final String projectToXML = "config";

	List<String> projectlist = null;
	boolean readproject = false;
	String selectProject = "";


	public String run(String[] args) throws Exception {
		if (args.length < 1) {
			printUsage("\nargs[0]:��Ŀ��Ŀ¼ args[1]:xml�ļ���ַ");
			return settingXmlName;// û�в����Ļ�ʲôҲ��������
		}
		if (args.length >= 1) {// ������չΪ����ļ�ͬʱ��ȡ��Ŀǰ����Ҫ
			if (checkFile(args[0]).toString().equals("null")) {
				System.out.println("configtoxml:Ŀ¼������");
				// System.out.println(settingXmlName);
				return settingXmlName;// Ŀ¼�����ھ�ʲôҲ��������
			}
			// ������ڣ����ڸ�Ŀ¼���½�һ���ļ�
			// �����Ľ�������Ŀ¼��
			SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmmss");// �������ڸ�ʽ
			settingXmlName = args[0] + "/" + projectToXML + "_"
					+ df.format(new Date()) + ".xml";
			File file = new File(settingXmlName);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			writer = createPrintWriter(settingXmlName);
			process(args[0]);
			// return "�ɹ�";
		}
		// �˴����������־��¼������ʲô����
		System.out.println(settingXmlName);
		return settingXmlName;

	}

	protected void process(String rootProject) throws Exception {
		if (!(Project.getInstance().getProjectlist() == null)) {
			recordConfig();
		}
		if (writer.equals(null))
			writer = createPrintWriter(null);
		File projectDir = new File(rootProject);
		writer.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
		writer.println("<!-- Date:" + df.format(new Date()) + " -->");// new
																		// Date()Ϊ��ȡ��ǰϵͳʱ��
		writer.println("<!-- Created by Supermans1201 -->\n");
		writer.println("<!-- rootPath:" + rootProject + " -->");
		writer.println("<" + projectToXML + " path=\""
				+ projectDir.getAbsolutePath() + "\">");
		writeConfigToXml();
		writer.println("</" + projectToXML + ">");
		writer.flush();

	}

	protected PrintWriter createPrintWriter(String xmlPath) throws Exception {
		// Ĭ���Լ�������·��
		if (level < 0)
			level = 0;
		if (level > 13)
			level = 13;
		xmlPath = xmlPath.substring(0, xmlPath.length() - ".xml".length()
				- level)
				+ ".xml";
		// Ĭ���Լ�������·��
		File file = new File(xmlPath);
		return new PrintWriter(file);
	}

	protected String checkFile(String arg) {
		File file = new File(arg);
		if (file.isDirectory()) {
			if (file.exists())
				return "Directory";
			// ��Ŀ¼���Ҵ��� �ŷ���Directory
		}
		return "null";
	}

	public void writeConfigToXml() throws IOException {
		if (projectlist != null) {
			writer.println("\t<selectProject name=\"" + selectProject + "\"/>");
			writer.println("\t<projectlist readproject=\"" + readproject
					+ "\">");
			for (int i = 0; i < projectlist.size(); i++) {
				writer.println("\t\t<project name=\"" + projectlist.get(i)
						+ "\"/>");
			}
			writer.println("\t</projectlist>");
		} else {
			writer.println("\t<selectProject name=\"" + selectProject + "\"/>");
			writer.println("\t<projectlist readproject=\"" + readproject
					+ "\">");
			writer.println("\t</projectlist>");
		}
		if(Project.getInstance().getsdkPath()==null)
		{writer.println("<SDK path=\"\"  >");
		writer.println("</SDK>");}
		else
		{writer.println("<SDK path="+Project.getInstance().getsdkPath()+">");
		writer.println("</SDK>");
		}
		
		writer.println("<emulator exePath=\""+Project.getInstance().getEmuExePath()+"\"  avdPath=\""+Project.getInstance().getEmuAvdPath()+"\" avdName=\""+Project.getInstance().getEmuAvdName()+"\">");
		writer.println("</emulator>");

	}

	public void recordConfig() {
		selectProject = Project.getInstance().getSelectProject();
		projectlist = Project.getInstance().getProjectlist();
		readproject = Project.getInstance().getReadProject();
	}


}
