package xmlmutation;

import java.io.IOException;
import org.dom4j.*;

public interface XmlVisitor extends Visitor {
	
	public void visit(Node node) throws IOException;

	public void visit(String s) throws IOException;

}
