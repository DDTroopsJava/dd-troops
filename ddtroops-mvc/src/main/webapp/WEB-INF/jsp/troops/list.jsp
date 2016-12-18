<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Troops">
    <jsp:attribute name="body">
      <c:set var="end" value="troops"/>

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
          <caption>Troops</caption>
          <thead>
          <tr>
            <th>Name</th>
            <th>Mission</th>
            <th>Number of heroes</th>
            <my:protected>
              <th>Delete</th>
              <th>Update</th>
              <th>Add Hero</th>
            </my:protected>

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
                  <td>
                    <c:out value="${troop.heroes.size()}"/>

                  </td>
                  <my:protected>

                    <td>
                      <button class="glyphicon glyphicon-trash btn" onclick=" openModal(${troop.id}) ">
                      </button>


                      <my:modal_template suffix="${troop.id}" title="Delete troop">
                          <jsp:attribute name="body">
                              <strong>Are you sure you want to delete the troop: <c:out value="${troop.name}"/></strong>
                          </jsp:attribute>
                          <jsp:attribute name="footer">
                              <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                      onclick="closeModal(${troop.id})">Close
                              </button>
                            <form style="float: right; margin-left: 10px" method="post"
                                  action="${pageContext.request.contextPath}/${end}/delete/${troop.id}">
                              <input type="submit" class="btn btn-primary" value="Delete"/>
                            </form>
                          </jsp:attribute>
                        </my:modal_template>

                    </td>
                    <td>
                      <button class="glyphicon glyphicon-edit btn"
                              onclick="location.href='${pageContext.request.contextPath}/${end}/edit/${troop.id}'">
                      </button>
                    </td>
                    <td>
                      <button class="glyphicon glyphicon-user btn"
                              onclick="location.href='${pageContext.request.contextPath}/${end}/addhero/${troop.id}'">
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
                Add Troop
              </button>
      </my:protected>
        
              <button class="btn btn-primary"
                      onclick="location.href='${pageContext.request.contextPath}/${end}/battle'">
                BATTLE!
              </button>
              <button class="btn btn-primary"
                      onclick="location.href='${pageContext.request.contextPath}/${end}/topn'">
                View Top N Troops
              </button>

    </jsp:attribute>
</my:pagetemplate>