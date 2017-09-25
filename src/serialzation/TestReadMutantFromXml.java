package serialzation;

import org.junit.Test;

import singleton.FileList;

public class TestReadMutantFromXml {

	@Test
	public void test() {
		String[] args = new String[] {
				"F:/EspressoExamples-master/EspressoExamples-master/.Filter/mut/Filter_java.xml",
				"RecyclerViewActivity.java", "all", "classOp", "all", "all" };
		ReadMutantfromXml rmx= new ReadMutantfromXml();
		try {
			rmx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileList.getInstance().readMFlist();
	}

}
