package vttp2022.paf.assessment.eshop.controllers;
import java.io.StringReader;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.servlet.http.HttpSession;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;
import vttp2022.paf.assessment.eshop.services.OrderException;
import vttp2022.paf.assessment.eshop.services.OrderService;
import vttp2022.paf.assessment.eshop.services.WarehouseService;

@RestController
@RequestMapping(path = "/api")
public class OrderController {

	//TODO: Task 3
	// @PostMapping(path = "/")
	@Autowired
    OrderService orderService;

    // @PostMapping("/order")
    // public String postCheckout( Model model, HttpSession session) throws OrderException {
    //     List<LineItem> lineItems = (List<LineItem>) session.getAttribute("order");
    //     Order ord = (Order) session.getAttribute("checkoutOrder");
    //     System.out.println("HIT HERE");
    //     //destroy session
    //     session.invalidate();
    //     orderService.createNewOrder(ord);
    //     model.addAttribute("total", lineItems.size());

    //     return "status";
    // }
    
        @Autowired
        CustomerRepository customerRepository;

        @Autowired
        OrderRepository orderRepository;

        @Autowired
        WarehouseService warehouseService;

    @PostMapping("/order")
    public ResponseEntity<String> findCust(@RequestBody String body) {
        JsonReader reader = Json.createReader(new StringReader(body));
        JsonObject data = reader.readObject();
        String name = data.getString("name");
        JsonArray lineItems = data.getJsonArray("lineItems");
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("name", name);
        job.add("lineItems", lineItems);
        try {
            if(!customerRepository.findCustomerByName(name).isEmpty()){
                Order o = Order.createOrder(customerRepository.findCustomerByName(name).get());
                orderRepository.insertOrder(o);
                OrderStatus oss = warehouseService.dispatch(o);
                JsonObjectBuilder jjb = Json.createObjectBuilder();
                jjb.add("orderId", oss.getOrderId());
                jjb.add("deliveryId", oss.getDeliveryId());
                jjb.add("status", oss.getStatus());
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(jjb.build().toString());
            };
            
        } catch (Exception e) {
            // TODO: handle exception
            e.getMessage();
            
        }
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(job.toString());
    }
}
