package com.manager.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.manager.constants.AppConstant;
import com.manager.utils.AppUtils;

@Service
public class FileStorageService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    private final Path fileStorageLocation;
    
    private String fileDir;
    
    @Autowired
    public FileStorageService(@Value("${file.upload-dir}") String fileDir) {
    		
    		this.fileDir = fileDir;
        this.fileStorageLocation = Paths.get(fileDir)
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            AppUtils.preCondition(true, "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }
    
    public Resource loadFile(String fileId) {
    		String fileName= getFileNameById(fileId);
        Resource resource = null;
    		try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            resource = new UrlResource(filePath.toUri());
            
            AppUtils.preCondition(!resource.exists(), "File not found "+fileName);
        } catch (MalformedURLException ex) {
        		ex.printStackTrace();
        		AppUtils.preCondition(true, "File not found "+fileName, ex);
        }
        
        return resource;
    }

    public void download(String fileId, HttpServletResponse response) {
    		
    		String fileName= getFileNameById(fileId);
    		File file = new File(fileDir+"/"+fileName);
    		
    		AppUtils.preCondition(!file.exists(), "No such file exists");
    		
    		InputStream inputSteam = null; 
    		OutputStream outputSream = null;
    		try {
	    		inputSteam = new FileInputStream(file);
	    		
	    	    response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	
	    	    int read=0;
	    	    byte[] bytes = new byte[1024];
	    	    outputSream = response.getOutputStream();
	
	    	    while((read = inputSteam.read(bytes))!= -1){
	    	    		outputSream.write(bytes, 0, read);
	    	    }
    		}catch(IOException iex) {
    			iex.printStackTrace();
    			AppUtils.preCondition(true, "Failed to read file "+fileName, iex);
    		}finally {
    			try {
				outputSream.flush();
				outputSream.close();
			} catch (NullPointerException | IOException e) {
				 	AppUtils.preCondition(true, "Invalid file "+fileName, e);
			}
	    	}
    }
    
	public String createFile(String fileId)  {
		
		String newFileName= fileId+".pdf";
		logger.info("File name to create .. "+newFileName);
		try {
            Path src = this.fileStorageLocation.resolve(AppConstant.MASTER_FILE_NAME).normalize();
            Path dest  = this.fileStorageLocation.resolve(newFileName).normalize();
            Files.copy(src,dest);
		}catch(IOException iex) {
			iex.printStackTrace();
			AppUtils.preCondition(true, "No Such file exist .. ", iex);
		}
		return newFileName;
	}
	
	public String deleteFile(String fileId) {
		
		String fileName= getFileNameById(fileId);
    
		Path fileToDeletePath =this.fileStorageLocation.resolve(fileName).normalize();
	    try {
			Files.delete(fileToDeletePath);
		} catch (IOException e) {
			e.printStackTrace();
			AppUtils.preCondition(true, "No Such file exist .. "+fileName, e);
		}
	    
	    return fileName;
	}
	
	public String getFileNameById(String fileId) {
		String fileName= "";
		if(fileId.equalsIgnoreCase("master"))
			fileName= AppConstant.MASTER_FILE_NAME;
		else
			fileName= fileId+".pdf";
		return fileName;
	}
}
