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
// �Ŵ��̳й�ϵ�Ľڵ���
public class InheritanceINFO {
	// �丸Ϊ��һ�������
	InheritanceINFO direct_parent = null;
	// �ӽڵ�
	@SuppressWarnings("rawtypes")
	Vector direct_child = new Vector();
	// �ýڵ������е���ϢΪ ������ֺ��丸�������
	String class_name = null;
	String parent_class_name = null;

	public InheritanceINFO(String my_name, String parent_name) {
		class_name = my_name;
		parent_class_name = parent_name;
	}

	// ����ӽڵ�
	@SuppressWarnings("unchecked")
	public void addChild(InheritanceINFO child) {
		direct_child.add(child);
	}

	// ���ø��ڵ�
	public void setParent(InheritanceINFO parent) {
		direct_parent = parent;
	}

	// ��ø��ڵ�����
	public String getParentName() {
		return parent_class_name;
	}

	// ��ȡȥ���ڵ�
	public InheritanceINFO getParent() {
		return direct_parent;
	}

	// ��ȡ�ӽڵ�
	@SuppressWarnings("rawtypes")
	public Vector getChilds() {
		return direct_child;
	}

	// ��ȡ����
	public String getClassName() {
		return class_name;
	}
}
