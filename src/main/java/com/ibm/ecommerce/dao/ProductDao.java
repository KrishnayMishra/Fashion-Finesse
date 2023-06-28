package com.ibm.ecommerce.dao;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.ecommerce.entity.Product;

/**
 *
 * This is basically a repository to perform crud operations inside the database.
 *
 */
@Repository
public interface ProductDao  extends CrudRepository<Product, Integer>{
         public List<Product>  findAll(Pageable pageable);
         public List<Product> findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
        		 String Key1,String Key2,Pageable pageable
        		 );
}
                                                                                                                                    