package com.gwinsoft.components.dmb;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.gwinsoft.framework.cache.gwincache.CacheManager;

public class DMB {
	public static Map getOrgDMB() {
		/*Map map = new HashMap();
		Map<String, Map<String, Object>> cache = CacheManager.getCache("ORG_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			map.put(key, cache.get(key).get("ORGMC"));
		}
		return map;*/
		return getDMB("ORG_CACHE","ORG_MC");
	}
	/**
	 * @param orgdm
	 * @param type {0:全部 1:所有下级 2:本级 3:下一级 4:所有下级(包括部门)5:取得上一级}
	 * @param orgtype {J:当type为0,2时}
	 * @return
	 */
	public static Map getOrgDMB(String orgdm, int type, String orgtype, Map<String, Map<String, Object>> cache) {
		Map<String, String> selected2 = new HashMap<String,String>();
		switch(type) {
		case 0:
			{
				Iterator<String> it = cache.keySet().iterator();
				while(it.hasNext()) {
					String key = it.next();
					Map<String, Object> data = cache.get(key);
					if("ALL".equals(orgtype) || orgtype.equals(data.get("ORG_TYPE"))) {
						selected2.put(key, (String) data.get("ORG_MC"));
					}
				}
				break;
			}
		case 1:
			{
				List list = new ArrayList();
				list.add(orgdm);
				for (int i = 0; i < list.size(); i++) {
					String key = (String) list.get(i);
					if (!selected2.containsKey(key)) {
						Map<String, Object> data = cache.get(key);
						selected2.put(key, (String) data.get("ORG_MC"));
						getChild(cache, key, list, "J");
					}
				}
				break;
			}
		case 2:
			{
				List list = new ArrayList();
				Map<String,Object> org = cache.get(orgdm);
				selected2.put(orgdm, (String) org.get("ORG_MC"));
				getChild(cache, orgdm, list, "B");
				for(int i = 0; i < list.size(); i ++) {
					String key = (String) list.get(i);
					if(!selected2.containsKey(key)) {
						Map<String,Object> data = cache.get(key);
						selected2.put(key, (String) data.get("ORG_MC"));
					}
				}
				break;
			}
		case 3:
			{
				List list = new ArrayList();
				Map<String,Object> org = cache.get(orgdm);
				selected2.put(orgdm, (String) org.get("ORG_MC"));
				getChild(cache, orgdm, list, "J");
				for(int i = 0; i < list.size(); i ++) {
					String key = (String) list.get(i);
					if(!selected2.containsKey(key)) {
						Map<String,Object> data = cache.get(key);
						selected2.put(key, (String) data.get("ORG_MC"));
					}
				}
				break;
			}
		case 4:
			{
				List list = new ArrayList();
				list.add(orgdm);
				for (int i = 0; i < list.size(); i++) {
					String key = (String) list.get(i);
					if (!selected2.containsKey(key)) {
						Map<String, Object> data = cache.get(key);
						selected2.put(key, (String) data.get("ORG_MC"));
						getChild(cache, key, list, "J");
						getChild(cache, key, list, "B");
					}
				}
				break;
			}
		case 5:
			{
				Map<String,Object> org = cache.get(orgdm);
				String SJ_ORG_DM = (String) org.get("SJ_ORG_DM");
				Iterator<String> it = cache.keySet().iterator();
				while(it.hasNext()) {
					String key = it.next();
					Map<String, Object> data = cache.get(key);
					if("J".equals(data.get("ORG_TYPE")) && key.equals(SJ_ORG_DM)) {
						selected2.put(key, (String) data.get("ORG_MC"));
						break;
					}
				}
				break;
			}
		}
		return selected2;
	}
	/**
	 * @param orgdm
	 * @param type {0:全部 1:所有下级 2:本级 3:下一级 4:所有下级(包括部门)5:取得上一级}
	 * @param orgtype {J:当type为0,2时}
	 * @return
	 */
	public static Map getOrgDMB(String orgdm, int type, String orgtype) {
		Map<String, Map<String, Object>> cache = CacheManager.getCache("ORG_CACHE");
		return getOrgDMB(orgdm, type, orgtype, cache);
	}
	public static void getChild(Map<String, Map<String, Object>> all, String dm, List list, String ORGTYPE) {
		Iterator<Map<String, Object>> it = all.values().iterator();
		while(it.hasNext()) {
			Map<String, Object> org = it.next();
			Object obj = org.get("SJ_ORG_DM");
			Object ORG_TYPE = org.get("ORG_TYPE");
			if(ORG_TYPE!=null && ORGTYPE.equals(ORG_TYPE) && obj!=null) {
				String sj = (String) obj;
				if(dm.equals(sj)) {
					String ORG_DM = (String) org.get("ORG_DM");
					list.add(ORG_DM);
				}
			}
		}
	}
	public static Map getUserMap() {
		return getDMB("USER_CACHE","USER_MC");
	}
	public static Map getXLDMB() {
		/*Map map = new HashMap();
		Map<String, Map<String, Object>> cache = CacheManager.getCache("XL_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			map.put(key, cache.get(key).get("XL_MC"));
		}
		return map;*/
		return getDMB("XL_CACHE","XL_MC");
	}
	public static Map getHPZLDMB() {
		return sortByValue(getDMB("HPZL_CACHE","PMGG"),true);
	}
	public static Map getCKDMB() {
		return getDMB("CK_CACHE","CKMC");//same
	}
	public static List<Map<String, String>> getDQDMB() {
		Map dq = getDMB("DQ_CACHE","DQ");//same,CS
		Map cs = getDMB("DQ_CACHE","DQCS");
		return getCSDMB(dq, cs);
	}
	public static Map getGYSDMB() {
		return getDMB("GYS_CACHE","GYSJC");
	}
	public static List<Map<String, String>> getHPLBDMB() {
		Map dq = getDMB("HPLB_CACHE","HPLB");//same,CS
		Map cs = getDMB("HPLB_CACHE","HPLBCS");
		return getCSDMB(dq, cs);
	}
	public static Map getKHDMB() {
		return sortByValue(getDMB("KH_CACHE","KHJC"), true);
	}
	public static List<Map<String, String>> getWLDWLXDMB() {
//		return getDMB("WLLX_CACHE","WLDWLX_MC");//CS
		Map dq = getDMB("WLLX_CACHE","WLDWLX_MC");//CS
		Map cs = getDMB("WLLX_CACHE","CSWLDWLX_DM");
		return getCSDMB(dq, cs);
	}
	public static Map getYSFSDMB() {
		return getDMB("YSFS_CACHE","YSFS");//same
	}
	public static List<Map<String, String>> getCSDMB(Map dq, Map cs) {
		List<LL> ll = getParentNode(dq, cs);
		while(true) {
			if(dq.size()==0) {
				break;
			}
			for(LL l : ll) {
				dq.remove(l.key);
				Iterator it2 = cs.keySet().iterator();
				while(it2.hasNext()) {//没有所属的
					String key2 = (String) it2.next();
					String value2 = (String) cs.get(key2);
					boolean isOK = l.put(key2, (String)dq.get(key2), value2);
					if(isOK) {
						dq.remove(key2);
					}
				}
			}
		}
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for(LL l:ll) {
			getSub(list, l, 0);
		}
		return list;
	}
	private static void getSub(List<Map<String, String>> list, LL l, int CC) {
		if(l == null) {
			return;
		} else {
			String value = l.value;
			for(int i = 0; i < CC; i ++) {
				value = "---"+value;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("key",l.key);
			map.put("value",value);
			list.add(map);
			for(LL _l : l.list) {
				getSub(list, _l, CC+1);
			}
		}
		return;
	}
	/**
	 * 取得最上层的NODE
	 */
	private static List<LL> getParentNode(Map dq, Map ss) {
		List<LL> ll = new ArrayList<LL>();
		Iterator it = dq.keySet().iterator();
		while(it.hasNext()) {//没有所属的地区
			String value = (String) it.next();
			if(!ss.containsKey(value)) {
				ll.add(new LL(value, (String)dq.get(value)));
			}
		}
		if(ss.containsValue("")) {
			Iterator it2 = ss.keySet().iterator();
			while(it2.hasNext()) {
				String key = (String) it2.next();
				if("".equals(ss.get(key))) {
					ll.add(new LL(key, (String)dq.get(key)));
				}
			}
		}
		Iterator it2 = ss.values().iterator();
		while(it2.hasNext()) {
			String value = (String) it2.next();
			if(!ss.containsKey(value) && !"".equals(value)) {
				boolean isHave = false;
				for(LL _L:ll) {
					if(_L.key.equals(value)) {
						isHave = true;
						break;
					}
				}
				if(!isHave) {
					ll.add(new LL(value, (String)dq.get(value)));
				}
			}
		}
		return ll;
	}
	public static Map getDMB(String DMBName, String valueName) {
		Map map = new HashMap();
		Map<String, Map<String, Object>> cache = CacheManager.getCache(DMBName);
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			map.put(key, cache.get(key).get(valueName));
		}
		return map;
	}
	public static String getTranslateStr(Map dmb) {
		StringBuffer sb = new StringBuffer();
		Iterator it = dmb.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = dmb.get(key).toString();
			sb.append(key).append(":").append(value).append(";");
		}
		return sb.toString();
	}
	
    public static Map sortByKey(Map map, final boolean ASC) {
        Map<Object, Object> mapVK = new TreeMap<Object, Object>(
            new Comparator<Object>() {
                public int compare(Object obj1, Object obj2) {
                	if(obj1 instanceof String) {
                        String v1 = (String)obj1;
                        String v2 = (String)obj2;
                        if(ASC) {
                        	return v1.compareTo(v2);
                        } else {
                            return v2.compareTo(v1);
                        }
                	} else if(obj1 instanceof Integer) {
						Integer v1 = (Integer) obj1;
						Integer v2 = (Integer) obj2;
						if (ASC) {
							return v1.compareTo(v2);
						} else {
							return v2.compareTo(v1);
						}
                	} else {
                		 String v1 = obj1.toString();
                         String v2 = obj2.toString();
                         if(ASC) {
                         	return v1.compareTo(v2);
                         } else {
                             return v2.compareTo(v1);
                         }
                	}
                }
            }
        );
        Set col = map.keySet();
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            mapVK.put(key, map.get(key));
        }
        return mapVK;
    }
    
    public static Map sortByValue(final Map map, final boolean ASC) {
        Map<String, Object> mapVK = new TreeMap<String, Object>(
            new Comparator<String>() {
                public int compare(String key1, String key2) {
                	Object obj1 = map.get(key1);
                	Object obj2 = map.get(key2);
					if (obj1 instanceof String) {
						String v1 = (String) obj1;
						String v2 = (String) obj2;
	                    if(ASC) {
	                    	return Collator.getInstance(Locale.CHINESE).compare(v1, v2);
	                    } else {
	                        return Collator.getInstance(Locale.CHINESE).compare(v2, v1);
	                    }
					} else if (obj1 instanceof Integer) {
						Integer v1 = (Integer) obj1;
						Integer v2 = (Integer) obj2;
	                    if(ASC) {
	                    	return v1.compareTo(v2);
	                    } else {
	                        return v2.compareTo(v1);
	                    }
					} else if (obj1 instanceof Date) {
						Date v1 = (Date) obj1;
						Date v2 = (Date) obj2;
	                    if(ASC) {
	                    	return v1.compareTo(v2);
	                    } else {
	                        return v2.compareTo(v1);
	                    }
					} else if (obj1 instanceof Double) {
						Double v1 = (Double) obj1;
						Double v2 = (Double) obj2;
	                    if(ASC) {
	                    	return v1.compareTo(v2);
	                    } else {
	                        return v2.compareTo(v1);
	                    }
					} else if (obj1 instanceof Float) {
						Float v1 = (Float) obj1;
						Float v2 = (Float) obj2;
	                    if(ASC) {
	                    	return v1.compareTo(v2);
	                    } else {
	                        return v2.compareTo(v1);
	                    }
					} else {
						return 0;
					}
                }
            }
        );
        Set col = map.keySet();
        Iterator iter = col.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            mapVK.put(key, map.get(key));
        }
        return mapVK;
    }
	public static Map<String, Map<String, Object>> sortByValue(final Map<String, Map<String, Object>> map, final String DM, final boolean ASC) {
		Map<String, Map<String, Object>> mapVK = new TreeMap<String, Map<String, Object>>(
	            new Comparator<String>() {
	                public int compare(String key1, String key2) {
	                	Map<String, Object> map1 = map.get(key1);
	                	Map<String, Object> map2 = map.get(key2);
	                	Object obj1 = map1.get(DM);
	                	Object obj2 = map2.get(DM);
						if (obj1 instanceof String) {
							String v1 = (String) obj1;
							String v2 = (String) obj2;
		                    if(ASC) {
		                    	return Collator.getInstance(Locale.CHINESE).compare(v1, v2);
		                    } else {
		                        return Collator.getInstance(Locale.CHINESE).compare(v2, v1);
		                    }
						} else if (obj1 instanceof Integer) {
							Integer v1 = (Integer) obj1;
							Integer v2 = (Integer) obj2;
		                    if(ASC) {
		                    	return v1.compareTo(v2);
		                    } else {
		                        return v2.compareTo(v1);
		                    }
						} else if (obj1 instanceof Date) {
							Date v1 = (Date) obj1;
							Date v2 = (Date) obj2;
		                    if(ASC) {
		                    	return v1.compareTo(v2);
		                    } else {
		                        return v2.compareTo(v1);
		                    }
						} else if (obj1 instanceof Double) {
							Double v1 = (Double) obj1;
							Double v2 = (Double) obj2;
		                    if(ASC) {
		                    	return v1.compareTo(v2);
		                    } else {
		                        return v2.compareTo(v1);
		                    }
						} else if (obj1 instanceof Float) {
							Float v1 = (Float) obj1;
							Float v2 = (Float) obj2;
		                    if(ASC) {
		                    	return v1.compareTo(v2);
		                    } else {
		                        return v2.compareTo(v1);
		                    }
						} else {
							return 0;
						}
	                }
	            }
	        );
	        Set col = map.keySet();
	        Iterator iter = col.iterator();
	        while (iter.hasNext()) {
	            String key = (String) iter.next();
	            mapVK.put(key, map.get(key));
	        }
	        return mapVK;
	}
	public static void main(String[] args) {
		Map dq = new HashMap();//铁西：铁西		大东：大东		沈阳：沈阳		云峰：云峰
		Map cs = new HashMap();//铁西：沈阳		大东：沈阳		沈阳：NULL		云峰：铁西
		dq.put("铁西", "铁西");
		dq.put("大东", "大东");
		dq.put("沈阳", "沈阳");
		dq.put("云峰", "云峰");
		dq.put("锦州", "锦州");
		dq.put("兴工", "兴工");

		cs.put("铁西", "沈阳");
		cs.put("沈阳", "辽宁");
		cs.put("锦州", "辽宁");
		cs.put("大东", "沈阳");
		cs.put("云峰", "铁西");
		cs.put("兴工", "铁西");
		
		Map maps = new HashMap();
//		BM001	
//		BM002	
//		K17	联想手机K17
//		APPLE	大个的黄元帅苹果
//		XCQ	嘉得力629商用吸尘器
		maps.put("BM001", "DELL笔记本 14");
		maps.put("BM002", "康佳54寸液晶电视");
		maps.put("K17", "联想手机K17");
		maps.put("APPLE", "大个的黄元帅苹果");
		maps.put("XCQ", "嘉得力629商用吸尘器");
		maps.put("K171", "Q");
		maps.put("K172", "好");
		maps.put("K173", "Z");
		maps.put("K174", "你");
		maps.put("K175", "这个");
		//排序前的输出
        Set set = maps.entrySet();
        Iterator i = set.iterator();
        while(i.hasNext()){
            Map.Entry<String, Integer> entry1=(Map.Entry<String, Integer>)i.next();
            System.out.println(entry1.getKey() + "-------->" + entry1.getValue());
        }

        System.out.println("----------------");
         //排序后的输出
        Map<String, Integer> sortMaps = sortByValue(maps, true);
        Set sortSet = sortMaps.entrySet();
        Iterator ii = sortSet.iterator();
        while(ii.hasNext()){
            Map.Entry<String, Integer> entry1=(Map.Entry<String, Integer>)ii.next();
            System.out.println(entry1.getKey() + "-------->" + entry1.getValue());
        }
	}
}

class LL {
	boolean isXS;
	String key;
	String value;
	List<LL> list = new ArrayList<LL>();
	public LL(String key, String value) {
		this.key = key;
		this.value = value;
	}
	public boolean put(String key, String value, String ss) {
		if(this.key.equals(ss)) {
			for(LL l : list) {
				if(l.key.equals(key)) {
					return true;
				}
			}
			list.add(new LL(key,value));
			return true;
		} else {
			for(LL l : list) {
				boolean isOK = l.put(key,value, ss);
				if(isOK) {
					return true;
				}
			}
		}
		return false;
	}
}