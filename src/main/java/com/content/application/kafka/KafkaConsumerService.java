package com.content.application.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.content.application.CategoryService;

@Service
public class KafkaConsumerService {

    private final CategoryService categoryService;

    @Autowired
    public KafkaConsumerService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @KafkaListener(topics = "video-deleted", groupId = "content-service")
    public void onVideoDeleted(String message) {
        categoryService.deleteVideoIdFromAllCategories(Long.parseLong(message));
    }

}
