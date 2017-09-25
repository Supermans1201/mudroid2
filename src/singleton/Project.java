package singleton;

import java.io.File;
import java.util.List;

public class Project {
	private static Project instance;

	private Project() {
	}

	Boolean readProject;
	String selectProject;
	List<String> projectlist = null;

	String javaSrcDir = "/app/src/main/java";
	String xmlSrcDir = "/app/src/main/res/layout";
	String manifestSrcDir = "/app/src/main";

	String javaMutDir = "/app/mutant/main/java";
	String xmlMutDir = "/app/mutant/main/res/layout";
	String manifestMutDir = "/app/mutant/main";
	
	String mutDir="/app/mutant";
	
	public String getMutDir()
	{
		return Project.getInstance().getSelectProject()+mutDir;
	}

	public static String FilterName = "Filter";

	String javaSrcFilterLoc;
	String xmlSrcFilterLoc;
	String javaMutFilterLoc;
	String xmlMutFilterLoc;
	String jarFilterLoc;
	String classFilterLoc;

	public String getFilterDir() {
		return this.selectProject+"/."+FilterName;
	}
	
	public void setJavaSrcFilterLoc(String location) {
		this.javaSrcFilterLoc = location;
	}

	public void setXmlSrcFilterLoc(String location) {
		this.xmlSrcFilterLoc = location;
	}

	public void setJavaMutFilterLoc(String location) {
		this.javaMutFilterLoc = location;
	}

	public void setXmlMutFilterLoc(String location) {
		this.xmlMutFilterLoc = location;
	}

	public void setJarFilterLoc(String location) {
		this.jarFilterLoc = location;
	}

	public void setClassFilterLoc(String location) {
		this.classFilterLoc = location;
	}

	public String getJavaSrcFilterLoc() {
		return this.javaSrcFilterLoc;
	}

	public String getXmlSrcFilterLoc() {
		return this.xmlSrcFilterLoc;
	}

	public String getJavaMutFilterLoc() {
		return javaMutFilterLoc;
	}

	public String getXmlMutFilterLoc() {
		return this.xmlMutFilterLoc;
	}

	public String getJarFilterLoc() {
		return this.jarFilterLoc;
	}

	public String getClassFilterLoc() {
		return this.classFilterLoc;
	}

	public String getJavaSrcPath() {
		return Project.getInstance().getSelectProject() + javaSrcDir;
	}

	public String getXmlSrcPath() {
		return Project.getInstance().getSelectProject() + xmlSrcDir;
	}

	public String getmanifestSrcPath() {
		return Project.getInstance().getSelectProject() + manifestSrcDir;
	}

	public String getJavaMutPath() {
		return Project.getInstance().getSelectProject() + javaMutDir;
	}

	public String getXmlMutPath() {
		return Project.getInstance().getSelectProject() + xmlMutDir;
	}

	public String getmanifestMutPath() {
		return Project.getInstance().getSelectProject() + manifestMutDir;
	}

	public String getConfigPath() {
		File directory = new File("");
		return directory.getAbsolutePath().replace("\\", "/") + "/config.xml";
	}

	public String getConfigDir() {
		File directory = new File("");
		return directory.getAbsolutePath().replace("\\", "/");
	}

	public void setReadProject(Boolean readProject) {
		this.readProject = readProject;
	}

	public Boolean getReadProject() {
		return this.readProject;
	}

	public void setSelectProject(String selectProject) {
		this.selectProject = selectProject;
	}

	public String getSelectProject() {
		return this.selectProject;
	}

	public void setProjectlist(List<String> projectlist) {
		this.projectlist = projectlist;
		removeNull();
	}

	public List<String> getProjectlist() {
		return this.projectlist;
	}

	public void readProjectlist() {
		for (int i = 0; i < this.projectlist.size(); i++)
			System.out.println(this.projectlist.get(i));
	}

	public void removeNull() {
		for (int i = 0; i < this.projectlist.size(); i++) {
			if (projectlist.get(i) == null) {
				projectlist.remove(i);
			}
		}
	}

	public static Project getInstance() {
		if (instance == null) {
			instance = new Project();
		}
		return instance;
	}
}
