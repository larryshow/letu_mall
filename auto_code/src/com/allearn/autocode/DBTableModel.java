package com.allearn.autocode;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表模型
 * @Package codeGenerate
 * @ClassName: DBTableModel
 * @Description: 
 * @author 赵汉江
 * @date 2014-1-22 上午10:20:58
 * @version V1.0
 */
public class DBTableModel implements Serializable {
	
	private String keytype;
	
	private List<ColumnData> collist = null;

	public String getKeytype() {
		return keytype;
	}

	public void setKeytype(String keytype) {
		this.keytype = keytype;
	}

	public List<ColumnData> getCollist() {
		return collist;
	}

	public void setCollist(List<ColumnData> collist) {
		this.collist = collist;
	}

	public DBTableModel(String keytype, List<ColumnData> collist) {
		super();
		this.keytype = keytype;
		this.collist = collist;
	}

	public DBTableModel() {
		super();
	}
	
}