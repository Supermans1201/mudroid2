package xmlOp;

import java.io.PrintWriter;

import org.dom4j.Element;

import xmlmutation.XmlWriterByProxyVisitor;
import mujava.op.util.CodeChangeLog;

public class TWD_Writer extends XmlWriterByProxyVisitor{
	Element mutant=null;
	  String exception_name = "";
	  String file_name;

	  public TWD_Writer( String file_name,PrintWriter out ) {
		super(out);
		this.file_name=file_name;
	  }

	  public void setMutant(Element p,String mutated_name){
		  mutant = p;
		  exception_name = mutated_name;
	  }

		@Override
		public void visit(Element element) {
			
			if(element==mutant)
			{
				CodeChangeLog.writeLog(element.toString() + "is deleted");
				System.out.println(element.getParent());
				System.out.println("true");
			}else
			{
				System.out.println("false");
				super.visit(element);
				// XmlNodeProxy XmlNodeProxy;

			}
		}

		
}
