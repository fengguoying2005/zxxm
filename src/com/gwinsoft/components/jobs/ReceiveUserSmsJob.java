package com.gwinsoft.components.jobs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gwinsoft.components.nsrgl.jbxx.Nsrjbxx;
import com.gwinsoft.components.smssh.sms.ReceiveUserSMS;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;

//TODO 无用
@Deprecated
public class ReceiveUserSmsJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		List<UserSmsData> list = null;
		try {
			list = ReceiveUserSMS.receiveusersms();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
//		if (list != null) {
//			System.out.println("获得回复短信数:"+list.size());
//		}
		if (list != null && list.size() > 0) {
			DBPersistenceManager pm = null;
			try {
				String sqlI = "INSERT INTO SMS_INBOX(KEYID,PHONE,MSG,RECEIVETIME,KZM,TYPE_DM,EXT1,EXT2,EXT3,EXT4,EXT5) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
				String sqlS = "SELECT 1 AS NUMB FROM SMS_INBOX WHERE KEYID=";
				pm = DBHelper.getPm();
				for(UserSmsData data : list) {
					if(data.getMSG()==null) {
						continue;
					} else {
						//类型
						String type = "";
						String msg = data.getMSG().trim();
						boolean isHave = false;
						if(msg.toUpperCase().startsWith("TS")) {
							//投诉：TS+投诉内容
							type = "TSJBCX";
							data.setEXT1(msg.substring(2));
							isHave = true;
						} else if(msg.toUpperCase().startsWith("FPZJ") && (msg.length()==12 || msg.length()==13)) {
							//发票中奖查询：FPZJ+（sj或fx等市的缩写）+*******（开奖年月，如201306）
							type = "FPZJCX";
							//EXT1:市的缩写
							String sx = msg.substring(4, msg.length()-6);
							//EXT2:开奖年月，如201306
							String ym = msg.substring(msg.length()-6);
							try {
								Integer.parseInt(ym);
								isHave = true;
							} catch (Exception e) {
							}
							data.setEXT1(sx);
							data.setEXT2(ym);
						} else if(msg.toUpperCase().startsWith("F") && msg.length()==21) {
							//发票真伪查询：F+12位发票号码8位发票代码
							type = "FPZWCX";
							data.setEXT1(msg.substring(1, 13));
							data.setEXT2(msg.substring(13));
							isHave = true;
						} else if(msg.toUpperCase().indexOf("SF")>-1) {
							//税费查询：“纳税人识别码”+“SF****年**月”
							type = "SFCX";
							String ym = msg.substring(msg.toUpperCase().indexOf("SF")+2);
							if(ym.length()==6) {
								try {
									Integer.parseInt(ym);
									data.setEXT1(msg.substring(0, msg.toUpperCase().indexOf("SF")));//NSR
									data.setEXT2(ym);//YEARMONTH
									isHave = true;
								} catch (Exception e) {
								}
							}
						} 
						if(!isHave){
							//问卷调查回复
							type = "WHAT";
						}
						data.setTYPE_DM(type);
						data.setMSG(msg);
					}
					List<Map<String, Object>> rs = pm.queryForList(sqlS+"'"+data.getKEYID()+"'");
					int n = rs.size();
					if(n==0) {
						pm.executeUpdate(sqlI, data);
						if("TD".equalsIgnoreCase(data.getMSG())) {
							String sql_nsr = "SELECT NSRBM,NSRMC,ORG_DM FROM NSR_JBXX where FRSJH='"+data.getPHONE()+"' OR CWJLSJH='"+data.getPHONE()+"' OR BSRYSJH='"+data.getPHONE()+"'";
							List<Nsrjbxx> nsrs = pm.queryForList(sql_nsr, Nsrjbxx.class);
							for(Nsrjbxx nsr:nsrs) {
								String sb = "INSERT INTO T_HMD(LSH,NSRBM,NSRMC,ORG_DM_J,SJHM,SJLX_DM,RYLX_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,INFO) " +
										"SELECT SYS_GUID() AS LSH,'"+nsr.getNSRBM()+"'NSRBM,'"+nsr.getNSRMC()+"'NSRMC,'"+nsr.getORG_DM()+"' AS ORG_DM_J,'"+data.getPHONE()+"' AS SJHM,'04' AS SJLX_DM,'02' AS RYLX_DM,'"+data.getPHONE()+"' AS LRRY_DM,'"+data.getPHONE()+"' AS XGRY_DM,SYSDATE AS LR_SJ,SYSDATE AS XG_SJ,'' AS INFO FROM DUAL";
								pm.executeUpdate(sb.toString());
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pm != null) {
					pm.close();
				}
			}
		}
	}
}