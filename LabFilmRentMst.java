package com.ddsc.km.lab.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.core.entity.BaseEntity;

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
@Table(name = "LAB_FILM_RENT_MST")
public class LabFilmRentMst extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String rentOid;
	private String filmId;
	private CommOptCde membGrade;
	private BigDecimal rentHotAmt;
	private BigDecimal rentAmt;
	
	@Id
	@Column (name = "RENT_OID")
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getRentOid() {
		return rentOid;
	}
	
	public void setRentOid(String rentOid) {
		this.rentOid = rentOid;
	}
	
	@Column (name = "FILM_ID")
	public String getFilmId() {
		return filmId;
	}

	public void setFilmId(String filmId) {
		this.filmId = filmId;
	}

	@OneToOne(targetEntity = CommOptCde.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "MEMB_GRADE", referencedColumnName = "OPT_CDE_OID")
	public CommOptCde getMembGrade() {
		return membGrade;
	}
	
	public void setMembGrade(CommOptCde membGrade) {
		this.membGrade = membGrade;
	}
	
	@Column (name = "RENT_HOT_AMT")
	public BigDecimal getRentHotAmt() {
		return rentHotAmt;
	}

	public void setRentHotAmt(BigDecimal rentHotAmt) {
		this.rentHotAmt = rentHotAmt;
	}
	
	@Column (name = "RENT_AMT")
	public BigDecimal getRentAmt() {
		return rentAmt;
	}

	public void setRentAmt(BigDecimal rentAmt) {
		this.rentAmt = rentAmt;
	}
}
