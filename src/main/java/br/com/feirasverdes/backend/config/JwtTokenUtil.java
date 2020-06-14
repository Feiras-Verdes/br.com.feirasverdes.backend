package br.com.feirasverdes.backend.config;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

	private static final long serialVersionUID = -2550185165626007488L;

	public static final Duration VALIDADE_TOKEN_JWT = Duration.ofHours(48);

	@Value("${jwt.secret}")
	private String secret;

	public String getEmailDeToken(final String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getDataDeExpiracaoDeToken(final String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(final String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean tokenExpirado(final String token) {
		final Date expiration = getDataDeExpiracaoDeToken(token);
		return expiration.before(new Date());
	}

	// generate token for user
	public String generateToken(final UserDetails userDetails) {
		final Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(final Map<String, Object> claims, final String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(ZonedDateTime.now().plusNanos(VALIDADE_TOKEN_JWT.toNanos()).toInstant()))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validarToken(final String token, final UserDetails userDetails) {
		final String email = getEmailDeToken(token);
		return email.equals(userDetails.getUsername()) && !tokenExpirado(token);
	}
}