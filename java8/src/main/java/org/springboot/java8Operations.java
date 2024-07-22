package org.springboot;

import java.util.List;


import static org.springboot.Main.orders;
import static org.springboot.Main.products;

public class java8Operations {

    // 1)- Exercise 1 — Obtain a list of products belongs to category “Books” with price > 100
    List<Product> productList1 = products.stream()
            .filter(p-> p.getCategory().equals("Books"))
            .filter(p -> p.getPrice()>100)
            .toList();

    // Exercise 2 — Obtain a list of order with products belong to category “Baby”
    List<Order> orderList = orders.stream()
            .filter(o->o.getProducts().stream().anyMatch(p->p.getCategory().equals("Baby")))
            .toList();

    // Exercise 3 — Obtain a list of product with category = “Toys” and then apply 10% discount
    List<Product> productList2 = products.stream()
            .filter(p->p.getCategory().equals("Toys"))
            .map(p->p.withPrice(p.getPrice()*0.90))
            .toList();

}
