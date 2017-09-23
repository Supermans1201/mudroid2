package xmlmutation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.dom4j.Node;

public class XmlNodeProxy implements InvocationHandler {
	private Object obj;

	XmlNodeProxy(Object obj) {
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		// TODO Auto-generated method stub
		Object result = null;
		doBefore();
		XmlVisitor visitor = (XmlWriterByProxyVisitor) args[0];
		visitor.visit((Node) obj);
		// result = method.invoke(obj,args);
		// doAfter();
		return result;
	}

	public void doBefore() {
		System.out.println("do something with proxy");
	}

	public static Object factory(Object obj) {
		Class<? extends Object> cls = obj.getClass();
		System.out.println(cls);
		System.out.println(cls.getClassLoader());
		System.out.println(cls.getInterfaces());
		return Proxy.newProxyInstance(cls.getClassLoader(),
				new Class[] { Node.class }, new XmlNodeProxy(obj));
	}
}
