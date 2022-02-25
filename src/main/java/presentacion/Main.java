package presentacion;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import biblioteca.servicios.impl.BibliotecaServiceImpl;
import negocio.servicios.BibliotecaService;
import negocio.vo.Genero;
import negocio.vo.Libro;

public class Main {
	
	private static BibliotecaService servicios = new BibliotecaServiceImpl();

	public static void main(String[] args) {
		int opcion = -1;
		int opcion2 = -1;
		do {
			menu();
			opcion = leerOpcion();
			switch (opcion) {
			case 0:
				System.out.println("Hasta la próxima");
				break;
			case 1:
				insertarLibro();
				// Don Quijote:46N5fdB:Ficcion:Miguel de Cervantes:523
				// Platero y yo:64gr98:Novela:Miguel de Cervantes:256
				// La Celestina:88f6dg7:Novela:Fernando de Rojas:365
				break;
			case 2:
				borrarLibro();
				break;
			case 3:
				actualizaLibro();
				break;

			case 4:
				limpiarCatalogo();
				break;

			case 5:
				menu2();
				opcion2 = leerOpcion();
				switch (opcion2) {
				case 1:
					listaLibros();
					break;
				case 2:
					listaLibrosGT();
					break;
				case 3:
					System.out.println("Introduzca el título");
					String titulo = leerCadena();
					imprimir(mostrarLibroPorTitulo(titulo));
				break;
				case 4:
					System.out.println("Introduzca la palabra clave del libro");
					String palabra = leerCadena();
					imprimir(mostrarLibroPorPalabra(palabra));
				break;
				case 5:
					System.out.println("Introduzca el numero de páginas del libro a mostrar");
					int numPaginas = leerOpcion();
					imprimir(mostrarLibroPorPagina(numPaginas));
				break;
				case 6:
					System.out.println("Introduzca el numero de paginas");
					int numPaginasX = leerOpcion();
					imprimir(mostrarLibroMenosDeXPaginas(numPaginasX));
				break;
				case 7:
					System.out.println("Introduzca el genero");
					String genero = leerCadena();
					imprimir(mostrarLibroPorGenero(genero));
				break;
				case 8:
					imprimir(mostrarLibroMasPaginas());
				break;
				case 9:
					imprimir(mostrarLibroMenosPaginas());
				break;
				case 10:
					System.out.println("Introduzca el genero");
					String genero2 = leerCadena();
					imprimir(mostrarLibroMasPaginasGenero(genero2));
				break;
				case 11:
					System.out.println("Introduzca el genero");
					String genero3 = leerCadena();
					imprimir(mostrarLibroMenosPaginasGenero(genero3));
				break;
				case 12:
					mostrarResumenLibros();
					break;
				default:
					System.out.println("Hasta la próxima");
					break;
				}
				break;

			default:
				System.out.println("Hasta la próxima");
				break;
			}
		} while (opcion != 0);

	}

	private static void menu() {
		System.out.println("Elija una opcion");
		System.out.println("0.Salir del programa");
		System.out.println("1.Insertar un libro");
		System.out.println("2.Borrar libro");
		System.out.println("3.Editar libro");
		System.out.println("4.Limpiar catálogo");
		System.out.println("5.Lista de Libros");
	}

	private static int leerOpcion() {
		int retorno = 0;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		try {
			retorno = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Formato no válido");
			e.printStackTrace();
		}
		return retorno;
	}

	private static String pedirDatosLibro() {
		String datos = "";
		System.out.println(
				"Introduzca los datos del libro de la siguiente forma:\n" + "Título:ISBN:Genero:Autor:Páginas");
		try {
			datos = leerCadena();
		} catch (InputMismatchException e) {
			System.out.println("Datos de entrada no validos");
		}
		return datos;
	}

	// se usa en pedirdatoslibro
	private static String leerCadena() {
		String opcion = null;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		try {
			opcion = sc.nextLine();
		} catch (InputMismatchException e) {
			System.out.println("La opcion no es correcta");
		}
		return opcion;
	}

	private static Libro procesaEntrada(String entrada) {
		Libro libro = null;

		String[] datos = entrada.split(":");
		try {
			String titulo = datos[0];
			String isbn = datos[1];
			Genero genero = Genero.getGenero(datos[2]);
			String autor = datos[3];
			Integer paginas = Integer.parseInt(datos[4]);

			libro = new Libro(titulo, isbn, genero, autor, paginas);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("El formato no es correcto");
		}
		return libro;
	}

	private static boolean insertarLibro() {
		boolean retorno = false;

		String datosLibro = pedirDatosLibro();

		Libro libro = procesaEntrada(datosLibro);

		servicios.insertarLibro(libro);

		return retorno;
	}

