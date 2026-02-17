export interface Alert {
  id: number;
  podName: string;
  errorMessage: string;
  severity: 'CRITICAL' | 'WARNING' | 'INFO';
  status: string;
  createdAt: string;
  aiAnalysis?: string;
  suggestedAction?: string;
}
