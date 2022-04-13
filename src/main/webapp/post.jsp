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
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script>
        function validate() {
            /*document
                .querySelector('#form-candidate-name')
                .onsubmit = function (evt) {
                if ($('#candidate-name').val() === '') {
                    alert($('#candidate-name').attr('title'));
                    evt.preventDefault();
                }
                if ($('select option:selected').val() === '---Не выбран---') {
                    alert($('#city-selector').attr('title'));
                    evt.preventDefault();
                }
            }*/
        }
    </script>
    <script>
        $(document).ready(function () {
            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/cars/brand.do',
                dataType: 'json'
            }).done(function (brands) {
                var brand = document.querySelector('#brand-selector');
                for (let i = 0; i <= brands.length - 1; i++) {
                    let option = document.createElement('option');
                    option.value = brands[i].id;
                    option.text = brands[i].name;
                    brand.append(option);
                }
            }).fail(function (err) {
                console.log(err);
            });
            document.getElementById('brand-selector').addEventListener('change', function() {
                var model = document.querySelector('#model-selector');
                var firstChild = model.children[0];
                var body = document.querySelector('#body-type-selector');
                var bodyChild = body.children[0];
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/cars/model.do',
                    data: {
                        id: this.value
                    },
                    dataType: 'json',
                    async: false
                }).done(function (models) {
                    model.innerHTML = "";
                    model.append(firstChild);
                    body.innerHTML = "";
                    body.append(bodyChild);
                    for (let i = 0; i <= models.length - 1; i++) {
                        let option = document.createElement('option');
                        option.value = models[i].id;
                        option.text = models[i].name;
                        model.append(option);
                    }
                }).fail(function (err) {
                    console.log(err);
                });
            });
            document.getElementById('model-selector').addEventListener('change', function() {
                var body = document.querySelector('#body-type-selector');
                var bodyChild = body.children[0];
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/cars/body.do',
                    data: {
                        id: this.value
                    },
                    dataType: 'json',
                    async: false
                }).done(function (model) {
                    body.innerHTML = "";
                    body.append(bodyChild);
                    for (let i = 0; i <= model.bodyTypes.length - 1; i++) {
                        let option = document.createElement('option');
                        option.value = model.bodyTypes[i].id;
                        option.text = model.bodyTypes[i].name;
                        body.append(option);
                    }
                }).fail(function (err) {
                    console.log(err);
                });
            });
        });

    </script>
    <title>Авто</title>
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
                Новое обьявление
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/post.do"
                      id="form-candidate-name"
                      method="post">
                    <div class="form-group">
                        <label for="brand-selector">Марка</label>
                        <select class="form-control" id="brand-selector" name="brand" title="Выберите марку авто">
                            <option>---Не выбран---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="model-selector">Модель</label>
                        <select class="form-control" id="model-selector" name="model" title="Выберите модель марки">
                            <option>---Не выбран---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="body-type-selector">Кузов</label>
                        <select class="form-control" id="body-type-selector" name="body-type" title="Выберите кузов модели">
                            <option>---Не выбран---</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>Год</label>
                        <input type="text"
                               class="form-control"
                               name="year"
                               id="car-year"
                               title="Введите год сборки автомобиля">
                    </div>
                    <div class="form-group">
                        <label>Пробег</label>
                        <input type="text"
                               class="form-control"
                               name="mileage"
                               id="mileage"
                               placeholder="В километрах"
                               title="Введите пробег автомобиля">
                    </div>
                    <div class="form-group">
                        <label>Обьем двигателя</label>
                        <input type="text"
                               class="form-control"
                               name="volume"
                               id="volume"
                               placeholder="В см/куб"
                               title="Введите обьем двигателя">
                    </div>
                    <div class="form-group">
                        <label>Тип топлива</label>
                        <input type="text"
                               class="form-control"
                               name="fuel"
                               id="fuel-type"
                               placeholder="Введите один вариантов: Бензин, Дизель, Газ">
                    </div>
                    <div class="form-group">
                        <label>Модель двигателя</label>
                        <input type="text"
                               class="form-control"
                               name="engine"
                               id="engine-name"
                               value="Не известно">
                    </div>
                    <div class="form-group">
                        <label>VIN автомобиля</label>
                        <input type="text"
                               class="form-control"
                               name="serial-number"
                               id="serial-number">
                    </div>
                    <div class="form-group">
                        <label>Регистрационный номер автомобиля</label>
                        <input type="text"
                               class="form-control"
                               name="reg-number"
                               id="reg-number"
                               placeholder="Опционально">
                    </div>
                    <div class="form-group">
                        <label>Описание</label>
                        <input type="text"
                               class="form-control"
                               name="description"
                               id="description">
                    </div>
                    <div class="form-group">
                        <label>Цена</label>
                        <input type="text"
                               class="form-control"
                               name="price"
                               id="price">
                    </div>
                    <div class="form-group">
                        <label>Контакты</label>
                        <input type="text"
                               class="form-control"
                               name="phone"
                               id="phone">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="validate()">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>