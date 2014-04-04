package com.gwinsoft.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

public class ConvertTool2 {

	public static <T> T vo2ui(Class<T> clazz, Map<String, Object> map) throws Exception {
		if(map == null) {
			return null;
		}
		T t = null;
		t = clazz.newInstance();
		Method[] ms = clazz.getMethods();
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getModifiers() == Modifier.PUBLIC && "void".equals(ms[i].getGenericReturnType().toString()) && ms[i].getName().startsWith("set")) {
				String name = ms[i].getName().substring(3);
				//method 1
				Iterator<String> it = map.keySet().iterator();
				while(it.hasNext()) {
					String _key = it.next();
					if(_key.toUpperCase().equals(name.toUpperCase())){
						Object o = map.get(_key);
						if (ms[i].getParameterTypes().length == 1 && o != null) {
							Class parameterType = ms[i].getParameterTypes()[0];
							if(parameterType.equals(o.getClass())) {
								ms[i].invoke(t, o);
							} else {
								//类型不匹配 TODO 需要循环到结尾，如果真不存在类型匹配的参数列表，就默认取一个不匹配的
								if(parameterType.equals(String.class)) {
									ms[i].invoke(t, o.toString());
								} else if(parameterType.equals(Integer.class) || parameterType.equals(int.class)) {
									if(o instanceof BigDecimal) {
										ms[i].invoke(t, ((BigDecimal)o).intValue());
									} else {
										ms[i].invoke(t, Integer.parseInt(o.toString()));
									}
								} else if(parameterType.equals(Float.class) || parameterType.equals(float.class)) {
									if(o instanceof BigDecimal) {
										ms[i].invoke(t, ((BigDecimal)o).floatValue());
									} else {
										ms[i].invoke(t, o);
									}
								} else if(parameterType.equals(Double.class) || parameterType.equals(double.class)) {
									ms[i].invoke(t, ((BigDecimal)o).doubleValue());
								} else if(parameterType.equals((new byte[]{}).getClass())) {
									if(o.getClass().equals(SerialBlob.class)) {
										SerialBlob sb = (SerialBlob) o;
										byte[] bytes = sb.getBytes(1, sb.getBinaryStream().available());
										ms[i].invoke(t, bytes);
									} else {
										ms[i].invoke(t, o);//not execute could be.
									}
								} else {
									ms[i].invoke(t, o);
								}
							}
						}
						break;
					}
				}
				//method 2
				/*Object o = map.get(name.toUpperCase());
				if (ms[i].getParameterTypes().length == 1 && o != null) {
					ms[i].invoke(t, o);
				}*/
			}
		}
		return t;
	}

	public static <T> T vo2ui(T t, Map<String, Object> map) throws Exception {
		return (T) vo2ui(t.getClass(), map);
	}
	public static void main(String[] args) throws Exception {
		Map map = new HashMap();
		map.put("dm", "222");
		map.put("DM", "111");
		map.put("AGE", 21);
		map.put("age", 31);
		A aa = new A();
		A a = (A) vo2ui(aa, map);
		System.out.println(a.getDm());
		System.out.println(a.getAge());
	}
}

class A {

	private String dm;
	private int age;

	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		System.out.println("set age="+age);
	}

}