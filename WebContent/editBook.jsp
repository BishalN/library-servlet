<html>

<body>
    <h2>Edit Book</h2>
    <form action="BookServlet" method="post">
        <input type="hidden" name="action" value="update" />
        <input type="hidden" name="id" value="<%= request.getAttribute("id") %>"/>
        Title: <input type="text" name="title" value="<%= request.getAttribute("title") %>"/><br />
        Author: <input type="text" name="author" value="<%= request.getAttribute("author") %>"/><br />
        Year: <input type="text" name="year" value="<%= request.getAttribute("year") %>"/><br />
        <input type="submit" value="Update" />
    </form>
    <a href="BookServlet">Back to List</a>
</body>

</html>