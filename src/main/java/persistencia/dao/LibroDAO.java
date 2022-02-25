package persistencia.dao;

import java.util.List;

import negocio.vo.Libro;

public interface LibroDAO {

	List<Libro> obtenerDatosLibro();
	
	boolean insertarLibro(Libro l);
	
	boolean borrarLibro(Libro l);
	
	boolean actualizaLibro(Libro l, String isbn);
	
	//limpiar catalogo
	boolean truncateLibro();

	List<Libro> obtenerDatosLibroGT();
	
	List<Libro> obtenerLibroPorTitulo(String titulo);

	List<Libro> obtenerLibroPorPagina(int numPaginas);

	List<Libro> obtenerLibroPorPalabra(String titulo);
	
	List<Libro>obtenerLibroMenosDeXPaginas(int numPaginas);
	
	List<Libro> obtenerLibroPorGenero(String genero);
	
	List<Libro>obtenerLibroMasPaginas();
	
	List<Libro>obtenerLibroMenosPaginas();
	
	List<Libro>obtenerLibroMasPaginasGenero(String genero);
	
	List<Libro>obtenerLibroMenosPaginasGenero(String genero);

	void resumen();
	
}
