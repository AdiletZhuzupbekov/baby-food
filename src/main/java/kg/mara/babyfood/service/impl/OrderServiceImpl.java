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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
    public void createOrder(List<Product> products, Integer price, String id, String name, String address, String phone) {

        List<ReservedProduct> reservedProducts = new ArrayList<>();
        for (Product p : products){
            ProductEntity pe = productDao.findByBarCode(p.getBarCode());
            pe.setCount(pe.getCount() - p.getCount());

            ReservedProduct rp = new ReservedProduct();
            rp.setCount(p.getCount());
            rp.setProductId(pe.getId());
            rp.setBarcode(pe.getBarCode());
            rp.setName(pe.getName());
            rp.setPrice(pe.getPrice());
            rp.setDescription(pe.getDescription());
            rp.setOriginalPrice(pe.getOriginalPrice());
            reservedProductDao.save(rp);
            productDao.save(pe);
            reservedProducts.add(rp);
        }
        OrderEntity order = new OrderEntity();
        order.setOrderDt(LocalDateTime.now());
        order.setOrderType(OrderType.НОВЫЙ);
        order.setTotalPrice(Double.valueOf(price));
        order.setReservedProducts(reservedProducts);
        order.setOrderId(id);
        order.setAddress(address);
        order.setName(name);
        order.setPhone(phone);
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
        List<ProductEntity> productEntities = new ArrayList<>();
        for (ReservedProduct rp : reservedProducts){
            ProductEntity pe = productDao.findByBarCode(rp.getBarcode());
            pe.setCount(pe.getCount() + rp.getCount());
            productEntities.add(pe);
        }
        productDao.saveAll(productEntities);
        orderEntity.setOrderType(OrderType.ОТМЕНЕН);
        orderDao.save(orderEntity);
    }

    @Override
    public void closeOrder(Long orderId, List<String> barcode, List<Integer> counts) {
        OrderEntity orderEntity = orderDao.getById(orderId);
        List<ReservedProduct> rp = orderEntity.getReservedProducts();
        Double total = 0.0;
        for (int i = 0; i < barcode.size(); i ++){
            ProductEntity pe = productDao.findByBarCode(barcode.get(i));
            pe.setCount(pe.getCount() + (rp.get(i).getCount() - counts.get(i)));
            rp.get(i).setCount(counts.get(i));
            total += rp.get(i).getPrice() * rp.get(i).getCount();
            productDao.save(pe);
        }
        orderEntity.setOrderType(OrderType.ЗАВЕРШЕН);
        orderEntity.setReservedProducts(rp);
        orderEntity.setTotalPrice(total);
        orderDao.save(orderEntity);

    }

    @Override
    public List<OrderEntity> getOrderByDeliver(LocalDateTime start, LocalDateTime end, String driver) {
        List<OrderEntity> orderEntities;
        if (start == null){
            start = LocalDateTime.now().minusMonths(3);
        }
        if (end == null){
            end = LocalDateTime.now();
        }
        if (driver.isEmpty()){
            orderEntities = orderDao.findByDate(start, end, OrderType.ЗАВЕРШЕН);
        }else {
            orderEntities = orderDao.findByDateAndDriver(start, end, OrderType.ЗАВЕРШЕН, driver);
        }
        return orderEntities;
    }

    @Override
    public Double getByDt(LocalDateTime startDt, LocalDateTime endDt) {
        double total = 0.0;
        try {
            List<OrderEntity> orderEntities = orderDao.findByDate(startDt, endDt, OrderType.ЗАВЕРШЕН);
            if (orderEntities != null) {
                for (OrderEntity o : orderEntities) {
                    for (ReservedProduct rp : o.getReservedProducts()) {
                        if (rp.getOriginalPrice() != null && rp.getPrice() != null) {
                            total += (rp.getPrice() * rp.getCount()) - (rp.getOriginalPrice() * rp.getCount());
                        }
                    }
                }
            }
            return total;
        }catch (Exception e){
            log.info(e.getMessage());
            return total;
        }
    }
}
