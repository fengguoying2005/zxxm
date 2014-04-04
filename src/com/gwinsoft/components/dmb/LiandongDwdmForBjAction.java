package com.gwinsoft.components.dmb;

import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.json.annotations.JSON;

import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class LiandongDwdmForBjAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 1L;

	private String dm;
	private String result;
	
	@JSON(serialize=false)
	public String getDWDM() throws Exception {
		KeyValue kv = new KeyValue();
		kv.setKey("key");
		Map map = DMB.getDMB("HPZL_CACHE", "JBDW_DM");
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (key.equals(dm)) {
				String value = (String) map.get(key);
				kv.setValue(value);
			}
		}
		map = DMB.getDMB("HPZL_CACHE", "SCJ_JE");
		it = map.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (key.equals(dm)) {
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
