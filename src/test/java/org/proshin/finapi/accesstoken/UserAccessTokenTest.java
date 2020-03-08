package org.proshin.finapi.accesstoken;

import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.exception.NoFieldException;

public class UserAccessTokenTest {

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

        final Exception exception =
            assertThrows(NoFieldException.class, token::refreshToken);
        assertThat(exception.getMessage()).isEqualTo(("Field 'refresh_token' may not be null for user's access token"));
    }
}
