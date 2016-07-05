/*    */ package com.allearn.autocode;
/*    */ 
/*    */ import codeGenerate.factory.CodeGenerateFactory;
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.OutputStreamWriter;
/*    */ import java.util.Properties;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.velocity.Template;
/*    */ import org.apache.velocity.VelocityContext;
/*    */ import org.apache.velocity.app.VelocityEngine;
/*    */ 
/*    */ public class CommonPageParser
/*    */ {
/*    */   private static VelocityEngine ve;
/*    */   private static final String CONTENT_ENCODING = "UTF-8";
/* 33 */   private static final Log log = LogFactory.getLog(CommonPageParser.class);
/*    */ 
/* 35 */   private static boolean isReplace = true;
/*    */ 
/*    */   static
/*    */   {
/*    */     try
/*    */     {
///* 42 */       String templateBasePath = CodeGenerateFactory.getProjectPath() + "/jeecg/template";
	  			String templateBasePath = CommonPageParser.class.getClassLoader().getResource("").getPath() + "/jeecg/template";
System.out.println(templateBasePath);
/* 43 */       Properties properties = new Properties();
/* 44 */       properties.setProperty("resource.loader", "file");
/* 45 */       properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
/* 46 */       properties.setProperty("file.resource.loader.path", templateBasePath);
/* 47 */       properties.setProperty("file.resource.loader.cache", "true");
/* 48 */       properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
/* 49 */       properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
/* 50 */       properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
/* 51 */       properties.setProperty("directive.set.null.allowed", "true");
/* 52 */       VelocityEngine velocityEngine = new VelocityEngine();
/* 53 */       velocityEngine.init(properties);
/* 54 */       ve = velocityEngine;
/*    */     } catch (Exception e) {
/* 56 */       log.error(e);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void WriterPage(VelocityContext context, String templateName, String fileDirPath, String targetFile)
/*    */   {
/*    */     try
/*    */     {
/* 71 */       File file = new File(fileDirPath + targetFile);
/* 72 */       if (!file.exists()) {
/* 73 */         new File(file.getParent()).mkdirs();
/*    */       }
/* 75 */       else if (isReplace) {
/* 76 */         log.info("替换文件:" + file.getAbsolutePath());
/*    */       }
/*    */ 
/* 81 */       Template template = ve.getTemplate(templateName, "UTF-8");
/* 82 */       FileOutputStream fos = new FileOutputStream(file);
/* 83 */       BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
/* 84 */       template.merge(context, writer);
/* 85 */       writer.flush();
/* 86 */       writer.close();
/* 87 */       fos.close();
/* 88 */       log.info("生成文件：" + file.getAbsolutePath());
/*    */     } catch (Exception e) {
/* 90 */       log.error(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\jeecg-mybatis-generate.jar
 * Qualified Name:     codeGenerate.CommonPageParser
 * JD-Core Version:    0.6.2
 */