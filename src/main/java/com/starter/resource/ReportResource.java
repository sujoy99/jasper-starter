package com.starter.resource;

import com.starter.dto.EmployeeDTO;
import com.starter.entity.Employee;
import com.starter.entity.Product;
import com.starter.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "api/v1/report")
@Api(tags = "Report's Data")
//@CrossOrigin(origins = "*")
public class ReportResource {

    private final EmployeeRepository repository;

    @GetMapping("/test/{id}")
    @ApiOperation(value = "get report by id", response = String.class)
    public String findById(@Valid @PathVariable("id") @NotNull @Min(1) Long id) {

        return "hi";
    }

    //    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/list/")
    @ApiOperation(value = "get report by id", response = String.class)
    public List<EmployeeDTO> list() {

        List<Employee> empList = repository.findAll();
        List<EmployeeDTO> list = empList.stream().map(EmployeeDTO::fromEntity).collect(Collectors.toList());
        return list;
    }

    /*
    @GetMapping("/save-command")
    @ApiOperation(value = "get report by id", response = String.class)
    public String save() {

        Employee empOne = new Employee();
        empOne.setName("EMP-ONE");
        empOne.setDesignation("programmer");

        empOne.getProducts().addAll(Arrays.asList(
                new Product(null, "Keyboard", 54884),
                new Product(null, "Mouse", 54884),
                new Product(null, "Laptop", 54884)

        ));

        Employee empTwo = new Employee();
        empTwo.setName("EMP-TWO");
        empTwo.setDesignation("developer");

        empTwo.getProducts().addAll(Arrays.asList(
                new Product(null, "Mobile", 54884),
                new Product(null, "Headphone", 54884)

        ));

        repository.saveAll(Arrays.asList(empOne, empTwo));
        return "data saved";
    }
    */

    @GetMapping("/generate-report/{empId}")
    public String generateReport(HttpServletResponse response, @NotNull @PathVariable("empId") Long empId) {

        try {
            //load file and compile it
            File file = ResourceUtils.getFile("classpath:reports.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            List<Product> productList = repository.getProductList(empId);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(productList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "test");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment; filename=invoice.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            /*
            if (jasperPrint != null) {
                try {
                    JasperPrintManager.printReport(jasperPrint, false);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Report printing failed");
            }
            */

        } catch (Exception e) {

            System.out.println("test  " + e.getMessage());

        }

        return "Response with header using HttpServletResponse";
    }

   /* @GetMapping("/http-servlet-response")
    public String usingHttpServletResponse(HttpServletResponse response) {
//        response.addHeader("Baeldung-Example-Header", "Value-HttpServletResponse");

        try {
            //load file and compile it
            File file = ResourceUtils.getFile("classpath:reports.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Arrays.asList(

                    new Product(121, "Keyboard", 54884), new Product(122, "Mouse", 54884),
                    new Product(123, "Laptop", 54884), new Product(124, "Mobile", 54884),
                    new Product(125, "Headphone", 54884)

            ), false);



            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "test");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment; filename=test.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());

            *//*
            if (jasperPrint != null) {
                try {
                    JasperPrintManager.printReport(jasperPrint, false);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Report printing failed");
            }
            *//*

        } catch (Exception e) {

            System.out.println("test" + e.getMessage());

        }





        return "Response with header using HttpServletResponse";
    }*/
}
