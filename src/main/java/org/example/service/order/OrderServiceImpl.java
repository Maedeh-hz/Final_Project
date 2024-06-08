package org.example.service.order;

import org.example.base.service.BaseServiceImpl;
import org.example.model.Order;
import org.example.repository.order.OrderRepository;
import org.hibernate.SessionFactory;

public class OrderServiceImpl extends BaseServiceImpl<Order, Long, OrderRepository>
        implements OrderService {
    public OrderServiceImpl(OrderRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }
}
