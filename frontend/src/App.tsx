import { useEffect, useState } from "react";
import "./App.css";

type StatsSummary = {
  label: string;
  count: number;
};

function App() {
  const [statusStats, setStatusStats] = useState<StatsSummary[]>([]);
  const [boroughStats, setBoroughStats] = useState<StatsSummary[]>([]);
  const [monthlyTrends, setMonthlyTrends] = useState<StatsSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadDashboardData() {
      try {
        const statusResponse = await fetch("http://localhost:8080/api/stats/by-status");
        const boroughResponse = await fetch("http://localhost:8080/api/stats/by-borough");
        const trendsResponse = await fetch("http://localhost:8080/api/stats/monthly-trends");

        if (!statusResponse.ok || !boroughResponse.ok || !trendsResponse.ok) {
          throw new Error("Failed to load dashboard data");
        }

        const statusData = await statusResponse.json();
        const boroughData = await boroughResponse.json();
        const trendsData = await trendsResponse.json();

        setStatusStats(statusData);
        setBoroughStats(boroughData);
        setMonthlyTrends(trendsData);
      } catch (err) {
        setError("Could not connect to the backend API.");
      } finally {
        setLoading(false);
      }
    }

    loadDashboardData();
  }, []);

  return (
    <main className="dashboard">
      <section className="hero">
        <p className="eyebrow">Montreal Service Intelligence Platform</p>
        <h1>311 Service Request Dashboard</h1>
        <p className="subtitle">
          A backend-powered dashboard for analyzing service requests by status,
          borough, category, and monthly trends.
        </p>
      </section>

      {loading && <p className="message">Loading dashboard data...</p>}

      {error && <p className="error">{error}</p>}

      {!loading && !error && (
        <section className="grid">
          <div className="card">
            <h2>Requests by Status</h2>
            {statusStats.map((item) => (
              <div className="stat-row" key={item.label}>
                <span>{item.label}</span>
                <strong>{item.count}</strong>
              </div>
            ))}
          </div>

          <div className="card">
            <h2>Requests by Borough</h2>
            {boroughStats.map((item) => (
              <div className="stat-row" key={item.label}>
                <span>{item.label}</span>
                <strong>{item.count}</strong>
              </div>
            ))}
          </div>

          <div className="card">
            <h2>Monthly Trends</h2>
            {monthlyTrends.map((item) => (
              <div className="stat-row" key={item.label}>
                <span>{item.label}</span>
                <strong>{item.count}</strong>
              </div>
            ))}
          </div>
        </section>
      )}
    </main>
  );
}

export default App;