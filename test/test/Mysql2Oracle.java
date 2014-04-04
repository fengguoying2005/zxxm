package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.sql.ConnectionPoolDataSource;

import com.gwinsoft.framework.otherdbpool.DataSourceFactory;
import com.gwinsoft.framework.otherdbpool.MiniConnectionPoolManager;

public class Mysql2Oracle {

	private static String DBURL = "jdbc:mysql://localhost:3306/lnds?useUnicode=true&amp;characterEncoding=UTF-8";
	private static String DBUSER = "root";
	private static String DBPASSWORD = "12345";
	private static String MAXCONS = "1000";
	private static String TIMEOUT = "60";
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) {
		com.mysql.jdbc.Driver d;
	}

	public static void SMS_NSRDATA() throws Exception {
		Set set = new HashSet();
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File("C:/datas/SMS_NSRDATA.txt")));
		String sql = "SELECT T.*,N.YYYYMMDD FROM V_SFXC T,sms_nsrdata N WHERE N.NSRDATA_LSH=T.NSRDATA_LSH";
		String sql_1 = new String("INSERT INTO SMS_SFXCMSG(SFXCMSG_LSH,NSRDATA_LSH,NSRBM,NSRMC,SJHM,CBRQQ,CBRQZ,MSG,JE,SMSTYPE_DM,FS_SJ,LRRY_DM,XGRY_DM,LR_SJ,XG_SJ,FSCS,NSR_LSH,PHONETYPE) VALUES (");
		String isql = null;
		Connection con = null;
		try {
			ConnectionPoolDataSource ds = DataSourceFactory.createMySQLDataSource(DBURL, DBUSER, DBPASSWORD);
			MiniConnectionPoolManager dbpool = new MiniConnectionPoolManager(ds, Integer.parseInt(MAXCONS), Integer.parseInt(TIMEOUT));
			SMSDBTOOL.setDbpool(dbpool);
			con = SMSDBTOOL.getCon();
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()) {
				i++;
				String NSRDATA_LSH=rs.getString("NSRDATA_LSH");
				String SMSTYPE_DM=rs.getString("SMSTYPE_DM");
				String GROUP_NAME=rs.getString("GROUP_NAME");
				String SMSZT_DM=rs.getString("SMSZT_DM");
				String LRRY_DM=rs.getString("LRRY_DM");
				String XGRY_DM=rs.getString("XGRY_DM");
				String ORG_DM_JG=rs.getString("ORG_DM_JG");
				String DXQM=rs.getString("DXQM");
				String DXMB=rs.getString("DXMB");
				String CALLBACKINFO=rs.getString("CALLBACKINFO");
				String DXMBINFO=rs.getString("DXMBINFO");
				String THYY=rs.getString("THYY");
				String CALLBACK=rs.getString("CALLBACK");
				String SHY=rs.getString("SHY");
				String YYYYMMDD=rs.getString("YYYYMMDD");

				Date LR_SJ=rs.getDate("LR_SJ");
				Date XG_SJ=rs.getDate("XG_SJ");

				if(NSRDATA_LSH==null)NSRDATA_LSH="";
				if(SMSTYPE_DM==null)SMSTYPE_DM="";
				if(GROUP_NAME==null)GROUP_NAME="";
				if(SMSZT_DM==null)SMSZT_DM="";
				if(LRRY_DM==null)LRRY_DM="";
				if(XGRY_DM==null)XGRY_DM="";
				if(ORG_DM_JG==null)ORG_DM_JG="";
				if(DXQM==null)DXQM="";
				if(DXMB==null)DXMB="";
				if(CALLBACKINFO==null)CALLBACKINFO="";
				if(DXMBINFO==null)DXMBINFO="";
				if(THYY==null)THYY="";
				if(CALLBACK==null)CALLBACK="";
				if(SHY==null)SHY="";
				if(YYYYMMDD==null)YYYYMMDD="";

				if(LR_SJ==null) {
					LR_SJ = new Date();
				}
				if(XG_SJ==null) {
					XG_SJ = new Date();
				}
				
				isql = sql_1 +"'"+ NSRDATA_LSH+"','"+SMSTYPE_DM+"','"+GROUP_NAME+"','"+SMSZT_DM+"','"+LRRY_DM+"','"+XGRY_DM+"',TO_DATE('"+sdf.format(LR_SJ)+"','YYYY-MM-DD'),TO_DATE('"+sdf.format(XG_SJ)+"','YYYY-MM-DD'),'"+ORG_DM_JG+"','"+DXQM+"','"+DXMB+"','"+CALLBACKINFO+"','"+DXMBINFO+"','"+THYY+"','"+CALLBACK+"','"+SHY+"','"+YYYYMMDD+"')";
				bw.write(isql+";\n");
				bw.flush();
				set.add(isql);
			}
			System.out.println(i);
			System.out.println(set.size());
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		SMSDBTOOL.updateBath(set);
	}

	public static String getDBURL() {
		return DBURL;
	}

	public static void setDBURL(String dBURL) {
		DBURL = dBURL;
	}

	public static String getDBUSER() {
		return DBUSER;
	}

	public static void setDBUSER(String dBUSER) {
		DBUSER = dBUSER;
	}

	public static String getDBPASSWORD() {
		return DBPASSWORD;
	}

	public static void setDBPASSWORD(String dBPASSWORD) {
		DBPASSWORD = dBPASSWORD;
	}

	public static String getMAXCONS() {
		return MAXCONS;
	}

	public static void setMAXCONS(String mAXCONS) {
		MAXCONS = mAXCONS;
	}

	public static String getTIMEOUT() {
		return TIMEOUT;
	}

	public static void setTIMEOUT(String tIMEOUT) {
		TIMEOUT = tIMEOUT;
	}
}