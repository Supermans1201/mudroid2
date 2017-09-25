package javamutation;

import org.junit.Test;

import singleton.Project;
import util.GetFiles;
import util.GetInheritanceRelation;

public class TestDealJavaOJMutantClass {

	@Test
	public void test() {

		Project.getInstance().setSelectProject("F:/coolweather-master/Android_APP");
		GetFiles.getJarClassFiles();
	
		GetInheritanceRelation rir=new GetInheritanceRelation();
		try {
			rir.run(new String[]{Project.getInstance().getSelectProject()});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DealJavaOJMutant dj=new DealJavaOJMutantClass(); 
		try {
			dj.run(new String[] { "F:/coolweather-master/Android_APP/app/src/TimeManager.java" });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dj=new DealJavaOJMutantTradtional(); 
		try {
			dj.run(new String[] { "F:/coolweather-master/Android_APP/app/src/TimeManager.java" });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dj=new DealJavaOJMutantAndroid(); 
		try {
			dj.run(new String[] { "F:/coolweather-master/Android_APP/app/src/TimeManager.java" });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dj=new DealJavaOJMutantException(); 
		try {
			dj.run(new String[] { "F:/coolweather-master/Android_APP/app/src/TimeManager.java" });
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
