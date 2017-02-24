var restModule = angular.module( 'JangularApp.rest', [] );

restModule.factory( 'restUtils', function( $http ) {

    function createHandleGetSuccess( callback, url ) {
        return function( data, status, headers, config ) {
            callback( data );
        };
    }

    function createHandleGetError( callback, url ) {
        return function( data, status, headers, config ) {
            console.log( url + ': ERROR' );
            console.log( arguments );
            callback( status, data );
        };
    }

    function createHandlePostSuccess( callback, url ) {
        return function handleSuccess( data, status, headers, config ) {
            console.log( url + ': SUCCESS' );
//            console.log( arguments );
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

    function getCsrfToken() {
        return $( "meta[name='_csrf']" ).attr( 'content' );
    }

    function getCsrfHeaderName() {
        return $( "meta[name='_csrf_header']" ).attr( 'content' );
    }

    function addCsrfHeaders( config ) {
        config = config || {};
        config.headers = config.headers || {};
        var headers = config.headers;
        headers[getCsrfHeaderName()] = getCsrfToken();
        return config;
    }

    function doGet( url, successCallback, errorCallback ) {
        console.log( 'GET: ' + url );
        var promise = $http.get( url );
        promise.success( createHandleGetSuccess( successCallback, url ) );
        promise.error( createHandleGetError( errorCallback, url ) );
        return promise;
    }

    function doPost( url, successCallback, errorCallback, data, config ) {
        console.log( 'POST: ' + url );
        config = addCsrfHeaders( config );
        var promise = $http.post( url, data, config );
        promise.success( createHandlePostSuccess( successCallback, url ) );
        promise.error( createHandlePostError( errorCallback, url ) );
        return promise;
    }

    return {
        doGet:doGet,
        doPost:doPost
    }

} );

restModule.factory( 'restCommon', function( restUtils ) {

    function logout( successCallback, errorCallback ) {
        var url = '/jAngular/logout';
        console.log( 'logout(): ' + url );
        return restUtils.doPost( url, successCallback, errorCallback );
    }

    function version( successCallback, errorCallback ) {
        var url = '/jAngular/rest/version';
        console.log( 'looking up version: ' + url );
        return restUtils.doPost( url, successCallback, errorCallback );
    }

    function greeting( name, successCallback, errorCallback ) {
        var url = '/jAngular/rest/greeting';
        if ( name )
        {
            url += '?name=' + name;
        }
        console.log( 'greeting: ' + name + ', url: ' + url );
        return restUtils.doGet( url, successCallback, errorCallback );
    }

    return {
        logout:logout,
        version:version,
        greeting:greeting
    }

} );
