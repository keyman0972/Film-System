package com.ddsc.km.lab.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.common.comm.service.ICommOptCdeService;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.BeanUtilsHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.lab.dao.ILabFilmMstDao;
import com.ddsc.km.lab.dao.ILabFilmRentMstDao;
import com.ddsc.km.lab.entity.LabFilmMst;
import com.ddsc.km.lab.entity.LabFilmRentMst;
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

public class LabFilmMstServiceImpl implements ILabFilmMstService {
	
	private ILabFilmMstDao labFilmMstDao;
	private ILabFilmRentMstDao labFilmRentMstDao;
	private ICommOptCdeService commOptCdeService;

	@Override
	public LabFilmMst create(LabFilmMst entity, UserInfo info) throws DdscApplicationException {
		try{
			labFilmMstDao.save(entity, info);
			String filmId = entity.getFilmId();
			for(LabFilmRentMst labFilmRentMst:entity.getLabFilmRentMstList()){
				labFilmRentMst.setFilmId(filmId);
				labFilmRentMstDao.save(labFilmRentMst, info);
			}
			
			return entity;
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabFilmMst update(LabFilmMst entity, UserInfo info) throws DdscApplicationException {
		try{
			LabFilmMst labFilmMstPo = this.labFilmMstDao.get(entity.getFilmId(), info);
			if(entity.getVer().getTime() == labFilmMstPo.getVer().getTime()){
				List<LabFilmRentMst> labFilmRentMstList = labFilmRentMstDao.getList(entity.getFilmId(), info);
				
				if(labFilmRentMstList != null){
					labFilmMstPo.setLabFilmRentMstList(labFilmRentMstList);
				}else{
					labFilmMstPo.setLabFilmRentMstList(new ArrayList<LabFilmRentMst>());
				}
				//全部刪除
				for(LabFilmRentMst labFilmRentMstPo:labFilmRentMstList){
					
					labFilmRentMstDao.delete(labFilmRentMstPo, info);
					this.getLabFilmRentMstDao().flush();
				}
				//全部新增
				for(LabFilmRentMst labFilmRentMst:entity.getLabFilmRentMstList()){
					labFilmRentMst.setFilmId(entity.getFilmId());
					labFilmRentMstDao.save(labFilmRentMst, info);
				}
				
				BeanUtilsHelper.copyProperties(labFilmMstPo, entity, entity.obtainLocaleFieldNames());
				this.getLabFilmMstDao().update(labFilmMstPo, info);
				return labFilmMstPo;
			}else{
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR, "eP.0013");
			}
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}

	@Override
	public LabFilmMst delete(LabFilmMst entity, UserInfo info) throws DdscApplicationException {
		try{
			LabFilmMst labFilmMstPo = this.labFilmMstDao.get(entity.getFilmId(), info);
			if(entity.getVer().getTime() == labFilmMstPo.getVer().getTime()){
				List<LabFilmRentMst> labFilmRentMstList = labFilmRentMstDao.getList(entity.getFilmId(), info);
				for(LabFilmRentMst labFilmRentMstPo:labFilmRentMstList){
					
					labFilmRentMstDao.delete(labFilmRentMstPo, info);
				}
				labFilmMstDao.delete(labFilmMstPo, info);
				this.getLabFilmRentMstDao().flush();
				this.getLabFilmMstDao().flush();
				return entity;
			}else{
				throw new DdscApplicationException(DdscApplicationException.DDSCEXCEPTION_TYPE_ERROR,"eP.0013");
			}
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Override
	public LabFilmMst get(String id, UserInfo info) throws DdscApplicationException {
		try{
			LabFilmMst labFilmMst = this.getLabFilmMstDao().get(id, info);
			labFilmMst.setLabFilmRentMstList(this.getLabFilmRentMstDao().getList(id, info));
			
			CommOptCde filmGrade = this.getCommOptCdeService().getByKey("F0002", labFilmMst.getFilmGrade(), info);
			labFilmMst.setFilmgrade(filmGrade);
			
			return labFilmMst;
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	@Deprecated
	@Override
	public List<LabFilmMst> searchAll(UserInfo info) throws DdscApplicationException {
		return null;
	}

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		try{
			return labFilmMstDao.searchByConditions(conditions, pager, userInfo);
		}catch (DdscApplicationException e) {
			throw e;
		} catch (Exception e) {
			throw new DdscApplicationException(e, userInfo);
		}
	}
	
	@Deprecated
	@Override
	public List<LabFilmMst> searchByConditions(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}
	
	@Deprecated
	@Override
	public List<Object> queryDataByParamsByService(Map<String, Object> conditions, UserInfo userInfo) throws DdscApplicationException {
		return null;
	}

	@Override
	public int getDataRowCountByConditions(Map<String, Object> conditions, UserInfo info) throws DdscApplicationException {
		try{
			return this.labFilmMstDao.getDataRowCountByConditions(conditions, info);
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	public List<Map<String,Object>> getFilmListCount(String id, UserInfo info) throws DdscApplicationException{
		try{
			return labFilmMstDao.getFilmListCount(id, info);
		}catch (DdscApplicationException e) {
			throw e;
		}catch (Exception e) {
			throw new DdscApplicationException(e, info);
		}
	}
	
	public ILabFilmMstDao getLabFilmMstDao() {
		return labFilmMstDao;
	}

	public void setLabFilmMstDao(ILabFilmMstDao labFilmMstDao) {
		this.labFilmMstDao = labFilmMstDao;
	}

	public ILabFilmRentMstDao getLabFilmRentMstDao() {
		return labFilmRentMstDao;
	}

	public void setLabFilmRentMstDao(ILabFilmRentMstDao labFilmRentMstDao) {
		this.labFilmRentMstDao = labFilmRentMstDao;
	}

	public ICommOptCdeService getCommOptCdeService() {
		return commOptCdeService;
	}

	public void setCommOptCdeService(ICommOptCdeService commOptCdeService) {
		this.commOptCdeService = commOptCdeService;
	}
	
}
