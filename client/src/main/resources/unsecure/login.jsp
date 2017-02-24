<!DOCTYPE html>
<html xmlns:ng='http://angularjs.org' id='ng-app' ng-app='JangularApp.unsecure'>
<head>
    <title>jAngular Login</title>
    <meta name='viewport' content='width=device-width, initial-scale=1.0'>
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <!-- Bootstrap -->
    <link rel='stylesheet' type='text/css' href='../lib/bootstrap-3.0.3/css/bootstrap.css'/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src='https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js'></script>
    <script src='https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js'></script>
    <![endif]-->

    <link rel='stylesheet' type='text/css' href='authenticate.css'>
</head>

<body>

<div ng-view ng-controller='login'></div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src='https://code.jquery.com/jquery.js'></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src='../lib/bootstrap-3.0.3/js/bootstrap.min.js'></script>

<script src='../lib/angular/angular.js'></script>
<script src='../lib/angular/angular-route.js'></script>

<script src='authenticate.js'></script>

</body>

</html>