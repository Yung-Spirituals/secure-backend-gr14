package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.models.Order;
import no.ntnu.secureBackendGr14.models.ShoppingCart;
import no.ntnu.secureBackendGr14.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Get all orders from the database.
     *
     * @return all orders from the database.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * For each shopping cart the user has, saves the information as an order and empties the cart
     * afterwards.
     *
     * @param username of user.
     */
    public void addNewOrder(String username) {
        for (ShoppingCart shoppingCart : shoppingCartService.getCarts(username)) {
            orderRepository.save(
                new Order(shoppingCart.getProduct(), shoppingCart.getUser(),
                    shoppingCart.getQuantity()));
        }
        shoppingCartService.deleteCarts(username);
    }

    /**
     * Deletes order by the order id.
     *
     * @param id of order.
     */
    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.getById(id));
        orderRepository.flush();
    }

    /**
     * Deletes all orders from database.
     */
    public void deleteAllOrders() {
        orderRepository.deleteAll();
        orderRepository.flush();
    }

    /**
     * Marks the order as processed.
     *
     * @param id of order.
     */
    public void processOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setProcessed(true);
        orderRepository.save(order);
    }

    /**
     * Unchecks the order as processed.
     *
     * @param id of order.
     */
    public void revertProcessedOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setProcessed(false);
        orderRepository.save(order);
    }
}
