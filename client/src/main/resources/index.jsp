<!DOCTYPE html>
<html lang='en' ng-app='JangularApp.app'>

<head>
    <title>jAngular</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv='Content-Type' content='text/html; charset=utf-8'/>

    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <!-- Bootstrap -->
    <link rel='stylesheet' type='text/css' href='lib/bootstrap-3.0.3/css/bootstrap.css'/>

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <link rel='stylesheet' type='text/css' href='app.css'>
</head>

<body>

<div ng-view ng-controller='appController'></div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://code.jquery.com/jquery.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="lib/bootstrap-3.0.3/js/bootstrap.min.js"></script>

<script src='lib/angular/angular.js'></script>
<script src='lib/angular/angular-route.js'></script>

<script src='common/utils.js'></script>
<script src='common/rest.js'></script>

<script src='app.js'></script>

<script src='shared/sharedDirectives.js'></script>
<script src='home/home.js'></script>
<script src='about/about.js'></script>

</body>

</html>