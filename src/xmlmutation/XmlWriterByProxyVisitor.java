package xmlmutation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.StringTokenizer;
import org.dom4j.Attribute;
import org.dom4j.CDATA;
import org.dom4j.Comment;
import org.dom4j.Document;
import org.dom4j.DocumentType;
import org.dom4j.Element;
import org.dom4j.Entity;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.ProcessingInstruction;
import org.dom4j.Text;
import org.dom4j.dtd.Decl;
import org.dom4j.tree.NamespaceStack;

public class XmlWriterByProxyVisitor implements XmlVisitor {

	private NamespaceStack namespaceStack = new NamespaceStack();

	protected boolean isNamespaceDeclaration(Namespace ns) {
		if ((ns != null) && (ns != Namespace.XML_NAMESPACE)) {
			String uri = ns.getURI();
			if (uri != null) {
				if (!namespaceStack.contains(ns)) {
					return true;
				}
			}
		}
		return false;
	}

	protected int lastOutputNodeType;

	protected void writeAttributes(Element element) throws IOException {
		for (int i = 0, size = element.attributeCount(); i < size; i++) {
			Attribute attribute = element.attribute(i);
			Namespace ns = attribute.getNamespace();
			if ((ns != null) && (ns != Namespace.NO_NAMESPACE)
					&& (ns != Namespace.XML_NAMESPACE)) {
				String prefix = ns.getPrefix();
				String uri = namespaceStack.getURI(prefix);
				if (!ns.getURI().equals(uri)) {
					visit(ns);
					namespaceStack.push(ns);
				}
			}
			String attName = attribute.getName();
			if (attName.startsWith("xmlns:")) {
				String prefix = attName.substring(6);
				if (namespaceStack.getNamespaceForPrefix(prefix) == null) {
					String uri = attribute.getValue();
					namespaceStack.push(prefix, uri);
					writeNamespace(prefix, uri);
				}
			} else if (attName.equals("xmlns")) {
				if (namespaceStack.getDefaultNamespace() == null) {
					String uri = attribute.getValue();
					namespaceStack.push(null, uri);
					writeNamespace(null, uri);
				}
			} else {
				visit(attribute);
			}
		}
	}

	protected void writeNamespace(String prefix, String uri) throws IOException {
		if ((prefix != null) && (prefix.length() > 0)) {
			writer.write(" xmlns:");
			writer.write(prefix);
			writer.write("=\"");
		} else {
			writer.write(" xmlns=\"");
		}
		writer.write(uri);
		writer.write("\"");
	}

	private static final String PAD_TEXT = " ";

	@SuppressWarnings("unused")
	protected void writeElementContent(Element element) throws IOException {
		boolean trim = true;
		boolean isPadText = false;
		if (trim) {
			Text lastTextNode = null;
			StringBuilder buff = null;
			boolean textOnly = true;

			for (Node node : element.content()) {
				if (node instanceof Text) {
					if (lastTextNode == null) {
						lastTextNode = (Text) node;
					} else {
						if (buff == null) {
							buff = new StringBuilder(lastTextNode.getText());
						}
						buff.append((node).getText());
					}
				} else {
					if (!textOnly && isPadText) {

						char firstChar = 'a';
						if (buff != null) {
							firstChar = buff.charAt(0);
						} else if (lastTextNode != null) {
							firstChar = lastTextNode.getText().charAt(0);
						}
						if (Character.isWhitespace(firstChar)) {
							writer.write(PAD_TEXT);
						}
					}
					if (lastTextNode != null) {
						if (buff != null) {
							visit(buff.toString());
							buff = null;
						} else {
							visit(lastTextNode.getText());
						}
						if (isPadText) {

							char lastTextChar = 'a';
							if (buff != null) {
								lastTextChar = buff.charAt(buff.length() - 1);
							} else if (lastTextNode != null) {
								String txt = lastTextNode.getText();
								lastTextChar = txt.charAt(txt.length() - 1);
							}
							if (Character.isWhitespace(lastTextChar)) {
								writer.write(PAD_TEXT);
							}
						}
						lastTextNode = null;
					}
					textOnly = false;
					visit(node);
				}
			}
			if (lastTextNode != null) {
				if (!textOnly && isPadText) {
					char firstChar = 'a';
					if (buff != null) {
						firstChar = buff.charAt(0);
					} else {
						firstChar = lastTextNode.getText().charAt(0);
					}
					if (Character.isWhitespace(firstChar)) {
						writer.write(PAD_TEXT);
					}
				}
				if (buff != null) {
					visit(buff.toString());
					buff = null;
				} else {
					visit(lastTextNode.getText());
				}
				lastTextNode = null;
			}
		} else {
			Node lastTextNode = null;
			for (Node node : element.content()) {
				if (node instanceof Text) {
					visit(node);
					lastTextNode = node;
				} else {
					if ((lastTextNode != null) && isPadText) {
						String txt = lastTextNode.getText();
						char lastTextChar = txt.charAt(txt.length() - 1);
						if (Character.isWhitespace(lastTextChar)) {
							writer.write(PAD_TEXT);
						}
					}
					visit(node);

					lastTextNode = null;
				}
			}
		}
	}

