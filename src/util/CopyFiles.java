package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CopyFiles {
	
	public static void main(String[] args)
	{
		File fromDir= new File("F:/EspressoExamples-master/EspressoExamples-master/app/src");
		File toDir=new File("F:/test/test/test");
		CopyFiles.copyDir(fromDir, toDir, true);
//		System.out.println("复制完毕！");
	}

	public static void copyFile(File fromFile, File toFile, Boolean rewrite) {

		if (!fromFile.exists()) {
			return;
		}
		if (!fromFile.isFile()) {
			return;
		}
		if (!fromFile.canRead()) {
			return;
		}
		if(fromFile.getAbsolutePath().equals(toFile.getAbsolutePath()))
		{
			return;
		}
		if (!toFile.getParentFile().exists()) {
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() && rewrite) {
			toFile.delete();
		}
		try {
			FileInputStream fosfrom = new FileInputStream(fromFile);
			FileOutputStream fosto = new FileOutputStream(toFile);

			byte[] bt = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0) {
				fosto.write(bt, 0, c);
			}
			// 关闭输入、输出流
			fosfrom.close();
			fosto.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void copyDir(File fromDir,File toDir,Boolean rewrite)
	{
//		System.out.println("from: "+fromDir.getAbsolutePath());
//		System.out.println("to: "+toDir.getAbsolutePath());
		if (!fromDir.exists()) {
			return;
		}
		if (!fromDir.isDirectory()) {
			return;
		}
		if (!fromDir.canRead()) {
			return;
		}
		if (!toDir.getParentFile().exists()) {
			toDir.getParentFile().mkdirs();
		}
		if (toDir.exists() && rewrite) {
			DeleteDir.deleteDir(toDir);
		}
		
		for(File file :fromDir.listFiles())
		{
			if(file.isFile())
			{
				copyFile(file,new File(file.getAbsolutePath().replace(fromDir.getAbsolutePath(), toDir.getAbsolutePath())),true);
			}
			else if(file.isDirectory())
			{
				copyDir(file,new File(file.getAbsolutePath().replace(fromDir.getAbsolutePath(), toDir.getAbsolutePath())),true);
			}
			
		}
		
	}

}
