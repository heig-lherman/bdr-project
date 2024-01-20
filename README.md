<h3 align="center">
  Project ChooChoo
</h3>

<p align="center">
  Track your train trips and know when you've seen it all!
</p>

---

## Docker build & run

Copy `docker/.env.example` to `docker/.env` and fill in the following values:

- `FRONTEND_GEOPS_API_KEY`: The API key for the [GeOps API](https://developer.geops.io/)
- `BACKEND_JWT_SECRET_KEY`: A 512-bit secret key for JWT signing
  (generate one with `openssl rand -base64 172 | tr -d '\n'`)

Then run `docker compose up --build` in the `docker` directory to build and start the frontend and backed.

The backend will initialize its database on first run, so it may take a few seconds to start up
(this also includes the synchronization of reference data from the SBB OpenData, which takes around 30 seconds).

Using the default docker compose configuration, the frontend will be available at http://localhost:3000
and the backend at http://localhost:8080/api.

## Local development

### Frontend

The frontend is build using [Nuxt 3](https://nuxt.com), to start a development servers run the following
commands in the `frontend` directory:

```bash
pnpm install
pnpm run dev
```

### Backend

The backend is a simple [Spring Boot](https://spring.io/projects/spring-boot/) application, an IntelliJ run
configuration with sample environment variables is available in `.run/ChooChooApplication.run.xml` which starts the
backend using the appropriate settings for the frontend development server.

Using gradle, the backend can be started with the following commands:

```bash
export DATABASE_URL="{database url}"
export JWT_SECRET_KEY="{512-bit base64 encoded jwt secret key}"

./gradlew bootRun
```
