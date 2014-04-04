package com.gwinsoft.components.qxgl.login;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;

public class QxglMoidfyPwdALO extends BaseALO {

	protected void doService() {
		User user = this.getData("user");
		String sql = "SELECT * FROM QX_USER WHERE USER_DM='"+user.getUSER_DM()+"'";
		DBPersistenceManager pm = this.getPM();
		User _user = pm.queryForObject(sql, User.class);
		if(_user==null) {
			throw new APPException("用户不存在!");
		} else {
			String mm = _user.getPASSWORD();
			if(!mm.equals(user.getPASSWORD())) {
				throw new APPException("密码有误!");
			} else {
				pm.executeUpdate("UPDATE QX_USER SET PASSWORD=? WHERE USER_DM=?", new String[]{user.getLRRY_DM(), user.getUSER_DM()});
			}
		}
		this.resEnv.addMessage("密码修改成功!");
	}
}