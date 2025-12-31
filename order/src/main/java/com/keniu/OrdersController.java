package com.keniu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private static final String USER_ID = "userId";
    private static final String TOTAL = "total";

    @GetMapping("/users/{userId}")
    public List<Map<String, Object>> getOrders(@PathVariable("userId") int userId) {
        List<Map<String, Object>> allOrders = List.of(
                Map.of("id", 101, USER_ID, 1, TOTAL, 100),
                Map.of("id", 102, USER_ID, 1, TOTAL, 200)
        );

        return allOrders.stream()
                .filter(o -> (int) o.get(USER_ID) == userId)
                .toList();
    }
}
