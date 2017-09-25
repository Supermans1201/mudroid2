package util;

import org.junit.Test;

import singleton.Project;

public class TestDeleteDirctory {

	@Test
	public void test() {
		Project.getInstance().setSelectProject("F:/EspressoExamples-master/EspressoExamples-master/");
    	DeleteDir.deleteMut();
	}

}
