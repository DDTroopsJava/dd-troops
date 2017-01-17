<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Edit hero">
<jsp:attribute name="body">
  <c:set var="end" value="heroes"/>

    <form:form method="post" action="${pageContext.request.contextPath}/${end}/edit/${heroEdit.id}"
               modelAttribute="heroEdit" cssClass="form-horizontal">

        <div class="form-group ${name_error?'has-error':''}">
          <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
          <div class="col-sm-10">
            <form:input path="name" cssClass="form-control"/>
            <form:errors path="name" cssClass="help-block"/>
          </div>
        </div>
        
        <input type="hidden" id="level" name="level" value="${heroEdit.level}">

         <div class="form-group">
           <form:label path="roles" cssClass="col-sm-2 control-label">Roles</form:label>
           <div class="col-sm-10">
                <form:select path="roles" cssClass="form-control">
                    <c:forEach items="${roles}" var="role">
                        <form:option value="${role.id}">${role.name}</form:option>
                    </c:forEach>
                </form:select>
             <p class="help-block"><form:errors path="roles" cssClass="error"/></p>
           </div>
         </div>


      <button class="btn btn-primary" type="submit">Update Hero</button>
    </form:form>

</jsp:attribute>
</my:pagetemplate>