package com.itbcp.report.shared;

import net.sf.jasperreports.engine.JRParameter;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JasperReportGeneration {
    private String name;
    private String type;
    private Boolean fill;
    private List<JasperReportParameterGeneration> parameters;

    public JasperReportGeneration() {
    }

    public JasperReportGeneration(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public JasperReportGeneration(String name, String type, List<JasperReportParameterGeneration> parameters) {
        this.name = name;
        this.type = type;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getExtension() {
        switch (type) {
            case "TXT" : return "txt";
            case "XLSX" : return "xlsx";
            case "HTML" : return "html";
            case "DOCX" : return "docx";
            default: return "pdf";
        }
    }

    public String getMimeType() {
        switch (type) {
            case "TXT" : return "text/plain";
            case "XLSX" : return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "HTML" : return "text/html";
            case "DOCX" : return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            default: return "application/pdf";
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, Object> getParameters() throws UnsupportedEncodingException {
        HashMap<String, Object> output = new HashMap<>();

        if (type.equals("XLSX") || type.equals("HTML"))
            output.put(JRParameter.IS_IGNORE_PAGINATION, true);

        if (this.parameters == null)
            this.parameters = new ArrayList<>();

        for (JasperReportParameterGeneration item : this.parameters) {
            output.put(item.getName(), item.getValue());
        }

        return output;
    }
    public void setParameters(List<JasperReportParameterGeneration> parameters) {
        this.parameters = parameters;
    }

    public void setFill() {
        this.fill = Boolean.TRUE;
    }

    public boolean getFill() {
        return this.fill != null && this.fill;
    }
}