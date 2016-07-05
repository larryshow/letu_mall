 package codeGenerate.factory;
 
  import codeGenerate.def.ClassName;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FtlDef;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.allearn.autocode.ColumnData;
import com.allearn.autocode.CommonPageParser;
import com.allearn.autocode.CreateBean;
import com.allearn.autocode.DBTableModel;
import com.allearn.autocode.util.FifteenLongId;
 
 public class CodeGenerateFactory
 {
   private static final Log log = LogFactory.getLog(CodeGenerateFactory.class);
 
   public static ClassName codeGenerate(String tableName, String codeName, String entityPackage, String keyType,String bussi_package,String outputBasepath, 
		   String author,String actionType,String company){
	   if(null == actionType || actionType.trim().length() == 0)
		   actionType = FtlDef.ACTION_TYPE_WEB;
	   ClassName cn = new ClassName();
	     CreateBean createBean = new CreateBean();
	     String className = createBean.getTablesNameToClassName(tableName);
	     String lowerName = className.substring(0, 1).toLowerCase() + className.substring(1, className.length());
	 
	     String srcPath = outputBasepath + CodeResourceUtil.source_root_package + "\\";
	 
	     String pckPath = srcPath + (bussi_package == null ? "" : bussi_package.replace(".", "\\")) + "\\";
	 
	     String webPath = outputBasepath + CodeResourceUtil.web_root_package + "\\view\\" + (bussi_package == null ? "" : bussi_package.replace(".", "\\")) + "\\";
	 
	     String modelPath = entityPackage + "\\" + "page\\" +  className + "Page.java";
	     String beanPath = entityPackage + "\\" +"vo\\" +  className + ".java";
	     String mapperPath = entityPackage + "\\" +"dao\\" +  className + "Dao.java";
	     String servicePath = entityPackage + "\\" +"service\\" +  className + "Service.java";
		 String serviceImplPath = entityPackage + "\\" +"service\\impl\\" + className + "ServiceImpl.java";
		 String daoImplPath = entityPackage + "\\" +"dao\\impl\\" + className + "DaoImpl.java";
	     String controllerPath = entityPackage + "\\" +"action\\" +  className + "Action.java";
	     String listJspPath = "WEB-INF\\jsp\\" +entityPackage + "\\" +  lowerName + "_list.jsp";
	     String inputJspPath = "WEB-INF\\jsp\\" +entityPackage + "\\" +  lowerName + "_input.jsp";
	     String sqlMapperPath = "mapper\\" +  className + "Mapper.xml";
	     String csharpEntityPath = "csharp\\" +  className + ".cs";
	     webPath = webPath + entityPackage + "\\";
	 
//	     String jspPath = lowerName + ".jsp";
//	     String jsPath = "page-" + lowerName + ".js";
	 
	     VelocityContext context = new VelocityContext();
	     Date date = new Date();
	     SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
	     SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");
	     //类名
	     context.put("className", className);
	     //-------------------------------------------------------------------	     
	     //author
	     context.put("author", author);
	     context.put("yyyy", sdfy.format(date));
	     context.put("yyyyMMdd", sdfymd.format(date));
	     //-------------------------------------------------------------------
	     //company
	     context.put("company", company);
	     //------------------------------------------------------------------- 
	
	     //首字母小写的类名
	     context.put("lowerName", lowerName);
	     
	     context.put("codeName", codeName);
	     //数据库表名
	     context.put("tableName", tableName);
	     //根包名
	     context.put("bussPackage", bussi_package);
	     //模块包名
	     context.put("entityPackage", entityPackage);
	     //主键类型
	     context.put("keyType", keyType);
	     
	     FifteenLongId id = new FifteenLongId(2);
	     context.put("vo_serialVersionUID", id.nextId());
	     context.put("page_serialVersionUID", id.nextId());
	     //数据库字段的get set方法
	     try
	     {
	     context.put("feilds", createBean.getBeanFeilds(tableName,CodeResourceUtil.DATABASE_NAME));
	     context.put("CsharpFeilds", createBean.getCsharpEntityFeilds(tableName,CodeResourceUtil.DATABASE_NAME));
	     context.put("keytype", createBean.getKeytype(tableName,CodeResourceUtil.DATABASE_NAME));
	     context.put("implActionAnnotation", createBean.getImplActionAnnotation(tableName,CodeResourceUtil.DATABASE_NAME,actionType,false,false));
	     context.put("implPageActionAnnotation", createBean.getImplActionAnnotation(tableName,CodeResourceUtil.DATABASE_NAME,actionType,true,false));
	     context.put("implPRIActionAnnotation", createBean.getImplActionAnnotation(tableName,CodeResourceUtil.DATABASE_NAME,actionType,false,true));
	     } catch (Exception e) {
	       e.printStackTrace();
	     }
	     DBTableModel dbm = null;
	     try
	     {
	       dbm = createBean.getColumnDatas(tableName,CodeResourceUtil.DATABASE_NAME); 
	       Map sqlMap = createBean.getAutoCreateSql(tableName,CodeResourceUtil.DATABASE_NAME);
	       context.put("columnDatas", dbm.getCollist());
	       context.put("SQL", sqlMap);
	       StringBuffer clums = new StringBuffer();
	       List<ColumnData> collist = dbm.getCollist();
	       for (ColumnData columnData : collist) {
	    	   
	    	   clums.append("<result column=\"").append(columnData.getColumnName()).append("\"  property=\"")
	    	   		.append(columnData.getVoColumnName()).append("\"/> \n");
	       }
	       context.put("clums",clums.toString());
	     } catch (Exception e) {
	       e.printStackTrace();
	       return null;
	     }
	 
	     CommonPageParser.WriterPage(context, "EntityTemplate.ftl", pckPath, beanPath);
	     CommonPageParser.WriterPage(context, "PageTemplate.ftl", pckPath, modelPath);
	     CommonPageParser.WriterPage(context, "DaoTemplate.ftl", pckPath, mapperPath);
	     CommonPageParser.WriterPage(context, "DaoImplTemplate.ftl", pckPath, daoImplPath);
	     CommonPageParser.WriterPage(context, "ServiceTemplate.ftl", pckPath, servicePath);
		 CommonPageParser.WriterPage(context, "ServiceImplTemplate.ftl", pckPath, serviceImplPath);
	     CommonPageParser.WriterPage(context, "MapperTemplate.xml", srcPath, sqlMapperPath);
	     if(FtlDef.ACTION_TYPE_IMPL.equals(actionType)){
	    	 CommonPageParser.WriterPage(context, "ImplActionTemplate.ftl", pckPath, controllerPath);
	    	 CommonPageParser.WriterPage(context, "CsharpEntityTemplate.ftl", srcPath, csharpEntityPath);
	     }else if(FtlDef.ACTION_TYPE_WEB.equals(actionType)){
	    	 CommonPageParser.WriterPage(context, "ControllerTemplate.ftl", pckPath, controllerPath);
	    	 context.put("jspValidate",createBean.jspValidate(dbm.getCollist()));
	    	 CommonPageParser.WriterPage(context, "listJspTemplate.ftl", srcPath, listJspPath);
	    	 CommonPageParser.WriterPage(context, "inputJspTemplate.ftl", srcPath, inputJspPath);
	     }
//	   CommonPageParser.WriterPage(context, "jspTemplate.ftl", webPath, jspPath);
//	   CommonPageParser.WriterPage(context, "jsTemplate.ftl", webPath, jsPath);
	 
	     log.info("----------------------------代码生成完毕---------------------------");
	     cn.setcName(className);
	     cn.setcPakge(bussi_package+"."+entityPackage+".vo."+cn.getcName());
	     return cn;
	   }
 
   public static String getProjectPath()
   {
     String path = System.getProperty("user.dir").replace("\\", "/") + "/";
     return path;
   }
}