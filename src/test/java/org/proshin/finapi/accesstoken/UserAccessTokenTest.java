package org.proshin.finapi.accesstoken;

import org.json.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.proshin.finapi.exception.NoFieldException;

public class UserAccessTokenTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThatUserTokenFailsIfNoRefreshTokenIsSent() {
        final AccessToken token = new UserAccessToken(new JSONObject(
            String.join("",
                "{",
                "\"access_token\": \"access token\",",
                "\"token_type\": \"bearer\",",
                "\"expires_in\": 156,",
                "\"scope\": \"all\"",
                "}"
            )
        ));
        this.expectedException.expect(NoFieldException.class);
        this.expectedException.expectMessage("Field 'refresh_token' may not be null for user's access token");
        token.refreshToken();
    }
}
