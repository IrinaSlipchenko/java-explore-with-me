package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryServiceImpl;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryServiceImpl categoryService;

    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryService.create(category));
    }

    @PatchMapping
    public CategoryDto update(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toCategory(categoryDto);
        return categoryMapper.toDto(categoryService.update(category));
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        categoryService.delete(catId);

    }


}
