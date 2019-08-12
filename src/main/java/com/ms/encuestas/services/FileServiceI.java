package com.ms.encuestas.services;

import org.springframework.core.io.Resource;

public interface FileServiceI {
	public Resource loadFileAsResource(String fileName);
}
