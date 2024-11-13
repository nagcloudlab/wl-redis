## Redis Exporter

./redis_exporter --redis.addr=redis://localhost:6379

## Prometheus

```yaml
scrape_configs:
  - job_name: "redis"
    static_configs:
      - targets: ["localhost:9121"]
```

prometheus --config.file=/usr/local/etc/prometheus.yml

## Grafana

import dashboard: 763

## Alert

Prometheus uses Alertmanager to handle and route alerts to different notification channels (like email, Slack, etc.).

Install Alertmanager
If Alertmanager is not already installed, you can download and install it:

bash
Copy code

# Download Alertmanager

wget https://github.com/prometheus/alertmanager/releases/download/v0.22.2/alertmanager-0.22.2.linux-amd64.tar.gz
tar -xzf alertmanager-0.22.2.linux-amd64.tar.gz
cd alertmanager-0.22.2.linux-amd64

# Run Alertmanager

./alertmanager --config.file=alertmanager.yml
Configure Alertmanager (alertmanager.yml)
Create or edit the alertmanager.yml file with your notification preferences.

Example: Configuring Alertmanager to send alerts via email.

yaml
Copy code
global:
smtp_smarthost: 'smtp.example.com:587'
smtp_from: 'your-email@example.com'
smtp_auth_username: 'your-email@example.com'
smtp_auth_password: 'your-password'

route:
receiver: 'email-notifications'

receivers:

- name: 'email-notifications'
  email_configs: - to: 'alert-recipient@example.com'
  smtp_smarthost: SMTP server address for sending emails.
  smtp_from: The email sender.
  receivers: Define the recipients and notification channels.

4. Integrate Prometheus with Alertmanager
   In prometheus.yml, specify the Alertmanager configuration.

yaml
Copy code
alerting:
alertmanagers: - static_configs: - targets: ['localhost:9093'] # Replace with Alertmanager address
targets: The address where Alertmanager is running.
