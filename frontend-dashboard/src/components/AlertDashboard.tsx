import { Alert } from '../types';
import { clsx } from 'clsx';
import { ShieldCheck, Activity, Server, AlertOctagon } from 'lucide-react';

interface AlertDashboardProps {
  alerts: Alert[];
}

export function AlertDashboard({ alerts }: AlertDashboardProps) {
  const criticalCount = alerts.filter(a => a.severity === 'CRITICAL').length;
  const warningCount = alerts.filter(a => a.severity === 'WARNING').length;
  const totalAlerts = alerts.length;

  const cards = [
    {
      title: 'Active Alerts',
      value: totalAlerts,
      icon: <Activity className="w-8 h-8 text-blue-500" />,
      color: 'border-blue-500/30 bg-blue-500/10 text-blue-500'
    },
    {
      title: 'Critical Issues',
      value: criticalCount,
      icon: <AlertOctagon className="w-8 h-8 text-sentinel-red" />,
      color: 'border-sentinel-red/30 bg-sentinel-red/10 text-sentinel-red'
    },
    {
      title: 'Warnings',
      value: warningCount,
      icon: <ShieldCheck className="w-8 h-8 text-yellow-500" />,
      color: 'border-yellow-500/30 bg-yellow-500/10 text-yellow-500'
    },
    {
      title: 'System Status',
      value: 'Online',
      icon: <Server className="w-8 h-8 text-sentinel-green" />,
      color: 'border-sentinel-green/30 bg-sentinel-green/10 text-sentinel-green'
    }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
      {cards.map((card, index) => (
        <div
            key={index}
            className={clsx(
                "rounded-xl border p-6 flex items-center justify-between shadow-lg backdrop-blur-sm transition-transform hover:-translate-y-1",
                card.color
            )}
        >
          <div>
            <h3 className="text-sm font-medium uppercase tracking-wider opacity-70 mb-1">{card.title}</h3>
            <p className="text-3xl font-bold font-mono">{card.value}</p>
          </div>
          <div className="p-3 rounded-full bg-white/5">
            {card.icon}
          </div>
        </div>
      ))}
    </div>
  );
}
