package com.gwinsoft.components.dmb;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.json.annotations.JSON;

import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class LiandongAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 1L;

	private String dm;
    private String result;
	
    @JSON(serialize=false)
    public String swjgbm() throws Exception {
    	if(dm!=null && !"".equals(dm)) {
	    	Map dmb = DMB.getOrgDMB(dm, 2, "ALL");
			Map data = DMB.sortByKey(dmb, true);
			Iterator it = data.keySet().iterator();
			List<KeyValue> list = new ArrayList<KeyValue>();
			while(it.hasNext()) {
				String key = (String) it.next();
				String value = (String) data.get(key);
				KeyValue kv = new KeyValue();
				kv.setKey(key);
				kv.setValue(value);
				list.add(kv);
			}
			JSONArray json = JSONArray.fromObject(list);
	        this.result = json.toString();
    	}
    	return "success";
    }

    @JSON(serialize=false)
    public String dxmb() throws Exception {
    	String INFO = "";
    	Map<String, Map<String, Object>> cache = CacheManager.getCache("DXMB_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(dm)) {
				INFO = (String) cache.get(key).get("INFO");
			}
		}
		KeyValue kv = new KeyValue();
		kv.setKey("INFO");
		kv.setValue(INFO);
		JSONArray json = JSONArray.fromObject(kv);
        this.result = json.toString();
    	return "success";
    }
    @JSON(serialize=false)
	public String getDWDM() throws Exception {
		KeyValue kv = new KeyValue();
		kv.setKey("key");
		Map map = DMB.getDMB("HPZL_CACHE", "JBDW_DM");
		Iterator it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(key.equals(dm)) {
				String value = (String) map.get(key);
				kv.setValue(value);
			}
		}
		map = DMB.getDMB("HPZL_CACHE", "GHJ_JE");
		it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			if(key.equals(dm)) {
				String value = map.get(key).toString();
				kv.setKey(value);
			}
		}
		JSONArray json = JSONArray.fromObject(kv);
        this.result = json.toString();
        return "success";
	}
	public String getDm() {
		return dm;
	}

	public void setDm(String dm) {
		this.dm = dm;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
}
