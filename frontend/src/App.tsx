import { useEffect, useState } from "react";
import "./App.css";

type StatsSummary = {
  label: string;
  count: number;
};

type ServiceRequest = {
  id: number;
  externalId: string;
  createdDate: string | null;
  closedDate: string | null;
  borough: string | null;
  geographicBorough: string | null;
  category: string | null;
  requestType: string | null;
  status: string | null;
  description: string | null;
  street: string | null;
  latitude: number | null;
  longitude: number | null;
  source: string | null;
  importedAt: string | null;
};

type PageResponse<T> = {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
};

function App() {
  const [statusStats, setStatusStats] = useState<StatsSummary[]>([]);
  const [boroughStats, setBoroughStats] = useState<StatsSummary[]>([]);
  const [monthlyTrends, setMonthlyTrends] = useState<StatsSummary[]>([]);
  const [recentRequests, setRecentRequests] = useState<ServiceRequest[]>([]);
  const [totalRequests, setTotalRequests] = useState(0);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    async function loadDashboardData() {
      try {
        const statusResponse = await fetch("http://localhost:8080/api/stats/by-status");
        const boroughResponse = await fetch("http://localhost:8080/api/stats/by-borough");
        const trendsResponse = await fetch("http://localhost:8080/api/stats/monthly-trends");
        const requestsResponse = await fetch("http://localhost:8080/api/requests?page=0&size=10");

        if (
          !statusResponse.ok ||
          !boroughResponse.ok ||
          !trendsResponse.ok ||
          !requestsResponse.ok
        ) {
          throw new Error("Failed to load dashboard data");
        }

        const statusData: StatsSummary[] = await statusResponse.json();
        const boroughData: StatsSummary[] = await boroughResponse.json();
        const trendsData: StatsSummary[] = await trendsResponse.json();
        const requestsData: PageResponse<ServiceRequest> = await requestsResponse.json();

        setStatusStats(statusData);
        setBoroughStats(boroughData);
        setMonthlyTrends(trendsData);
        setRecentRequests(requestsData.content);
        setTotalRequests(requestsData.totalElements);
      } catch (err) {
        setError("Could not connect to the backend API.");
      } finally {
        setLoading(false);
      }
    }

    loadDashboardData();
  }, []);

  const openRequests = getCountByLabel(statusStats, "OPEN");
  const closedRequests = getCountByLabel(statusStats, "CLOSED");
  const latestMonth = monthlyTrends.length > 0 ? monthlyTrends[monthlyTrends.length - 1] : null;

  return (
    <main className="dashboard">
      <section className="hero">
        <p className="eyebrow">Montreal Service Intelligence Platform</p>
        <h1>311 Service Request Dashboard</h1>
        <p className="subtitle">
          Track service request volume, open cases, borough activity, and recent
          city issues from a Spring Boot and PostgreSQL backend.
        </p>
      </section>

      {loading && <p className="message">Loading dashboard data...</p>}

      {error && <p className="error">{error}</p>}

      {!loading && !error && (
        <>
          <section className="kpi-grid">
            <div className="kpi-card">
              <p>Total Requests</p>
              <strong>{totalRequests}</strong>
            </div>

            <div className="kpi-card">
              <p>Open Requests</p>
              <strong>{openRequests}</strong>
            </div>

            <div className="kpi-card">
              <p>Closed Requests</p>
              <strong>{closedRequests}</strong>
            </div>

            <div className="kpi-card">
              <p>Latest Month</p>
              <strong>{latestMonth ? latestMonth.count : 0}</strong>
              <span>{latestMonth ? latestMonth.label : "No data"}</span>
            </div>
          </section>

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
              <h2>Top Boroughs</h2>
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

          <section className="table-card">
            <div className="table-header">
              <div>
                <h2>Recent Service Requests</h2>
                <p>Showing the first 10 requests from the backend API.</p>
              </div>
            </div>

            <div className="table-wrapper">
              <table>
                <thead>
                  <tr>
                    <th>External ID</th>
                    <th>Borough</th>
                    <th>Category</th>
                    <th>Status</th>
                    <th>Created Date</th>
                  </tr>
                </thead>

                <tbody>
                  {recentRequests.map((request) => (
                    <tr key={request.id}>
                      <td>{request.externalId}</td>
                      <td>{request.borough ?? "N/A"}</td>
                      <td>{request.category ?? "N/A"}</td>
                      <td>
                        <span className={`status-pill ${getStatusClass(request.status)}`}>
                          {request.status ?? "UNKNOWN"}
                        </span>
                      </td>
                      <td>{formatDate(request.createdDate)}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </section>
        </>
      )}
    </main>
  );
}

function getCountByLabel(stats: StatsSummary[], label: string) {
  const found = stats.find((item) => item.label === label);
  return found ? found.count : 0;
}

function formatDate(value: string | null) {
  if (!value) {
    return "N/A";
  }

  return value.substring(0, 10);
}

function getStatusClass(status: string | null) {
  if (status === "OPEN") {
    return "status-open";
  }

  if (status === "CLOSED") {
    return "status-closed";
  }

  return "status-other";
}

export default App;