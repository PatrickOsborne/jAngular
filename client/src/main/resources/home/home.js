appModule.controller( 'Home', function( $scope, $location, restCommon ) {
    console.log( 'Home init' );

    $scope.greeting = '';

    function handleGreetingSuccess( result ) {
        console.log( result );
        $scope.greeting = result.content;
    }

    function handleGreetingError( status, data ) {
        console.log( status );
        console.log( data );
        $scope.greeting = 'greeting error';
    }

    function init() {
        restCommon.greeting( '', handleGreetingSuccess, handleGreetingError );
    }

    init();
} );