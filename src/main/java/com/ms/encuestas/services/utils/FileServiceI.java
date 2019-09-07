package com.ms.encuestas.services.utils;

import org.springframework.core.io.Resource;

public interface FileServiceI {
	public Resource loadFileAsResource(String fileName);

	public Resource loadFileLogAsResource(String fileName);
}
