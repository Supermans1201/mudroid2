package serialzation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import singleton.Project;

public class TestConfigToXml {

	@Test
	public void test() {

		String[] args = new String[] { Project.getInstance().getConfigDir() };
		System.out.println(args[0]);
		assertEquals(args[0], "F:/workspace/testgetdir");

		Project.getInstance().setReadProject(true);
		List<String> pl = new ArrayList<String>();
		pl.add("F:/EspressoExamples-master/EspressoExamples-master");
		pl.add("F:/mudroid3/Android_APP");
		Project.getInstance().setSelectProject(
				"F:/EspressoExamples-master/EspressoExamples-master");
		Project.getInstance().setProjectlist(pl);

		ToXml tx = new ConfigToXml();
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

}
