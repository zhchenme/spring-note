
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h2>文件上传成功！点击即可下载上传的文件！</h2>
    <span>${description}</span>
    <a href="download?fileName=${fileName}"> ${fileName}</a>
</body>
</html>
