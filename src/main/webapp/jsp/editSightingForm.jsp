<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Super 101</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">        
    </head>
    <body>
        <div class="container">
            <h1>Sighting Details</h1>
            <hr/>
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
            <sf:form class="form-horizontal" role="form" modelAttribute="sighting" 
                     action="editSighting" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="add-superhero" class="col-md-4 control-label">Superhero:</label>
                    <div class="col-md-8"> 
                        <sf:select class="form-control" name="superpersonId" path="sp.superpersonId">
                            <c:forEach var="currentSuper" items="${superheroes}">                                
                                <sf:option value="${currentSuper.superpersonId}" name="superpersonId">   
                                    <c:out value="${currentSuper.name}"/>
                                </sf:option>
                            </c:forEach>
                        </sf:select>
                        <sf:errors path="sp.superpersonId" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-date-seen" class="col-md-4 control-label">Date Seen:</label>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="add-date-seen"
                                   value="${date}" placeholder="Date Seen" name="date" 
                            required pattern="[0-9]{4}[-][0-9]{2}[-][0-9]{2}"/>
                        <sf:errors path="dateSeen" cssclass="error"></sf:errors>
                        <h4 style="color:red">${message}</h4>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-location" class="col-md-4 control-label">Location:</label>
                    <div class="col-md-8"> 
                        <sf:select class="form-control" name="locationId" path="location.locationId">                              
                            <c:forEach var="currentLocation" items="${locations}">  
                                <sf:option value="${currentLocation.locationId}" id="add-location" name="locationId">   
                                    <c:out value="${currentLocation.nameOfResidence}" />
                                </sf:option>
                            </c:forEach>
                        </sf:select>
                        <sf:errors path="location.locationId" cssclass="error"></sf:errors>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="add-description" class="col-md-4 control-label">Description:</label>
                        <div class="col-md-8">
                        <sf:input type="text" class="form-control" id="add-description"
                                  path="description" placeholder="Description" name="description"/>
                        <sf:errors path="description" cssclass="error"></sf:errors>

                            <h4 style="color:red">${message}</h4>
                    </div>
                </div>

                <div class="form-group">
                    <label for="add-picture" class="col-md-4 control-label">Upload picture:</label>
                    <div class="col-md-8">
                        <input type="file" 
                               id="picture" 
                               name="picture"/>
                        <sf:errors path="fileName" cssclass="error"></sf:errors>
                        <sf:hidden path="sightingId"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update"/>
                    </div>
                </div>
            </sf:form>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>
