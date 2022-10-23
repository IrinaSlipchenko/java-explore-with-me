package ru.practicum.ewm.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.service.CategoryMapper;
import ru.practicum.ewm.category.service.CategoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryMapper categoryMapper;
    private final CategoryServiceImpl categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") Integer from,
                                    @RequestParam(defaultValue = "10") Integer size) {
        return categoryMapper.toDto(categoryService.getAll(from, size));
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable Long catId) {

        return categoryMapper.toDto(categoryService.getById(catId));
    }
}
