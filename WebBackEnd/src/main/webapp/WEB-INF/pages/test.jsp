<%@ page import="com.vinhnq.common.URLConst" %>
<%@ page import="com.vinhnq.common.CommonConst" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<link rel="stylesheet" href="//cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css"></link>
<link rel="stylesheet" href="<c:url value = "/resources/css/dashboard.css" />"></link>
<style>
    .btn-message {
        vertical-align: baseline;
    }

</style>
<script>
    let LAYOUT = {
        recent_alerts: '<spring:message code="LAYOUT.recent_alerts" />',
        normal: '<spring:message code="LAYOUT.normal" />',
        danger_of_falling: '<spring:message code="LAYOUT.danger_of_falling" />',
        sudden_movement: '<spring:message code="LAYOUT.sudden_movement" />',
    }
    let MessageTitle = {
        MESSAGE_falling_warning: '<spring:message code="LAYOUT.fall_warning" />',
    }
</script>
<jsp:include page="common/menu.jsp"/>
<%--<div class="container ">
    <div class="d-flex justify-content-center h-100">--%>
<div class="w-100">
    <div class="d-f justify-content-left h-100">
        <div class="card col-12">
            <div class="card-header">
                <h3><spring:message code="LAYOUT.real_time"/></h3>
                <div class="d-flex justify-content-end social_icon">
                    <%--						<span><i class="fab fa-facebook-square"></i></span>
                                            <span><i class="fab fa-google-plus-square"></i></span>
                                            <span><i class="fab fa-twitter-square"></i></span>--%>
                </div>
            </div>
            <div class="card-body">
                <div>
                    <div> Test</div>
                </div>
                <%--==========================================--%>

                <div class="tab-panel">

                    <form:form method="POST"
                               action="${contextPath}/${lang}/file/upload-file"
                               enctype="multipart/form-data">
                        <table>
                            <tr>
                                <td>Name</td>
                                <td><input type="text" name="name" /></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><input type="text" name="email" /></td>
                            </tr>
                            <tr>
                                <td>Select a file to upload</td>
                                <td><input type="file" name="file" /></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Submit" /></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>


    </div>
    <%--            <div class="card-footer">
                    <div class="d-flex justify-content-center links">
                        Don't have an account?<a href="#">Sign Up</a>
                    </div>
                    <div class="d-flex justify-content-center">
                        <a href="#">Forgot your password?</a>
                    </div>
                </div>--%>
</div>
</div>
</div>
<script>
    $(document).ready(function () {
        getData();
    });
</script>
<script src="//cdn.datatables.net/1.10.25/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" language="javascript"
        src="https://nightly.datatables.net/responsive/js/dataTables.responsive.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/common/Utils.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dashboard/dashboard.js"></script>
<jsp:include page="common/footer.jsp"/>

