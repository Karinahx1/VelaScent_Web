/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package basedatos;

import com.Conexion;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author karyj
 */
@WebServlet(name = "CrudServlet", urlPatterns = {"/crud"})
public class CrudServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Acción recibida del formulario
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String tipovela = request.getParameter("tipovela");
        String mensaje = request.getParameter("mensaje");
        try (Connection conexion = Conexion.getConnection()){
        //try (Connection conexion = Conexion.getConnection()) {
            switch (action) {
                case "read": // Buscar por ID
                    buscarRegistro(conexion, id, request, response);
                    break;
                case "update": // Actualizar datos excepto el ID
                    actualizarRegistro(conexion, id, nombre, correo, celular, tipovela, mensaje, response);
                    break;
                case "delete": // Eliminar registro por ID
                    eliminarRegistro(conexion, id, response);
                    break;
                default:
                    response.getWriter().println("Acción no válida.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al realizar la operación: " + e.getMessage());
        }
    }

    private void buscarRegistro(Connection conexion, String id, HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String query = "SELECT * FROM basedatosvelas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Rellenar el formulario con los datos encontrados
                response.setContentType("application/json");
                response.getWriter().write("{");
                response.getWriter().write("\"nombre\": \"" + rs.getString("nombre") + "\",");
                response.getWriter().write("\"correo\": \"" + rs.getString("correo") + "\",");
                response.getWriter().write("\"celular\": \"" + rs.getString("celular") + "\",");
                response.getWriter().write("\"tipovela\": \"" + rs.getString("tipovela") + "\",");
                response.getWriter().write("\"mensaje\": \"" + rs.getString("mensaje") + "\"");
                response.getWriter().write("}");
            } else {
                response.getWriter().println("Registro no encontrado.");
            }
        }
    }


    private void actualizarRegistro(Connection conexion, String id, String nombre, String correo, String celular, String tipovela, String mensaje, HttpServletResponse response) throws SQLException, IOException {
        String query = "UPDATE basedatosvelas SET nombre = ?, correo = ?, celular = ?, tipovela = ?, mensaje = ? WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, celular);
            stmt.setString(4, tipovela);
            stmt.setString(5, mensaje);
            stmt.setString(6, id);  // El ID no se puede modificar
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                response.getWriter().println("Registro actualizado exitosamente.");
            } else {
                response.getWriter().println("No se encontró el registro con el ID especificado.");
            }
        }
    }


    private void eliminarRegistro(Connection conexion, String id, HttpServletResponse response) throws SQLException, IOException {
        String query = "DELETE FROM basedatosvelas WHERE id = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, id);  // Eliminar el registro por ID
            int filas = stmt.executeUpdate();
            if (filas > 0) {
                response.getWriter().println("Registro eliminado exitosamente.");
            } else {
                response.getWriter().println("No se encontró el registro con el ID especificado.");
            }
        }
    }

}

