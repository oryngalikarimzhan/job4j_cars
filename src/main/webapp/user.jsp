<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet"
              href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
              integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
              crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
                integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
                crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
                integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
                crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
                integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
                crossorigin="anonymous"></script>
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <title>Профиль</title>
    </head>
    <body>
        <div class="container pt-3">
            <div class="row">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/index.do">Главная страница</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/post.do">Добавить обьявление</a>
                    </li>
                    <c:if test="${user != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        Профиль пользователя
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Фото</th>
                                    <th scope="col">Информация</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>
                                        <img src="<c:url value='/download.do?id=${user.email}'/>"
                                             width="200px" height="200px"/>
                                        <br>
                                        <a href='<c:url value="/photoDelete.do?id=${user.email}"/>'>
                                            <i class="fa fa-trash"></i>
                                        </a>
                                        <a href='<c:url value="/photoUpload.jsp?id=${user.email}"/>'>
                                            <i class="fa fa-plus"></i>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="Имя пользователя - ${user.name}"/>
                                        <br>
                                        <c:out value="Email пользователя - ${user.email}"/>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        Мои обьявления
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th scope="col" style="width: 350px">Фото</th>
                                <th scope="col" style="width: 180px">Характеристики</th>
                                <th scope="col" style="width: 300px">Описание</th>
                                <th scope="col">Цена</th>
                                <th scope="col">Статус</th>
                                <th scope="col">Действия</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td>
                                        <img src="<c:url value='/download.do?id=${post.id}'/>" width="100%"/>
                                        <br>
                                        <a href='<c:url value="/photoDelete.do?id=${post.id}"/>'>
                                            <i class="fa fa-trash"></i>
                                        </a>
                                        <a href='<c:url value="/photoUpload.jsp?id=${post.id}"/>'>
                                            <i class="fa fa-plus"></i>
                                        </a>
                                    </td>
                                    <td>
                                        <c:out value="Марка - ${post.car.brand.name}"/>
                                        <br>
                                        <c:out value="Модель - ${post.car.model.name}"/>
                                        <br>
                                        <c:out value="Кузов - ${post.car.bodyType.name}"/>
                                        <br>
                                        <c:out value="Мотор - ${post.car.engine.name}"/>
                                        <br>
                                        <c:out value="Пробег - ${post.car.engine.mileage}км"/>
                                        <br>
                                        <c:out value="Обьем двигателя - ${post.car.engine.volume}см/куб"/>
                                        <br>
                                        <c:out value="Тип топлива - ${post.car.engine.fuelType}"/>
                                        <br>
                                        <c:out value="Год - ${post.car.assemblyYear}г"/>
                                    </td>
                                    <td>
                                        <c:out value="${post.description}"/>
                                        <br>
                                        <c:out value="Продавец - ${post.user.email}"/>
                                        <br>
                                        <c:out value="Контакты - ${post.phone}"/>
                                    </td>
                                    <td>
                                        <c:out value="${post.price}"/>
                                    </td>
                                    <td>
                                        <c:if test="${post.sold == false}">
                                            Активно
                                        </c:if>
                                        <c:if test="${post.sold == true}">
                                            В архиве
                                        </c:if>
                                    </td>
                                    <td>
                                        <a href='<c:url value="/delete.do?id=${post.id}"/>'>
                                            Удалить
                                        </a>
                                        <br>

                                        <c:if test="${post.sold == false}">
                                            <a href='<c:url value="/status.do?id=${post.id}&sold=${post.sold}"/>'>
                                                Деактивировать
                                            </a>
                                        </c:if>
                                        <c:if test="${post.sold == true}">
                                            <a href='<c:url value="/status.do?id=${post.id}&sold=${post.sold}"/>'>
                                                Активировать
                                            </a>
                                        </c:if>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>