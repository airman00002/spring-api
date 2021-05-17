package com.airtest.stockbackend.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	void init(); //*จัดเตรียม part file ที่จะ upload
	String store(MultipartFile file);
}
