package test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class TestExp {

	public static void main(String[] args) throws Exception {
		String cs = "gbk";
		BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream("C:/exp.txt"),cs ));
		StringBuffer sb = new StringBuffer();
		String line = r.readLine();
		while(line!=null) {
			sb.append(line);
			line = r.readLine();
			if(line!=null) {
				sb.append("\n");
			}
		}
		String validate = validate(sb.toString());
		System.out.print(validate);
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
			if(index[0]==-1) {
				msg = "#{循环：征收品目（开始）}应与#{循环：征收品目（结束）}配对出现";
				return msg;
			}
		}
		if(index[2]+index[3]>-2) {
			if(index[2]>=index[3]) {
				msg = "#{循环内部判断：如果有下一个品目（开始）}应出现在#{循环内部判断：如果有下一个品目（结束）}之前";
				return msg;
			}
			if(index[2]==-1) {
				msg = "#{循环内部判断：如果有下一个品目（开始）}应与#{循环内部判断：如果有下一个品目（结束）}配对出现";
				return msg;
			}
		}
		if(index[4]+index[5]>-2) {
			if(index[4]>=index[5]) {
				msg = "#{循环内部判断：如果无下一个品目（开始）}应出现在#{循环内部判断：如果无下一个品目（结束）}之前";
				return msg;
			}
			if(index[4]==-1) {
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
		if(start!=index[0] || end!=index[5]) {
			msg = "循环内部判断表达式应在循环表达式的内部。";
		}
		return msg;
	}
}