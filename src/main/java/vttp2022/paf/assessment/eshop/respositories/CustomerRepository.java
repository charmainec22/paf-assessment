package vttp2022.paf.assessment.eshop.respositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.services.CustomerException;

import static vttp2022.paf.assessment.eshop.respositories.Queries.*;


@Repository
public class CustomerRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// You cannot change the method's signature
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
