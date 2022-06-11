package com.mypackage.helpers;

import com.mypackage.model.Detail;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Workbook extends XSSFWorkbook {

    //    public Workbook create(){
//        Workbook workbook= (Workbook) new XSSFWorkbook();
//        return workbook;
//    }
    public XSSFSheet newSheet(XSSFWorkbook workbook, String name){
        XSSFSheet sheet=workbook.createSheet(name);
        XSSFCellStyle style= createStyleForTitle(workbook);
        Cell cell;
        Row row=sheet.createRow(0);
        // Name
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Наименование");
        cell.setCellStyle(style);
        // Length
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Длина");
        cell.setCellStyle(style);
        // width
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Ширина");
        cell.setCellStyle(style);
        // count
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Количество");
        cell.setCellStyle(style);

        return sheet;
    }

    public void addLine(XSSFSheet sheet, Detail detail){
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        Cell cell;

        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(detail.getName());

        if(detail.getName().length() > 20 && detail.getName().contains("\n")){ //Length of String for my test
            cell.getRow().setHeightInPoints(cell.getSheet().getDefaultRowHeightInPoints() * 2);
            CellStyle style = sheet.getWorkbook().createCellStyle(); //Create new style
            style.setWrapText(true);
            cell.setCellStyle(style); //Apply style to cell
        }
        sheet.autoSizeColumn(0);
        XSSFCellStyle alignmentStyle=setAlignment(sheet);


        // Length

        cell = row.createCell(1, CellType.NUMERIC);
        cell.setCellValue(detail.getLength());
        cell.setCellStyle(alignmentStyle);
        // width
        cell = row.createCell(2, CellType.NUMERIC);
        cell.setCellValue(detail.getWidth());
        cell.setCellStyle(alignmentStyle);
        // count
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue(detail.getCount());
        cell.setCellStyle(alignmentStyle);

    }

    private XSSFCellStyle setAlignment(XSSFSheet sheet){
        XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }

    private XSSFCellStyle createStyleForTitle(XSSFWorkbook workbook) {
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }
}
