package com.eni.pizzaWebsite.dao;

import com.eni.pizzaWebsite.bo.Order;
import com.eni.pizzaWebsite.bo.OrderDetail;
import com.eni.pizzaWebsite.bo.Product;
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
    public Order getOrder(Long id_client) {

        List<Order> orderList = jdbcTemplate.query("SELECT * FROM `order` WHERE id_client=? AND id_state=?", new BeanPropertyRowMapper<Order>(Order.class), id_client, 1);

        if (orderList.size() > 0) {
            return orderList.get(0);
        }else{
            return null;
        }


    }

    @Override
    public OrderDetail getOrderDetail(Long id_order,Long id_product,Long id_size) {

        List<OrderDetail> orderDetailList = jdbcTemplate.query("SELECT * FROM order_details WHERE id_order=? AND id_product=? AND id_size=?;", new BeanPropertyRowMapper<OrderDetail>(OrderDetail.class), id_order, id_product,id_size);

        if (orderDetailList.size() > 0) {
            return orderDetailList.get(0);
        }else{
            return null;
        }

    }


    @Override
    public void addProductToOrder(Product product, Long id_client, Long quantity,Long size) {

        Order order = getOrder(id_client);

        String sql ="";

        //Ajout de données dans order
        if (order == null) {

            sql = "INSERT INTO `order` (id_client,id_user,id_state,is_in_delivery,delivery_datetime,total_price,is_paid) " +
                    "VALUES (:id_client, :id_user, :id_state, :is_in_delivery, :delivery_datetime, :total_price, :is_paid)";

        }else{

            sql = "UPDATE `order` SET id_client= :id_client,id_user= :id_user,id_state= :id_state,is_in_delivery= :is_in_delivery,delivery_datetime= :delivery_datetime ,total_price= :total_price,is_paid= :is_paid WHERE id_order= :id_order";

        }

        MapSqlParameterSource orderMapSqlParameterSource = new MapSqlParameterSource();

        orderMapSqlParameterSource.addValue("id_client", id_client);
        orderMapSqlParameterSource.addValue("id_user", 0);
        orderMapSqlParameterSource.addValue("id_state", 1);
        orderMapSqlParameterSource.addValue("is_in_delivery", false);
        orderMapSqlParameterSource.addValue("delivery_datetime", LocalDateTime.now().plusMinutes(30));
        orderMapSqlParameterSource.addValue("total_price", 0);
        orderMapSqlParameterSource.addValue("is_paid", false);
        if (order != null) {
            orderMapSqlParameterSource.addValue("id_order", order.getId_order());
        }

        namedParameterJdbcTemplate.update(sql, orderMapSqlParameterSource);

        order = getOrder(id_client);
        OrderDetail orderDetail = getOrderDetail(order.getId_order(),product.getId_product(),size);

        //Ajout de données dans order_details
        if (orderDetail == null) {

            sql = "INSERT INTO order_details (id_order, id_product, id_size, quantity) " +
                    "VALUES (:id_order, :id_product, :id_size, :quantity)";

        }else{
            sql = "UPDATE order_details SET id_order= :id_order, id_product= :id_product, id_size= :id_size, quantity= :quantity WHERE id_order= :id_order AND id_product= :id_product AND id_size= :id_size";
        }

        MapSqlParameterSource orderDetailmapSqlParameterSource = new MapSqlParameterSource();

        orderDetailmapSqlParameterSource.addValue("id_order", order.getId_order());
        orderDetailmapSqlParameterSource.addValue("id_product", product.getId_product());
        orderDetailmapSqlParameterSource.addValue("id_size", size);
        orderDetailmapSqlParameterSource.addValue("quantity", quantity);

        namedParameterJdbcTemplate.update(sql, orderDetailmapSqlParameterSource);


        //Calcul du total_price
        Float total_price = getOrderTotalPrice(id_client);

        sql = "UPDATE `order` SET total_price =? WHERE id_order=?";

        jdbcTemplate.update(sql, total_price, order.getId_order());

    }


    static final RowMapper<OrderDetail> ORDERDETAIL_ROW_MAPPER = new RowMapper<OrderDetail>() {

        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setId_order(rs.getLong("id_order"));
            orderDetail.setProduct(new Product(rs.getLong("id_product"), rs.getString("name"), rs.getString("description"), rs.getFloat("price"), rs.getString("image_url")));
            orderDetail.setSize(rs.getInt("id_size"));
            orderDetail.setQuantity(rs.getInt("quantity"));

            return orderDetail;
        }
    };

    @Override
    public List<OrderDetail> getOrderDetail(Long id_client) {
        Order order = getOrder(id_client);
        if (order != null) {

            String sql = "SELECT * FROM order_details AS o INNER JOIN product AS p ON o.id_product = p.id_product WHERE id_order=?";

            List<OrderDetail> orderDetails = jdbcTemplate.query(sql, ORDERDETAIL_ROW_MAPPER, order.getId_order());
            return orderDetails;

        }

        return null;
    }

    @Override
    public void removeProductFromOrder(Long id_product, Long id_client, Long id_size) {

        Order order = getOrder(id_client);

        if (order != null) {

            String sql = "DELETE FROM order_details WHERE id_order=? AND id_product=? AND id_size=?";

            jdbcTemplate.update(sql, order.getId_order(), id_product, id_size);

        }

    }

    @Override
    public Float getOrderTotalPrice(Long id_client) {

        Order order = getOrder(id_client);

        if (order != null) {
            String sql = "SELECT SUM((p.price+s.price_difference)*o.quantity) as total_price FROM order_details as o INNER JOIN product AS p ON o.id_product = p.id_product INNER JOIN product_size AS s ON o.id_size = s.id_size where id_order=?";
            Float total_price = jdbcTemplate.queryForObject(sql, new Object[]{order.getId_order()}, Float.class);

            return total_price;
        }

        return 0f;
    }
}
