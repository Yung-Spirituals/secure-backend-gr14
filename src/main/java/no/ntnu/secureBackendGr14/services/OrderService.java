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

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addNewOrder(String username) {
        for (ShoppingCart shoppingCart : shoppingCartService.getCarts(username)) {
            orderRepository.save(
                    new Order(shoppingCart.getProduct(), shoppingCart.getUser(), shoppingCart.getQuantity()));
        }
        shoppingCartService.deleteCarts(username);
    }

    public void deleteOrder(Long id) {
        orderRepository.delete(orderRepository.getById(id));
        orderRepository.flush();
    }

    public void deleteAllOrders() {
        orderRepository.deleteAll();
        orderRepository.flush();
    }

    public void processOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setProcessed(true);
        orderRepository.save(order);
    }

    public void revertProcessedOrder(Long id) {
        Order order = orderRepository.getById(id);
        order.setProcessed(false);
        orderRepository.save(order);
    }
}
