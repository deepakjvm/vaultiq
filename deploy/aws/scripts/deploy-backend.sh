#!/usr/bin/env bash
set -euo pipefail

APP_DIR="/opt/vaultiq"
BACKUP_DIR="/opt/vaultiq/backups"
NEW_ARTIFACT="/tmp/vaultiq.jar"
ACTIVE_JAR="$APP_DIR/vaultiq.jar"
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

echo "==> [VaultIQ Deployment] Starting deployment at $(date)..."

# Ensure directories exist
sudo mkdir -p "$APP_DIR" "$BACKUP_DIR"
sudo chown -R ubuntu:ubuntu "$APP_DIR"

if [ ! -f "$NEW_ARTIFACT" ]; then
    echo "ERROR: New artifact not found at $NEW_ARTIFACT"
    exit 1
fi

# Backup existing artifact if present
if [ -f "$ACTIVE_JAR" ]; then
    echo "==> Backing up current artifact to $BACKUP_DIR/vaultiq_${TIMESTAMP}.jar"
    cp "$ACTIVE_JAR" "$BACKUP_DIR/vaultiq_${TIMESTAMP}.jar"
fi

# Replace active jar
echo "==> Installing new artifact..."
mv "$NEW_ARTIFACT" "$ACTIVE_JAR"
chmod 500 "$ACTIVE_JAR"

# Restart systemd service
echo "==> Restarting vaultiq.service..."
sudo systemctl daemon-reload
sudo systemctl restart vaultiq.service

# Health check verification loop (wait up to 60s)
echo "==> Verifying service health..."
MAX_ATTEMPTS=12
ATTEMPT=0
HEALTH_URL="http://127.0.0.1:8180/api/users/login"

while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
    ATTEMPT=$((ATTEMPT + 1))
    HTTP_CODE=$(curl -s -o /dev/null -w "%{http_code}" "$HEALTH_URL" || true)

    # 200, 400, 401, or 405 indicates Spring Boot is running and responding
    if [[ "$HTTP_CODE" =~ ^(200|400|401|403|405)$ ]]; then
        echo "==> Deployment successful! Service is healthy (HTTP $HTTP_CODE)."
        exit 0
    fi

    echo "Health check attempt $ATTEMPT/$MAX_ATTEMPTS (HTTP $HTTP_CODE)... waiting 5s"
    sleep 5
done

echo "ERROR: Health check failed after $MAX_ATTEMPTS attempts."
if [ -f "$BACKUP_DIR/vaultiq_${TIMESTAMP}.jar" ]; then
    echo "==> Rolling back to previous artifact..."
    cp "$BACKUP_DIR/vaultiq_${TIMESTAMP}.jar" "$ACTIVE_JAR"
    sudo systemctl restart vaultiq.service
fi
exit 1
