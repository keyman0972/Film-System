package com.ddsc.km.lab.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.core.entity.BaseEntity;
import com.ddsc.core.util.LocaleDataHelper;

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

@Entity
@Table(name= "LAB_FILM_MST")
public class LabFilmMst extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = -8354309961822658111L;
	
	private String filmId;
	private String filmName;
	private String filmName_lang1;
	private String filmName_lang2;
	private String filmName_lang3;
	private LabFilmCorpMst labFilmCorpMst;
	private String filmType;
	private String filmGrade;
	private String filmHot;
	private CommOptCde filmCate;
	private String filmQty;
	
	private List<LabFilmRentMst> labFilmRentMstList;

	private CommOptCde filmgrade;
	
	@Id
	@Column(name="FILM_ID")
	public String getFilmId() {
		return filmId;
	}
	
	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}
	
	@Transient
	public String getFilmName() {
		if(null != this.filmName && this.filmName.length()==0){
			return null;
		}else{
			return filmName;			
		}
	}
	
	public void setFilmName(String filmName) {
		this.filmName = filmName;
	}
	
	@Column(name="FILM_NAME_LANG1")
	public String getFilmName_lang1() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_1)) {
			return this.getFilmName();
		}else{
			return filmName_lang1;			
		}
	}
	
	public void setFilmName_lang1(String filmName_lang1) {
		this.filmName_lang1 = filmName_lang1;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_1)) {
			this.filmName = filmName_lang1;
		}
	}
	
	@Column(name="FILM_NAME_LANG2")
	public String getFilmName_lang2() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_2)) {
			return this.getFilmName();
		}else{
			return filmName_lang2;			
		}
	}
	
	public void setFilmName_lang2(String filmName_lang2) {
		this.filmName_lang2 = filmName_lang2;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_2)) {
			this.filmName = filmName_lang2;
		}
	}
	
	@Column(name="FILM_NAME_LANG3")
	public String getFilmName_lang3() {
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_3)) {
			return this.getFilmName();
		}else{
			return filmName_lang3;			
		}
	}
	
	public void setFilmName_lang3(String filmName_lang3) {
		this.filmName_lang3 = filmName_lang3;
		if (LocaleDataHelper.equalToLocale(LocaleDataHelper.LANGUAGE_3)) {
			this.filmName = filmName_lang3;
		}
	}
	
	@OneToOne(targetEntity = LabFilmCorpMst.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "FM_CORP_ID", referencedColumnName = "FM_CORP_ID")
	public LabFilmCorpMst getLabFilmCorpMst() {
		return labFilmCorpMst;
	}
	
	public void setLabFilmCorpMst(LabFilmCorpMst labFilmCorpMst) {
		this.labFilmCorpMst = labFilmCorpMst;
	}
	
	@Column(name="FILM_TYPE")
	public String getFilmType() {
		return filmType;
	}
	
	public void setFilmType(String filmType) {
		this.filmType = filmType;
	}
	
	@Column(name="FILM_Grade")
	public String getFilmGrade() {
		return filmGrade;
	}
	
	public void setFilmGrade(String filmGrade) {
		this.filmGrade = filmGrade;
	}
	
	@Column(name="FILM_HOT")
	public String getFilmHot() {
		return filmHot;
	}
	
	public void setFilmHot(String filmHot) {
		this.filmHot = filmHot;
	}
	
	@OneToOne(targetEntity = CommOptCde.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "FILM_CATE", referencedColumnName = "OPT_CDE_OID")
	public CommOptCde getFilmCate() {
		return filmCate;
	}
	
	public void setFilmCate(CommOptCde filmCate) {
		this.filmCate = filmCate;
	}
	
	@Column(name="FILM_QTY")
	public String getFilmQty() {
		return filmQty;
	}
	
	public void setFilmQty(String filmQty) {
		this.filmQty = filmQty;
	}
	
	@Transient
	public List<LabFilmRentMst> getLabFilmRentMstList() {
		return labFilmRentMstList;
	}

	public void setLabFilmRentMstList(List<LabFilmRentMst> labFilmRentMstList) {
		this.labFilmRentMstList = labFilmRentMstList;
	}
	
	@Transient
	public CommOptCde getFilmgrade() {
		
		return filmgrade;
	}

	public void setFilmgrade(CommOptCde filmgrade) {
		this.filmgrade = filmgrade;
	}
	
}
