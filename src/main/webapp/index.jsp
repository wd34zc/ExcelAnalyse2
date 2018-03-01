<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<h2>Hello World!</h2>
<form action="score/createTable" method="post" enctype="multipart/form-data">
    成绩Excel:<input type="file" name="scoreExcel"><br>
    样板Excel:<input type="file" name="templetExcel"><br>
    <input type="submit">
</form>
<a href="score/getScoreTemplet">成绩 模板</a><br>
<a href="score/getTempletTemplet">生成样板 模板</a><br>
</body>
</html>
