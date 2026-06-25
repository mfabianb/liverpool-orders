package com.liverpool.orders.infrastructure.adapters.api.controllers;

import com.liverpool.orders.application.ports.ItemsApiPort;
import com.liverpool.orders.infrastructure.adapters.api.dto.ItemResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/items", produces = "application/json")
public class ItemController {

    @Autowired
    private ItemsApiPort itemsApiPort;

    /*@PostMapping(consumes = "application/json")
    public ResponseEntity<Item> create(
            @RequestBody Item customer) {

        return ResponseEntity.ok(null);
    }*/

    @GetMapping("/{userId}")
    public ResponseEntity<List<ItemResponse>> get(
            @PathVariable String userId) {

        return ResponseEntity.ok(
                itemsApiPort.getItems());
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> getList() {
        return ResponseEntity.ok(
                itemsApiPort.getItems());
    }
}
