package com.itbcp.report.core;

import com.itbcp.report.shared.JasperReportGeneration;
import com.itbcp.report.shared.WebException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class ReportManager implements IReportManager {

    @Value("${spring.datasource.url}")
    private String DATASOURCE_URL;

    @Override
    public byte[] generate(JasperReportGeneration input) {
        try {

            if(input == null || input.getName() == null || input.getName().isEmpty())
                throw new WebException("Aviso", "La solicituda es inválida. Verifique la información antes de continuar", 500);

            File report = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + input.getName() + ".jasper");

            if (!report.exists())
                throw new WebException("Aviso", "El reporte solicitado: " + report + ", es inválido o no existe. Verifique la información antes de continuar", 500);

            try (Connection con = DriverManager.getConnection(DATASOURCE_URL)) {
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, input.getParameters(), con);

                if (input.getType().equals("XLSX")) {

                    JRXlsxExporter excelExporter = new JRXlsxExporter();
                    SimpleXlsxExporterConfiguration configuration = new SimpleXlsxExporterConfiguration();
                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    excelExporter.setConfiguration(configuration);
                    excelExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    excelExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    excelExporter.exportReport();

                    return os.toByteArray();
                }

                if (input.getType().equals("HTML")) {

                    HtmlExporter exporter = new HtmlExporter();

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    exporter.setExporterOutput(new SimpleHtmlExporterOutput(os));
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.exportReport();

                    return os.toByteArray();
                }

                if (input.getType().equals("DOCX")) {

                    JRDocxExporter exporter = new JRDocxExporter();

                    ByteArrayOutputStream os = new ByteArrayOutputStream();
                    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                    exporter.exportReport();

                    return os.toByteArray();
                }

                JRPdfExporter pdfExporter = new JRPdfExporter();
                SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                pdfExporter.setConfiguration(configuration);
                pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                pdfExporter.exportReport();

                return os.toByteArray();
            }
        } catch (JRException e) {
            throw new WebException("Aviso", "Hubo un error al procesar el archivo Jasper: " + e.toString(), 500);
        } catch (SecurityException e) {
            throw new WebException("Aviso", "No hay permisos suficientes para acceder al archivo: " + e.toString(), 500);
        } catch (SQLException e) {
            throw new WebException("Aviso", "El tiempo de espera de conexión a proveedor de servicios ha expirado: " + e.toString(), 500);
        } catch (UnsupportedEncodingException e) {
            throw new WebException("Aviso", "No se pudo procesar la conexión con el proveedor de servicio de datos: " + e.toString(), 500);
        }
    }

    @Override
    public byte[] fill(JasperReportGeneration input) {
        try {

            if(input == null || input.getName() == null || input.getName().isEmpty())
                throw new WebException("Aviso", "La solicituda es inválida. Verifique la información antes de continuar", 500);

            var report = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + input.getName() + ".jasper");

            if (!report.exists())
                throw new WebException("Aviso", "El reporte solicitado: " + report + ", es inválido o no existe. Verifique la información antes de continuar", 500);

            var parameters = input.getParameters();

            if (parameters == null || !parameters.containsKey("datasource"))
                throw new WebException("Aviso", "El reporte solicitado: " + report + ", no contiene ningún origen de recursos. Verifique la información antes de continuar", 500);

            var json = ((String)parameters.get("datasource"));
            var dataSource = new JsonDataSource(new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8)));

            var jasperReport = (JasperReport) JRLoader.loadObject(report);
            var jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            if (input.getType().equals("XLSX")) {

                var excelExporter = new JRXlsxExporter();
                var configuration = new SimpleXlsxExporterConfiguration();
                var os = new ByteArrayOutputStream();

                excelExporter.setConfiguration(configuration);
                excelExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                excelExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                excelExporter.exportReport();

                return os.toByteArray();
            }

            if (input.getType().equals("HTML")) {

                var exporter = new HtmlExporter();
                var os = new ByteArrayOutputStream();

                exporter.setExporterOutput(new SimpleHtmlExporterOutput(os));
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

                var configuration = new SimpleHtmlExporterConfiguration();
                configuration.setHtmlHeader(
                        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">" +
                                "<html>" +
                                "<head>" +
                                "  <title></title>" +
                                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>" +
                                "  <style type=\"text/css\">" +
                                "    a {text-decoration: none}" +
                                "   .jrPage {width:100% !important}" +
                                "  </style>" +
                                "</head>" +
                                "<body text=\"#000000\" link=\"#000000\" alink=\"#000000\" vlink=\"#000000\">" +
                                "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">" +
                                "<tr><td align=\"center\">");

                configuration.setHtmlFooter(
                        "</td></table></body></html>");
                exporter.setConfiguration(configuration);

                exporter.exportReport();

                return os.toByteArray();
            }

            if (input.getType().equals("DOCX")) {

                var exporter = new JRDocxExporter();
                var os = new ByteArrayOutputStream();

                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.exportReport();

                return os.toByteArray();
            }

            var pdfExporter = new JRPdfExporter();
            var configuration = new SimplePdfExporterConfiguration();
            var os = new ByteArrayOutputStream();

            pdfExporter.setConfiguration(configuration);
            pdfExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            pdfExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(os));
            pdfExporter.exportReport();

            return os.toByteArray();

        } catch (JRException ex) {
            throw new WebException("Aviso", "Hubo un error al procesar el archivo Jasper", 500);
        } catch (SecurityException ex) {
            throw new WebException("Aviso", "No hay permisos suficientes para acceder al archivo", 500);
        } catch (UnsupportedEncodingException e) {
            throw new WebException("Aviso", "No se pudo procesar la conexión con el proveedor de servicio de datos", 500);
        }
    }
}