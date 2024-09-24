package br.com.comercio.ecommerce.service.Impl;

import br.com.comercio.ecommerce.exceptions.APIException;
import br.com.comercio.ecommerce.exceptions.ResourceNotFoundException;
import br.com.comercio.ecommerce.model.Category;
import br.com.comercio.ecommerce.payload.CategoryDTO;
import br.com.comercio.ecommerce.payload.CategoryResponse;
import br.com.comercio.ecommerce.repository.CategoryRepository;
import br.com.comercio.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {

    //private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            throw new APIException("No category created till now");
        }

        List<CategoryDTO> categoryDTOS = categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTOS);

        return categoryResponse;
    }

    public CategoryDTO createCategory(CategoryDTO categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);

        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if (categoryFromDb != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");

        Category createdCategory = categoryRepository.save(category);

        return modelMapper.map(createdCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);

        category.setCategoryId(categoryId);

        savedCategory = categoryRepository.save(category);

        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
