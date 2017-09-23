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

package mujava.util;

import java.util.Vector;

/**
 * <p>
 * Description:
 * </p>
 * 
 * @author Yu-Seung Ma
 * @version 1.0
 */
// 遗传继承关系的节点类
public class InheritanceINFO {
	// 其父为另一个结点类
	InheritanceINFO direct_parent = null;
	// 子节点
	@SuppressWarnings("rawtypes")
	Vector direct_child = new Vector();
	// 该节点所具有的信息为 类的名字和其父类的名字
	String class_name = null;
	String parent_class_name = null;

	public InheritanceINFO(String my_name, String parent_name) {
		class_name = my_name;
		parent_class_name = parent_name;
	}

	// 添加子节点
	@SuppressWarnings("unchecked")
	public void addChild(InheritanceINFO child) {
		direct_child.add(child);
	}

	// 设置父节点
	public void setParent(InheritanceINFO parent) {
		direct_parent = parent;
	}

	// 获得父节点类名
	public String getParentName() {
		return parent_class_name;
	}

	// 获取去父节点
	public InheritanceINFO getParent() {
		return direct_parent;
	}

	// 获取子节点
	@SuppressWarnings("rawtypes")
	public Vector getChilds() {
		return direct_child;
	}

	// 获取类名
	public String getClassName() {
		return class_name;
	}
}
