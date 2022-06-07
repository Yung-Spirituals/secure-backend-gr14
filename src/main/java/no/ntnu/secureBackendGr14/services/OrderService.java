package no.ntnu.secureBackendGr14.services;

import no.ntnu.secureBackendGr14.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void addNewOrder(String Username) {

    }
}
