package com.gwinsoft.framework.db.support;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.gwinsoft.framework.context.WebThreadLocal;
import com.gwinsoft.framework.core.exception.APPException;
import com.gwinsoft.framework.db.DBHelper;
import com.gwinsoft.framework.db.DBPersistenceManager;
import com.gwinsoft.framework.taglib.splitpage.PageBean;
import com.gwinsoft.util.ConvertTool;
import com.gwinsoft.util.GwinSoftUtil;

/**
 * NoXA没有问题,但是XA的
 * 
 * TODO1:
 * jdbctemplate能够自动关闭连接，但通过jdbctemplate得到的connection用完后关闭连接，是否影响事务的一致性？
 * 
 * TODO2：
 * 
 */
public class OracleDBPersistenceManagerImpl2 implements DBPersistenceManager {

	private static final Log logger = LogFactory.getLog(OracleDBPersistenceManagerImpl.class);
	private String dataSourceName = "";
	private DataSource datasource;
	private JdbcTemplate jdbcTemplate;

	public OracleDBPersistenceManagerImpl2(DataSource dataSource, String dataSourceName) {
		this.dataSourceName = dataSourceName;
		this.datasource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(this.datasource);
	}

	public OracleDBPersistenceManagerImpl2() {
		
	}
	public void setDataSourceName(String dataSourceName) {
		this.dataSourceName = dataSourceName;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
		this.jdbcTemplate = new JdbcTemplate(this.datasource);
	}

