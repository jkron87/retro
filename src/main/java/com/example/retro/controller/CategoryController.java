package com.example.retro.controller;

import com.example.retro.exception.ResourceNotFoundException;
import com.example.retro.model.Category;
import com.example.retro.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

	@Autowired
	CategoryRepository categoryRepository;

	@GetMapping("/categories")
	public List<Category> getAllCategorys() {
		return categoryRepository.findAll();
	}

	@PostMapping("/categories")
	public Category createCategory(@Valid @RequestBody Category category) {
		return categoryRepository.save(category);
	}

	@GetMapping("/categories/{id}")
	public Category getCategoryById(@PathVariable(value = "id") Long categoryId) {
		return categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
	}

	@PutMapping("/categories/{id}")
	public Category updateCategory(@PathVariable(value = "id") Long categoryId,
						   @Valid @RequestBody Category categoryDetails) {

		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

		category.setName(categoryDetails.getName());

		Category updatedCategory = categoryRepository.save(category);
		return updatedCategory;
	}

	@DeleteMapping("/categories/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value = "id") Long categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Board", "id", categoryId));

		categoryRepository.delete(category);

		return ResponseEntity.ok().build();
	}


}