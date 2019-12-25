package com.ms.encuestas.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.repositories.ObjetoRepository;

@Service
public class ObjetoService implements ObjetoServiceI {
	private Logger logger = LoggerFactory.getLogger(ObjetoService.class);
    private final Long lineaTipoId = new Long(1); // Linea
    private final Long canalTipoId = new Long(2); // Canal
    private final Long productoTipoId = new Long(3); // Producto
    private final Long subcanalTipoId = new Long(4); // Subcanal
	
	@Autowired
	private ObjetoRepository objetoRepository;
	
	@Override
	public Long countLineas() {
		return objetoRepository.count(lineaTipoId);
	}
	
	@Override
	public Long countCanales() {
		return objetoRepository.count(canalTipoId);
	}
	
	@Override
	public Long countProductos() {
		return objetoRepository.count(productoTipoId);
	}
	
	@Override
	public Long countSubcanales() {
		return objetoRepository.count(subcanalTipoId);
	}

	
	@Override
	public List<Objeto> findAllLineas() {
		try {
			return objetoRepository.findAll(lineaTipoId);
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ninguna línea registrada en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}

	
	@Override
	public List<Objeto> findAllCanales() {
		try {
			return objetoRepository.findAll(canalTipoId);
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún canal registrado en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}
	
	@Override
	public List<Objeto> findAllProductos() {
		try {
			return objetoRepository.findAll(productoTipoId);
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún producto registrado en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}
	
	@Override
	public List<Objeto> findAllSubcanales() {
		try {
			return objetoRepository.findAll(subcanalTipoId);
		} catch(EmptyResultDataAccessException e) {
			logger.info("No existe ningún subcanal registrado en la base de datos.");
			return new ArrayList<Objeto>();
		}
	}
	
	@Override
	public void deleteAllLineas() {
		objetoRepository.deleteAll(lineaTipoId);
	}
	
	@Override
	public void deleteAllCanales() {
		objetoRepository.deleteAll(canalTipoId);
	}
	
	@Override
	public void deleteAllProductos() {
		objetoRepository.deleteAll(productoTipoId);
	}
	
	@Override
	public void deleteAllSubcanales() {
		objetoRepository.deleteAll(subcanalTipoId);
	}

	@Override
	public Objeto insertLinea(Objeto objeto) {
		return objetoRepository.insert(objeto, lineaTipoId);
	}
	
	@Override
	public Objeto insertCanal(Objeto objeto) {
		return objetoRepository.insert(objeto, canalTipoId);
	}	
	
	@Override
	public Objeto insertProducto(Objeto objeto) {
		return objetoRepository.insert(objeto, productoTipoId);
	}
	
	@Override
	public Objeto insertSubcanal(Objeto objeto) {
		return objetoRepository.insert(objeto, subcanalTipoId);
	}
	
	@Override
	public Objeto updateLinea(Objeto objeto) {
		return objetoRepository.update(objeto, lineaTipoId);
	}
	
	@Override
	public Objeto updateCanal(Objeto objeto) {
		return objetoRepository.update(objeto, canalTipoId);
	}
	
	@Override
	public Objeto updateProducto(Objeto objeto) {
		return objetoRepository.update(objeto, productoTipoId);
	}
	
	@Override
	public Objeto updateSubcanal(Objeto objeto) {
		return objetoRepository.update(objeto, subcanalTipoId);
	}

	@Override
	public Objeto findById(Long id) {
		try {
			return objetoRepository.findById(id);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener el objeto de costos con ID='%d' porque está repetido en la base de datos.", id));
			return null;
		}
	}
	
	@Override
	public void deleteById(Long id) {
		objetoRepository.deleteById(id);
	}
	
	@Override
	public Objeto softDelete(Objeto objeto) {
		return objetoRepository.softDelete(objeto);
	}
	
	@Override
	public Objeto softUndelete(Objeto objeto) {
		return objetoRepository.softUndelete(objeto);
	}
}
