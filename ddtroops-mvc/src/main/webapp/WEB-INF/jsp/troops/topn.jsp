<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Top N Troops">
<jsp:attribute name="body">
  <c:set var="end" value="troops"/>
   
    <form:form method="post" action="${pageContext.request.contextPath}/${end}/topn"
               cssClass="form-horizontal">
        
        <div class="form-group">
          <label form="number" class="col-sm-2 control-label">Number of results</label>
          <div class="col-sm-10">
              <input type="number" name="number" id="number" class="form-control" required/>
          </div>
        </div>
       <div class="form-group">
         <label form="mission" class="col-sm-2 control-label">Mission name</label>
          <div class="col-sm-10">
             <input type="text" name="mission" id="mission" class="form-control" placeholder="(optional)"/>
          </div>
       </div>
        <div class="form-group">
          <label form="troopSize" class="col-sm-2 control-label">Troop size</label>
          <div class="col-sm-10">
             <input type="number" name="troopSize" id="troopSize" class="form-control" placeholder="(optional)"/>
          </div>
        </div>        
      <button class="btn btn-primary" type="submit">View Top N Troops</button>
    </form:form>
      
    <c:if test="${not empty troops}">
        <br/>
        <br/>
        
        <table class="table">
            <caption>Results</caption>
            <thead>
            <tr>
              <th>Name</th>
              <th>Mission</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${troops}" var="troop">
                  <tr>
                    <td>
                      <my:a href="/${end}/read/${troop.id}"><c:out value="${troop.name}"/> </my:a>
                    </td>

                    <td>
                      <c:out value="${troop.mission}"/>
                    </td>

                  </tr>

              </c:forEach>
            </tbody>
        </table>
              
    </c:if>  
           
    <Br>
    <Br>
    <button class="btn btn-primary"
            onclick="location.href='${pageContext.request.contextPath}/${end}'">
      Return
    </button>

</jsp:attribute>
</my:pagetemplate>
