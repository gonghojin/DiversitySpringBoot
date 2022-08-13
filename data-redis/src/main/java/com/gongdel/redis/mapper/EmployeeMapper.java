package com.gongdel.redis.mapper;

import com.gongdel.redis.dto.EmployeeDto;
import com.gongdel.redis.entity.Employee;
import com.gongdel.redis.mapper.common.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Service;

@Service
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper extends EntityMapper<Employee, EmployeeDto> {
}
