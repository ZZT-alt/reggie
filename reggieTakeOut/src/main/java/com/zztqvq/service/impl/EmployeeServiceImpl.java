package com.zztqvq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zztqvq.entity.Employee;
import com.zztqvq.mapper.EmployeeMapper;
import com.zztqvq.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
