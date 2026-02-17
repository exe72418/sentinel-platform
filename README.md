# 🛡️ Sentinel

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Python](https://img.shields.io/badge/Python-3.11%2B-3776AB?style=for-the-badge&logo=python&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## Architecture

*   **Core Backend (Java Spring Boot 3.2+):** Hexagonal architecture handling users, security (JWT), and orchestration. It is the authoritative component that executes K8s commands after approval.
*   **AI "Brain" (Python FastAPI):** An intelligent service using LangChain and RAG (ChromaDB) to analyze logs and propose remediation actions, communicating only via REST with the Backend.
*   **Frontend & Infrastructure:** A React-based dashboard for observability and human-in-the-loop approvals, running on a Docker/Kubernetes infrastructure designed for self-healing.

## Quick Start

Coming soon
