package com.starter.resource;

import com.starter.dto.EmployeeDTO;
import com.starter.dto.SerialDTO;
import com.starter.entity.Employee;
import com.starter.entity.Product;
import com.starter.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.ArrayList;
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
   private static final Logger logger = LoggerFactory.getLogger(ReportResource.class);
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(origins = "*")
    public ResponseEntity uploadFile(@Valid @ModelAttribute EmployeeDTO dto) {
        logger.info(String.format("File name '%s' uploaded successfully.", dto.getFile().getOriginalFilename()));
        logger.info(String.format("File name '%s' uploaded '%s' successfully.", dto.getName(), dto.getSalary()));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/generate-report")
    public String generateReportForSerialNo(HttpServletResponse response) {

        try {
            //load file and compile it
//            File file = ResourceUtils.getFile("classpath:serial.jrxml");
            File file = ResourceUtils.getFile("classpath:bds.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//            int startRoll = 4200001;
            int startRoll = 8100001;
//            int endRoll = 4204200;
            int endRoll = 8101032;
            int rowPerPage = 6;
            int columnPerPage = 2;
            int totalPage = 350;

            int rollRange = endRoll - startRoll + 1;
            int totalRow = rollRange / 2;
            totalPage = rollRange / (rowPerPage * columnPerPage);

            int addForFirstNum = columnPerPage * totalPage;
            List<SerialDTO> serialList = new ArrayList<>();
            serialList.add(new SerialDTO(startRoll, startRoll + totalPage));

            int i = 1;
            while (i < totalRow){
                if(i % rowPerPage == 0){
                   SerialDTO prevPageFirstSerial = serialList.get( i - rowPerPage);
                   int firstNum = prevPageFirstSerial.getId1() + 1;
                   int secondNum = firstNum + totalPage;
                   serialList.add(new SerialDTO(firstNum, secondNum));
                }else {
                    SerialDTO prevSerial = serialList.get(i - 1);
                    int firstNum = prevSerial.getId1() + addForFirstNum;
                    int secondNum = firstNum + totalPage;
                    serialList.add(new SerialDTO(firstNum, secondNum));
                }
                i++;
            }

/*            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(Arrays.asList(

                    new SerialDTO(1, 6),
                    new SerialDTO(11, 16),
                    new SerialDTO(21, 26),
                    new SerialDTO(31, 36),
                    new SerialDTO(2, 7),
                    new SerialDTO(12, 17),
                    new SerialDTO(22, 27),
                    new SerialDTO(32, 37),
                    new SerialDTO(3, 8),
                    new SerialDTO(13, 18),
                    new SerialDTO(23, 28),
                    new SerialDTO(33, 38),
                    new SerialDTO(4, 9),
                    new SerialDTO(14, 19),
                    new SerialDTO(24, 29),
                    new SerialDTO(34, 39),
                    new SerialDTO(5, 10),
                    new SerialDTO(15, 20),
                    new SerialDTO(25, 30),
                    new SerialDTO(35, 40)

            ), false);*/

            JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(serialList, false);

//            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(serialList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("CollectionBeanParam", beanCollectionDataSource);
            parameters.put("rowPerPage", rowPerPage + 1);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, beanCollectionDataSource);

            //Export to PDF
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "attachment; filename=rollList.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());


            //Export to Word
//            String fileName = "rollList";
//            JRDocxExporter exporter = new JRDocxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            File exportReportFile = new File("rollList.docx");
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(exportReportFile));
//            exporter.exportReport();

//            JRDocxExporter exporter = new JRDocxExporter();
//            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
//            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
//            response.setHeader(
//                    HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName + ".docx;");
//            response.setContentType("application/octet-stream");
//            exporter.exportReport();

        } catch (Exception e) {
            System.out.println("test  " + e.getMessage());
        }
        return "Response with header using HttpServletResponse";
    }
}
