package com.example.sso_poc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nimbusds.oauth2.sdk.AccessTokenResponse;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.AuthorizationCodeGrant;
import com.nimbusds.oauth2.sdk.AuthorizationGrant;
import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.TokenErrorResponse;
import com.nimbusds.oauth2.sdk.TokenRequest;
import com.nimbusds.oauth2.sdk.TokenResponse;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Base64;


@RestController
public class UserInfoController {

    @RequestMapping(value = "/user_info", method = RequestMethod.POST)
    public UserInfo userInfov2(@RequestBody UserInfoRequestBody userInfoRequestBody) throws URISyntaxException, IOException, InterruptedException, ParseException, ParseException {

        AuthorizationCode code = new AuthorizationCode(userInfoRequestBody.code);
        URI callback = new URI("https://guyssopoc.herokuapp.com/callback");
        AuthorizationGrant codeGrant = new AuthorizationCodeGrant(code, callback);

        ClientID clientID = new ClientID("e154112d4c3a4bd5a58d559876ddafef");
        Secret clientSecret = new Secret("0bd7e5bee711f6b75282159c26091728d46f6c38f63888028fd0499ef8b49c2e");
        ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);

        URI tokenEndpoint = new URI("https://accounts.dev.fiverr.com/oauth2/token");

        TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, codeGrant);

        TokenResponse response = TokenResponse.parse(request.toHTTPRequest().send());

        if (!response.indicatesSuccess()) {
            // We got an error response...
            TokenErrorResponse errorResponse = response.toErrorResponse();
        }

        AccessTokenResponse successResponse = response.toSuccessResponse();

        String accessToken = successResponse.getCustomParameters().get("id_token").toString();

        String[] chunks = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        Gson g = new Gson();

        TokenHeader tokenHeader = g.fromJson(new String(decoder.decode(chunks[0])), TokenHeader.class);
        TokenPayload tokenPayload = g.fromJson(new String(decoder.decode(chunks[1])), TokenPayload.class);

        String sig = new String(decoder.decode(chunks[2]));


        return new UserInfo(tokenPayload.email, tokenPayload.email_verified, tokenPayload.preferred_username, tokenPayload.picture, tokenPayload.name);
    }
}
