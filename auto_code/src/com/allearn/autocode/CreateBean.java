package com.allearn.autocode;

import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FtlDef;
import codeGenerate.def.TableConvert;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

public class CreateBean
{
  private Connection connection = null;
  static String url;
  static String username;
  static String password;
  static String rt = "\r\t";
  String SQLTables = "show tables";
  private String method;
  private String argv;
  static String selectStr = "select ";
  static String from = " from ";

  static
  {
    try
    {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void setMysqlInfo(String url, String username, String password) {
    url = url;
    username = username;
    password = password;
  }
  public void setConnection(Connection connection) {
    this.connection = connection;
  }
  public Connection getConnection() throws SQLException {
//	  url = "jdbc:mysql://127.0.0.1:3306/yw?useUnicode=true&characterEncoding=UTF-8";
//	  username = "root";
//	  password = "123456";
    //return DriverManager.getConnection(url, username, password);
    
    return DriverManager.getConnection(CodeResourceUtil.URL, CodeResourceUtil.USERNAME, CodeResourceUtil.PASSWORD); 
  }
  public List<String> getTables() throws SQLException {
    Connection con = getConnection();
    PreparedStatement ps = con.prepareStatement(this.SQLTables);
    ResultSet rs = ps.executeQuery();
    List list = new ArrayList();
    while (rs.next()) {
      String tableName = rs.getString(1);
      list.add(tableName);
    }
    rs.close();
    ps.close();
    con.close();
    return list;
  }

  /**
   * 获得数据表 字段模型集合
   * @Title: getColumnDatas
   * @Description: 
   * @param tableName
   * @return
   * @throws SQLException List<ColumnData>
   * @author 赵汉江
   * @date 2014-1-22 上午10:17:10
   * @version V1.0
   */
  public DBTableModel getColumnDatas(String tableName,String dbname)
    throws SQLException
  {
    String SQLColumns = "select column_name,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable,column_key from information_schema.columns where table_name =  '" + tableName + "' " + "and table_schema =  '" + dbname + "'";

    Connection con = getConnection();
    PreparedStatement ps = con.prepareStatement(SQLColumns);
    List<ColumnData> columnList = new ArrayList<ColumnData>();
    ResultSet rs = ps.executeQuery();
    StringBuffer str = new StringBuffer();
    StringBuffer getset = new StringBuffer();
    String keytype = null;
    while (rs.next()) {
      String name = rs.getString(1);
      String type = rs.getString(2);
      String comment = rs.getString(3);
      String precision = rs.getString(4);
      String scale = rs.getString(5);
      String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
      String nullable = TableConvert.getNullAble(rs.getString(7));
      String key = rs.getString(8);
      type = getType(type, precision, scale,key);
      
      //获得主键类型
      if("PRI".equalsIgnoreCase(key))
    	  keytype = type;
      
      ColumnData cd = new ColumnData();
      cd.setColumnName(name);
      cd.setDataType(type);
      cd.setColumnType(rs.getString(2));
      cd.setColumnComment(comment);
      cd.setPrecision(precision);
      cd.setScale(scale);
      cd.setCharmaxLength(charmaxLength);
      cd.setNullable(nullable);
      cd.setKey(key);
      formatFieldClassType(cd);
      columnList.add(cd);
    }
    this.argv = str.toString();
    this.method = getset.toString();
    rs.close();
    ps.close();
    con.close();
    
    DBTableModel dbm = new DBTableModel(keytype, columnList);
    
    return dbm;
  }

  /**
   * 获得主键类型
   * @Title: getKeytype
   * @Description: 
   * @param tableName
   * @return
   * @throws SQLException String
   * @author 赵汉江
   * @date 2014-1-22 上午10:27:44
   * @version V1.0
   */
  public String getKeytype(String tableName,String dbname) throws SQLException{
	  DBTableModel dbm = getColumnDatas(tableName,dbname);
	  return dbm.getKeytype();
  }
  
   /** 
	* 【获得接口类型注释】(这里用一句话描述这个方法的作用)
	* @param tableName
	* @param dbname
	* @param actionType
	* @param isPage 是否分页
	* @param onlyPRI 是否只需要主键
	* @return
	* @throws SQLException  
	*/
	public String getImplActionAnnotation(String tableName,String dbname,String actionType,boolean isPage,boolean onlyPRI) throws SQLException{
	  StringBuffer str = new StringBuffer();
	  if(FtlDef.ACTION_TYPE_IMPL.equals(actionType)){
		  DBTableModel dbm = getColumnDatas(tableName,dbname);
		  List<ColumnData> dataList = dbm.getCollist();
//	    	str.append("* @param sessionKey").append("\r\t");
//	    	str.append("*         <p>会话key</p>").append("\r\t");
//	    	str.append("* @param s").append("\r\t");
//	    	str.append("* 		<p>当前用户session</p>").append("\r\t");
//	    	str.append("* @param map").append("\r\t");
	    	str.append("* <table border=1 cellspacing=0>").append("\r\t");
	    	str.append("*   <tr><th>参数</th><th>参数名</th><th>类型</th><th>可为空</th><th>限长</th><th>备注</th></tr>").append("\r\t");
		    for (ColumnData d : dataList) {
		        String name = d.getVoColumnName();
		        String type = d.getDataType();
		        String comment = d.getColumnComment();
		        String charmaxLength = d.getCharmaxLength();
		        String nullable = d.getNullable();
		        String key = d.getKey();
		        
		        if(onlyPRI){
		        	if("PRI".equalsIgnoreCase(key))
		        		str.append("*   <tr><td>").append(name).append("</td><td>").append(comment).append("</td><td>").append(type).append("</td><td>").append(nullable).append("</td><td>").append(charmaxLength).append("</td><td>").append(comment).append("</td></tr>").append("\r\t");
		        }
		        else
		        	str.append("*   <tr><td>").append(name).append("</td><td>").append(comment).append("</td><td>").append(type).append("</td><td>").append(nullable).append("</td><td>").append(charmaxLength).append("</td><td>").append(comment).append("</td></tr>").append("\r\t");
		      }
		    if(isPage){
		    	str.append("*   <tr><td>nowPage</td><td>当前页</td><td>Integer</td><td></td><td></td><td>当前页</td></tr>").append("\r\t");
		    	str.append("*   <tr><td>pageSize</td><td>每页显示条数</td><td>Integer</td><td></td><td></td><td>每页显示条数</td></tr>").append("\r\t");
		    	str.append("*   <tr><td>orderField</td><td>排序字段</td><td>String</td><td></td><td></td><td>排序字段</td></tr>").append("\r\t");
		    	str.append("*   <tr><td>orderDirection</td><td>升</td><td>boolean</td><td></td><td></td><td>升</td></tr>").append("\r\t");
		    }
	    	str.append("* </table>").append("\r\t");
//	    	str.append("* ").append("\r\t");
//	    	str.append("* @return").append("\r\t");
//	    	str.append("* 		<p>根据公共参数判断成功/失败</p>").append("\r\t");
//	    	str.append("* @throws ActionException").append("\r\t");
	  }
	return str.toString();
  }
	
	/** 
	* 【jsp表单验证】(这里用一句话描述这个方法的作用)
	* @param dataList
	* @return  
	*/
	public String jspValidate(List<ColumnData> dataList){
		StringBuffer s1 = new StringBuffer();
		StringBuffer s2 = new StringBuffer();
		for (int i = 0; null != dataList && i < dataList.size(); i++) {
			ColumnData d = dataList.get(i);
			if("PRI".equalsIgnoreCase(d.getKey())) continue;
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			if(null != d.getCharmaxLength() && d.getCharmaxLength().trim().length() > 0){
				if(sb1.toString().length() > 0) {sb1.append(",");sb2.append(",");}
				sb1.append("rangelength:[0,"+d.getCharmaxLength()+"]");
				sb2.append("rangelength:\"最大长度"+d.getCharmaxLength()+"\"");
			}
			if("N".equals(d.getNullable())){
				if(sb1.toString().length() > 0) {sb1.append(",");sb2.append(",");}
				sb1.append("required:true");
				sb2.append("required:\"请输入\"");
			}
			if("java.lang.Integer".equals(d.getDataType()) || "java.lang.Long".equals(d.getDataType()) || "java.lang.Double".equals(d.getDataType())){
				if(sb1.toString().length() > 0) {sb1.append(",");sb2.append(",");}
				sb1.append("number:true");
				sb2.append("number:\"只能是数字\"");
			}
			if(sb1.toString().length() > 0){
				if(s1.toString().length() > 0) {{s1.append(",").append("\r\t");s2.append(",").append("\r\t");}};
				s1.append(d.getVoColumnName()).append(":{").append(sb1.toString()).append("}");
				s2.append(d.getVoColumnName()).append(":{").append(sb2.toString()).append("}");
			}
		}
		StringBuffer s = new StringBuffer();
		if(s1.toString().length() > 0){
			s.append("$('#editForm_id').validate({").append("\r\t");
			s.append("rules:{").append("\r\t");
			s.append(s1);
			s.append("\r\t").append("},").append("\r\t");
			s.append("messages: {").append("\r\t");
			s.append(s2);
			s.append("\r\t").append("}").append("\r\t");
			s.append("});").append("\r\t");
		}
		return s.toString();
	}
  
  /**
   * 获得数据库表字段 并组装get set方法
   * @Title: getBeanFeilds
   * @Description: 
   * @param tableName
   * @return
   * @throws SQLException String
   * @author 赵汉江
   * @date 2014-1-22 上午10:02:43
   * @version V1.0
   */
  public String getBeanFeilds(String tableName,String dbname) throws SQLException{
    DBTableModel dbm = getColumnDatas(tableName,dbname);
    List<ColumnData> dataList = dbm.getCollist();
    StringBuffer str = new StringBuffer();
    StringBuffer getset = new StringBuffer();
    StringBuffer toStr	= new StringBuffer();
    StringBuffer preStr	= new StringBuffer("\"[\" + ");
    toStr.append("\r\tpublic String toString() {\r\t    ").append("return ");
//    preStr.append("\"id").append(":\" + getId()").append("+\",\" +");
    int i = 0;
    for (ColumnData d : dataList) {
//    if("PRI".equalsIgnoreCase(d.getKey()))continue;//放弃主键
//    if (name.equals("id")) continue;
      String name = d.getVoColumnName();
      String type = d.getDataType();
      String comment = d.getColumnComment();

      String maxChar = name.substring(0, 1).toUpperCase();
      if("PRI".equalsIgnoreCase(d.getKey()) && name.equals("id")){//字段名为id的主键不生成属性
    	  
      }else{
    	  str.append("\r\t").append("/** ").append(comment).append(" */").append("\r\t").append("private ").append(type + " ").append(name).append(";");
      }
//      str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
      String method = maxChar + name.substring(1, name.length());
      getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
      getset.append("    return this.").append(name).append(";\r\t}");
      getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
      getset.append("    this." + name + "=").append(name).append(";\r\t}");
      preStr.append("\"").append(name).append(":\" + get").append(method).append("() +");
      if (i < dataList.size()-1) preStr.append("\",\" + ");
      i++;
    }
    toStr.append(preStr.toString()).append("\"]\";\r\t}");
    this.argv = str.toString();
    this.method = getset.toString();
    return this.argv + this.method + toStr.toString();
  }
  
  /**
   * 获得数据库表字段 并组装get set方法
   * @Title: getBeanFeilds
   * @Description: 
   * @param tableName
   * @return
   * @throws SQLException String
   * @author 赵汉江
   * @date 2014-1-22 上午10:02:43
   * @version V1.0
   */
  public String getCsharpEntityFeilds(String tableName,String dbname) throws SQLException{
	  DBTableModel dbm = getColumnDatas(tableName,dbname);
	  List<ColumnData> dataList = dbm.getCollist();
	  StringBuffer str = new StringBuffer();
	  for (ColumnData d : dataList) {
		  String name = d.getVoColumnName();
		  String type = d.getDataType();
		  String comment = d.getColumnComment();
		  str.append("    /// <summary>").append("\r\t");
		  str.append("    /// ").append(comment).append("\r\t");
		  str.append("    /// </summary>").append("\r\t");
		  str.append("    [DataMember]").append("\r\t");
		  str.append("    public ").append(type.indexOf(".") > -1 ? type.substring(type.lastIndexOf(".")+1, type.length()).toLowerCase() :  type.toLowerCase()).append(" ").append(name).append(" { get; set; }").append("\r\t");
		  
	  }
	  return str.toString();
  }

  private void formatFieldClassType(ColumnData columnt)
  {
    String fieldType = columnt.getColumnType();
    String scale = columnt.getScale();

    if ("N".equals(columnt.getNullable())) {
      columnt.setOptionType("required:true");
    }
    if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
      columnt.setClassType("easyui-datetimebox");
    } else if ("date".equals(fieldType)) {
      columnt.setClassType("easyui-datebox");
    } else if ("int".equals(fieldType)) {
      columnt.setClassType("easyui-numberbox");
    } else if ("number".equals(fieldType)) {
      if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
        columnt.setClassType("easyui-numberbox");
        if (StringUtils.isNotBlank(columnt.getOptionType()))
          columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
        else
          columnt.setOptionType("precision:2,groupSeparator:','");
      }
      else {
        columnt.setClassType("easyui-numberbox");
      }
    } else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
      columnt.setClassType("easyui-numberbox");
      if (StringUtils.isNotBlank(columnt.getOptionType()))
        columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
      else
        columnt.setOptionType("precision:2,groupSeparator:','");
    }
    else {
      columnt.setClassType("easyui-validatebox");
    }
  }

  /**
   * 转换字段类型为java类型
   * @Title: getType
   * @Description: 
   * @param dataType
   * @param precision
   * @param scale
   * @return String
   * @author 赵汉江
   * @date 2014-1-22 上午10:19:55
   * @version V1.0
   */
  public String getType(String dataType, String precision, String scale,String key)
  {
    dataType = dataType.toLowerCase();
    if (dataType.contains("char") || dataType.contains("text"))
      dataType = "java.lang.String";
    else if (dataType.contains("bigint"))
    	dataType = "java.lang.Long";
    else if (dataType.contains("int")){
    	if("PRI".equalsIgnoreCase(key))
    		dataType = "java.lang.Long";
    	else
    		dataType = "java.lang.Integer";
    }
    else if (dataType.contains("float"))
      dataType = "java.lang.Float";
    else if (dataType.contains("double"))
      dataType = "java.lang.Double";
    else if (dataType.contains("number")) {
      if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
    	  dataType = "java.lang.Double";//dataType = "java.math.BigDecimal";
      else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
        dataType = "java.lang.Long";
      else if("PRI".equalsIgnoreCase(key))
  		dataType = "java.lang.Long";
      else
        dataType = "java.lang.Integer";
    }
    else if (dataType.contains("decimal"))
      dataType = "java.math.BigDecimal";
    else if (dataType.contains("date"))
      dataType = "java.util.Date";
    else if (dataType.contains("time"))
      dataType = "java.util.Date";
    else if (dataType.contains("clob"))
      dataType = "java.sql.Clob";
    else {
      dataType = "java.lang.Object";
    }
    return dataType;
  }
  public void getPackage(int type, String createPath, String content, String packageName, String className, String extendsClassName, String[] importName) throws Exception {
    if (packageName == null) {
      packageName = "";
    }
    StringBuffer sb = new StringBuffer();
    sb.append("package ").append(packageName).append(";\r");
    sb.append("\r");
    for (int i = 0; i < importName.length; i++) {
      sb.append("import ").append(importName[i]).append(";\r");
    }
    sb.append("\r");
    sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
    sb.append("\r");
    sb.append("\rpublic class ").append(className);
    if (extendsClassName != null) {
      sb.append(" extends ").append(extendsClassName);
    }
    if (type == 1)
      sb.append(" ").append("implements java.io.Serializable {\r");
    else {
      sb.append(" {\r");
    }
    sb.append("\r\t");
    sb.append("private static final long serialVersionUID = 1L;\r\t");
    String temp = className.substring(0, 1).toLowerCase();
    temp = temp + className.substring(1, className.length());
    if (type == 1) {
      sb.append("private " + className + " " + temp + "; // entity ");
    }
    sb.append(content);
    sb.append("\r}");
    System.out.println(sb.toString());
    createFile(createPath, "", sb.toString());
  }

  public String getTablesNameToClassName(String tableName)
  {
    String[] split = tableName.split("_");
    if (split.length > 1) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < split.length; i++) {
        String tempTableName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
        sb.append(tempTableName);
      }

      return sb.toString();
    }
    String tempTables = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
    return tempTables;
  }

  public void createFile(String path, String fileName, String str)
    throws IOException
  {
    FileWriter writer = new FileWriter(new File(path + fileName));
    writer.write(new String(str.getBytes("utf-8")));
    writer.flush();
    writer.close();
  }

  public Map<String, Object> getAutoCreateSql(String tableName,String dbname)
    throws Exception
   {
    Map sqlMap = new HashMap();
    DBTableModel dbm = getColumnDatas(tableName,dbname);
    List<ColumnData> columnDatas = dbm.getCollist();
    String columns = getColumnSplit(columnDatas);
    String idcolumns = getIdColumnSplit(columnDatas);
    String vocolumns = getVoColumnSplit(columnDatas);	//VO field name
    String idvocolumns = getIdVoColumnSplit(columnDatas);
    String[] columnList = getColumnList(columns);
    String[] idcolumnList = getColumnList(idcolumns);
    String[] vocolumnList = getColumnList(idvocolumns);
    String columnFields = getColumnFields(idcolumns);
    String insert = "insert into " + tableName + "(" + idcolumns.replaceAll("\\|", ",") + ")\n values(#{" + idvocolumns.replaceAll("\\|", "},#{") + "})";
    String insertList = "insert into " + tableName + "(" + idcolumns.replaceAll("\\|", ",") + ") values";
    String update = getUpdateSql(tableName, idcolumnList);
    String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
    String selectiveSql = getSelectiveSql(tableName, columnDatas);
    String selectById = getSelectByIdSql(tableName, idcolumnList);
    String delete = getDeleteSql(tableName, idcolumnList);
    sqlMap.put("columnList", columnList);
    sqlMap.put("columnFields", columnFields);
//    sqlMap.put("insert", insert.replace("#{crttm}", "now()").replace("#{updtm}", "now()"));
    sqlMap.put("insert", insert);
    sqlMap.put("insertListHead", insertList);
//    sqlMap.put("insertListClum", getInsertCloums(vocolumnList).replace("#{item.crttm}", "now()").replace("#{item.updtm}", "now()"));
    sqlMap.put("insertListClum", getInsertCloums(vocolumnList));
//    sqlMap.put("update", update.replace("#{updtm}", "now()"));
    sqlMap.put("update", update);
    sqlMap.put("delete", delete);
    sqlMap.put("deleteList", getDeleteIdsSql(tableName, idcolumnList));
//    sqlMap.put("updateSelective", updateSelective.replace("#{updtm}", "now()"));
    sqlMap.put("updateSelective", updateSelective);
    sqlMap.put("selectiveSql", selectiveSql);
    sqlMap.put("selectById", selectById);
    return sqlMap;
  }
  
  public String getInsertCloums(String[] columnsList)
  {
	  StringBuffer sb = new StringBuffer();
	  int j = 0;
	  for(int i = 0;i<columnsList.length;i++)
	  {
		  String id = columnsList[i];
//		  if (!id.equals("id")) {
			  if(j > 0) sb.append(",");
			  sb.append("#{item.").append(columnsList[i]).append("}");
			  j++;
//		  }
		  
	  }
	  return sb.toString();
  }
  
  public String getDeleteIdsSql(String tableName, String[] columnsList)
  {
	  StringBuffer sb = new StringBuffer();
	    sb.append("delete ");
	    sb.append("\t from ").append(tableName).append(" where ");
	    sb.append(columnsList[0]).append(" in ");
	    return sb.toString();
  }
  public String getDeleteSql(String tableName, String[] columnsList)
    throws SQLException
  {
    StringBuffer sb = new StringBuffer();
    sb.append("delete ");
    sb.append("\t from ").append(tableName).append(" where ");
    sb.append(columnsList[0]).append(" = #{").append(columnsList[0]).append("}");
    return sb.toString();
  }

  public String getSelectByIdSql(String tableName, String[] columnsList)
    throws SQLException
  {
    StringBuffer sb = new StringBuffer();
    sb.append("select <include refid=\"Base_Column_List\" /> \n");
    sb.append("\t from ").append(tableName).append(" t where ");
    sb.append("t.").append(columnsList[0]).append(" = #{").append(this.getVoCn(columnsList[0])).append("}");
    return sb.toString();
  }

  public String getColumnFields(String columns)
    throws SQLException
  {
    String fields = columns;
    if ((fields != null) && (!"".equals(fields))) {
      fields = fields.replaceAll("[|]", ",t.");
    }
    fields = fields != null && fields.trim().length() > 0 ? "t."+fields : fields;
    return fields;
  }

  public String[] getColumnList(String columns)
    throws SQLException
  {
    String[] columnList = columns.split("[|]");
    return columnList;
  }

  public String getUpdateSql(String tableName, String[] columnsList)
    throws SQLException
  {
    StringBuffer sb = new StringBuffer();

    for (int i = 1; i < columnsList.length; i++) {
      String column = columnsList[i];
      if (!"CRTTM".equals(column.toUpperCase()) && !"CRTCDE".equals(column.toUpperCase()))
      {
//        if ("UPDTM".equals(column.toUpperCase()))
//          sb.append(column + "=now()");
//        else {
//          sb.append(column + "=#{" + getVoCn(column) + "}");
//        }
    	  sb.append(column + "=#{" + getVoCn(column) + "}");
        if (i + 1 < columnsList.length)
          sb.append(",");
      }
    }
    String update = "update " + tableName + " set " + sb.toString() + " where " + columnsList[0] + "=#{" + columnsList[0] + "}";
    return update;
  }
  
  private String getVoCn(String column) {
		String result = "";
		String [] strs = column.split("\\_");
		int length = strs.length;
		for (int i = 0 ; i < length ; i++) {
			String istr = strs[i];
			if (i > 0) {
				istr = istr.substring(0, 1).toUpperCase() + istr.substring(1);
			}
			result += istr;
		}
		return result;
	}

  public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
    StringBuffer sb = new StringBuffer();
    ColumnData cd = (ColumnData)columnList.get(0);
    sb.append("\t<trim  suffixOverrides=\",\" >\n");
    for (int i = 1; i < columnList.size(); i++) {
      ColumnData data = (ColumnData)columnList.get(i);
      String columnName = data.getColumnName();
      String vocolumnName = data.getVoColumnName();
      if ("CRTCDE".equals(vocolumnName.toUpperCase()) || "CRTTM".equals(vocolumnName.toUpperCase())) continue;
      sb.append("\t<if test=\"").append(vocolumnName).append(" != null ");

      String dataType = data.getDataType();
      dataType = dataType != null ? dataType.toLowerCase() : dataType;
      if ("string".equals(dataType) || "java.lang.string".equals(dataType) ) {
        sb.append(" and ").append(vocolumnName).append(" != ''");
      }
      sb.append(" \">\n\t\t");
      sb.append(columnName + "=#{" + vocolumnName + "},\n");
      sb.append("\t</if>\n");
    }
    sb.append("\t</trim>");
    String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{" + cd.getVoColumnName() + "}";
    return update;
  }
  
