package com.ibm.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.ecommerce.entity.User;

/**
 *
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface UserDao extends CrudRepository<User, String> {
}
