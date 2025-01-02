package com.itbcp.report.core;

import com.itbcp.report.shared.JasperReportGeneration;

public interface IReportManager {
    byte[] generate(JasperReportGeneration input);
    byte[] fill(JasperReportGeneration input);
}
