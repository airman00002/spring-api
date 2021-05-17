package com.airtest.stockbackend.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.airtest.stockbackend.exception.StorageException;

@Service // * พัฒนา app ต้องแบ่ง layer มาจาก Component
public class StorageServiceImpl implements StorageService {

	@Value("${app.upload.path:images}") // *ดึงค่า images มา binding ตัวแปร :images เป็น defualt
	private String path;
	private Path rootlocation; // *root

	@Override
	public void init() {// * สร้าง floder ถ้าไม่มี จะ new ใหม่
		this.rootlocation = Paths.get(path); // *ให้ไป get path จาก apppropoty ได้ images มา
		try {
			Files.createDirectories(rootlocation); // *ถ้าไม่มี path create ใหม่

		} catch (IOException ex) {
			throw new StorageException("Could not init Storage" + ex.getMessage());
		}
	}

	@Override
	public String store(MultipartFile file) { // *การเก็บ
		if (file == null || file.isEmpty()) { // *เป็น null มั้ย
			return null; // *return path ลูก
		}
		//*มีไฟล์
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());//* clear path ที่มี ... มา  
		try {
			if(fileName.contains("..")) {
				throw new StorageException("Path outdside current directory");
			}
			fileName = UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".")+1);  //*
		try(InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, this.rootlocation.resolve(fileName),StandardCopyOption.REPLACE_EXISTING);//* ถ้ามีอยู่แล้วจะทับของเก่า
			return fileName;  //* retrn ชื่อลูกและเก็บที่ database
		}
			
			
		} catch (IOException ex) {
			throw new StorageException("Failed to store file : "+fileName +" , " + ex.getMessage());
		}
		
	
	}

}
