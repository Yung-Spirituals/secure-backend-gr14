package no.ntnu.secureBackendGr14.Controllers;

import no.ntnu.secureBackendGr14.security.JwtUtil;
import no.ntnu.secureBackendGr14.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/new-order")
    public void newOrder(@RequestHeader("authorization") String authorization){
        orderService.addNewOrder(jwtUtil.getUsername(authorization));
    }

    @DeleteMapping("/delete-order/{id}")
    public void deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
    }

    @DeleteMapping("/delete-all-orders")
    public void deleteAllOrders(){
        orderService.deleteAllOrders();
    }

    @PostMapping("/process/{id}")
    public void processOrder(@PathVariable("id") Long id){
        orderService.processOrder(id);
    }

    @PostMapping("/revert-processed-order/{id}")
    public void revertProcessedOrder(@PathVariable("id") Long id){
        orderService.revertProcessedOrder(id);
    }
}