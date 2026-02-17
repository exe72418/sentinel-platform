import { useState, useEffect } from 'react';
import axios from 'axios';
import { Alert } from './types';
import { AlertDashboard } from './components/AlertDashboard';
import { AlertTable } from './components/AlertTable';
import { AIInsightsPanel } from './components/AIInsightsPanel';
import { Shield } from 'lucide-react';

function App() {
  const [alerts, setAlerts] = useState<Alert[]>([]);
  const [selectedAlert, setSelectedAlert] = useState<Alert | null>(null);

  useEffect(() => {
    const fetchAlerts = async () => {
      try {
        const response = await axios.get('/api/alerts');
        setAlerts(response.data);
      } catch (error) {
        console.error('Failed to fetch alerts:', error);
      }
    };

    fetchAlerts();
    const interval = setInterval(fetchAlerts, 5000);
    return () => clearInterval(interval);
  }, []);

  const handleAlertClick = (alert: Alert) => {
    setSelectedAlert(alert);
  };

  const handleClosePanel = () => {
    setSelectedAlert(null);
  };

  return (
    <div className="min-h-screen bg-sentinel-bg text-sentinel-text font-sans antialiased bg-[url('https://www.transparenttextures.com/patterns/carbon-fibre.png')]">
      <div className="fixed inset-0 bg-gradient-to-br from-sentinel-bg via-gray-900 to-black opacity-90 -z-10"></div>

      <header className="sticky top-0 z-40 bg-gray-900/80 backdrop-blur-md border-b border-gray-800 shadow-md">
        <div className="container mx-auto px-6 py-4 flex items-center justify-between">
          <div className="flex items-center gap-3">
            <Shield className="w-8 h-8 text-sentinel-green animate-pulse" />
            <h1 className="text-2xl font-bold tracking-tight text-white uppercase">
              Sentinel <span className="text-sentinel-green font-mono text-sm align-super">v1.0</span>
            </h1>
          </div>
          <div className="flex items-center gap-4 text-sm font-mono text-gray-400">
            <span className="bg-gray-800 px-3 py-1 rounded-full border border-gray-700 flex items-center gap-2">
                <span className="w-2 h-2 rounded-full bg-sentinel-green"></span>
                System: OPERATIONAL
            </span>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-6 py-8">
        <AlertDashboard alerts={alerts} />

        <div className="mt-8">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-bold text-white flex items-center gap-2">
                Latest Incidents
                <span className="text-xs font-normal text-gray-500 bg-gray-800 px-2 py-1 rounded ml-2">Live Feed</span>
            </h2>
          </div>

          <AlertTable alerts={alerts} onAlertClick={handleAlertClick} />
        </div>
      </main>

      {selectedAlert && (
        <AIInsightsPanel alert={selectedAlert} onClose={handleClosePanel} />
      )}
    </div>
  );
}

export default App;
