package ru.practicum.ewm.category.service;

import ru.practicum.ewm.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category create(Category category);

    Category update(Category category);

    void delete(Long catId);

    List<Category> getAll(Integer from, Integer size);

    Category getById(Long catId);
}
