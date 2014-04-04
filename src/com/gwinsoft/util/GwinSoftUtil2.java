package com.gwinsoft.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;

import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class GwinSoftUtil2 {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String formatDouble(double value, int bit) {
		String pattern = "0.";
		for(int i = 0; i < bit; i ++) {
			pattern = pattern+"0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(value);
	}
	public static String getLSH() {
		UUID uid = UUID.randomUUID();
		return uid.toString();
	}
	public static Date getSysDate() {
		return new Date();
	}
	public static String getSysDate(Date date) {
		return sdf.format(date);
	}
	public static String getSysFormatDate(String format) {
		return getFormatDate(getSysDate(), format);
	}
	public static String getFormatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Date getdateAdded(Date date, int type, int i)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, i);
        return c.getTime();
    }
	public static Object[] listToStrings(List<Object> objs) {
		Object[] ss = new Object[objs.size()];
		int i = 0;
		for(Object s : objs) {
			ss[i++] = s;
		}
		return ss;
	}
	public static <T> T[] listToArray(List<T> objs) {
		Object[] ss = new Object[objs.size()];
		int i = 0;
		for(Object s : objs) {
			ss[i++] = s;
		}
		return (T[]) ss;
	}
	public static <T> List<T> arrayToList(T[] ss) {
		List<T> sqls = new ArrayList<T>();
		for(T s:ss) {
			sqls.add(s);
		}
		return sqls;
	}
	/**
	 * @param <T>
	 * @param t
	 * @return
	 * @throws Exception
	 * 克隆对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T clone(T t) throws Exception {
		//将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(t);
        //将流序列化成对象   
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (T) ois.readObject();
	}
	
	/**
	 * 取得业务流水号
	 */
	public static synchronized String getYWLSHXL(String date) {
		String result = null;
		DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			int n = 0;
			try {
				n = pm.queryForInt("SELECT VALUE FROM T_YWLSHXL WHERE YWLXRQ='"+date+"'");
			} catch (Exception e) {
				if(e instanceof EmptyResultDataAccessException) {
					;
				} else {
					throw new RuntimeException(e);
				}
			}
			if(n==0) {
				pm.executeUpdate("INSERT INTO T_YWLSHXL(YWLXRQ,VALUE) VALUES('"+date+"',1)");
				result = "1";
			} else {
				pm.executeUpdate("UPDATE T_YWLSHXL SET VALUE="+(n+1)+" WHERE YWLXRQ='"+date+"'");
				result = (n+1)+"";
			}
//			result = M2N(new BigInteger(result),26);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm != null) {
				pm.close();
			}
		}
		return result;
	}

    private static String MN = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    private static String M2N(BigInteger value, int n) {
        if(value.divide(new BigInteger(""+n)).equals(new BigInteger("0")) ) {
            return ""+MN.charAt(value.intValue());
        } else {
            return M2N(value.divide(new BigInteger(""+n)), n) + MN.charAt(value.remainder(new BigInteger(""+n)).intValue());
        }
    }
	private static Map<String,String> map = new HashMap<String,String>();
	/**
	 * OK.15位流水号
	 */
	public static synchronized String getLSHXL(String table) {
		long v = System.currentTimeMillis();
		String lsh = "";//TODO <2 wei
		if(map.get(table) != null) {//当前有值
			String[] vv = map.get(table).split(":");
			if(vv[0].equals(""+v)) {//前缀相等
				String _ = "";
				int n = (Integer.parseInt(vv[1])+1);
				if(n>99) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					v = System.currentTimeMillis();
					lsh = v +"00";
					map.put(table, v+":00");
					return lsh;
				} else if(n<10) {
					_ = "0";
				}
				lsh = v+_+n;
				map.put(table, v+":"+_+n);
			} else {//前缀不等
				lsh = v+"00";
				map.put(table, v+":00");
			}
		} else {//当前无值
			lsh = v+"00";
			map.put(table, v+":00");
		}
		return lsh;
	}
	public static <T> void translate(List<T> list, String[] colNames, String trans) throws Exception {
		if (list != null) {
			for (T t : list) {
				for(String colName:colNames) {
					translate(t, colName, trans);
				}
			}
		}
	}
	public static <T> void translate(List<T> list, String colName, String trans) throws Exception {
		if (list != null) {
			for (T t : list) {
				translate(t, colName, trans);
			}
		}
	}

	// "zhangsan:男;1:女"
	public static <T> void translate(T t, String colName, String trans) throws Exception {
		Method[] ms = t.getClass().getMethods();
		// 取get
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getModifiers() == Modifier.PUBLIC && "class java.lang.String".equals(ms[i].getGenericReturnType().toString()) && ms[i].getName().startsWith("get")) {
				String name = ms[i].getName().substring(3);
				if (colName.toUpperCase().equals(name.toUpperCase()) && ms[i].getParameterTypes().length == 0) {
					String value = (String) ms[i].invoke(t);
					if(value == null) {
						return;
					}
					//得到要转码的值： 008,admin,004,007,001,002,003
					for (int j = 0; j < ms.length; j++) {
						if (ms[j].getModifiers() == Modifier.PUBLIC && "void".equals(ms[j].getGenericReturnType().toString()) && ms[j].getName().startsWith("set")) {
							String _name = ms[j].getName().substring(3);
							if (colName.toUpperCase().equals(_name.toUpperCase()) && ms[j].getParameterTypes().length == 1) {
								Class parameterType = ms[j].getParameterTypes()[0];
								if (parameterType.equals(String.class)) {
									//得到set值的方法
									StringBuffer vvvvv = new StringBuffer("");
									String[] values = value.split(",");
									String[] ss = trans.split(";");//008：张三		004：李四		005：王二
									for(String _value : values) {
										for (int k = 0; k < ss.length; k++) {
											String[] sss = ss[k].split(":");
											if (sss[0] != null && sss[0].equals(_value)) {
												if(vvvvv.toString().length()!=0) {
													vvvvv.append(",");
												}
												vvvvv.append(sss[1]);
											}
										}
									}
									if(!"".equals(vvvvv.toString())) {
										ms[j].invoke(t, vvvvv.toString());
									}
								}
							}
						}
					}
				}
			}
		}
	}
	public static String translate(String value, String transStr) {
		if(value==null || transStr==null) {
			return null;
		}
		String[] strs = transStr.split(";");
		for(String str: strs) {
			String[] keyValue = str.split(":");
			if(value.equals(keyValue[0])) {
				return keyValue[1];
			}
		}
		return value;
	}
	public static Object translate(Object value, String cacheName, String valueColumnName, String realColumnName) {
		if(value==null) {
			return null;
		}
		Map<String, Map<String, Object>> cache = CacheManager.getCache(cacheName);
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			Map<String, Object> _map = cache.get(key);
			Object obj = _map.get(valueColumnName);
			if(value.equals(obj)) {
				return _map.get(realColumnName);
			}
		}
		return null;
	}
	public static <T> void translatedateformat(List<T> list) {
		if (list != null) {
			for (T t : list) {
				Method[] ms = t.getClass().getMethods();
				// 取get
				for (int i = 0; i < ms.length; i++) {
					if (ms[i].getModifiers() == Modifier.PUBLIC && "class java.util.Date".equals(ms[i].getGenericReturnType().toString()) && ms[i].getName().startsWith("get")) {
						String name = ms[i].getName().substring(3);
//						if (colName.toUpperCase().equals(name.toUpperCase()) && ms[i].getParameterTypes().length == 0) {
//							String value = (String) ms[i].invoke(t);
//							if(value == null) {
//								return;
//							}
//							//得到要转码的值： 008,admin,004,007,001,002,003
//							for (int j = 0; j < ms.length; j++) {
//								if (ms[j].getModifiers() == Modifier.PUBLIC && "void".equals(ms[j].getGenericReturnType().toString()) && ms[j].getName().startsWith("set")) {
//									String _name = ms[j].getName().substring(3);
//									if (colName.toUpperCase().equals(_name.toUpperCase()) && ms[j].getParameterTypes().length == 1) {
//										Class parameterType = ms[j].getParameterTypes()[0];
//										if (parameterType.equals(String.class)) {
//											//得到set值的方法
//											StringBuffer vvvvv = new StringBuffer("");
//											String[] values = value.split(",");
//											String[] ss = trans.split(";");//008：张三		004：李四		005：王二
//											for(String _value : values) {
//												for (int k = 0; k < ss.length; k++) {
//													String[] sss = ss[k].split(":");
//													if (sss[0] != null && sss[0].equals(_value)) {
//														if(vvvvv.toString().length()!=0) {
//															vvvvv.append(",");
//														}
//														vvvvv.append(sss[1]);
//													}
//												}
//											}
//											if(!"".equals(vvvvv.toString())) {
//												ms[j].invoke(t, vvvvv.toString());
//											}
//										}
//									}
//								}
//							}
//						}
					}
				}
			}
		}
	}
	/**
	 * TODO  没价值，暂不考虑实现
	 */
	public static <T> String formatSQL(String sql, T t, DBPersistenceManager pm) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> objs = new ArrayList<Object>();
		Method[] ms = t.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getModifiers() == Modifier.PUBLIC && ms[i].getName().startsWith("get")) {
				String name = ms[i].getName().substring(3);
				if (ms[i].getParameterTypes().length == 0) {
					try {
						Object o = ms[i].invoke(t);
						map.put(name, o);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(sql.trim().toUpperCase().startsWith("INSERT")) {
			String regex1 = "(\\w+)[,|\\)]";//insert 语句 取出所有列 AUTH_DM AUTH_MC AUTH_CC C1 C2
			String regex2 = "(\\W)[,|\\)]";//insert 语句 取出所有值   ? ' ' ? ?
			Pattern p1 = Pattern.compile(regex1);
			Matcher m1 = p1.matcher(sql);
			List<String> keys = new ArrayList<String>();
			while (m1.find()) {
				String key = m1.group(1);
				keys.add(key);
			}
			Pattern p = Pattern.compile(regex2);
			Matcher m = p.matcher(sql);
			int i = 0;
			while (m.find()) {
				String key = m.group(1);
				if ("?".equals(key)) {
					Object obj = map.get(keys.get(i));
					objs.add(obj);
					String str = "";
					if (obj == null) {
						str = "NULL";
					} else if (obj instanceof String) {
						str = "'" + obj.toString() + "'";
					} else if (obj instanceof Integer) {
						str = ((Integer) obj).intValue() + "";
					} else if (obj instanceof Float) {
						str = ((Float) obj).floatValue() + "";
					} else if (obj instanceof Double) {
						str = ((Double) obj).doubleValue() + "";
					} else if (obj instanceof Date) {
						str = "'" + getFormatDate((Date) obj, "yyyy-MM-dd HH:mm:ss.SSS") + "'";
					} else {
						str = "";
					}
					sql = sql.replaceFirst("\\?", str);
				}
				i ++;
			}
		} else {
			String regex = "(\\w+)=\\?";//update 语句 //group(1)
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(sql);
			while (m.find()) {
				String key = m.group(1);
				if(map.containsKey(key)) {
					Object obj = map.get(key);
					objs.add(obj);
					String str = "";
					if (obj == null) {
						str = "NULL";
					} else if (obj instanceof String) {
						str = "'" + obj.toString() + "'";
					} else if (obj instanceof Integer) {
						str = ((Integer) obj).intValue() + "";
					} else if (obj instanceof Float) {
						str = ((Float) obj).floatValue() + "";
					} else if (obj instanceof Double) {
						str = ((Double) obj).doubleValue() + "";
					} else if (obj instanceof Date) {
						str = "'" + getFormatDate((Date) obj, "yyyy-MM-dd HH:mm:ss.SSS") + "'";
					} else {
						str = "";
					}
					sql = sql.replaceFirst("\\?", str);
				}
			}
		}
		return sql;
	}
	
	public static List stringsToList(String[] s)
    {
        List list = new ArrayList();
        for (int i = 0; i < s.length; i++)
        {
            list.add(s[i]);
        }
        return list;
    }

	public static <T> void clearBean(T t) {
		Method[] ms = t.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getModifiers() == Modifier.PUBLIC && ms[i].getName().startsWith("set")) {
				String name = ms[i].getName().substring(3);
				if (ms[i].getParameterTypes().length == 1 && ms[i].getParameterTypes()[0].equals(String.class)) {
					try {
						ms[i].invoke(t, "");
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		/*CRKMX mx = new CRKMX();
		mx.setLSH("LSH");
		mx.setTAX_JE("123");
		System.out.println(formatSQL(sql, mx));
		sql = "update T_CRKMX set HP_DM=? where LSH=?";
		System.out.println(formatSQL(sql, mx));
		sql = "select * from T_CRKMX where LSH=?";
		System.out.println(formatSQL(sql, mx));
		
		sql = "insert  into  T (A,B) values ('','')";
		String regex = "INSERT\\s+INTO\\s+(\\w+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql.toUpperCase());
		while (m.find()) {
			String key = m.group(1);
			System.out.println(key);
		}*/
	}
}
