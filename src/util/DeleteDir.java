package util;

import java.io.File;

import singleton.Project;

public class DeleteDir {
    /**
     * ɾ����Ŀ¼
     * @param dir ��Ҫɾ����Ŀ¼·��
     */
    @SuppressWarnings("unused")
	private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
    }

    /**
     * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
     * @param dir ��Ҫɾ�����ļ�Ŀ¼
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // Ŀ¼��ʱΪ�գ�����ɾ��
        return dir.delete();
    }
    
    public static void deleteManifestMut()
    {
    	deleteDir(new File(Project.getInstance().getmanifestMutPath()));
    	System.out.println("manifrest mutant is delete");
    }
    
    public static void deleteJavaMut()
    {
    	deleteDir(new File(Project.getInstance().getJavaMutPath()));
    	System.out.println("java mutant is delete");
    }
    
    public static void deleteXmlMut()
    {
    	deleteDir(new File(Project.getInstance().getXmlMutPath()));
    	System.out.println("xml mutant is delete");
    }
    
    public static void deleteMut()
    {
    	deleteManifestMut(); 
    	deleteJavaMut();
    	deleteXmlMut();
    	System.out.println("all mutant is delete");
    	deleteDir(new File(Project.getInstance().getMutDir()));;
    }
    /**
     *����
     */
    public static void main(String[] args) {
      
    	
    
    }
}