#!/usr/bin/env sh
set -eu

DEPLOY_DIR="${DEPLOY_DIR:-$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)}"
COMPOSE_FILE="${COMPOSE_FILE:-$DEPLOY_DIR/docker-compose.prod.yml}"

if [ ! -f "$DEPLOY_DIR/.env" ]; then
  echo "Missing $DEPLOY_DIR/.env. Copy $DEPLOY_DIR/.env.example and fill in the production values first."
  exit 1
fi

: "${APP_IMAGE:?APP_IMAGE is required}"
: "${GHCR_USERNAME:?GHCR_USERNAME is required}"
: "${GHCR_TOKEN:?GHCR_TOKEN is required}"

mkdir -p "$DEPLOY_DIR/config" "$DEPLOY_DIR/logs" "$DEPLOY_DIR/runtime"

if docker compose version >/dev/null 2>&1; then
  compose() {
    docker compose "$@"
  }
else
  compose() {
    docker-compose "$@"
  }
fi

printf '%s' "$GHCR_TOKEN" | docker login ghcr.io -u "$GHCR_USERNAME" --password-stdin

cd "$DEPLOY_DIR"
compose --env-file .env -f "$COMPOSE_FILE" up -d mysql redis minio
APP_IMAGE="$APP_IMAGE" compose --env-file .env -f "$COMPOSE_FILE" pull yuan-backend-admin
APP_IMAGE="$APP_IMAGE" compose --env-file .env -f "$COMPOSE_FILE" up -d yuan-backend-admin
docker image prune -f >/dev/null 2>&1 || true
