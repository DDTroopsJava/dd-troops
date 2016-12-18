<%-- 
    Document   : create.jsp
    Created on : Dec 13, 2016, 6:14:26 PM
    Author     : Richard
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Create troop">
<jsp:attribute name="body">
  <c:set var="end" value="troops"/>

    <form:form method="post" action="${pageContext.request.contextPath}/${end}/create"
               modelAttribute="troopCreate" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
          <div class="col-sm-10">
            <form:input path="name" cssClass="form-control"/>
            <form:errors path="name" cssClass="help-block"/>
          </div>
        </div>
       <div class="form-group ${mission_error?'has-error':''}">
         <form:label path="mission" cssClass="col-sm-2 control-label">Mission</form:label>
         <div class="col-sm-10">
           <form:input path="mission" cssClass="form-control"/>
           <form:errors path="mission" cssClass="help-block"/>
         </div>
       </div>
        <div class="form-group ${gold_error?'has-error':''}">
          <form:label path="gold" cssClass="col-sm-2 control-label">Gold</form:label>
          <div class="col-sm-10">
            <form:input path="gold" cssClass="form-control"/>
            <form:errors path="gold" cssClass="help-block"/>
          </div>
        </div>

          <button class="btn btn-primary" type="submit">Create Troop</button>
    </form:form>
    
    <Br>
    <Br>
    <button class="btn"
            onclick="location.href='${pageContext.request.contextPath}/${end}'">
      Return
    </button>

</jsp:attribute>
</my:pagetemplate>
