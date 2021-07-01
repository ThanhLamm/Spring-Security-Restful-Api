package com.security.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.entity.Category;
import com.security.repository.CategoryRepository;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping
	public ResponseEntity<List<Category>> findAll() {
		return ResponseEntity.ok(categoryRepository.findAll());
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Category> findOne(@PathVariable("id") Long id) {
		if(!categoryRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(categoryRepository.findById(id).get());
	}
	
	@PostMapping
	public ResponseEntity<Category> post(@RequestBody Category category) {
		if(categoryRepository.existsById(category.getCategoryId())) {
			return ResponseEntity.badRequest().build();
		}
		categoryRepository.save(category);
		return ResponseEntity.ok(category);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Category> put(@RequestBody Category category, @PathVariable("id") Long id) {
		if(!categoryRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		if(!categoryRepository.existsById(category.getCategoryId())) {
			return ResponseEntity.badRequest().build();
		}
		categoryRepository.save(category);
		return ResponseEntity.ok(category);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
		if(!categoryRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		categoryRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
}
