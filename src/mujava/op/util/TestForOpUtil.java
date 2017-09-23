package mujava.op.util;

import java.io.PrintWriter;


public class TestForOpUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		{
//			test.MakeMuAndroidStructure.main(null);
//			CodeChangeLog.openLogFile();
//			CodeChangeLog.writeLog("test for op util");
//			CodeChangeLog.closeLogFile();
//		}
	//	CodeChangeLog.writeLog("test for op util");
		{
			PrintWriter out = null;
			MutantCodeWriter mcw=new MutantCodeWriter(out);
			String str="0123456789";
			str=str.substring(0,str.length()-1);
			System.out.println(str);
			System.out.println(mcw.removeNewline("12345\n6\n789\n10\n\n"));
			
		}
	
	}

}
