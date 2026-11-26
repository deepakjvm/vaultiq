#!/usr/bin/env bash
set -euo pipefail

echo "==> [VaultIQ Provisioning] Initializing Ubuntu EC2 host..."

# Update system packages
sudo apt-get update -y
sudo apt-get upgrade -y

# Install dependencies: Java 21/25 runtime, Nginx, curl, jq, certbot
sudo apt-get install -y openjdk-21-jdk nginx curl jq fail2ban ufw

# Set up application and config directories
sudo mkdir -p /opt/vaultiq /opt/vaultiq/backups /etc/vaultiq
sudo id -u ubuntu &>/dev/null || sudo useradd -r -s /bin/false ubuntu
sudo chown -R ubuntu:ubuntu /opt/vaultiq /etc/vaultiq

# Install systemd service unit
if [ -f "/tmp/vaultiq.service" ]; then
    echo "==> Installing systemd unit file..."
    sudo mv /tmp/vaultiq.service /etc/systemd/system/vaultiq.service
    sudo chmod 644 /etc/systemd/system/vaultiq.service
    sudo systemctl daemon-reload
    sudo systemctl enable vaultiq.service
fi

# Install Nginx reverse proxy configuration
if [ -f "/tmp/vaultiq.conf" ]; then
    echo "==> Installing Nginx site configuration..."
    sudo mv /tmp/vaultiq.conf /etc/nginx/sites-available/vaultiq.conf
    sudo ln -sf /etc/nginx/sites-available/vaultiq.conf /etc/nginx/sites-enabled/vaultiq.conf
    sudo rm -f /etc/nginx/sites-enabled/default
    sudo nginx -t && sudo systemctl reload nginx
fi

# Configure basic firewall rules
sudo ufw allow OpenSSH
sudo ufw allow 'Nginx Full'
sudo ufw --force enable

echo "==> EC2 initialization completed successfully."
