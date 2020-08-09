package com.manager.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager.response.models.AppResponse;
import com.manager.service.FileStorageService;

/**
 * Controller related to file management
 * @author shivamkumar
 */
@RestController
@RequestMapping(value="/api/v1/file")
@CrossOrigin("*")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${server.ip}")
    String serverIp;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    /**
     * Post Api which create a copy of master file and return file Id 
     * @return AppResponse that contains fileName which is created along with ID
     */
    @GetMapping(path = "/copy", produces=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<AppResponse> uploadFile() {
        
    		Instant start = Instant.now();
		
    		String id = UUID.randomUUID().toString();
    		
    		logger.info("File started creating with Id: {}", id);
    		String fileName = fileStorageService.createFile(id);
        
    		Instant end = Instant.now();
		logger.info("Time taken to finish copy file creation -- {}", Duration.between(start, end).toMillis());
			
    		Map<String, String> resultBody = new HashMap<>();
    		resultBody.put("downloadUrl", serverIp+"/api/v1/file/download/"+id);
    		resultBody.put("id", id);
    		resultBody.put("fileName", fileName);
    		
    		AppResponse appResponse = AppResponse.builder().result(resultBody).success(true).description("File has been created. To download this file please make get request to downloadurl").build();
    		return ResponseEntity.ok(appResponse);
    }
    
    /**
     * Get Api that download either master or fileId
     * @param fileId either - (master or FileId)
     * @param response
     */
    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable("fileId") final String fileId, HttpServletResponse response) {
        
    		Instant start = Instant.now();
    		
    		logger.info("File started creating with Id: {}", fileId);
    		
    		fileStorageService.download(fileId, response);
    		
    		Instant end = Instant.now();
    		logger.info("Time taken to finish copy file creation -- {}", Duration.between(start, end).toMillis());
    		
    }
    
    /**
     * Delete Api that delete either master of Id based file
     * @param fileId (master or anyId)
     * @return app response that contains filename which deleted
     */
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<AppResponse> deleteFile(@PathVariable("fileId") final String fileId){
    		
    		Instant start = Instant.now();
    		logger.info("File started creating with Id: {}", fileId);
    		
		String fileName= fileStorageService.deleteFile(fileId);
    		
		Instant end = Instant.now();
		logger.info("Time taken to finish copy file creation -- {}", Duration.between(start, end).toMillis());
		
    		Map<String, String> resultBody = new HashMap<>();
    		resultBody.put("id", fileId);
    		resultBody.put("fileName", fileName);
    		
    		AppResponse appResponse = AppResponse.builder().result(resultBody).success(true).description("File has been deleted").build();
    		return ResponseEntity.ok(appResponse);
    }

}
