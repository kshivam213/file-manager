package com.manager.service;

import java.io.File;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceTests {

	@Autowired
	FileStorageService fileStorageService;
	
	@Value("${file.upload-dir}") String fileDir;
	
	String uid = UUID.randomUUID().toString();
	
	@Test
	public void createCopyTest() {
		
		
		fileStorageService.createFile(uid);
		
		File file = new File(fileDir+"/"+uid+".pdf");
		assertThat(file.exists() == true);
	}
	
	@Test
	public void deleteTest() {
		
		fileStorageService.deleteFile(uid);
		
		File file = new File(fileDir+"/"+uid+".pdf");
		assertThat(file.exists() == false);
	}
}
