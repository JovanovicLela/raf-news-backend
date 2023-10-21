package rs.raf.demo.resources;

import rs.raf.demo.entities.Category;
import rs.raf.demo.services.CategoryService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @Inject
    private CategoryService categoryService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> allCategories() {
        return this.categoryService.allCategories();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Category addCategory(@Valid Category category) {
        return this.categoryService.addCategory(category.getName(), category.getDescription());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category findCategory(@PathParam("id") int categoryId) {
        return this.categoryService.findCategory(categoryId);
    }


    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category updateCategory(@PathParam("id") int categoryId, @Valid Category category) {
        return this.categoryService.updateCategory(categoryId, category.getName(), category.getDescription());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public void deleteCategory(@PathParam("id") int categoryId) {
        this.categoryService.deleteCategory(categoryId);
    }
}
