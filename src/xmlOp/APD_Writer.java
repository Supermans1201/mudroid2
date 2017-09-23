package xmlOp;

import java.io.PrintWriter;

import org.dom4j.Element;

import xmlmutation.XmlWriterByProxyVisitor;
import mujava.op.util.CodeChangeLog;;


public class APD_Writer extends XmlWriterByProxyVisitor{
	//CatchBlock mutant = null;
		Element mutant=null;
	  String exception_name = "";
	  String file_name;

	  public APD_Writer( String file_name,PrintWriter out ) {
		super(out);
		this.file_name=file_name;
	  }

	  public void setMutant(Element p,String mutated_name){
		  mutant = p;
		  exception_name = mutated_name;
	  }

		@Override
		public void visit(Element element) {
			// TODO Auto-generated method stub
			//Node nd=( Node) XmlNodeProxy.factory(node);
	         //nd.accept(this);
	         //node.accept(this);
			
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
