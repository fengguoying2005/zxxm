package com.gwinsoft.components2.dxmb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.gwinsoft.components.dmb.DMB;
import com.gwinsoft.components2.job.JobUtils;
import com.gwinsoft.components2.job.beans.GMFPTX_BEAN;
import com.gwinsoft.components2.job.beans.JKTX_BEAN;
import com.gwinsoft.components2.job.beans.KKSBTX_BEAN;
import com.gwinsoft.components2.job.beans.SFCBTX_BEAN;
import com.gwinsoft.components2.job.beans.SFCJTX_BEAN;
import com.gwinsoft.components2.job.beans.TYDQTX_BEAN;
import com.gwinsoft.components2.job.beans.plian.FPXX_BEAN;
import com.gwinsoft.components2.job.beans.plian.FPZJXX_BEAN;
import com.gwinsoft.components2.job.beans.plian.SFXX_BEAN;
import com.gwinsoft.util.GwinSoftUtil;

public class DxmbUtil {
	private static final String DXQM = "[辽宁地税]";
	public static void main(String[] args) {
		String dxmb = "${纳税人名称}(${纳税人编码})税款所属期(${税款所属期起}-${税款所属期止}),税款合计金额${税款合计金额},以下税款尚未缴款：#{循环：征收项目（开始）}${征收项目},${征收项目金额}（${税票号码}）#{循环内部判断：如果有下一个项目（开始）}；#{循环内部判断：如果有下一个项目（结束）}#{循环内部判断：如果无下一个项目（开始）}。#{循环内部判断：如果无下一个项目（结束）}#{循环：征收项目（结束）}切已过了规定的缴款期限，请尽快缴纳。";
		KKSBTX_BEAN beans = new KKSBTX_BEAN();
		beans.setLSH("LSH");
		beans.setXTSPHM("XTSPHM");
		beans.setJKLX_DM("JKLX_DM");
		beans.setNSRSBM("NSRSBM");
		beans.setJK_RQ(new Date());
		beans.setSKSSQ(new Date());
		beans.setSKSSZ(new Date());
		beans.setSF_JE(1000.00);
		beans.setCNT(4);
		beans.setZSXM_DMS("15,15,16,17");//乱的话处理
		beans.setZSPM_DMS("1511,1512,1611,1711");
		beans.setSF_JES("200.00,300,400,100.00");
		beans.setNSRMC("NSRMC");
		beans.setSWJGBM("SWJGBM");
		beans.setSJHM("SJHM");
		beans.setSJLX("SJLX");
		beans.setSMSINFO("SMSINFO");
		beans.setLR_SJ(new Date());
		beans.setBZ("BZ");
		beans.setFSZT_DM("FSZT_DM");
		beans.setFS_SJ(new Date());
		beans.setFRSJH("FRSJH");
		beans.setCWJLSJH("CWJLSJH");
		beans.setBSRYSJH("BSRYSJH");
		/*List<SFXX_BEAN> beans = new ArrayList<SFXX_BEAN>();
		for(int i = 1 ; i < 4; i ++) {
			for(int j = 1; j < 5; j ++) {
				SFXX_BEAN bean = new SFXX_BEAN();
				bean.setBSRYSJH("BSRYSJH");
				bean.setCWJLSJH("CWJLSJH");
				bean.setFRSJH("FRSJH");
				bean.setNSRBM("NSRBM");
				bean.setNSRMC("张三丰");
				bean.setORG_DM("ORG_DM");
				bean.setSF_JE(1+i);
				bean.setSKSSQ(JobUtils.getFormatDate("2013-01-01", "yyyy-MM-dd"));
				bean.setSKSSZ(JobUtils.getFormatDate("2013-01-31", "yyyy-MM-dd"));
				bean.setZSXM_DM("ZSXM_DM"+i);
				bean.setZSPM_DM("ZSPM_DM"+i+"_"+j);
				beans.add(bean);
			}
		}*/
		String msg = genSMS(beans, dxmb);
		System.out.println(msg);
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
	public static String validateDx(Dxmb2 dxmb) {
		String str = dxmb.getINFO();
		//验证项目
		String msg = validateXm(str);
		
		if(msg!=null) {
			return msg;
		}
		int[] index2 = new int[6];
		String[] exps2 = new String[] { "#{循环：征收项目（开始）}", "#{循环：征收项目（结束）}",
				"#{循环内部判断：如果有下一个项目（开始）}", "#{循环内部判断：如果有下一个项目（结束）}",
				"#{循环内部判断：如果无下一个项目（开始）}", "#{循环内部判断：如果无下一个项目（结束）}" };
		for(int i = 0; i < index2.length; i ++) {
			index2[i] = str.indexOf(exps2[i]);
		}
		
		int[] index = new int[6];
		String[] exps = new String[] { "#{循环：征收品目（开始）}", "#{循环：征收品目（结束）}",
				"#{循环内部判断：如果有下一个品目（开始）}", "#{循环内部判断：如果有下一个品目（结束）}",
				"#{循环内部判断：如果无下一个品目（开始）}", "#{循环内部判断：如果无下一个品目（结束）}" };
		for(int i = 0; i < index.length; i ++) {
			index[i] = str.indexOf(exps[i]);
		}
		//征收项目和征收品目循环不能同时存在
		int sumxm = 0;
		for(int xmi : index2) {
			sumxm += xmi;
		}
		int sumpm = 0;
		for(int pmi : index) {
			sumpm += pmi;
		}
		if(sumxm!=-6 && sumpm!=-6) {
			msg = "征收项目和征收品目循环不能同时存在";
			return msg;
		}
		//验证品目
		msg = validatePm(str);
		if(msg!=null) {
			return msg;
		}
		//验证类型 
		//不应该有的变量 
		if("SFCBTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${征收项目金额}","${税款合计金额}","${扣款类型}","${缴款日期}","${中奖年月}","${开奖内容}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","${市级缩写}","${税票号码}","#{循环：征收品目（开始）}","#{循环内部判断：如果有下一个品目（开始）}","#{循环内部判断：如果有下一个品目（结束）}","#{循环内部判断：如果无下一个品目（开始）}","#{循环内部判断：如果无下一个品目（结束）}","#{循环：征收品目（结束）}","${征收品目}","${征收品目金额}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "催报提醒模板不能包含变量："+exclude;
				}
			}
		} else if("SFCJTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${扣款类型}","${申报日期}","${中奖年月}","${开奖内容}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","${市级缩写}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "催缴提醒模板不能包含变量："+exclude;
				}
			}
		} else if("FPZWCX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${扣款类型}","#{循环：征收品目（开始）}","#{循环内部判断：如果有下一个品目（开始）}","#{循环内部判断：如果有下一个品目（结束）}","#{循环内部判断：如果无下一个品目（开始）}","#{循环内部判断：如果无下一个品目（结束）}","#{循环：征收品目（结束）}","${征收品目}","${征收品目金额}","${申报日期}","${缴款日期}","${中奖年月}","${开奖内容}","${税款合计金额}","${征收项目}","${税款所属期起}","${税款所属期止}","${停业日期}","#{循环：征收项目（开始）}","#{循环内部判断：如果有下一个项目（开始）}","#{循环内部判断：如果有下一个项目（结束）}","#{循环内部判断：如果无下一个项目（开始）}","#{循环内部判断：如果无下一个项目（结束）}","#{循环：征收项目（结束）}","${征收项目金额}","${税票号码}","${市级缩写}","${发票号码起}","${发票号码止}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "发票真伪查询模板不能包含变量："+exclude;
				}
			}
		} else if("FPZJCX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${扣款类型}","#{循环：征收品目（开始）}","#{循环内部判断：如果有下一个品目（开始）}","#{循环内部判断：如果有下一个品目（结束）}","#{循环内部判断：如果无下一个品目（开始）}","#{循环内部判断：如果无下一个品目（结束）}","#{循环：征收品目（结束）}","${征收品目}","${征收品目金额}","${申报日期}","${缴款日期}","${纳税人编码}","${纳税人名称}","${税款合计金额}","${征收项目}","${税款所属期起}","${税款所属期止}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","#{循环：征收项目（开始）}","#{循环内部判断：如果有下一个项目（开始）}","#{循环内部判断：如果有下一个项目（结束）}","#{循环内部判断：如果无下一个项目（开始）}","#{循环内部判断：如果无下一个项目（结束）}","#{循环：征收项目（结束）}","${征收项目金额}","${税票号码}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "发票中奖查询模板不能包含变量："+exclude;
				}
			}
		} else if("GMFPTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${扣款类型}","#{循环：征收品目（开始）}","#{循环内部判断：如果有下一个品目（开始）}","#{循环内部判断：如果有下一个品目（结束）}","#{循环内部判断：如果无下一个品目（开始）}","#{循环内部判断：如果无下一个品目（结束）}","#{循环：征收品目（结束）}","${征收品目}","${征收品目金额}","${申报日期}","${缴款日期}","${中奖年月}","${开奖内容}","${税款合计金额}","${征收项目}","${税款所属期起}","${税款所属期止}","${停业日期}","#{循环：征收项目（开始）}","#{循环内部判断：如果有下一个项目（开始）}","#{循环内部判断：如果有下一个项目（结束）}","#{循环内部判断：如果无下一个项目（开始）}","#{循环内部判断：如果无下一个项目（结束）}","#{循环：征收项目（结束）}","${征收项目金额}","${税票号码}","${市级缩写}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "购买发票提醒模板不能包含变量："+exclude;
				}
			}
		} else if("JKTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${申报日期}","${中奖年月}","${开奖内容}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","${市级缩写}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "缴款提醒模板不能包含变量："+exclude;
				}
			}
		} else if("KKSBTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${申报日期}","${中奖年月}","${开奖内容}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","${市级缩写}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "缴款失败提醒模板不能包含变量："+exclude;
				}
			}
		} else if("SFCX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"${扣款类型}","${申报日期}","${中奖年月}","${开奖内容}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${停业日期}","${发票日期}","${市级缩写}","${税票号码}","${缴款日期}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "税费查询模板不能包含变量："+exclude;
				}
			}
		} else if("TYDQTX".equals(dxmb.getDXLX_DM())) {
			String[] excludes = new String[]{"#{循环：征收品目（开始）}","#{循环内部判断：如果有下一个品目（开始）}","#{循环内部判断：如果有下一个品目（结束）}","#{循环内部判断：如果无下一个品目（开始）}","#{循环内部判断：如果无下一个品目（结束）}","#{循环：征收品目（结束）}","${征收品目}","${征收品目金额}","${申报日期}","${缴款日期}","${中奖年月}","${开奖内容}","${税款合计金额}","${征收项目}","${税款所属期起}","${税款所属期止}","${发票种类}","${发票代码}","${发票号码}","${发票号码起}","${发票号码止}","${发票金额}","${发票日期}","#{循环：征收项目（开始）}","#{循环内部判断：如果有下一个项目（开始）}","#{循环内部判断：如果有下一个项目（结束）}","#{循环内部判断：如果无下一个项目（开始）}","#{循环内部判断：如果无下一个项目（结束）}","#{循环：征收项目（结束）}","${征收项目金额}","${税票号码}","${市级缩写}"};
			for(String exclude:excludes) {
				if(str.indexOf(exclude)!=-1) {
					return "停业到期提醒模板不能包含变量："+exclude;
				}
			}
		}
		return msg;
	}
	public static String validatePm(String str) {
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
			msg = "'品目'循环内部判断表达式不能嵌套。";
		}
		int start = index[0];
		int end = index[1];
		Arrays.sort(index);
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
//		if(start!=index[0] || end!=index[5]) {
//			msg = "'品目'循环内部判断表达式应在循环表达式的内部。";
//		}
		return msg;
	}
	public static String validateXm(String str) {
		String msg = null;
		int[] index = new int[6];
		String[] exps = new String[] { "#{循环：征收项目（开始）}", "#{循环：征收项目（结束）}",
				"#{循环内部判断：如果有下一个项目（开始）}", "#{循环内部判断：如果有下一个项目（结束）}",
				"#{循环内部判断：如果无下一个项目（开始）}", "#{循环内部判断：如果无下一个项目（结束）}" };
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
				msg = "#{循环：征收项目（开始）}应出现在#{循环：征收项目（结束）}之前";
				return msg;
			}
			if(index[0]==-1 || index[1]==-1) {
				msg = "#{循环：征收项目（开始）}应与#{循环：征收项目（结束）}配对出现";
				return msg;
			}
		}
		if(index[2]+index[3]>-2) {
			if(index[2]>=index[3]) {
				msg = "#{循环内部判断：如果有下一个项目（开始）}应出现在#{循环内部判断：如果有下一个项目（结束）}之前";
				return msg;
			}
			if(index[2]==-1 || index[3]==-1) {
				msg = "#{循环内部判断：如果有下一个项目（开始）}应与#{循环内部判断：如果有下一个项目（结束）}配对出现";
				return msg;
			}
		}
		if(index[4]+index[5]>-2) {
			if(index[4]>=index[5]) {
				msg = "#{循环内部判断：如果无下一个项目（开始）}应出现在#{循环内部判断：如果无下一个项目（结束）}之前";
				return msg;
			}
			if(index[4]==-1 || index[5]==-1) {
				msg = "#{循环内部判断：如果无下一个项目（开始）}应与#{循环内部判断：如果无下一个项目（结束）}配对出现";
				return msg;
			}
		}
		if((index[2]>index[4] && index[2]<index[5]) || (index[2]<index[4] && index[3]>index[4])) {
			msg = "'项目'循环内部判断表达式不能嵌套。";
		}
		int start = index[0];
		int end = index[1];
		Arrays.sort(index);
