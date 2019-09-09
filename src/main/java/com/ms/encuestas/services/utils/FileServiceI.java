package com.ms.encuestas.services.utils;

import java.nio.file.Path;

import org.springframework.core.io.Resource;

public interface FileServiceI {
	public Resource loadFileAsResource(String fileName);

	Resource loadFileLogAsResource(Path fileName);
}
