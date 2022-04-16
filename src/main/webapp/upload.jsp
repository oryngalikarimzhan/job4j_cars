<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Upload</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#form2Submit').on('submit', uploadFile);
        });

        function uploadFile(event) {
            event.stopPropagation();
            event.preventDefault();
            var form = document.getElementById('form2Submit');
            var data = new FormData(form);
            postFilesData(data);
        }

        function postFilesData(data) {
            $.ajax({
                url :  'http://localhost:8080/hibernate/upload2',
                type : 'POST',
                data : data,
                cache : false,
                processData : false,
                contentType : false,
                success : function(data, textStatus, jqXHR) {
                    alert(data);
                    document.getElementById('file').value = "";
                },
                error : function(jqXHR, textStatus, errorThrown) {
                    alert('ERRORS: ' + textStatus);
                }
            });
        }
    </script>
</head>
<body>
    <div class="container">
        <table class="table">
            <thead>
            <tr>
                <th>URL</th>
                <th>View</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${images}" var="image" varStatus="status">
                <tr valign="top">
                    <td><a href="<c:url value='/download?name=${image}'/>">Download</a></td>
                    <td>
                        <img src="<c:url value='/download?name=${image}'/>" width="100px" height="100px"/>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <h2>Upload image</h2>
        <form id ="form2Submit"<%-- enctype="multipart/form-data"--%>>
            <input type="file" id="file" name="file" multiple accept="image/*"/>
            Last name:<br>
            <input type="text" name="lastname" value="Mouse">
            <input type="submit" value="Upload" />
        </form>
    </div>
</body>
</html>