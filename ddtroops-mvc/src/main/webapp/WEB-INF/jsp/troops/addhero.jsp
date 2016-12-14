<%-- 
    Document   : edit.jsp
    Created on : Dec 13, 2016, 6:14:26 PM
    Author     : Richard
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="false" session="false" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:pagetemplate title="Add hero">
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
            <caption>Heroes of troop <c:out value="${troop.name}"/></caption>
            <thead>
            <tr>
              <th>Name</th>
              <th>Level</th>
              <th>Remove from troop</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${troop.heroes}" var="hero">
                    <tr>
                      <td>
                        <my:a href="/heroes/read/${hero.id}"><c:out value="${hero.name}"/> </my:a>
                      </td>

                      <td>
                        <c:out value="${hero.level}"/>
                      </td>
                        <td>
                          <button class="glyphicon glyphicon-trash btn" onclick=" openModal(${hero.id}) ">
                          </button>


                          <my:modal_template suffix="${hero.id}" title="Remove hero">
                              <jsp:attribute name="body">
                                  <strong>Are you sure you want to remove the hero: <c:out value="${hero.name}"/></strong>
                              </jsp:attribute>
                              <jsp:attribute name="footer">
                                  <button type="button" class="btn btn-secondary" data-dismiss="modal"
                                          onclick="closeModal(${hero.id})">Close
                                  </button>
                                <form style="float: right; margin-left: 10px" method="post"
                                      action="${pageContext.request.contextPath}/${end}/removehero/${troop.id}/${hero.id}">
                                  <input type="submit" class="btn btn-primary" value="Remove"/>
                                </form>
                              </jsp:attribute>
                          </my:modal_template>
                        </td>

                    </tr>
                </c:forEach>
            </tbody>
        </table>
      <br>
        <form method="post" action="${pageContext.request.contextPath}/${end}/addhero/${troop.id}"
                   cssClass="form-horizontal">

            <div class="form-group">
                <label cssClass="col-sm-2 control-label">Add hero</label>
                    <select name="heroId" cssClass="form-control" style="display: block;
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
                        <c:forEach items="${heroes}" var="h">
                            <option value="${h.id}">${h.name}</option>
                        </c:forEach>
                    </select>
            </div>

          <button class="btn btn-primary" type="submit">Add Hero</button>
        </form>

        <Br>
        <Br>
        <button class="btn btn-primary"
                onclick="location.href='${pageContext.request.contextPath}/${end}'">
          Return
        </button>

    </jsp:attribute>
</my:pagetemplate>
