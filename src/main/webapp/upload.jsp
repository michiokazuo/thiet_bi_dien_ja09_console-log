<%--
  Created by IntelliJ IDEA.
  User: Phong
  Date: 8/7/2020
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload File - ITStudent</title>
</head>
<body>
<h1>Demo Servlet Upload File</h1>

<form action="upload-file" method="post" enctype="multipart/form-data">
    <label>Select file to upload:</label>
    <input type="file" name="file" multiple="multiple" > <br>
    <input type="submit" value="Upload">
</form>

</body>
</html>
