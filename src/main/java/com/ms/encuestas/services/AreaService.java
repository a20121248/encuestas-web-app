package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.repositories.AreaRepository;

@Service
public class AreaService implements AreaServiceI {
	@Autowired
	private AreaRepository areaRepository;

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Area> findAll() {
		return areaRepository.findAll();
	}

	public Area findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Area save(Area area) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Area area) {
		// TODO Auto-generated method stub

	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
