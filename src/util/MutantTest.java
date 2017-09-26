package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import singleton.Project;

public class MutantTest {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Project.getInstance().setSelectProject(
				"F:/EspressoExamples-master/EspressoExamples-master");
		// TODO Auto-generated method stub
		try {
			System.out.println("run test class Op>>>>>>>");
			Process p = null;
			String line = null;
			BufferedReader stdout = null;
			BufferedWriter bw = null;
			bw = new BufferedWriter(new FileWriter(Project.getInstance()
					.getSelectProject() + "/CO.log"));
			// list the files and directorys
			// under C:\
			p = Runtime.getRuntime().exec(
					Project.getInstance().getSelectProject()
							+ "/gradlew.bat connectAndroidTest", null,
					new File(Project.getInstance().getSelectProject()));
			stdout = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				System.out.println(line);
				bw.write(line);
				bw.newLine();
				;
				bw.flush();
				// Thread.sleep( 10 );
			}

			stdout.close();
			p.destroy();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}