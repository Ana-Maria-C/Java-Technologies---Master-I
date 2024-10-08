<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
</head>
<body>
<h1> Upload a file
</h1>
<br/>
<form action="uploadServlet" method="post" enctype="multipart/form-data">
    <label for="file">Choose a file:</label>
    <input type="file" id="file" name="file" required>
    <br><br>
    <input type="submit" value="Upload">
</form>

</body>
</html>