//		if(start!=index[0] || end!=index[5]) {
//			msg = "'项目'循环内部判断表达式应在循环表达式的内部。";
//		}
		boolean isStart = false;
		boolean isEnd = false;
		for(int i : index) {
			if(i>-1) {
				if(!isStart && start!=i) {
					msg = "'项目'循环内部判断表达式应在循环表达式的内部。";
					return msg;
				} else {
					isStart = true;
				}
				if(isEnd) {
					msg = "'项目'循环内部判断表达式应在循环表达式的内部。";
					return msg;
				}
				if(!isEnd && end==i) {
					isEnd = true;
				}
			}
		}
		return msg;
	}
	
	//自动发送
	public static String genSMS(GMFPTX_BEAN bean, String dxmb) {
		//购买发票提醒 
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${发票种类}", bean.getFPZL());
		dxmb = replace(dxmb,"${发票代码}", bean.getFPDM());
		dxmb = replace(dxmb,"${发票号码}", bean.getFPHM());
		dxmb = replace(dxmb,"${发票号码起}", bean.getFPQH());
		dxmb = replace(dxmb,"${发票号码止}", bean.getFPZH());
		dxmb = replace(dxmb,"${发票金额}", bean.getFP_JE());
		dxmb = replace(dxmb,"${发票日期}", JobUtils.getFormatDate(bean.getFP_RQ(), "yyyy-MM-dd HH:mm:ss"));
		dxmb+=DXQM;
		return dxmb;
	}

	public static String genSMS(JKTX_BEAN bean, String dxmb) {
		List<JKTX_BEAN> beans = new ArrayList<JKTX_BEAN>();
		String[] XMS = bean.getZSXM_DMS().split(",");
		String[] PMS = bean.getZSPM_DMS().split(",");
		String[] JES = bean.getSF_JES().split(",");
		int count = bean.getCNT();
		for(int i = 0 ; i < count; i ++) {
			JKTX_BEAN _bean = new JKTX_BEAN();
			_bean.setLSH(bean.getLSH());
			_bean.setXTSPHM(bean.getXTSPHM());
			_bean.setJKLX_DM(bean.getJKLX_DM());
			_bean.setNSRSBM(bean.getNSRSBM());
			_bean.setJK_RQ(bean.getJK_RQ());
			_bean.setSKSSQ(bean.getSKSSQ());
			_bean.setSKSSZ(bean.getSKSSZ());
			_bean.setSF_JE(bean.getSF_JE());
			_bean.setCNT(bean.getCNT());
			_bean.setZSXM_DMS(XMS[i]);
			_bean.setZSPM_DMS(PMS[i]);
			_bean.setSF_JES(JES[i]);
			_bean.setNSRMC(bean.getNSRMC());
			_bean.setSWJGBM(bean.getSWJGBM());
			_bean.setSJHM(bean.getSJHM());
			_bean.setSJLX(bean.getSJLX());
			_bean.setSMSINFO(bean.getSMSINFO());
			_bean.setLR_SJ(bean.getLR_SJ());
			_bean.setBZ(bean.getBZ());
			_bean.setFSZT_DM(bean.getFSZT_DM());
			_bean.setFS_SJ(bean.getFS_SJ());
			_bean.setFRSJH(bean.getFRSJH());
			_bean.setCWJLSJH(bean.getCWJLSJH());
			_bean.setBSRYSJH(bean.getBSRYSJH());
			beans.add(_bean);
		}
		// 扣款成功提醒信息
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		
		int startxm = dxmb.indexOf("#{循环：征收项目（开始）}");
		int endxm = dxmb.indexOf("#{循环：征收项目（结束）}");
		int startpm = dxmb.indexOf("#{循环：征收品目（开始）}");
		int endpm = dxmb.indexOf("#{循环：征收品目（结束）}");
		
		StringBuffer part_xms = new StringBuffer("");//xm
		StringBuffer part_pms = new StringBuffer("");//pm
		String part_xm = "";
		String part_pm = "";
		if(startxm>-1 && endxm>startxm) {
			part_xm = dxmb.substring(startxm + 14, endxm);
		}
		if(startpm>-1 && endpm>startpm) {
			part_pm = dxmb.substring(startpm + 14, endpm);
		}
		int index = 0;
		JKTX_BEAN nowbean = null;
		//如果短信模板按照征收项目，则归并集合
		// 按照品目分组的，那么此时这个金额是品目的金额，而不是项目的金额
		if(startxm>-1) {
			List<JKTX_BEAN> beans2 = beans;
			beans = new ArrayList<JKTX_BEAN>();
			String prexm = null;
			String nowxm = null;
			double xmje = 0;
			boolean xmend = true;
			for(JKTX_BEAN sfxx : beans2) {
				xmend = false;
				nowxm = sfxx.getZSXM_DMS();
				if(prexm == null) {//第一条
					nowbean = sfxx;
					prexm = nowxm;
					xmje = Double.parseDouble(sfxx.getSF_JES());
				} else {
					if(prexm.equals(nowxm)) {
						//同xm
						xmje += Double.parseDouble(sfxx.getSF_JES());
					} else {
						//不同xm
						nowbean.setSF_JES(""+xmje);
						nowbean.setZSPM_DMS("");
						beans.add(nowbean);
						prexm = nowxm;
						xmje = Double.parseDouble(sfxx.getSF_JES());
						nowbean = sfxx;
					}
				}
			}
			if(!xmend) {
				//处理最后项目
				nowbean.setSF_JE(xmje);
				nowbean.setZSPM_DMS("");
				beans.add(nowbean);
			}
		}
		for(JKTX_BEAN sfxx : beans) {
			String now_part_xm = part_xm;
			String now_part_pm = part_pm;
			index++;
			boolean isLast = (index == beans.size());
			int xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
			int xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
			int xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
			int xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
			int xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
			int xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
			int xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
			int xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
			if(isLast) {//last
				if(xmlen121>-1 && xmlen122>xmlen121) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen121) +now_part_xm.substring(xmlen121+22, xmlen122)+ now_part_xm.substring(xmlen122+22);
					xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
					xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
					if(xmlen112 > xmlen111) {
						now_part_xm = now_part_xm.substring(0, xmlen111) + now_part_xm.substring(xmlen112+22);
					}
				}
				if(xmlen221>-1 && xmlen222>xmlen221) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen221) +now_part_pm.substring(xmlen221+22, xmlen222)+ now_part_pm.substring(xmlen222+22);
					xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
					xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
					if(xmlen212>xmlen211) {
						now_part_pm = now_part_pm.substring(0, xmlen211) + now_part_pm.substring(xmlen212+22);
					}
				}
			} else {//not last
				if(xmlen111>-1 && xmlen112>-xmlen111) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen111) +now_part_xm.substring(xmlen111+22, xmlen112)+ now_part_xm.substring(xmlen112+22);
					xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
					xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
					if(xmlen122>xmlen121) {
						now_part_xm = now_part_xm.substring(0, xmlen121) + now_part_xm.substring(xmlen122+22);
					}
				}
				if(xmlen211>-1 && xmlen212>-xmlen211) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen211) +now_part_pm.substring(xmlen211+22, xmlen212)+ now_part_pm.substring(xmlen212+22);
					xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
					xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
					if(xmlen222>xmlen221) {
						now_part_pm = now_part_pm.substring(0, xmlen221) + now_part_pm.substring(xmlen222+22);
					}
				}
			}
			
			if(startxm>-1 && endxm>startxm) {
				String xmmc = GwinSoftUtil.translate(sfxx.getZSXM_DMS(), DMB.getTranslateStr(DMB.getDMB("ZSXM_CACHE", "ZSXM_MC")));
				now_part_xm = replace(now_part_xm,"${征收项目}", xmmc);
				now_part_xm = replace(now_part_xm,"${征收项目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			if(startpm>-1 && endpm>startpm) {
				String pmmc = GwinSoftUtil.translate(sfxx.getZSPM_DMS(), DMB.getTranslateStr(DMB.getDMB("XMPM_CACHE", "XMPM_MC")));
				now_part_pm = replace(now_part_pm,"${征收品目}", pmmc);
				now_part_pm = replace(now_part_pm,"${征收品目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			part_xms.append(now_part_xm);
			part_pms.append(now_part_pm);
		}
		if(startxm>-1 && endxm>startxm) {
			dxmb = dxmb.substring(0, startxm) +part_xms+ dxmb.substring(endxm + 14);
		}
		if(startpm>-1 && endpm>startpm) {
			dxmb = dxmb.substring(0, startpm) +part_pms+ dxmb.substring(endpm + 14);
		}
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${税款所属期起}", JobUtils.getFormatDate(bean.getSKSSQ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款所属期止}", JobUtils.getFormatDate(bean.getSKSSZ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款合计金额}", JobUtils.formatMoney(bean.getSF_JE()));
		dxmb = replace(dxmb,"${税票号码}", bean.getXTSPHM());
		dxmb = replace(dxmb,"${缴款日期}", JobUtils.getFormatDate(bean.getJK_RQ(), "yyyy-MM-dd"));
		String kklx = GwinSoftUtil.translate(bean.getJKLX_DM(), DMB.getTranslateStr(DMB.getDMB("JKLX_CACHE", "JKLX_MC")));
		dxmb = replace(dxmb,"${扣款类型}", kklx);
		dxmb+=DXQM;
		return dxmb;
	}

	public static String genSMS(KKSBTX_BEAN bean, String dxmb) {
		// 扣款失败提醒信息
		List<KKSBTX_BEAN> beans = new ArrayList<KKSBTX_BEAN>();
		String[] XMS = bean.getZSXM_DMS().split(",");
		String[] PMS = bean.getZSPM_DMS().split(",");
		String[] JES = bean.getSF_JES().split(",");
		int count = bean.getCNT();
		for(int i = 0 ; i < count; i ++) {
			KKSBTX_BEAN _bean = new KKSBTX_BEAN();
			_bean.setLSH(bean.getLSH());
			_bean.setXTSPHM(bean.getXTSPHM());
			_bean.setJKLX_DM(bean.getJKLX_DM());
			_bean.setNSRSBM(bean.getNSRSBM());
			_bean.setJK_RQ(bean.getJK_RQ());
			_bean.setSKSSQ(bean.getSKSSQ());
			_bean.setSKSSZ(bean.getSKSSZ());
			_bean.setSF_JE(bean.getSF_JE());
			_bean.setCNT(bean.getCNT());
			_bean.setZSXM_DMS(XMS[i]);
			_bean.setZSPM_DMS(PMS[i]);
			_bean.setSF_JES(JES[i]);
			_bean.setNSRMC(bean.getNSRMC());
			_bean.setSWJGBM(bean.getSWJGBM());
			_bean.setSJHM(bean.getSJHM());
			_bean.setSJLX(bean.getSJLX());
			_bean.setSMSINFO(bean.getSMSINFO());
			_bean.setLR_SJ(bean.getLR_SJ());
			_bean.setBZ(bean.getBZ());
			_bean.setFSZT_DM(bean.getFSZT_DM());
			_bean.setFS_SJ(bean.getFS_SJ());
			_bean.setFRSJH(bean.getFRSJH());
			_bean.setCWJLSJH(bean.getCWJLSJH());
			_bean.setBSRYSJH(bean.getBSRYSJH());
			beans.add(_bean);
		}
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		
		int startxm = dxmb.indexOf("#{循环：征收项目（开始）}");
		int endxm = dxmb.indexOf("#{循环：征收项目（结束）}");
		int startpm = dxmb.indexOf("#{循环：征收品目（开始）}");
		int endpm = dxmb.indexOf("#{循环：征收品目（结束）}");
		
		StringBuffer part_xms = new StringBuffer("");//xm
		StringBuffer part_pms = new StringBuffer("");//pm
		String part_xm = "";
		String part_pm = "";
		if(startxm>-1 && endxm>startxm) {
			part_xm = dxmb.substring(startxm + 14, endxm);
		}
		if(startpm>-1 && endpm>startpm) {
			part_pm = dxmb.substring(startpm + 14, endpm);
		}
		int index = 0;
		KKSBTX_BEAN nowbean = null;
		//如果短信模板按照征收项目，则归并集合
		// 按照品目分组的，那么此时这个金额是品目的金额，而不是项目的金额
		if(startxm>-1) {
			List<KKSBTX_BEAN> beans2 = beans;
			beans = new ArrayList<KKSBTX_BEAN>();
			String prexm = null;
			String nowxm = null;
			double xmje = 0;
			boolean xmend = true;
			for(KKSBTX_BEAN sfxx : beans2) {
				xmend = false;
				nowxm = sfxx.getZSXM_DMS();
				if(prexm == null) {//第一条
					nowbean = sfxx;
					prexm = nowxm;
					xmje = Double.parseDouble(sfxx.getSF_JES());
				} else {
					if(prexm.equals(nowxm)) {
						//同xm
						xmje += Double.parseDouble(sfxx.getSF_JES());
					} else {
						//不同xm
						nowbean.setSF_JES(""+xmje);
						nowbean.setZSPM_DMS("");
						beans.add(nowbean);
						prexm = nowxm;
						xmje = Double.parseDouble(sfxx.getSF_JES());
						nowbean = sfxx;
					}
				}
			}
			if(!xmend) {
				//处理最后项目
				nowbean.setSF_JE(xmje);
				nowbean.setZSPM_DMS("");
				beans.add(nowbean);
			}
		}
		for(KKSBTX_BEAN sfxx : beans) {
			String now_part_xm = part_xm;
			String now_part_pm = part_pm;
			index++;
			boolean isLast = (index == beans.size());
			int xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
			int xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
			int xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
			int xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
			int xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
			int xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
			int xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
			int xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
			if(isLast) {//last
				if(xmlen121>-1 && xmlen122>xmlen121) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen121) +now_part_xm.substring(xmlen121+22, xmlen122)+ now_part_xm.substring(xmlen122+22);
					xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
					xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
					if(xmlen112 > xmlen111) {
						now_part_xm = now_part_xm.substring(0, xmlen111) + now_part_xm.substring(xmlen112+22);
					}
				}
				if(xmlen221>-1 && xmlen222>xmlen221) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen221) +now_part_pm.substring(xmlen221+22, xmlen222)+ now_part_pm.substring(xmlen222+22);
					xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
					xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
					if(xmlen212>xmlen211) {
						now_part_pm = now_part_pm.substring(0, xmlen211) + now_part_pm.substring(xmlen212+22);
					}
				}
			} else {//not last
				if(xmlen111>-1 && xmlen112>-xmlen111) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen111) +now_part_xm.substring(xmlen111+22, xmlen112)+ now_part_xm.substring(xmlen112+22);
					xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
					xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
					if(xmlen122>xmlen121) {
						now_part_xm = now_part_xm.substring(0, xmlen121) + now_part_xm.substring(xmlen122+22);
					}
				}
				if(xmlen211>-1 && xmlen212>-xmlen211) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen211) +now_part_pm.substring(xmlen211+22, xmlen212)+ now_part_pm.substring(xmlen212+22);
					xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
					xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
					if(xmlen222>xmlen221) {
						now_part_pm = now_part_pm.substring(0, xmlen221) + now_part_pm.substring(xmlen222+22);
					}
				}
			}
			
			if(startxm>-1 && endxm>startxm) {
				String xmmc = GwinSoftUtil.translate(sfxx.getZSXM_DMS(), DMB.getTranslateStr(DMB.getDMB("ZSXM_CACHE", "ZSXM_MC")));
				now_part_xm = replace(now_part_xm,"${征收项目}", xmmc);
				now_part_xm = replace(now_part_xm,"${征收项目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			if(startpm>-1 && endpm>startpm) {
				String pmmc = GwinSoftUtil.translate(sfxx.getZSPM_DMS(), DMB.getTranslateStr(DMB.getDMB("XMPM_CACHE", "XMPM_MC")));
				now_part_pm = replace(now_part_pm,"${征收品目}", pmmc);
				now_part_pm = replace(now_part_pm,"${征收品目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			part_xms.append(now_part_xm);
			part_pms.append(now_part_pm);
		}
		if(startxm>-1 && endxm>startxm) {
			dxmb = dxmb.substring(0, startxm) +part_xms+ dxmb.substring(endxm + 14);
		}
		if(startpm>-1 && endpm>startpm) {
			dxmb = dxmb.substring(0, startpm) +part_pms+ dxmb.substring(endpm + 14);
		}
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${税款所属期起}", JobUtils.getFormatDate(bean.getSKSSQ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款所属期止}", JobUtils.getFormatDate(bean.getSKSSZ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款合计金额}", JobUtils.formatMoney(bean.getSF_JE()));
		dxmb = replace(dxmb,"${税票号码}", bean.getXTSPHM());
		dxmb = replace(dxmb,"${缴款日期}", JobUtils.getFormatDate(bean.getJK_RQ(), "yyyy-MM-dd"));
		String kklx = GwinSoftUtil.translate(bean.getJKLX_DM(), DMB.getTranslateStr(DMB.getDMB("JKLX_CACHE", "JKLX_MC")));
		dxmb = replace(dxmb,"${扣款类型}", kklx);
		dxmb+=DXQM;
		return dxmb;
	}

	public static String genSMS(SFCJTX_BEAN bean, String dxmb) {
		// 催缴提醒信息
		List<SFCJTX_BEAN> beans = new ArrayList<SFCJTX_BEAN>();
		String[] XMS = bean.getZSXM_DMS().split(",");
		String[] PMS = bean.getZSPM_DMS().split(",");
		String[] JES = bean.getSF_JES().split(",");
		int count = bean.getCNT();
		for(int i = 0 ; i < count; i ++) {
			SFCJTX_BEAN _bean = new SFCJTX_BEAN();
			_bean.setLSH(bean.getLSH());
			_bean.setYZPZXH(bean.getYZPZXH());
//			_bean.setJKLX_DM(bean.getJKLX_DM());
			_bean.setNSRSBM(bean.getNSRSBM());
			_bean.setJKQX_RQ(bean.getJKQX_RQ());
			_bean.setSKSSQ(bean.getSKSSQ());
			_bean.setSKSSZ(bean.getSKSSZ());
			_bean.setSF_JE(bean.getSF_JE());
			_bean.setCNT(bean.getCNT());
			_bean.setZSXM_DMS(XMS[i]);
			_bean.setZSPM_DMS(PMS[i]);
			_bean.setSF_JES(JES[i]);
			_bean.setNSRMC(bean.getNSRMC());
			_bean.setSWJGBM(bean.getSWJGBM());
			_bean.setSJHM(bean.getSJHM());
			_bean.setSJLX(bean.getSJLX());
			_bean.setSMSINFO(bean.getSMSINFO());
			_bean.setLR_SJ(bean.getLR_SJ());
			_bean.setBZ(bean.getBZ());
			_bean.setFSZT_DM(bean.getFSZT_DM());
			_bean.setFS_SJ(bean.getFS_SJ());
			_bean.setFRSJH(bean.getFRSJH());
			_bean.setCWJLSJH(bean.getCWJLSJH());
			_bean.setBSRYSJH(bean.getBSRYSJH());
			beans.add(_bean);
		}
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		
		int startxm = dxmb.indexOf("#{循环：征收项目（开始）}");
		int endxm = dxmb.indexOf("#{循环：征收项目（结束）}");
		int startpm = dxmb.indexOf("#{循环：征收品目（开始）}");
		int endpm = dxmb.indexOf("#{循环：征收品目（结束）}");
		
		StringBuffer part_xms = new StringBuffer("");//xm
		StringBuffer part_pms = new StringBuffer("");//pm
		String part_xm = "";
		String part_pm = "";
		if(startxm>-1 && endxm>startxm) {
			part_xm = dxmb.substring(startxm + 14, endxm);
		}
		if(startpm>-1 && endpm>startpm) {
			part_pm = dxmb.substring(startpm + 14, endpm);
		}
		int index = 0;
		SFCJTX_BEAN nowbean = null;
		//如果短信模板按照征收项目，则归并集合
		// 按照品目分组的，那么此时这个金额是品目的金额，而不是项目的金额
		if(startxm>-1) {
			List<SFCJTX_BEAN> beans2 = beans;
			beans = new ArrayList<SFCJTX_BEAN>();
			String prexm = null;
			String nowxm = null;
			double xmje = 0;
			boolean xmend = true;
			for(SFCJTX_BEAN sfxx : beans2) {
				xmend = false;
				nowxm = sfxx.getZSXM_DMS();
				if(prexm == null) {//第一条
					nowbean = sfxx;
					prexm = nowxm;
					xmje = Double.parseDouble(sfxx.getSF_JES());
				} else {
					if(prexm.equals(nowxm)) {
						//同xm
						xmje += Double.parseDouble(sfxx.getSF_JES());
					} else {
						//不同xm
						nowbean.setSF_JES(""+xmje);
						nowbean.setZSPM_DMS("");
						beans.add(nowbean);
						prexm = nowxm;
						xmje = Double.parseDouble(sfxx.getSF_JES());
						nowbean = sfxx;
					}
				}
			}
			if(!xmend) {
				//处理最后项目
				nowbean.setSF_JE(xmje);
				nowbean.setZSPM_DMS("");
				beans.add(nowbean);
			}
		}
		for(SFCJTX_BEAN sfxx : beans) {
			String now_part_xm = part_xm;
			String now_part_pm = part_pm;
			index++;
			boolean isLast = (index == beans.size());
			int xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
			int xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
			int xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
			int xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
			int xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
			int xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
			int xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
			int xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
			if(isLast) {//last
				if(xmlen121>-1 && xmlen122>xmlen121) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen121) +now_part_xm.substring(xmlen121+22, xmlen122)+ now_part_xm.substring(xmlen122+22);
					xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
					xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
					if(xmlen112 > xmlen111) {
						now_part_xm = now_part_xm.substring(0, xmlen111) + now_part_xm.substring(xmlen112+22);
					}
				}
				if(xmlen221>-1 && xmlen222>xmlen221) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen221) +now_part_pm.substring(xmlen221+22, xmlen222)+ now_part_pm.substring(xmlen222+22);
					xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
					xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
					if(xmlen212>xmlen211) {
						now_part_pm = now_part_pm.substring(0, xmlen211) + now_part_pm.substring(xmlen212+22);
					}
				}
			} else {//not last
				if(xmlen111>-1 && xmlen112>-xmlen111) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen111) +now_part_xm.substring(xmlen111+22, xmlen112)+ now_part_xm.substring(xmlen112+22);
					xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
					xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
					if(xmlen122>xmlen121) {
						now_part_xm = now_part_xm.substring(0, xmlen121) + now_part_xm.substring(xmlen122+22);
					}
				}
				if(xmlen211>-1 && xmlen212>-xmlen211) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen211) +now_part_pm.substring(xmlen211+22, xmlen212)+ now_part_pm.substring(xmlen212+22);
					xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
					xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
					if(xmlen222>xmlen221) {
						now_part_pm = now_part_pm.substring(0, xmlen221) + now_part_pm.substring(xmlen222+22);
					}
				}
			}
			
			if(startxm>-1 && endxm>startxm) {
				String xmmc = GwinSoftUtil.translate(sfxx.getZSXM_DMS(), DMB.getTranslateStr(DMB.getDMB("ZSXM_CACHE", "ZSXM_MC")));
				now_part_xm = replace(now_part_xm,"${征收项目}", xmmc);
				now_part_xm = replace(now_part_xm,"${征收项目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			if(startpm>-1 && endpm>startpm) {
				String pmmc = GwinSoftUtil.translate(sfxx.getZSPM_DMS(), DMB.getTranslateStr(DMB.getDMB("XMPM_CACHE", "XMPM_MC")));
				now_part_pm = replace(now_part_pm,"${征收品目}", pmmc);
				now_part_pm = replace(now_part_pm,"${征收品目金额}", JobUtils.formatMoney(sfxx.getSF_JES()));
			}
			part_xms.append(now_part_xm);
			part_pms.append(now_part_pm);
		}
		if(startxm>-1 && endxm>startxm) {
			dxmb = dxmb.substring(0, startxm) +part_xms+ dxmb.substring(endxm + 14);
		}
		if(startpm>-1 && endpm>startpm) {
			dxmb = dxmb.substring(0, startpm) +part_pms+ dxmb.substring(endpm + 14);
		}
		
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${税款所属期起}", JobUtils.getFormatDate(bean.getSKSSQ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款所属期止}", JobUtils.getFormatDate(bean.getSKSSZ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款合计金额}", JobUtils.formatMoney(bean.getSF_JE()));
		dxmb = replace(dxmb,"${税票号码}", bean.getYZPZXH());
		dxmb = replace(dxmb,"${缴款日期}", JobUtils.getFormatDate(bean.getJKQX_RQ(), "yyyy-MM-dd"));
		dxmb+=DXQM;
		return dxmb;
	
	}

	public static String genSMS(SFCBTX_BEAN bean, String dxmb) {
		// 催报提醒信息
		List<SFCBTX_BEAN> beans = new ArrayList<SFCBTX_BEAN>();
		String[] XMS = bean.getZSXM_DMS().split(",");
		int count = bean.getCNT();
		for(int i = 0 ; i < count; i ++) {
			SFCBTX_BEAN _bean = new SFCBTX_BEAN();
			_bean.setLSH(bean.getLSH());
			_bean.setNSRSBM(bean.getNSRSBM());
			_bean.setSBQX(bean.getSBQX());
			_bean.setSKSSQ(bean.getSKSSQ());
			_bean.setSKSSZ(bean.getSKSSZ());
			_bean.setCNT(bean.getCNT());
			_bean.setZSXM_DMS(XMS[i]);
			_bean.setNSRMC(bean.getNSRMC());
			_bean.setSWJGBM(bean.getSWJGBM());
			_bean.setSJHM(bean.getSJHM());
			_bean.setSJLX(bean.getSJLX());
			_bean.setSMSINFO(bean.getSMSINFO());
			_bean.setLR_SJ(bean.getLR_SJ());
			_bean.setBZ(bean.getBZ());
			_bean.setFSZT_DM(bean.getFSZT_DM());
			_bean.setFS_SJ(bean.getFS_SJ());
			_bean.setFRSJH(bean.getFRSJH());
			_bean.setCWJLSJH(bean.getCWJLSJH());
			_bean.setBSRYSJH(bean.getBSRYSJH());
			beans.add(_bean);
		}
		// 扣款成功提醒信息
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		
		int startxm = dxmb.indexOf("#{循环：征收项目（开始）}");
		int endxm = dxmb.indexOf("#{循环：征收项目（结束）}");
		int startpm = dxmb.indexOf("#{循环：征收品目（开始）}");
		int endpm = dxmb.indexOf("#{循环：征收品目（结束）}");
		
		StringBuffer part_xms = new StringBuffer("");//xm
		StringBuffer part_pms = new StringBuffer("");//pm
		String part_xm = "";
		String part_pm = "";
		if(startxm>-1 && endxm>startxm) {
			part_xm = dxmb.substring(startxm + 14, endxm);
		}
		if(startpm>-1 && endpm>startpm) {
			part_pm = dxmb.substring(startpm + 14, endpm);
		}
		int index = 0;
		for(SFCBTX_BEAN sfxx : beans) {
			String now_part_xm = part_xm;
			String now_part_pm = part_pm;
			index++;
			boolean isLast = (index == beans.size());
			int xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
			int xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
			int xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
			int xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
			int xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
			int xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
			int xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
			int xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
			if(isLast) {//last
				if(xmlen121>-1 && xmlen122>xmlen121) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen121) +now_part_xm.substring(xmlen121+22, xmlen122)+ now_part_xm.substring(xmlen122+22);
					xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
					xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
					if(xmlen112 > xmlen111) {
						now_part_xm = now_part_xm.substring(0, xmlen111) + now_part_xm.substring(xmlen112+22);
					}
				}
				if(xmlen221>-1 && xmlen222>xmlen221) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen221) +now_part_pm.substring(xmlen221+22, xmlen222)+ now_part_pm.substring(xmlen222+22);
					xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
					xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
					if(xmlen212>xmlen211) {
						now_part_pm = now_part_pm.substring(0, xmlen211) + now_part_pm.substring(xmlen212+22);
					}
				}
			} else {//not last
				if(xmlen111>-1 && xmlen112>-xmlen111) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen111) +now_part_xm.substring(xmlen111+22, xmlen112)+ now_part_xm.substring(xmlen112+22);
					xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
					xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
					if(xmlen122>xmlen121) {
						now_part_xm = now_part_xm.substring(0, xmlen121) + now_part_xm.substring(xmlen122+22);
					}
				}
				if(xmlen211>-1 && xmlen212>-xmlen211) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen211) +now_part_pm.substring(xmlen211+22, xmlen212)+ now_part_pm.substring(xmlen212+22);
					xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
					xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
					if(xmlen222>xmlen221) {
						now_part_pm = now_part_pm.substring(0, xmlen221) + now_part_pm.substring(xmlen222+22);
					}
				}
			}
			
			if(startxm>-1 && endxm>startxm) {
				String xmmc = GwinSoftUtil.translate(sfxx.getZSXM_DMS(), DMB.getTranslateStr(DMB.getDMB("ZSXM_CACHE", "ZSXM_MC")));
				now_part_xm = replace(now_part_xm,"${征收项目}", xmmc);
			}
			part_xms.append(now_part_xm);
			part_pms.append(now_part_pm);
		}
		if(startxm>-1 && endxm>startxm) {
			dxmb = dxmb.substring(0, startxm) +part_xms+ dxmb.substring(endxm + 14);
		}
		if(startpm>-1 && endpm>startpm) {
			dxmb = dxmb.substring(0, startpm) +part_pms+ dxmb.substring(endpm + 14);
		}
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${税款所属期起}", JobUtils.getFormatDate(bean.getSKSSQ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款所属期止}", JobUtils.getFormatDate(bean.getSKSSZ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${申报日期}", JobUtils.getFormatDate(bean.getSBQX(), "yyyy-MM-dd"));
		dxmb+=DXQM;
		return dxmb;
	
	}

	public static String genSMS(TYDQTX_BEAN bean, String dxmb) {
		// 停业到期信息
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${停业日期}", JobUtils.getFormatDate(bean.getTYDQ_RQ(), "yyyy-MM-dd"));
		dxmb+=DXQM;
		return dxmb;
	}

	//被动发送
	public static String genSMS(FPZJXX_BEAN bean, String dxmb) {
		// 发票中奖信息
		if(bean.getINFO()==null || "".equals(bean.getINFO())) {
			dxmb = "未发布该市该月中奖信息。";
		} else {
			String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
			String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
			dxmb = replace(dxmb,"${当前日期}", nowdate);
			dxmb = replace(dxmb,"${当前时间}", nowtime);
			dxmb = replace(dxmb,"${中奖年月}", bean.getKJNY());
			dxmb = replace(dxmb,"${开奖内容}", bean.getINFO());
			String ssxm = GwinSoftUtil.translate(bean.getSSX(), DMB.getTranslateStr(DMB.getDMB("SJSX_CACHE", "SJSX_MC")));
			dxmb = replace(dxmb,"${市级缩写}", ssxm);
		}
		dxmb+=DXQM;
		return dxmb;
	}

	public static String genSMS(List<SFXX_BEAN> beans, String dxmb) {
		// 税费查询  税款所属期跨月时，如何查询税费缴纳情况？
		if(beans==null || beans.size()==0) {
			dxmb = "未查询到税费缴纳情况。 ";
			return dxmb;
		}
		SFXX_BEAN bean = beans.get(0);
		String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
		String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
		double hjje = 0;
		
		int startxm = dxmb.indexOf("#{循环：征收项目（开始）}");
		int endxm = dxmb.indexOf("#{循环：征收项目（结束）}");
		int startpm = dxmb.indexOf("#{循环：征收品目（开始）}");
		int endpm = dxmb.indexOf("#{循环：征收品目（结束）}");
		
		StringBuffer part_xms = new StringBuffer("");//xm
		StringBuffer part_pms = new StringBuffer("");//pm
		String part_xm = "";
		String part_pm = "";
		if(startxm>-1 && endxm>startxm) {
			part_xm = dxmb.substring(startxm + 14, endxm);
		}
		if(startpm>-1 && endpm>startpm) {
			part_pm = dxmb.substring(startpm + 14, endpm);
		}
		int index = 0;
		SFXX_BEAN nowbean = null;
		//如果短信模板按照征收项目，则归并集合
		// 按照品目分组的，那么此时这个金额是品目的金额，而不是项目的金额
		if(startxm>-1) {
			List<SFXX_BEAN> beans2 = beans;
			beans = new ArrayList<SFXX_BEAN>();
			String prexm = null;
			String nowxm = null;
			double xmje = 0;
			boolean xmend = true;
			for(SFXX_BEAN sfxx : beans2) {
				xmend = false;
				nowxm = sfxx.getZSXM_DM();
				if(prexm == null) {//第一条
					nowbean = sfxx;
					prexm = nowxm;
					xmje = sfxx.getSF_JE();
				} else {
					if(prexm.equals(nowxm)) {
						//同xm
						xmje += sfxx.getSF_JE();
					} else {
						//不同xm
						nowbean.setSF_JE(xmje);
						nowbean.setZSPM_DM("");
						beans.add(nowbean);
						prexm = nowxm;
						xmje = sfxx.getSF_JE();
						nowbean = sfxx;
					}
				}
			}
			if(!xmend) {
				//处理最后项目
				nowbean.setSF_JE(xmje);
				nowbean.setZSPM_DM("");
				beans.add(nowbean);
			}
		}
		for(SFXX_BEAN sfxx : beans) {
			String now_part_xm = part_xm;
			String now_part_pm = part_pm;
			index++;
			boolean isLast = (index == beans.size());
			int xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
			int xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
			int xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
			int xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
			int xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
			int xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
			int xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
			int xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
			if(isLast) {//last
				if(xmlen121>-1 && xmlen122>xmlen121) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen121) +now_part_xm.substring(xmlen121+22, xmlen122)+ now_part_xm.substring(xmlen122+22);
					xmlen111 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（开始）}");
					xmlen112 = now_part_xm.indexOf("#{循环内部判断：如果有下一个项目（结束）}");
					if(xmlen112 > xmlen111) {
						now_part_xm = now_part_xm.substring(0, xmlen111) + now_part_xm.substring(xmlen112+22);
					}
				}
				if(xmlen221>-1 && xmlen222>xmlen221) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen221) +now_part_pm.substring(xmlen221+22, xmlen222)+ now_part_pm.substring(xmlen222+22);
					xmlen211 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（开始）}");
					xmlen212 = now_part_pm.indexOf("#{循环内部判断：如果有下一个品目（结束）}");
					if(xmlen212>xmlen211) {
						now_part_pm = now_part_pm.substring(0, xmlen211) + now_part_pm.substring(xmlen212+22);
					}
				}
			} else {//not last
				if(xmlen111>-1 && xmlen112>-xmlen111) {//xm
					now_part_xm = now_part_xm.substring(0, xmlen111) +now_part_xm.substring(xmlen111+22, xmlen112)+ now_part_xm.substring(xmlen112+22);
					xmlen121 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（开始）}");
					xmlen122 = now_part_xm.indexOf("#{循环内部判断：如果无下一个项目（结束）}");
					if(xmlen122>xmlen121) {
						now_part_xm = now_part_xm.substring(0, xmlen121) + now_part_xm.substring(xmlen122+22);
					}
				}
				if(xmlen211>-1 && xmlen212>-xmlen211) {//pm
					now_part_pm = now_part_pm.substring(0, xmlen211) +now_part_pm.substring(xmlen211+22, xmlen212)+ now_part_pm.substring(xmlen212+22);
					xmlen221 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（开始）}");
					xmlen222 = now_part_pm.indexOf("#{循环内部判断：如果无下一个品目（结束）}");
					if(xmlen222>xmlen221) {
						now_part_pm = now_part_pm.substring(0, xmlen221) + now_part_pm.substring(xmlen222+22);
					}
				}
			}
			
			if(startxm>-1 && endxm>startxm) {
				String xmmc = GwinSoftUtil.translate(sfxx.getZSXM_DM(), DMB.getTranslateStr(DMB.getDMB("ZSXM_CACHE", "ZSXM_MC")));
				now_part_xm = replace(now_part_xm,"${征收项目}", xmmc);
				now_part_xm = replace(now_part_xm,"${征收项目金额}", JobUtils.formatMoney(sfxx.getSF_JE()));
			}
			if(startpm>-1 && endpm>startpm) {
				String pmmc = GwinSoftUtil.translate(sfxx.getZSPM_DM(), DMB.getTranslateStr(DMB.getDMB("XMPM_CACHE", "XMPM_MC")));
				now_part_pm = replace(now_part_pm,"${征收品目}", pmmc);
				now_part_pm = replace(now_part_pm,"${征收品目金额}", JobUtils.formatMoney(sfxx.getSF_JE()));
			}
			part_xms.append(now_part_xm);
			part_pms.append(now_part_pm);
			hjje += sfxx.getSF_JE();
		}
		if(startxm>-1 && endxm>startxm) {
			dxmb = dxmb.substring(0, startxm) +part_xms+ dxmb.substring(endxm + 14);
		}
		if(startpm>-1 && endpm>startpm) {
			dxmb = dxmb.substring(0, startpm) +part_pms+ dxmb.substring(endpm + 14);
		}
		dxmb = replace(dxmb,"${当前日期}", nowdate);
		dxmb = replace(dxmb,"${当前时间}", nowtime);
		dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRBM());
		dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
		dxmb = replace(dxmb,"${税款所属期起}", JobUtils.getFormatDate(bean.getSKSSQ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款所属期止}", JobUtils.getFormatDate(bean.getSKSSZ(), "yyyy-MM-dd"));
		dxmb = replace(dxmb,"${税款合计金额}", JobUtils.formatMoney(hjje));
		dxmb+=DXQM;
		return dxmb;
	}

	public static String genSMS(FPXX_BEAN bean, String dxmb) {
		// 发票真伪查询
		if(bean==null || bean.getNSRSBM()==null) {
			dxmb = "未查询到该发票任何信息。";
		} else {
			String nowdate = GwinSoftUtil.getSysFormatDate("yyyy-MM-dd");
			String nowtime = GwinSoftUtil.getSysFormatDate("HH:mm:ss");
			dxmb = replace(dxmb,"${当前日期}", nowdate);
			dxmb = replace(dxmb,"${当前时间}", nowtime);
			dxmb = replace(dxmb,"${纳税人编码}", bean.getNSRSBM());
			dxmb = replace(dxmb,"${纳税人名称}", bean.getNSRMC());
			dxmb = replace(dxmb,"${发票种类}", bean.getFPZL());
			dxmb = replace(dxmb,"${发票代码}", bean.getFPDM());
			dxmb = replace(dxmb,"${发票号码}", bean.getFPHM());
			dxmb = replace(dxmb,"${发票金额}", ""+bean.getFP_JE());
			dxmb = replace(dxmb,"${发票日期}", JobUtils.getFormatDate(bean.getFP_RQ(), "yyyy-MM-dd HH:mm:ss"));
		}
		dxmb+=DXQM;
		return dxmb;
	}
}