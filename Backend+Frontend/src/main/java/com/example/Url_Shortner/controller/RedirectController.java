package com.example.Url_Shortner.controller;

import com.example.Url_Shortner.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/r/{shortUrl}")
    public ResponseEntity<?> redirectToLongUrl(
            @PathVariable String shortUrl) {

        String longUrl = urlService.getLongUrl(shortUrl);

        if (longUrl == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .status(302)
                .header("Location", longUrl)
                .build();
    }
}

