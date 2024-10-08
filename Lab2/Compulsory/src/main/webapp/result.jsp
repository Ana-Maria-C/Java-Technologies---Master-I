<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8"%>


<!DOCTYPE html>
<html>
<head>
    <title>File Upload Result</title>
</head>
<body>
<h1>Uploaded File Lines</h1>
<%
  // extract lines from the session

  Object lines = session.getAttribute("fileLines");
  List<String> fileLines= null;
  if (lines instanceof List<?>)
  {
    fileLines = (List<String>) lines;

    // shuffle lines
    Collections.shuffle(fileLines);
  }


  // check if the lines are not null
  if (fileLines != null) {
%>
<ul>
  <%
    for (String line : fileLines) {
  %>
  <li><%= line %></li>
  <%
    }
  %>
</ul>
<%
} else {
%>
<p>No lines were found.Please upload a file.</p>
<%
  }
%>
<a href="input.jsp">Upload another file</a>
</body>
</html>
