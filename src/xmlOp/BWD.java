package xmlOp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;

import xmlmutation.XmlMutator;
import xmlmutation.XmlNodeProxy;

public class BWD extends XmlMutator{
	public BWD(Document document) {
		super(document);
		// TODO Auto-generated constructor stub
	}
	public void visit(Element element)
	{
		if(element.getName().equals("Button"))
		{
			System.out.println("*************find Butten");
			outputToFile(element);
		}
		else 
			super.visit(element);
			//outputToFile(element);
//		else
//		{
//		Node nd=( Node) XmlNodeProxy.factory(node);
//        nd.accept(this);
//		}
	}
	public void outputToFile(Element original){
	      if (document==null) return;

	      String f_name;
	      num++;
	      f_name = getSourceName(this);
	      System.out.println("f_name:"+f_name);
	      String mutant_dir = getMuantID();
	      System.out.println("mutant_dir:"+mutant_dir);
	      File f=new File(f_name);
	      if(!f.getParentFile().exists())
	      {
	    	  f.getParentFile().mkdirs();
	    	  System.out.println("f.getParent"+f.getParentFile());
	      }
	      try {
			 PrintWriter out = getPrintWriter(f_name);
			 BWD_Writer writer = new BWD_Writer( mutant_dir,out );
			 writer.setMutant(original,"");
			 Node nd=( Node) XmlNodeProxy.factory(document);
	         nd.accept(writer);
			// document.accept( writer );
			 out.flush();  out.close();
	      } catch ( IOException e ) {
		    System.err.println( "fails to create " + f_name );
	      
	      }
	   }
}
