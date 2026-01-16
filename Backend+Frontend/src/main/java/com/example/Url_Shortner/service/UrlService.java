package com.example.Url_Shortner.service;

import com.example.Url_Shortner.Enums.UrlStatus;
import com.example.Url_Shortner.entity.UrlShortner;
import com.example.Url_Shortner.repository.UrlRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
@Service
public class UrlService {

    private static UrlRepository urlrepository = null;

    public UrlService(UrlRepository urlrepository) {
        this.urlrepository = urlrepository;
    }
    private static final String CHAR_SET="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH=6;


    public static String generateRandomString(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < SHORT_CODE_LENGTH;i++){
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }
    private static String generateUniqueShortUrl(){
        String ShortUrl;
        do{
            ShortUrl=generateRandomString();
        }while(urlrepository.findByShortUrl(ShortUrl).isPresent());
        return ShortUrl;
    }

    public String getLongUrl(String ShortUrl) {
        Optional<UrlShortner> optionalUrl = urlrepository.findByShortUrl(ShortUrl);
        if (optionalUrl.isEmpty()) {
            return null;
        }

        UrlShortner url = optionalUrl.get();
        if (url.getStatus() == UrlStatus.EXPIRED || LocalDateTime.now().isAfter(url.getExpiry())) {
            url.setStatus(UrlStatus.EXPIRED);
            urlrepository.save(url);
        }

        return url.getLongurl();
    }

    public static List<UrlShortner> getAllUrls() { return urlrepository.findAll(); }

    public static UrlShortner createShortUrl(String longUrl, int expiryMinutes) {

        UrlShortner url = new UrlShortner();
        url.setLongurl(longUrl);
        url.setShortUrl(generateUniqueShortUrl());
        url.setExpiry(LocalDateTime.now().plusMinutes(expiryMinutes));
        url.setStatus(UrlStatus.ACTIVE);

        return urlrepository.save(url);
    }


    @Scheduled(fixedRate = 60000) // every 1 minute
    public void expireUrls() {
        List<UrlShortner> expiredUrls =
                urlrepository.findByStatusAndExpiryBefore(
                        UrlStatus.ACTIVE,
                        LocalDateTime.now()
                );

        for (UrlShortner url : expiredUrls) {
            url.setStatus(UrlStatus.EXPIRED);
            urlrepository.save(url);
        }
        System.out.println("Expired URLs updated: " + expiredUrls.size());
    }






}
