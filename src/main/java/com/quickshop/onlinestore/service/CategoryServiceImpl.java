package com.quickshop.onlinestore.service;

import com.quickshop.onlinestore.model.Category;
import com.quickshop.onlinestore.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private List<Category> categories=new ArrayList<>();
   // private Long nextId= 1L;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public List<Category> getAllCategories() {
        //return categories;
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        //category.setCategoryId(nextId++);
        //categories.add(category);
        categoryRepository.save(category);

    }

    @Override
    public String deleteCategory(Long categoryId) {
        //Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId)).findFirst().get();
        //Category category=categories.stream().filter(c->c.getCategoryId().equals(categoryId)).findFirst().orElse(null);
       List<Category> categories=categoryRepository.findAll();
        Category category=categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found"));
        categories.remove(category);
        return "Category with categoryId "+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        List<Category> categories=categoryRepository.findAll();
        Optional<Category> optionalCategory=categories.stream()
                .filter(c->c.getCategoryId().equals(categoryId))
                .findFirst();
        if(optionalCategory.isPresent()){
            Category existingCategory=optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existingCategory);
        }
        else {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
        }

    }


}
