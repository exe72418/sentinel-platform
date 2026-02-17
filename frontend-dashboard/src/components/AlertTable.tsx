import { Alert } from '../types';
import { clsx } from 'clsx';
import { Bot, AlertTriangle, Info, ShieldAlert } from 'lucide-react';

interface AlertTableProps {
  alerts: Alert[];
  onAlertClick: (alert: Alert) => void;
}

export function AlertTable({ alerts, onAlertClick }: AlertTableProps) {
  const getSeverityIcon = (severity: string) => {
    switch (severity.toUpperCase()) {
      case 'CRITICAL': return <ShieldAlert className="w-5 h-5 text-sentinel-red" />;
      case 'WARNING': return <AlertTriangle className="w-5 h-5 text-yellow-500" />;
      default: return <Info className="w-5 h-5 text-blue-500" />;
    }
  };

  const getSeverityBadge = (severity: string) => {
    const base = "px-2 py-1 rounded-full text-xs font-bold uppercase tracking-wide flex items-center gap-1 w-fit";
    switch (severity.toUpperCase()) {
      case 'CRITICAL': return clsx(base, "bg-sentinel-red/20 text-sentinel-red border border-sentinel-red/30");
      case 'WARNING': return clsx(base, "bg-yellow-500/20 text-yellow-500 border border-yellow-500/30");
      default: return clsx(base, "bg-blue-500/20 text-blue-500 border border-blue-500/30");
    }
  };

  return (
    <div className="overflow-x-auto rounded-lg border border-gray-700 bg-sentinel-card shadow-lg">
      <table className="w-full text-left text-sm text-gray-400">
        <thead className="bg-gray-800 text-xs uppercase text-gray-300 font-semibold tracking-wider">
          <tr>
            <th className="px-6 py-4">Status</th>
            <th className="px-6 py-4">Timestamp</th>
            <th className="px-6 py-4">Pod</th>
            <th className="px-6 py-4">Error</th>
            <th className="px-6 py-4">Analysis</th>
            <th className="px-6 py-4 text-right">Action</th>
          </tr>
        </thead>
        <tbody className="divide-y divide-gray-700">
          {alerts.map((alert) => (
            <tr
                key={alert.id}
                className="hover:bg-gray-800/50 transition-colors cursor-pointer group"
                onClick={() => onAlertClick(alert)}
            >
              <td className="px-6 py-4 whitespace-nowrap">
                {getSeverityIcon(alert.severity)}
              </td>
              <td className="px-6 py-4 font-mono text-gray-500">
                {new Date(alert.createdAt).toLocaleTimeString()}
              </td>
              <td className="px-6 py-4 font-medium text-white group-hover:text-sentinel-accent transition-colors">
                {alert.podName}
              </td>
              <td className="px-6 py-4 max-w-xs truncate text-gray-300" title={alert.errorMessage}>
                {alert.errorMessage}
              </td>
              <td className="px-6 py-4">
                {alert.aiAnalysis ? (
                    <span className="flex items-center gap-1 text-sentinel-green text-xs font-mono">
                        <Bot className="w-3 h-3" /> Ready
                    </span>
                ) : (
                    <span className="text-gray-600 text-xs italic">Pending...</span>
                )}
              </td>
              <td className="px-6 py-4 text-right">
                <button className="text-sentinel-accent hover:text-white text-xs font-bold uppercase tracking-wide">
                    Details &rarr;
                </button>
              </td>
            </tr>
          ))}
          {alerts.length === 0 && (
            <tr>
                <td colSpan={6} className="px-6 py-12 text-center text-gray-500 italic">
                    No active alerts. System operational.
                </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
