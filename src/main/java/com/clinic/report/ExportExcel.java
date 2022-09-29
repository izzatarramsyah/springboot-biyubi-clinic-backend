package com.clinic.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;

import com.clinic.api.object.CheckUpSchedule;
import com.clinic.api.object.VaccineSchedule;
import com.clinic.entity.AuditTrail;
import com.clinic.entity.CheckUpMaster;
import com.clinic.entity.Child;
import com.clinic.entity.User;
import com.clinic.entity.VaccineMaster;
import com.clinic.util.Util;


public class ExportExcel {
	
	
	public static byte [] checkUp(List < CheckUpSchedule > schedule, Child child) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Jadwal Rekam Medis");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(3,3,0,4));
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));
	    
	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Jadwal Rekam Medis");
        header1.getCell(0).setCellStyle(style1);
        
        Row header2 = sheet.createRow(3);
        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        style2.setAlignment(HorizontalAlignment.LEFT); 
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        font2.setFontHeightInPoints((short)10);
        font2.setBold(false);
        style2.setFont(font2);
        header2.createCell(0).setCellValue("Nama Anak : " + child.getFullname());
        header2.getCell(0).setCellStyle(style2);

        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        
	    Row header = sheet.createRow(5);
	    header.createCell(1).setCellValue("Jadwal Pemeriksaan");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Bulan Ke-");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Deskripsi");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Status");
	    header.getCell(4).setCellStyle(style);
	    header.createCell(5).setCellValue("Tanggal Pemeriksaan");
	    header.getCell(5).setCellStyle(style);
	    header.createCell(6).setCellValue("Berat Badan");
	    header.getCell(6).setCellStyle(style);
	    header.createCell(7).setCellValue("Panjang Badan");
	    header.getCell(7).setCellStyle(style);
	    header.createCell(8).setCellValue("Lingkar Kepala");
	    header.getCell(8).setCellStyle(style);
	  	header.createCell(9).setCellValue("Catatan");
	    header.getCell(9).setCellStyle(style);
	   
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);
	    styleData.setAlignment(HorizontalAlignment.LEFT);
	    styleData.setVerticalAlignment(VerticalAlignment.TOP);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 6;
	    int no = 0;
	    for( CheckUpSchedule sch : schedule){
	        Row userRow =  sheet.createRow(rowCount++);
	        userRow.createCell(0).setCellValue(no++);
	        userRow.getCell(0).setCellStyle(styleData);
	        userRow.createCell(1).setCellValue(sch.getScheduleDate());
	        userRow.getCell(1).setCellStyle(styleData);
	        userRow.createCell(2).setCellValue(sch.getBatch());
	        userRow.getCell(2).setCellStyle(styleData);
	 	    userRow.createCell(3).setCellValue(sch.getDescription());
	        userRow.getCell(3).setCellStyle(styleData);
	        if (sch.getCheckUpDate() == null) {
	        	userRow.createCell(4).setCellValue("Belum Dilakukan");
		        userRow.getCell(4).setCellStyle(styleData);
		        userRow.createCell(5).setCellValue("-");
		        userRow.getCell(5).setCellStyle(styleData);
		        userRow.createCell(6).setCellValue("-");
		        userRow.getCell(6).setCellStyle(styleData);
		        userRow.createCell(7).setCellValue("-");
		        userRow.getCell(7).setCellStyle(styleData);
		        userRow.createCell(8).setCellValue("-");
		        userRow.getCell(8).setCellStyle(styleData);
		        userRow.createCell(9).setCellValue("-");
		        userRow.getCell(9).setCellStyle(styleData);
	        } else {
	        	userRow.createCell(4).setCellValue("Sudah Dilakukan");
		        userRow.getCell(4).setCellStyle(styleData);
		        userRow.createCell(5).setCellValue(sch.getCheckUpDate());
		        userRow.getCell(5).setCellStyle(styleData);
		        userRow.createCell(6).setCellValue(sch.getWeight());
		        userRow.getCell(6).setCellStyle(styleData);
		        userRow.createCell(7).setCellValue(sch.getLength());
		        userRow.getCell(7).setCellStyle(styleData);
		        userRow.createCell(8).setCellValue(sch.getHeadDiameter());
		        userRow.getCell(8).setCellStyle(styleData);
		        userRow.createCell(9).setCellValue(sch.getNotes());
		        userRow.getCell(9).setCellStyle(styleData);
	        }
	        
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.autoSizeColumn(5);
	    sheet.autoSizeColumn(6);
	    sheet.autoSizeColumn(7);
	    sheet.autoSizeColumn(8);
	    sheet.autoSizeColumn(9);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
	public static byte [] vaccine(List < VaccineSchedule > schedule, Child child) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Jadwal Rekam Imunisasi");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(3,3,0,4));
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));

	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Jadwal Rekam Imunisasi");
        header1.getCell(0).setCellStyle(style1);
        
        Row header2 = sheet.createRow(3);
        CellStyle style2 = workbook.createCellStyle();
        Font font2 = workbook.createFont();
        style2.setAlignment(HorizontalAlignment.LEFT); 
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        font2.setFontHeightInPoints((short)10);
        font2.setBold(false);
        style2.setFont(font2);
        header2.createCell(0).setCellValue("Nama Anak : " + child.getFullname());
        header2.getCell(0).setCellStyle(style2);

        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        
	    Row header = sheet.createRow(5);
	    header.createCell(0).setCellValue("No");
	    header.getCell(0).setCellStyle(style);
	    header.createCell(1).setCellValue("Jadwal Imunisasi");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Nama Imunisasi");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Tipe Imunisasi");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Bulan Ke-");
	    header.getCell(4).setCellStyle(style);
	    header.createCell(5).setCellValue("Status");
	    header.getCell(5).setCellStyle(style);
	    header.createCell(6).setCellValue("Tanggal Imunisasi");
	    header.getCell(6).setCellStyle(style);
	    header.createCell(7).setCellValue("Tanggal Kadaluarsa");
	    header.getCell(7).setCellStyle(style);
	    header.createCell(8).setCellValue("Catatan");
	    header.getCell(8).setCellStyle(style);
	   
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);
	    styleData.setAlignment(HorizontalAlignment.LEFT);
	    styleData.setVerticalAlignment(VerticalAlignment.TOP);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 6;
	    int no = 0;
	    for( VaccineSchedule sch : schedule){
	        Row userRow =  sheet.createRow(rowCount++);
	        userRow.createCell(0).setCellValue(no++);
	        userRow.getCell(0).setCellStyle(styleData);
	        userRow.createCell(1).setCellValue(sch.getScheduleDate());
	        userRow.getCell(1).setCellStyle(styleData);
	        userRow.createCell(2).setCellValue(sch.getVaccineName());
	        userRow.getCell(2).setCellStyle(styleData);
	        userRow.createCell(3).setCellValue(sch.getVaccineType());
	        userRow.getCell(3).setCellStyle(styleData);
	 	    userRow.createCell(4).setCellValue(sch.getBatch());
	        userRow.getCell(4).setCellStyle(styleData);
	        if (sch.getVaccineDate() == null) {
	        	userRow.createCell(5).setCellValue("Belum Dilakukan");
		        userRow.getCell(5).setCellStyle(styleData);
		        userRow.createCell(6).setCellValue("-");
		        userRow.getCell(6).setCellStyle(styleData);
		        userRow.createCell(7).setCellValue("-");
		        userRow.getCell(7).setCellStyle(styleData);
		        userRow.createCell(8).setCellValue("-");
		        userRow.getCell(8).setCellStyle(styleData);
	        } else {
	        	userRow.createCell(5).setCellValue("Sudah Dilakukan");
		        userRow.getCell(5).setCellStyle(styleData);
		        userRow.createCell(6).setCellValue(sch.getVaccineDate());
		        userRow.getCell(6).setCellStyle(styleData);
		        userRow.createCell(7).setCellValue(sch.getExpDate());
		        userRow.getCell(7).setCellStyle(styleData);
		        userRow.createCell(8).setCellValue(sch.getNotes());
		        userRow.getCell(8).setCellStyle(styleData);
	        }
	        
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.autoSizeColumn(5);
	    sheet.autoSizeColumn(6);
	    sheet.autoSizeColumn(7);
	    sheet.autoSizeColumn(8);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
	public static byte [] vaccineMst(List < VaccineMaster > mst) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Daftar Jadwal Imunisasi");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));

	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Daftar Jadwal Imunisasi");
        header1.getCell(0).setCellStyle(style1);
        
        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        
	    Row header = sheet.createRow(4);
	    header.createCell(0).setCellValue("No");
	    header.getCell(0).setCellStyle(style);
	    header.createCell(1).setCellValue("Kode Imunisasi");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Nama Imunisasi");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Merk Imunisasi");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Bulan Ke-");
	    header.getCell(4).setCellStyle(style);
	    header.createCell(5).setCellValue("Hari Kadaluarsa");
	    header.getCell(5).setCellStyle(style);
	    header.createCell(6).setCellValue("Catatan");
	    header.getCell(6).setCellStyle(style);
	    header.createCell(7).setCellValue("Status");
	    header.getCell(7).setCellStyle(style);
	   
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);
	    styleData.setAlignment(HorizontalAlignment.LEFT);
	    styleData.setVerticalAlignment(VerticalAlignment.TOP);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 5;
	    int no = 0;
	    
	    for ( VaccineMaster vm : mst ) {
	    	for ( int i = 0; i < vm.getDetail().size(); i++) {
	    		Row userRow =  sheet.createRow(rowCount++);
	    		userRow.createCell(0).setCellValue(no++);
	    		userRow.getCell(0).setCellStyle(styleData);
	    		userRow.createCell(1).setCellValue(vm.getVaccineCode());
	    		userRow.getCell(1).setCellStyle(styleData);
	    		userRow.createCell(2).setCellValue(vm.getVaccineName());
	    		userRow.getCell(2).setCellStyle(styleData);
	    		userRow.createCell(3).setCellValue(vm.getVaccineType());
	    		userRow.getCell(3).setCellStyle(styleData);
	    		userRow.createCell(4).setCellValue(vm.getDetail().get(i).getBatch());
	    		userRow.getCell(4).setCellStyle(styleData);
	    		userRow.createCell(5).setCellValue(vm.getExpDays());
	    		userRow.getCell(5).setCellStyle(styleData);
	    		userRow.createCell(6).setCellValue(vm.getNotes());
	    		userRow.getCell(6).setCellStyle(styleData);
	    		userRow.createCell(7).setCellValue(vm.getStatus());
	    		userRow.getCell(7).setCellStyle(styleData);
	    	} 
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.autoSizeColumn(5);
	    sheet.autoSizeColumn(6);
	    sheet.autoSizeColumn(7);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
	public static byte [] checkUpMst(List < CheckUpMaster > mst) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Daftar Jadwal Rekam Medis");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));

	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Daftar Jadwal Rekam Medis");
        header1.getCell(0).setCellStyle(style1);
        
        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        
	    Row header = sheet.createRow(4);
	    header.createCell(0).setCellValue("No");
	    header.getCell(0).setCellStyle(style);
	    header.createCell(1).setCellValue("Kode Kegiatan");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Nama Kegiatan");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Deskripsi");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Bulan Ke-");
	    header.getCell(4).setCellStyle(style);
	    header.createCell(5).setCellValue("Hari Selanjtunya");
	    header.getCell(5).setCellStyle(style);
	    header.createCell(6).setCellValue("Status");
	    header.getCell(6).setCellStyle(style);
	   
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);
	    styleData.setAlignment(HorizontalAlignment.LEFT);
	    styleData.setVerticalAlignment(VerticalAlignment.TOP);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 5;
	    int no = 0;
	    
	    for ( CheckUpMaster m : mst ) {
	    	Row userRow =  sheet.createRow(rowCount++);
	    	userRow.createCell(0).setCellValue(no++);
	    	userRow.getCell(0).setCellStyle(styleData);
	    	userRow.createCell(1).setCellValue(m.getCode());
	    	userRow.getCell(1).setCellStyle(styleData);
	    	userRow.createCell(2).setCellValue(m.getActName());
	    	userRow.getCell(2).setCellStyle(styleData);
	    	userRow.createCell(3).setCellValue(m.getDescription());
	    	userRow.getCell(3).setCellStyle(styleData);
	    	userRow.createCell(4).setCellValue(m.getBatch());
	    	userRow.getCell(4).setCellStyle(styleData);
	    	userRow.createCell(5).setCellValue(m.getNextCheckUpDays());
	    	userRow.getCell(5).setCellStyle(styleData);
	    	userRow.createCell(6).setCellValue(m.getStatus());
	    	userRow.getCell(6).setCellStyle(styleData);
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.autoSizeColumn(5);
	    sheet.autoSizeColumn(6);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
	public static byte [] listUser(List < User > user) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Daftar Data Orangtua dan Anak");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));

	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Daftar Data Orangtuan dan Anak");
        header1.getCell(0).setCellStyle(style1);
        
        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
	    Row header = sheet.createRow(4);
	    header.createCell(0).setCellValue("No");
	    header.getCell(0).setCellStyle(style);
	    header.createCell(1).setCellValue("Nama Orangtua");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Alamat");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Email");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Nomor Telepon");
	    header.getCell(4).setCellStyle(style);
	    header.createCell(5).setCellValue("Nama Anak");
	    header.getCell(5).setCellStyle(style);
	    header.createCell(6).setCellValue("Tanggal Lahir");
	    header.getCell(6).setCellStyle(style);
	    header.createCell(7).setCellValue("Jenis Kelamin");
	    header.getCell(7).setCellStyle(style);
	    header.createCell(8).setCellValue("Catatan");
	    header.getCell(8).setCellStyle(style);
	    
	    
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);;
	    styleData.setAlignment(HorizontalAlignment.CENTER);
	    styleData.setVerticalAlignment(VerticalAlignment.CENTER);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 5;
	    int no = 0;
	    int rowFrom = 5;
	    int rowTo = 0;
	
	    for ( User u : user ) {
	    	if ( u.getChild().size() > 1 ) {
	    		rowTo = rowFrom + ( u.getChild().size() - 1 );
	    		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,0,0));
	    		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,1,1));
	    		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,2,2));
	    		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,3,3));
	    		sheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,4,4));
	    	}
	    	rowFrom = rowTo + 1;
	    }
	    
	    Row userRow = null;
	    for ( User u : user ) {
	    	userRow = sheet.createRow(rowCount++);
	    	userRow.createCell(0).setCellValue(no++);
	    	userRow.getCell(0).setCellStyle(styleData);
	    	userRow.createCell(1).setCellValue(u.getFullname());
	    	userRow.getCell(1).setCellStyle(styleData);
	    	userRow.createCell(2).setCellValue(u.getAddress());
	    	userRow.getCell(2).setCellStyle(styleData);
	    	userRow.createCell(3).setCellValue(u.getEmail());
	    	userRow.getCell(3).setCellStyle(styleData);
	    	userRow.createCell(4).setCellValue(u.getPhone_no());
	    	userRow.getCell(4).setCellStyle(styleData);
	    	int count = 1;
	    	for ( Child c : u.getChild()) {
	    		userRow.createCell(5).setCellValue(c.getFullname());
		    	userRow.getCell(5).setCellStyle(styleData);
		    	userRow.createCell(6).setCellValue(Util.formatDate(c.getBirthDate()));
		    	userRow.getCell(6).setCellStyle(styleData);
		    	userRow.createCell(7).setCellValue(c.getGender());
		    	userRow.getCell(7).setCellStyle(styleData);
		    	userRow.createCell(8).setCellValue(c.getNotes());
		    	userRow.getCell(8).setCellStyle(styleData);
		    	if ( count < u.getChild().size() ) {
		    		userRow =  sheet.createRow(rowCount++);
		    	}
		    	count ++;
	    	}
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.autoSizeColumn(5);
	    sheet.autoSizeColumn(6);
	    sheet.autoSizeColumn(7);
	    sheet.autoSizeColumn(8);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
	public static byte [] auditTrail(List < AuditTrail > auditTrail, String startDate, String endDate) throws IOException{
		Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet("Daftar Aktivitas Admin");
	    sheet.setPrintGridlines(false);
	    sheet.addMergedRegion(new CellRangeAddress(2,2,0,4));

	    //Create Header 1
        CellStyle styleMuf = workbook.createCellStyle();
        Font fontMuf = workbook.createFont();
        styleMuf.setAlignment(HorizontalAlignment.LEFT); 
        styleMuf.setVerticalAlignment(VerticalAlignment.CENTER);
        fontMuf.setFontHeightInPoints((short)12.5);
        fontMuf.setBold(false);
        styleMuf.setFont(fontMuf);
        
        //Create Header 2
        Row header1 = sheet.createRow(2);
        CellStyle style1 = workbook.createCellStyle();
        Font font1 = workbook.createFont();
        style1.setAlignment(HorizontalAlignment.LEFT); 
        style1.setVerticalAlignment(VerticalAlignment.CENTER);
        font1.setFontHeightInPoints((short)12);
        font1.setBold(true);
        style1.setFont(font1);
        header1.createCell(0).setCellValue("Periode : " + startDate + " - " + endDate);
        header1.getCell(0).setCellStyle(style1);
        
        //create header row
        //create style for header cells
	    CellStyle style = workbook.createCellStyle();
	    Font font = workbook.createFont();
	    font.setFontName("Arial");
	    font.setFontHeightInPoints((short)10);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        
	    Row header = sheet.createRow(4);
	    header.createCell(0).setCellValue("No");
	    header.getCell(0).setCellStyle(style);
	    header.createCell(1).setCellValue("Username");
	    header.getCell(1).setCellStyle(style);
	    header.createCell(2).setCellValue("Aktivitas");
	    header.getCell(2).setCellStyle(style);
	    header.createCell(3).setCellValue("Detail");
	    header.getCell(3).setCellStyle(style);
	    header.createCell(4).setCellValue("Tanggal");
	    header.getCell(4).setCellStyle(style);
	   
	    CellStyle styleData = workbook.createCellStyle();
	    styleData.setWrapText(false);
	    styleData.setAlignment(HorizontalAlignment.LEFT);
	    styleData.setVerticalAlignment(VerticalAlignment.TOP);
	    Font fontdata = workbook.createFont();
	    fontdata.setFontName("Arial");
	    fontdata.setFontHeightInPoints((short) 10);
	    styleData.setFont(fontdata);
	    CellStyle styleDataTotal = workbook.createCellStyle();
	    Font fontTotal = workbook.createFont();
	    fontTotal.setBold(true);
	    styleDataTotal.setFont(fontTotal);
	    int rowCount = 5;
	    int no = 0;
	    
	    for ( AuditTrail m : auditTrail ) {
	    	Row userRow =  sheet.createRow(rowCount++);
	    	userRow.createCell(0).setCellValue(no++);
	    	userRow.getCell(0).setCellStyle(styleData);
	    	userRow.createCell(1).setCellValue(m.getActivity());
	    	userRow.getCell(1).setCellStyle(styleData);
	    	userRow.createCell(2).setCellValue(m.getValue2());
	    	userRow.getCell(2).setCellStyle(styleData);
	    	userRow.createCell(3).setCellValue(m.getValue1());
	    	userRow.getCell(3).setCellStyle(styleData);
	    	userRow.createCell(4).setCellValue(Util.formatDate(m.getCreatedDtm()));
	    	userRow.getCell(4).setCellStyle(styleData);
	    }
  	 	    
	    sheet.autoSizeColumn(1);
	    sheet.autoSizeColumn(2);
	    sheet.autoSizeColumn(3);
	    sheet.autoSizeColumn(4);
	    sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
	    
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    workbook.write(baos);
	    workbook.close();
		
	    return baos.toByteArray();
	}
	
}
