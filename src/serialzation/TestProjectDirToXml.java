package serialzation;

import static org.junit.Assert.*;

import org.junit.Test;

import singleton.Project;

public class TestProjectDirToXml {

	@Test
	public void test() {

		String[] args = new String[] { Project.getInstance().getConfigDir(),"files" };
		System.out.println(args[0]);
		assertEquals(args[0], "F:/workspace/testgetdir");

		
		ToXml tx = new ProjectDirToXml();
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

}
