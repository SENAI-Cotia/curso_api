#!/bin/bash
set -euo pipefail

BUCKET=$1
APP_DIR=/home/ec2-user/app
JAR=$APP_DIR/api.jar
LOG=$APP_DIR/app.log
ENV_FILE=/home/ec2-user/.env

mkdir -p "$APP_DIR"

echo "==> Baixando JAR do S3..."
aws s3 cp "s3://$BUCKET/api.jar" "$JAR"

# Variáveis de banco ficam em ~/.env na EC2 (configurado uma única vez)
# Exemplo do arquivo:
#   export DB_HOST=seu-rds-endpoint.rds.amazonaws.com
#   export DB_NAME=curso_api
#   export DB_USER=admin
#   export DB_PASSWORD=senha
if [ -f "$ENV_FILE" ]; then
  # shellcheck source=/dev/null
  source "$ENV_FILE"
fi

echo "==> Parando versão anterior..."
pkill -f "api.jar" || true
sleep 2

echo "==> Iniciando nova versão..."
nohup java -jar "$JAR" \
  --spring.profiles.active=prod \
  > "$LOG" 2>&1 &

echo "==> Deploy concluído. PID: $! | Log: $LOG"
