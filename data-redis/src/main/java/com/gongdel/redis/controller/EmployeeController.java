package com.gongdel.redis.controller;

import com.gongdel.redis.dto.EmployeeDto;
import com.gongdel.redis.exception.DataNotFoundException;
import com.gongdel.redis.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

	private final EmployeeService employeeService;

	@PostMapping("/")
	public EmployeeDto create(@RequestBody EmployeeDto employeeDto) {
		return employeeService.create(employeeDto);
	}

	@GetMapping("/{id}")
	@Cacheable(key = "#id", value = "EMPLOYEE")
	public EmployeeDto getById(@PathVariable("id") Long id) throws DataNotFoundException {
		return employeeService.getById(id);
	}

	@GetMapping("/")
	public List<EmployeeDto> getAll() {
		return employeeService.getAll();
	}

	@PutMapping("/{id}")
	@CacheEvict(key = "#id", value = "EMPLOYEE")
	public EmployeeDto update(@PathVariable("id") Long id, @RequestBody EmployeeDto employeeDTO) {
		return employeeService.update(id, employeeDTO);
	}

	@DeleteMapping("/{id}")
	@CacheEvict(key = "#id", value = "EMPLOYEE")
	public void delete(@PathVariable("id") Long id) {
		employeeService.delete(id);
	}
}
