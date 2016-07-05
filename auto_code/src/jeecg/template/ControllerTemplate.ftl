package ${bussPackage}.${entityPackage}.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.mall.common.action.BaseAction;
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
@Controller
@RequestMapping("${entityPackage}/${lowerName}")
public class ${className}Action extends BaseAction {
	/**初始化日志对象*/
	private final static Logger log= Logger.getLogger(${className}Action.class);
	/**注入${codeName}Service类*/
	@Resource(name="$!{lowerName}ServiceImpl")
	private ${className}Service ${lowerName}Service; 
	
	/** 
	* 【保存】
	* @param req
	* @param vo
	* @return
	* @throws ActionException  
	*/
	@RequestMapping(value="save")
	public String save(HttpServletRequest req,${className} vo) throws ActionException{
		try {
			${lowerName}Service.save(vo);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
		return "redirect:/${entityPackage}/${lowerName}/list.htm";
	}
	
	/** 
	* 【删除】
	* @param req
	* @return
	* @throws ActionException  
	*/
	@RequestMapping(value="delete")
	public String delete(HttpServletRequest req,Long id) throws ActionException{
		try {
			${lowerName}Service.delId(id);
		} catch (Exception e) {
			throw new ActionException(e);
		}
		return "redirect:/${entityPackage}/${lowerName}/list.htm";
	}
	
	/** 
	* 【分页】
	* @param req
	* @param page
	* @return
	* @throws ActionException  
	*/
	@RequestMapping(value="list")
	public String list(HttpServletRequest req,${className}Page page) throws ActionException{
		try {
			${lowerName}Service.query(page);
		} catch (ServiceException e) {
			throw new ActionException(e);
		}
		req.setAttribute("page", page);
		return "${entityPackage}/${lowerName}_list";
	}
	
	/** 
	* 【编辑】
	* @param req
	* @param id
	* @return
	* @throws ActionException  
	*/
	@RequestMapping(value="input")
	public String input(HttpServletRequest req,Long id) throws ActionException{
		${className} vo = new ${className}();
		try {
			if(null != id)
				vo = ${lowerName}Service.findVo(id);
		} catch (Exception e) {
			throw new ActionException(e);
		}
		req.setAttribute("vo", vo);
		return "${entityPackage}/${lowerName}_input";
	}
}