<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<my:pagetemplate>
    <jsp:attribute name="body">
       <form:form method="post" action="${pageContext.request.contextPath}/auth/login/"
                  modelAttribute="userLogin" cssClass="form-horizontal">

       <div class="form-group ${email_error?'has-error':''}">
         <form:label path="email" cssClass="col-sm-2 control-label">Email</form:label>
         <div class="col-sm-10">
           <form:input path="email" cssClass="form-control"/>
           <form:errors path="email" cssClass="help-block"/>
         </div>
       </div>
        <div class="form-group ${password_error?'has-error':''}">
          <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
          <div class="col-sm-10">
            <form:password path="password" cssClass="form-control"/>
            <form:errors path="password" cssClass="help-block"/>
          </div>
        </div>
      <button class="btn btn-primary" type="submit">Login</button>
    </form:form>


    </jsp:attribute>
</my:pagetemplate>