	private static boolean borrarLibro() {
		boolean retorno = false;
		System.out.println("Introduce el ISBN del libro a borrar");
		String isbn = leerCadena();
		Libro l = new Libro();
		l.setIsbn(isbn);

		if (servicios.borrarLibro(l)) {
			retorno = true;
			System.out.println("Libro eliminado con exito");
		} else {
			System.out.println("Error al eliminar libro");
		}
		return retorno;
	}
	
	private static boolean actualizaLibro() {
		System.out.println("Introduce el ISBN del libro que quieres actualizar");
		String isbn = leerCadena();
		System.out.println("Introduce los nuevos datos para el libro");
		String datos = pedirDatosLibro();
		Libro l = procesaEntrada(datos);
		boolean retorno = servicios.actualizaLibro(l, isbn);
		if (retorno) {
			System.out.println("El libro se ha actualizado");
		}
		else {
			System.out.println("Error al actualizar el libro");
		}
		return retorno;
		
	}

	private static void limpiarCatalogo() {
		boolean retorno = servicios.limpiarCatalogo();
		if (retorno) {
			System.out.println("El catálogo se ha limpiado con éxito");
		} else {
			System.out.println("Error al limpiar el catálogo");
		}
	}

	private static List<Libro> listaLibros() {
		List<Libro> libros = servicios.listaLibros();
		imprimir(libros);
		return libros;
	}

	private static void imprimir(List<Libro> libros) {
		String format = "%-15s %-20s %-15s %-20s %-4s %n";
		String linesep = "---------------------------------------------------------------------------------";
		System.out.printf(format, "ISBN", "TITULO", "GENERO", "AUTOR", "PAGINAS");
		System.out.println(linesep);
		for (Libro libro : libros) {
			System.out.printf(format, libro.getIsbn(), libro.getTitulo(), libro.getGenero().toString(),
					libro.getAutor(), libro.getPaginas());
			System.out.println(linesep);
		}
	}
	
	private static void menu2() {
		System.out.println("Menu de Informes, elija una opción");
		System.out.println("Mostrar libros ordenados: ");
		System.out.println("1.Por título");
		System.out.println("2.Por género y título");
		System.out.println("3.Mostrar libro introduciendo el título");
		System.out.println("4.Mostrar libro introduciendo una palabra que contiene el título");
		System.out.println("5.Mostrar libro introduciendo el número de páginas");
		System.out.println("6.Mostrar libro introduciendo al menos el número de páginas");
		System.out.println("7.Mostrar el libro de un genero a introducir");
		System.out.println("8.Mostrar libro con más páginas");
		System.out.println("9.Mostrar libro con menos páginas");
		System.out.println("10.Mostrar libro con más páginas de introduciendo el género");
		System.out.println("11.Mostrar libro con menos páginas de introduciendo el género");
		System.out.println("12.Resumen de la media de páginas por libro y el número de libros");
		
	}
	
	private static List<Libro> listaLibrosGT(){
		List<Libro> libros = servicios.listaLibrosGT();
		imprimir(libros);
		return libros;
	}
	
	private static List<Libro> mostrarLibroPorTitulo(String titulo){
		List<Libro> libros = servicios.obtenerLibroPorTitulo(titulo);
			return libros;
	}
	
	private static List<Libro> mostrarLibroPorPagina(int numPaginas){
		List<Libro> libros = servicios.obtenerLibroPorPagina(numPaginas);
		return libros;
	}
	
	private static List<Libro> mostrarLibroPorPalabra(String palabra){
		List<Libro> libros = servicios.obtenerLibroPorPalabra(palabra);
		return libros;
	}
	
	private static List<Libro> mostrarLibroMenosDeXPaginas(int numPaginas){
		List<Libro> libros = servicios.obtenerLibroMenosDeXPaginas(numPaginas);
		return libros;
	}
	
	private static List<Libro> mostrarLibroPorGenero(String genero){
		List<Libro> libros = servicios.obtenerLibroPorGenero(genero);
		return libros;
	}
	
	private static List<Libro> mostrarLibroMasPaginas(){
		List<Libro> libros = servicios.obtenerLibroMasPaginas();
		return libros;
	}
	
	private static List<Libro> mostrarLibroMenosPaginas(){
		List<Libro> libros = servicios.obtenerLibroMenosPaginas();
		return libros;
	}
	
	private static List<Libro> mostrarLibroMasPaginasGenero(String genero){
		List<Libro> libros = servicios.obtenerLibroPorGenero(genero);
		return libros;
	}
	
	private static List<Libro> mostrarLibroMenosPaginasGenero(String genero){
		List<Libro> libros = servicios.obtenerLibroPorGenero(genero);
		return libros;
	}
	
	private static void mostrarResumenLibros(){
		servicios.resumen();
	}
	
}