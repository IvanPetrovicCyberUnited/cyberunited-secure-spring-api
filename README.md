# CyberUnited Secure Spring API

Minimal Spring Boot REST API for security testing.

## Build and Run

```sh
./gradlew bootRun
```

## Usage

Login to obtain a JWT:

```sh
curl -XPOST -H 'Content-Type: application/json' \
  -d '{"username":"user","password":"password"}' \
  http://localhost:8080/api/auth/login
```

Access a protected route:

```sh
TOKEN=... # token from login
curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/users/me
```

## Security Notes

- Secure headers, CSP nonce, and rate limiting enabled.
- JWT authentication with BCrypt hashed passwords.
- Integration tests cover IDOR and input sanitization.
