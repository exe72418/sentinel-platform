#!/bin/bash

# 1. Levantar Infraestructura (Base de datos y Vector Store)
echo "🚀 Levantando Docker Containers..."
docker compose up -d postgres chromadb

# Esperar unos segundos para asegurar que la DB esté lista
sleep 3

# 2. Abrir Ventana 1: Backend Java
echo "☕ Iniciando Backend Java..."
gnome-terminal --title="Backend Java" -- bash -c "cd backend-core && mvn spring-boot:run; exec bash" &

# 3. Abrir Ventana 2: AI Brain
echo "🧠 Iniciando AI Brain..."
gnome-terminal --title="AI Brain" -- bash -c "cd ai-brain && source venv/bin/activate && uvicorn src.main:app --reload --port 8001; exec bash" &

# 4. Abrir Ventana 3: Frontend React
echo "⚛️ Iniciando Frontend..."
gnome-terminal --title="Frontend Dashboard" -- bash -c "cd frontend-dashboard && npm run dev; exec bash" &

echo "✅ Todo corriendo en 3 ventanas separadas."
