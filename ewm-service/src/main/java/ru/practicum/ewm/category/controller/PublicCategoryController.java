package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        log.info("get all category, from={}, size={}", from, size);
        return categoryMapper.toDto(categoryService.getAll(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {
        log.info("get category by id={}", catId);
        return categoryMapper.toDto(categoryService.getById(catId));
    }
}
