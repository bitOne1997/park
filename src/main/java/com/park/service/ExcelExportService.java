package com.park.service;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.park.model.AccessDetail;
import com.park.model.BusinessCarport;
import com.park.model.Channel;
import com.park.model.ChannelDetail;
import com.park.model.DataUsageCardDetail;
import com.park.model.GongzxRecord;
import com.park.model.PosChargeData;
import com.park.model.Posdata;
import com.park.model.SingleParkInfo;
@Service
public class ExcelExportService {
	public void produceExceldataDataUsage(String title, String[] headers, List<DataUsageCardDetail> dataset,
			HSSFWorkbook workbook) {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		for(int j=0;j<dataset.size();j++){
			HSSFRow row1 = sheet.createRow(j+1);
			DataUsageCardDetail dataUsageCardDetail=dataset.get(j);
					
				HSSFCell cell1 = row1.createCell(0);				
				cell1.setCellStyle(style2);
				cell1.setCellValue(dataUsageCardDetail.getParkName());
				
				HSSFCell cell2 = row1.createCell(1);				
				cell2.setCellStyle(style2);
				cell2.setCellValue(dataUsageCardDetail.getCardNumber());
			
				HSSFCell cell3 = row1.createCell(2);				
				cell3.setCellStyle(style2);
				cell3.setCellValue(dataUsageCardDetail.getPhoneNumber());
				
				HSSFCell cell7 = row1.createCell(3);				
				cell7.setCellStyle(style2);			
				cell7.setCellValue(dataUsageCardDetail.getDataUsage());
				
				HSSFCell cell4 = row1.createCell(4);				
				cell4.setCellStyle(style2);
				String typeValue="";
				switch (dataUsageCardDetail.getType()) {
				case 0:
					typeValue="出入口卡";
					break;
				case 1:
					typeValue="车位发布器卡";
					break;
				case 2:
					typeValue="室外车位卡";
					break;
				default:
					typeValue="未知类型";
					break;
				}
				cell4.setCellValue(typeValue);
				
				HSSFCell cell5 = row1.createCell(5);				
				cell5.setCellStyle(style2);			
				cell5.setCellValue(dataUsageCardDetail.getPosition());
				
				HSSFCell cell6 = row1.createCell(6);				
				cell6.setCellStyle(style2);
				String zuobiao="("+dataUsageCardDetail.getLatitude()+","+
				dataUsageCardDetail.getLongitude()+")";
				cell6.setCellValue(zuobiao);															
		}
	}
	public void produceMonthCountsInfoExcel(String title, String[] headers, List<Map<String, Object>> dataset,
			XSSFWorkbook workbook) {
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为25个字节
		sheet.setDefaultColumnWidth(25);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		for(int j=0;j<dataset.size();j++){
			XSSFRow row1 = sheet.createRow(j+1);
			Map<String, Object> posdata=dataset.get(j);
			
			XSSFCell cell1 = row1.createCell(0);				
			cell1.setCellStyle(style2);
			
			cell1.setCellValue(posdata.get("owner")==null?"":(String)posdata.get("owner"));
			
			XSSFCell cell2 = row1.createCell(1);				
			cell2.setCellStyle(style2);
			cell2.setCellValue((long)posdata.get("count"));
			
			XSSFCell cell3 = row1.createCell(2);				
			cell3.setCellStyle(style2);
			cell3.setCellValue((String)posdata.get("cardNumber"));
			
			XSSFCell cell4 = row1.createCell(3);				
			cell4.setCellStyle(style2);
			cell4.setCellValue(posdata.get("type")==null?-1:(int)posdata.get("type"));
						
		}
	}
	public void produceExceldataPosData(String title, String[] headers, List<Posdata> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为25个字节
		sheet.setDefaultColumnWidth(25);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		for(int j=0;j<dataset.size();j++){
			XSSFRow row1 = sheet.createRow(j+1);
			Posdata posdata=dataset.get(j);
			
			XSSFCell cell1 = row1.createCell(0);				
			cell1.setCellStyle(style2);
			cell1.setCellValue(posdata.getCardsnr());
			
			XSSFCell cell2 = row1.createCell(1);				
			cell2.setCellStyle(style2);
			cell2.setCellValue(posdata.getSitename());
		
			XSSFCell cell3 = row1.createCell(2);				
			cell3.setCellStyle(style2);
			cell3.setCellValue(posdata.getBackbyte());
			
	
			
			XSSFCell cell5 = row1.createCell(3);				
			cell5.setCellStyle(style2);			
			if (posdata.getIsarrearage()==false) {
				cell5.setCellValue(posdata.getMode()==0?"进场":"出场");				
			}
			else {
				cell5.setCellValue("补交");				
			}
		
			XSSFCell cell6 = row1.createCell(4);				
			cell6.setCellStyle(style2);
			cell6.setCellValue(posdata.getUserid());
			
			XSSFCell cell7 = row1.createCell(5);				
			cell7.setCellStyle(style2);
			cell7.setCellValue(posdata.getPossnr());
			
			XSSFCell cell8 = row1.createCell(6);				
			cell8.setCellStyle(style2);
			cell8.setCellValue(Float.parseFloat(posdata.getMoney().toString()));
		
			XSSFCell cell9 = row1.createCell(7);				
			cell9.setCellStyle(style2);
			cell9.setCellValue(Float.parseFloat(posdata.getGiving().toString()));
			
			XSSFCell cell10 = row1.createCell(8);				
			cell10.setCellStyle(style2);
			cell10.setCellValue(Float.parseFloat(posdata.getRealmoney().toString()));
			
			XSSFCell cell11 = row1.createCell(9);				
			cell11.setCellStyle(style2);
			cell11.setCellValue(Float.parseFloat(posdata.getReturnmoney().toString()));
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			XSSFCell cell12 = row1.createCell(10);				
			cell12.setCellStyle(style2);
			cell12.setCellValue(sdf.format(posdata.getStarttime()));
			
			XSSFCell cell13 = row1.createCell(11);				
			cell13.setCellStyle(style2);
			String date="";
			if (posdata.getEndtime()!=null) {	
				date=sdf.format(posdata.getEndtime());
			}	
			cell13.setCellValue(date);
			
			
		}
	}
	public void produceExceldataPosChargeData(String title, String[] headers, List<PosChargeData> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
				XSSFSheet sheet = workbook.createSheet(title);
				// 设置表格默认列宽度为25个字节
				sheet.setDefaultColumnWidth(25);
				// 生成一个样式
				XSSFCellStyle style = workbook.createCellStyle();
				// 设置这些样式
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 生成一个字体
				XSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.VIOLET.index);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);

				XSSFCellStyle style2 = workbook.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				// 生成另一个字体
				XSSFFont font2 = workbook.createFont();
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 把字体应用到当前的样式
				style2.setFont(font2);
				
				// 产生表格标题行
				XSSFRow row = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					XSSFRichTextString text = new XSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
				for(int j=0;j<dataset.size();j++){
					XSSFRow row1 = sheet.createRow(j+1);
					PosChargeData posdata=dataset.get(j);
					
					XSSFCell cell1 = row1.createCell(0);				
					cell1.setCellStyle(style2);
					cell1.setCellValue(posdata.getCardNumber());
					
					XSSFCell cell2 = row1.createCell(1);				
					cell2.setCellStyle(style2);
					cell2.setCellValue(posdata.getParkDesc());
				
					XSSFCell cell3 = row1.createCell(2);				
					cell3.setCellStyle(style2);
					cell3.setCellValue(posdata.getPortNumber());
					
					XSSFCell cell4 = row1.createCell(3);				
					cell4.setCellStyle(style2);
					cell4.setCellValue(posdata.getOperatorId());
				
					XSSFCell cell5 = row1.createCell(4);				
					cell5.setCellStyle(style2);
					cell5.setCellValue(posdata.isPaidCompleted()==false?"未收费":"已收费");
					
					XSSFCell cell6 = row1.createCell(5);				
					cell6.setCellStyle(style2);
					cell6.setCellValue(posdata.getPaidMoney());
					
					NumberFormat df = NumberFormat.getNumberInstance();
					//DecimalFormat df = new DecimalFormat("#.00");
					XSSFCell cell7 = row1.createCell(6);				
					cell7.setCellStyle(style2);
			        df.setMaximumFractionDigits(2);
					cell7.setCellValue(df.format(posdata.getChargeMoney()));
					
					XSSFCell cell8 = row1.createCell(7);				
					cell8.setCellStyle(style2);
					cell8.setCellValue(posdata.getGivenMoney());
				
					XSSFCell cell9 = row1.createCell(8);				
					cell9.setCellStyle(style2);
					cell9.setCellValue(posdata.getChangeMoney());
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					XSSFCell cell10 = row1.createCell(9);				
					cell10.setCellStyle(style2);
					cell10.setCellValue(sdf.format(posdata.getEntranceDate()));
				
					XSSFCell cell11 = row1.createCell(10);				
					cell11.setCellStyle(style2);
					if (posdata.getExitDate()!=null) {
						cell11.setCellValue(sdf.format(posdata.getExitDate()));
					}
					else {
						cell11.setCellValue("");
					}
					
					XSSFCell cell12= row1.createCell(11);				
					cell12.setCellStyle(style2);
					cell12.setCellValue(posdata.getRejectReason());
					
					XSSFCell cell13 = row1.createCell(12);				
					cell13.setCellStyle(style2);
					cell13.setCellValue(posdata.getDiscount());
					
				}
				
	}
	
	public void produceExceldataChannelData(String title, String[] headers, List<ChannelDetail> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
				XSSFSheet sheet = workbook.createSheet(title);
				// 设置表格默认列宽度为25个字节
				sheet.setDefaultColumnWidth(25);
				// 生成一个样式
				XSSFCellStyle style = workbook.createCellStyle();
				// 设置这些样式
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 生成一个字体
				XSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.VIOLET.index);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);

				XSSFCellStyle style2 = workbook.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				// 生成另一个字体
				XSSFFont font2 = workbook.createFont();
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 把字体应用到当前的样式
				style2.setFont(font2);
				
				// 产生表格标题行
				XSSFRow row = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					XSSFRichTextString text = new XSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
				for(int j=0;j<dataset.size();j++){
					XSSFRow row1 = sheet.createRow(j+1);
					ChannelDetail channel=dataset.get(j);
					
					XSSFCell cell0 = row1.createCell(0);				
					cell0.setCellStyle(style2);
					cell0.setCellValue(channel.getId());
					
					XSSFCell cell1 = row1.createCell(1);				
					cell1.setCellStyle(style2);
					cell1.setCellValue(channel.getParkName());
					
					XSSFCell cell2 = row1.createCell(2);				
					cell2.setCellStyle(style2);
					cell2.setCellValue(channel.getChannelId());
					
					XSSFCell cell3 = row1.createCell(3);				
					cell3.setCellStyle(style2);
					if (channel.getMacId()!=null) {
						cell3.setCellValue(channel.getMacId());
					} else {
						cell3.setCellValue("");
					}
					
					XSSFCell cell4 = row1.createCell(4);				
					cell4.setCellStyle(style2);
					if (channel.getChannelFlag()!=null&&channel.getChannelFlag().equals("1")) {
						cell4.setCellValue("入口");
					}else {
						cell4.setCellValue("出口");
					}		
					
					XSSFCell cell5 = row1.createCell(5);				
					cell5.setCellStyle(style2);
					if (channel.getStatus()!=null&&channel.getStatus().equals("1")) {
						cell5.setCellValue("可用");
					}else {
						cell5.setCellValue("不可用");
					}
					
					XSSFCell cell7 = row1.createCell(5);				
					cell7.setCellStyle(style2);
					if (channel.getDescription()!=null) {
						cell7.setCellValue(channel.getDescription());
					}
					else {
						cell7.setCellValue("");
					}
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					XSSFCell cell8 = row1.createCell(8);				
					cell8.setCellStyle(style2);
					cell8.setCellValue(sdf.format(channel.getDate()));
					}
				
	}
	
	public void produceExcelAllChannelData(String title, String[] headers, List<ChannelDetail> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
				XSSFSheet sheet = workbook.createSheet(title);
				// 设置表格默认列宽度为25个字节
				sheet.setDefaultColumnWidth(25);
				// 生成一个样式
				XSSFCellStyle style = workbook.createCellStyle();
				// 设置这些样式
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 生成一个字体
				XSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.VIOLET.index);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);

				XSSFCellStyle style2 = workbook.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				// 生成另一个字体
				XSSFFont font2 = workbook.createFont();
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 把字体应用到当前的样式
				style2.setFont(font2);
				
				// 产生表格标题行
				XSSFRow row = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					XSSFRichTextString text = new XSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
				for(int j=0;j<dataset.size();j++){
					XSSFRow row1 = sheet.createRow(j+1);
					ChannelDetail channel=dataset.get(j);
					
					XSSFCell cell0 = row1.createCell(0);				
					cell0.setCellStyle(style2);
					cell0.setCellValue(channel.getId());
					
					XSSFCell cell1 = row1.createCell(1);				
					cell1.setCellStyle(style2);
					cell1.setCellValue(channel.getParkName());
					
					XSSFCell cell2 = row1.createCell(2);				
					cell2.setCellStyle(style2);
					cell2.setCellValue(channel.getChannelId());
					
					XSSFCell cell3 = row1.createCell(3);				
					cell3.setCellStyle(style2);
					if (channel.getMac()!=null) {
						cell3.setCellValue(channel.getMac());
					} else {
						cell3.setCellValue("");
					}
					
					XSSFCell cell4 = row1.createCell(4);				
					cell4.setCellStyle(style2);
					if (channel.getChannelFlag()!=null&&channel.getChannelFlag().equals("1")) {
						cell4.setCellValue("入口");
					}else {
						cell4.setCellValue("出口");
					}		
					
					XSSFCell cell5 = row1.createCell(5);				
					cell5.setCellStyle(style2);
					if (channel.getStatus()!=null&&channel.getStatus().equals("1")) {
						cell5.setCellValue("可用");
					}else {
						cell5.setCellValue("不可用");
					}
					
					XSSFCell cell6 = row1.createCell(6);				
					cell6.setCellStyle(style2);
					if (channel.getDescription()!=null) {
						cell6.setCellValue(channel.getDescription());
					}
					else {
						cell6.setCellValue("");
					}
					
					XSSFCell cell7 = row1.createCell(7);				
					cell7.setCellStyle(style2);
					cell7.setCellValue(channel.getDate());
					}
				
	}
	
	public void produceExceldataGongzx(String title, String[] headers, List<GongzxRecord> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
				XSSFSheet sheet = workbook.createSheet(title);
				// 设置表格默认列宽度为25个字节
				sheet.setDefaultColumnWidth(25);
				// 生成一个样式
				XSSFCellStyle style = workbook.createCellStyle();
//				HSSFCellStyle cellStyle=workbook.createCellStyle(); 
				// 设置这些样式
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 生成一个字体
				XSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.VIOLET.index);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);

				XSSFCellStyle style2 = workbook.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				// 生成另一个字体
				XSSFFont font2 = workbook.createFont();
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 把字体应用到当前的样式
				style2.setFont(font2);
				
				// 产生表格标题行
				XSSFRow row = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					XSSFRichTextString text = new XSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
				for(int j=0;j<dataset.size();j++){
					XSSFRow row1 = sheet.createRow(j+1);
					GongzxRecord gongzxr=dataset.get(j);
					
					XSSFCell cell1 = row1.createCell(0);				
					cell1.setCellStyle(style2);
					cell1.setCellValue(gongzxr.getCarNumber());
					
					XSSFCell cell2 = row1.createCell(1);				
					cell2.setCellStyle(style2);
					cell2.setCellValue(gongzxr.getCardNumber());
				
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					XSSFCell cell3 = row1.createCell(2);				
					cell3.setCellStyle(style2);
					cell3.setCellValue(sdf.format(gongzxr.getArriveTime()));
					
					XSSFCell cell4 = row1.createCell(3);				
					cell4.setCellStyle(style2);
					cell4.setCellValue(gongzxr.getParkId());
				
					XSSFCell cell5 = row1.createCell(4);				
					cell5.setCellStyle(style2);
					cell5.setCellValue(gongzxr.getParkName());
					
					XSSFCell cell6 = row1.createCell(5);				
					cell6.setCellStyle(style2);
					cell6.setCellValue(gongzxr.getStopType());
				
					XSSFCell cell7 = row1.createCell(6);				
					cell7.setCellStyle(style2);
					cell7.setCellValue(gongzxr.getShouldCharge());
					
					XSSFCell cell8 = row1.createCell(7);				
					cell8.setCellStyle(style2);
					cell8.setCellValue(gongzxr.getDiscount());
				
					XSSFCell cell9 = row1.createCell(8);				
					cell9.setCellStyle(style2);
					cell9.setCellValue(gongzxr.getRealPay());
					
					XSSFCell cell10 = row1.createCell(9);				
					cell10.setCellStyle(style2);
					cell10.setCellValue(gongzxr.getPicturePath());
				
					XSSFCell cell11 = row1.createCell(10);				
					cell11.setCellStyle(style2);
					cell11.setCellValue(gongzxr.getOther());
					
					XSSFCell cell12= row1.createCell(11);				
					cell12.setCellStyle(style2);
					cell12.setCellValue(gongzxr.getTradeNumber());
					
					XSSFCell cell13 = row1.createCell(12);				
					cell13.setCellStyle(style2);
/*					cell13.setCellValue(sdf.format(gongzxr.getLeaveTime()));*/
					if (gongzxr.getLeaveTime()!=null) {
						cell13.setCellValue(sdf.format(gongzxr.getLeaveTime()));
					} else {
						cell13.setCellValue("");
					}
					
				}
				
	}
	
	public void produceExceldataAccessDetail(String title, String[] headers, List<AccessDetail> dataset,
			XSSFWorkbook workbook) {
		// 生成一个表格
				XSSFSheet sheet = workbook.createSheet(title);
				// 设置表格默认列宽度为15个字节
				sheet.setDefaultColumnWidth(15);
				// 生成一个样式
				XSSFCellStyle style = workbook.createCellStyle();
				// 设置这些样式
				style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
				style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				// 生成一个字体
				XSSFFont font = workbook.createFont();
				font.setColor(HSSFColor.VIOLET.index);
				font.setFontHeightInPoints((short) 12);
				font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				// 把字体应用到当前的样式
				style.setFont(font);

				XSSFCellStyle style2 = workbook.createCellStyle();
				style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
				style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
				style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
				style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
				style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
				// 生成另一个字体
				XSSFFont font2 = workbook.createFont();
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				// 把字体应用到当前的样式
				style2.setFont(font2);
				
				// 产生表格标题行
				XSSFRow row = sheet.createRow(0);
				for (int i = 0; i < headers.length; i++) {
					XSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					XSSFRichTextString text = new XSSFRichTextString(headers[i]);
					cell.setCellValue(text);
				}
				for(int j=0;j<dataset.size();j++){
					XSSFRow row1 = sheet.createRow(j+1);
					AccessDetail accessDetail=dataset.get(j);
					
					XSSFCell cell1 = row1.createCell(0);				
					cell1.setCellStyle(style2);
					cell1.setCellValue(accessDetail.getId());
					
					XSSFCell cell2 = row1.createCell(1);				
					cell2.setCellStyle(style2);
					cell2.setCellValue(accessDetail.getName());
					
					XSSFCell cell3 = row1.createCell(2);				
					cell3.setCellStyle(style2);
					cell3.setCellValue(accessDetail.getChannelNumber());
					
					XSSFCell cell4 = row1.createCell(3);				
					cell4.setCellStyle(style2);
					cell4.setCellValue(accessDetail.getDate());
					
					}
	}
	
	public void produceExceldataChannel(String title, String[] headers,List<ChannelDetail> dataset,XSSFWorkbook workbook){
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth(15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.VIOLET.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);

		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		// 产生表格标题行
		XSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		
		for(int j=0;j<dataset.size();j++){
			XSSFRow row1 = sheet.createRow(j+1);
			ChannelDetail channelDetail=dataset.get(j);
			
			XSSFCell cell1 = row1.createCell(0);				
			cell1.setCellStyle(style2);
			cell1.setCellValue(channelDetail.getId());
			
			XSSFCell cell2 = row1.createCell(1);				
			cell2.setCellStyle(style2);
			cell2.setCellValue(channelDetail.getParkName());
			
			XSSFCell cell3 = row1.createCell(2);				
			cell3.setCellStyle(style2);
			if (channelDetail.getMac()!=null) {
				cell3.setCellValue(channelDetail.getMac());
			} else {
				cell3.setCellValue("");
			}
			
			
			XSSFCell cell4 = row1.createCell(3);				
			cell4.setCellStyle(style2);
			if (channelDetail.getChannelFlag()!=null&&channelDetail.getChannelFlag().equals("1")) {
				cell4.setCellValue("入口");
			}else {
				cell4.setCellValue("出口");
			}		
			
			XSSFCell cell5 = row1.createCell(4);				
			cell5.setCellStyle(style2);
			if (channelDetail.getDescription()!=null) {
				cell5.setCellValue(channelDetail.getDescription());
			}
			else {
				cell5.setCellValue("");
			}
			
			
			XSSFCell cell6 = row1.createCell(5);				
			cell6.setCellStyle(style2);
			cell6.setCellValue(channelDetail.getDate());
			}
	}
	
