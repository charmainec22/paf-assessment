package vttp2022.paf.assessment.eshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.LineItemRepo;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@Service
public class OrderService {
    
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private LineItemRepo lineItemRepo;

    @Transactional(rollbackFor = OrderException.class)
    public void createNewOrder (Order ord) throws OrderException {
        System.out.println("start of application");
        orderRepository.insertOrder(ord);
        String orderId = ord.getOrderId();
        lineItemRepo.addLineItems(ord.getLineItems(), orderId);
    }

    // public List<Order> getCustomerOrder(String orderId) {
    //     //SqlRowSet rs = orderRepository.get
    // }

    // @Transactional
    // public void upsertOrder(Customer cust, Order order) throws OrderException {
    //     Optional<Customer> custO = orderRepository.findCustomerByName(cust.getName());
    //     if(!custO.isEmpty()) {
    //         orderRepository.insertOrder(order);
    //     }
        
    // }
} 
