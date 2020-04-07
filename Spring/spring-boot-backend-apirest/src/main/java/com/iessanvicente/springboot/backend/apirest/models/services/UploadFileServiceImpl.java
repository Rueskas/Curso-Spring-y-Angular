package com.iessanvicente.springboot.backend.apirest.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UploadFileServiceImpl implements IUploadFileService {


	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private final static String DIRECTORY_UPLOAD = "upload-dir";
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFile = getPath(filename);
		log.info(pathFile.toString());
		Resource resource;
		resource = new UrlResource(pathFile.toUri());
		if(!resource.exists() || !resource.isReadable()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not exists or can not be readed");
		}
		return resource;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String filename = UUID.randomUUID().toString() + "_"+file.getOriginalFilename().replace(" ", "_");
		Path path = getPath(filename);

		log.info(path.toString());
		Files.copy(file.getInputStream(),path);
		return filename;
	}

	@Override
	public boolean delete(String filename) {
		if(filename != null && !filename.isEmpty()) {
			Path pathAvatarBefore = Paths.get("upload-dir").resolve(filename).toAbsolutePath();
			File fileBefore = pathAvatarBefore.toFile();
			if(fileBefore.exists() && fileBefore.canRead()) {
				fileBefore.delete();
				return true;
			}
		}
		return false;
	}

	@Override
	public Path getPath(String filename) {
		return Paths.get(DIRECTORY_UPLOAD).resolve(filename).toAbsolutePath();
	}

}
