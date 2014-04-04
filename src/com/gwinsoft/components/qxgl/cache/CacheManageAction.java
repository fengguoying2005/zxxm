package com.gwinsoft.components.qxgl.cache;

import java.util.Collection;
import java.util.Map;

import com.gwinsoft.framework.cache.gwincache.CacheBean;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class CacheManageAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 1L;
	
	//private static Map<String, CacheBean> caches = new HashMap<String, CacheBean>();
	private Collection<CacheBean> caches;
	private String name;

	public String clearCache() {
		DBPersistenceManager pm = DBHelper.getPm();
		try {
			Map<String, CacheBean> _caches = CacheManager.getCacheBeans();
			Collection<CacheBean> cole = _caches.values();
			for(CacheBean bean : cole) {
				if(name==null || "".equals(name) || bean.getName().equals(name)) {
					//清除所有缓存  或  清除单个缓存
					CacheManager.saveCache(bean.getName(), CacheManager.genCache(pm, bean.getCachesql(), bean.getName(), bean.getCachedm(), bean.getInfo()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pm != null) {
				//DBHelper.closeAllLocalPMs(DBHelper.getAllLocalPMs());//
				pm.close();
			}
		}
		return getCacheList();
	}
	public String getCacheList() {
		caches = CacheManager.getCacheBeans().values();
		return "list";
	}
	
	public Collection<CacheBean> getCaches() {
		return caches;
	}
	public void setCaches(Collection<CacheBean> caches) {
		this.caches = caches;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}