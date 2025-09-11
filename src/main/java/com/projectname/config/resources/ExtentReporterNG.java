package com.projectname.config.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {

	public static ExtentReports getReporterObject() 
	{
		String path = System.getProperty("user.dir") + "\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");	//metadata
		reporter.config().setDocumentTitle("Test Results");			//metadata

		ExtentReports extent = new ExtentReports(); // This class is responsible to drive all your reporting executions
		extent.attachReporter(reporter); // attaching complete report to the above main class: ExtentReports
		extent.setSystemInfo("Tester", "Rishi");
		extent.createTest(path);
		return extent;
		
		
		
	}

}
