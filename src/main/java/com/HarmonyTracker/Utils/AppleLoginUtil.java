package com.HarmonyTracker.Utils;

import com.HarmonyTracker.Models.Apple.IdTokenPayload;
import com.HarmonyTracker.Models.Apple.TokenResponse;
import com.nimbusds.jose.shaded.gson.Gson;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileReader;
import java.security.PrivateKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class AppleLoginUtil {
    private final WebClient webClient;

    private static String APPLE_AUTH_URL = "https://appleid.apple.com/auth/token";

    private static String KEY_ID = "Z3XCBR246A";
    private static String TEAM_ID = "3367YZUD76";
    private static String CLIENT_ID = "com.HarmonyTracker";


    private static PrivateKey pKey;

    private static PrivateKey getPrivateKey() throws Exception {
        //read your key
        String path = new ClassPathResource("keys/AuthKey_Z3XCBR246A.p8").getFile().getAbsolutePath();

        final PEMParser pemParser = new PEMParser(new FileReader(path));
        final JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        final PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
        final PrivateKey pKey = converter.getPrivateKey(object);

        return pKey;
    }

    private static String generateJWT() throws Exception {
        if (pKey == null) {
            pKey = getPrivateKey();
        }

        String token = Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, KEY_ID)
                .setIssuer(TEAM_ID)
                .setAudience("https://appleid.apple.com")
                .setSubject(CLIENT_ID)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 5)))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(pKey, SignatureAlgorithm.ES256)
                .compact();

        return token;
    }



    /*
     * Returns unique user id from apple
     * */
    public IdTokenPayload appleAuth(String authorizationCode) throws Exception {
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
        bodyMap.add("client_id", CLIENT_ID);
        bodyMap.add("client_secret", generateJWT());
        bodyMap.add("grant_type", "authorization_code");
        bodyMap.add("code", authorizationCode);
        bodyMap.add("redirect_uri", null);
        TokenResponse tokenResponse;
        try {
            tokenResponse = webClient.post()
                    .uri(APPLE_AUTH_URL)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body(BodyInserters.fromFormData(bodyMap))
                    .retrieve()
                    .bodyToMono(TokenResponse.class)
                    .block();


            String idToken = tokenResponse.getId_token();
            System.out.println("| ID TOKEN: "+idToken);
            String payload = idToken.split("\\.")[1];//0 is header we ignore it for now
            String decoded = new String(Decoders.BASE64.decode(payload));
            System.out.println("| DECODED!: "+ decoded);
            IdTokenPayload idTokenPayload = new Gson().fromJson(decoded, IdTokenPayload.class);

            return idTokenPayload;
        } catch (Exception e) {
            throw e;

        }
    }

}