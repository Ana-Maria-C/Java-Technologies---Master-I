<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>File Upload</title>
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>
<h1> Upload a file
</h1>
<br/>
<form action="uploadServlet" method="post" enctype="multipart/form-data">
    <label for="file">Choose a file:</label>
    <input type="file" id="file" name="file" required>
    <br><br>
    <div class="g-recaptcha" data-sitekey="6LekPWAqAAAAAD-2dZPm4CwK9-XK0PiSp6DPEIvA"></div>
    <input type="submit" value="Upload">
</form>

</body>
</html>