package com.example.shortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.Semaphore;

@RestController
public class ShortenerController {

    Map<String, String> shortToLong = new HashMap<>();
    Map<String, String> longToShort = new HashMap<>();
    int id = 0;
    Semaphore limit;

    @Value("${limit.concurrent:10}")
    public void setLimit(int l) {
        limit = new Semaphore(l);
    }

    @PostMapping("/encode")
    public ResponseEntity<Map<String, String>> encode(@RequestBody Map<String, String> in) {
        if (!limit.tryAcquire()) return ResponseEntity.status(429).body(Map.of("error", "Too many"));
        try {
            String url = in.get("url");
            if (url == null) return ResponseEntity.badRequest().body(Map.of("error", "Missing url"));
            if (longToShort.containsKey(url)) return ResponseEntity.ok(Map.of("url", "http://short.est/" + longToShort.get(url)));
            String code = Integer.toHexString(++id); // ⬅ hex encoding
            longToShort.put(url, code);
            shortToLong.put(code, url);
            return ResponseEntity.ok(Map.of("url", "http://short.est/" + code));
        } finally {
            limit.release();
        }
    }

    @PostMapping("/decode")
    public ResponseEntity<Map<String, String>> decode(@RequestBody Map<String, String> in) {
        if (!limit.tryAcquire()) return ResponseEntity.status(429).body(Map.of("error", "Too many"));
        try {
            String url = in.get("url");
            if (url == null || !url.startsWith("http://short.est/"))
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid short URL"));
            String code = url.substring("http://short.est/".length());
            String longUrl = shortToLong.get(code);
            if (longUrl == null) return ResponseEntity.status(404).body(Map.of("error", "Not found"));
            return ResponseEntity.ok(Map.of("url", longUrl));
        } finally {
            limit.release();
        }
    }
}