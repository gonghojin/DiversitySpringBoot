package com.gongdel.redis.service;

import com.gongdel.redis.dto.EmployeeDto;
import com.gongdel.redis.entity.Employee;
import com.gongdel.redis.exception.DataNotFoundException;
import com.gongdel.redis.mapper.EmployeeMapper;
import com.gongdel.redis.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmployeeService {

	private final EmployeeRepository employeeRepository;

	private final EmployeeMapper employeeMapper;

	@Transactional
	public EmployeeDto create(EmployeeDto employeeDTO) {
		employeeRepository.save(employeeMapper.toEntity(employeeDTO));
		return employeeDTO;
	}

	public EmployeeDto getById(Long id) throws DataNotFoundException {
		Employee employee = employeeRepository.findById(id).orElseThrow(DataNotFoundException::new);
		return employeeMapper.toDto(employee);
	}

	public List<EmployeeDto> getAll() {
		List<Employee> employees = employeeRepository.findAll();
		return employeeMapper.toDto(employees);
	}

	@Transactional
	public EmployeeDto update(Long id, EmployeeDto employeeDTO) {
		Employee employee = employeeMapper.toEntity(employeeDTO);
		employee.setId(id);
		employeeRepository.save(employee);
		return employeeDTO;
	}

	@Transactional
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}
}