	PrintWriter writer;

	public XmlWriterByProxyVisitor(PrintWriter out) {
		writer = out;
		namespaceStack.push(Namespace.NO_NAMESPACE);
	}

	public XmlWriterByProxyVisitor() {
		writer = new PrintWriter(System.out);
		namespaceStack.push(Namespace.NO_NAMESPACE);
	}

	@Override
	public void visit(Document doc) {
		// TODO Auto-generated method stub
		System.out.println("visit Document by Proxy");
		writer.write("<?xml version=\"1.0\"");
		{
			writer.write(" encoding=\"" + doc.getXMLEncoding() + "\"");
		}
		writer.write("?>");
		if (doc.getDocType() != null) {

			visit(doc.getDocType());
		}
		for (int i = 0, size = doc.nodeCount(); i < size; i++) {
			Node node = doc.node(i);
			try {
				visit(node);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writer.println();
		writer.flush();
	}

	@Override
	public void visit(DocumentType docType) {
		writer.write("<!DOCTYPE ");
		writer.write(docType.getElementName());
		boolean hasPublicID = false;
		String publicID = docType.getPublicID();
		if ((publicID != null) && (publicID.length() > 0)) {
			writer.write(" PUBLIC \"");
			writer.write(publicID);
			writer.write("\"");
			hasPublicID = true;
		}
		String systemID = docType.getSystemID();
		if ((systemID != null) && (systemID.length() > 0)) {
			if (!hasPublicID) {
				writer.write(" SYSTEM");
			}
			writer.write(" \"");
			writer.write(systemID);
			writer.write("\"");
		}
		List<Decl> list = docType.getInternalDeclarations();
		if ((list != null) && (list.size() > 0)) {
			writer.write(" [");
			for (Decl decl : list) {
				writer.write("\n  ");
				writer.write(decl.toString());
			}
			writer.write("\n]");
		}
		writer.write(">");
		writer.println();
	}

	@Override
	public void visit(Element element) {
		// TODO Auto-generated method stub
		int size = element.nodeCount();
		String qualifiedName = element.getQualifiedName();
		writer.println();
		writer.write("<");
		writer.write(qualifiedName);
		int previouslyDeclaredNamespaces = namespaceStack.size();
		Namespace ns = element.getNamespace();
		if (isNamespaceDeclaration(ns)) {
			namespaceStack.push(ns);
			visit(ns);
		}
		boolean textOnly = true;
		for (int i = 0; i < size; i++) {
			Node node = element.node(i);
			if (node instanceof Namespace) {
				Namespace additional = (Namespace) node;
				if (isNamespaceDeclaration(additional)) {
					namespaceStack.push(additional);
					visit(additional);
				}
			} else if (node instanceof Element) {
				textOnly = false;
			} else if (node instanceof Comment) {
				textOnly = false;
			}
		}
		try {
			writeAttributes(element);
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastOutputNodeType = Node.ELEMENT_NODE;
		if (size <= 0) {
			writer.write("/>");// Ð´¼òµ¥½áÊø
		} else {
			writer.write(">");
			if (textOnly) {
				try {
					writeElementContent(element);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				try {
					writeElementContent(element);
				} catch (IOException e) {
					e.printStackTrace();
				}
				writer.println();

			}
			writer.write("</");
			writer.write(qualifiedName);
			writer.write(">");
		}
		while (namespaceStack.size() > previouslyDeclaredNamespaces) {
			namespaceStack.pop();
		}
		lastOutputNodeType = Node.ELEMENT_NODE;
	}

	@Override
	public void visit(Attribute attribute) {
		writer.write(" ");
		writer.write(attribute.getQualifiedName());
		writer.write("=");
		writer.write("\"");
		writer.write(attribute.getValue());
		writer.write("\"");
		lastOutputNodeType = Node.ATTRIBUTE_NODE;
	}

	@Override
	public void visit(CDATA cdata) {
		// TODO Auto-generated method stub
		String text = cdata.getText();
		writer.write("<![CDATA[");
		if (text != null) {
			writer.write(text);
		}
		writer.write("]]>");
		lastOutputNodeType = Node.CDATA_SECTION_NODE;
	}

	@Override
	public void visit(Comment comment) {
		String text = comment.getText();
		writer.write("<!--");
		writer.write(text);
		writer.write("-->");
		lastOutputNodeType = Node.COMMENT_NODE;
	}

	private boolean resolveEntityRefs = true;

	@Override
	public void visit(Entity entity) {
		// TODO Auto-generated method stub
		if (!resolveEntityRefs) {
			writer.write("&");
			writer.write(entity.getName());
			writer.write(";");
			lastOutputNodeType = Node.ENTITY_REFERENCE_NODE;
		} else {
			writer.write(entity.getText());
		}
	}

	@Override
	public void visit(Namespace namespace) {
		// TODO Auto-generated method stub
		System.out.println("visit NameSpace by proxy");
		if (namespace != null) {
			String prefix = namespace.getPrefix();
			String uri = namespace.getURI();
			try {
				writeNamespace(prefix, uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void visit(ProcessingInstruction pi) {
		// TODO Auto-generated method stub
		writer.write("<?");
		writer.write(pi.getName());
		writer.write(" ");
		writer.write(pi.getText());
		writer.write("?>");
		writer.println();
		lastOutputNodeType = Node.PROCESSING_INSTRUCTION_NODE;
	}

	@Override
	public void visit(Text text) {
		// TODO Auto-generated method stub
		try {
			visit(text.getText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
	}

	@Override
	public void visit(Node node) throws IOException {
		// TODO Auto-generated method stub
		int nodeType = node.getNodeType();
		switch (nodeType) {
		case Node.ELEMENT_NODE:
			visit((Element) node);
			break;
		case Node.ATTRIBUTE_NODE:
			visit((Attribute) node);
			break;
		case Node.TEXT_NODE:
			visit(node);
			break;
		case Node.CDATA_SECTION_NODE:
			visit((CDATA) node);
			break;
		case Node.ENTITY_REFERENCE_NODE:
			visit((Entity) node);
			break;
		case Node.PROCESSING_INSTRUCTION_NODE:
			visit((ProcessingInstruction) node);
			break;
		case Node.COMMENT_NODE:
			visit((Comment) node);
			break;
		case Node.DOCUMENT_NODE:
			visit((Document) node);
			break;
		case Node.DOCUMENT_TYPE_NODE:
			visit((DocumentType) node);
			break;
		case Node.NAMESPACE_NODE:
			break;
		default:
			throw new IOException("Invalid node type: " + node);
		}
	}

	@Override
	public void visit(String text) throws IOException {
		// TODO Auto-generated method stub
		boolean isTrimText = false;
		if ((text != null) && (text.length() > 0)) {
			if (isTrimText) {
				boolean first = true;
				StringTokenizer tokenizer = new StringTokenizer(text);
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					if (first) {
						first = false;
						if (lastOutputNodeType == Node.TEXT_NODE) {
							writer.write(" ");
						}
					} else {
						writer.write(" ");
					}
					writer.write(token);
					lastOutputNodeType = Node.TEXT_NODE;
				}
			} else {
				lastOutputNodeType = Node.TEXT_NODE;
				writer.write(text);
			}
		}
	}
}
