<%@ page import="java.util.*" %>
    <% List<Map<String, Object>> books = (List<Map<String, Object>>)request.getAttribute("books");
            %>
            <html>

            <body>
                <h2>Book List</h2>
                <table border="1">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Author</th>
                        <th>Year</th>
                        <th>Action</th>
                    </tr>
                    <% if (books !=null) { for (Map<String, Object> book : books) { %>
                        <tr>
                            <td>
                                <%= book.get("id") %>
                            </td>
                            <td>
                                <%= book.get("title") %>
                            </td>
                            <td>
                                <%= book.get("author") %>
                            </td>
                            <td>
                                <%= book.get("year") %>
                            </td>
                            <td>
                                <a href="BookServlet?action=edit&id=<%= book.get("id") %>">Edit</a>
                                <form action="BookServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete" />
                                    <input type="hidden" name="id" value="<%= book.get("id") %>"/>
                                    <input type="submit" value="Delete" />
                                </form>
                            </td>
                        </tr>
                        <% } } %>
                </table>
                <a href="addBook.jsp">Add Book</a>
            </body>

            </html>