package com.keniu;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/dashboard")
public class BffController {

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/users")
    public List<Map<String, Object>> getUsersWithOrders() {
        List<Map<String, Object>> users = restTemplate.getForObject("http://localhost:8082/users", List.class);

        for (Map<String, Object> user : users) {
            int userId = (int) user.get("id");
            List<Map<String, Object>> orders =
                    Optional.ofNullable(
                            restTemplate.getForObject(
                                    "http://localhost:8081/orders/users/" + userId,
                                    List.class
                            )
                    ).orElse(List.of());

            user.put("orders", orders);
            int totalSpent = orders.stream()
                    .mapToInt(o -> (Integer) o.get("total"))
                    .sum();
            user.put("totalSpent", totalSpent);
        }

        return users;
    }
}
