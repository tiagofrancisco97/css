<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--  No scriptlets!!! 
	  See http://download.oracle.com/javaee/5/tutorial/doc/bnakc.html 
-->
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="model" class="presentation.web.model.InscricaoModel" scope="request"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AulasGes: inscrição numa aula</title>
</head>
<body>
<h2>Inscrição em aula</h2>
<form action="inscreverAula" method="post">
   <div class="mandatory_field">
		<label for="modalidade">Modalidades:</label>
		<select name="modalidade">  
			<c:forEach var="modalidade" items="${modalidades}">
				<option value="${modalidade}" ${model.modalidade == modalidade ? 'selected' : ''}>${modalidade}</option>
			</c:forEach> 
		</select>
   </div>
   <div class="mandatory_field">
		<label for="inscricao">Tipo de Inscrição:</label>
		<select name="inscricao">  
			<c:forEach var="inscricao" items="${tipo_inscricoes}">
				<option value="${inscricao}" ${model.inscricao == inscricao ? 'selected' : ''}>${inscricao}</option>
				<c:set var="refreshSent" value="false"/>
			</c:forEach> 
		</select>
   </div>
   <div>
		 <table>
            <thead>
                <tr>
                    <th>Nome da aula</th>
                    <th>Instalação</th>
                    <th>Numero de Vagas</th>
                </tr>
            </thead>
            
            <tbody>
                <c:forEach items="${aulas}" var="aula">
                <tr>
                    <td>${aula.nome}</td>
                    <td>${aula.instalacao}</td>
                    <td>${aula.vagas}</td>
                </tr>
                </c:forEach>   
            </tbody>
        </table>
   </div>
  
   <div class="button" align="right">
   	<c:choose>
    <c:when test="${refreshSent eq 'false'}">
        <input type="submit" value="Iniciar Inscrição"> 
    </c:when>    
    <c:otherwise>
       <input type="submit" value="Inscrever">  
    </c:otherwise>
  </c:choose>
   </div>
</form>
</body>
</html>