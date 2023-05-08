package kg.mara.babyfood.service.impl;

import kg.mara.babyfood.dao.OrderDao;
import kg.mara.babyfood.dao.ProductDao;
import kg.mara.babyfood.dao.ReservedProductDao;
import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.entities.ProductEntity;
import kg.mara.babyfood.entities.ReservedProduct;
import kg.mara.babyfood.enums.OrderType;
import kg.mara.babyfood.model.Product;
import kg.mara.babyfood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final ReservedProductDao reservedProductDao;


    @Override
    public List<OrderEntity> getOrders() {
        List<OrderType> orderTypes = new ArrayList<>();
        orderTypes.add(OrderType.НОВЫЙ);
        return orderDao.findByOrderType(orderTypes);
    }
    @Override
    public List<OrderEntity> getOrdersInProcess() {
        List<OrderType> orderTypes = new ArrayList<>();
        orderTypes.add(OrderType.ДОСТАВКА);
        return orderDao.findByOrderType(orderTypes);
    }

    @Override
    public List<OrderEntity> getOrdersClosed() {
        List<OrderType> orderTypes = new ArrayList<>();
        orderTypes.add(OrderType.ЗАВЕРШЕН);
        orderTypes.add(OrderType.ОТМЕНЕН);
        return orderDao.findByOrderType(orderTypes);
    }

    @Override
    public void createOrder(List<Product> products, Double price, String id, String address) {

        List<ReservedProduct> reservedProducts = new ArrayList<>();
        for (Product p : products){
            ProductEntity pe = productDao.findByBarCode(p.getBarCode());
            pe.setCount(pe.getCount() - p.getCount());
            productDao.save(pe);
            ReservedProduct rp = new ReservedProduct();
            rp.setCount(p.getCount());
            rp.setProductId(pe.getId());
            rp.setBarcode(pe.getBarCode());
            rp.setName(pe.getName());
            rp.setPrice(pe.getPrice());
            reservedProductDao.save(rp);
            reservedProducts.add(rp);
        }
        OrderEntity order = new OrderEntity();
        order.setOrderDt(LocalDateTime.now());
        order.setOrderType(OrderType.НОВЫЙ);
        order.setTotalPrice(price);
        order.setReservedProducts(reservedProducts);
        order.setOrderId(id);
        order.setAddress(address);
        orderDao.save(order);
    }

    @Override
    public void saveOrder(OrderEntity order) {
        orderDao.save(order);
    }

    @Override
    public OrderEntity getOrder(Long order) {
        return orderDao.findById(order).orElse(null);
    }



    @Override
    public void cancelOrder(Long orderId) {
        OrderEntity orderEntity = orderDao.getById(orderId);
        List<ReservedProduct> reservedProducts = orderEntity.getReservedProducts();
        for (ReservedProduct rp : reservedProducts){
            ProductEntity pe = productDao.findByBarCode(rp.getBarcode());
            pe.setCount(pe.getCount() + rp.getCount());
            productDao.save(pe);
        }
        orderEntity.setOrderType(OrderType.ОТМЕНЕН);
        orderDao.save(orderEntity);
    }

    @Override
    public void closeOrder(Long orderId, List<String> barcode, List<Integer> counts) {
        OrderEntity orderEntity = orderDao.getById(orderId);
        List<ReservedProduct> rp = orderEntity.getReservedProducts();
        for (int i = 0; i < barcode.size(); i ++){
            ProductEntity pe = productDao.findByBarCode(barcode.get(i));
            pe.setCount(pe.getCount() + (rp.get(i).getCount() - counts.get(i)));
            rp.get(i).setCount(counts.get(i));
            productDao.save(pe);
        }
        orderEntity.setOrderType(OrderType.ЗАВЕРШЕН);
        orderEntity.setReservedProducts(rp);
        orderDao.save(orderEntity);

    }

}
