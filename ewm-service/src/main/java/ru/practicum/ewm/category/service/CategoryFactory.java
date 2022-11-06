package ru.practicum.ewm.category.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CategoryFactory {
    private final CategoryRepository categoryRepository;

    public Category getById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