//	public void produceExcelSingleParkInfo(String title, String[] headers,List<SingleParkInfo> dataset,XSSFWorkbook workbook){
//		// 生成一个表格
//		XSSFSheet sheet = workbook.createSheet(title);
//		// 设置表格默认列宽度为15个字节
//		sheet.setDefaultColumnWidth(15);
//		// 生成一个样式
//		XSSFCellStyle style = workbook.createCellStyle();
//		// 设置这些样式
//		style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
//		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体
//		XSSFFont font = workbook.createFont();
//		font.setColor(HSSFColor.VIOLET.index);
//		font.setFontHeightInPoints((short) 12);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		// 把字体应用到当前的样式
//		style.setFont(font);
//
//		XSSFCellStyle style2 = workbook.createCellStyle();
//		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
//		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		// 生成另一个字体
//		XSSFFont font2 = workbook.createFont();
//		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
//		// 把字体应用到当前的样式
//		style2.setFont(font2);
//		// 产生表格标题行
//		XSSFRow row = sheet.createRow(0);
//		for (int i = 0; i < headers.length; i++) {
//			XSSFCell cell = row.createCell(i);
//			cell.setCellStyle(style);
//			XSSFRichTextString text = new XSSFRichTextString(headers[i]);
//			cell.setCellValue(text);
//		}
//		
//		for(int j=0;j<dataset.size();j++){
//			XSSFRow row1 = sheet.createRow(j+1);
//			SingleParkInfo channelDetail=dataset.get(j);
//			
//			XSSFCell cell1 = row1.createCell(0);				
//			cell1.setCellStyle(style2);
//			cell1.setCellValue(channelDetail.getId());
//			
//			XSSFCell cell2 = row1.createCell(1);				
//			cell2.setCellStyle(style2);
//			cell2.setCellValue(channelDetail.getParkName());
//			
//			XSSFCell cell3 = row1.createCell(2);				
//			cell3.setCellStyle(style2);
//			if (channelDetail.getMac()!=null) {
//				cell3.setCellValue(channelDetail.getMac());
//			} else {
//				cell3.setCellValue("");
//			}
//			
//			
//			XSSFCell cell4 = row1.createCell(3);				
//			cell4.setCellStyle(style2);
//			if (channelDetail.getChannelFlag()!=null&&channelDetail.getChannelFlag().equals("1")) {
//				cell4.setCellValue("入口");
//			}else {
//				cell4.setCellValue("出口");
//			}		
//			
//			XSSFCell cell5 = row1.createCell(4);				
//			cell5.setCellStyle(style2);
//			if (channelDetail.getDescription()!=null) {
//				cell5.setCellValue(channelDetail.getDescription());
//			}
//			else {
//				cell5.setCellValue("");
//			}
//			
//			
//			XSSFCell cell6 = row1.createCell(5);				
//			cell6.setCellStyle(style2);
//			cell6.setCellValue(channelDetail.getDate());
//			}
//	}
}
