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
import org.springframework.stereotype.Service;

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
        return orderDao.findByOrderType();
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
}
