from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import chromadb
from chromadb.config import Settings
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

app = FastAPI(title="Sentinel AI Brain")

# Initialize ChromaDB Client
try:
    chroma_client = chromadb.HttpClient(host='chromadb', port=8000)
    collection = chroma_client.get_or_create_collection(name="k8s_solutions")
    logger.info("Connected to ChromaDB and retrieved collection 'k8s_solutions'.")
except Exception as e:
    logger.error(f"Failed to connect to ChromaDB: {e}")
    # In a real scenario, we might want to fail fast, but for dev we'll log
    chroma_client = None
    collection = None

class Alert(BaseModel):
    id: int | None = None
    podName: str
    errorMessage: str
    severity: str
    status: str | None = None

class AnalysisResult(BaseModel):
    original_alert_id: int | None
    analysis: str
    suggested_action: str

@app.post("/analyze", response_model=AnalysisResult)
async def analyze_alert(alert: Alert):
    logger.info(f"Received alert for analysis: {alert}")

    recommendation = "Manual investigation required."
    analysis_text = "No specific pattern matched in knowledge base."

    # Simple keyword-based logic for now (mocking RAG)
    error_msg = alert.errorMessage.lower()

    if "oom" in error_msg or "out of memory" in error_msg:
        recommendation = "Increase memory limits for the pod."
        analysis_text = "Detected OutOfMemoryError. The pod is consuming more RAM than allocated."
    elif "crashloopbackoff" in error_msg:
        recommendation = "Check application logs for startup failures."
        analysis_text = "Pod is in CrashLoopBackOff. Likely a configuration issue or missing dependency."
    elif "imagepullbackoff" in error_msg:
        recommendation = "Verify image name and pull secrets."
        analysis_text = "Failed to pull container image. Check registry authentication."

    # Future: Use ChromaDB to find similar past issues
    if collection:
        try:
            # This is a placeholder for actual embedding generation and query
            # results = collection.query(query_texts=[alert.errorMessage], n_results=1)
            pass
        except Exception as e:
            logger.error(f"ChromaDB query failed: {e}")

    return AnalysisResult(
        original_alert_id=alert.id,
        analysis=analysis_text,
        suggested_action=recommendation
    )

@app.get("/health")
def health_check():
    return {"status": "ok", "chromadb": "connected" if chroma_client else "disconnected"}
