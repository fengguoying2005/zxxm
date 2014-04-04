package com.gwinsoft.components.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.jbxx.Nsrjbxx;
import com.gwinsoft.components.nsrgl.nsrgroup.Nsrgroup;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.iao.BaseIAO;
import com.gwinsoft.framework.web.action.BaseAction;
import com.gwinsoft.util.GwinSoftUtil;
import com.itextpdf.text.log.SysoLogger;

public class UserTreeAction extends BaseAction<BaseIAO> {

	private static final long serialVersionUID = 7395593013189662187L;
	
	private String orgdm;
	private String name;
    private String result;

    /**
     * 审核员列表
     */
    public String shycombox() {
    	User user = UserHelper.getLoginUserFromRequest(request);
    	String sql = 
    	"SELECT QX_USER.USER_DM,QX_USER.USER_MC FROM QX_USER,QX_AUTH,QX_USER_AUTH WHERE QX_USER.USER_DM=QX_USER_AUTH.USER_DM AND QX_AUTH.AUTH_DM=QX_USER_AUTH.AUTH_DM AND QX_AUTH.AUTH_LJ='smssh/sh!list.action' AND QX_USER.YX_BJ='1' AND QX_USER.ORG_DM_JG='"+user.getORG_DM_JG()+"'"+
    	"UNION " + 
    	"SELECT QX_USER.USER_DM,QX_USER.USER_MC FROM QX_USER,QX_AUTH,QX_USER_ROLE,QX_ROLE_AUTH WHERE QX_USER.USER_DM=QX_USER_ROLE.USER_DM AND QX_ROLE_AUTH.ROLE_LSH=QX_USER_ROLE.ROLE_LSH AND QX_AUTH.AUTH_DM=QX_ROLE_AUTH.AUTH_DM AND QX_AUTH.AUTH_LJ='smssh/sh!list.action' AND QX_USER.YX_BJ='1' AND QX_USER.ORG_DM_JG='"+user.getORG_DM_JG()+"'";
    	JSONArray jsonArray = new JSONArray();
    	DBPersistenceManager pm = null;
    	JSONObject jsonObject = null;
		try {
			pm = DBHelper.getPm();
			List<User> shys = pm.queryForList(sql , User.class, new Object[] {});
			for(User _user : shys) {
				jsonObject = new JSONObject();
				jsonObject.put("DM", _user.getUSER_DM());
				jsonObject.put("MC", _user.getUSER_MC());
				jsonArray.add(jsonObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
		this.result = jsonArray.toString();
		try {
			response.setContentType("text/html; charset=utf-8");
			response.getWriter().print(this.result);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    public String nsrgrouptree() {
    	User user = UserHelper.getLoginUserFromRequest(request);
    	//组树
    	WdTreeNode root = new WdTreeNode(user.getORG_DM_JG(),"纳税人群组","",0,true, true, false);
    	root.setShowcheck(false);
    	DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			String sql = "SELECT * FROM NSR_GROUP WHERE ORG_DM='"+user.getORG_DM_JG()+"'";
			List<Nsrgroup> nsrgroup = pm.queryForList(sql , Nsrgroup.class, new Object[] {});
			for(Nsrgroup group : nsrgroup) {
				WdTreeNode node = new WdTreeNode(group.getLSH(),group.getGROUP_NAME()+"(共"+group.getGROUP_COUNT()+"人)",group.getLSH(),0,true, true, false);
        		node.setShowcheck(true);
        		node.setpId(user.getORG_DM_JG());
        		root.addChild(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
		JSONArray json2 = JSONArray.fromObject(root);
		this.result = json2.toString();
    	result = result.replaceAll("childNodes", "ChildNodes");
    	return "success";
    }
    public String thensrtree() {
    	//JSONArray json = new JSONArray();
    	WdTreeNode json = new WdTreeNode("ALL","纳税人结果","",0,true, true, false);
    	User user = UserHelper.getLoginUserFromRequest(request);
    	String THE_TEL = request.getParameter("THE_TEL");
    	String THE_NSRBM = request.getParameter("THE_NSRBM");
    	String THE_NSRMC = request.getParameter("THE_NSRMC");
    	DBPersistenceManager pm = null;
		try {
			pm = DBHelper.getPm();
			StringBuffer sql = new StringBuffer("SELECT NSRBM,NSRMC,ORG_DM FROM NSR_JBXX WHERE 1=1");
			Map jg = DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J");
			if(jg!=null) {
				sql.append(" AND ORG_DM IN (''");
				Iterator it = jg.keySet().iterator();
				while(it.hasNext()) {
					String swjg = (String) it.next();
					sql.append(",'"+swjg+"'");
				}
				sql.append(")");
			}
			if(THE_TEL != null && !"".equals(THE_TEL)) {
				sql.append(" AND (FRSJH LIKE '%"+THE_TEL+"%' OR CWJLSJH LIKE '%"+THE_TEL+"%' OR BSRYSJH LIKE '%"+THE_TEL+"%')");
			}
			if(THE_NSRBM != null && !"".equals(THE_NSRBM)) {
				sql.append(" AND NSRBM LIKE '%"+THE_NSRBM+"%'");
			}
			if(THE_NSRMC != null && !"".equals(THE_NSRMC)) {
				sql.append(" AND NSRMC LIKE '%"+THE_NSRMC+"%'");
			}
			List<Nsrjbxx> nsrjbxxs = pm.queryForList(sql.toString()+" AND ROWNUM<=1000" , Nsrjbxx.class, new Object[] {});
			for(Nsrjbxx jbxx : nsrjbxxs) {
				WdTreeNode node = new WdTreeNode(jbxx.getNSRBM(),jbxx.getNSRMC()+"("+jbxx.getNSRBM()+")","NSR"+jbxx.getNSRBM()+":"+jbxx.getORG_DM(),0,true, false, false);
        		node.setShowcheck(true);
        		node.setpId("ALL");
            	json.addChild(node);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pm !=null) {
				pm.close();
			}
		}
		JSONArray json2 = JSONArray.fromObject(json);
		this.result = json2.toString();
    	result = result.replaceAll("childNodes", "ChildNodes");
    	result = result.replaceAll("\\\"", "'");
    	return "success";
    }
    public String nsrtree() {
    	User user = UserHelper.getLoginUserFromRequest(request);
    	String id = request.getParameter("id");
    	JSONArray json = null;
    	if(id==null) {
    		WdTreeNode root = new WdTreeNode(user.getORG_DM_JG(),user.getLRRY_DM(),user.getORG_DM_JG(),0,false, false, true);
        	root.setShowcheck(true);
        	json = JSONArray.fromObject(root);
    	} else {
        	json = new JSONArray();
        	String FIND_NSRBM = request.getParameter("FIND_NSRBM");
        	String FIND_NSRMC = request.getParameter("FIND_NSRMC");
        	try {
//        		System.out.println(".."+FIND_NSRMC);
//        		System.out.println("GB2312"+new String(FIND_NSRMC.getBytes("ISO-8859-1"),"GB2312"));
//        		System.out.println("GBK"+new String(FIND_NSRMC.getBytes("ISO-8859-1"),"GBK"));
//        		System.out.println("UTF-8"+new String(FIND_NSRMC.getBytes("ISO-8859-1"),"UTF-8"));
//        		System.out.println("ISO-8859-1"+new String(FIND_NSRMC.getBytes("ISO-8859-1"),"ISO-8859-1"));
//        		
//        		System.out.println("UTF-8GB2312"+new String(FIND_NSRMC.getBytes("UTF-8"),"GB2312"));
//        		System.out.println("UTF-8GBK"+new String(FIND_NSRMC.getBytes("UTF-8"),"GBK"));
        		
        		FIND_NSRMC = new String(FIND_NSRMC.getBytes("ISO-8859-1"),"GB2312");
        		
//        		System.out.println("##"+FIND_NSRMC);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
        	String FIND_HYDM = request.getParameter("FIND_HYDM");
        	String FIND_SBFS = request.getParameter("FIND_SBFS");
        	String FIND_DJLX = request.getParameter("FIND_DJLX");
        	String FIND_ZCLX = request.getParameter("FIND_ZCLX");
//    		System.out.println("^^^^^^^^^^^^"+id+";"+FIND_DJLX);
        	//取得所有单位
        	String orgsql = "SELECT * FROM";
        	Map orgs = DMB.getOrgDMB(id, 3, "J");
        	Iterator it = orgs.keySet().iterator();
        	while(it.hasNext()) {
        		String key = (String) it.next();
        		if(key.equals(id)) {
        			continue;
        		}
        		WdTreeNode node = new WdTreeNode(key,(String) orgs.get(key),key,0,false, false, true);
        		node.setShowcheck(true);
        		node.setChildNodes(null);
            	json.add(node);
        	}
        	//add nsr
        	DBPersistenceManager pm = null;
    		try {
    			pm = DBHelper.getPm();
    			String sql = "SELECT * FROM NSR_JBXX WHERE ORG_DM='"+id+"'";
    			if(FIND_NSRBM != null && !"".equals(FIND_NSRBM)) {
    				sql += " AND NSRBM LIKE '%"+FIND_NSRBM+"%'";
    			}
    			if(FIND_NSRMC != null && !"".equals(FIND_NSRMC)) {
    				sql += " AND NSRMC LIKE '%"+FIND_NSRMC+"%'";
    			}
    			if(FIND_HYDM != null && !"".equals(FIND_HYDM)) {
    				sql += " AND HYDM='"+FIND_HYDM+"'";
    			}
    			if(FIND_SBFS != null && !"".equals(FIND_SBFS)) {
    				sql += " AND SBFS='"+FIND_SBFS+"'";
    			}
    			if(FIND_DJLX != null && !"".equals(FIND_DJLX)) {
    				sql += " AND DJLX='"+FIND_DJLX+"'";
    			}
    			if(FIND_ZCLX != null && !"".equals(FIND_ZCLX)) {
    				sql += " AND ZCLX='"+FIND_ZCLX+"'";
    			}
    			List<Nsrjbxx> nsrjbxxs = pm.queryForList(sql+" AND ROWNUM<=1000" , Nsrjbxx.class, new Object[] {});
    			for(Nsrjbxx jbxx : nsrjbxxs) {
    				WdTreeNode node = new WdTreeNode(jbxx.getNSRBM(),jbxx.getNSRMC(),"NSR"+jbxx.getNSRBM()+":"+jbxx.getORG_DM(),0,true, false, false);
            		node.setShowcheck(true);
                	json.add(node);
    			}
//    			for(int i = 0 ; i < json.size(); i ++) {
//    				Object jsonObj = json.get(i);
//    				if(jsonObj!=null) {
//    					JSONObject jsonO = (JSONObject) jsonObj;
//    				}
//    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			if(pm !=null) {
    				pm.close();
    			}
    		}
    	}
    	//组树
    	
    	
    	
		this.result = json.toString();
    	result = result.replaceAll("childNodes", "ChildNodes");
    	result = result.replaceAll("\\\"", "'");
    	return "success";
    }
    public String grouptree() {
    	User user = UserHelper.getLoginUserFromRequest(request);
    	
    	//组树
    	WdTreeNode root = new WdTreeNode(user.getORG_DM_JG(),"本局群组","",0,true, true, false);
    	root.setShowcheck(false);
    	Map<String, Map<String, Object>> cache = null;
		//人员树
		Map<String, Map<String, Object>> allpersons = null;

		String rytype = request.getParameter("rytype");
		if("czy".equals(rytype)) {
			cache = CacheManager.getCache("CZYGROUP_CACHE");
			allpersons = CacheManager.getCache("CZYGROUPMX_CACHE");
		} else {
			cache = CacheManager.getCache("ORGGROUP_CACHE");
			allpersons = CacheManager.getCache("ORGGROUPMX_CACHE");
		}
		
    	Iterator<Map<String, Object>> it = cache.values().iterator();
		while(it.hasNext()) {
			Map<String, Object> org = it.next();
			Object ORG_DM_JG = org.get("ORG_DM_JG");
			if(user.getORG_DM_JG().equals(ORG_DM_JG)) {
				String GROUP_LSH = (String) org.get("GROUP_LSH");
				String GROUP_MC = (String) org.get("GROUP_NAME");
				WdTreeNode node = new WdTreeNode(GROUP_LSH,GROUP_MC,"",0,true, false, false);
				node.setpId(user.getORG_DM_JG());
				root.addChild(node);
			}
		}
		//rytype
		addGroupPerson(root, allpersons, CacheManager.getCache("ORG_CACHE"));
		JSONArray json2 = JSONArray.fromObject(root);
		this.result = json2.toString();
    	result = result.replaceAll("childNodes", "ChildNodes");
    	return "success";
    }
    private void addGroupPerson(WdTreeNode node, Map<String, Map<String, Object>> allpersons, Map<String, Map<String, Object>> orgs) {
    	Map zwmap = DMB.getDMB("ZW_CACHE", "ZW_MC");
    	for(Map<String, Object> map : allpersons.values()) {
    		String GROUP_LSH = (String) map.get("GROUP_LSH");
			WdTreeNode person = new WdTreeNode();
			person.setpId(GROUP_LSH);
			person.setId(GROUP_LSH+"USER"+(String) map.get("USER_DM"));
			person.setText((String) map.get("USER_MC"));
			String jg = (String) map.get("ORG_DM_JG");
			Map<String, Object> jgorg = orgs.get(jg);
			String bm = (String) map.get("ORG_DM_BM");
			Map<String, Object> bmorg = orgs.get(bm);
			
			String ZWDM = (String) map.get("ZW_DM");
			String ZWMC = "";
			if(ZWDM!=null && !"".equals(ZWDM)) {
				ZWMC = GwinSoftUtil.translate(ZWDM, DMB.getTranslateStr(zwmap));
			} else {
				ZWDM = "";
			}
			String JGPX = "";
			if(map.get("JGPX")!=null) {
				JGPX = (String)map.get("JGPX");
			}
			person.setValue((String) map.get("USER_DM")
					+ "~" + (String) map.get("USER_MC") 
					+ "~" + (String) map.get("SJHM")
					+ "~" + jg
					+ "~" + bm
					+ "~" + (jgorg==null?"":(String) jgorg.get("ORG_MC"))
					+ "~" + (bmorg==null?"":(String) bmorg.get("ORG_MC"))
					+ "~" + ZWDM
					+ "~" + ZWMC
					+"~" +JGPX
			);
//			System.out.println(person.getValue());
			//person.setShowcheck(false);
			node.addChild(person);
    	}
    }
    public String usertree() {
//		long a = System.currentTimeMillis();
    	User user = UserHelper.getLoginUserFromRequest(request);
		//机构树
    	WdTreeNode root = new WdTreeNode(user.getORG_DM_JG(),user.getLRRY_DM(),"00000000000000000000",0,true, true, false);
    	root.setFolder(true);
    	Map<String, Map<String, Object>> cache = CacheManager.getCache("ORG_CACHE");
		Map<String, String> selected2 = new HashMap<String,String>();
		List list = new ArrayList();
		list.add(user.getORG_DM_JG());
		for (int i = 0; i < list.size(); i++) {
			String key = (String) list.get(i);
			if (!selected2.containsKey(key)) {
				Map<String, Object> data = cache.get(key);
				selected2.put(key, (String) data.get("ORG_MC"));
				{
					String orgtype = (String) data.get("ORG_TYPE");
					Object JGPXOBJ = data.get("JGPX");
					String JGPX = "ZZZZZZZZZZ";
					if(JGPXOBJ!=null && !"".equals(JGPXOBJ)) {
						JGPX = (String)JGPXOBJ;
					}
					WdTreeNode node = new WdTreeNode(key,(String) data.get("ORG_MC"),JGPX,0,true, false, false);
					node.setFolder(true);
					node.setpId((String) data.get("SJ_ORG_DM"));
					root.addChild(node);
				}
				Iterator<Map<String, Object>> it = cache.values().iterator();
				while(it.hasNext()) {
					Map<String, Object> org = it.next();
					Object obj = org.get("SJ_ORG_DM");
					Object ORG_TYPE = org.get("ORG_TYPE");
					if(ORG_TYPE!=null && obj!=null) {
						String sj = (String) obj;
						if(key.equals(sj)) {
							String ORG_DM = (String) org.get("ORG_DM");
							list.add(ORG_DM);
						}
					}
				}
			}
		}
		//人员树
		Map<String, Map<String, Object>> allpersons = CacheManager.getCache("USER_CACHE");
		//allpersons = DMB.sortByValue(allpersons, "JGPX", true);
		addPerson(root, allpersons, cache);

//    	WdTreeNode root2 = new WdTreeNode(user.getORG_DM_JG(),user.getLRRY_DM(),"00000000000000000000",0,true, true, false);
		//
		sortOrg(root);
		validate(root);
		rebuild(root);
		
//		List<WdTreeNode> chs = root.getChildNodes();
		
		JSONArray json2 = JSONArray.fromObject(root);
		this.result = json2.toString();
    	result = result.replaceAll("childNodes", "ChildNodes");
//		System.out.println("spite "+(System.currentTimeMillis()-a)+"ms.");
    	return "success";
    }
    void rebuild(WdTreeNode node) {
    	if(!node.isFolder()) {
    		return;
    	}
		List<WdTreeNode> nodes = node.getChildNodes();
		for (int i = nodes.size() - 1; i >= 0; i--) {
			WdTreeNode _node = nodes.get(i);
			if(_node.isDeleteflag()) {
				nodes.remove(i);
			} else {
				rebuild(_node);
			}
		}
    }

    void rebuild(WdTreeNode root, WdTreeNode node) {
    	
    }
    boolean validate(WdTreeNode node) {
    	if(node.isFolder()) {
    		if(node.isHasChildren()) {
    			boolean isValiedate = false;
    			List<WdTreeNode> nodes = node.getChildNodes();
    			for(WdTreeNode _node : nodes) {
    				isValiedate = validate(_node) || isValiedate;
    			}
    			if(!isValiedate) {
        			node.setDeleteflag(true);
    			}
    			return isValiedate;
    		} else {
    			node.setDeleteflag(true);
    			return false;
    		}
    	}
    	return true;
    }
    
    private void sortOrg(WdTreeNode node) {
    	List<WdTreeNode> childs = node.getChildNodes();
    	if(childs==null||childs.size()==0) {
    		return;
    	}
    	Collections.sort(childs);
    	for(WdTreeNode _node:childs) {
    		sortOrg(_node);
    	}
    }
    private void addPerson(WdTreeNode node, Map<String, Map<String, Object>> allpersons, Map<String, Map<String, Object>> orgs) {
    	String rytype = request.getParameter("rytype");
    	Map zwmap = DMB.getDMB("ZW_CACHE", "ZW_MC");
    	for(Map<String, Object> map : allpersons.values()) {
    		if(map.get("CZY_BJ")!=null) {
        		String CZY_BJ = (String) map.get("CZY_BJ");
        		if("czy".equals(rytype)) {
            		if(!"1".equals(CZY_BJ)) {
            			continue;
            		}
        		} else {
            		if("1".equals(CZY_BJ)) {
            			continue;
            		}
        		}
    		}
    		String type = (String) map.get("ORG_DM_BM");
			WdTreeNode person = new WdTreeNode();
			person.setpId(type);
			person.setId("USER"+(String) map.get("USER_DM"));
			person.setText((String) map.get("USER_MC"));
			String jg = (String) map.get("ORG_DM_JG");
			Map<String, Object> jgorg = orgs.get(jg);
			String bm = (String) map.get("ORG_DM_BM");
			Map<String, Object> bmorg = orgs.get(bm);
			String ZWDM = (String) map.get("ZW_DM");
			String ZWMC = "";
			if(ZWDM!=null && !"".equals(ZWDM)) {
				ZWMC = GwinSoftUtil.translate(ZWDM, DMB.getTranslateStr(zwmap));
			} else {
				ZWDM = "";
			}
			String JGPX = "";
			if(map.get("JGPX")!=null) {
				JGPX = (String)map.get("JGPX");
			}
			person.setValue(
					(String) map.get("USER_DM")
					+ "~" + (String) map.get("USER_MC") 
					+ "~" + (String) map.get("TEL")
					+ "~" + (String) map.get("ORG_DM_JG")
					+ "~" + bm
					+ "~" + (jgorg==null?"":(String) jgorg.get("ORG_MC"))
					+ "~" + (bmorg==null?"":(String) bmorg.get("ORG_MC"))
					
					+ "~" + ZWDM
					+ "~" + ZWMC
					+"~" +JGPX
			);
			node.addChild(person);
    	}
    }
	// ajax返回结果
    public String getResult() {
        return result;
    }

	public String getOrgdm() {
		return orgdm;
	}

	public void setOrgdm(String orgdm) {
		this.orgdm = orgdm;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setResult(String result) {
		this.result = result;
	}
    
}
