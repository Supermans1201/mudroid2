package util;

import gui.ProgressJFrame2;

import java.io.File;

import singleton.Project;

public class MutantRunManager {
	
	public MutantRunManager()
	{
	}
	public void start() {
		// TODO Auto-generated method stub

		File fromDir= new File(Project.getInstance().getSelectProject()+"/app/src");
		File toDir=new File(Project.getInstance().getSelectProject()+"/copyofsrc");
		CopyFiles.copyDir(fromDir, toDir, true);
		System.out.println("复制完毕！");
	}
	
	public void commit(String type,float size) {
		
		if(type==null)
		{
			return ;
		}

		if(type.equals("c"))
		{
//			System.out.println(":"+size);
			ProgressJFrame2.getInstance().jpbcm.setValue((int) (100*size)+1);
		}
		if(type.equals("t"))
		{
//			System.out.println(":"+size);
			ProgressJFrame2.getInstance().jpbtm.setValue((int) (100*size)+1);
		}
		if(type.equals("e"))
		{
//			System.out.println(":"+size);
			ProgressJFrame2.getInstance().jpbem.setValue((int) (100*size)+1);
		}
		if(type.equals("a"))
		{
//			System.out.println(":"+size);
			ProgressJFrame2.getInstance().jpbam.setValue((int) (100*size)+1);
		}
		if(type.equals("x"))
		{
//			System.out.println(":"+size);
			ProgressJFrame2.getInstance().jpbxm.setValue((int) (100*size)+1);
		}
		// TODO Auto-generated method stub
		
	}

	public void rollback() {
		// TODO Auto-generated method stub

		File fromDir= new File(Project.getInstance().getSelectProject()+"/app/src");
		File toDir=new File(Project.getInstance().getSelectProject()+"/copyofsrc");
		CopyFiles.copyDir(toDir, fromDir, true);
		System.out.println("复制完毕！");
	}

	public void close() {
		// TODO Auto-generated method stub
		
	}

}
