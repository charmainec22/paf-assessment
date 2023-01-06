package vttp2022.paf.assessment.eshop.services;

import java.io.Reader;
import java.io.StringReader;

import javax.print.DocFlavor.STRING;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

public class WarehouseService {

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		// TODO: Task 4
		String SERVER_NAME = "http://paf.chukless.com/dispatch/";
		String FINAL_URL = SERVER_NAME + order.getOrderId();

		JsonObjectBuilder ob = Json.createObjectBuilder();
		ob.add("orderId", order.getOrderId());
		ob.add("name", order.getName());
		ob.add("address", order.getAddress());
		ob.add("email", order.getEmail());
		JsonArrayBuilder ja = Json.createArrayBuilder();
		for (LineItem li : order.getLineItems()){
			JsonObjectBuilder job = Json.createObjectBuilder();
			job.add("item", li.getItem());
			job.add("quantity", li.getQuantity());
			ja.add(job.build());
		}
		ob.add("lineItems", ja.build());
		ob.add("createdBy", "Chia Shu Teng Charmaine");
		JsonObject o = ob.build();
		RequestEntity<String> req = RequestEntity.post(FINAL_URL).contentType(MediaType.APPLICATION_JSON).body(o.toString());
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> re = null;
		try{
			re = rt.exchange(req, String.class);
			String payload = re.getBody();
			JsonReader reader = Json.createReader(new StringReader(payload));
			JsonObject obj = reader.readObject();
			OrderStatus os = new OrderStatus();
			os.setOrderId(obj.getString("orderId"));
			os.setDeliveryId(obj.getString("deliveryId"));
			os.setStatus("dispatch");
			return os;
		} catch (Exception e) {
			//TODO ADD EXCEPTION
			return null;
		}

	}
}
