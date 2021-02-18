package org.proshin.finapi.primitives.pair;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class PlainNameValuePair implements NameValuePair {

    private final NameValuePair origin;

    public PlainNameValuePair(final String name, final Object value) {
        this(name, value.toString());
    }

    public PlainNameValuePair(final String name, final String value) {
        this(new BasicNameValuePair(name, value));
    }

    public PlainNameValuePair(final NameValuePair origin) {
        this.origin = origin;
    }

    @Override
    public String getName() {
        return this.origin.getName();
    }

    @Override
    public String getValue() {
        return this.origin.getValue();
    }
}
