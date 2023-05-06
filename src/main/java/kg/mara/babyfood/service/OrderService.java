package kg.mara.babyfood.service;

import kg.mara.babyfood.entities.OrderEntity;
import kg.mara.babyfood.model.Product;

import java.util.List;

public interface OrderService {
    List<OrderEntity> getOrders();

    void createOrder(List<Product> products, Double price, String id, String address);

    void saveOrder(OrderEntity order);

    OrderEntity getOrder(Long order);
}
