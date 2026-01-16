package com.example.Url_Shortner.controller;


import com.example.Url_Shortner.entity.UrlShortner;
import com.example.Url_Shortner.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/url")
public class UrlshortnerController {
    @Autowired
    private UrlService service;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createshorturl(@RequestBody UrlShortner urlShortner){
        UrlShortner savedUrl = service.createShortUrl(urlShortner.getLongurl(), 1);

        Map<String, String> response = new HashMap<>();
        response.put("shortUrl", savedUrl.getShortUrl());  // ONLY the short code

        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<List<UrlShortner>> getAllUrls() {
        return ResponseEntity.ok(UrlService.getAllUrls());
    }

}
