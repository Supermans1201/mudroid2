package serialzation;

import org.junit.Test;

import singleton.FileList;

public class TestSummaryMutantFromXml {

	@Test
	public void test() {
		
			
			String[] args=new String[]{"F:/EspressoExamples-master/EspressoExamples-master/.Filter/mut/Filter_java.xml"};
			DealXmlSax smx=new SummaryMutantfromXml();
			try {
				smx.run(args);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			FileList.getInstance().readJavaMList();
			//	mutantJavaAndXmlList.getInstance().readJavaList();
			//	System.out.println(nameSet);
			//	System.out.println(opTypeSet);
			//	System.out.println(cmopSet);
			//	System.out.println(tmopSet);
			//	System.out.println(emopSet);
			//	System.out.println(amopSet);
			//	System.out.println(xmopSet);
			//		System.out.println(nameMap);
			//		System.out.println(name_opMap);
			//		System.out.println(opTypeMap);
			//		System.out.println(opType_opMap);
			//		System.out.println(name_tm_methodMap);
			//		System.out.println(name_opTypeMap);
	}

}
