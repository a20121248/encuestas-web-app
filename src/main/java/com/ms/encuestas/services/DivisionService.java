package com.ms.encuestas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Division;
import com.ms.encuestas.repositories.DivisionRepository;

@Service
public class DivisionService implements DivisionServiceI {
	@Autowired
	private DivisionRepository divisionRepository;

	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<Division> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Division findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public int save(Division division) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void delete(Division division) {
		// TODO Auto-generated method stub

	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
}
