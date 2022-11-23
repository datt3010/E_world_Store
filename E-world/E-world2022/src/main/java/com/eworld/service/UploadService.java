package com.eworld.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {
	
	@Autowired
	ServletContext ctx;
	 
	public File save (MultipartFile file, String folder) throws IOException {

			File dir = new File(ctx.getRealPath(folder));

			if(!dir.exists()) {
				dir.mkdirs();
			}
			
			File saveFile = new File(dir, file.getOriginalFilename());
			file.transferTo(saveFile);
		
		return saveFile;
	}
	

	public String saveMultiFiles(MultipartFile multipartFile, String folder) {
		try {
			byte[] bytes = multipartFile.getBytes();
			Path path = Paths.get(ctx.getRealPath(folder + multipartFile.getOriginalFilename()));
			Files.write(path, bytes);
			return multipartFile.getOriginalFilename();
		} catch (IOException e) {
			return null;
		}
}
}
