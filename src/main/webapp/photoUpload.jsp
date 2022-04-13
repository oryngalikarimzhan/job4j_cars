<%@ page language="java" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>PhotoUpload</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
        <script>
           function validate() {
               document.
                   querySelector('#form-photo')
                   .onsubmit = function (evt) {
                   if ($('#photo').val() === '') {
                       alert($('#photo').attr('title'));
                       evt.preventDefault();
                   }
               }
           }
        </script>
    </head>
    <body>
        <%
            String id = request.getParameter("id");
            String head = request.getHeader("referer");
        %>
        <div class="container">
            <h2>Upload photo</h2>

            <form action="<%=request.getContextPath()%>/photoUpload.do?id=<%=id%>&ref=<%=head%>"
                  id="form-photo"
                  method="post"
                  enctype="multipart/form-data">
                <div class="checkbox">
                    <input type="file"
                           name="file"
                           id="photo"
                           title="Выберите сперва фото для загрузки">
                </div>
                <button type="submit" class="btn btn-default" onclick="validate()">Submit</button>
            </form>
        </div>
    </body>
</html>