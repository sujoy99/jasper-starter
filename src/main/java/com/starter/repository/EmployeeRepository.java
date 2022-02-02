package com.starter.repository;

import com.starter.entity.Employee;
import com.starter.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select products from Employee e join e.products products where e.id = :empId ")
    List<Product> getProductList(Long empId);
}
