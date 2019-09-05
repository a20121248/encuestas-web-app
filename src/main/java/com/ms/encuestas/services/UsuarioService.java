package com.ms.encuestas.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ms.encuestas.models.Area;
import com.ms.encuestas.models.Centro;
import com.ms.encuestas.models.DatosPosicion;
import com.ms.encuestas.models.LineaCanal;
import com.ms.encuestas.models.Objeto;
import com.ms.encuestas.models.Perfil;
import com.ms.encuestas.models.Posicion;
import com.ms.encuestas.models.Proceso;
import com.ms.encuestas.models.Rol;
import com.ms.encuestas.models.Tipo;
import com.ms.encuestas.models.Usuario;
import com.ms.encuestas.repositories.PosicionRepository;
import com.ms.encuestas.repositories.RolRepository;
import com.ms.encuestas.repositories.UsuarioRepository;
import com.ms.encuestas.services.utils.ExcelServiceI;
import com.ms.encuestas.services.utils.FileServiceI;

@Service
public class UsuarioService implements UserDetailsService, UsuarioServiceI {
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Autowired
	private FileServiceI fileService;
	@Autowired
	private ExcelServiceI excelService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private PosicionRepository posicionRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findUsuariosDependientesByCodigo(Long procesoId, String usuarioCodigo) {
		return usuarioRepository.findUsuariosDependientesByCodigo(procesoId, usuarioCodigo);
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
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos.", codigo));
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
	@Transactional(readOnly = true)
	public Usuario findByCodigoWithPosicionFull(String codigo) {
		Usuario usuario = usuarioRepository.findByCodigoWithPosicion(codigo);
		usuario.setPosicion(posicionRepository.findByCodigoWithAreaAndCentro(usuario.getPosicion().getCodigo()));
		return usuario;
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario save(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub
		
	}

	public void deleteById(String codigo) {
		// TODO Auto-generated method stub
		
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
	public Usuario findByUsuarioGenerales(String usuarioRed) {
		try {
			return usuarioRepository.findByUsuarioGenerales(usuarioRed);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos de Generales.", usuarioRed));
			return null;
		}
	}

	@Override
	public Usuario findByUsuarioVida(String usuarioRed) {
		try {
			return usuarioRepository.findByUsuarioVida(usuarioRed);
		} catch(EmptyResultDataAccessException e) {
			logger.info(String.format("No se encontró al usuario '%s' en la base de datos de Vida.", usuarioRed));
			return null;
		}
	}

	@Override
	public void processExcel(InputStream file) {
		logger.info("======================INICIANDO CARGA DE COLABORADORES====================================");
		List<String> usuarioCodigos = findAllCodigos();
		
		try (XSSFWorkbook libro = new XSSFWorkbook(file)) {
        	XSSFSheet hoja = libro.getSheet("COLABORADORES");
			if (hoja == null) {
				logger.error("No se pudo procesar el Excel porque la hoja COLABORADORES no existe.");
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
	   				if (!usuarioCodigos.contains(codigo)) {
	   					if (rolNombre.equals("USUARIO")) {
		   					rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.insert(usuario);
		   					logger.info(String.format("FILA %d: Se creó el colaborador '%s'.", numFila, codigo));
	   					} else if (rolNombre.equals("ADMINISTRADOR")) {
	   						rolRepository.deletesRolesUsuario(codigo);
	   						rolRepository.insertRolUsuario(new Long(1), codigo);
	   						rolRepository.insertRolUsuario(new Long(2), codigo);
		   					usuarioRepository.insert(usuario);
		   					logger.info(String.format("FILA %d: Se creó el colaborador '%s'.", numFila, codigo));
	   					} else {
	   						logger.info(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el rol '%s' no existe.", numFila, nombreCompleto, rolNombre));
	   					}
	   				} else {
	   					logger.info(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el código '%s' ya fue usado.", numFila, nombreCompleto, codigo));
	   				}
	   			} else if (accion.equals("EDITAR")) {
	   				Usuario usuarioBuscado = usuarioRepository.findByCodigo(codigo);
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
	   						logger.info(String.format("FILA %d: No se pudo crear el colaborador '%s' porque el rol '%s' no existe.", numFila, nombreCompleto, rolNombre));
	   					}
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo editar el colaborador con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else if (accion.equals("ELIMINAR")) {
	   				Usuario usuarioBuscado = usuarioRepository.findByCodigo(codigo);
	   				if (usuarioBuscado != null) {
	   					usuarioRepository.delete(usuarioBuscado);
	   					logger.info(String.format("FILA %d: Se eliminó el colaborador con código '%s'.", numFila, codigo));
	   				} else {
	   					logger.error(String.format("FILA %d: No se pudo eliminar el colaborador con código '%s' porque no se encontró en la base de datos.", numFila, codigo));
	   				}
	   			} else {
	   				logger.error(String.format("FILA %d: No se realizó ninguna acción en el colaborador con código '%s' porque la acción '%s' no existe.", numFila, codigo, accion));
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
		Path currentRelativePath = Paths.get("");
		String currentPath = currentRelativePath.toAbsolutePath().toString();
		
		List<String> alphabets = Arrays.asList(currentPath, "storage", "app", "reportes", "Colaboradores.xlsx");
		String result = String.join(File.separator, alphabets);
		
        SXSSFWorkbook wb = excelService.crearLibro();
        SXSSFSheet sh = wb.createSheet("COLABORADORES");

        List<Map<String,Object>> data = usuarioRepository.findAllList();
        
        if (data == null || data.size() == 0) {
        	data = usuarioRepository.findAllListEmpty();
        	excelService.crearCabecera(sh, 0, data);
            excelService.crearArchivo(wb, result);
    		return fileService.loadFileAsResource(result);
        }
        
        int rowNum = 0;
        excelService.crearCabecera(sh, rowNum++, data);
		CellStyle dateStyle = wb.createCellStyle();
	    dateStyle.setDataFormat((short)14);
        for (Map<String, Object> fila: data) {
    		Row row = sh.createRow(rowNum++);
    		int colNum = 0;
    		row.createCell(colNum).setCellValue((String) fila.get("MATRICULA"));sh.setColumnWidth(colNum++, 3000);
    		row.createCell(colNum).setCellValue((String) fila.get("USUARIO_VIDA"));sh.setColumnWidth(colNum++, 4000);
    		row.createCell(colNum).setCellValue((String) fila.get("USUARIO_GENERALES"));sh.setColumnWidth(colNum++, 4000);
    		row.createCell(colNum).setCellValue((String) fila.get("NOMBRE_COMPLETO"));sh.setColumnWidth(colNum++, 8000);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_CREACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
    		row.createCell(colNum).setCellValue((Date) fila.get("FECHA_ACTUALIZACION"));sh.setColumnWidth(colNum, 3000);row.getCell(colNum++).setCellStyle(dateStyle);
        }
        excelService.crearArchivo(wb, result);        	
		return fileService.loadFileAsResource(result);
	}
}
