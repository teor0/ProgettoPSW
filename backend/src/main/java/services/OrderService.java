package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.OrderProductRepository;
import repositories.OrderRepository;

@Service
public class OrderService{

    @Autowired
    private OrderRepository orderrepo;

}
