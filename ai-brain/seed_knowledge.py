import chromadb
from chromadb.config import Settings
import logging

# Configure logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

def seed_knowledge_base():
    try:
        # Connect to ChromaDB (running locally via Docker on port 8000)
        chroma_client = chromadb.HttpClient(host='localhost', port=8000)
        logger.info("Connected to ChromaDB")

        # Get or create collection
        collection_name = "k8s_solutions"

        # Reset: Delete existing collection if it exists
        try:
            chroma_client.delete_collection(name=collection_name)
            logger.info(f"Deleted existing collection '{collection_name}'")
        except Exception:
            pass # Collection might not exist

        collection = chroma_client.get_or_create_collection(name=collection_name)
        logger.info(f"Created fresh collection '{collection_name}'")

        # Knowledge Base Data
        errors = [
            "CrashLoopBackOff",
            "OOMKilled",
            "ImagePullBackOff",
            "502 Bad Gateway",
            "PVC Pending",
            "NodeNotReady",
            "CreateContainerConfigError",
            "ErrImagePull"
        ]

        solutions = [
            "Revisar logs con 'kubectl logs', verificar configuración de liveness/readiness probes, y asegurar que el comando de inicio sea correcto.",
            "El contenedor excedió el límite de memoria. Aumentar 'resources.limits.memory' en el Deployment o optimizar el consumo de la aplicación.",
            "Verificar el nombre de la imagen, etiquetas (tags), y asegurar que las credenciales del registry (ImagePullSecrets) sean correctas.",
            "Verificar que los Pods del servicio upstream estén en estado Running y pasando sus health checks. Revisar configuración del Ingress/Service.",
            "Chequear que exista la StorageClass solicitada, que haya capacidad disponible en el backend de almacenamiento, y que el modo de acceso sea compatible.",
            "Verificar estado del kubelet en el nodo, espacio en disco, y conectividad de red. Reiniciar el nodo si es necesario.",
            "Revisar configuración de ConfigMaps o Secrets montados. Es posible que falte una clave requerida o el archivo no exista.",
            "Error al descargar la imagen. Verificar conectividad de red del nodo hacia el registry y que la imagen exista."
        ]

        ids = [str(i) for i in range(1, len(errors) + 1)]
        metadatas = [{"error_type": error, "solution": solution} for error, solution in zip(errors, solutions)]
        documents = [f"{error}: {solution}" for error, solution in zip(errors, solutions)]

        # Add data to collection
        collection.add(
            documents=documents,
            metadatas=metadatas,
            ids=ids
        )

        logger.info(f"Successfully seeded {len(documents)} knowledge entries into ChromaDB.")

    except Exception as e:
        logger.error(f"Failed to seed knowledge base: {e}")

if __name__ == "__main__":
    seed_knowledge_base()
