/*    */ package codeGenerate.def;
/*    */ 
/*    */ public class TableConvert
/*    */ {
/*    */   public static String getNullAble(String nullable)
/*    */   {
/* 12 */     if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable))) {
/* 13 */       return "Y";
/*    */     }
/* 15 */     if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable))) {
/* 16 */       return "N";
/*    */     }
/* 18 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Documents and Settings\Administrator\桌面\jeecg-mybatis-generate.jar
 * Qualified Name:     codeGenerate.def.TableConvert
 * JD-Core Version:    0.6.2
 */