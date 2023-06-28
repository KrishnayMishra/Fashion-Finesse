package com.ibm.ecommerce.service;

import com.ibm.ecommerce.configuration.JwtRequestFilter;
import com.ibm.ecommerce.dao.CartDao;
import com.ibm.ecommerce.dao.OrderDetailDao;
import com.ibm.ecommerce.dao.ProductDao;
import com.ibm.ecommerce.dao.UserDao;
import com.ibm.ecommerce.entity.*;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * I have implemented functionalities of fetching order details by their status which is either delivered or placed,
 *  placing order, getting email-confirmation with the help of smtp host and mime message internet standard and maintaing transactions.
 * 
 *
 */
@Service
public class OrderDetailService {

    private static final String ORDER_PLACED = "Placed";

    private static final String KEY = "rzp_test_AXBzvN2fkD4ESK";
    private static final String KEY_SECRET = "bsZmiVD7p1GMo6hAWiy4SHSH";
    private static final String CURRENCY = "INR";

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartDao cartDao;

    public List<OrderDetail> getAllOrderDetails(String status) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        if(status.equals("All")) {
            orderDetailDao.findAll().forEach(
                    x -> orderDetails.add(x)
            );
        } else {
            orderDetailDao.findByOrderStatus(status).forEach(
                    x -> orderDetails.add(x)
            );
        }


         return orderDetails;
    }

    public List<OrderDetail> getOrderDetails() {
        String currentUser = JwtRequestFilter.CURRENT_USER;
        User user = userDao.findById(currentUser).get();

        return orderDetailDao.findByUser(user);
    }

    public void placeOrder(OrderInput orderInput, boolean isSingleProductCheckout) {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();

        for (OrderProductQuantity o: productQuantityList) {
            Product product = productDao.findById(o.getProductId()).get();

            String currentUser = JwtRequestFilter.CURRENT_USER;
            User user = userDao.findById(currentUser).get();

            OrderDetail orderDetail = new OrderDetail(
                  orderInput.getFullName(),
                  orderInput.getFullAddress(),
                  orderInput.getContactNumber(),
                  orderInput.getEmailId(),
                    ORDER_PLACED,
                    product.getProductDiscountedPrice() * o.getQuantity(),
                    product,
                    user,
                    orderInput.getTransactionId()
            );

            // empty the cart.
            if(!isSingleProductCheckout) {
                List<Cart> carts = cartDao.findByUser(user);
                carts.stream().forEach(x -> cartDao.deleteById(x.getCartId()));
            }

            orderDetailDao.save(orderDetail);          
            
            sendOrderConfirmationEmail(orderDetail);
        }
        

        
    }
    
    private void sendOrderConfirmationEmail(OrderDetail orderDetail) {
        
        String senderEmail = "fashionfinesse.ibm@gmail.com";
        String senderPassword = "genkfpbwgkscszts";

        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(orderDetail.getOrderEmailId()));
            message.setSubject("Order Confirmation");
            
            
            StringBuilder contentBuilder = new StringBuilder();
            contentBuilder.append("Dear ").append(orderDetail.getOrderFullName()).append(",\n\n");
            contentBuilder.append("Thank you for placing an order with our e-commerce store.\n");
            contentBuilder.append("Order details:\n");
            contentBuilder.append("Order ID: ").append(orderDetail.getOrderId()).append("\n");
            contentBuilder.append("Please keep this email for your reference.\n\n");
            contentBuilder.append("Regards,\n Fashion Finesse Store");
            
            
            message.setText(contentBuilder.toString());

            
            Transport.send(message);

            System.out.println("Order confirmation email sent to: " + orderDetail.getOrderEmailId());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
 

    public void markOrderAsDelivered(Integer orderId) {
        OrderDetail orderDetail = orderDetailDao.findById(orderId).get();

        if(orderDetail != null) {
            orderDetail.setOrderStatus("Delivered");
            orderDetailDao.save(orderDetail);
        }

    }

    public TransactionDetails createTransaction(Double amount) {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100) );
            jsonObject.put("currency", CURRENCY);

            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

            Order order = razorpayClient.orders.create(jsonObject);

            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
            return transactionDetails;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionDetails prepareTransactionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
        return transactionDetails;
    }
}
