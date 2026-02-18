import chromadb
import uuid
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def seed_knowledge_base():
    try:
        # Use PersistentClient at ./chroma_db as requested
        chroma_client = chromadb.PersistentClient(path="./chroma_db")
        logger.info("Connected to PersistentClient at ./chroma_db")

        collection_name = "k8s_solutions"

        # Try to delete existing collection to avoid duplicates
        try:
            chroma_client.delete_collection(name=collection_name)
            logger.info(f"Deleted existing collection '{collection_name}'")
        except ValueError:
            logger.info(f"Collection '{collection_name}' not found, skipping delete.")

        # Create fresh collection
        collection = chroma_client.create_collection(name=collection_name)
        logger.info(f"Created fresh collection '{collection_name}'")

        # Knowledge Base Data
        cases = [
            {"error": "CrashLoopBackOff", "solution": "Revisar logs con kubectl logs y liveness probes."},
            {"error": "OOMKilled", "solution": "Aumentar resources.limits.memory en el deployment."},
            {"error": "ImagePullBackOff", "solution": "Verificar credenciales del registry y nombre de imagen."},
            {"error": "502 Bad Gateway", "solution": "Verificar servicio upstream y puerto del container."},
            {"error": "PVC Pending", "solution": "Verificar StorageClass y capacidad del nodo."}
        ]

        # Insert data
        for case in cases:
            unique_id = str(uuid.uuid4())
            collection.add(
                documents=[case["error"]], # Embedding will be generated based on error description
                metadatas=[{"solution": case["solution"]}],
                ids=[unique_id]
            )
            logger.info(f"Inserted: {case['error']}")

        print("\n✅ Knowledge base seeded successfully!")

    except Exception as e:
        logger.error(f"Failed to seed knowledge base: {e}")
        print("\n❌ Seeding failed.")

if __name__ == "__main__":
    seed_knowledge_base()
