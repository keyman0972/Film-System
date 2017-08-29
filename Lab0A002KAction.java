package com.ddsc.km.lab.action;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.common.comm.service.ICommOptCdeService;
import com.ddsc.core.action.AbstractAction;
import com.ddsc.core.action.IBaseAction;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.exception.DdscAuthException;
import com.ddsc.core.util.Pager;
import com.ddsc.km.lab.entity.LabFilmCorpMst;
import com.ddsc.km.lab.entity.LabFilmMst;
import com.ddsc.km.lab.entity.LabFilmRentMst;
import com.ddsc.km.lab.service.ILabFilmCorpMstService;
import com.ddsc.km.lab.service.ILabFilmMstService;

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

public class Lab0A002KAction extends AbstractAction implements IBaseAction {
	
	private static final long serialVersionUID = -3919667919425078977L;
	
	private ILabFilmMstService labFilmMstService;
	private ICommOptCdeService commOptCdeService;
	private ILabFilmCorpMstService labFilmCorpMstService;
	private List<LabFilmMst> labFilmMstList;
	private LabFilmMst labFilmMst;
	
	private List<LabFilmRentMst> labFilmRentMstList;
	
	private List<CommOptCde> flimGradeList;			//動態產生 "影片等級"
	private List<CommOptCde> flimCateList;			//動態產生 "影片分類"
	private List<CommOptCde> membGradeList;			//動態產生 "會員等級"
	private List<String> filmHotList;
	
