package singleton;

import java.util.Arrays;
import java.util.List;

public class Op {
	private static Op instance;  
	private Op(){
		classOp=(Arrays.asList(cm_operators));
		traditionalOp=(Arrays.asList(tm_operators));
		exceptionOp=(Arrays.asList(em_operators));
		androidOp=(Arrays.asList(am_operators));
		xmlOp=(Arrays.asList(xm_operators));
	}
	
	private String[] cm_operators = {
		"AMC","IHI","IHD","IOD","IOP","IOR","ISI","ISD","IPC",
		"PNC","PMD","PPD","PCI","PCC","PCD","PRV",
		"OMR","OMD","OAN", 
		"JTI","JTD","JSI","JSD","JID","JDC",
		"EOA","EOC","EAM","EMM" };
	private String[] tm_operators = {
		"AORB","AORS","AOIU","AOIS","AODU","AODS",
		"ROR","COR","COD","COI","SOR","LOR","LOI","LOD","ASRS","SDL","VDL","CDL","ODL"};
	private  String[] em_operators = {
		"EFD", "EHC", "EHD", "EHI",
		"ETC", "ETD"};
	private  String[] am_operators = {
			"ECR", "ETR", "IPR", "ISR","ITR", "MDL"};
	private  String[] xm_operators = {
			"APD", "BWD", "TWD"};
	
	List<String> classOp=null;
	List<String> traditionalOp=null;
	List<String> exceptionOp=null;
	List<String> androidOp=null;
	List<String> xmlOp=null;
	
    public void setClassOp(List<String> classOp)
    {
    	this.classOp=classOp;
    }
    public void setTraditionalOp(List<String> traditionalOp)
    {
    	this.traditionalOp=traditionalOp;
    }
    public void setExceptionOp(List<String> exceptionOp)
    {
    	this.exceptionOp=exceptionOp;
    }
    public void setAndroidOp(List<String> androidOp)
    {
    	this.androidOp=androidOp;
    }
    public void setXmlOp(List<String> xmlOp)
    {
    	this.xmlOp=xmlOp;
    }
    
    public List<String> getClassOp()
    {
    	return this.classOp;
    }
    public List<String> getTradtionalOp()
    {
    	return this.traditionalOp;
    }
    public List<String> getExceptionOp()
    {
    	return this.exceptionOp;
    }
    public List<String> getAndroidOp()
    {
    	return this.androidOp;
    }
    public List<String> getXmlOp()
    {
    	return this.xmlOp;
    }
    
    public void readClassOp()
    {
    	for(int i=0;i<this.classOp.size();i++)
    		System.out.println(this.classOp.get(i));
    }
    public void readTradtionalOp()
    {
    	for(int i=0;i<this.traditionalOp.size();i++)
    		System.out.println(this.traditionalOp.get(i));
    }
    public void readExceptionOp()
    {
    	for(int i=0;i<this.exceptionOp.size();i++)
    		System.out.println(this.exceptionOp.get(i));
    }
    public void readAndroidOp()
    {
    	for(int i=0;i<this.androidOp.size();i++)
    		System.out.println(this.androidOp.get(i));
    }
    public void readXmlOp()
    {
    	for(int i=0;i<this.xmlOp.size();i++)
    		System.out.println(this.xmlOp.get(i));
    }
    public String[] getCm_operators()
    {
    	return this.cm_operators;
    }
    public String[] getTm_operators()
    {
    	return this.tm_operators;
    }
    public String[] getAm_operators()
    {
    	return this.am_operators;
    }
    public String[] getXm_operators()
    {
    	return this.xm_operators;
    }
    
    public String[] getEm_operators()
    {
    	return this.em_operators;
    }
    public static Op getInstance(){  
        if (instance==null){  
            instance=new Op();  
        }  
        return instance;  
    }
}
