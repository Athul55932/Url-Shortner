package com.example.Url_Shortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Url_Shortner.Enums.UrlStatus;
import com.example.Url_Shortner.entity.UrlShortner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<UrlShortner,Long> {
    //urlShortner is the entity file name and Long is the type of id

    Optional<UrlShortner> findByShortUrl(String shortUrl);
    List<UrlShortner> findByStatusAndExpiryBefore(
            UrlStatus status,
            LocalDateTime now
    );

}

