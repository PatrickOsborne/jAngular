'use strict';

var appModule = angular.module( 'JangularApp.app', ['ngRoute', 'JangularApp.shared', 'JangularApp.rest'] );

appModule.config( ['$routeProvider', function( $routeProvider ) {
    $routeProvider.when( '/', {templateUrl:'home/home.html', controller:'Home'} );
    $routeProvider.when( '/about', {templateUrl:'about/about.html', controller:'About'} );
    $routeProvider.otherwise( {redirectTo:'/'} )
}] );

appModule.controller( 'appController', function( $scope, $location, $window, restCommon ) {
    console.log( 'appController init' );

    function navigateTo( place ) {
        console.log( 'navigateTo: place: ' + place );
        if ( place )
        {
            $location.path( '/' + place );
        }
        else
        {
            $location.path( '/' );
        }
    }

    function redirectHome() {
        var url = '/jAngular/index.jsp';
        console.log( url );
        $window.location.href = url;
    }

    function handleVersionSuccess( versionData ) {
        $scope.version = 'Version: ' + versionData.version;
        $scope.versionData = versionData;
    }

    function handleVersionError( status, data ) {
        console.log( status );
        console.log( data );
        $scope.version = 'version lookup error';
    }

    function handleLogoutSuccess( result ) {
        console.log( 'logout(): SUCCESS' );
        redirectHome();
    }

    function handleLogoutError( status, data ) {
        console.log( 'logout(): ERROR' );
        console.log( status );
        console.log( data );
        redirectHome();
    }

    function logout() {
        console.log( 'logout()' );
        restCommon.logout( handleLogoutSuccess, handleLogoutError );
    }

    function init() {
        $scope.version = '';
        $scope.navigateTo = navigateTo;
        $scope.logout = logout;

        restCommon.version( handleVersionSuccess, handleVersionError );
    }

    init();

} );

