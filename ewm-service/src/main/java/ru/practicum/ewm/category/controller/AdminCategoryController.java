package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("create category {}", categoryDto);

        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryService.create(category));
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        log.info("update category {}", categoryDto);

        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryService.update(category));
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("delete category, id={}", catId);
        categoryService.delete(catId);
    }
}
