package com.eworld.repository.order;

import com.eworld.contstant.OrderStatus;
import com.eworld.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderCustomRepository{

    @Query("SELECT o FROM Order o WHERE o.account.username = :username")
    List<Order> findByUserName(@Param(value = "username") String username);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE MONTH(o.createdAt)= :month")
    public Long sumRevenueByMonth(@Param(value = "month")Integer month);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE YEAR(o.createdAt) = :years")
    public Long sumRevenueByYear(@Param(value = "years") Integer year);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE DAY(o.createdAt) = DAY(GETDATE()) AND o.status LIKE :status")
    public Long sumRevenueByToday(@Param(value = "status")OrderStatus status);
}
