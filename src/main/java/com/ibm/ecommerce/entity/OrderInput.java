package com.ibm.ecommerce.entity;

import java.util.List;

/**
 * 
 * This is an entity class OrderInput in which I have stated the properties as fullName, fullAddress, contactNumber, EmailId, transactionId, and created a list of orderProductQuantityList 
 *
 *
 */
public class OrderInput {

    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String EmailId;
    private String transactionId;
    private List<OrderProductQuantity> orderProductQuantityList;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailId() {
        return EmailId;
    }

    public void setEmailId(String EmailId) {
        this.EmailId = EmailId;
    }

    public List<OrderProductQuantity> getOrderProductQuantityList() {
        return orderProductQuantityList;
    }

    public void setOrderProductQuantityList(List<OrderProductQuantity> orderProductQuantityList) {
        this.orderProductQuantityList = orderProductQuantityList;
    }
}
