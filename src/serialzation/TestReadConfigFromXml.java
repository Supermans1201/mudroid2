package serialzation;

import org.junit.Test;

import singleton.Project;
import dealxml.DealXmlSax;

public class TestReadConfigFromXml {

	@Test
	public void test() {

		// configpath 有默认值，即程序运行位置+"/config.xml"
		String[] args = new String[] { Project.getInstance().getConfigPath() };
		DealXmlSax dxs=new ReadConfigFromXml();
		try {
			dxs.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		org.junit.Assert.assertNotNull(Project.getInstance().getProjectlist());
		org.junit.Assert.assertNotNull(Project.getInstance().getSelectProject());
		org.junit.Assert.assertNotNull(Project.getInstance().getReadProject());
		
		Project.getInstance().readProjectlist();
		
	}
}
