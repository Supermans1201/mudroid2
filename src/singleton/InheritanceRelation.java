package singleton;

import java.util.ArrayList;
import java.util.List;

import mujava.util.InheritanceINFO;

public class InheritanceRelation {
	private static InheritanceRelation instance;

	private InheritanceRelation() {
	}
	InheritanceINFO[] classInfo = null;

	public void setInheritanceInfo(InheritanceINFO[] classInfo) {
		this.classInfo = classInfo;
	}

	public List<InheritanceINFO> getInheritanceInfoByPackage(String all_name) {
		List<InheritanceINFO> InheritanceINFOlist = new ArrayList<InheritanceINFO>();
		InheritanceINFO InheritanceINFOthis = null;
		System.out.println(all_name);
		String class_name = all_name.substring(0, all_name.lastIndexOf('.'));

		System.out.println(class_name);
		String package_name = class_name.substring(0,
				class_name.lastIndexOf('.'));

		System.out.println(package_name);
		// ��������������б���������Ǿͷ��������㡣�Ҳ����ͷ���Ϊ��
		for (int i = 0; i < classInfo.length; i++) {
			// System.out.println("classinfo:"+classInfo[i].getClassName()+"\ni:"+i+"\nlength"+classInfo[i].getChilds().size());
			// ;
			if (classInfo[i].getClassName()
					.substring(0, classInfo[i].getClassName().lastIndexOf('.'))
					.equals(package_name)) {
				if (classInfo[i].getClassName().equals(class_name)) {
					InheritanceINFOthis = classInfo[i];
				}
				// ��ȡ�����ͣ����Ϊactivity service ����broadcast ���뱾��ͬ���ǿ���ָ���Ķ���
			}
		}
		for (int i = 0; i < classInfo.length; i++) {
			// System.out.println("classinfo:"+classInfo[i].getClassName()+"\ni:"+i+"\nlength"+classInfo[i].getChilds().size());
			// ;
			if (classInfo[i].getClassName()
					.substring(0, classInfo[i].getClassName().lastIndexOf('.'))
					.equals(package_name)) {
				if (!classInfo[i].getClassName().equals(class_name)) {
					if (classInfo[i].getParentName().equals(
							InheritanceINFOthis.getParentName()))
						// System.out.println(classInfo[i].getClassName());
						InheritanceINFOlist.add(classInfo[i]);
				}
				// ��ȡ�����ͣ����Ϊactivity service ����broadcast ���뱾��ͬ���ǿ���ָ���Ķ���
			}
		}
		return InheritanceINFOlist;
	}

	public InheritanceINFO getInheritanceInfo(String class_name) {
		for (int i = 0; i < classInfo.length; i++) {
			if (classInfo[i].getClassName().equals(class_name)) {
				return classInfo[i];
			}
		}
		return null;
	}

	public static InheritanceRelation getInstance() {
		if (instance == null) {
			instance = new InheritanceRelation();
		}
		return instance;
	}

	public void readInheritanceInfo() {
		// TODO Auto-generated method stub
		for (int i = 0; i < this.classInfo.length; i++)
			System.out.println(classInfo[i].getClassName());
	}

}
