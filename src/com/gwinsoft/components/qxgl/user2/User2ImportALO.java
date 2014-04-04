package com.gwinsoft.components.qxgl.user2;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.gwinsoft.components.common.CommonTool;
import com.gwinsoft.components.qxgl.user.User;
import com.gwinsoft.framework.alo.BaseALO;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.util.GwinSoftUtil;

public class User2ImportALO extends BaseALO {

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
				continue;
			}
			{
				Cell cell0 = sheet.getCell(0, 0);
				Cell cell1 = sheet.getCell(1, 0);
				Cell cell2 = sheet.getCell(2, 0);
				Cell cell3 = sheet.getCell(3, 0);
				Cell cell4 = sheet.getCell(4, 0);
				Cell cell5 = sheet.getCell(5, 0);
				//判断格式
				String c0 = cell0.getContents();
				String c1 = cell1.getContents();
				String c2 = cell2.getContents();
				String c3 = cell3.getContents();
				String c4 = cell4.getContents();
				String c5 = cell5.getContents();

				if (c0 == null || !"用户名称".equals(c0.trim()) 
					|| c1 == null || !"机关".equals(c1.trim()) 
					|| c2 == null || !"部门".equals(c2.trim()) 
					|| c3 == null || !"职务".equals(c3.trim()) 
					|| c4 == null || !"性别".equals(c4.trim()) 
					|| c5 == null || !"手机号码".equals(c5.trim()) 
					) {
						if (i == 0) {
							this.putData("message", "文件内容样式非法，请参考导出的模板样式。");
						}
						return;
				}
			}
			Map<String, Map<String, Object>> orgcache = CacheManager.getCache("ORG_CACHE");
			Map<String, Map<String, Object>> zwcache = CacheManager.getCache("ZW_CACHE");
			//保存
			String sql = "INSERT INTO QX_USER(USER_DM,USER_MC,PASSWORD,XB_DM,ORG_DM_JG,ORG_DM_BM,SFZHM,TEL,JG,YZBM,EMAIL,ADDR,BZ,YX_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ZW_DM,JGPX,CZY_BJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			for(int n = 1; n < rows; n ++) {
				//用户编码	用户名称   机关	部门	 职务	性别	  身份证号	手机号码	 籍贯	邮政编码	 EMAIL	地址	  机构排序	备注
				User userdata = new User();
				String LSH = CommonTool.getLSH();
				userdata.setUSER_DM(LSH);//
				userdata.setUSER_MC(sheet.getCell(0, n).getContents());//
				userdata.setORG_DM_JG(sheet.getCell(1, n).getContents());//
				userdata.setORG_DM_BM(sheet.getCell(2, n).getContents());//
				userdata.setZW_DM(sheet.getCell(3, n).getContents());
				userdata.setXB_DM(sheet.getCell(4, n).getContents());
				userdata.setTEL(sheet.getCell(5, n).getContents());//

				userdata.setYX_BJ("1");
				userdata.setLR_SJ(time);
				userdata.setXG_SJ(time);
				userdata.setLRRY_DM(user.getUSER_DM());
				userdata.setXGRY_DM(user.getUSER_DM());
				userdata.setCZY_BJ("0");
				//用户名称
				if(userdata.getUSER_MC()==null || "".equals(userdata.getUSER_MC().trim())) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘用户名称’出错，不允许为空，请检查。");
				}
				//机关
				if(userdata.getORG_DM_JG()==null || "".equals(userdata.getORG_DM_JG().trim())) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘机关’出错，不允许为空，请检查。");
				}
				String swjgmc = userdata.getORG_DM_JG();
				boolean isHaveJG = false;
				Iterator<String> it = orgcache.keySet().iterator();
				ORGJG:while(it.hasNext()) {
					String key = it.next();
					Map<String, Object> data = orgcache.get(key);
					if(userdata.getORG_DM_JG().equals(data.get("ORG_MC"))) {
						userdata.setORG_DM_JG((String) data.get("ORG_DM"));
						isHaveJG = true;
						break ORGJG;
					}
				}
				if(!isHaveJG) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘机关’出错，不存在‘"+swjgmc+"’，请检查。");
				}
				//部门
				if(userdata.getORG_DM_BM()==null || "".equals(userdata.getORG_DM_BM().trim())) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘部门’出错，不允许为空，请检查。");
				}
				boolean isHaveBM = false;
				if(swjgmc.equals(userdata.getORG_DM_BM())) {
					userdata.setORG_DM_BM(userdata.getORG_DM_JG());
					isHaveBM = true;
				} else {
					it = orgcache.keySet().iterator();
					ORGBM:while(it.hasNext()) {
						String key = it.next();
						Map<String, Object> data = orgcache.get(key);
						if(userdata.getORG_DM_BM().equals(data.get("ORG_MC"))) {
							String sj = (String) data.get("SJ_ORG_DM");
							if(userdata.getORG_DM_JG().equals(sj)) {
								userdata.setORG_DM_BM((String) data.get("ORG_DM"));
								isHaveBM = true;
								break ORGBM;
							}
						}
					}
				}
				if(!isHaveBM) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘部门’出错，机关‘"+swjgmc+"’中不存在部门‘"+userdata.getORG_DM_BM()+"’，请检查。");
					//userdata.setORG_DM_BM(userdata.getORG_DM_JG());
				}
				//手机号码
				if(userdata.getTEL()==null || userdata.getTEL().length()!=11) {
					throw new APPException("导入文件时，第"+(n+1)+"行‘手机号码’出错，手机号码须为11位数字，请检查。");
				} else {
					try {
						Long.parseLong(userdata.getTEL());
					} catch (NumberFormatException e) {
						throw new APPException("导入文件时，第"+(n+1)+"行‘手机号码’出错，手机号码须为11位数字，请检查。");
					}
				}
				
				//职务
				it = zwcache.keySet().iterator();
				ZW:while(it.hasNext()) {
					String key = it.next();
					Map<String, Object> data = zwcache.get(key);
					if(data.get("ZW_MC").equals(userdata.getZW_DM())) {
						userdata.setZW_DM((String) data.get("ZW_DM"));
						break ZW;
					}
				}
				//性别
				if("女".equals(userdata.getXB_DM())) {
					userdata.setXB_DM("2");
				} else {
					userdata.setXB_DM("1");
				}
				try {
					//以手机号和姓名做判断是否存在
					String selectSQL = "SELECT 1 FROM QX_USER WHERE USER_MC='"+userdata.getUSER_MC()+"' AND TEL='"+userdata.getTEL()+"'";
					SqlRowSet rowset = pm.quereyForRowSet(selectSQL);
					if(rowset.next()) {
						//存在的修改
						//INSERT INTO QX_USER(USER_DM,USER_MC,PASSWORD,XB_DM,ORG_DM_JG,ORG_DM_BM,SFZHM,TEL,JG,YZBM,EMAIL,ADDR,BZ,YX_BJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,ZW_DM,JGPX,CZY_BJ) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
						String update = "UPDATE QX_USER SET ORG_DM_JG='"+userdata.getORG_DM_JG()+"',ORG_DM_BM='"+userdata.getORG_DM_BM()+"',XB_DM='"+userdata.getXB_DM()+"',ZW_DM='"+userdata.getZW_DM()+"' WHERE USER_MC='"+userdata.getUSER_MC()+"' AND TEL='"+userdata.getTEL()+"'";
						pm.executeUpdate(update);
					} else {
						//不存在的新增
						pm.executeUpdate(sql, userdata);
					}
				} catch (Exception e) {
					if(e instanceof APPException) {
						throw (APPException)e;
					} else {
						throw new APPException("导入文件时，第"+(n+1)+"行出错，请检查。");
					}
				}
			}
		}
		this.putData("message", "导入内部人员成功。");
	}
}