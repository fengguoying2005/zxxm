package com.gwinsoft.components2.yw.wjdc;

import java.util.Date;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.components2.job.SmsSender;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class WjdcModALO extends BaseALO {

	protected void doService() {
		String message = "";
		User user = reqEnv.getData("user");
		Wjdc wjdc = reqEnv.getData("wjdc");
		Date time = GwinSoftUtil.getSysDate();
		//String uid = GwinSoftUtil.getLSH();
		String BH = reqEnv.getData("LSH");
		DBPersistenceManager pm = this.getPM();
        wjdc.setLSH(BH);
		wjdc.setXG_SJ(time);
		wjdc.setXGRY_DM(user.getUSER_DM());
		String sfjs = wjdc.getSFJS();
		String sql = "SELECT * FROM DX_WJDC WHERE LSH=?";
		Wjdc temp = pm.queryForObject(sql, Wjdc.class, new String[]{wjdc.getLSH()});
		//SqlRowSet row = pm.quereyForRowSet(sql, new String[]{wjdc.getLSH()});
		if(true) {
			String js = temp.getSFJS();
			if("1".equals(js)) {
				this.resEnv.setAPPException(new APPException("问卷调查已结束不允许修改。"));
				message="问卷调查已结束不允许修改。";
				wjdc.setBZ(temp.getBZ());
				wjdc.setINFO(temp.getINFO());
				wjdc.setSFJS(temp.getSFJS());
				wjdc.setDCJL(temp.getDCJL());
			} else if("2".equals(js) && !"1".equals(sfjs)) {
				this.resEnv.setAPPException(new APPException("问卷调查已启动不允许修改。"));
				message="问卷调查已启动不允许修改。";
				wjdc.setBZ(temp.getBZ());
				wjdc.setINFO(temp.getINFO());
				wjdc.setSFJS(temp.getSFJS());
				wjdc.setDCJL(temp.getDCJL());
			} else {
				if("1".equals(sfjs) && "2".equals(js)) {
					//2->1  发送短信
					pm.executeUpdate("UPDATE DX_WJDC SET SFJS='1',DCJL=? WHERE LSH=?", wjdc);
					
					String sql2 = "SELECT 'DCFK'||M.LSH AS LSH,M.SJHM,'"+wjdc.getDCJL()+"' AS SMSINFO FROM DX_WJDC Z,DX_WJDCMX M WHERE Z.LSH=M.ZB_LSH AND M.FK_SJ IS NOT NULL AND Z.LSH='"+BH+"'";
					List<WjdcSMS> smss = pm.queryForList(sql2, WjdcSMS.class);
					for(WjdcSMS sms : smss) {
						//启动短信发送队列
						SmsSender.schedule(sms);
					}
					message="调查被成功关闭！调查结论已发送。";
					wjdc.setBZ(temp.getBZ());
					wjdc.setINFO(temp.getINFO());
				} else if("1".equals(sfjs) && "0".equals(js)) {
					//0->1
					pm.executeUpdate("UPDATE DX_WJDC SET SFJS='1' WHERE LSH=?", wjdc);
					message="调查被成功关闭！";
					wjdc.setBZ(temp.getBZ());
					wjdc.setINFO(temp.getINFO());
					wjdc.setDCJL(temp.getDCJL());
				} else {
					//0->0
					String sql2 = "SELECT BZ FROM DX_WJDC WHERE SUBSTR('"+wjdc.getBZ()+"',0,LENGTH(BZ))=BZ AND LSH!='"+BH+"' UNION SELECT BZ FROM DX_WJDC WHERE BZ LIKE '"+wjdc.getBZ()+"%' AND LSH!='"+BH+"'";
					SqlRowSet row = pm.quereyForRowSet(sql2);
					if(row.next()) {
						String tzm = row.getString("BZ");
						this.resEnv.setAPPException(new APPException("特征码与已存在的调查‘"+tzm+"’相似，请更换。"));
						message="特征码与已存在的调查‘"+tzm+"’相似，请更换。";
						resEnv.putData("message", message);
					} else {
						pm.executeUpdate("UPDATE DX_WJDC SET INFO=?,BZ=?,DCJL=?,XGRY_DM=?,XG_SJ=? WHERE LSH=?", wjdc);
						message="修改成功！";
					}
				}
			}
		}
		resEnv.putData("message", message);
	}
}
