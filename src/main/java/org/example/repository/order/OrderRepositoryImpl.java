package org.example.repository.order;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Order;
import org.hibernate.SessionFactory;

public class OrderRepositoryImpl extends BaseRepositoryImpl<Order, Long>
        implements OrderRepository{
    public OrderRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Order> getClassName() {
        return Order.class;
    }
}
