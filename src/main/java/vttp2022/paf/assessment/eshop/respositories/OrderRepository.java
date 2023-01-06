package vttp2022.paf.assessment.eshop.respositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.services.CustomerException;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepository {
	// TODO: Task 3
	@Autowired
	JdbcTemplate jdbcTemplate;

	public boolean insertOrder(Order ord) {
		return jdbcTemplate.update(INSERT_ORDER, 
		ord.getOrderId(),
		ord.getDeliveryId(),
		ord.getAddress(),
		ord.getEmail(),
		ord.getStatus()
		) > 0;
	}

	// public Order searchOrderByName( String name) {
	// 	System.out.println("its in here");
	// 	final List<Order> orders = new LinkedList<>();

	// 	final SqlRowSet rs = jdbcTemplate.queryForRowSet(SEARCH_ORDER_BY_NAME, name);

	// 	while (rs.next()) {
	// 		orders.add(Order.createOrder(rs));
	// 	}
	// 	return orders.get(0);
	// }

	public Optional<Customer> findCustomerByName(String name) throws CustomerException {
		// TODO: Task 3 
		System.out.println("find customer name");
		SqlRowSet rs = jdbcTemplate.queryForRowSet(CUSTOMER_BY_NAME, name);
		Customer customer = new Customer();
		rs.next();
		try {
			customer.setName(rs.getString("name"));
			customer.setAddress(rs.getString("address"));
			customer.setEmail(rs.getString("email"));
		} catch (Exception e) {
			throw new CustomerException(" error: customer"+ customer.getName()+ " not found");
		}
		return Optional.of(customer);
	}
}
