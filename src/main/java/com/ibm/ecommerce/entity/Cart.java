package com.ibm.ecommerce.entity;


import javax.persistence.*;

/**
 * 
 * This is an entity of cart in which I have described cartId, product and user as parameters.
 * We have also created one to one relationship between cart, product and cart,user.
 *
 */
@Entity
@Table
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartId;
    @OneToOne
    private Product product;
    @OneToOne
    private User user;

    public Cart(){

    }

    public Cart(Product product, User user) {
        this.product = product;
        this.user = user;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
