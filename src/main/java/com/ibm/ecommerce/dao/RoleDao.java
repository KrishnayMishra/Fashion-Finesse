package com.ibm.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.ecommerce.entity.Role;

/**
 *
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface RoleDao extends CrudRepository<Role, String> {

}
