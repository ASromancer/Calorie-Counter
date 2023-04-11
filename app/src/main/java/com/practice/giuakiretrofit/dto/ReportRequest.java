package com.practice.giuakiretrofit.dto;

public class ReportRequest {
    private String dateTime;
    private String reportType;
    private int userId;

    public ReportRequest( int userId, String dateTime, String reportType) {
        this.dateTime = dateTime;
        this.reportType = reportType;
        this.userId = userId;
    }
}
