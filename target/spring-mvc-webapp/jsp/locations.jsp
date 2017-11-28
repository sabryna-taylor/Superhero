<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Locations</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1>Locations</h1>
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
                    <li role="presentation" class="active">
                        <a href="${pageContext.request.contextPath}/locations">
                            Locations
                        </a>
                    </li>
                    <li role="presentation">
                        <a href="${pageContext.request.contextPath}/organizations">
                            Organizations
                        </a>
                    </li>
                    <li role="presentation">
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
                    <h2>Locations </h2>
                    <table id="contactTable" class="table table-hover">
                        <tr>
                            <th width="40%">Name Of Residence</th>
                            <th width="30%">Address</th>
                            <th width="15%"></th>
                            <th width="15%"></th>
                        </tr>
                        <tr>
                            <c:forEach var="currentPlace" items="${lList}">
                            <tr>
                                <td>
                                    <a href="displayLocationDetails?locationId=${currentPlace.locationId}">
                                        <c:out value="${currentPlace.nameOfResidence}"/>
                                    </a>
                                </td>
                                <td>
                                    <c:out value="${currentPlace.address}"/>
                                </td>
                                <td>
                                    <sec:authorize access="isAuthenticated()">
                                        <a href="editLocationForm?locationId=${currentPlace.locationId}">
                                            Edit
                                        </a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <a href="deleteLocation?locationId=${currentPlace.locationId}">
                                            Delete
                                        </a>
                                    </sec:authorize>
                                </td>
                            </tr>
                        </c:forEach>
                        </tr>
                    </table> 
                </div>
                <div class="col-md-6">
                    <sec:authorize access="isAuthenticated()">
                        <h2>Add New</h2>
                        <form class="form-horizontal"
                              role="form" method="POST"
                              action="addNewLocation"
                              enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="add-name-of-residence" class="col-md-4 control-label">
                                    Name of Residence:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="nameOfResidence" 
                                           placeholder="Name of Residence" required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-address" class="col-md-4 control-label">
                                    Address:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="address" placeholder="Address"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-latitude" class="col-md-4 control-label">
                                    Latitude:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="latitude" placeholder="Latitude"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-longitude" class="col-md-4 control-label">
                                    Longitude:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="longitude" placeholder="Longitude"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="add-description" class="col-md-4 control-label">
                                    Description:
                                </label>
                                <div class="col-md-8">
                                    <input type="text" class="form-control" name="description" placeholder="Description"
                                           required/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-offset-4 col-md-8">
                                    <input type="submit" value="Add"/>
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
