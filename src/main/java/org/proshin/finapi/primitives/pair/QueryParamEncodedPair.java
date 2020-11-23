package org.proshin.finapi.primitives.pair;

import java.nio.charset.StandardCharsets;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.util.UriUtils;

public final class QueryParamEncodedPair implements NameValuePair {

    private final NameValuePair origin;

    public QueryParamEncodedPair(final String name, final Object value) {
        this(name, value.toString());
    }

    public QueryParamEncodedPair(final String name, final String value) {
        this(new BasicNameValuePair(name, value));
    }

    public QueryParamEncodedPair(final NameValuePair origin) {
        this.origin = origin;
    }

    @Override
    public String getName() {
        return this.origin.getName();
    }

    @Override
    public String getValue() {
        return UriUtils.encodeQueryParam(this.origin.getValue(), StandardCharsets.UTF_8);
    }
}
