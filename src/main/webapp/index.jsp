<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>Авто обьявления</title>
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
                    <c:if test="${user == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Вoйти</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/reg.do">Регистрация</a>
                        </li>
                    </c:if>
                    <c:if test="${user != null}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="<%=request.getContextPath()%>/user.do"><c:out value="${user.name}"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">Выйти</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        О Б Ь Я В Л Е Н И Я
                    </div>
                    <div class="card-body">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col" style="width: 200px">Автомобиль</th>
                                    <th scope="col" style="width: 300px">Характеристики</th>
                                    <th scope="col" style="width: 300px">Описание</th>
                                    <th scope="col">Цена</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${posts}" var="post">
                                <tr>
                                    <td>
                                        <c:out value="${post.car.brand.name} ${post.car.model.name}"/>
                                    </td>
                                    <td>
                                        <c:out value="Категория - ${post.car.category.name}"/>
                                        <br>
                                        <c:out value="Кузов - ${post.car.bodyType.name}"/>
                                        <br>
                                        <c:out value="Мотор - ${post.car.engine.name}"/>
                                        <br>
                                        <c:out value="Пробег - ${post.car.engine.mileage} км"/>
                                        <br>
                                        <c:out value="Обьем двигателя - ${post.car.engine.volume} см/куб"/>
                                        <br>
                                        <c:out value="Тип топлива - ${post.car.engine.fuelType}"/>
                                        <br>
                                        <c:out value="Год - ${post.car.assemblyYear}"/>
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
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <c:forEach items="${post.image}" var="image">
                                            <img src="<c:url value='/download.do?id=${image}'/>" width="200px"/>
                                        </c:forEach>
                                        <br>
                                        <br>
                                        <br>
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