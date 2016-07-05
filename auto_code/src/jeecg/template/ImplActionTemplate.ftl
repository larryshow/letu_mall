package ${bussPackage}.${entityPackage}.action;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSONObject;
import com.hsz.session.service.impl.Session;
import com.mall.core.common.BeanUtils;
import com.mall.core.common.Constants;
import com.hsz.thrift.util.PConstants;
import com.mall.core.common.ExceptionConstants;
import com.mall.core.common.RegexUtil;
import com.mall.core.common.StringUtil;
import com.mall.core.exception.ActionException;
import com.mall.core.exception.ServiceException;
import ${bussPackage}.${entityPackage}.vo.${className};
import ${bussPackage}.${entityPackage}.page.${className}Page;
import ${bussPackage}.${entityPackage}.service.${className}Service;

/**
 * <p>Title:${className}Action</p>
 * <p>Description:${codeName}Action类</p>
 * <p>Copyright: Copyright (c) ${yyyy}</p>
  * <p>Company: ${company}</p>
 * @author ${author}
 * @version v1.0 ${yyyyMMdd}
 */
@Component("${lowerName}Action")
public class ${className}Action {
	/**初始化日志对象*/
	private final static Logger log= Logger.getLogger(${className}Action.class);
	/**注入${codeName}Service类*/
	@Resource(name="$!{lowerName}ServiceImpl")
	private ${className}Service ${lowerName}Service; 
	
	/**
	 * 
	 * 【分页查询】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implPageActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	* <p>page 分页数据 如下：</p>
	* <table border=1 cellspacing=0>
	*   <tr><th>参数</th><th>参数名</th><th>类型</th><th>可为空</th><th>限长</th><th>备注</th></tr>
	*   <tr><td>nowPage</td><td>当前页</td><td>Integer</td><td></td><td></td><td>当前页</td></tr>
	*   <tr><td>rowCount</td><td>总行数</td><td>Integer</td><td></td><td></td><td>总行数</td></tr>
	*   <tr><td>pageSize</td><td>每页显示条数</td><td>Integer</td><td></td><td></td><td>每页显示条数</td></tr>
	*   <tr><td>pageCount</td><td>总页数</td><td>Integer</td><td></td><td></td><td>总页数</td></tr>
	*   <tr><td>pageOffset</td><td>当前页起始记录</td><td>Integer</td><td></td><td></td><td>当前页起始记录</td></tr>
	*   <tr><td>pageTail</td><td>当前页到达的记录</td><td>Integer</td><td></td><td></td><td>当前页到达的记录</td></tr>
	*   <tr><td>orderField</td><td>排序字段</td><td>String</td><td></td><td></td><td>排序字段</td></tr>
	*   <tr><td>orderDirection</td><td>升</td><td>boolean</td><td></td><td></td><td>升</td></tr>
	*   <tr><td>length</td><td>页面显示分页按钮个数</td><td>Integer</td><td></td><td></td><td>页面显示分页按钮个数</td></tr>
	*   <tr><td>startIndex</td><td>开始分页数字</td><td>Integer</td><td></td><td></td><td>开始分页数字</td></tr>
	*   <tr><td>endIndex</td><td>结束分页数字</td><td>Integer</td><td></td><td></td><td>结束分页数字</td></tr>
	*   <tr><td>indexs</td><td>显示分页的页数数组</td><td>int[]</td><td></td><td></td><td>显示分页的页数数组</td></tr>
	* </table>
	* <p>list 结果集 如下：</p>
	${implActionAnnotation}
	* @throws ActionException
	 */
	public JSONObject query(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		${className}Page page = new ${className}Page();
		BeanUtils.transMap2Bean2(map, page);
		try {
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, ${lowerName}Service.query(page),s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
	
	/**
	 * 
	 * 【添加】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	* @throws ActionException
	 */
	public JSONObject add(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		${className} vo = new ${className}();
		BeanUtils.transMap2Bean2(map, vo);
		try {
			vo = ${lowerName}Service.addVo(vo);
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, null,s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
	
	/**
	 * 
	 * 【修改】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	* @throws ActionException
	 */
	public JSONObject update(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		${className} vo = new ${className}();
		BeanUtils.transMap2Bean2(map, vo);
		try {
			${lowerName}Service.modVoNotNull(vo);
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, null,s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
	
	/**
	 * 
	* 【删除】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implPRIActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	* @throws ActionException
	 */
	public JSONObject delete(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		try {
			String idStr = (String) map.get("id");
			if(StringUtil.isEmpty(idStr) || !RegexUtil.isNumber(idStr))
				return PConstants.getResult(Constants.SUCCESS_FALSE, Constants.FLAG_MSG_ERROR_ERRORCODE_7,null,ExceptionConstants.ERRORCODE_7,s);
			Long id = Long.parseLong(idStr);
			
			${lowerName}Service.delId(id);
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, null,s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
	
	/**
	 * 
	* 【查询唯一数据】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implPRIActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	${implActionAnnotation}
	* @throws ActionException
	 */
	public JSONObject findVo(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		try {
			String idStr = (String) map.get("id");
			if(StringUtil.isEmpty(idStr) || !RegexUtil.isNumber(idStr))
				return PConstants.getResult(Constants.SUCCESS_FALSE, Constants.FLAG_MSG_ERROR_ERRORCODE_7,null,ExceptionConstants.ERRORCODE_7,s);
			Long id = Long.parseLong(idStr);
			
			${className} vo = ${lowerName}Service.findVo(id);
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, vo,s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
	
	
	/**
	 * 
	* 【查询集合】(这里用一句话描述这个方法的作用)
	* @param sessionKey
	*         <p>会话key</p>
	* @param s
	* 		<p>当前用户session</p>
	* @param map
	${implActionAnnotation}
	* 
	* @return
	* 		<p>根据公共参数判断成功/失败</p>
	${implActionAnnotation}
	
	* @throws ActionException
	 */
	public JSONObject list(String sessionKey,Map<String,Object> map,Session s) throws ActionException {
		try {
			${className} vo = new ${className}();
			BeanUtils.transMap2Bean2(map, vo);
			List<${className}> list = ${lowerName}Service.findList(vo);
			return PConstants.getResult(Constants.SUCCESS_TRUE, Constants.FLAG_MSG_SUCCESS, list,s);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
	}
}