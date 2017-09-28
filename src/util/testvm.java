package util;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class testvm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 VelocityEngine ve = new VelocityEngine(); 
		 ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		 ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName()); 
		 
		 ve.init(); 
		 
		 Template t = ve.getTemplate("hellovelocity.vm"); 
		 VelocityContext ctx = new VelocityContext(); 
		 
		 ctx.put("stringbuffer", "velocity"); 
		 
		 
		 StringWriter sw = new StringWriter(); 
		 
		 t.merge(ctx, sw); 
		 
		 System.out.println(sw.toString()); 
	}
	 
		

}
