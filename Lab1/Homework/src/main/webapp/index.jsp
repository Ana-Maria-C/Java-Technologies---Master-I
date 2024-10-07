<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Graph Generator</title>
</head>
<body>
<h1>Generate Random Graph</h1>
<form action="generateGraph" method="get">
    <label for="numVertices">Number of Vertices:</label>
    <input type="number" id="numVertices" name="numVertices" required min="1">
    <br>
    <label for="numEdges">Number of Edges:</label>
    <input type="number" id="numEdges" name="numEdges" required min="0">
    <br>
    <input type="submit" value="Generate Graph">
</form>
</body>
</html>