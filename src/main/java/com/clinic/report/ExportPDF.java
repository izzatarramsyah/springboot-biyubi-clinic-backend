package com.clinic.report;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ExportPDF {
	
	public static byte[] download (Map<String, Object> object, String path) throws JRException {
		
		List<String> dummy = new ArrayList<String>();
		String dum = "dummyContent";
		dummy.add(dum);
		
		JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dummy);
		InputStream jasperSteam = ExportPDF.class.getClassLoader().getResourceAsStream(path);
		JasperDesign design = JRXmlLoader.load(jasperSteam);
		DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.query.executer.factory.plsql",
				"com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");
		JasperReport jasperReport = JasperCompileManager.compileReport(design);
		jasperReport.setProperty("net.sf.jasperreports.query.executer.factory.plsql",
				"com.jaspersoft.jrx.query.PlSqlQueryExecuterFactory");
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, object, beanColDataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}
	
}
