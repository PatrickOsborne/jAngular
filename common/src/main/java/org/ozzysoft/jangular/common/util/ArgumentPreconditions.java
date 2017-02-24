package org.ozzysoft.jangular.common.util;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.List;

import com.google.common.base.Strings;

/**
 * Convenience functions to assert simple qualities about an argument.
 */
public class ArgumentPreconditions
{

    private ArgumentPreconditions()
    {
    }

    public static <T> T checkNull( T reference )
    {
        if ( reference != null )
        {
            throw new IllegalArgumentException();
        }
        return reference;
    }

    public static <T> T checkNull( T reference, Object errorMessage )
    {
        if ( reference != null )
        {
            throw new IllegalArgumentException( String.valueOf( errorMessage ) );
        }
        return reference;
    }

    public static <T> List<T> checkNotNullAndNotEmpty( List<T> input )
    {
        checkNotNull( input );
        checkGreaterThan( input.size(), 0 );
        return input;
    }

    public static <T> List<T> checkNotNullAndNotEmpty( List<T> input, String errorMessage )
    {
        checkNotNull( input, errorMessage );
        checkGreaterThan( input.size(), 0, errorMessage );
        return input;
    }

    public static String checkNotNullAndNotEmpty( final String input )
    {
        return checkNotNullAndNotEmpty( input, "value must be not null and not empty, value: " + input );
    }

    public static String checkNotNullAndNotEmpty( final String input, String errorMessage )
    {
        checkArgument( !Strings.isNullOrEmpty( input ), errorMessage );
        return input;
    }

    public static int checkNotZero( final int input, String errorMessage )
    {
        return checkNotEqual( input, 0, errorMessage );
    }

    public static int checkNotEqual( int input, int shouldNotBeEqualTo, String errorMessage )
    {
        checkArgument( input != shouldNotBeEqualTo, errorMessage );
        return input;
    }

    public static int checkEqual( int input, int shouldBeEqualTo, String errorMessage )
    {
        checkArgument( input == shouldBeEqualTo, errorMessage );
        return input;
    }

    public static long checkNotZero( final long input, String errorMessage )
    {
        checkNotEqual( input, 0, errorMessage );
        return input;
    }

    public static long checkNotEqual( long input, long shouldNotBeEqualTo, String errorMessage )
    {
        checkArgument( input != shouldNotBeEqualTo, errorMessage );
        return input;
    }

    public static long checkEqual( long input, long shouldBeEqualTo, String errorMessage )
    {
        checkArgument( input == shouldBeEqualTo, errorMessage );
        return input;
    }

    public static long checkGreaterThan( long input, long minimum, String error )
    {
        checkArgument( input > minimum, error );
        return input;
    }

    public static float checkGreaterThan( float input, float minimum, String error )
    {
        checkArgument( input > minimum, error );
        return input;
    }

    public static long checkGreaterThan( long input, long minimum )
    {
        if ( input > minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be greater than ( " + minimum + " )" );
    }

    public static int checkGreaterThan( int input, int minimum )
    {
        if ( input > minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be greater than ( " + minimum + " )" );
    }

    public static int checkGreaterThan( int input, int minimum, String error )
    {
        checkArgument( input > minimum, error );
        return input;
    }

    public static Number checkNumberGreaterThan( Number input, Number minimum, String error )
    {
        checkNotNull( input );
        checkNotNull( minimum );
        checkArgument( input.doubleValue() > minimum.doubleValue(), error );
        return input;
    }

    public static long checkLessThan( long input, long minimum )
    {
        if ( input < minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be less than ( " + minimum + " )" );
    }

    public static int checkLessThan( int input, int minimum )
    {
        if ( input < minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be less than ( " + minimum + " )" );
    }

    public static long checkLessThanOrEqualTo( long input, long minimum )
    {
        if ( input <= minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be less than or equal to ( " + minimum + " )" );
    }

    public static int checkLessThanOrEqualTo( int input, int minimum )
    {
        if ( input <= minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be less than or equal to ( " + minimum + " )" );
    }

    public static Number checkGreaterThan( Number input, Number minimum )
    {
        checkNotNull( input );
        checkArgument( minimum.doubleValue() < input.doubleValue() );
        return input;
    }

    public static long checkGreaterThanOrEqualTo( long input, long minimum )
    {
        if ( input >= minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be greater than or equal to ( " + minimum + " )" );
    }

    public static long checkGreaterThanOrEqualTo( long input, long minimum, String error )
    {
        checkArgument( minimum <= input, error );
        return input;
    }

    public static int checkGreaterThanOrEqualTo( int input, int minimum )
    {
        if ( input >= minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be greater than or equal to ( " + minimum + " )" );
    }

    public static int checkGreaterThanOrEqualTo( int input, int minimum, String error )
    {
        checkArgument( minimum <= input, error );
        return input;
    }

    public static float checkGreaterThanOrEqualTo( float input, float minimum )
    {
        if ( input >= minimum )
        {
            return input;
        }
        throw new IllegalArgumentException( "value( " + input + " ) must be greater than or equal to ( " + minimum + " )" );
    }

    public static float checkGreaterThanOrEqualTo( float input, float minimum, String error )
    {
        checkArgument( minimum <= input, error );
        return input;
    }

    public static int checkNotNegative( final int input )
    {
        return checkGreaterThanOrEqualTo( input, 0, "input must not be negative." );
    }

    public static float checkNotNegative( final float input )
    {
        return checkGreaterThanOrEqualTo( input, 0f, "input must not be negative." );
    }

    public static long checkNotNegative( final long input )
    {
        return checkGreaterThanOrEqualTo( input, 0L, "input must not be negative." );
    }

    public static long checkPositive( final long input )
    {
        return checkGreaterThan( input, 0, "input must be positive." );
    }

    public static float checkPositive( final float input )
    {
        return checkGreaterThan( input, 0, "input must be positive." );
    }

    public static Number checkPositiveNumber( final Number input )
    {
        return checkNumberGreaterThan( input, 0, "input must be positive." );
    }

    public static int checkPositive( final int input )
    {
        return checkGreaterThan( input, 0, "input must be positive." );
    }

    public static int checkWithinRange( final int input, final int minimum, final int maximum, String message )
    {
        checkArgument( minimum <= input && input <= maximum, message );
        return input;
    }

    public static float checkWithinRange( final float input, final float minimum, final float maximum, String message )
    {
        checkArgument( minimum <= input && input <= maximum, message );
        return input;
    }

    public static int checkWithinRange( final int input, final int minimum, final int maximum )
    {
        return checkWithinRange( input, minimum, maximum, "" );
    }

    public static int checkStringIsInteger( final String input, String message )
    {
        try
        {
            return Integer.valueOf( input );
        }
        catch ( NumberFormatException nfe )
        {
            throw new IllegalArgumentException( message, nfe );
        }
    }

    public static long checkWithinRange( final long input, final long minimum, final long maximum )
    {
        checkGreaterThanOrEqualTo( input, minimum, "input must be between " + minimum + " and " + maximum + " inclusive." );
        checkLessThanOrEqualTo( input, maximum );
        return input;
    }

    public static String checkLength( final String input, final int length )
    {
        checkEqual( input.length(), length, "input must be " + length + "characters." );
        return input;
    }

    public static <T> void checkContains( Collection<T> collection, T object )
    {
        checkContains( collection, object, "Collection does not contain object: " + object );
    }

    public static <T> void checkContains( Collection<T> collection, T object, String error )
    {
        checkState( collection.contains( object ), error );
    }

}
