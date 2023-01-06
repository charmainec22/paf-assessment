package vttp2022.paf.assessment.eshop.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.Or;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class Order {

	private String orderId;
	private String deliveryId;
	private String name;
	private String address;
	private String email;
	private String status;
	private Date orderDate = new Date();
	private List<LineItem> lineItems = new LinkedList<>();


	public Order() {
		this.orderId = UUID.randomUUID().toString().substring(0,8);
		this.orderDate = new Date();
	}

	public String getOrderId() { return this.orderId; }
	public void setOrderId(String orderId) { this.orderId = orderId; }

	public String getDeliveryId() { return this.deliveryId; }
	public void setDeliveryId(String deliveryId) { this.deliveryId = deliveryId; }

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; }

	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; }

	public String getEmail() { return this.email; }
	public void setEmail(String email) { this.email = email; }

	public String getStatus() { return this.status; }
	public void setStatus(String status) { this.status = status; }

	public Date getOrderDate() { return this.orderDate; }
	public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

	public Customer getCustomer() { 
		Customer customer = new Customer();
		customer.setName(name);
		customer.setAddress(address);
		customer.setEmail(email);
		return customer;
	}
	public void setCustomer(Customer customer) {
		name = customer.getName();
		address = customer.getAddress();
		email = customer.getEmail();
	}

	

	public static Order createOrder(Customer rs) {
		Order order = new Order();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		String datenow = dateFormat.format(date);
		order.setAddress(rs.getAddress());
		order.setEmail(rs.getEmail());
		order.setName(rs.getName());
		//order.setOrderId(rs.get);
		//order.setOrderDate(dateFormat.parse(datenow));
		// List<Document> lineItemsDoc = rs
		return order;
	}

	public JsonObject toJson() {
		return Json.createObjectBuilder()
			.add("orderId", getOrderId())
			.add("deliveryId", getDeliveryId())
			.add("name", getName())
			.add("address", getAddress())
			.add("email", getEmail())
			.add("status", getStatus())
			.build();
	}

	public List<LineItem> getLineItems() { return this.lineItems; }
	public void setLineItems(List<LineItem> lineItems) { this.lineItems = lineItems; }
	public void addLineItem(LineItem lineItem) { this.lineItems.add(lineItem); }
}

