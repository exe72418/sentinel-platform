#!/bin/bash

# 1. Start Infrastructure (Databases only) in background
echo "🚀 Starting Infrastructure (Postgres, ChromaDB)..."
docker compose up -d postgres chromadb

# 2. Open 3 Terminal Tabs for Microservices
echo "🖥️  Opening Development Terminals..."

gnome-terminal --tab --title="Backend Java" -- bash -c "cd backend-core && mvn spring-boot:run; exec bash" \
               --tab --title="AI Brain" -- bash -c "cd ai-brain && python3 -m venv venv && source venv/bin/activate && pip install -r requirements.txt && uvicorn src.main:app --reload --port 8001; exec bash" \
               --tab --title="Frontend" -- bash -c "cd frontend-dashboard && npm install && npm run dev; exec bash"

echo "✅ Development environment initialized!"
