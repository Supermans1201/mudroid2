package util;

import org.junit.Test;

import singleton.Project;

public class TestGetFiles {

	@Test
	public void test() {
		Project.getInstance().setSelectProject("F:/EspressoExamples-master/EspressoExamples-master/");
		GetFiles.getFiiles();
		// printFilesList();
		GetFiles.printFiltetloc();
	}

}
