package persistencia.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import negocio.vo.Genero;
import negocio.vo.Libro;
import persistencia.dao.LibroDAO;

public class LibroDaoIMPL implements LibroDAO{
	
	private Connection getConexion() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:6033/biblioteca", "developer", "programaciondaw");
	}

	@Override
	public List<Libro> obtenerDatosLibro() {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();
			
			String sql = "SELECT * FROM libro ORDER BY titulo;";
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet rs = sentencia.executeQuery(sql);
			
			lista = construyeLista(rs);
			
			rs.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}
	
	private List<Libro> construyeLista(ResultSet rs) throws SQLException{
		List<Libro> lista = new ArrayList<>();
		
		while(rs.next()) {
			Libro l = new Libro();
			l.setIsbn(rs.getString("isbn"));
			l.setAutor(rs.getString("autor"));
			l.setGenero(Genero.getGenero(rs.getString("genero")));
			l.setTitulo(rs.getString("titulo"));
			l.setPaginas(rs.getInt("paginas"));
			
			lista.add(l);
		}
		return lista;
	}

	@Override
	public boolean insertarLibro(Libro l){
		boolean retorno = false;
		try {
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:6033/biblioteca","developer","programaciondaw");
			
			String sql = "INSERT INTO libro VALUES (?,?,?,?,?);";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, l.getTitulo());
			sentencia.setString(2, l.getIsbn());
			sentencia.setString(3, l.getGenero().toString());
			sentencia.setString(4, l.getAutor());
			sentencia.setInt(5, l.getPaginas());
			
			sentencia.execute();
			if(sentencia.getUpdateCount()>0) {
				retorno = true;
				sentencia.close();
				conexion.close();
			}
		} catch (SQLException e) {
			System.out.println("Error con la Base de Datos");
			e.printStackTrace();
		}
		return retorno;
	}
	
	public boolean borrarLibro(Libro l){
		boolean retorno = false;
		try {
			Connection conexion = getConexion();
			
			String sql = "DELETE FROM libro WHERE isbn=?;";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, l.getIsbn());
			
			if(sentencia.executeUpdate()>0) {
				retorno = true;
				sentencia.close();
				conexion.close();
			}
			
		} catch (SQLException e) {
			System.out.println("Error con la Base de Datos");
			e.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public boolean truncateLibro() {
		boolean clear = false;

		try {
			Connection conexion = getConexion();

			String sql = "TRUNCATE TABLE libro";

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			sentencia.execute(sql);
			clear = true;
			sentencia.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}

		return clear;

	}
	
	public boolean actualizaLibro(Libro l, String isbn) {
		boolean retorno = false;
		try {
			Connection conexion = getConexion();
			
			String sql = "UPDATE libro SET titulo=?, isbn=?, genero=?, autor=?, paginas=? WHERE isbn = ?;";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, l.getTitulo());
			sentencia.setString(2, l.getIsbn());
			sentencia.setString(3, (l.getGenero()).toString());
			sentencia.setString(4, l.getAutor());
			sentencia.setInt(5, l.getPaginas());
			sentencia.setString(6, isbn);
			
			if(sentencia.executeUpdate() == 1) {
				retorno = true;
			}
			sentencia.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error con la Base de Datos");
			e.printStackTrace();
		}
		return retorno;
	}
	
	@Override
	public List<Libro> obtenerDatosLibroGT() {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();
			
			String sql = "SELECT * FROM libro ORDER BY genero,titulo;";
			
			Statement sentencia = conexion.createStatement();
			
			ResultSet rs = sentencia.executeQuery(sql);
			
			while(rs.next()) {
				lista = construyeLista(rs);
			}

			rs.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroPorTitulo(String titulo) {
		List<Libro> lista = new ArrayList<>();
		Libro l = null;
		try {
			Connection conexion = getConexion();
			
			String sql = "SELECT * FROM libro WHERE titulo=?;";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setString(1, titulo);
			
			ResultSet rs = sentencia.executeQuery();
			if(rs.next()) {
				l = new Libro(rs.getString(1),rs.getString(2),Genero.getGenero(rs.getString(3)),rs.getString(4),rs.getInt(5));
				lista.add(l);
			}
			rs.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public List<Libro> obtenerLibroPorPagina(int numPaginas) {
		List<Libro> lista = new ArrayList<>();
		Libro l = null;
		try {
			Connection conexion = getConexion();
			
			String sql = "SELECT * FROM libro WHERE paginas=?;";
			
			PreparedStatement sentencia = conexion.prepareStatement(sql);
			
			sentencia.setInt(1, numPaginas);
			
			ResultSet rs = sentencia.executeQuery();
			if(rs.next()) {
				l = new Libro(rs.getString(1),rs.getString(2),Genero.getGenero(rs.getString(3)),rs.getString(4),rs.getInt(5));
				lista.add(l);
			}
			rs.close();
			conexion.close();
			
		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}
	
	@Override
	public List<Libro> obtenerLibroPorPalabra(String titulo) {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();

			String sql = "SELECT * FROM libro WHERE titulo like ?;";

			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, "%" + titulo + "%");

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroMenosDeXPaginas(int numPaginas) {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();

			String sql = "SELECT * FROM libro WHERE paginas<?;";

			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setInt(1, numPaginas);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroPorGenero(String genero) {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();

			String sql = "SELECT * FROM libro WHERE genero=?;";

			PreparedStatement sentencia = conexion.prepareStatement(sql);
			sentencia.setString(1, genero);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroMasPaginas() {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();
			
			//CREATE VIEW maxPaginas AS SELECT MAX(paginas) FROM libro;
			String sql = "SELECT * FROM libro WHERE paginas=(select * from maxPaginas);";

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroMenosPaginas() {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();

			//CREATE VIEW minPaginas AS SELECT MIN(paginas) FROM libro;
			String sql = "SELECT * FROM libro WHERE paginas=(select * from minPaginas);";

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroMasPaginasGenero(String genero) {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();
			
			//CREATE VIEW maxPaginas AS SELECT MAX(paginas) FROM libro;
			String sql = "SELECT * FROM libro WHERE paginas=(select * from maxPaginas) AND genero=?;";

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}

	@Override
	public List<Libro> obtenerLibroMenosPaginasGenero(String genero) {
		List<Libro> lista = null;
		try {
			Connection conexion = getConexion();
			
			//CREATE VIEW maxPaginas AS SELECT MAX(paginas) FROM libro;
			String sql = "SELECT * FROM libro WHERE paginas=(select * from minPaginas) AND genero=?;";

			PreparedStatement sentencia = conexion.prepareStatement(sql);

			ResultSet rs = sentencia.executeQuery();

			lista = construyeLista(rs);
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
		return lista;
	}
	
	public void resumen() {
		try {
			Connection conexion = getConexion();

			String sql = "SELECT DISTINCT genero FROM libro;";

			Statement sentencia = conexion.createStatement();

			ResultSet rs = sentencia.executeQuery(sql);

			while (rs.next()) {
				System.out.println(rs.getString("genero"));
				System.out.println("------------------------------");
				String sql2 = "SELECT AVG(paginas), COUNT(paginas) FROM libro WHERE genero=? ";
				PreparedStatement sentencia2 = conexion.prepareStatement(sql2);
				sentencia2.setString(1, rs.getString("genero"));

				ResultSet rs2 = sentencia2.executeQuery();

				while (rs2.next()) {
					System.out.println("Media de páginas: " + rs2.getFloat(1));
					System.out.println("Numero de libros: " + rs2.getInt(2));
					System.out.println("------------------------------");
				}
			}
			rs.close();
			conexion.close();

		} catch (SQLException e) {
			System.out.println("Error en la conexion con la BD");
			e.printStackTrace();
		}
	
	}
	
}
