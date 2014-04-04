package com.gwinsoft.components.nsrsms.cbimport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class CbimportALO extends BaseALO {

	protected void doService() {
		User user  = this.getData("user");
		String title  = this.getData("title");
		Workbook book = this.getData("book");
		Sheet[] sheets = book.getSheets();
		String uid = GwinSoftUtil.getLSH();
		Date time = GwinSoftUtil.getSysDate();
		DBPersistenceManager pm = this.getPM();
		for(int i = 0; i < sheets.length; i ++) {
			Sheet sheet = sheets[i];
			int rows = sheet.getRows();
			if(rows<1) {
				this.putData("message", "文件中无内容，不允许导入。");
				return;
			}
			{
				Cell cell0 = sheet.getCell(0, 0);
				Cell cell1 = sheet.getCell(1, 0);
				Cell cell2 = sheet.getCell(2, 0);
				Cell cell3 = sheet.getCell(3, 0);
				Cell cell4 = sheet.getCell(4, 0);
				Cell cell5 = sheet.getCell(5, 0);
				Cell cell6 = sheet.getCell(6, 0);
				Cell cell7 = sheet.getCell(7, 0);
				Cell cell8 = sheet.getCell(8, 0);
				Cell cell9 = sheet.getCell(9, 0);
				//判断格式
				String c0 = cell0.getContents();
				String c1 = cell1.getContents();
				String c2 = cell2.getContents();
				String c3 = cell3.getContents();
				String c4 = cell4.getContents();
				String c5 = cell5.getContents();
				String c6 = cell6.getContents();
				String c7 = cell7.getContents();
				String c8 = cell8.getContents();
				String c9 = cell9.getContents();
				if(c0==null || !"纳税人编码".equals(c0.trim())|| c1==null || !"纳税人名称".equals(c1.trim())|| c2==null || !"手机号码".equals(c2.trim())|| c3==null || !"催报日期起".equals(c3.trim())|| c4==null || !"催报日期止".equals(c4.trim())|| c5==null || !"征收项目".equals(c5.trim())|| c6==null || !"征收品目".equals(c6.trim())|| c7==null || !"税款所属期起".equals(c7.trim())|| c8==null || !"税款所属期止".equals(c8.trim())|| c9==null || !"金额".equals(c9.trim())) {
					if(i==0) {
						this.putData("message", "文件内容样式非法，请参考导出的模板样式。");
					}
					return;
				}
			}
			//保存
			String sql = "INSERT INTO SMS_NSRDATAMX(NSRDATAMX_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,ZSXM,ZSPM,SKSSQ_Q,SKSSQ_Z,JE,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			for(int n = 1; n < rows; n ++) {
				String NSRBM = sheet.getCell(0, n).getContents();
				String NSRMC = sheet.getCell(1, n).getContents();
				String SJHM = sheet.getCell(2, n).getContents();
				String CBRQQ = sheet.getCell(3, n).getContents();
				String CBRQZ = sheet.getCell(4, n).getContents();
				String ZSXM = sheet.getCell(5, n).getContents();
				String ZSPM = sheet.getCell(6, n).getContents();
				String SKSSQ_Q = sheet.getCell(7, n).getContents();
				String SKSSQ_Z = sheet.getCell(8, n).getContents();
				String JE = sheet.getCell(9, n).getContents();
				NSRDATAMX mx = new NSRDATAMX();
				mx.setNSRDATAMX_LSH(GwinSoftUtil.getLSH());
				mx.setNSRDATA_LSH(uid);
				mx.setNSRBM(NSRBM);
				mx.setNSRMC(NSRMC);
				mx.setSJHM(SJHM);
				mx.setCBRQQ(getFormatDate(CBRQQ,"yyyy-MM-dd"));
				mx.setCBRQZ(getFormatDate(CBRQZ,"yyyy-MM-dd"));
				mx.setZSXM(ZSXM);
				mx.setZSPM(ZSPM);
				mx.setSKSSQ_Q(getFormatDate(SKSSQ_Q,"yyyy-MM-dd"));
				mx.setSKSSQ_Z(getFormatDate(SKSSQ_Z,"yyyy-MM-dd"));
				mx.setJE(JE);
				mx.setLRRY_DM(user.getUSER_DM());
				mx.setXGRY_DM(user.getUSER_DM());
				mx.setLR_SJ(time);
				mx.setXG_SJ(time);
				//手机号码
				if(SJHM==null || SJHM.length()!=11) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘手机号码’出错，手机号码须为11位数字，请检查。");
				} else {
					try {
						Long.parseLong(SJHM);
					} catch (NumberFormatException e) {
						throw new APPException("导入文件时，第"+(n+1)+"行‘手机号码’出错，手机号码须为11位数字，请检查。");
					}
				}
				try {
					pm.executeUpdate(sql, mx);
				} catch (Exception e) {
					if(e instanceof APPException) {
						throw (APPException)e;
					} else {
						throw new APPException("导入文件时，第"+(n+1)+"行出错，请检查。");
					}
				}
			}
		}
		String sql = "INSERT INTO SMS_NSRDATA( NSRDATA_LSH,SMSTYPE_DM,GROUP_NAME,SMSZT_DM,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ORG_DM_JG) VALUES (?,?,?,?,?,?,?,?,'"+user.getORG_DM_JG()+"')";
		Cbimport cb = new Cbimport();
		cb.setNSRDATA_LSH(uid);
		cb.setSMSTYPE_DM("01");
		cb.setGROUP_NAME(title);
		cb.setSMSZT_DM("01");
		cb.setLRRY_DM(user.getUSER_DM());
		cb.setXGRY_DM(user.getUSER_DM());
		cb.setLR_SJ(time);
		cb.setXG_SJ(time);
		pm.executeUpdate(sql, cb);
		this.putData("message", "导入短信组["+title+"]成功。");
	}

	private static Date getFormatDate(String date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}
}
