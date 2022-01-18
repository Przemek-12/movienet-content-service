package com.content.application.kafka;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.content.application.CategoryService;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerServiceTest {

    @InjectMocks
    private KafkaConsumerService kafkaConsumerService;

    @Mock
    private CategoryService categoryService;

    @Test
    void shouldDeleteVideoIdFromAllCategoriesOnVideoDeleted() {
        kafkaConsumerService.onVideoDeleted("1");
        verify(categoryService, times(1))
                .deleteVideoIdFromAllCategories(Mockito.argThat(arg -> arg.equals(1L)));
    }

}
