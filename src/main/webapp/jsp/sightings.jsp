<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Sightings</h1>
            <hr/>
            <c:if test="${pageContext.request.userPrincipal == null}">
                <button type="submit"
                        id="login-button"
                        class="btn btn-default"
                        style="margin-right: 150px;">
                    <a href="${pageContext.request.contextPath}/login"> 
                        Login</a>
                </button>
            </c:if>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}">
                            Home
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/superpersons">
                            Super Heroes and Villains
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/locations">
                            Locations
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/organizations">
                            Organizations
                        </a>
                    </li>
                    <li role="presentation" class="active">
                        <a href="${pageContext.request.contextPath}/sightings">
                            Sightings
                        </a>
                    </li>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <li role="presentation">
                            <a href="${pageContext.request.contextPath}/displayUserList">
                                User Admin
                            </a>
                        </li>                        
                    </sec:authorize>

                </ul>    
            </div>
            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <h4>Hello : ${pageContext.request.userPrincipal.name}
                    | <a href="<c:url value="/j_spring_security_logout" />" > Logout</a>
                </h4>
            </c:if>
            <div class="row">
                <div class="col-md-6">
                    <h2>Whose that? </h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="40%">Superhero</th>
                            <th width="30%">When</th>
                            <th width="10%"></th>
                            <th width="10%"></th>
                        </tr>

                        <c:forEach var="currentSighting" items="${sList}">
                            <tr>
                                <td>
                                    <a href="displaySightingDetails?sightingId=${currentSighting.sightingId}">
                                        <c:out value="${currentSighting.sp.name}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentSighting.dateSeen}"/>
                                </td>
                                <td>
                                    <sec:authorize access="isAuthenticated()">
                                        <a href="editSightingForm?sightingId=${currentSighting.sightingId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteSighting?sightingId=${currentSighting.sightingId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>

                    </table> 
                </div>
                <div class="col-md-6">
                    <sec:authorize access="isAuthenticated()">
                        <h2>Add New</h2>
                        <form class="form-horizontal"
                              id="sightingForm"
                              role="form" method="POST"
                              action="addNewSighting"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="add-superhero" class="col-md-4 control-label">
                                    Superhero:
                                </label>
                                <div class="col-md-8">
                                    <select id="supehero-categories" class="form-control" 
                                            name="superpersonId" 
                                            required>
                                        <option value="" selected hidden>Superhero</option>
                                        <c:forEach var="currentSp" items="${spList}">

                                            <option value="${currentSp.superpersonId}" name="superpersonId">
                                                <c:out value="${currentSp.name}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group date">
                                <h3 style="color:red">${errorMessage}</h3>
                                <label for="add-date" class="col-md-4 control-label">
                                    When:
                                </label>
                                <div class="col-md-8 input-group input-append date">
                                    <input type="text" class="form-control" name="dateSeen" placeholder="Please enter yyyy-mm-dd"
                                           required pattern="[0-9]{4}[-][0-9]{2}[-][0-9]{2}"/>
                                    <span class="input-group-addon add-on">
                                        <span class="glyphicon glyphicon-calendar">
                                        </span>
                                    </span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-latitude" class="col-md-4 control-label">
                                    Location:
                                </label>
                                <div class="col-md-8">
                                    <select  id="location-categories" class="form-control" 
                                             name="locationId" required>
                                        <option value="" selected hidden>Location</option>
                                        <c:forEach var="currentLocation" items="${lList}">                                    
                                            <option value="${currentLocation.locationId}" name="locationId">
                                                <c:out value="${currentLocation.nameOfResidence}"/>
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-description" class="col-md-4 control-label">
                                    Description:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="description" id="add-description" placeholder="Description"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="displayTitle" class="col-md-4 control-label">
                                    Enter Title:
                                </label>
                                <div class="col-sm-4">
                                    <input type="text" 
                                           id="displayTitle" 
                                           name="displayTitle"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <h3 style="color:red">${errorMsg}</h3>
                                <label for="fileName" class="col-md-4 control-label">
                                    Upload Picture:
                                </label>
                                <div class="col-md-8">
                                    <input type="file" 
                                           id="picture" 
                                           name="fileName"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" value="Add" id="submit-button"/>
                                </div>
                            </div>
                        </form>
                    </sec:authorize>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>

