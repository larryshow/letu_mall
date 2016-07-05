package com.allearn.autocode.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;

import com.allearn.autocode.CommonPageParser;

import codeGenerate.def.ClassName;
import codeGenerate.def.CodeResourceUtil;
import codeGenerate.def.FtlDef;
import codeGenerate.factory.CodeGenerateFactory;


public class CodeUtil {

	public static void main(String[] args) {
		 /** 此处修改成你的 表名 和 中文注释***/
//		String tableName="t_topic"; //
//		String codeName ="题目";//中文注释  当然你用英文也是可以的 
//		String entityPackage ="attendance";//实体包
//		String keyType = FtlDef.KEY_TYPE_01;//主键生成方式 01:UUID  02:自增

		List<String[]> params = new ArrayList<String[]>();
	    String bussi_package="com.qtz.sm";

	    String outputBasepath = "F:\\temp_code\\";
//	    params.add(new String[]{"gd_goods_desc_picture","商品描述图片","goods",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    
//	    params.add(new String[]{"gd_goods","商品","goods",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
//	    params.add(new String[]{"gd_goods","商品","goods",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
//	    params.add(new String[]{"gd_goods_operation_history","商品操作历史记录","goods",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
//		params.add(new String[] { "gd_goods_brands", "商品品牌", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_categroy_rate", "商品分类溢价率", "goods", FtlDef.KEY_TYPE_01,
//				FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_picture", "商品图片", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_sku", "商品SKU", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_sku_property", "商品SKU属性", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_sku_rate", "商品SKU溢价率", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "gd_goods_type", "商品分类", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
//		params.add(new String[] { "goods_desc", "商品描述", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
	    params.add(new String[] { "gd_goods_property_val", "商品属性实际值", "goods", FtlDef.KEY_TYPE_01, FtlDef.ACTION_TYPE_IMPL });
		
		
		/*
		 * params.add(new
		 * String[]{"wt_wallet","钱包信息","wallet",FtlDef.KEY_TYPE_01,FtlDef.
		 * ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_bank_card","银行卡信息","wallet",FtlDef.KEY_TYPE_01,FtlDef.
		 * ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_bld_income","便利店分润流水","wallet",FtlDef.KEY_TYPE_01,FtlDef
		 * .ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_bld_withdrawals","便利店提现流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_bldgl_income","便利店管理公司应收货款流水","wallet",FtlDef.
		 * KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_bldgl_withdrawals","便利店管理公司提现流水","wallet",FtlDef.
		 * KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_cs_income","超市应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef
		 * .ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_cs_withdrawals","超市提现流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_cczx_income","仓储中心分润流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_cczx_withdrawals","仓储中心提现流水","wallet",FtlDef.KEY_TYPE_01
		 * ,FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_ycc_bldgl_income","云仓储向便利店管理公司应收货款流水","wallet",FtlDef.
		 * KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_ycc_bldgl_withdrawals","云仓储向便利店管理公司提现流水","wallet",FtlDef
		 * .KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_ycc_cs_income","云仓储向超市应收货款流水","wallet",FtlDef.
		 * KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_ycc_cs_withdrawals","云仓储向超市提现流水","wallet",FtlDef.
		 * KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * params.add(new
		 * String[]{"wt_gyl_income","供应链应收货款流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_gyl_withdrawals","供应链提现流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL});
		 * 
		 * 
		 * params.add(new
		 * String[]{"wt_gys_income","供应商应收货款流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL}); params.add(new
		 * String[]{"wt_gys_withdrawals","供应商提现流水","wallet",FtlDef.KEY_TYPE_01,
		 * FtlDef.ACTION_TYPE_IMPL});
		 */
	    
	    //params.add(new String[]{"wt_bld_income","便利店分润流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    //params.add(new String[]{"wt_cczx_income","仓储中心分润流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    //params.add(new String[]{"wt_bank_card","银行卡信息","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    //params.add(new String[]{"wt_withdrawals","银行卡信息","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    
	    /*
	    params.add(new String[]{"wt_bld_withdrawals","便利店提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_bldgl_withdrawals","便利店管理公司提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_cs_withdrawals","超市提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_cczx_withdrawals","仓储中心提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_ycc_bldgl_withdrawals","云仓储向便利店管理公司提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_ycc_cs_withdrawals","云仓储向超市提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_gyl_withdrawals","供应链提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_gys_withdrawals","供应商提现流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	   */
	    
	    //params.add(new String[]{"wt_wallet","钱包信息","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    
	    /*
	    params.add(new String[]{"wt_bld_income","便利店分润流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_bldgl_income","便利店管理公司应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_cs_income","超市应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_cczx_income","仓储中心分润流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_ycc_bldgl_income","云仓储向便利店管理公司应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_ycc_cs_income","云仓储向超市应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_gyl_income","供应链应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
	    params.add(new String[]{"wt_gys_income","供应商应收货款流水","wallet",FtlDef.KEY_TYPE_01,FtlDef.ACTION_TYPE_IMPL});
		*/
	    auto2(params,bussi_package,outputBasepath, "欧江波 - 928482427@qq.com","深圳擎天柱信息技术有限公司");
	}
	/*（1）表名称，
	 * （2）中文注释，
	 * （3）实体包，
	 * （4）主键生成方式 01:UUID  02:自增， 
	 * （5）action类型 01:WEB  02:接口
	 * */
	public static void auto2(List<String[]> params,String bussi_package,String outputBasepath, String author,String company){
		List<ClassName> cList = new ArrayList<ClassName>();
		VelocityContext context = new VelocityContext();
		ClassName cn = null;
		for (int i = 0; i < params.size(); i++) {
			String[] table = params.get(i);
			String tableName=table[0]; //
			String codeName =table[1];//中文注释  当然你用英文也是可以的 
			String entityPackage =table[2];//实体包
			String keyType = table[3];//主键生成方式 01:UUID  02:自增
			String actionType = table[4];//action类型 01:WEB  02:接口
			cn = CodeGenerateFactory.codeGenerate(tableName, codeName,entityPackage,keyType,bussi_package,outputBasepath, author,actionType,company);
			if(cn == null) continue;
			cList.add(cn);
		}
		//<typeAlias alias="TUsers" type="com.yw.user.login.entity.TUsers"/>
		StringBuffer cb = new StringBuffer();
		for (ClassName ccn : cList) {
			cb.append("<typeAlias alias=\"").append(ccn.getcName()).append("\"")
			  .append(" type=\"").append(ccn.getcPakge()).append("\"/> \n");
			
		}
		
		context.put("clist", cb.toString());
		
		 String srcPath = outputBasepath + CodeResourceUtil.source_root_package + "\\";
		 String sqlMapperPath ="configuration.xml";
		 CommonPageParser.WriterPage(context, "configurationTemplate.xml", srcPath, sqlMapperPath);
	}
	
}