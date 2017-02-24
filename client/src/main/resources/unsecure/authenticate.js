'use strict';

var authenticateModule = angular.module( 'JangularApp.unsecure', ['ngRoute'] );

authenticateModule.config( ['$routeProvider', function( $routeProvider ) {
    $routeProvider.when( '/authenticate', {templateUrl:'authenticate.html', controller:'authenticate'} );
    $routeProvider.otherwise( {redirectTo:'/authenticate'} )
}] );

authenticateModule.controller( 'login', function( $scope, $location ) {
    console.log( 'login init' );

    function navigateTo( place ) {
        console.log( 'navigateTo: place: ' + place );
        $location.path( '/' + place );
    }

    function getCsrfToken() {
        return $( "meta[name='_csrf']" ).attr( "content" );
    }

    function init() {
        $scope.errorMsg = '';
        $scope.navigateTo = navigateTo;
        $scope.csrfToken = getCsrfToken();
    }

    init();

} );

authenticateModule.controller( 'authenticate', function( $scope, $location ) {
    console.log( 'authenticate: ' + $location.absUrl() );

    function setErrorMsg( errorMsg ) {
        errorMsg = errorMsg || 'authentication failure';
        $scope.errorMsg = errorMsg;
    }

    function init() {
        $scope.errorMsg = '';

        var url = $location.absUrl();
        var isError = url.indexOf( '?error#' ) >= 0 ? true : false;
        if ( isError )
        {
            setErrorMsg( 'login failure, try again' );
        }
    }

    init();

} );

authenticateModule.factory( 'restUtils', function( $http ) {

    function createHandlePostSuccess( callback, url ) {
        return function handleSuccess( data, status, headers, config ) {
            console.log( url + ': SUCCESS' );
            console.log( arguments );
            if ( callback )
            {
                callback( data );
            }
        };
    }

    function createHandlePostError( callback, url ) {
        return function handleError( data, status, headers, config ) {
            console.log( url + ': ERROR' );
            console.log( arguments );
            if ( callback )
            {
                callback( data, status, headers, config );
            }
        };
    }

    function doPost( url, successCallback, errorCallback, data, config ) {
        console.log( 'POST: ' + url );
        var promise = $http.post( url, data, config );
        promise.success( createHandlePostSuccess( successCallback, url ) );
        promise.error( createHandlePostError( errorCallback, url ) );
        return promise;
    }

    return {
        doPost:doPost
    }

} );
