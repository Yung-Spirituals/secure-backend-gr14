package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.models.Order;
import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Get all orders from database.
     * @return all orders from database.
     */
    @GetMapping("/all-orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    /**
     * "purchases" the shopping cart, clears it and makes it an order.
     * @param authorization used to identify who is buying.
     */
    @PostMapping("/new-order")
    public void newOrder(@RequestHeader("authorization") String authorization){
        orderService.addNewOrder(jwtUtil.getUsername(authorization));
    }

    /**
     * Deletes that order with the specified id.
     * @param id of the order.
     */
    @DeleteMapping("/delete-order/{id}")
    public void deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
    }

    /**
     * Deletes all orders.
     */
    @DeleteMapping("/delete-all-orders")
    public void deleteAllOrders(){
        orderService.deleteAllOrders();
    }

    /**
     * Mark orders as processed.
     * @param id of order.
     */
    @PostMapping("/process/{id}")
    public void processOrder(@PathVariable("id") Long id){
        orderService.processOrder(id);
    }

    /**
     * Marks orders as not processed.
     * @param id of order.
     */
    @PostMapping("/revert-processed-order/{id}")
    public void revertProcessedOrder(@PathVariable("id") Long id){
        orderService.revertProcessedOrder(id);
    }
}