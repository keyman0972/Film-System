package com.ddsc.km.lab.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.km.lab.dao.ILabFilmRentMstDao;
import com.ddsc.km.lab.entity.LabFilmRentMst;

/**
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2017/8/10</td>
 * <td>新建檔案</td>
 * <td>"keyman"</td>
 * </tr>
 * </table>
 * @author "keyman"
 *
 * 類別說明 :
 *
 *
 * 版權所有 Copyright 2008 © 中菲電腦股份有限公司 本網站內容享有著作權，禁止侵害，違者必究。 <br>
 * (C) Copyright Dimerco Data System Corporation Inc., Ltd. 2009 All Rights
 */

public class LabFilmRentMstDaoHibernate extends GenericDaoHibernate<LabFilmRentMst,String> implements ILabFilmRentMstDao {

	@Override
	public List<LabFilmRentMst> getList(String id, UserInfo info) throws DdscApplicationException {
		
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbsql= new StringBuffer();
		sbsql.append("select lfrm");
		sbsql.append(" from LabFilmRentMst lfrm ");
		
		String keyword = "where ";
		if(StringUtils.isNotEmpty(id)){
			sbsql.append(keyword+"lfrm.filmId = ? ");
			values.add(id);
			keyword = "and ";
		}
		return super.findByHQLString(sbsql.toString(), values, info);
	}
	
}
