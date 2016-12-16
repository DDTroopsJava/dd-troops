<%-- 
    Document   : edit.jsp
    Created on : Dec 13, 2016, 6:14:26 PM
    Author     : Richard
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Battle">
<jsp:attribute name="body">
  <c:set var="end" value="troops"/>

    
    <form method="post" action="${pageContext.request.contextPath}/${end}/battle"
               css="form-horizontal">
       <div class="form-group">
            <label css="col-sm-2 control-label">First troop</label>
            
            <select name="firstTroopId" style="display: block;
                                                width: 100%;
                                                height: 34px;
                                                padding: 6px 12px;
                                                font-size: 14px;
                                                line-height: 1.42857143;
                                                color: #555;
                                                background-color: #fff;
                                                background-image: none;
                                                border: 1px solid #ccc;
                                                border-radius: 4px;">
                <c:forEach items="${troops}" var="t">
                    <option value="${t.id}">${t.name}</option>
                </c:forEach>
            </select>             
            
        </div>
            <label css="col-sm-2 control-label">vs.</label>
        <div class="form-group">
            <label css="col-sm-2 control-label">Second troop</label>
            <select name="secondTroopId" style="display: block;
                                                width: 100%;
                                                height: 34px;
                                                padding: 6px 12px;
                                                font-size: 14px;
                                                line-height: 1.42857143;
                                                color: #555;
                                                background-color: #fff;
                                                background-image: none;
                                                border: 1px solid #ccc;
                                                border-radius: 4px;">
                <c:forEach items="${troops}" var="t">
                    <option value="${t.id}">${t.name}</option>
                </c:forEach>
            </select>             
        </div>
       <button class="btn btn-primary" type="submit">Fight!</button>
    </form>
      
    <Br>
    <Br>
    <button class="btn"
            onclick="location.href='${pageContext.request.contextPath}/${end}'">
      Return
    </button>

</jsp:attribute>
</my:pagetemplate>
