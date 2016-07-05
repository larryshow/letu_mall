/*     */ package com.allearn.autocode;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileWriter;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.dom4j.Document;
/*     */ import org.dom4j.Element;
/*     */ import org.dom4j.io.OutputFormat;
/*     */ import org.dom4j.io.SAXReader;
/*     */ import org.dom4j.io.XMLWriter;
/*     */ 
/*     */ public class WolfXmlUtil
/*     */ {
/*     */   private void getAddStrutsElemant(String filePath, String nodexPath)
/*     */     throws Exception
/*     */   {
/*  22 */     Document document = getPath(filePath, "utf-8");
/*  23 */     Element element = document.getRootElement();
/*  24 */     Element nextElement = element.element("package");
/*  25 */     Element newElement = nextElement.addElement("action");
/*  26 */     newElement.addComment("系统自动创建");
/*  27 */     newElement.addAttribute("name", "test");
/*  28 */     newElement.addAttribute("class", "");
/*  29 */     newElement.addAttribute("method", "");
/*  30 */     newElement.addText("hello");
/*     */   }
/*     */ 
/*     */   public void getAddNode(String filePath, String xPath, String newNode, Map<String, String> attrMap, String text)
/*     */     throws Exception
/*     */   {
/*  43 */     if (getQueryNode(filePath, xPath, newNode, attrMap, text) < 1) {
/*  44 */       Document document = getPath(filePath, "UTF-8");
/*  45 */       List list = document.selectNodes(xPath);
/*  46 */       System.out.println(xPath);
/*  47 */       Element element = (Element)list.get(0);
/*  48 */       Element newElement = element.addElement(newNode);
/*  49 */       for (Map.Entry entry : attrMap.entrySet()) {
/*  50 */         newElement.addAttribute((String)entry.getKey(), (String)entry.getValue());
/*     */       }
/*  52 */       if ((text != null) && (text.trim().length() > 0)) {
/*  53 */         newElement.addText(text);
/*     */       }
/*  55 */       getXMLWrite(document, filePath);
/*  56 */       System.out.println("修改" + xPath + "成功");
/*     */     } else {
/*  58 */       System.out.println("已添");
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getQueryNode(String filePath, String xPath, String newNode, Map<String, String> attrMap, String text)
/*     */     throws Exception
/*     */   {
/*  84 */     int count = 0;
/*  85 */     Document document = getPath(filePath, "UTF-8");
/*  86 */     StringBuffer sb = new StringBuffer();
/*  87 */     for (Map.Entry entry : attrMap.entrySet()) {
/*  88 */       sb.append("[@" + (String)entry.getKey() + "='" + (String)entry.getValue() + "']");
/*     */     }
/*  90 */     xPath = xPath + "/" + newNode + sb.toString();
/*  91 */     System.out.println("xPath=" + xPath);
/*  92 */     document.selectNodes(xPath);
/*  93 */     List list = document.selectNodes(xPath);
/*  94 */     for (int i = 0; i < list.size(); i++) {
/*  95 */       Element element = (Element)list.get(i);
/*  96 */       if (element.getText().equals(text)) {
/*  97 */         count++;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 102 */     return count;
/*     */   }
/*     */ 
/*     */   public void getXMLWrite(Document document, String filePath)
/*     */     throws Exception
/*     */   {
/* 116 */     OutputFormat of = new OutputFormat(" ", true);
/* 117 */     of.setEncoding("UTF-8");
/* 118 */     XMLWriter xw = new XMLWriter(new FileWriter(filePath), of);
/* 119 */     xw.setEscapeText(false);
/* 120 */     xw.write(document);
/* 121 */     xw.close();
/* 122 */     System.out.println(document.asXML());
/*     */   }
/*     */ 
/*     */   public void getEditNode(String filePath, String xPath, Map<String, String> attrMap, String text) throws Exception {
/* 126 */     Document document = getPath(filePath, "UTF-8");
/* 127 */     List list = document.selectNodes(xPath);
/* 128 */     Element element = (Element)list.get(0);
/* 129 */     if (attrMap != null) {
/* 130 */       for (Map.Entry entry : attrMap.entrySet()) {
/* 131 */         element.addAttribute((String)entry.getKey(), (String)entry.getValue());
/*     */       }
/*     */     }
/*     */ 
/* 135 */     List nodelist = element.elements();
/* 136 */     for (int i = 0; i < nodelist.size(); i++) {
/* 137 */       Element nodeElement = (Element)nodelist.get(i);
/* 138 */       nodeElement.getParent().remove(nodeElement);
/*     */     }
/* 140 */     element.setText(text);
/* 141 */     getXMLWrite(document, filePath);
/*     */   }
/*     */ 
/*     */   public Document getPath(String filePath, String coding)
/*     */   {
/* 154 */     SAXReader saxReader = new SAXReader();
/*     */ 
/* 156 */     Document document = null;
/*     */     try {
/* 158 */       saxReader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
/* 159 */       BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), coding));
/* 160 */       document = saxReader.read(read);
/*     */     } catch (Exception e) {
/* 162 */       e.printStackTrace();
/*     */     }
/* 164 */     return document;
/*     */   }
/*     */   public static void main(String[] args) {
/* 167 */     WolfXmlUtil xml = new WolfXmlUtil();
/* 168 */     String filePath1 = "D:\\MyEclipse 8.5\\ssi\\src\\com\\wei\\ssi\\conf\\sqlmap\\ProUserSQL.xml";
/* 169 */     String filePath = "D:\\MyEclipse 8.5\\ssi\\src\\com\\wei\\ssi\\conf\\struts2\\struts2-ssi-proWbType.xml";
/*     */     try
/*     */     {
/* 176 */       Map map = new HashMap();
/* 177 */       map.put("file", "no");
/* 178 */       xml.getEditNode(filePath1, "/sqlMap/select[@id='getProUserList']", map, "嘿嘿");
/*     */     } catch (Exception e) {
/* 180 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\jeecg-mybatis-generate.jar
 * Qualified Name:     codeGenerate.WolfXmlUtil
 * JD-Core Version:    0.6.2
 */