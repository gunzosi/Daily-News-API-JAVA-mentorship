package code.mentor.service;

import code.mentor.models.Category;

import java.util.List;


public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(int id);
    Category saveCategory(Category category);
    Category updateCategory(Category category);
    void deleteCategoryById(int id);
}
