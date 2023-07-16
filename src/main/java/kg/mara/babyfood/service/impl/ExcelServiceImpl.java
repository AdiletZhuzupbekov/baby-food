package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.entities.ReservedProduct;
import kg.mara.babyfood.service.ExcelService;
import kg.mara.babyfood.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelServiceImpl implements ExcelService {
    private final ProductService productService;

    @Override
    public void exportToExcel(OrderEntity order, HttpServletResponse response) throws IOException {
        // Создаем новую книгу ExcelService
        Workbook workbook = new XSSFWorkbook();

        // Создаем новый лист
        Sheet sheet = workbook.createSheet("%"+order.getOrderId());

        // Создаем заголовок таблицы
        Row orderNum = sheet.createRow(0);
        orderNum.createCell(0).setCellValue("№"+order.getOrderId());
        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("Наименование товара");
        headerRow.createCell(1).setCellValue("Количество");
        headerRow.createCell(2).setCellValue("Цена за единицу");
        headerRow.createCell(3).setCellValue("общая цена");

        // Заполняем таблицу данными
        int rowNum = 3;
        double total = 0;
        for (ReservedProduct product : order.getReservedProducts()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(product.getName()+" - "+product.getBarcode());
            row.createCell(1).setCellValue("  " + product.getCount() + "шт");
            row.createCell(2).setCellValue(product.getPrice() + "сом");
            row.createCell(3).setCellValue((product.getCount() * product.getPrice()) + "сом");
            total += product.getCount() * product.getPrice();
        }
        Row row2 = sheet.createRow(rowNum);
        row2.createCell(2).setCellValue("Общая стоимость товара:");
        row2.createCell(3).setCellValue(total + " сом");
        Row row3 = sheet.createRow(rowNum + 1);
        row3.createCell(2).setCellValue("Стоимость с доставкой: ");
        row3.createCell(3).setCellValue(order.getTotalPrice() + "сом");
        Row row4 = sheet.createRow(rowNum + 2);
        row4.createCell(0).setCellValue("Имя: " + order.getName());
        Row row5 = sheet.createRow(rowNum + 3);
        row5.createCell(0).setCellValue("Номер: " + order.getPhone());
        Row row6 = sheet.createRow(rowNum + 4);
        row6.createCell(0).setCellValue("адрес: " + order.getAddress());



        // Устанавливаем размер колонок
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);




        // Устанавливаем заголовок файла и тип содержимого
        String name = order.getOrderId();
        response.setHeader("Content-Disposition", "attachment; filename="+ name +".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Записываем данные в поток
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();

        // Освобождаем ресурсы
        workbook.close();
    }

    @Override
    public void revisionToExcel(HttpServletResponse response) throws IOException {
        // Создаем новую книгу ExcelService
        Workbook workbook = new XSSFWorkbook();

        // Создаем новый лист
        Sheet sheet = workbook.createSheet("%" + LocalDate.now());

        // Создаем заголовок таблицы
        Row orderNum = sheet.createRow(0);
        orderNum.createCell(1).setCellValue("Дата: " + LocalDate.now());
        Row headerRow = sheet.createRow(2);
        headerRow.createCell(1).setCellValue("Наименование товара");
        headerRow.createCell(2).setCellValue("Описание");
        headerRow.createCell(3).setCellValue("Количество");

        // Заполняем таблицу данными
        int rowNum = 3;
        int  count = 1;
        List<ProductEntity> productEntities = productService.findAll();
        for (ProductEntity product : productEntities) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(count);
            row.createCell(1).setCellValue(product.getName());
            row.createCell(2).setCellValue(product.getDescription());
            row.createCell(3).setCellValue(product.getCount() + "шт");
            count++;
        }

        // Устанавливаем размер колонок
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);

        // Устанавливаем заголовок файла и тип содержимого
        String name = "Ревизия " + LocalDate.now();
        response.setHeader("Content-Disposition", "attachment; filename="+ name +".xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Записываем данные в поток
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.close();

        // Освобождаем ресурсы
        workbook.close();
    }

}
