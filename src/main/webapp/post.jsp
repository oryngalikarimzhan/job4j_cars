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
    <script type="text/javascript">
        $(document).ready(function () {
            var category = document.querySelector('#category-selector');
            var brand = document.querySelector('#brand-selector');
            var noBrandChild = brand.children[0];
            var model = document.querySelector('#model-selector');
            var noModelChild = model.children[0];
            var bodyType = document.querySelector('#body-type-selector');
            var noBodyTypeChild = bodyType.children[0];

            $.ajax({
                type: 'GET',
                url: 'http://localhost:8080/cars/category.do',
                dataType: 'json'
            }).done(function (categories) {
                for (let i = 0; i <= categories.length - 1; i++) {
                    let option = document.createElement('option');
                    option.value = categories[i].id;
                    option.text = categories[i].name;
                    category.append(option);
                }
            }).fail(function (err) {
                console.log(err);
            });

            document.getElementById('category-selector').addEventListener('change', function() {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/cars/brand.do',
                    data: {
                        id: this.value
                    },
                    dataType: 'json'
                }).done(function (brands) {
                    brand.innerHTML = "";
                    brand.append(noBrandChild);
                    model.innerHTML = "";
                    model.append(noModelChild);
                    bodyType.innerHTML = "";
                    bodyType.append(noBodyTypeChild);

                    for (let i = 0; i <= brands.length - 1; i++) {
                        let option = document.createElement('option');
                        option.value = brands[i].id;
                        option.text = brands[i].name;
                        brand.append(option);
                    }
                }).fail(function (err) {
                    console.log(err);
                });
            });

            document.getElementById('brand-selector').addEventListener('change', function() {
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/cars/model.do',
                    data: {
                        brandId: this.value,
                        categoryId: category.value
                    },
                    dataType: 'json',
                    async: false
                }).done(function (models) {
                    model.innerHTML = "";
                    model.append(noModelChild);
                    bodyType.innerHTML = "";
                    bodyType.append(noBodyTypeChild);

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
                $.ajax({
                    type: 'POST',
                    url: 'http://localhost:8080/cars/body.do',
                    data: {
                        id: this.value
                    },
                    dataType: 'json',
                    async: false
                }).done(function (model) {
                    bodyType.innerHTML = "";
                    bodyType.append(noBodyTypeChild);

                    for (let i = 0; i <= model.bodyTypes.length - 1; i++) {
                        let option = document.createElement('option');
                        option.value = model.bodyTypes[i].id;
                        option.text = model.bodyTypes[i].name;
                        bodyType.append(option);
                    }
                }).fail(function (err) {
                    console.log(err);
                });
            });
        });
    </script>
    <title>????????</title>
</head>
    <body>
        <div class="container pt-3">
            <div class="row">
                <ul class="nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/index.do">?????????????? ????????????????</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=request.getContextPath()%>/post.do">???????????????? ????????????????????</a>
                    </li>
                    <c:if test="${user != null}">
                        <li class="nav-item">
                            <a class="nav-link"
                               href="<%=request.getContextPath()%>/user.do"><c:out value="${user.name}"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<%=request.getContextPath()%>/logout.do">??????????</a>
                        </li>
                    </c:if>
                </ul>
            </div>
            <div class="row">
                <div class="card" style="width: 100%">
                    <div class="card-header">
                        ?? ?? ?? ?? ?? &nbsp; &nbsp; ?? ?? ?? ?? ?? ?? ?? ?? ?? ??
                    </div>
                    <div class="card-body">
                        <form id="form-post" action="<%=request.getContextPath()%>/post.do"
                              method="post" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="category-selector">?????????????????? ????????????????????</label>
                                <select class="form-control"
                                        id="category-selector"
                                        name="category"
                                        title="???????????????? ?????????????????? ????????????????????">
                                    <option>---???? ????????????---</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="brand-selector">??????????</label>
                                <select class="form-control"
                                        id="brand-selector"
                                        name="brand"
                                        title="???????????????? ?????????? ????????">
                                    <option>---???? ????????????---</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="model-selector">????????????</label>
                                <select class="form-control"
                                        id="model-selector"
                                        name="model"
                                        title="???????????????? ???????????? ??????????">
                                    <option>---???? ????????????---</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="body-type-selector">??????????</label>
                                <select class="form-control" id="body-type-selector" name="body-type">
                                    <option>---???? ????????????---</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>??????</label>
                                <input type="text"
                                       class="form-control"
                                       name="year"
                                       id="car-year"
                                       placeholder="YYYY">
                            </div>
                            <div class="form-group">
                                <label>????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="mileage"
                                       id="mileage"
                                       placeholder="?? ????????????????????">
                            </div>
                            <div class="form-group">
                                <label>?????????? ??????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="volume"
                                       id="volume"
                                       placeholder="?? ????/??????">
                            </div>
                            <div class="form-group">
                                <label for="fuel-type-selector">?????? ??????????????</label>
                                <select class="form-control" id="fuel-type-selector" name="fuel-type">
                                    <option>---???? ????????????---</option>
                                    <option>????????????</option>
                                    <option>????????????</option>
                                    <option>??????</option>
                                    <option>????????????????????????</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label>???????????? ??????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="engine"
                                       id="engine-name"
                                       placeholder="??????????????????????">
                            </div>
                            <div class="form-group">
                                <label>VIN ????????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="serial-number"
                                       id="serial-number">
                            </div>
                            <div class="form-group">
                                <label>?????????????????????????????? ?????????? ????????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="reg-number"
                                       id="reg-number"
                                       placeholder="??????????????????????">
                            </div>
                            <div class="form-group">
                                <label>????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="description"
                                       id="description">
                            </div>
                            <div class="form-group">
                                <label>????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="price"
                                       id="price"
                                       placeholder="?? ??????????">
                            </div>
                            <div class="form-group">
                                <label>????????????????</label>
                                <input type="text"
                                       class="form-control"
                                       name="phone"
                                       id="phone"
                                       placeholder="8-XXX-XXXXXXX">
                            </div>
                            <div class="form-group">
                                <label>?????????????????? ????????</label>
                                <br>
                                <input type="file" id="file" name="file" multiple accept="image/*"/>
                            </div>
                            <button type="submit" class="btn btn-primary">??????????????????</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>