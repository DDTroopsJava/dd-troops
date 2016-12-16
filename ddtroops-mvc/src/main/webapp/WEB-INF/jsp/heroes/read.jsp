<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Hero ${hero.name}">
    <jsp:attribute name="body">
      <c:set var="end" value="heroes"/>

        <script>
            function openModal(suffix) {
                var modal = $("#modal_" + suffix);
                if (modal)
                    modal.modal('show');
            }

            function closeModal(suffix) {
                var modal = $("#modal_" + suffix);
                if (modal)
                    modal.modal('hide');
            }
        </script>

        <table class="table">
          <caption>Hero   <c:out value="${hero.name}"/></caption>
          <thead>
          <tr>
            <th>Name</th>
            <th>Level</th>
            <th>Number of Roles</th>
            <my:protected>
              <th>Roles</th>
              <th>Delete</th>
              <th>Update</th>
            </my:protected>

          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              <my:a href="/${end}/read/${hero.id}"><c:out value="${hero.name}"/> </my:a>
            </td>

            <td>
              <c:out value="${hero.level}"/>
            </td>

            <td>
              <c:out value="${hero.roles.size()}"/>
            </td>

            <my:protected>
                <td>
                    <my:a href="/${end}/addrole/${hero.id}">
                      <span class="glyphicon glyphicon-tower"> </span>
                    </my:a>
                </td>
                    <td>
                      <button class="glyphicon glyphicon-trash btn" onclick=" openModal(${hero.id}) ">
                      </button>


                      <my:modal_template suffix="${hero.id}" title="Delete hero">
                          <jsp:attribute name="body">
                              <strong>Are you sure you want to delete the hero: <c:out value="${hero.name}"/></strong>
                          </jsp:attribute>
                          <jsp:attribute name="footer">
                              <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                      onclick="closeModal(${hero.id})">Close
                              </button>
                            <form style="float: right; margin-left: 10px" method="post"
                                  action="${pageContext.request.contextPath}/${end}/delete/${hero.id}">
                              <input type="submit" class="btn btn-primary" value="Delete"/>
                            </form>
                          </jsp:attribute>
                        </my:modal_template>

                    </td>
                    <td>
                      <button class="glyphicon glyphicon-edit btn"
                              onclick="location.href='${pageContext.request.contextPath}/${end}/edit/${hero.id}'">
                      </button>
                    </td>
                </my:protected>

          </tr>
          </tbody>
        </table>


       <table class="table">
         <caption>Roles</caption>
         <thead>
         <tr>
           <th>Name</th>
           <th>Attack power</th>
           <th>Defense power</th>

         </tr>
         </thead>
         <tbody>
         <c:forEach items="${hero.roles}" var="role">
                <tr>
                  <td>
                    <my:a href="/roles/read/${role.id}"><c:out value="${role.name}"/> </my:a>
                  </td>

                  <td>
                    <c:out value="${role.attackPower}"/>
                  </td>

                  <td>
                    <c:out value="${role.defensePower}"/>
                  </td>
                </tr>
            </c:forEach>
         </tbody>
       </table>

       <button class="btn"
               onclick="location.href='${pageContext.request.contextPath}/${end}'">
         Return
       </button>

    </jsp:attribute>
</my:pagetemplate>