# PROYECTO: Sentinel - Self-Healing Infrastructure Platform

## 1. Misión del Proyecto
Crear una plataforma de observabilidad y remediación autónoma para Kubernetes.
- **Core:** Java Spring Boot (Gestión de usuarios, Permisos, Auditoría).
- **Brain:** Python FastAPI + LangChain (Análisis de logs, decisiones RAG).
- **Frontend:** React + Vite + Tailwind (Dashboard de monitoreo).
- **Infra:** Docker Compose & Kubernetes (Minikube).

## 2. Reglas de Arquitectura (ESTRICTAS)

### Backend (Java - Spring Boot 3.2+)
- **JDK:** 21.
- **Arquitectura:** Hexagonal (Ports & Adapters).
- **Persistencia:** Spring Data JPA con PostgreSQL.
- **Seguridad:** Spring Security con JWT. NADA pasa sin token.
- **Comunicación:** Expone REST API para el Frontend y cliente REST para hablar con el Brain (Python).
- **Estilo:** Usa `record` para DTOs. Usa Lombok. NUNCA expongas Entidades JPA directamente en el Controller.

### AI Service "Brain" (Python - FastAPI)
- **Versión:** 3.11+.
- **Framework:** FastAPI (Asíncrono).
- **AI Logic:** LangChain / LangGraph.
- **Responsabilidad:**
    1. Recibe logs o alertas del Backend Java.
    2. Consulta Vector DB (ChromaDB) para buscar soluciones en "Manuales".
    3. Decide una acción (Tool Call).
    4. Devuelve la decisión al Java Backend (JSON estructurado).
    - **NOTA:** Python NUNCA ejecuta cambios en K8s directamente. Solo "sugiere". Java aprueba y ejecuta.

### Frontend (React)
- **Stack:** Vite, TypeScript, TailwindCSS, ShadcnUI (si es posible).
- **Estado:** Zustand o React Query.
- **Diseño:** Dark Mode por defecto (Estilo "Hacker/DevOps").

## 3. Infraestructura
- Todo debe tener su `Dockerfile` optimizado (Multi-stage build).
- `docker-compose.yml` debe levantar:
    - Postgres (BD Principal)
    - ChromaDB (Vector DB)
    - Redis (Cache de IA)
    - Backend (Java)
    - Brain (Python)
    - Frontend (React)

## 4. Flujo Crítico "Self-Healing"
1. Java recibe Webhook de Alerta (ej. Prometheus).
2. Java envía alerta a Python.
3. Python analiza, busca en RAG y responde: "Recomiendo reiniciar Pod X".
4. Java guarda la recomendación.
5. Usuario (Frontend) ve la alerta y hace clic en "Aprobar".
6. Java ejecuta `kubectl delete pod X` (usando librería Fabric8 Kubernetes Client).
