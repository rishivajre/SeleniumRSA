package com.projectname.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {

	public List<HashMap<String, String>> getJsonDataToMap() throws IOException {

	    //reading the json to string
	    String jsonContent = FileUtils.readFileToString(new File("C:\\Users\\LENOVO\\eclipse-workspace\\SeleniumFrameworkDesign\\src\\test\\java\\rahulshettyacademy\\data\\PurchaseOrder.json"), StandardCharsets.UTF_8);
	    
	    //String to HashMap: it requires downloading the jackson dependency for conversion
	    //we just build this utility once and it will automatically push it into the framework
	    ObjectMapper mapper = new ObjectMapper();
	    
	    List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
	    });
	    
	    return data;
	    //{map, map1}
	}


}