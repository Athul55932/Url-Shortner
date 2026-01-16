package com.example.Url_Shortner.entity;

import com.example.Url_Shortner.Enums.UrlStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="url")
@Data
public class UrlShortner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable=false, length = 2048)
    private String longurl;

    @Column(nullable = false, unique = true , length =20)
    private String shortUrl;

    @Column(nullable= false)
    private LocalDateTime expiry;

    @Column(nullable = false,length=10)
    @Enumerated(EnumType.STRING)
    private UrlStatus status;
}
