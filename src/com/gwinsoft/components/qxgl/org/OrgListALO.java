package com.gwinsoft.components.qxgl.org;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheBean;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.GwinSoftUtil;

public class OrgListALO extends BaseALO {

	protected void doService() {
		PageBean pageBean = reqEnv.getData("pageBean");
		DBPersistenceManager pm = this.getPM();
		StringBuffer sql = new StringBuffer("SELECT T.ORG_DM AS HID_LSH,T.* FROM QX_ORG T WHERE 1=1 ");
		String FIND_ORG_DM = reqEnv.getData("FIND_ORG_DM");
		if(FIND_ORG_DM != null && !"".equals(FIND_ORG_DM.trim())) {
			sql.append(" AND ORG_DM like '%"+FIND_ORG_DM+"%'");
		} else {
			User user = this.reqEnv.getLoginUser();
			String data_sql = "SELECT ORG_DM,ORG_MC,ORG_TYPE,ORG_DESC,SJ_ORG_DM,JGPX FROM QX_ORG";
			Map<String, Map<String, Object>> datas = genOrg(pm, data_sql, "ORG_CACHE2", "ORG_DM","所有机构");
			Map jg = DMB.getOrgDMB(user.getORG_DM_JG(), 4, "J", datas);
			if(jg!=null) {
				sql.append(" AND (ORG_DM IN (''");
				Iterator it = jg.keySet().iterator();
				int i = 0;
				while(it.hasNext()) {
					i ++;
					String swjg = (String) it.next();
					if(i>800) {
						i = 0;
						sql.append(") or ORG_DM IN (''");
					}
					sql.append(",'"+swjg+"'");
				}
				sql.append("))");
			}
		}
		String FIND_ORG_MC = reqEnv.getData("FIND_ORG_MC");
		if(FIND_ORG_MC != null && !"".equals(FIND_ORG_MC.trim())) {
			sql.append(" AND ORG_MC LIKE '%"+FIND_ORG_MC+"%'");
		}
		String FIND_ORG_TYPE = reqEnv.getData("FIND_ORG_TYPE");
		if(FIND_ORG_TYPE != null && !"".equals(FIND_ORG_TYPE.trim())) {
			sql.append(" AND ORG_TYPE LIKE '%"+FIND_ORG_TYPE+"%'");
		}
		String FIND_ORG_DESC = reqEnv.getData("FIND_ORG_DESC");
		if(FIND_ORG_DESC != null && !"".equals(FIND_ORG_DESC.trim())) {
			sql.append(" AND ORG_DESC LIKE '%"+FIND_ORG_DESC+"%'");
		}
		String FIND_SJ_ORG_DM = reqEnv.getData("FIND_SJ_ORG_DM");
		if(FIND_SJ_ORG_DM != null && !"".equals(FIND_SJ_ORG_DM.trim())) {
			sql.append(" AND SJ_ORG_DM = '"+FIND_SJ_ORG_DM+"'");
		}
		User loginuser = this.getData("user");
		//sql.append(" AND FIND_IN_SET(ORG_DM, getChildLst('"+loginuser.getORG_DM_JG()+"'))>0");
		/*Map jg = DMB.getOrgDMB(loginuser.getORG_DM_JG(), 4, "J");
		if(jg!=null) {
			sql.append(" AND ORG_DM IN (''");
			Iterator it = jg.keySet().iterator();
			while(it.hasNext()) {
				String swjg = (String) it.next();
				sql.append(",'").append(swjg).append("'");
			}
			sql.append(")");
		}*/
		Object[] args = new Object[] {};
		List<Org> orgs = null;
		if(pageBean!=null) {
			orgs = pm.queryPageList(sql.toString() , Org.class, args , pageBean);
		} else {
			orgs = pm.queryForList(sql.toString() , Org.class, args);
		}
		try {
			GwinSoftUtil.translate(orgs, "ORG_TYPE", DMB.getTranslateStr(DMB.getDMB("ORGTYPE_CACHE", "ORGTYPE_MC")));
			GwinSoftUtil.translate(orgs, "SJ_ORG_DM", DMB.getTranslateStr(DMB.getDMB("ORG_CACHE", "ORG_MC")));
			GwinSoftUtil.translate(orgs, "YX_BJ", "0:无效;1:有效;");
		} catch (Exception e) {
			e.printStackTrace();
		}
		resEnv.putData("orgs", orgs);
	}
	private Map<String, Map<String, Object>> genOrg(DBPersistenceManager pm, final String sql, final String name, final String cachedm, final String info) {
		final Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
		pm.query(sql, new RowMapper<Map<String, Map<String, Object>>>() {
			public Map<String, Map<String, Object>> mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResultSetMetaData meta = rs.getMetaData();
				String cachedmValue = null;
				Map<String,Object> _map = new HashMap<String,Object>();
				for(int i = 0; i < meta.getColumnCount(); i ++) {
					String key = meta.getColumnLabel(i+1);
					Object value = rs.getObject(i+1);
					if(cachedm.equals(key)) {
						cachedmValue = value.toString();
					}
					_map.put(key, value);
				}
				maps.put(cachedmValue, _map);
				return maps;
			}
		});
		return maps;
	}
}
