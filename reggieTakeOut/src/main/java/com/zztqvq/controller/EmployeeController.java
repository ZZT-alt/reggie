package com.zztqvq.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zztqvq.common.R;
import com.zztqvq.entity.Employee;
import com.zztqvq.mapper.EmployeeMapper;
import com.zztqvq.service.EmployeeService;
import com.zztqvq.util.SqlSessionFactoryUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    private SqlSession session;

    public EmployeeMapper getEmployeeMapper() {
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryUtils.getSqlSessionFactory();
        this.session = sqlSessionFactory.openSession();
        return this.session.getMapper(EmployeeMapper.class);
    }

    /**
     * TODO 员工登录
     *
     * @param request  前端请求
     * @param employee 员工对象
     * @return R<Employee>对象
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if (one == null) {
            return R.error("Login Error!");
        }
        if (!one.getPassword().equals(password)) {
            return R.error("Password Error!");
        }
        if (one.getStatus() == 0) {
            return R.error("Disabled!");
        }
        request.getSession().setAttribute("employee", one.getId());
        return R.success(one);
    }

    /**
     * TODO 退出登录
     *
     * @param request 前端请求
     * @return R<String>对象
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清除Session
        request.getSession().removeAttribute("employee");
        return R.success("Logout Success!");
    }

    /**
     * TODO 添加员工
     *
     * @param request  前端请求
     * @param employee 员工对象
     * @return R<String>对象
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工的信息:{}", employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("Save Success");
    }

    /**
     * TODO 分页查询
     *
     * @param page     当前页
     * @param pageSize 每页条数
     * @param name     员工名字
     * @return R<Page>对象
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page => {}    pageSize => {}    name => {}",page,pageSize,name);
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        wrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        wrapper.orderByDesc(Employee::getUpdateTime);
        employeeService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    /**
     * TODO 更新员工信息
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateStatus(HttpServletRequest request, @RequestBody Employee employee) {
        Long updateUser = (Long) request.getSession().getAttribute("employee");
//        long id = Thread.currentThread().getId();
//        log.info("Controller线程id => " + id);
//        employee.setUpdateUser(updateUser);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("更新成功");
    }

    /**
     * TODO 通过id查询单个员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> updateEmployee(@PathVariable Long id) {
        EmployeeMapper employeeMapper = getEmployeeMapper();
        Employee employee = employeeMapper.getByIdEmployee(id);
        this.session.close();
        return employee == null ? R.error("id错误") : R.success(employee);
    }
}
