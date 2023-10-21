package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Category;

import java.util.List;

public interface CategoryRepository {

    List<Category> allCategories();

    Category addCategory(String name, String description);

    Category findCategory(int categoryId);

    Category updateCategory(int categoryId, String name, String description);

    void deleteCategory(int categoryId);

}
