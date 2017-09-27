package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import singleton.Project;

public class MutantTest {

	@SuppressWarnings("resource")
	public static void run(String[] args) {
		// TODO Auto-generated method stub
		try {
			System.out.println("run test mutant>>>>>>>");
			Process p = null;
			String line = null;
			BufferedReader stdout = null;
			BufferedWriter bw = null;
			
			bw = new BufferedWriter(new FileWriter(Project.getInstance().getRunTestLogPath(), true));
			
			bw.write("#"+args[0]+" "+args[1]+ " "+args[2]+"\n");
			
			bw.newLine();
			// list the files and directorys
			// under C:\
			p = Runtime.getRuntime().exec(
					Project.getInstance().getSelectProject()
							+ "/gradlew.bat connectAndroidTest ", null,
					new File(Project.getInstance().getSelectProject()));
			stdout = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			while ((line = stdout.readLine()) != null) {
				if (!line.startsWith(":")) {
					System.out.println(line);
					
				}
				
				bw.write(line);
				bw.newLine();
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