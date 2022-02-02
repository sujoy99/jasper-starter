package com.starter.dto;

import com.starter.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class EmployeeDTO {

    private Long id;
    private String name;
    private String designation;
    private Double salary;
    private Date doj;

    public static EmployeeDTO fromEntity(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setDesignation(employee.getDesignation());
        dto.setSalary(employee.getSalary());
        return dto;
    }



    /*public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String var0 = "";
        try {
            var0 = request.getParameter("classId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.setContentType(CONTENT_TYPE);
        Map parameters = new HashMap();
        parameters.put("format", "pdf");
        parameters.put("WEBDIR", getServletContext().getRealPath("/"));
        parameters.put("REPORT_LOCALE", new Locale("ar"));
        parameters.put("salesOrderIdParam", new BigDecimal(918));
        Connection conn = null;
        InputStream is = null;
        try {
            conn = getConnection();
            is = getServletContext().getResourceAsStream("/Reports/SalesOrderRep.jrxml");
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "inline;filename=Employees.pdf");
            JasperDesign jasperDesign = JRXmlLoader.load(is);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            if (jasperPrint != null) {
                try {
                    JasperPrintManager.printReport(jasperPrint, false);
                } catch (JRException ex) {
                    ex.printStackTrace();
                }
            } else {
                System.out.println("Report printing failed");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception localException1) {
            }
        }
    }*/
}
