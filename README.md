# Secure Spring API

This project provides a small Spring Boot REST API used for security testing.

## Running

```bash
./gradlew bootRun
```

## Usage

Health:
```bash
curl http://localhost:8080/api/health
```

Login and access protected route:
```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"user","password":"password"}' | jq -r .token)

curl -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/users/me
```

See [docs/SECURITY.md](docs/SECURITY.md) for security notes.
