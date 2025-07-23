<html>

<body>
    <h2>Add Book</h2>
    <form action="BookServlet" method="post">
        <input type="hidden" name="action" value="add" />
        Title: <input type="text" name="title" /><br />
        Author: <input type="text" name="author" /><br />
        Year: <input type="text" name="year" /><br />
        <input type="submit" value="Add" />
    </form>
    <a href="BookServlet">Back to List</a>
</body>

</html>