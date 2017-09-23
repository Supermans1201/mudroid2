/**
 * Copyright (C) 2015  the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 
package mujava.exceptionOp;

import java.io.*;

import openjava.mop.*;
import openjava.ptree.*;
import singleton.InheritanceRelation;
import mujava.util.InheritanceINFO;

/**
 * <p>Description: </p>
 * @author Yu-Seung Ma
 * @version 1.0
  */

public class EHC extends mujava.op.util.Mutator
{
  public EHC(FileEnvironment file_env,ClassDeclaration cdecl,
    CompilationUnit comp_unit)
  {
	super( file_env, comp_unit );
  }

  public void visit( TryStatement p ) throws ParseTreeException
  {
    CatchList catchlist = p.getCatchList();
    
    
    if (! catchlist.isEmpty()) {
    	System.out.println("catchlist.get(0).getParameter().getTypeSpecifier().getName()");
        System.out.println(catchlist.get(0).getParameter().getTypeSpecifier().getName());
      int num = catchlist.size();
      if(num==1){
        StatementList finstmts = p.getFinallyBody();
        if(!finstmts.isEmpty()){
        	
          generateEHC(catchlist.get(0),catchlist.get(0).getParameter().getTypeSpecifier().getName());
        }
      }else{
        for(int i=0;i<num;i++){
          generateEHC(catchlist.get(i),catchlist.get(i).getParameter().getTypeSpecifier().getName());
        }

      }
    }
  }

  public void generateEHC(CatchBlock p,String e_name){
	  
//	  {{{
//		  if(e_name.indexOf('.')<0)
//	 	e_name="openjava.mop."+e_name;
//	  }}}
	  //这是增加的设置。e_name不含包名。但INheritance里视乎含有包名
	  //classpath包含所有的吗？是在classpath里搜索 ？还是需要 手动添加classpath进行遍历。？？大概如此吧。需要吗？
	  //获得的是其继承关系。在有类的基础上进行的。如果对java文件去处理视乎也能获取到继承关系。为何要在类上？
	  //如果导入import正确，呢么就能解析正确。在解析之前，会将所有的包外变量替换为完整包名的变量。
	  //继承关系应该是在所有的classpath上的class文件。包括 java android 以及当前应用。
    InheritanceINFO inf = InheritanceRelation.getInstance().getInheritanceInfo(e_name);
    System.out.println("e_name:"+e_name);
    System.out.println("inf:"+inf);
    if(inf==null) return;
    //找到该结点的父亲结点。
    InheritanceINFO parent = inf.getParent();
    
    if(parent!=null){
    	System.out.println("inf.parent:"+inf.getParent().getClassName());
      String parent_name = parent.getClassName();
      outputToFile(p,parent_name);//将父节点输出到文件
      generateEHC(p,parent_name);//递归其父节点。
    }
  }

  public void outputToFile(CatchBlock p,String exception_type){
      if (comp_unit==null) return;
      String f_name;
      num++;
      f_name = getSourceName(this);
      String mutant_dir = getMuantID();

      try {
		 PrintWriter out = getPrintWriter(f_name);
		 EHC_Writer writer = new EHC_Writer( mutant_dir,out );
		 writer.setMutant(p,exception_type);
		 comp_unit.accept( writer );
		 out.flush();  out.close();
      } catch ( IOException e ) {
	    System.err.println( "fails to create " + f_name );
      } catch ( ParseTreeException e ) {
	    System.err.println( "errors during printing " + f_name );
	    e.printStackTrace();
      }
   }

}
