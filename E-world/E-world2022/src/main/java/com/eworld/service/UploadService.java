package com.eworld.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

	public List<String> saveMulitpleFiles(List<MultipartFile> fileList, String folder) throws  IOException{
		List<String> fileNames =  new ArrayList<>();
		File dir = new File(ctx.getRealPath(folder));
		if(!dir.exists()){
			dir.mkdirs();
		}
		fileList.stream().forEach(file -> {
			try {
				File saveFile = new File(dir, file.getOriginalFilename());
				file.transferTo(saveFile);
				fileNames.add(file.getOriginalFilename());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		return fileNames;
	}

}
