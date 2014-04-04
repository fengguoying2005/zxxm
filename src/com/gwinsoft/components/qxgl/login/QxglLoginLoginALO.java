package com.gwinsoft.components.qxgl.login;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.qxgl.role.Role;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class QxglLoginLoginALO extends BaseALO {

	protected void doService() {
		User user = this.getData("user");
		String sql = "SELECT A.YX_BJ,A.USER_DM,A.ORG_DM_JG,A.ORG_DM_BM,A.USER_MC,B.ROLE_LSH,C.ROLE_MC FROM QX_USER A LEFT OUTER JOIN QX_USER_ROLE B ON A.USER_DM=B.USER_DM LEFT OUTER JOIN QX_ROLE C ON B.ROLE_LSH=C.ROLE_LSH  AND C.YX_BJ='1' WHERE A.USER_DM=? AND A.PASSWORD=? ORDER BY A.USER_DM,B.ROLE_LSH";
		DBPersistenceManager pm = getPM();
		SqlRowSet row = pm.quereyForRowSet(sql, new String[]{user.getUSER_DM(), user.getPASSWORD()});
		User _user = new User();
		List<Role> roles = new ArrayList<Role>();
		_user.setRoles(roles);
		boolean isFirst = true;
		while(row.next()) {
			Role role = new Role();
			String ROLE_LSH = row.getString("ROLE_LSH");
			String ROLE_MC = row.getString("ROLE_MC");
			if(isFirst) {
				isFirst = false;
				String USER_DM = row.getString("USER_DM");
				String USER_MC = row.getString("USER_MC");
				String ORG_DM_JG = row.getString("ORG_DM_JG");
				String ORG_DM_BM = row.getString("ORG_DM_BM");
				_user.setUSER_DM(USER_DM);
				_user.setUSER_MC(USER_MC);
				_user.setORG_DM_JG(ORG_DM_JG);
				_user.setORG_DM_BM(ORG_DM_BM);
				_user.setLRRY_DM(GwinSoftUtil.translate(ORG_DM_JG, DMB.getTranslateStr(DMB.getOrgDMB(ORG_DM_JG, 2, "J"))));
				_user.setYX_BJ(row.getString("YX_BJ"));
			}
			role.setROLE_LSH(ROLE_LSH);
			role.setROLE_MC(ROLE_MC);
			roles.add(role);
		}
		if(!"1".equals(_user.getYX_BJ())) {
			this.resEnv.setAPPException(new APPException("该用户不是有效状态！"));
		} else if(_user.getUSER_DM() != null) {
			this.putData("user", _user);
		} else {
			this.resEnv.setAPPException(new APPException("用户名或密码错误！"));
		}
		/*List<User> users = getPM().queryForList("SELECT * FROM QX_USER WHERE ACCOUNT=? AND PASSWORD=?", User.class, new String[]{user.getACCOUNT(), user.getPASSWORD()});
		if(users.size()>0) {
			this.putData("user", users.get(0));
		} else {
			this.resEnv.setAPPException(new APPException("用户名或密码错误！"));
		}*/
	}
}