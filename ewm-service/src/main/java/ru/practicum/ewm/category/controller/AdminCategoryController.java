package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.service.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryService;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.exception.ValidationService;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;
    private final ValidationService validator;

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        log.info("create category {}", categoryDto);
        try {
            Category category = categoryMapper.toCategory(validator.validateCategory(categoryDto));
            return categoryMapper.toDto(categoryService.create(category));
        }catch (ConstraintViolationException exception){
            throw new ValidationException("category name is blank");
        }
    }

    @PatchMapping
    public CategoryDto update(@RequestBody CategoryDto categoryDto) {
        log.info("update category {}", categoryDto);
        try {
            Category category = categoryMapper.toCategory(validator.validateCategory(categoryDto));
            return categoryMapper.toDto(categoryService.update(category));
        }catch (ConstraintViolationException exception){
            throw new ValidationException("category name is blank");
        }
    }

    @DeleteMapping("/{catId}")
    public void delete(@PathVariable Long catId) {
        log.info("delete category, id={}", catId);
        categoryService.delete(catId);
    }
}
