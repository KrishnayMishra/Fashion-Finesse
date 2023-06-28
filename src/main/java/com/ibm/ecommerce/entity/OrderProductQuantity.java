package com.ibm.ecommerce.entity;

/**
 * 
 * This is an entity of OrderProductQuantity in which I have described productId and quantity as parameters.
 *
 *
 */
public class OrderProductQuantity {

    private Integer productId;
    private Integer quantity;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

