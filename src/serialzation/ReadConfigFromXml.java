package serialzation;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Visitor;
import org.dom4j.VisitorSupport;
import org.dom4j.io.OutputFormat;

import singleton.Project;
import dealxml.DealXmlSax;

public class ReadConfigFromXml extends DealXmlSax {

	List<String> projectList = new ArrayList<String>();
	Boolean readProject = false;
	String selectProject = "";
	List<Element> elements = new ArrayList<Element>();

	public ReadConfigFromXml() {
	}

	protected void process(Document document) throws Exception {
		getXMLWriter().write(document);
		getXMLWriter().flush();

		Visitor visit = new VisitorSupport() {
			public void visit(Element e) {
				if (e.getName().equals("projectlist")) {
					if (e.attributeValue("readproject").equals("true"))
						readProject = true;
					else
						readProject = false;
//					System.out.println(readProject);

					elements = e.elements();
					for (int i = 0; i < elements.size(); i++) {
						projectList.add(i,
								elements.get(i).attributeValue("name"));

//						System.out.println(elements.get(i).attributeValue(s"name"));
					}
				}
				if (e.getName().equals("selectProject")) {
					selectProject = e.attributeValue("name");
					// System.out.println("file:"+selectProject);
				}

			}

		};
		document.accept(visit);
		setOP();
	}
	protected void setFormat() {
		format = OutputFormat.createPrettyPrint();
	}

	protected void setOP() {
		Project.getInstance().setReadProject(readProject);
		Project.getInstance().setProjectlist(projectList);
		Project.getInstance().setSelectProject(selectProject);
	}
}
