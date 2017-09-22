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
