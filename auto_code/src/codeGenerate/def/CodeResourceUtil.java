package codeGenerate.def;

import java.util.ResourceBundle;

public class CodeResourceUtil
{
  private static final ResourceBundle bundle = ResourceBundle.getBundle("jeecg/jeecg_database");
  private static final ResourceBundle bundlePath = ResourceBundle.getBundle("jeecg/jeecg_config");

  public static String DIVER_NAME = null;

  public static String URL = null;

  public static String USERNAME = null;

  public static String PASSWORD = null;

  public static String DATABASE_NAME = null;

  public static String DATABASE_TYPE = "mysql";
  public static String DATABASE_TYPE_MYSQL = "mysql";
  public static String DATABASE_TYPE_ORACLE = "oracle";

  public static String JEECG_UI_FIELD_REQUIRED_NUM = "4";

  public static String JEECG_UI_FIELD_SEARCH_NUM = "3";

  public static String web_root_package = "";

  public static String source_root_package = "src";


  public static String entity_package = "entity";

  public static String page_package = "page";


  public static String TEMPLATEPATH;

  public static String JEECG_GENERATE_TABLE_ID;
  public static String JEECG_GENERATE_UI_FILTER_FIELDS;
  public static String SYSTEM_ENCODING;

  static
  {
    web_root_package = web_root_package.replace(".", "/");
    DIVER_NAME = getDIVER_NAME();
    URL = getURL();
    USERNAME = getUSERNAME();
    PASSWORD = getPASSWORD();
    DATABASE_NAME = getDATABASE_NAME();

    SYSTEM_ENCODING = getSYSTEM_ENCODING();
    TEMPLATEPATH = getTEMPLATEPATH();
    source_root_package = getSourceRootPackage();
    web_root_package = getWebRootPackage();

    JEECG_GENERATE_TABLE_ID = getJeecg_generate_table_id();

    JEECG_UI_FIELD_SEARCH_NUM = getJeecg_ui_search_filed_num();

//    if ((URL.indexOf("mysql") >= 0) || (URL.indexOf("MYSQL") >= 0))
//      DATABASE_TYPE = DATABASE_TYPE_MYSQL;
//    else if ((URL.indexOf("oracle") >= 0) || (URL.indexOf("ORACLE") >= 0)) {
//      DATABASE_TYPE = DATABASE_TYPE_ORACLE;
//    }

    source_root_package = source_root_package.replace(".", "/");
  }

  private void ResourceUtil()
  {
  }

  public static final String getDIVER_NAME()
  {
    return bundle.getString("diver_name");
  }

  public static final String getURL()
  {
    return bundle.getString("url");
  }

  public static final String getUSERNAME()
  {
    return bundle.getString("username");
  }

  public static final String getPASSWORD()
  {
    return bundle.getString("password");
  }

  public static final String getDATABASE_NAME()
  {
    return bundle.getString("database_name");
  }

  private static String getBussiPackage() {
    return bundlePath.getString("bussi_package");
  }

  public static final String getEntityPackage()
  {
    return bundlePath.getString("entity_package");
  }

  public static final String getPagePackage()
  {
    return bundlePath.getString("page_package");
  }

  public static final String getTEMPLATEPATH()
  {
    return bundlePath.getString("templatepath");
  }

  public static final String getSourceRootPackage()
  {
    return bundlePath.getString("source_root_package");
  }

  public static final String getWebRootPackage()
  {
    return bundlePath.getString("webroot_package");
  }

  public static final String getSYSTEM_ENCODING()
  {
    return bundlePath.getString("system_encoding");
  }

  public static final String getJeecg_generate_table_id()
  {
    return bundlePath.getString("jeecg_generate_table_id");
  }

  public static final String getJeecg_generate_ui_filter_fields()
  {
    return bundlePath.getString("jeecg_generate_ui_filter_fields");
  }

  public static final String getJeecg_ui_search_filed_num()
  {
    return bundlePath.getString("jeecg_ui_search_filed_num");
  }

  public static final String getJeecg_ui_field_required_num()
  {
    return bundlePath.getString("jeecg_ui_field_required_num");
  }
}