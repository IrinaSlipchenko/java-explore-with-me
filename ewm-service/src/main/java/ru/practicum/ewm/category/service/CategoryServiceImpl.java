package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.OffsetLimitPageable;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category category) {
        return save(category);
    }

    @Override
    public Category update(Category category) {
        Category oldCategory = getById(category.getId());

        categoryMapper.update(category, oldCategory);
        return save(oldCategory);
    }

    private Category save(Category category) {
        try {
            return categoryRepository.save(category);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Category name already in use", "name = " + category.getName());
        }

    }

    @Override
    public void delete(Long catId) {
        // TODO Обратите внимание: с категорией не должно быть связано ни одного события.

        categoryRepository.deleteById(catId);

    }

    @Override
    public List<Category> getAll(Integer from, Integer size) {
        Pageable pageable = OffsetLimitPageable.of(from, size);
        return categoryRepository.findAll(pageable).getContent();
    }

    @Override
    public Category getById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Category not found"));
    }
}
