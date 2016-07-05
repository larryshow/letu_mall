package ${bussPackage}.${entityPackage}.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.mall.core.dao.BizDao;
import com.mall.core.log.LogTool;
import com.mall.core.service.impl.BaseServiceImpl;
import ${bussPackage}.${entityPackage}.service.${className}Service;
import ${bussPackage}.${entityPackage}.dao.${className}Dao;
/**
 * <p>Title:${className}ServiceImpl</p>
 * <p>Description:${codeName}服务实现类</p>
 * <p>Copyright: Copyright (c) ${yyyy}</p>
  * <p>Company: ${company}</p>
 * @author ${author}
 * @version v1.0 ${yyyyMMdd}
 */
@Service("$!{lowerName}ServiceImpl")
public class ${className}ServiceImpl extends BaseServiceImpl<${bussPackage}.${entityPackage}.vo.${className},${keytype}> implements ${className}Service  {
	/**初始化日志对象*/
	private static LogTool log = LogTool.getInstance(${className}ServiceImpl.class);
	/**注入${codeName}DAO接口类*/
	@Resource(name="$!{lowerName}DaoImpl")
    private ${className}Dao dao;
    
	/** 
	* 【取得】业务DAO对象
	* @return 	业务DAO对象  
	*/
	@Override
	protected BizDao<${bussPackage}.${entityPackage}.vo.${className},${keytype}> getDao() {
		return dao;
	}
	/** 
	* 【取得】日志对象
	* @return 	日志对象  
	*/
	@Override
	protected LogTool getLog() {
		return log;
	}
}