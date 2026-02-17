# 🛡️ Sentinel

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Python](https://img.shields.io/badge/Python-3.11%2B-3776AB?style=for-the-badge&logo=python&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Kubernetes](https://img.shields.io/badge/Kubernetes-326CE5?style=for-the-badge&logo=kubernetes&logoColor=white)

Sentinel is a cutting-edge **Self-Healing Infrastructure Platform** designed to autonomously monitor and remediate Kubernetes environments. By leveraging AI Agents, Sentinel analyzes logs, consults knowledge bases via RAG, and suggests precise remediation actions, ensuring robust and resilient system operations with human-in-the-loop approval.

## Architecture

*   **Project Mission:** To build an autonomous observability and remediation platform where the Core (Java) manages security and orchestration, the Brain (Python) handles AI logic, and the Frontend (React) provides a comprehensive monitoring dashboard.
*   **Architecture Rules:** Strict Hexagonal Architecture for the Java Backend (Spring Boot 3.2+, JDK 21) ensuring security and clean boundaries. The AI Service (Python FastAPI) uses LangChain/LangGraph for intelligent decision-making but never executes infrastructure changes directly—it only suggests them.
*   **Infrastructure:** A fully containerized environment using Docker Compose to orchestrate services including PostgreSQL, ChromaDB (Vector DB), Redis, and the core application components.
*   **Self-Healing Flow:** A critical loop where Java receives alerts, forwards them to the Python Brain for RAG-based analysis, and presents a remediation plan (e.g., "Restart Pod X") on the Dashboard for user approval before execution via the Kubernetes API.

## Project Structure

```ascii
.
├── backend-core/
├── ai-brain/
└── frontend-dashboard/
```

## Quick Start

🚧 Coming Soon - Work in Progress