	@SuppressWarnings("unchecked")
	public List call(String procName,final int[] params_type, final Object[] params_in) {
		final List values = new ArrayList();
		String _procName = "{call cover(?,?,?,?,?,?,?,?)}";
		int paramSize = (params_type == null) ? 0 : params_type.length;
		_procName = "{call "+procName+"(";
		for(int i = 0 ; i < paramSize; i ++) {
			if(i != 0) {
				_procName += ",";
			}
			_procName += "?";
		}
		_procName += ")}";
		final String THEPROCNAME = _procName;
		return jdbcTemplate.execute(THEPROCNAME, new CallableStatementCallback() {
			public List doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
				int paramsout_size = 0;
				if (params_type != null) {
					for (int i = 0; i < params_type.length; i++) {
						if(i < params_in.length) {
							switch (params_type[i]) {
							case Types.INTEGER:
								cs.setInt(i + 1, (Integer) params_in[i]);
								break;
							case Types.VARCHAR:
								cs.setString(i + 1, (String) params_in[i]);
								break;
							case Types.DATE:
								cs.setDate(i + 1, (java.sql.Date) params_in[i]);
								break;
							case Types.FLOAT:
								cs.setFloat(i + 1, (Float) params_in[i]);
								break;
							case Types.DOUBLE:
								cs.setDouble(i + 1, (Double) params_in[i]);
								break;
							case Types.TIME:
								cs.setTime(i + 1, (Time) params_in[i]);
								break;
							default :
								APPException e = new APPException("存储过程参数未定义！请联系管理员！");
								logger.error("存储过程参数未定义！请联系管理员！", e);
								throw e;
							}
						} else {
							paramsout_size++;
							cs.registerOutParameter(i + 1, params_type[i]);
						}
					}
				}
//				logger.info("执行存储过程~~"+THEPROCNAME+",参数为："+GwinSoftUtil.stringsout(params_in, ","));
				cs.execute();
				List rowlist = new ArrayList();
				for (int i = params_type.length - paramsout_size; i <= paramsout_size; i++) {
					switch (params_type[i]) {
					case Types.INTEGER:
						rowlist.add(cs.getInt(i+1));
						break;
					case Types.VARCHAR:
						rowlist.add(cs.getString(i + 1));
						break;
					case Types.DATE:
						rowlist.add(cs.getDate(i + 1));
						break;
					case Types.FLOAT:
						rowlist.add(cs.getFloat(i + 1));
						break;
					case Types.DOUBLE:
						rowlist.add(cs.getDouble(i + 1));
						break;
					case Types.TIME:
						rowlist.add(cs.getTime(i + 1));
						break;
					default :
						APPException e = new APPException("存储过程参数未定义！请联系管理员！");
						logger.error("存储过程参数未定义！请联系管理员！", e);
						throw e;
					}
				}
				values.add(rowlist);
				return rowlist;
			}
		});
	}
	public <T> List<T> query(String sql, RowMapper<T> rowMapper) {
		return jdbcTemplate.query(sql, rowMapper);
	}
	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int executeUpdate(String sql) {
		return this.getJdbcTemplate().update(sql);
	}

	public int executeUpdate(String sql, Object[] obj) {
		return this.getJdbcTemplate().update(sql, obj);
	}

	public int[] executeUpdateBatch(String[] sql) {
		if(sql == null || sql.length==0) {
			return new int[0];
		}
		return this.getJdbcTemplate().batchUpdate(sql);
	}

	public int[] executeUpdateBatch(List<String> sql) {
		if(sql == null || sql.size()==0) {
			return new int[0];
		}
		String[] sqls = (String[]) sql.toArray();
		return executeUpdateBatch(sqls);
	}

	public int[] executeUpdateBatch(String sql, final List<Object[]> dataSet) {
		BatchPreparedStatementSetter setter = new BatchPreparedStatementSetter() {
			public int getBatchSize() {
				return dataSet.size();
			}
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				Object[] obj = dataSet.get(i);
				int ii = 1;
				for(Object param:obj) {
					ps.setObject(ii, param);
					ii++;
				}
			}
		};
		return this.getJdbcTemplate().batchUpdate(sql, setter);
		//return this.getJdbcTemplate().batchUpdate(sql, dataSet);
	}

	public <T> int[] executeUpdateBatchT(String sql, List<T> args) {
		List<Object[]> batchArgs = new ArrayList<Object[]>();
		for(T t : args) {
			Object[] objs = tToObjects(sql, t);
			batchArgs.add(objs);
		}
		return executeUpdateBatch(sql, batchArgs);
	}

	private <T> Object[] tToObjects(String sql, T t) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Object> objs = new ArrayList<Object>();
		Method[] ms = t.getClass().getMethods();
		for (int i = 0; i < ms.length; i++) {
			if (ms[i].getModifiers() == Modifier.PUBLIC && ms[i].getName().startsWith("get")) {
				String name = ms[i].getName().substring(3);
				if (ms[i].getParameterTypes().length == 0) {
					try {
						Object o = ms[i].invoke(t);
						map.put(name, o);
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(sql.trim().toUpperCase().startsWith("INSERT")) {
			String regex1 = "(\\w+)[,|\\)]";//insert 语句 取出所有列 AUTH_DM AUTH_MC AUTH_CC C1 C2
			String regex2 = "(\\W)[,|\\)]";//insert 语句 取出所有值   ? ' ' ? ?
			Pattern p1 = Pattern.compile(regex1);
			Matcher m1 = p1.matcher(sql);
			List<String> keys = new ArrayList<String>();
			while (m1.find()) {
				String key = m1.group(1);
				keys.add(key);
			}
			Pattern p = Pattern.compile(regex2);
			Matcher m = p.matcher(sql);
			int i = 0;
			while (m.find()) {
				String key = m.group(1);
				if("?".equals(key)) {
					Object obj = map.get(keys.get(i));
					objs.add(obj);
				}
				i ++;
			}
		} else {
			String regex = "(\\w+)=\\?";//update 语句 //group(1)
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(sql);
			while (m.find()) {
				String key = m.group(1);
				if(map.containsKey(key)) {
					Object obj = map.get(key);
					objs.add(obj);
				}
			}
		}
		Object[] _objs = GwinSoftUtil.listToStrings(objs);
		return _objs;
	}
	
	public SqlRowSet quereyForRowSet(String sql) {
		return this.getJdbcTemplate().queryForRowSet(sql);
	}

	public SqlRowSet quereyForRowSet(String sql, Object[] args) {
		return this.getJdbcTemplate().queryForRowSet(sql, args);
	}

	public int queryForInt(String sql) {
		return this.getJdbcTemplate().queryForInt(sql);
	}

	public int queryForInt(String sql, Object[] args) {
		return this.getJdbcTemplate().queryForInt(sql, args);
	}

	public List<Map<String, Object>> queryForList(String sql) {
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List<Map<String, Object>> queryForList(String sql, Object[] args) {
		return this.getJdbcTemplate().queryForList(sql, args);
	}

	public <T> List<T> queryForList(String sql, Class<T> clazz) {
		return queryForList(sql, clazz, null);
	}

	public <T> List<T> queryForList(String sql, Class<T> clazz, Object[] args) {
		List<Map<String, Object>> values = this.getJdbcTemplate().queryForList(sql, args);
		List<T> results = new ArrayList<T>();
		try {
			for(Map<String, Object> map : values) {
				T t = ConvertTool.vo2ui(clazz, map);
				results.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	public <T> List<T> queryPageList(String sql, Class<T> clazz, Object[] args, PageBean pageBean) {
		String regex1 = "SELECT (.*) FROM ";
		String regex2 = "ORDER BY (.*) ASC";
		String regex3 = "ORDER BY (.*) DESC";
		Pattern p1 = Pattern.compile(regex1);
		Matcher m1 = p1.matcher(sql.toUpperCase());
		//new start
		String fromStr = "";
		boolean isFind = false;
		while(m1.find()) {
			isFind = true;
			String temp = m1.group();
			fromStr = temp;
			temp = temp.substring(0, temp.length()-4);
			m1 = p1.matcher(temp);
		}
		if(!isFind || "".equals(fromStr)) {
			throw new APPException("SQL出错！");
		}
		//fromStr = fromStr.replaceAll("\\*", "\\\\*");
		//String sqlCount = sql.replaceFirst(fromStr, "SELECT COUNT(0) FROM");
		
		String sqlCount = "SELECT COUNT(0) FROM "+ sql.substring(fromStr.length());
//		sqlCount = sqlCount.replaceAll(regex2, "");
//		sqlCount = sqlCount.replaceAll(regex3, "");
		if(sqlCount.indexOf("ORDER BY")>0)
			sqlCount = sqlCount.substring(0, sqlCount.indexOf("ORDER BY"));
		int n = queryForInt(sqlCount, args) ;
		pageBean.setTotalitems(n);
		//new end
		/*int n = 0;
		if (m1.find()) {
			String sqlCount = sql.replaceFirst(regex1, "SELECT COUNT(0) FROM");
			sqlCount = sqlCount.replaceAll(regex2, "");
			sqlCount = sqlCount.replaceAll(regex3, "");
			n = queryForInt(sqlCount, args) ;
			pageBean.setTotalitems(n);
		} else {
			throw new APPException("SQL出错！");
		}*/
		//所显示的记录为 (从[当前页-1]*每页的大小) 到 ([当前页*每页的大小)
		
		//String limit = " LIMIT " + (pageBean.getPageno() - 1) * pageBean.getPageitems() + "," + pageBean.getPageitems();
		//sql = sql + limit;
		
//		sql = "select *"+
//		"  from ("+
//		"       select row_.*,rownum rownum_ from ("+
//		             sql +
//		"       ) row_"+
//		"  )"+
//		"where rownum_ <= "+pageBean.getPageno() * pageBean.getPageitems()+" and rownum_ > "+(pageBean.getPageno() - 1) * pageBean.getPageitems();
		 
		sql = "SELECT * FROM ( SELECT A.*, ROWNUM RN "+
			  "FROM ("+sql+") A WHERE ROWNUM <= "+pageBean.getPageno() * pageBean.getPageitems()+" ) WHERE RN > "+(pageBean.getPageno() - 1) * pageBean.getPageitems();
		
		List<T> values = queryForList(sql, clazz, args);
		return values;
	}
	/*public <T, Z> List<T> queryPageList(String sql, Class<T> clazz, Z z, PageBean pageBean) {
		
	}*/
	public <T> T queryForObject(String sql, Class<T> clazz) {
		return queryForObject(sql, clazz, null);
	}

	public <T> T queryForObject(String sql, Class<T> clazz, Object[] args) {
		//Map<String, Object> m = this.getJdbcTemplate().queryForMap(sql, args);
				SqlRowSet row = this.getJdbcTemplate().queryForRowSet(sql, args);
				SqlRowSetMetaData meta = row.getMetaData();
				int count = meta.getColumnCount();
				Map<String, Object> map = new HashMap<String, Object>();
				if(row.next()) {
					for(int i = 0; i < count; i ++) {
						//int type = meta.getColumnType(i + 1);
						String name = meta.getColumnName(i + 1);
						//map.put(name, row.getObject(name));
						map.put(name, row.getObject(name));
					}
				}
				try {
					return ConvertTool.vo2ui(clazz, map);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
//		List<Map<String, Object>> resultList = this.getJdbcTemplate().queryForList(sql, args);
//		if(resultList!=null && resultList.size()>0) {
//			Map<String, Object> rowdata = resultList.get(0);
//		}
//		Map<String, Object> m = this.getJdbcTemplate().queryForMap(sql, args);
//		SqlRowSet row = this.getJdbcTemplate().queryForRowSet(sql, args);
//		SqlRowSetMetaData meta = row.getMetaData();
//		int count = meta.getColumnCount();
//		Map<String, Object> map = new HashMap<String, Object>();
//		if(row.next()) {
//			for(int i = 0; i < count; i ++) {
//				//int type = meta.getColumnType(i + 1);
//				String name = meta.getColumnName(i + 1);
//				//map.put(name, row.getObject(name));
//				map.put(name, m.get(name));
//			}
//		}
//		try {
//			return ConvertTool.vo2ui(clazz, map);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}

	public String queryForString(String sql) {
		return queryForObject(sql, String.class);
	}

	public String queryForString(String sql, Object[] args) {
		return queryForObject(sql, String.class, args);
	}

	public <T> void executeUpdate(String sql, T t) {
		Object[] _objs = tToObjects(sql, t);
		executeUpdate(sql, _objs);
	}

	private Map<String,Integer> getMetaData(String sql) {
		String tablename = null;
		if(sql==null) {
			return null;
		} else if(sql.toUpperCase().startsWith("SELECT")) {
			String regex = "SELECT\\s+(\\*)|(\\s*\\w\\s*[,]*\\s*)\\s+FROM\\s+(\\w+)\\s+";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(sql.toUpperCase());
			if (m.find()) {
				tablename = m.group(2);
			}
		} else if(sql.toUpperCase().startsWith("INSERT")) {
			String regex = "INSERT\\s+INTO\\s+(\\w+)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(sql.toUpperCase());
			if (m.find()) {
				tablename = m.group(1);
			}
		} else if(sql.toUpperCase().startsWith("UPDATE")) {
			String regex = "UPDATE\\s+(\\w+)";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(sql.toUpperCase());
			if (m.find()) {
				tablename = m.group(1);
			}
		}
		sql = "SELECT * FROM "+tablename +" LIMIT 0";
		Map<String,Integer> map = new HashMap<String,Integer>();
		SqlRowSet row = this.getJdbcTemplate().queryForRowSet(sql);
		SqlRowSetMetaData meta = row.getMetaData();
		int count = meta.getColumnCount();
		for(int i = 0; i < count; i ++) {
			String name = meta.getColumnName(i + 1);
			int type = meta.getColumnType(i + 1);
			map.put(name, type);
		}
		return map;
	}
	
	public String getDataSourceName() {
		return dataSourceName;
	}

	public void close() {
		String PMNames = WebThreadLocal.getThreadAttribute(DBHelper.PMNamesName);
		WebThreadLocal.removeThreadAttribute(DBHelper.DataSourcePreName + this.dataSourceName);
		if (PMNames != null) {
			String[] pmNames = PMNames.split(";");
			if (pmNames.length == 1) {
				WebThreadLocal.removeThreadAttribute(DBHelper.PMNamesName);
			} else if (pmNames.length >= 2) {
				if(PMNames.endsWith(";"+(DBHelper.DataSourcePreName + this.dataSourceName))) {
					WebThreadLocal.removeThreadAttribute(";"+(DBHelper.DataSourcePreName + this.dataSourceName));
				} else {
					WebThreadLocal.removeThreadAttribute((DBHelper.DataSourcePreName + this.dataSourceName)+";");
				}
			}
		}
	}

	public void closeConn() {
	}

	public BufferedImage queryImage(String sql) {
		BufferedImage image = null;
		Connection dbConnection = null;
		try {
			dbConnection = this.datasource.getConnection();
			Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Blob blob = (Blob) rs.getBlob(1);
				BufferedInputStream inputImage = new BufferedInputStream(blob.getBinaryStream());
				image = ImageIO.read(inputImage);
				inputImage.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	public boolean saveImage(String sql, ByteBuffer nbf) {
		boolean result = false;
		Connection dbConnection = null;
		try {
			byte[] content = nbf.array();
			dbConnection = this.datasource.getConnection();
			Statement stmt = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				rs.updateBytes(1, content);
				rs.updateRow();
				result = true;
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(dbConnection!=null && !dbConnection.isClosed()) {
					dbConnection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}