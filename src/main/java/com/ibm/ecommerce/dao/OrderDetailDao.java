package com.ibm.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibm.ecommerce.entity.OrderDetail;
import com.ibm.ecommerce.entity.User;

import java.util.List;

/**
 *
 * This is basically a repository to perform crud operations inside the database.
 *
 */
public interface OrderDetailDao extends CrudRepository<OrderDetail, Integer> {
    public List<OrderDetail> findByUser(User user);

    public List<OrderDetail> findByOrderStatus(String status);
}
