package kg.mara.babyfood.service;

import kg.mara.babyfood.entities.OrderEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ExcelService {
    void exportToExcel(OrderEntity order, HttpServletResponse response) throws IOException;

    void revisionToExcel(HttpServletResponse response) throws IOException;
}
