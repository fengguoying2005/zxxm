package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.auth.Auth;
import com.gwinsoft.components.qxgl.role.Role;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.cache.gwincache.QxglCacheManager;
import com.gwinsoft.framework.core.helper.UserHelper;
import com.gwinsoft.framework.taglib.menu.AuthMenu;
import com.gwinsoft.framework.taglib.menu.AuthMenuItem;
import com.gwinsoft.framework.web.action.BaseAction;

public class AjaxAction extends BaseAction<AjaxIAO> {

	private static final long serialVersionUID = 1L;
	private String para1;
    private String para2;
    private String para3;
    private String para4;
    private String para5;
    private String para6;
    private String result;
    
	public String menu() throws Exception {
		List<Menu1> root = new ArrayList<Menu1>();
		
		AuthMenu menu = getMenu();
		for(AuthMenuItem item : menu.getMenuItems()) {
			//一级菜单
			Menu1 data = new Menu1();
			data.setText(item.getAuth().getAUTH_MC());
			item.sort();
			List<Auth> auts = item.getAuths();
			for(Auth auth: auts) {
				//二级菜单
				Menu2 data2 = new Menu2();
				data2.setText(auth.getAUTH_MC());
				data2.setUrl(auth.getAUTH_LJ());
				data.getChildren().add(data2);
			}
			root.add(data );
		}
		JSONArray json2 = JSONArray.fromObject(root);
		this.result = json2.toString();
		return "success";
	}
	private AuthMenu getMenu() {
		User user = UserHelper.getLoginUserFromRequest(request);
		List<Role> roles = user.getRoles();
		Map<String, Map<String, Object>> auth = null;
		for(int i = 0, j = roles.size(); i < j; i ++) {
			Map<String, Map<String, Object>> au = QxglCacheManager.getRoleAuth(roles.get(i));
			if(auth == null) {
				auth = au;
			} else {
				auth = QxglCacheManager.getMergeAuth(auth, au);
			}
		}
		Map<String, Map<String, Object>> au = QxglCacheManager.getUserAuth(user.getUSER_DM());
		if(auth == null) {
			auth = au;
		} else {
			auth = QxglCacheManager.getMergeAuth(auth, au);
		}
		AuthMenu menu = QxglCacheManager.getMenu(auth);
		try {
			boolean isDJORG = false;
			Map<String, Map<String, Object>> cache = CacheManager.getCache("DJORG_CACHE");
			Iterator<String> it = cache.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				if(key.equals(user.getORG_DM_JG())) {
					isDJORG = true;
				}
			}
			if(!isDJORG) {
				Map djdmb = DMB.getDMB("AUTH_CACHE","SFDJ_BJ");
				List<AuthMenuItem> items = menu.getMenuItems();
				for(AuthMenuItem item : items) {
					String AUTH_DM = item.getAuth().getAUTH_DM();
					List<Auth> subAuths = item.getAuths();
					for(int i = subAuths.size() - 1; i >=0; i --) {
						Auth subAuth = subAuths.get(i);
						String SUB_AUTH_DM = subAuth.getAUTH_DM();
						if("1".equals(djdmb.get(SUB_AUTH_DM))) {
							subAuths.remove(i);
						}
					}
				}
			}
		} catch (Exception e) {
		}
		menu.sort();
		return menu;
	}
	public String test() throws Exception {
		return "success";
	}
	public String getPara1() {
		return para1;
	}
	public void setPara1(String para1) {
		this.para1 = para1;
	}
	public String getPara2() {
		return para2;
	}
	public void setPara2(String para2) {
		this.para2 = para2;
	}
	public String getPara3() {
		return para3;
	}
	public void setPara3(String para3) {
		this.para3 = para3;
	}
	public String getPara4() {
		return para4;
	}
	public void setPara4(String para4) {
		this.para4 = para4;
	}
	public String getPara5() {
		return para5;
	}
	public void setPara5(String para5) {
		this.para5 = para5;
	}
	public String getPara6() {
		return para6;
	}
	public void setPara6(String para6) {
		this.para6 = para6;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}