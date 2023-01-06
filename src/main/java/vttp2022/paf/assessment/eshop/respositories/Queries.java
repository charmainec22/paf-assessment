package vttp2022.paf.assessment.eshop.respositories;

public class Queries {
    public static final String CUSTOMER_BY_NAME = "select name, address, email from eshop.customers where name = ?";
    public static final String INSERT_ORDER = "insert into eshop.order(orderId, deliveryId, name, address, email, status, date) values (?, ?, ?, ?, ?, ?, SYSDATE())";
    public static final String INSERT_LINE_ITEM = "insert into eshop.line_item(item, quantity, orderId) values (?, ?, ?)";
    public static final String SEARCH_ORDER_BY_NAME = "select orderId, deliveryId, name, address, email, status, date from eshop.order where name = ?";
}
