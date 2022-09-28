package com.zztqvq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

    public List<Employee> getAll();

    public Page getByPage();

    public int updateStatus(@Param("id") Long id,@Param("employee") Employee employee);

    public Employee getByIdEmployee(@Param("id") Long id);
}
