<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Roles">
    <jsp:attribute name="body">
      <c:set var="end" value="roles"/>


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
          <caption>Roles</caption>
          <thead>
          <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Attack power</th>
            <th>Defense power</th>
            <my:protected>
              <th>Delete</th>
              <th>Update</th>
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
                    <c:out value="${role.description}"/>
                  </td>
                  
                  <td>
                    <c:out value="${role.attackPower}"/>
                  </td>

                  <td>
                    <c:out value="${role.defensePower}"/>
                  </td>

                  <my:protected>
                    <td>
                      <button class="glyphicon glyphicon-trash btn" onclick=" openModal(${role.id}) ">
                      </button>


                      <my:modal_template suffix="${role.id}" title="Delete role">
                            <jsp:attribute name="body">
                                <strong>Are you sure you want to delete the role: <c:out value="${role.name}"/></strong>
                            </jsp:attribute>
                            <jsp:attribute name="footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                        onclick="closeModal(${role.id})">Close
                                </button>
                              <form style="float: right; margin-left: 10px" method="post"
                                    action="${pageContext.request.contextPath}/${end}/delete/${role.id}">
                                <input type="submit" class="btn btn-primary" value="Delete"/>
                              </form>
                            </jsp:attribute>
                          </my:modal_template>

                    </td>
                    <td>
                      <button class="glyphicon glyphicon-edit btn"
                              onclick="location.href='${pageContext.request.contextPath}/${end}/edit/${role.id}'">
                      </button>
                    </td>
                  </my:protected>


                </tr>
            </c:forEach>
          </tbody>
        </table>

      <my:protected>
              <button class="btn btn-primary"
                      onclick="location.href='${pageContext.request.contextPath}/${end}/create'">
                Add role
              </button>
      </my:protected>
      <button class="btn"
              onclick="location.href='${pageContext.request.contextPath}/'">
        Return
      </button>

    </jsp:attribute>
</my:pagetemplate>