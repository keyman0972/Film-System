package com.ddsc.km.lab.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.lab.dao.ILabFilmMstDao;
import com.ddsc.km.lab.entity.LabFilmMst;

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

public class LabFilmMstDaoHibernate extends GenericDaoHibernate<LabFilmMst,String> implements ILabFilmMstDao {

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		
		String filmName_lang = LocaleDataHelper.getPropertityWithLocalUpper("FILM_NAME", userInfo.getLocale());
		String optCdeNam_lang = LocaleDataHelper.getPropertityWithLocalUpper("OPT_CDE_NAM", userInfo.getLocale());
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT LFM.FILM_ID, LFM." + filmName_lang + " AS FILM_NAME, GRADE.OPT_CDE AS OPT_GRADE_CDE, ");
		sbsql.append("	GRADE." + optCdeNam_lang + " AS OPT_GRADE_CDE_NAME, LFM.FILM_HOT, LFM.FILM_CATE, ");
		sbsql.append("	CATE.OPT_CDE AS OPT_CATE_CDE, CATE." + optCdeNam_lang + " AS OPT_CATE_CDE_NAME ");
		sbsql.append("FROM LAB_FILM_MST LFM ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE GRADE ON LFM.FILM_GRADE = GRADE.OPT_CDE AND GRADE.OPT_CTG_CDE = 'F0002' ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE CATE ON LFM.FILM_CATE = CATE.OPT_CDE_OID ");

		String keyword = "WHERE ";
		List<Object> value = new ArrayList<Object>();
		if (StringUtils.isNotEmpty((String) conditions.get("filmId"))) {
			sbsql.append(keyword + "LFM.FILM_ID LIKE ? ");
			value.add(conditions.get("filmId") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("filmName"))) {
			sbsql.append(keyword + "LFM."+filmName_lang+" LIKE ? ");
			value.add("%"+ conditions.get("filmName") + "%");
			keyword = "AND ";
		}
		
		List<String> filmHot = (List<String>) conditions.get("filmHot");
		if (filmHot != null && !filmHot.isEmpty()) {
			sbsql.append(keyword +"CATE.OPT_CDE IN( "+ this.getSqlQuestionMark(filmHot.size())+" ) ");
			value.addAll(filmHot);
			keyword = "AND ";
		}
		
		List<String> flimCate = (List<String>) conditions.get("flimCate");
		if (flimCate != null && !flimCate.isEmpty()) {
			sbsql.append(keyword +"CATE.OPT_CDE IN( "+ this.getSqlQuestionMark(flimCate.size())+" ) ");
			value.addAll(flimCate);
			keyword = "AND ";
		}
		
		sbsql.append("ORDER BY LFM.FILM_ID ");
		
		// 建立List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>(); 並add
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("FILM_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("FILM_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_GRADE_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_GRADE_CDE_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("FILM_HOT", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("FILM_CATE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_CATE_CDE", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("OPT_CATE_CDE_NAME", Hibernate.STRING));
		
		// 回傳
		return super.findBySQLQueryMapPagination(sbsql.toString(), pager, scalarList, value, userInfo);
	}

	@Override
	public List<Map<String,Object>> getFilmListCount(String id, UserInfo info) throws DdscApplicationException {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbsql= new StringBuffer();
		sbsql.append("SELECT LFCM.FM_CORP_ID, COUNT(LFM.FILM_ID) AS FILM_ID_COUNT ");
		sbsql.append("FROM LAB_FILM_CORP_MST LFCM ");
		sbsql.append("LEFT JOIN LAB_FILM_MST LFM ON LFCM.FM_CORP_ID = LFM.FM_CORP_ID ");
		
		String keyword = "WHERE ";
		if(StringUtils.isNotEmpty(id)){
			sbsql.append(keyword+"LFCM.FM_CORP_ID = ? ");
			values.add(id);
			keyword = "AND ";
		}
		
		sbsql.append("GROUP BY LFCM.FM_CORP_ID ");
		
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("FM_CORP_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("FILM_ID_COUNT", Hibernate.INTEGER));
		
		return super.findBySQLQueryMap(sbsql.toString(), scalarList, values, info);
	}

}
