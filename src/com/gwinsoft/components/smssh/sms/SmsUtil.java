package com.gwinsoft.components.smssh.sms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components.nsrsms.group.NSRDATAMX;
import com.gwinsoft.components.nsrsms.smssend.Nsrmsg;
import com.gwinsoft.framework.cache.gwincache.CacheManager;
import com.gwinsoft.util.GwinSoftUtil;

public class SmsUtil {
	public static void main(String[] args) {
		String s = "${纳税人名称}";
		s = replace(s,"${纳税人名称}", "a");
		System.out.println(s);
	}

	public static String replace(String strSource, String strFrom, String strTo) {
		if (strTo == null) {
			strTo = "";
		}
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}
	public static void genSMS(Nsrmsg nsrmsg, List<NSRDATAMX> datamx, String dxmb, String ORG_SWJG_JG, String swjgmc) {
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		dxmb = replace(dxmb,"${纳税人编码}", nsrmsg.getNSRBM());
		dxmb = replace(dxmb,"${纳税人名称}", nsrmsg.getNSRMC());
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${税款合计金额}", nsrmsg.getJE());
		int start = dxmb.indexOf("#{循环：征收品目（开始）}");
		int end = dxmb.indexOf("#{循环：征收品目（结束）}");
		if(start!=-1 && end>-1) {
			StringBuffer sb = new StringBuffer();
			int length = 0;
			int a1 = -1, b1 = -1, a2 = -1, b2 = -1;//if have or not next
			String s1 = "";//if have next
			String s2 = "";//if not have next
			String row = dxmb.substring(start + 14, end);
			if(row.indexOf("#{循环内部判断：如果有下一个品目（开始）}")!=-1) {
				a1 = row.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
				b1 = row.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
				s1 = row.substring(a1+22, b1);
				row = row.substring(0, a1)+row.substring(b1+22);//抠出if表达式
				for(NSRDATAMX mx: datamx) {//取出税种个数
					if (mx.getNSRBM().equals(nsrmsg.getNSRBM())
							&& mx.getNSRMC().equals(nsrmsg.getNSRMC())
							&& mx.getSJHM().equals(nsrmsg.getSJHM())
							&& mx.getCBRQQ().equals(nsrmsg.getCBRQQ())
							&& mx.getCBRQZ().equals(nsrmsg.getCBRQZ())) {
						length ++;
					}
				}
			}
			if(row.indexOf("#{循环内部判断：如果无下一个品目（开始）}")!=-1) {
				a2 = row.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
				b2 = row.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
				s2 = row.substring(a2+22, b2);
				row = row.substring(0, a2)+row.substring(b2+22);//抠出if表达式
				if(length==0) {//取出税种个数
					for(NSRDATAMX mx: datamx) {
						if (mx.getNSRBM().equals(nsrmsg.getNSRBM())
								&& mx.getNSRMC().equals(nsrmsg.getNSRMC())
								&& mx.getSJHM().equals(nsrmsg.getSJHM())
								&& mx.getCBRQQ().equals(nsrmsg.getCBRQQ())
								&& mx.getCBRQZ().equals(nsrmsg.getCBRQZ())) {
							length ++;
						}
					}
				}
			}
			int currow = 0;
			for(NSRDATAMX mx: datamx) {
				if (mx.getNSRBM().equals(nsrmsg.getNSRBM())
						&& mx.getNSRMC().equals(nsrmsg.getNSRMC())
						&& mx.getSJHM().equals(nsrmsg.getSJHM())
						&& mx.getCBRQQ().equals(nsrmsg.getCBRQQ())
						&& mx.getCBRQZ().equals(nsrmsg.getCBRQZ())) {
					currow++;
					String row1 = row;
					if(a1!=-1 && currow!=length) {
						s1 = replace(s1,"${征收项目}", mx.getZSXM());
						s1 = replace(s1,"${征收品目}", mx.getZSPM());
						s1 = replace(s1,"${征收品目金额}", mx.getJE());
						s1 = replace(s1,"${征收品目税款所属期起}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Q(), "yyyy-MM-dd"));
						s1 = replace(s1,"${征收品目税款所属期止}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Z(), "yyyy-MM-dd"));
						row1 = row1.substring(0, a1) + s1 + row1.substring(a1);
					}
					if(a2!=-1 && currow==length) {
						s2 = replace(s2,"${征收项目}", mx.getZSXM());
						s2 = replace(s2,"${征收品目}", mx.getZSPM());
						s2 = replace(s2,"${征收品目金额}", mx.getJE());
						s2 = replace(s2,"${征收品目税款所属期起}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Q(), "yyyy-MM-dd"));
						s2 = replace(s2,"${征收品目税款所属期止}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Z(), "yyyy-MM-dd"));
						row1 = row1.substring(0, a2) + s2 + row1.substring(a2);
					}
					row1 = replace(row1,"${征收项目}", mx.getZSXM());
					row1 = replace(row1,"${征收品目}", mx.getZSPM());
					row1 = replace(row1,"${征收品目金额}", mx.getJE());
					row1 = replace(row1,"${征收品目税款所属期起}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Q(), "yyyy-MM-dd"));
					row1 = replace(row1,"${征收品目税款所属期止}", GwinSoftUtil.getFormatDate(mx.getSKSSQ_Z(), "yyyy-MM-dd"));
					sb.append(row1);
				}
			}
			nsrmsg.setMSG(dxmb.substring(0, start)+sb.toString()+dxmb.substring(end+14));
		} else {
			nsrmsg.setMSG(dxmb);
		}
		String qm = null;
		Map<String, Map<String, Object>> cache = CacheManager.getCache("DXQM_CACHE");
		Iterator<String> it = cache.keySet().iterator();
		while(it.hasNext()) {
			String key = it.next();
			if(key.equals(ORG_SWJG_JG)) {
				qm = (String) cache.get(key).get("DXQM_MC");
			}
		}
		if(qm == null) {
			qm = ("【"+swjgmc+"】");
		}
		nsrmsg.setMSG(nsrmsg.getMSG()+qm);
	}
	public static String validate(String str) {
		String msg = null;
		int[] index = new int[6];
		String[] exps = new String[] { "#{循环：征收品目（开始）}", "#{循环：征收品目（结束）}",
				"#{循环内部判断：如果有下一个品目（开始）}", "#{循环内部判断：如果有下一个品目（结束）}",
				"#{循环内部判断：如果无下一个品目（开始）}", "#{循环内部判断：如果无下一个品目（结束）}" };
		for(int i = 0; i < index.length; i ++) {
			index[i] = str.indexOf(exps[i]);
			if(index[i]>-1) {
				if(str.substring(index[i]+2).indexOf(exps[i])>-1) {
					msg = "同一个表达式不允许出现多次。";
					return msg;
				}
			}
		}
		if(index[0]+index[1]>-2) {
			if(index[0]>=index[1]) {
				msg = "#{循环：征收品目（开始）}应出现在#{循环：征收品目（结束）}之前";
				return msg;
			}
			if(index[0]==-1 || index[1]==-1) {
				msg = "#{循环：征收品目（开始）}应与#{循环：征收品目（结束）}配对出现";
				return msg;
			}
		}
		if(index[2]+index[3]>-2) {
			if(index[2]>=index[3]) {
				msg = "#{循环内部判断：如果有下一个品目（开始）}应出现在#{循环内部判断：如果有下一个品目（结束）}之前";
				return msg;
			}
			if(index[2]==-1 || index[3]==-1) {
				msg = "#{循环内部判断：如果有下一个品目（开始）}应与#{循环内部判断：如果有下一个品目（结束）}配对出现";
				return msg;
			}
		}
		if(index[4]+index[5]>-2) {
			if(index[4]>=index[5]) {
				msg = "#{循环内部判断：如果无下一个品目（开始）}应出现在#{循环内部判断：如果无下一个品目（结束）}之前";
				return msg;
			}
			if(index[4]==-1 || index[5]==-1) {
				msg = "#{循环内部判断：如果无下一个品目（开始）}应与#{循环内部判断：如果无下一个品目（结束）}配对出现";
				return msg;
			}
		}
		if((index[2]>index[4] && index[2]<index[5]) || (index[2]<index[4] && index[3]>index[4])) {
			msg = "循环内部判断表达式不能嵌套。";
		}
		int start = index[0];
		int end = index[1];
		Arrays.sort(index);
//		if(start!=index[0] || end!=index[5]) {
//			msg = "循环内部判断表达式应在循环表达式的内部。";
//		}
		boolean isStart = false;
		boolean isEnd = false;
		for(int i : index) {
			if(i>-1) {
				if(!isStart && start!=i) {
					msg = "'品目'循环内部判断表达式应在循环表达式的内部。";
					return msg;
				} else {
					isStart = true;
				}
				if(isEnd) {
					msg = "'品目'循环内部判断表达式应在循环表达式的内部。";
					return msg;
				}
				if(!isEnd && end==i) {
					isEnd = true;
				}
			}
		}
		return msg;
	}

}
