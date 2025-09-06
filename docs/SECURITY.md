# Security Overview

This project models a minimal API for security testing. Threat modeling with STRIDE covers key endpoints:

- `/api/echo` – **Tampering/Information disclosure**: validates input and escapes output. Tested by property-based and fuzz tests.
- `/api/users/{id}` – **Elevation of privilege/Repudiation**: ensures users can access only their own data. Covered by integration tests.
- `/api/admin/stats` – **Spoofing/Elevation of privilege**: restricted to admins. Tested via integration tests.

Refer to unit and integration tests for enforcement examples.
