package ${bussPackage}.${entityPackage}.dao.impl;
import org.springframework.stereotype.Repository;
import com.mall.core.dao.impl.MyBaitsDaoImpl;
import ${bussPackage}.${entityPackage}.dao.${className}Dao;
/**
 * <p>Title:${className}DaoImpl</p>
 * <p>Description:${codeName}DAO实现类</p>
 * <p>Copyright: Copyright (c) ${yyyy}</p>
 * <p>Company: ${company}</p>
 * @author ${author}
 * @version v1.0 ${yyyyMMdd}
 */
@Repository("$!{lowerName}DaoImpl")
public class ${className}DaoImpl extends MyBaitsDaoImpl<${bussPackage}.${entityPackage}.vo.${className},${keytype}> implements ${className}Dao {
	/**MYBatis命名空间名*/
	private static String preName = ${className}Dao.class.getName();
	/** 
	* 【取得】MYBatis命名空间名
	* @return  	MYBatis命名空间名
	*/
	@Override
	protected String getPreName() {
		return preName;
	}
}