package singleton;

import java.util.ArrayList;
import java.util.List;

public class FileList {
	private static FileList instance;

	private FileList() {
	}

	List<String> javaSList = new ArrayList<String>();
	List<String> xmlSList = new ArrayList<String>();

	List<String> MFlist = new ArrayList<String>();

	List<String> javaList = new ArrayList<String>();
	List<String> xmlList = new ArrayList<String>();

	List<String> javaMList = new ArrayList<String>();
	List<String> xmlMList = new ArrayList<String>();

	List<String> jarList = new ArrayList<String>();
	List<String> classList = new ArrayList<String>();

	public void setJarList(List<String> jarList) {
		this.jarList = jarList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public List<String> getJarList() {
		return this.jarList;
	}

	public List<String> getClassList() {
		return this.classList;
	}

	public void readJarList() {
		for (int i = 0; i < jarList.size(); i++)
			System.out.println(jarList.get(i));
	}

	public void readClassList() {
		for (int i = 0; i < classList.size(); i++)
			System.out.println(classList.get(i));
	}

	public void setJavaSList(List<String> javaSList) {
		this.javaSList = javaSList;
	}

	public void readJavaSList() {
		for (int i = 0; i < javaSList.size(); i++)
			System.out.println(javaSList.get(i));
	}

	public void readXmlSList() {
		for (int i = 0; i < xmlSList.size(); i++)
			System.out.println(xmlSList.get(i));
	}

	public void setXmlSList(List<String> xmlSList) {
		this.xmlSList = xmlSList;
	}

	public List<String> getJavaSList() {
		return this.javaSList;
	}

	public List<String> getXmlSList() {
		return this.xmlSList;
	}

	public void setMFlist(List<String> MFlist) {
		this.MFlist = MFlist;
	}

	public void readMFlist() {
		for (int i = 0; i < MFlist.size(); i++)
			System.out.println(MFlist.get(i));
	}

	public List<String> getMFlist() {
		return this.MFlist;
	}

	public void removeMFlist() {
		this.MFlist.removeAll(MFlist);
	}

	public void removeJavaSList() {
		this.javaList.removeAll(javaSList);
	}

	public void removeXmlSList() {
		this.xmlList.removeAll(xmlSList);
	}

	public List<String> getXmlMFlist() {
		return this.xmlMList;
	}

	public void setJavaList(List<String> javaList) {
		this.javaList = javaList;
	}

	public void readJavaList() {
		for (int i = 0; i < javaList.size(); i++)
			System.out.println(javaList.get(i));
	}

	public void readXmlList() {
		for (int i = 0; i < xmlList.size(); i++)
			System.out.println(xmlList.get(i));
	}

	public void setXmlList(List<String> xmlList) {
		this.xmlList = xmlList;
	}

	public List<String> getJavaList() {
		return this.javaList;
	}

	public List<String> getXmlList() {
		return this.xmlList;
	}

	public void setJavaMList(List<String> javaMList) {
		this.javaMList = javaMList;
	}

	public void readJavaMList() {
		for (int i = 0; i < javaMList.size(); i++)
			System.out.println(javaMList.get(i));
	}

	public void readXmlMList() {
		for (int i = 0; i < xmlMList.size(); i++)
			System.out.println(xmlMList.get(i));
	}

	public void setXmlMList(List<String> xmlMList) {
		this.xmlMList = xmlMList;
	}

	public List<String> getJavaMList() {
		return this.javaMList;
	}

	public void removeJavaMList() {
		this.javaMList.removeAll(javaMList);
	}

	public void removeJavaList() {
		this.javaList.removeAll(javaList);
	}

	public void removeXmlMList() {
		this.xmlMList.removeAll(xmlMList);
	}

	public void removeXmlList() {
		this.xmlList.removeAll(xmlList);
	}

	public List<String> getXmlMList() {
		return this.xmlMList;
	}

	List<String> javaMSList = new ArrayList<String>();
	List<String> xmlMSList = new ArrayList<String>();

	public void setJavaMSList(List<String> javaMSList) {
		this.javaMSList = javaMSList;
	}

	public void readJavaMSList() {
		for (int i = 0; i < javaMSList.size(); i++)
			System.out.println(javaMSList.get(i));
	}

	public void readXmlMSList() {
		for (int i = 0; i < xmlMSList.size(); i++)
			System.out.println(xmlMSList.get(i));
	}

	public void setXmlMSList(List<String> xmlMSList) {
		this.xmlMSList = xmlMSList;
	}

	public List<String> getJavaMSList() {
		return this.javaMSList;
	}

	public void removeJavaMSList() {
		this.javaMSList.removeAll(javaMSList);
	}

	public List<String> getXmlMSList() {
		return this.xmlMList;
	}

	public static FileList getInstance() {
		if (instance == null) {
			instance = new FileList();
		}
		return instance;
	}

}
