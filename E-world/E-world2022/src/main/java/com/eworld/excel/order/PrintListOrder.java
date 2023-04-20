package com.eworld.excel.order;

import com.eworld.dto.order.OrderDto;
import com.eworld.excel.BaseExcel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrintListOrder implements BaseExcel {
    private XSSFWorkbook xssfWorkbook;
    private XSSFSheet sheet;

    private List<OrderDto> orderDtoList;

    public PrintListOrder(List<OrderDto> orderDtoList){
        this.orderDtoList =orderDtoList;
        xssfWorkbook = new XSSFWorkbook();
    }
    @Override
    public void writeHeaderLine() {
        sheet = xssfWorkbook.createSheet("ListOrder");

        Row row = sheet.createRow(0);

        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Mã đơn hàng", style);
        createCell(row, 1, "Địa chỉ đơn hàng", style);
        createCell(row, 2, "Phương thức thanh toán", style);
        createCell(row, 3, "tổng bill", style);
        createCell(row,4,"Người đặt hàng", style);
        createCell(row, 5, "Số điện thoại của đơn hàng", style);
    }

    @Override
    public void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else if (value instanceof  Double){
            cell.setCellValue(String.valueOf(value));
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    @Override
    public void writeDataLines() {
        int rowCount = 1;
        CellStyle style = xssfWorkbook.createCellStyle();
        XSSFFont font = xssfWorkbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (OrderDto orderDto : orderDtoList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, orderDto.getId(), style);
            createCell(row, columnCount++, orderDto.getAddress(), style);
            createCell(row, columnCount++, String.valueOf(orderDto.getStatus()), style);
            createCell(row, columnCount++, orderDto.getTotalPrice(), style);
            createCell(row,columnCount++,orderDto.getAccount().getAccountProfile().getFirstName(),style);
            createCell(row, columnCount++, orderDto.getPhone(), style);
        }
    }

    public void export() throws IOException {
        String filedownload ="C:\\Users\\ACER\\Downloads";
        writeHeaderLine();
        writeDataLines();
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());
        File file = new File(filedownload,"listOrder_" + currentDateTime+".xlsx");
        if(file.exists()){
            file.delete();
            file.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();

    }
}
