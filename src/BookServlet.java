import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.util.*;

public class BookServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            String idParam = req.getParameter("id");
            if (idParam != null && !idParam.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idParam);
                    try (Connection con = DBUtil.getConnection();
                         PreparedStatement ps = con.prepareStatement("SELECT * FROM books WHERE id=?")) {
                        ps.setInt(1, id);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                req.setAttribute("id", rs.getInt("id"));
                                req.setAttribute("title", rs.getString("title"));
                                req.setAttribute("author", rs.getString("author"));
                                req.setAttribute("year", rs.getInt("year"));
                            }
                        }
                    } catch (Exception e) { 
                        e.printStackTrace(); 
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            req.getRequestDispatcher("editBook.jsp").forward(req, res);
        } else {
            List<Map<String, Object>> books = new ArrayList<>();
            try (Connection con = DBUtil.getConnection();
                 PreparedStatement ps = con.prepareStatement("SELECT * FROM books");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> book = new HashMap<>();
                    book.put("id", rs.getInt("id"));
                    book.put("title", rs.getString("title"));
                    book.put("author", rs.getString("author"));
                    book.put("year", rs.getInt("year"));
                    books.add(book);
                }
            } catch (Exception e) { 
                e.printStackTrace(); 
            }
            req.setAttribute("books", books);
            req.getRequestDispatcher("bookList.jsp").forward(req, res);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");
        try (Connection con = DBUtil.getConnection()) {
            if ("add".equals(action)) {
                String title = req.getParameter("title");
                String author = req.getParameter("author");
                String yearParam = req.getParameter("year");
                
                if (title != null && author != null && yearParam != null && 
                    !title.trim().isEmpty() && !author.trim().isEmpty() && !yearParam.trim().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "INSERT INTO books (title, author, year) VALUES (?, ?, ?)")) {
                        ps.setString(1, title.trim());
                        ps.setString(2, author.trim());
                        ps.setInt(3, Integer.parseInt(yearParam));
                        ps.executeUpdate();
                    }
                }
            } else if ("update".equals(action)) {
                String idParam = req.getParameter("id");
                String title = req.getParameter("title");
                String author = req.getParameter("author");
                String yearParam = req.getParameter("year");
                
                if (idParam != null && title != null && author != null && yearParam != null &&
                    !idParam.trim().isEmpty() && !title.trim().isEmpty() && !author.trim().isEmpty() && !yearParam.trim().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE books SET title=?, author=?, year=? WHERE id=?")) {
                        ps.setString(1, title.trim());
                        ps.setString(2, author.trim());
                        ps.setInt(3, Integer.parseInt(yearParam));
                        ps.setInt(4, Integer.parseInt(idParam));
                        ps.executeUpdate();
                    }
                }
            } else if ("delete".equals(action)) {
                String idParam = req.getParameter("id");
                if (idParam != null && !idParam.trim().isEmpty()) {
                    try (PreparedStatement ps = con.prepareStatement("DELETE FROM books WHERE id=?")) {
                        ps.setInt(1, Integer.parseInt(idParam));
                        ps.executeUpdate();
                    }
                }
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        res.sendRedirect("BookServlet");
    }
}