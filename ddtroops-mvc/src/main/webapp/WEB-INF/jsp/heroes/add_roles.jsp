<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Roles to hero ${hero.name}">
    <jsp:attribute name="body">
      <c:set var="end" value="roles"/>

        <table class="table">
          <caption>Roles ${hero.name}</caption>
          <thead>
          <tr>
            <th>Name</th>
            <th>Attack power</th>
            <th>Defense power</th>
            <my:protected>
              <th>Action</th>
            </my:protected>

          </tr>
          </thead>
          <tbody>
          <c:forEach items="${roles}" var="role">
                <tr>
                  <td>
                    <my:a href="/${end}/read/${role.id}"><c:out value="${role.name}"/> </my:a>
                  </td>

                  <td>
                    <c:out value="${role.attackPower}"/>
                  </td>

                  <td>
                    <c:out value="${role.defensePower}"/>
                  </td>

                  <my:protected>
                    <td>
                      <c:choose>

                      <c:when test="${hero.roles.contains(role)}">
                        <form method="post"
                              action="${pageContext.request.contextPath}/heroes/delrole/${hero.id}/${role.id}">

                          <button class="glyphicon glyphicon-trash btn" type="submit">
                          </button>
                        </form>
                      </c:when>
                      <c:otherwise>
                         <form method="post"
                               action="${pageContext.request.contextPath}/heroes/addrole/${hero.id}/${role.id}">

                           <button class="glyphicon glyphicon-plus btn" type="submit">
                           </button>
                         </form>

                       </c:otherwise>
                   </c:choose>


                    </td>

                  </my:protected>
                </tr>
            </c:forEach>
          </tbody>
        </table>


    </jsp:attribute>
</my:pagetemplate>