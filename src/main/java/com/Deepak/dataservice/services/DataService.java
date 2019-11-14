package com.Deepak.dataservice.services;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Deepak.dataservice.constants.IConstants;
import com.Deepak.dataservice.facade.InMessageFacade;
import com.Deepak.dataservice.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DataService {
	
	@Autowired
	private ObjectMapper mapper;

	@Value("${file.location}")
	private String fileLocation;

	@Value("${file.archive.location}")
	private String fileArchiveLocation;
	
	@Autowired
	private InMessageFacade inMessageFacade;

	public void loadFiles() {

		Map<String, List<String>> map = new HashMap<>();
		File folder = new File(fileLocation);
		try {
			createArchiveDirectory(folder.getPath().toString());
			processFolder(folder,map);
		} catch (Exception e) {

		}

	}

	private void processFolder(File folderLoc, Map<String, List<String>> map) {

		File[] files=folderLoc.listFiles();
		for(File file :files) {
			if(!file.isDirectory()) {
				processFile(file,map);
			}
		}
	}

	private void processFile(File file, Map<String, List<String>> map) {

		try {
			List<String> fileContent=Files.readAllLines(file.toPath());
			map.put(file.getName(), fileContent);
			processMessageQueue(file.getName(),fileContent);
		}catch(Exception e) {
			log.error("Error occured while processing"+e.getMessage());
			
		}
	}

	private void processMessageQueue(String name, List<String> payload) {
		Map<String,Object> map=new HashMap<>();
		map.put(IConstants.DATA_STR, payload);
		processMessageFacade(map);
		
	}

	private void processMessageFacade(Map<String, Object> inMap) {

		String json=null;
		try {
			json= mapper.writeValueAsString(inMap);
			inMessageFacade.process(json);
			
			
		}catch(Exception e) {
			log.error("Error occured " + e.getMessage());
		}
	}

	private void createArchiveDirectory(String filePath) {
		try {
			Path path = Paths.get(fileArchiveLocation + CommonUtil.getTodaysDateInUIFormat());
			log.info("Creating Archive Folder:" + path.toString());
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}

		} catch (Exception e) {
			log.error("Error occured while creating archive folder" +e.getMessage());

		}
	}

}
