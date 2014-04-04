package com.gwinsoft.framework.cache.gwincache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.gwinsoft.components.qxgl.auth.Auth;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;

public class AuthTreeAction2 extends BaseAction<BaseIAO> {
	
	private static final long serialVersionUID = 1L;

	private String orgdm;
	private String name;
    private String result;

	// ajax返回结果
    public String getResult() {
        return result;
    }

    public String test() {
    	//System.out.println("请求参数:"+name+"\n\n");
    	TreeNode root = new TreeNode("0", "-1", "全部", "0");
		DBPersistenceManager pm = null;

		User user = UserHelper.getLoginUserFromRequest(request);
		String orgdm = user.getORG_DM_JG();
		boolean isTopLevel = false;
		Map<String, Map<String, Object>> cache = CacheManager.getCache("ORG_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			String org = (String) cache.get(key).get("ORG_DM");
			if(orgdm.equals(org)) {
				Object sj = cache.get(key).get("SJ_ORG_DM");
				if(sj==null || sj.toString().length()<1) {
					isTopLevel = true;
				}
				break;
			}
		}
		Map<String, TreeNode> map = null;
		List<Auth> values = null;
		try {
			pm = DBHelper.getPm();
			map = new HashMap<String, TreeNode>();
			String sql = null;
			if(name!=null && name.startsWith("assginUserAuth___")) {
				name = name.substring(17);
				sql = "SELECT A.AUTH_MC,A.AUTH_DM,A.AUTH_CC,A.SJ_AUTH_DM,(CASE WHEN B.USER_DM='"+name+"' THEN '1' ELSE '0' END) LRRY_DM FROM QX_AUTH A LEFT OUTER JOIN QX_USER_AUTH B ON A.AUTH_DM=B.AUTH_DM AND (B.USER_DM='"+name+"' OR B.USER_DM IS NULL)";
			} else {
				sql = "SELECT A.AUTH_MC,A.AUTH_DM,A.AUTH_CC,A.SJ_AUTH_DM,(CASE WHEN B.ROLE_LSH='"+name+"' THEN '1' ELSE '0' END) LRRY_DM FROM QX_AUTH A LEFT OUTER JOIN QX_ROLE_AUTH B ON A.AUTH_DM=B.AUTH_DM AND (B.ROLE_LSH='"+name+"' OR B.ROLE_LSH IS NULL)";
			}
			if(!isTopLevel) {
				sql = sql + " WHERE (A.SFDJ_BJ!='1')";
			}
			sql = sql + " ORDER BY A.AUTH_CC,A.SJ_AUTH_DM,A.AUTH_DM ASC";
			values = pm.queryForList(sql, Auth.class);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
		
		int k = 100;
		for(int i = 0; i < values.size(); i ++) {
			k++;
			Auth auth = values.get(i);
			String mc = auth.getAUTH_MC();
			String dm = auth.getAUTH_DM();
			String cc = auth.getAUTH_CC();
			String sj = auth.getSJ_AUTH_DM();
			String checked = auth.getLRRY_DM();
			TreeNode node = new TreeNode(dm, sj, mc, checked);
			if("1".equals(cc)) {
				// level 1
				TreeNode cnode = map.get(dm);
				if(cnode==null) {
					map.put(dm, node);
					root.addChildNode(node);
				} else {
					cnode.setId(node.getId());
					cnode.setText(node.getText());
					cnode.setChecked(node.getChecked());
				}
			} else {
				// level 2
				TreeNode cnode = map.get(sj);
				if(cnode==null) {
					cnode = new TreeNode(sj, "0", "", "0");
					map.put(sj, cnode);
					root.addChildNode(cnode);
				}
				cnode.addChildNode(node);
			}
		}
    	root.setIsExpand(true);
		JSONArray json2 = JSONArray.fromObject(root);
        this.result = json2.toString();  
        return "success";
    }
	public void setName(String name) {
		this.name = name;
	}
	public void setOrgdm(String orgdm) {
		this.orgdm = orgdm;
	}
}