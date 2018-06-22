package com.pol.gestionart.daoImpl;

import org.springframework.stereotype.Repository;

import com.pol.gestionart.dao.CajaReporteDao;
import com.pol.gestionart.entity.ReporteCaja;

@Repository
public class CajaReporteDaoImpl extends DaoImpl<ReporteCaja> implements CajaReporteDao{

	@Override
	public void destroy(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCamposFiltrables() {
		// TODO Auto-generated method stub
		return null;
	}
	

}