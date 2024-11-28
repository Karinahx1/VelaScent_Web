package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para manejar el registro de usuarios en la base de datos.
 */
@WebServlet(name = "ServletRegistro", urlPatterns = {"/servlet-registro"})
public class ServletRegistro extends HttpServlet {
    
    private static final String query = "INSERT INTO basedatosvelas (id, nombre, correo, celular, tipovela, mensaje) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Procesa las solicitudes HTTP POST.
     * 
     * @param request La solicitud HTTP
     * @param response La respuesta HTTP
     * @throws ServletException Si ocurre un error relacionado con el servlet
     * @throws IOException Si ocurre un error de entrada/salida
     */
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String celular = request.getParameter("celular");
        String tipovela = request.getParameter("tipovela");
        String mensaje = request.getParameter("mensaje");

        //System.out.println(id);
        System.out.println(celular);
        try (Connection conexion = Conexion.getConnection(); PreparedStatement stmt = conexion.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, nombre);
            stmt.setString(3, correo);
            stmt.setString(4, celular);
            stmt.setString(5, tipovela);
            stmt.setString(6, mensaje); // Insertamos el mensaje
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ServletRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.sendRedirect("index.html");
    }
}
