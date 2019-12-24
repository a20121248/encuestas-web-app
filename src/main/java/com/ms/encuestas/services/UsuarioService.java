package com.ms.encuestas.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.encuestas.models.Rol;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.RolRepository;
import com.ms.encuestas.repositories.UsuarioRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;

@Service
public class UsuarioService implements UserDetailsService, UsuarioServiceI {
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RolRepository rolRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuariosDependientes(Long procesoId, String posicionCodigo) {
		return usuarioRepository.findUsuariosDependientes(procesoId, posicionCodigo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuariosDependientesCompletados(Long procesoId, String posicionCodigo) {
		List<Usuario> usuarios = usuarioRepository.findUsuariosDependientes(procesoId, posicionCodigo);
		return usuarios.stream().filter(item -> item.isEstado()).collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuariosDependientesReplicar(Long procesoId, String posicionCodigo, String responsablePosicionCodigo, Long perfilId) {
		return usuarioRepository.findUsuariosDependientesReplicar(procesoId, posicionCodigo, responsablePosicionCodigo, perfilId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String codigo) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByCodigo(codigo);
		
		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario " + codigo +" en el sistema.");
			throw new UsernameNotFoundException("Error en el login: no existe el usuario " + codigo +" en el sistema.");
		}		
		
		usuario.setLstRoles(rolRepository.findAllByUsuarioCodigo(codigo));
		List<GrantedAuthority> authorities = usuario.getLstRoles()
				.stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
				.peek(authority -> logger.info("Rol: " + authority.getAuthority()))
				.collect(Collectors.toList());
	
		return new User(usuario.getCodigo(), usuario.getContrasenha(), true, true, true, true, authorities);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Long count() {
		return usuarioRepository.count();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		List<Usuario> usuarios = usuarioRepository.findAll(); 
		if (usuarios == null) {
			logger.info("No existe ningún colaborador registrado en la base de datos.");
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}
	
	@Override
	public List<String> findAllCodigos() {
		List<String> codigos = usuarioRepository.findAllCodigos();
		if (codigos == null) {
			logger.info("No existe ningún colaborador registrado en la base de datos.");
			codigos = new ArrayList<String>();
		}
		return codigos;
	}
	

	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigo(String codigo) {
		try {
			return usuarioRepository.findByCodigo(codigo);
		} catch(EmptyResultDataAccessException e) {
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("No se pudo obtener al colaborador con código '%s' porque está repetido en la base de datos.", codigo));
			return null;
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigoAndProceso(String codigo, Long procesoId) {
		return usuarioRepository.findByCodigoAndProceso(codigo, procesoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByPosicionCodigo(String posicionCodigo, Long procesoId) {
		return usuarioRepository.findByPosicionCodigo(posicionCodigo, procesoId);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario findByCodigoWithPosicion(String codigo) {
		return usuarioRepository.findByCodigoWithPosicion(codigo);
	}
	
	@Override
	public void deleteByCodigo(String codigo) {
		usuarioRepository.deleteByCodigo(codigo);		
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario insert(Usuario usuario) {
		return usuarioRepository.insert(usuario);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Usuario update(Usuario usuario) {
		return usuarioRepository.update(usuario);
	}
	
	@Override
	public void deleteAll() {
		usuarioRepository.deleteAll();
		logger.info("Se eliminaron todos los colaboradores de la base de datos.");	
	}

	@Override
	public List<GrantedAuthority> getRolesByCodigo(String codigo) {
		List<Rol> roles = usuarioRepository.findRolesByCodigo(codigo);
		if (roles != null && !roles.isEmpty()) {
			return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	public Usuario findByUsuarioRed(String usuarioRed) {
		String tipo = "";
		try {
			if (usuarioRed.length()>5 && usuarioRed.substring(0, 5).toUpperCase().equals("EPPS\\")) {
				tipo = "GENERALES";
				return usuarioRepository.findByUsuarioGenerales(usuarioRed);
			} else {
				tipo = "VIDA";
				return usuarioRepository.findByUsuarioVida(usuarioRed);
			}			
		} catch(EmptyResultDataAccessException e) {
			logger.error(String.format("El usuario de red '%s' no existe en la base de datos de %s.", usuarioRed, tipo));
			return null;
		} catch(IncorrectResultSizeDataAccessException e) {
			logger.error(String.format("El usuario de red '%s' está repetido en la base de datos de %s.", usuarioRed, tipo));
			return null;
		}
	}
	
	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE COLABORADORES====================================");
		List<String> usuarioCodigos = findAllCodigos();
		List<String> usuarioCodigosLeidos = new ArrayList<String>();
		
		try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("COLABORADORES");
			if (hoja == null) {
				logger.error("No se pudo procesar el Excel porque la hoja COLABORADORES no existe.");
				logger.info("======================FIN DE CARGA DE COLABORADORES====================================");
				return;
			}
           
			Iterator<Row> filas = hoja.iterator();
           
			/*if (!menuControlador.navegador.validarFilaNormal(filas.next(), new ArrayList(Arrays.asList("CODIGO","NOMBRE","ATRIBUIBLE","TIPO GASTO","CLASE GASTO")))) {
               menuControlador.navegador.mensajeError(titulo,menuControlador.MENSAJE_UPLOAD_HEADER);
               return null;
           	}*/
	   		int numFilasOmitir = 6;
	   		for (int i = 0; i < numFilasOmitir; ++i) filas.next();
           
	   		DataFormatter dataFormatter = new DataFormatter();
	   		for (int numFila = numFilasOmitir+1; filas.hasNext(); ++numFila) {
	   			Iterator<Cell> celdas = filas.next().cellIterator();
        	   
	   			String codigo = dataFormatter.formatCellValue(celdas.next());
	   			if (codigo.equals("")) break;
	   			String usuarioVida = dataFormatter.formatCellValue(celdas.next());
	   			String usuarioGenerales = dataFormatter.formatCellValue(celdas.next());
	   			String nombreCompleto = dataFormatter.formatCellValue(celdas.next());
	   			String rolNombre = dataFormatter.formatCellValue(celdas.next());
	   			String accion = dataFormatter.formatCellValue(celdas.next());
                
	   			Usuario usuario = new Usuario();
	   			usuario.setCodigo(codigo);
	   			usuario.setUsuarioVida(usuarioVida);
	   			usuario.setUsuarioGenerales(usuarioGenerales);
	   			usuario.setNombreCompleto(nombreCompleto);

	   			if (accion.equals("CREAR")) {
	   				if (usuarioCodigosLeidos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: La matrícula '%s' ya fue procesada para crearse en este Excel. Corregir el archivo y probar una nueva carga.", numFila, codigo));
	   				} else if (usuarioCodigos.contains(codigo)) {
	   					logger.error(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el código '%s' ya fue usado.", numFila, nombreCompleto, codigo));
	   				} else {
	   					if (rolNombre.equals("USUARIO")) {
		   					rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.insert(usuario);
		   		   			usuarioCodigosLeidos.add(codigo);
		   					logger.info(String.format("FILA %d: Se creó el colaborador con matrícula '%s'.", numFila, codigo));
	   					} else if (rolNombre.equals("ADMINISTRADOR")) {
	   						rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(1), codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.insert(usuario);
		   					usuarioCodigosLeidos.add(codigo);
		   					logger.info(String.format("FILA %d: Se creó el colaborador con matrícula '%s'.", numFila, codigo));
	   					} else {
	   						logger.error(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el rol '%s' no existe.", numFila, nombreCompleto, rolNombre));
	   					}
	   				}
	   			} else if (accion.equals("EDITAR")) {
	   				Usuario usuarioBuscado = findByCodigo(codigo);
	   				if (usuarioBuscado != null) {
	   					if (rolNombre.equals("USUARIO")) {
		   					rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.update(usuario);
		   					logger.info(String.format("FILA %d: Se editó el colaborador con código '%s'.", numFila, codigo));
	   					} else if (rolNombre.equals("ADMINISTRADOR")) {
	   						rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(1), codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.update(usuario);
		   					logger.info(String.format("FILA %d: Se editó el colaborador con código '%s'.", numFila, codigo));
	   					} else {
	   						logger.error(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el rol '%s' no existe.", numFila, nombreCompleto, rolNombre));
	   					}
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar el colaborador con matrícula '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else if (accion.equals("ELIMINAR")) {
	   				Usuario usuarioBuscado = findByCodigo(codigo);
	   				if (usuarioBuscado != null) {
	   					usuarioRepository.delete(usuarioBuscado);
	   					rolRepository.deletesRolesUsuario(codigo);
	   					logger.info(String.format("FILA %d: Se eliminó el colaborador con matrícula '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo eliminar el colaborador con matrícula '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else {
	   				logger.error(String.format("FILA %d: No se realizó ninguna acción en el colaborador con matrícula '%s' porque la acción '%s' no existe.", numFila, codigo, accion));
	   			}
	   		}
	   		libro.close();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		logger.info("======================FIN DE CARGA DE COLABORADORES====================================");
	}

	@Override
	public Resource downloadExcel() {
        SXSSFWorkbook wb = excelService.crearLibro();
        CellStyle cabeceraEstilo = excelService.cabeceraEstilo(wb);
        CellStyle bordeEstilo = excelService.bordeEstilo(wb);
        CellStyle fechaEstilo = excelService.fechaEstilo(wb);
        SXSSFSheet sh = wb.createSheet("COLABORADORES");

        List<Map<String,Object>> data = usuarioRepository.findAllList();
        List<Integer> widths = Arrays.asList(3000, 4000, 4000, 8000, 5000, 3000, 3000);
        
        if (data == null || data.size() == 0) {
        	data = usuarioRepository.findAllListEmpty();
        	excelService.crearCabecera(sh, 0, data, widths, cabeceraEstilo);
        	return excelService.crearResource(wb);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data, widths, cabeceraEstilo);
        for (Map<String, Object> fila: data) {
    		int colNum = 0;
    		Row row = sh.createRow(rowNum++);    		
    		excelService.createCell(row, colNum++, (String) fila.get("MATRICULA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("USUARIO_VIDA"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("USUARIO_GENERALES"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("NOMBRE_COMPLETO"), bordeEstilo);
    		excelService.createCell(row, colNum++, (String) fila.get("ROL"), bordeEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_CREACION"), fechaEstilo);
    		excelService.createCell(row, colNum++, (Date) fila.get("FECHA_ACTUALIZACION"), fechaEstilo);
        }
        return excelService.crearResource(wb);
	}
}
