package serialzation;

import org.junit.Test;

public class TestProjectDirToXmlWithFilter {

	@Test
	public void test() {

		String[] args = new String[] { "F:/EspressoExamples-master/EspressoExamples-master",
				"Filter","java" };
		System.out.println(args[0]);
		

		ToXml tx = new ProjectDirToXmlWithFilter();
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		args = new String[] { "F:/EspressoExamples-master/EspressoExamples-master",
				"Filter","xml" };
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		args = new String[] { "F:/EspressoExamples-master/EspressoExamples-master",
				"Filter","jar" };
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		args = new String[] { "F:/EspressoExamples-master/EspressoExamples-master",
				"Filter","class" };
		try {
			tx.run(args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}
}
