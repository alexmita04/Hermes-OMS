package com.alexmita.ordermanagementsystem.domain.order;

import java.util.List;
import java.util.Objects;

public class Order implements OrderInterface{
    private final List<OrderItem> orderList;

    public Order(List<OrderItem> orderList) {
        this.orderList = orderList;
    }

    @Override
    public Double getTotalPrice() {
        return 0.00;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderList, order.orderList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderList);
    }
}
