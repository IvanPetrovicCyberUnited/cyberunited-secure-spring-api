# Security Notes

## STRIDE Analysis

### /api/echo
- **Spoofing:** none
- **Tampering:** input validated and HTML-escaped
- **Repudiation:** correlation ID logged
- **Information Disclosure:** sanitized output
- **Denial of Service:** rate limited
- **Elevation of Privilege:** none

### /api/users/{id}
- **Spoofing:** JWT authentication
- **Tampering:** IDs validated
- **Repudiation:** correlation ID
- **Information Disclosure:** access restricted to own ID
- **Denial of Service:** rate limited
- **Elevation of Privilege:** role checks prevent access

### /api/admin/stats
- **Spoofing:** JWT authentication
- **Tampering:** request validation
- **Repudiation:** correlation ID
- **Information Disclosure:** admin only
- **Denial of Service:** rate limited
- **Elevation of Privilege:** ROLE_ADMIN required

See integration tests in `src/test/java` for examples.
