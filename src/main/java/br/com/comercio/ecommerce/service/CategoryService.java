package br.com.comercio.ecommerce.service;


import br.com.comercio.ecommerce.payload.CategoryDTO;
import br.com.comercio.ecommerce.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
