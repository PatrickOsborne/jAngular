'use strict';

var sharedModule = angular.module( 'JangularApp.shared', [] );

sharedModule.directive( 'jangularHeader', function() {
    return {
        restrict:'E',
        templateUrl:'shared/header.html'
    }
} );

sharedModule.directive( 'jangularFooter', function() {
    return {
        restrict:'E',
        templateUrl:'shared/footer.html'
    }
} );

