package com.ibm.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.ecommerce.entity.Cart;
import com.ibm.ecommerce.entity.User;

import java.util.List;

/**
 * 
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface CartDao extends CrudRepository<Cart, Integer > {
    public List<Cart> findByUser(User user);
}
