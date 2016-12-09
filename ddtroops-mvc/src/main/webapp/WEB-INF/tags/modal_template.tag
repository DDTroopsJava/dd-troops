<%@ tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" dynamic-attributes="attr" %>
<%@ attribute name="title" required="true" %>
<%@ attribute name="body" fragment="true" required="true" %>
<%@ attribute name="suffix" required="true" %>
<%@ attribute name="footer" fragment="true" required="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div>
    <div id="modal_${suffix}" class="modal fade">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title"><c:out value="${title}"/></h4>
                </div>
                <div class="modal-body">
                    <jsp:invoke fragment="body"/>
                </div>
                <div class="modal-footer">
                    <jsp:invoke fragment="footer"/>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <script>

    </script>
</div>
