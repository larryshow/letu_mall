package ${bussPackage}.${entityPackage}.page;
import com.mall.core.vo.Pager;
/**
 * <p>Title:${className}Page</p>
 * <p>Description:${codeName}分页类</p>
 * <p>Copyright: Copyright (c) ${yyyy}</p>
 * <p>Company: ${company}</p>
 * @author ${author}
 * @version v1.0 ${yyyyMMdd}
 */
public class ${className}Page extends Pager<${bussPackage}.${entityPackage}.vo.${className},${keytype}> implements java.io.Serializable{

	/**类的版本号*/
	private static final long serialVersionUID = ${page_serialVersionUID}L;

	${feilds}
}