package org.proshin.finapi.accesstoken;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.proshin.finapi.exception.NoFieldException;

final class UserAccessTokenTest {

    @Test
    void testThatUserTokenFailsIfNoRefreshTokenIsSent() {
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

        assertThatExceptionOfType(NoFieldException.class)
            .isThrownBy(token::refreshToken)
            .withMessage("Field 'refresh_token' may not be null for user's access token");
    }
}
