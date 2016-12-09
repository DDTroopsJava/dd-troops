<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Users">
    <jsp:attribute name="body">
      <c:set var="end" value="users"/>

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
          <caption>Users</caption>
          <thead>
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Joined</th>
            <th>Admin?</th>
            <th>Delete</th>
            <th>Update</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${users}" var="user">
                <tr>
                  <td>
                    <my:a href="/${end}/read/${user.id}"><c:out value="${user.name}"/> </my:a>
                  </td>
                  <td>
                    <my:a href="/${end}/read/${user.id}"><c:out value="${user.email}"/></my:a>
                  </td>
                  <td>
                    <c:out value="${user.phone}"/>
                  </td>
                  <td>
                    <fmt:formatDate value="${user.joinedDate}" pattern="yyyy-MM-dd"/>
                  </td>
                  <td>
                    <c:out value="${user.admin}"/>
                  </td>
                  <td>
                    <button class="glyphicon glyphicon-trash btn" onclick=" openModal(${user.id}) ">
                    </button>


                    <my:modal_template suffix="${user.id}" title="Delete user">
                      <jsp:attribute name="body">
                          <strong>Are you sure you want to delete a user: <c:out value="${user.email}"/></strong>
                      </jsp:attribute>
                      <jsp:attribute name="footer">
                          <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                  onclick="closeModal(${user.id})">Close
                          </button>
                        <form style="float: right; margin-left: 10px" method="post"
                              action="${pageContext.request.contextPath}/${end}/delete/${user.id}">
                          <input type="submit" class="btn btn-primary" value="Delete"/>
                        </form>
                      </jsp:attribute>
                    </my:modal_template>

                  </td>
                  <td>
                    <button class="glyphicon glyphicon-edit btn" onclick="location.href='${pageContext.request.contextPath}/${end}/edit/${user.id}'">
                    </button>
                  </td>
                </tr>
            </c:forEach>
          </tbody>
        </table>

    </jsp:attribute>
</my:pagetemplate>