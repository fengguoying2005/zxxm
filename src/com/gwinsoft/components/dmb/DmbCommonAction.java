package com.gwinsoft.components.dmb;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.json.annotations.JSON;

import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class DmbCommonAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 1L;

    private String result;
    @JSON(serialize=false)
	public String getHPDM() throws Exception {
		StringBuffer sb = new StringBuffer("");
		Map<String, Map<String, Object>> hpzls = CacheManager.getCache("HPZL_CACHE");//HPBM,PMGG,HPLB
		Map hplbs = DMB.sortByValue(DMB.getDMB("HPLB_CACHE","HPLB"), true);
		Iterator hplbIt = hplbs.keySet().iterator();
		while(hplbIt.hasNext()) {
			String HPLBDM = (String) hplbIt.next();
			boolean isHave = false;
			Map<String, String> optgrouphpzls = new HashMap<String, String>();
			Iterator it = hpzls.keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				Map<String, Object> hpzl = hpzls.get(key);
				String HPLB = (String) hpzl.get("HPLB");
				if(HPLBDM.equals(HPLB)) {
					isHave = true;
					optgrouphpzls.put((String)hpzl.get("HPBM"), (String)hpzl.get("PMGG"));
				}
			}
			if(isHave) {
				sb.append("<optgroup class='optgroup' label='"+HPLBDM+"'>");
				optgrouphpzls = DMB.sortByValue(optgrouphpzls,true);
				Iterator<String> hpzlIt = optgrouphpzls.keySet().iterator();
				while(hpzlIt.hasNext()) {
					String key = hpzlIt.next();
					String value = optgrouphpzls.get(key);
					sb.append("<option style='color:blue;' title='"+value+"' value='"+key+"'>"+value+"</option>");
				}
				sb.append("</optgroup>");
			}
		}
		//sb.append("<optgroup label='北海道'><option value='BM001'>Text</option><option value='BM002'>Text2</option></optgroup>");
		result = sb.toString();
		return "success";
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
