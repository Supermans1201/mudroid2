package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReplaceOrginalWithMutant  {
	
	File orginalFile=null;
	File mutantFile=null;
	List<String> list=new ArrayList<String>();
	public List<String> getList()
	{
	    return this.list;
	}
	ReplaceOrginalWithMutant()
	{
		try {
			replace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void replace() throws IOException
	{
		// TODO Auto-generated method stub
		if(orginalFile.exists())
			orginalFile.delete();
		
		FileInputStream fis = new FileInputStream(mutantFile);
        FileOutputStream fos = new FileOutputStream(orginalFile);
 
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = fis.read(buf)) != -1) {
            fos.write(buf, 0, len);
        }
        buf=("\n").getBytes();
        fos.write(buf);
      
        fis.close();
        fos.close();
       
		System.out.println("delete>>"+orginalFile);
	}

	 protected Boolean hasString(String file,String type)
	    {
	    	//System.out.println(file);
		 if(type=="")
			 return true;
	    	if(file.indexOf('.')>0)
	    	{
	    		//System.out.println(file.indexOf('.'));
	    	//	System.out.println(file.substring(file.indexOf('.')+1)+":"+type);
	    		if(file.substring(file.indexOf('.')+1).equals(type))
	    		{
	    	//	 System.out.println(file.substring(file.indexOf('.')));
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	
	
}
