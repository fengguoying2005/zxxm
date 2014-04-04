package com.gwinsoft.components.nsrgl.nsrimport;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrgl.jbxx.Nsrjbxx;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class NsrimportALO extends BaseALO {

	protected void doService() {
		User user  = this.getData("user");
		String ORG_DM  = this.getData("ORG_DM");
		String swjg = GwinSoftUtil.translate(ORG_DM, DMB.getTranslateStr(DMB.getOrgDMB(user.getORG_DM_JG(), 1, "J")));
		Map djztmap = DMB.sortByKey(DMB.getDMB("DJZT_CACHE", "DJZT_MC"),true);
		Map djlxmap = DMB.sortByKey(DMB.getDMB("DJLX_CACHE", "DJLX_MC"),true);
		Map zclxmap = DMB.sortByKey(DMB.getDMB("ZCLX_CACHE", "ZCLX_MC"),true);
		Map sbfsmap = DMB.sortByKey(DMB.getDMB("SBFS_CACHE", "SBFS_MC"),true);
		Map hymap = DMB.sortByKey(DMB.getDMB("HY_CACHE", "HY_MC"),true);
		
		Workbook book = this.getData("book");
		Sheet[] sheets = book.getSheets();
		Date time = GwinSoftUtil.getSysDate();
		DBPersistenceManager pm = this.getPM();
		int w = 0;
		for(int i = 0; i < sheets.length; i ++) {
			Sheet sheet = sheets[i];
			int rows = sheet.getRows();
			if(sheet.getColumns()==1) {
				continue;
			}
			if(rows<1) {
				throw new APPException("文件中无内容，不允许导入。");
			}
			if("行业代码".equals(sheet.getCell(0, 0).getContents())) {
				continue;
			}
			if(sheet.getColumns()<17) {
				throw new APPException("文件内容样式非法，请参考导出的模板样式。");
			}
			Cell cell0 = sheet.getCell(0, 0);
			Cell cell1 = sheet.getCell(1, 0);
			Cell cell2 = sheet.getCell(2, 0);
			Cell cell3 = sheet.getCell(3, 0);
			Cell cell4 = sheet.getCell(4, 0);
			Cell cell5 = sheet.getCell(5, 0);
			Cell cell6 = sheet.getCell(6, 0);
			Cell cell7 = sheet.getCell(7, 0);
//			Cell cell8 = sheet.getCell(8, 0);
//			Cell cell9 = sheet.getCell(9, 0);
//			Cell cell10 = sheet.getCell(10, 0);
//			Cell cell11 = sheet.getCell(11, 0);
//			Cell cell12 = sheet.getCell(12, 0);
//			Cell cell13 = sheet.getCell(13, 0);
//			Cell cell14 = sheet.getCell(14, 0);
//			Cell cell15 = sheet.getCell(15, 0);
//			Cell cell16 = sheet.getCell(16, 0);
			//判断格式
			String c0 = cell0.getContents();
			String c1 = cell1.getContents();
			String c2 = cell2.getContents();
			String c3 = cell3.getContents();
			String c4 = cell4.getContents();
			String c5 = cell5.getContents();
			String c6 = cell6.getContents();
			String c7 = cell7.getContents();
//			String c8 = cell8.getContents();
//			String c9 = cell9.getContents();
//			String c10 = cell10.getContents();
//			String c11 = cell11.getContents();
//			String c12 = cell12.getContents();
//			String c13 = cell13.getContents();
//			String c14 = cell14.getContents();
//			String c15 = cell15.getContents();
//			String c16 = cell16.getContents();
			if(i==0) {
				if (	   c0 == null || !"纳税人编码".equals(c0.trim()) 
						|| c1 == null || !"纳税人名称".equals(c1.trim()) 
						|| c2 == null || !"所属行业".equals(c2.trim()) 
						|| c3 == null || !"所属市".equals(c3.trim()) 
						|| c4 == null || !"主管税务机关".equals(c4.trim()) 
						|| c5 == null || !"法人手机号码".equals(c5.trim()) 
						|| c6 == null || !"财务经理手机号码".equals(c6.trim()) 
						|| c7 == null || !"办税人员手机号码".equals(c7.trim()) 
					)
				{
					throw new APPException("文件内容样式非法，请参考导出的模板样式。");
				}
			}
			
			//保存
			String sql = "INSERT INTO SMS_NSRDATAMX(NSRDATAMX_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,ZSXM,ZSPM,SKSSQ_Q,SKSSQ_Z,JE,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			for(int n = 1; n < rows; n ++) {
				String NSRBM = sheet.getCell(0, n).getContents();
				if(NSRBM==null || "".equals(NSRBM.trim())) {
					continue;
				}
				String NSRMC = sheet.getCell(1, n).getContents();
				String SSHY = sheet.getCell(2, n).getContents();
				String SSS = sheet.getCell(3, n).getContents();
				String SWJG = sheet.getCell(4, n).getContents();
//				String ZGY = sheet.getCell(5, n).getContents();
//				String SBFS = sheet.getCell(6, n).getContents();
//				String DJZT = sheet.getCell(7, n).getContents();
//				String DJLX = sheet.getCell(8, n).getContents();
//				String ZCDZ = sheet.getCell(9, n).getContents();
//				String ZCLX = sheet.getCell(10, n).getContents();
//				String FR = sheet.getCell(11, n).getContents();
				String FRDH = sheet.getCell(5, n).getContents();
//				String CW = sheet.getCell(13, n).getContents();
				String CWDH = sheet.getCell(6, n).getContents();
//				String BS = sheet.getCell(15, n).getContents();
				String BSDH = sheet.getCell(7, n).getContents();
				Nsrjbxx nsrjbxx = new Nsrjbxx();
				String uid = GwinSoftUtil.getLSH();
				nsrjbxx.setLSH(uid);
				nsrjbxx.setNSRBM(NSRBM);
				nsrjbxx.setNSRMC(NSRMC);
				nsrjbxx.setHYDM(SSHY);
				nsrjbxx.setSSS(SSS);
				nsrjbxx.setORG_DM(ORG_DM);
				nsrjbxx.setFRSJH(FRDH);
				nsrjbxx.setCWJLSJH(CWDH);
				nsrjbxx.setBSRYSJH(BSDH);
				nsrjbxx.setLR_SJ(time);
				nsrjbxx.setXG_SJ(time);
				//判断excel的内容
				if(!SWJG.trim().equals(swjg)) {
					throw new APPException("导入文件时，第"+(n+1)+"行'主管税务机关'出错，应为‘"+swjg+"’，请检查。");
				}
//				//转码：注册类型
//				{
//					Iterator it = zclxmap.keySet().iterator();
//					boolean isHave = false;
//					while(it.hasNext()) {
//						String key = (String) it.next();
//						String value = (String) zclxmap.get(key);
//						if(value.equals(nsrjbxx.getZCLX().trim())) {
//							isHave = true;
//							nsrjbxx.setZCLX(key);
//							break;
//						}
//					}
//					if(!isHave) {
//						//throw new APPException("导入文件时，第"+(n+1)+"行'注册类型'出错，现有注册类型中无此类型，请检查。");
//						nsrjbxx.setZCLX("");
//					}
//				}
				//转码：所属行业
				{
					Iterator it = hymap.keySet().iterator();
					boolean isHave = false;
					while(it.hasNext()) {
						String key = (String) it.next();
						String value = (String) hymap.get(key);
						if(value.equals(nsrjbxx.getHYDM().trim())) {
							isHave = true;
							nsrjbxx.setHYDM(key);
							break;
						}
					}
					if(!isHave) {
						//throw new APPException("导入文件时，第"+(n+1)+"行'所属行业'出错，现有行业中无此类型，请检查。");
						nsrjbxx.setHYDM("");
					}
				}
				StringBuffer sb1 = new StringBuffer("");
				StringBuffer sb2 = new StringBuffer("");
				StringBuffer sb3 = new StringBuffer("");
				//手机号码
				if(FRDH==null || "".equals(FRDH)) {
					//throw new APPException("导入文件时，第"+(n+1)+"行‘法人手机号码’出错，手机号码须为11位数字，请检查。");
				} else {
					try {
						Long.parseLong(FRDH);
						sb1.append("FRSJH=?,");
						sb2.append("FRSJH,");
						sb3.append("?,");
					} catch (NumberFormatException e) {
						throw new APPException("导入文件时，第"+(n+1)+"行‘法人手机号码’出错，手机号码须为数字，请检查。");
					}
				}
				if(CWDH==null || "".equals(CWDH)) {
					//throw new APPException("导入文件时，第"+(n+1)+"行‘财务经理手机号码’出错，手机号码须为11位数字，请检查。");
				} else {
					try {
						Long.parseLong(CWDH);
						sb1.append("CWJLSJH=?,");
						sb2.append("CWJLSJH,");
						sb3.append("?,");
					} catch (NumberFormatException e) {
						throw new APPException("导入文件时，第"+(n+1)+"行‘财务经理手机号码’出错，手机号码须为数字，请检查。");
					}
				}
				if(BSDH==null || "".equals(BSDH)) {
					//throw new APPException("导入文件时，第"+(n+1)+"行‘办税人手机号码’出错，手机号码须为11位数字，请检查。");
				} else {
					try {
						Long.parseLong(BSDH);
						sb1.append("BSRYSJH=?,");
						sb2.append("BSRYSJH,");
						sb3.append("?,");
					} catch (NumberFormatException e) {
						throw new APPException("导入文件时，第"+(n+1)+"行‘办税人手机号码’出错，手机号码须为数字，请检查。");
					}
				}
				try {
					SqlRowSet r = pm.quereyForRowSet("SELECT LSH FROM NSR_JBXX WHERE NSRBM = '"+nsrjbxx.getNSRBM()+"' AND ORG_DM='"+ORG_DM+"'");
					if(r.next()) {
						String LSH = r.getString("LSH");
						pm.executeUpdate("UPDATE NSR_JBXX SET NSRMC=?,HYDM=?,SSS=?,"+sb1.toString()+"XG_SJ=?,XGRY_DM=? WHERE LSH = '"+LSH+"'", nsrjbxx);
					} else {
						pm.executeUpdate("INSERT INTO NSR_JBXX(LSH,NSRBM,NSRMC,HYDM,SSS,ORG_DM,SSZGY,LRRY_DM,SBFS,XGRY_DM,DJZT,LR_SJ,DJLX,XG_SJ,ZCDZ,ZCLX,FR,CWJL,"+sb2.toString()+"BSRY) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"+sb3.toString()+"?)", nsrjbxx);
					}
					w++;
				} catch (Exception e) {
					if(e instanceof APPException) {
						throw (APPException)e;
					} else {
						e.printStackTrace();
						throw new APPException("导入文件时，第"+(n+1)+"行出错，请检查。");
					}
				}
			}
		}
		this.putData("message", "导入纳税人成功（"+w+"条）。");
	}

}
