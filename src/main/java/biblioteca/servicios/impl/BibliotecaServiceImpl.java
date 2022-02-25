package biblioteca.servicios.impl;

import java.util.List;

import negocio.servicios.BibliotecaService;
import negocio.vo.Libro;
import persistencia.dao.LibroDAO;
import persistencia.dao.impl.LibroDaoIMPL;

public class BibliotecaServiceImpl implements BibliotecaService{

	//aqui no hay que implementar nada ,simplemente hacer un libroDAOImpl y devolverlo
	
	LibroDAO librosBD = new LibroDaoIMPL();

	@Override
	public boolean insertarLibro(Libro l) {
		return librosBD.insertarLibro(l);
	}

	@Override
	public boolean borrarLibro(Libro l) {
		return librosBD.borrarLibro(l);
	}

	@Override
	public boolean actualizaLibro(Libro l, String isbn) {
		return librosBD.actualizaLibro(l, isbn);
	}
	
	@Override
	public boolean limpiarCatalogo() {
		return librosBD.truncateLibro();
	}
	
	@Override
	public List<Libro> listaLibros() {
		List<Libro> catalogo = librosBD.obtenerDatosLibro();
		return catalogo;
	}

	@Override
	public List<Libro> listaLibrosGT() {
		List<Libro> catalogo = librosBD.obtenerDatosLibroGT();
		return catalogo;
	}

	@Override
	public List<Libro> obtenerLibroPorTitulo(String titulo) {
		return librosBD.obtenerLibroPorTitulo(titulo);
	}

	@Override
	public List<Libro> obtenerLibroPorPagina(int numPaginas) {
		return librosBD.obtenerLibroPorPagina(numPaginas);
	}

	@Override
	public List<Libro> obtenerLibroPorPalabra(String titulo) {
		return librosBD.obtenerLibroPorPalabra(titulo);
	}

	@Override
	public List<Libro> obtenerLibroMenosDeXPaginas(int numPaginas) {
		return librosBD.obtenerLibroMenosDeXPaginas(numPaginas);
	}

	@Override
	public List<Libro> obtenerLibroPorGenero(String genero) {
		return librosBD.obtenerLibroPorGenero(genero);
	}

	@Override
	public List<Libro> obtenerLibroMasPaginas() {
		return librosBD.obtenerLibroMasPaginas();
	}

	@Override
	public List<Libro> obtenerLibroMenosPaginas() {
		return librosBD.obtenerLibroMenosPaginas();
	}

	@Override
	public List<Libro> obtenerLibroMasPaginasGenero(String genero) {
		return librosBD.obtenerLibroMasPaginasGenero(genero);
	}

	@Override
	public List<Libro> obtenerLibroMenosPaginasGenero(String genero) {
		return librosBD.obtenerLibroMenosPaginasGenero(genero);
	}

	@Override
	public void resumen() {
		librosBD.resumen();
	}
	

}