//  <if test="supSku != null  ">
//	sup_sku=#{supSku},
//</if>
//	<if test="supSku != null and supSku != ''  ">
//	and sup_sku=#{supSku}
//</if>
  
  public String getSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
	    StringBuffer sb = new StringBuffer();
	    ColumnData cd = (ColumnData)columnList.get(0);
	    for (int i = 0; i < columnList.size(); i++) {
	      ColumnData data = (ColumnData)columnList.get(i);
	      String columnName = data.getColumnName();
	      String vocolumnName = data.getVoColumnName();
	      sb.append("\t<if test=\"").append(vocolumnName).append(" != null ");
	      String dataType = data.getDataType();
	      dataType = dataType != null ? dataType.toLowerCase() : dataType;
	      if ("string".equals(dataType) || "java.lang.string".equals(dataType) ) {
	          sb.append(" and ").append(vocolumnName).append(" != ''");
	        }
	      sb.append(" \">\n\t\t");
	      sb.append(" and t.").append(columnName + "=#{" + vocolumnName + "}\n");
	      sb.append("\t</if>\n");
	    }
	    return sb.toString();
	  }

  public String getColumnSplit(List<ColumnData> columnList)
    throws SQLException
  {
    StringBuffer commonColumns = new StringBuffer();
    for (ColumnData data : columnList) {
      if (null != data.getColumnName() && !data.getColumnName().equals("id")) {
        commonColumns.append(data.getColumnName() + "|");
      }
    }
    return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
  }
  
  public String getIdColumnSplit(List<ColumnData> columnList)
		    throws SQLException
  {
    StringBuffer commonColumns = new StringBuffer();
    for (ColumnData data : columnList) {
        commonColumns.append(data.getColumnName() + "|");
    }
    return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
  }
  
  public String getVoColumnSplit(List<ColumnData> columnList)
    throws SQLException
  {
    StringBuffer commonColumns = new StringBuffer();
    for (ColumnData data : columnList) {
      if (null != data.getVoColumnName() && !data.getVoColumnName().equals("id")) {
    	commonColumns.append(data.getVoColumnName() + "|");
      }
    }
    return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
  }
  public String getIdVoColumnSplit(List<ColumnData> columnList)
		    throws SQLException
  {
    StringBuffer commonColumns = new StringBuffer();
    for (ColumnData data : columnList) {
      if (null != data.getVoColumnName()) {
    	commonColumns.append(data.getVoColumnName() + "|");
      }
    }
    return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
  }
}