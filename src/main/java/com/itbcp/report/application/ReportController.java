package com.itbcp.report.application;

import com.itbcp.report.core.IReportManager;
import com.itbcp.report.shared.JasperReportGeneration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api/services/app/Report")
public class ReportController {

    private final IReportManager reportManager;

    public ReportController(IReportManager reportManager) {
        this.reportManager = reportManager;
    }

    @PostMapping(value = "/Create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object create(@RequestBody JasperReportGeneration input) {

        byte[] reportResult = reportManager.generate(input);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(input.getMimeType()));
        headers.add("content-disposition","inline;filename=" + input.getName() + "." + input.getExtension());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(reportResult, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/Fill", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object fill(@RequestBody JasperReportGeneration input) {

        input.setFill();

        byte[] reportResult = reportManager.fill(input);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(input.getMimeType()));
        headers.add("content-disposition","inline;filename=" + input.getName() + "." + input.getExtension());
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return new ResponseEntity<>(reportResult, headers, HttpStatus.OK);
    }
}