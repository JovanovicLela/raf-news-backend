package rs.raf.demo.services;

import rs.raf.demo.entities.Category;
import rs.raf.demo.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    @Inject
    private CategoryRepository categoryRepository;

    public List<Category> allCategories() {
        return this.categoryRepository.allCategories();
    }

    public Category addCategory(String name, String description) {
        return this.categoryRepository.addCategory(name, description);
    }

    public Category findCategory(int categoryId) {
        return this.categoryRepository.findCategory(categoryId);
    }

    public Category updateCategory(int categoryId, String name, String description) {
        return this.categoryRepository.updateCategory(categoryId, name, description);
    }

    public void deleteCategory(int categoryId) {
        this.categoryRepository.deleteCategory(categoryId);
    }
}
