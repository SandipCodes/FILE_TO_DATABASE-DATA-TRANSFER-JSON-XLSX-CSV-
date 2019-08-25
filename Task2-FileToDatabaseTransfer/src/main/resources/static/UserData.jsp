<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="card">

			<div class="card-header bg-primary text-white">
				<h2>WELCOME TO USER DATA PAGE !!!</h2>
			</div>
			<!-- card header end -->

			<div class="card-body">

				<c:if test="${result !=null}">
					<div class="card-footer bg-danger text-white">${result}</div>
					<!-- //card-footer -->
				</c:if>
				
				<form:form action="users" method="post" 
					enctype="multipart/form-data">
					<pre>
	              SELECT DOCUMENT: <input type="file" name="fileObj" />
	                        
	              <input type="submit" value="Upload" /> 
                    </pre>
					<hr>
				</form:form>
			</div>
			<div class="card-body">

				<c:choose>
					<c:when test="${empty list }">
						<h4>No Data Found</h4>
					</c:when>
					<c:otherwise>

						<a href="excelExp">Export Excel</a>
						<a href="pdfExp">Export PDF</a>
						<a href="report">Show Report</a>
						<table class="table table-bordered table-hover">
							<tr class="thead-dark">
								<th>ID</th>
								<th>FIRST NAME</th>
								<th>FIRST NAME</th>
								<th>EMAIL</th>
								<th>MOBILE</th>
								<th>FILE TYPE</th>
							</tr>

							<c:forEach items="${list }" var="u">
								<tr>
									<td><c:out value="${u.id }" /></td>
									<td><c:out value="${u.firstName }" /></td>
									<td><c:out value="${u.lastName }" /></td>
									<td><c:out value="${u.email }" /></td>
									<td><c:out value="${u.phoneNumber }" /></td>

									<td><c:out value="${u.fileType }" /></td>

								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
			<!-- //card-body end -->

			<c:if test="${message !=null}">
				<div class="card-footer bg-danger text-white">${message}</div>
				<!-- //card-footer -->
			</c:if>

			<!-- //card  class end -->
		</div>
		<!-- //container end -->
</body>
</html>