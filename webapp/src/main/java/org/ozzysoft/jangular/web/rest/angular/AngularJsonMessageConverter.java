package org.ozzysoft.jangular.web.rest.angular;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public class AngularJsonMessageConverter extends MappingJackson2HttpMessageConverter
{
    @Override
    public void setJsonPrefix( String jsonPrefix )
    {
        // unescape \n in json prefix to conform to Angular's $http XSRF json prefix.
        // The Spring converter does not do this.
        super.setJsonPrefix( StringEscapeUtils.unescapeJava( jsonPrefix ) );
    }
}