	@Override
	public String init() throws Exception {
		try {
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@Deprecated
	@Override
	public String approve() throws Exception {
		return null;
	}
	
	@Override
	public String search() throws Exception {
		try {
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("filmId", labFilmMst.getFilmId());
			conditions.put("filmName", labFilmMst.getFilmName());
			
			if(filmHotList !=null){
				List<String> filmhotlist= new ArrayList<String>();
				filmhotlist.addAll(filmHotList);
				filmhotlist.removeAll(Collections.singleton(null));
				conditions.put("filmHot", filmhotlist);
			}
			
			if(flimCateList != null){
				List<CommOptCde> alist= new ArrayList<CommOptCde>();
				alist.addAll(flimCateList);
				alist.removeAll(Collections.singleton(null));
				conditions.put("flimCate", alist);
			}
			
			Pager resultPager = getLabFilmMstService().searchByConditions(conditions, getPager(), this.getUserInfo());
			
			labFilmMstList = (List<LabFilmMst>) resultPager.getData();
			
			setPager(resultPager);
			if (labFilmMstList == null || labFilmMstList.size() <= 0) {
				this.addActionError(this.getText("w.0001"));
			}
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().get(labFilmMst.getFilmId(), this.getUserInfo());
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_QUERY);
		return SUCCESS;
	}

	@Override
	public String create() throws Exception {
		try{
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_CREATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String createSubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				setNextAction(ACTION_CREATE_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				return this.createConfirm();
			}
		}
		catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}
	}

	@Override
	public String createConfirm() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().create(labFilmMst, getUserInfo());
			setNextAction(ACTION_CREATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().get(labFilmMst.getFilmId(), this.getUserInfo());
			
			setNextAction(ACTION_UPDATE_SUBMIT);
			return SUCCESS;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateSubmit() throws Exception {
		try {
			if (hasConfirm()) {
				setNextAction(ACTION_UPDATE_CONFIRM);
				return RESULT_CONFIRM;
			}else {
				return this.updateConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateConfirm() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().update(labFilmMst, getUserInfo());
			setNextAction(ACTION_UPDATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().get(labFilmMst.getFilmId(),  this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_DELETE_CONFIRM);
		return SUCCESS;
	}

	@Override
	public String deleteConfirm() throws Exception {
		try {
			labFilmMst = getLabFilmMstService().delete(labFilmMst, this.getUserInfo());
			setNextAction(ACTION_DELETE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_DELETE_CONFIRM);
			return RESULT_CONFIRM;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_DELETE_CONFIRM);
			return RESULT_CONFIRM;
		}
	}
	
	@Override
	public String copy() throws Exception {
		try{
			labFilmMst = getLabFilmMstService().get(labFilmMst.getFilmId(), this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_COPY_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String copySubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				// 有確認頁
				setNextAction(ACTION_COPY_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				// 沒有確認頁, 直接存檔
				return this.copyConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String copyConfirm() throws Exception {
		try{
			labFilmMst = this.getLabFilmMstService().create(labFilmMst, this.getUserInfo());
			
			setNextAction(ACTION_COPY);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}
	}
	
	@Override
	public void validate() {
		try {
			setUpInfo();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}
	
	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateCreateSubmit() {
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateCreateConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateUpdateSubmit() {
		try {
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateUpdateConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}
	
	/**
	 * 檢核 - 按確定鈕(刪除頁)
	 */
	public void validateDeleteConfirm() {
		try {
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(複製頁)
	 */
	public void validateCopySubmit() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核 - 按確定鈕(複製頁)
	 */
	public void validateCopyConfirm() {
		// 先執行Action所對應的 validate, 再執行 validate(). (即 validateCreateSubmit 執行完後, 再執行 validate())
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
	}

	/**
	 * 檢核ID是否重複
	 *
	 * @return
	 * @throws Exception
	 */
	private boolean checkPrimaryKey() throws Exception {
		boolean isValid = true;
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("filmId",labFilmMst.getFilmId());
		if (labFilmMstService.getDataRowCountByConditions(conditions, this.getUserInfo()) > 0) {
			this.addFieldError("filmId", this.getText("filmId") + this.getText("eP.0004"));
			isValid = false;
		}
		return isValid;
	}

	/**
	 * 資料檢核
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean checkValidateRule() throws Exception {
		boolean isValid = true;
		try {
			// 電影發行公司代碼
			LabFilmCorpMst labFilmCorpMst = this.getLabFilmCorpMstService().get(labFilmMst.getLabFilmCorpMst().getFmCorpId(), this.getUserInfo());
			if(labFilmCorpMst== null){
				this.addFieldError("fmCorpId", this.getText("fmCorpId")+ this.getText("eP.0003"));
				isValid = false;
			}else{
				labFilmMst.setLabFilmCorpMst(labFilmCorpMst);
			}
			
			
			// 檢查參數代碼是否存在 (影片分類)
			if (null != labFilmMst.getFilmCate().getOptCde() && StringUtils.isNotEmpty(labFilmMst.getFilmCate().getOptCde())) {
				CommOptCde filmCate = getCommOptCdeService().getByKey("F0001", labFilmMst.getFilmCate().getOptCde(), this.getUserInfo());
				if (filmCate == null) {
					this.addFieldError("filmCate", this.getText("filmCate")+ this.getText("eP.0003"));
					isValid = false;
				}else{
					labFilmMst.setFilmCate(filmCate);
				}
			}
			
			// 檢查參數代碼是否存在 (影片等級)
			if (null != labFilmMst.getFilmGrade() && StringUtils.isNotEmpty(labFilmMst.getFilmGrade())) {
				CommOptCde filmGrade = getCommOptCdeService().getByKey("F0002", labFilmMst.getFilmGrade(), this.getUserInfo());
				if (filmGrade == null) {
					this.addFieldError("filmGrade", this.getText("filmGrade")+ this.getText("eP.0003"));
					isValid = false;
				}else{
					labFilmMst.setFilmGrade(filmGrade.getOptCde());
					labFilmMst.setFilmgrade(filmGrade);
					
				}
			}
			
			List<LabFilmRentMst> labFilmRentMstList = labFilmMst.getLabFilmRentMstList();
			
			for(int i=0;i<labFilmRentMstList.size();i++){
				String OptCdeOid = labFilmRentMstList.get(i).getMembGrade().getOptCdeOid();
				CommOptCde membGrade = getCommOptCdeService().get(OptCdeOid, this.getUserInfo());
				if (membGrade == null) {
					this.addFieldError("membGrade", this.getText("membGrade")+ this.getText("eP.0003"));
					isValid = false;
				}else{
					labFilmRentMstList.get(i).setMembGrade(membGrade);
				}
			}
			
			
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		return isValid;
	}

	public ILabFilmMstService getLabFilmMstService() {
		return labFilmMstService;
	}

	public void setLabFilmMstService(ILabFilmMstService labFilmMstService) {
		this.labFilmMstService = labFilmMstService;
	}

	public List<LabFilmMst> getLabFilmMstList() {
		return labFilmMstList;
	}

	public void setLabFilmMstList(List<LabFilmMst> labFilmMstList) {
		this.labFilmMstList = labFilmMstList;
	}

	public LabFilmMst getLabFilmMst() {
		return labFilmMst;
	}

	public void setLabFilmMst(LabFilmMst labFilmMst) {
		this.labFilmMst = labFilmMst;
	}

	public ICommOptCdeService getCommOptCdeService() {
		return commOptCdeService;
	}

	public void setCommOptCdeService(ICommOptCdeService commOptCdeService) {
		this.commOptCdeService = commOptCdeService;
	}

	public List<LabFilmRentMst> getLabFilmRentMstList() {
		return labFilmRentMstList;
	}

	public void setLabFilmRentMstList(List<LabFilmRentMst> labFilmRentMstList) {
		this.labFilmRentMstList = labFilmRentMstList;
	}

	public List<CommOptCde> getFlimGradeList() {
		if(flimGradeList == null){
			this.setFlimGradeList(this.getCommOptCdeService().getList("F0002", this.getUserInfo()));
		}
		return flimGradeList;
	}

	public void setFlimGradeList(List<CommOptCde> flimGradeList) {
		this.flimGradeList = flimGradeList;
	}

	public List<CommOptCde> getFlimCateList() {
		if(flimCateList == null){
			this.setFlimCateList(this.getCommOptCdeService().getList("F0001", this.getUserInfo()));
		}
		return flimCateList;
	}

	public void setFlimCateList(List<CommOptCde> flimCateList) {
		this.flimCateList = flimCateList;
	}

	public List<CommOptCde> getMembGradeList() {
		if(membGradeList == null){
			this.setMembGradeList(this.getCommOptCdeService().getList("F0003", this.getUserInfo()));
		}
		return membGradeList;
	}

	public void setMembGradeList(List<CommOptCde> membGradeList) {
		this.membGradeList = membGradeList;
	}

	public ILabFilmCorpMstService getLabFilmCorpMstService() {
		return labFilmCorpMstService;
	}

	public void setLabFilmCorpMstService(
			ILabFilmCorpMstService labFilmCorpMstService) {
		this.labFilmCorpMstService = labFilmCorpMstService;
	}

	public List<String> getFilmHotList() {
		return filmHotList;
	}

	public void setFilmHotList(List<String> filmHotList) {
		this.filmHotList = filmHotList;
	}
}
