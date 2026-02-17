import { Alert } from '../types';
import { X, Bot, ShieldAlert } from 'lucide-react';
import { clsx } from 'clsx';

interface AIInsightsPanelProps {
  alert: Alert | null;
  onClose: () => void;
}

export function AIInsightsPanel({ alert, onClose }: AIInsightsPanelProps) {
  if (!alert) return null;

  return (
    <div className="fixed inset-y-0 right-0 w-96 bg-gray-900 border-l border-gray-700 shadow-2xl p-6 transform transition-transform duration-300 ease-in-out z-50 overflow-y-auto">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-xl font-bold text-sentinel-green flex items-center gap-2">
          <Bot className="w-6 h-6" /> AI Insights
        </h2>
        <button onClick={onClose} className="text-gray-400 hover:text-white transition-colors">
          <X className="w-6 h-6" />
        </button>
      </div>

      <div className="space-y-6">
        <div className="bg-gray-800 p-4 rounded-lg border border-gray-700">
          <h3 className="text-sm font-semibold text-gray-400 mb-2 uppercase tracking-wider">Error Context</h3>
          <p className="text-white font-mono text-sm break-words">{alert.errorMessage}</p>
        </div>

        <div className="bg-gray-800 p-4 rounded-lg border border-gray-700 relative overflow-hidden">
          <div className="absolute top-0 right-0 p-2 opacity-10">
            <ShieldAlert className="w-16 h-16 text-sentinel-green" />
          </div>
          <h3 className="text-sm font-semibold text-sentinel-green mb-2 uppercase tracking-wider">Analysis</h3>
          <p className="text-gray-300 text-sm leading-relaxed">
            {alert.aiAnalysis || "Analysis pending..."}
          </p>
        </div>

        <div className="bg-gray-800 p-4 rounded-lg border border-sentinel-green/30 shadow-[0_0_15px_rgba(16,185,129,0.1)]">
          <h3 className="text-sm font-semibold text-sentinel-green mb-2 uppercase tracking-wider">Suggested Action</h3>
          <p className="text-white text-sm font-medium">
            {alert.suggestedAction || "Review logs manually."}
          </p>
        </div>

        <div className="pt-4 border-t border-gray-700">
            <button className="w-full bg-sentinel-green hover:bg-emerald-600 text-gray-900 font-bold py-2 px-4 rounded transition-colors shadow-lg shadow-emerald-500/20">
                Execute Remediation
            </button>
        </div>
      </div>
    </div>
  );
}
