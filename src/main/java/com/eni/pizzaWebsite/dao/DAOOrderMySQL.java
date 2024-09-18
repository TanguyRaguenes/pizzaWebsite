
package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class DAOOrderMySQL implements IDAOOrder {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public DAOOrderMySQL(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Order getOrder(Long id_client, Long id_order) {
        String baseSql = "SELECT `order`.id_order, `order`.id_client, `order`.id_user, `order`.id_state, `order`.is_in_delivery, `order`.delivery_datetime, `order`.total_price, `order`.is_paid, " +
                "client.firstName, client.lastName, client.street, client.postalCode, client.city, " +
                "user.firstName AS userFirstName, user.lastName AS userLastName, user.email, user.password " +
                "FROM `order` " +
                "INNER JOIN client ON `order`.id_client = client.id_client " +
                "INNER JOIN user ON `order`.id_user = user.id_user ";

        String sql;
        List<Order> orderList;

        if (id_order != null) {
            sql = baseSql + "WHERE `order`.id_order = ?";
            orderList = jdbcTemplate.query(sql, ORDER_ROW_MAPPER, id_order);
        }

        else if (id_client != null) {
            sql = baseSql + "WHERE `order`.id_client = ? AND `order`.id_state = ?";
            orderList = jdbcTemplate.query(sql, ORDER_ROW_MAPPER, id_client, 1); // 1 pour l'état "Cart"
        } else {
            return null;
        }

        if (!orderList.isEmpty()) {
            return orderList.get(0);
        } else {
            return null;
        }
    }



    @Override
    public OrderDetail getOrderDetail(Long id_order, Long id_product, Long id_size) {

        List<OrderDetail> orderDetailList = jdbcTemplate.query("SELECT * FROM order_details WHERE id_order=? AND id_product=? AND id_size=?;", new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class), id_order, id_product, id_size);

        if (orderDetailList.size() > 0) {
            return orderDetailList.get(0);
        } else {
            return null;
        }

    }


    @Override
    public void addProductToOrder(Product product, Long id_client, Long quantity, Long size) {
        Order order = getOrder(id_client, null);

        String sql = "";

        if (order == null) {
            sql = "INSERT INTO `order` (id_client, id_user, id_state, is_in_delivery, delivery_datetime, total_price, is_paid) " +
                    "VALUES (:id_client, :id_user, :id_state, :is_in_delivery, :delivery_datetime, :total_price, :is_paid)";

            MapSqlParameterSource orderMapSqlParameterSource = new MapSqlParameterSource();
            orderMapSqlParameterSource.addValue("id_client", id_client);
            orderMapSqlParameterSource.addValue("id_user", 1);  // ID de l'utilisateur fixe pour l'instant
            orderMapSqlParameterSource.addValue("id_state", 1);  // État "Cart"
            orderMapSqlParameterSource.addValue("is_in_delivery", false);
            orderMapSqlParameterSource.addValue("delivery_datetime", LocalDateTime.now().plusMinutes(30));
            orderMapSqlParameterSource.addValue("total_price", 0);
            orderMapSqlParameterSource.addValue("is_paid", false);

            namedParameterJdbcTemplate.update(sql, orderMapSqlParameterSource);
            order = getOrder(id_client, null);  // Récupérer la commande nouvellement créée.
        }

        // Si aucun produit n'est fourni : commande vide
        if (product == null) {
            return;
        }

        OrderDetail orderDetail = getOrderDetail(order.getId_order(), product.getId_product(), size);
        if (orderDetail == null) {
            sql = "INSERT INTO order_details (id_order, id_product, id_size, quantity) " +
                    "VALUES (:id_order, :id_product, :id_size, :quantity)";
        } else {
            sql = "UPDATE order_details SET quantity = :quantity WHERE id_order = :id_order AND id_product = :id_product AND id_size = :id_size";
        }

        MapSqlParameterSource orderDetailMapSqlParameterSource = new MapSqlParameterSource();
        orderDetailMapSqlParameterSource.addValue("id_order", order.getId_order());
        orderDetailMapSqlParameterSource.addValue("id_product", product.getId_product());
        orderDetailMapSqlParameterSource.addValue("id_size", size);
        orderDetailMapSqlParameterSource.addValue("quantity", quantity);

        namedParameterJdbcTemplate.update(sql, orderDetailMapSqlParameterSource);


        Float total_price = getOrderTotalPrice(id_client);
        sql = "UPDATE `order` SET total_price = ? WHERE id_order = ?";
        jdbcTemplate.update(sql, total_price, order.getId_order());
    }

    static final RowMapper<Order> ORDER_ROW_MAPPER = new RowMapper<Order>() {

        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId_order(rs.getLong("id_order"));
            order.setClient(new Client(rs.getLong("id_client"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("street"), rs.getString("postalCode"), rs.getString("city")));
            order.setUser(new User(rs.getLong("id_user"), rs.getString("firstName"), rs.getString("lastName"), rs.getString("email"), rs.getString("password"), rs.getLong("id_role")));
            order.setId_state(rs.getLong("id_state"));
            order.setIs_in_delivery(rs.getInt("is_in_delivery") == 1 ? true : false);
            order.setDelivery_datetime(rs.getTimestamp("delivery_datetime").toLocalDateTime());
            order.setTotal_price(rs.getFloat("total_price"));
            order.setIs_paid(rs.getInt("is_paid") == 1 ? true : false);
            return order;
        }
    };


    @Override
    public List<Order> getOrdersList(Long id_state) {
        String sql = "";
        List<Order> ordersList = null;
        if (id_state == 0) {
            sql = "SELECT * FROM `order` as o INNER JOIN client as c ON o.id_client = c.id_client INNER JOIN state as s ON o.id_state = s.id_state INNER JOIN user as u ON o.id_user=u.id_user";
            ordersList = jdbcTemplate.query(sql, ORDER_ROW_MAPPER);
        } else {
            sql = "SELECT * FROM `order` as o INNER JOIN client as c ON o.id_client = c.id_client INNER JOIN state as s ON o.id_state = s.id_state INNER JOIN user as u ON o.id_user=u.id_user WHERE o.id_state=?";
            ordersList = jdbcTemplate.query(sql, ORDER_ROW_MAPPER, id_state);
        }

        return ordersList;
    }

    static final RowMapper<OrderDetail> ORDERDETAIL_ROW_MAPPER = new RowMapper<OrderDetail>() {

        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId_order(rs.getLong("id_order"));
            orderDetail.setProduct(new Product(rs.getLong("id_product"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getString("image_url")));
            orderDetail.setSize(rs.getInt("id_size"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setPrice_difference(rs.getFloat("price_difference"));

            return orderDetail;
        }
    };

    @Override
    public List<OrderDetail> getOrderDetail(Long id_client) {
        Order order = getOrder(id_client, null);
        if (order != null) {

            String sql = "SELECT * FROM order_details AS od INNER JOIN product AS p ON od.id_product = p.id_product INNER JOIN product_size AS ps ON od.id_size = ps.id_size WHERE id_order=?";

            List<OrderDetail> orderDetails = jdbcTemplate.query(sql, ORDERDETAIL_ROW_MAPPER, order.getId_order());
            return orderDetails;

        }

        return null;
    }

    @Override
    public void removeProductFromOrder(Long id_product, Long id_client, Long id_size) {

        Order order = getOrder(id_client, null);

        if (order != null) {

            String sql = "DELETE FROM order_details WHERE id_order=? AND id_product=? AND id_size=?";

            jdbcTemplate.update(sql, order.getId_order(), id_product, id_size);

        }

    }

    @Override
    public Float getOrderTotalPrice(Long id_client) {

        Order order = getOrder(id_client, null);

        if (order != null) {
            String sql = "SELECT SUM((p.price+s.price_difference)*o.quantity) as total_price FROM order_details as o INNER JOIN product AS p ON o.id_product = p.id_product INNER JOIN product_size AS s ON o.id_size = s.id_size where id_order=?";
            Float total_price = jdbcTemplate.queryForObject(sql, new Object[]{order.getId_order()}, Float.class);

            return total_price;
        }

        return 0f;
    }

    @Override
    public void checkout(Long id_client, Long id_order, LocalDateTime delivery_datetime) {
        Order order = getOrder(id_client, id_order);

        if (order != null) {
            String sql = "UPDATE `order` SET id_state =?, delivery_datetime=? WHERE id_order=?";
            jdbcTemplate.update(sql, 2, delivery_datetime, order.getId_order()); // 2 pour "Checked Out"
        }
    }


    @Override
    public void clearOrderForClient(Long id_client) {

        Order order = getOrder(id_client, null);

        if (order != null && order.getId_state() == 1) {

            String sql = "DELETE FROM order_details WHERE id_order=?";
            jdbcTemplate.update(sql, order.getId_order());

            sql = "DELETE FROM `order` WHERE id_order=?";
            jdbcTemplate.update(sql, order.getId_order());

        }

    }

    @Override
    public List<ProductSize> getPriceByProductSize() {

        List<ProductSize> productSizes = jdbcTemplate.query("SELECT * FROM product_size", new BeanPropertyRowMapper<ProductSize>(ProductSize.class));
        return productSizes;
    }

    @Override
    public void clearOrderByIdOrder(Long id_order) {
        String sql = "DELETE FROM order_details WHERE id_order=?";
        jdbcTemplate.update(sql, id_order);

        sql = "DELETE FROM `order` WHERE id_order=?";
        jdbcTemplate.update(sql, id_order);
    }

    @Override
    public void updateOrderState(Long id_order, Long id_state) {
        String sql = "UPDATE `order` SET id_state=? WHERE id_order=?";
        jdbcTemplate.update(sql, id_state, id_order);
    }
    @Override
    public List<State> getStatesList() {
        String sql = "SELECT * FROM state";
        List<State> states = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(State.class));
        return states;
    }

}
