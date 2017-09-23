package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import serialzation.ProjectDirToXmlWithFilter;
import serialzation.ToXml;
import singleton.FileList;
import singleton.Project;

public class GetFiles {

	public static void getMutFiles() {
		ToXml tx = new ProjectDirToXmlWithFilter();
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getJavaMutPath(), Project.FilterName,
					"java" });
			System.out.println("loc3:" + location);
			location = fileMove(location, Project.getInstance().getFilterDir()
					+ "/mut");

			Project.getInstance().setJavaMutFilterLoc(location);
			FileList.getInstance().setJavaMList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getXmlMutPath(), Project.FilterName,
					"xml" });
			System.out.println("loc4:" + location);
			location = fileMove(location, Project.getInstance().getFilterDir()
					+ "/mut");

			Project.getInstance().setXmlMutFilterLoc(location);
			FileList.getInstance().setXmlMList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void getSrcFiles() {
		ToXml tx = new ProjectDirToXmlWithFilter();
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getJavaSrcPath(), Project.FilterName,
					"java" });
			
			location = fileMove(location, Project.getInstance().getFilterDir()
					+ "/src");
			System.out.println("loc1:" + location);
			Project.getInstance().setJavaSrcFilterLoc(location);
			FileList.getInstance().setJavaList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getXmlSrcPath(), Project.FilterName,
					"xml" });
			
			location = fileMove(location, Project.getInstance().getFilterDir()
					+ "/src");
			System.out.println("loc2:" + location);
			Project.getInstance().setXmlSrcFilterLoc(location);
			FileList.getInstance().setXmlList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public static void getJarClassFiles() {

		ToXml tx = new ProjectDirToXmlWithFilter();
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getSelectProject(),
					Project.FilterName, "jar" });
			
			location = fileMove(location, Project.getInstance().getFilterDir());
			System.out.println("loc5:" + location);
			Project.getInstance().setJarFilterLoc(location);
			FileList.getInstance().setJarList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			String location = tx.run(new String[] {
					Project.getInstance().getSelectProject(),
					Project.FilterName, "class" });
			
			location = fileMove(location, Project.getInstance().getFilterDir());
			System.out.println("loc6:" + location);
			Project.getInstance().setClassFilterLoc(location);
			FileList.getInstance().setClassList(
					((ProjectDirToXmlWithFilter) tx).getList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String fileMove(String filepath, String dirpath) {
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		if (filepath == null) {
			return null;
		}
		if (dirpath == null) {
			return null;
		}
		File file = new File(filepath);
		if (!file.exists()) {
			return null;
		}
		File desfile = new File(dirpath + "/" + file.getName());

		if (file.getAbsolutePath().equals(desfile.getAbsolutePath())) {
			return desfile.getAbsolutePath();
		}

		InputStream inStream = null;
		OutputStream outStream = null;

		try {

			inStream = new FileInputStream(file);
			outStream = new FileOutputStream(desfile);

			byte[] buffer = new byte[1024];
			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);

			}

			inStream.close();
			outStream.close();

			// delete the original file
			File parentDir = file.getParentFile();
			file.delete();
			parentDir.delete();

			return desfile.getAbsolutePath();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	public static void printFilesList() {
		System.out.println("******************");
		FileList.getInstance().readJavaList();
		System.out.println("******************");
		FileList.getInstance().readJavaMList();
		System.out.println("******************");
		FileList.getInstance().readXmlList();
		System.out.println("******************");
		FileList.getInstance().readXmlMList();
		System.out.println("******************");
		FileList.getInstance().readJarList();
		System.out.println("******************");
		FileList.getInstance().readClassList();
	}
	public static void printFiltetloc() {
		System.out.println(Project.getInstance().getJavaSrcFilterLoc());
		System.out.println(Project.getInstance().getXmlSrcFilterLoc());
		System.out.println(Project.getInstance().getJavaMutFilterLoc());
		System.out.println(Project.getInstance().getXmlMutFilterLoc());
		System.out.println(Project.getInstance().getJarFilterLoc());
		System.out.println(Project.getInstance().getClassFilterLoc());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Project.getInstance().setSelectProject("F:/mudroid3/Android_APP");
		getFiiles();
		// printFilesList();
		 printFiltetloc();
		

	}

	private static void getFiiles() {
		// TODO Auto-generated method stub
		getSrcFiles();
		getMutFiles();
		getJarClassFiles();
	}

}
