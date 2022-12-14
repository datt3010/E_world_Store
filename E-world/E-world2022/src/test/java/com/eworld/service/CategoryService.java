package com.eworld.service;

import com.eworld.contstant.CategoryStatus;
import com.eworld.entity.Category;
import com.eworld.repository.category.CategoryRepository;
import com.eworld.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Date;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CategoryService {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryServiceImpl categoryService;

    private Category category;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.openMocks(this.categoryService);

        Instant instant = Instant.now();
        Date date = Date.from(instant);
        Category category = Category.builder()
                .name("test")
                .logo("1.jpg")
                .status(CategoryStatus.ACTIVE)
                .build();
    }
    @Test
    public void testChangeStatus(){
     int categoryId = 1;
     willDoNothing().given(categoryService).changeStatus(categoryId);
     verify(categoryService,timeout(1)).changeStatus(categoryId);
    }
}
