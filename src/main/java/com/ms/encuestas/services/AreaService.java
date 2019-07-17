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
		return areaRepository.count();
	}

	public List<Area> findAll() {
		return areaRepository.findAll();
	}
	
	public List<Area> findAllWithDivision() {
		return areaRepository.findAllWithDivision();
	}

	public Area findById(Long id) {
		return areaRepository.findById(id);
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

	public Area findByIdWithDivision(Long id) {
		return areaRepository.findByIdWithDivision(id);
	}
}
