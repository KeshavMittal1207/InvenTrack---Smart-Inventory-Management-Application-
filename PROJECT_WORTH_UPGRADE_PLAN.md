# InvenTrack — Deep Product/Engineering Upgrade Plan (for Internship & Job Shortlisting)

## 1) Current Architecture Snapshot (What’s already good)
- Microservices split for Auth, Inventory, Orders, Alerts, Seller, API Gateway, Config Server, and Service Discovery.
- Frontend dashboard already exists with inventory/order/alert views.
- Core inventory flows are implemented (add batch, reduce stock FEFO-style, summary updates).

This is a strong foundation for a fresher project. To multiply “shortlisting value,” the biggest wins are reliability, security, measurable business outcomes, and production-readiness.

---

## 2) Immediate Functional Gaps to Fix (High ROI, low-medium effort)

### A. Critical API/Frontend correctness
1. **Fix undefined params in frontend API layer**
   - `getExpiringSoon` uses `{ params: { days } }` without `days` in scope.
   - `getStockSummary` uses `{ params: { productId } }` without `productId` in scope.
   - Impact: hidden runtime failures in core dashboard/inventory flows.

2. **Standardize route naming and REST style**
   - Mixed naming (`/AllBatches`, `/getOrders`, `/placeOrder`) should be normalized to RESTful conventions (`GET /batches`, `GET /orders`, `POST /orders`).
   - Impact: cleaner API portfolio for interviews + better maintainability.

3. **Introduce global error response contract**
   - Current services throw raw `RuntimeException` messages.
   - Add `@ControllerAdvice` with unified error schema (`timestamp`, `code`, `message`, `details`, `traceId`).
   - Impact: enterprise feel + frontend can handle errors consistently.

### B. Inventory & Order business logic hardening
4. **Idempotency for order placement**
   - Add `Idempotency-Key` support so client retries don’t double-create orders.
   - Impact: real-world distributed systems thinking.

5. **Prevent race conditions in stock reduction**
   - Add optimistic locking (`@Version`) or DB-level locking for concurrent order writes.
   - Impact: avoids overselling in concurrent requests.

6. **Validate domain invariants at DTO level**
   - Bean validation (`@NotNull`, `@Positive`, `@Future`, etc.) for request DTOs.
   - Impact: fewer invalid states, stronger API contract.

---

## 3) Security Upgrades (Huge shortlisting signal)

1. **JWT verification at API Gateway**
   - Currently auth service issues token, but gateway-side central verification and route guards should be enforced.
2. **Role-based access control**
   - Roles: `ADMIN`, `INVENTORY_MANAGER`, `OPERATOR`, `VIEWER`.
   - Protect endpoints by role/authority.
3. **Refresh token + access token separation**
   - Add short-lived access tokens and refresh flow.
4. **Secret management**
   - Move secrets/keys from plain properties to environment variables or secret vault.
5. **Rate limiting and brute-force protection**
   - Gateway throttling + login attempt limits.

---

## 4) “Exponential Worth” Features (Product-level differentiation)

### A. Smart Inventory Intelligence
1. **Demand forecasting (per SKU)**
   - Use moving average / Holt-Winters / lightweight ML to predict weekly demand.
   - Show “projected stockout date.”
2. **Auto-reorder recommendation engine**
   - Suggest reorder quantity using lead time, safety stock, and service level.
3. **Dead stock detection**
   - Flag SKUs with low movement over configurable period.
4. **Expiry risk scoring**
   - Rank batches by expiry + sales velocity to trigger liquidation/promotion recommendations.

### B. Workflow/Productivity features
5. **Purchase order lifecycle**
   - Draft → Approved → Sent → Partially received → Closed.
6. **Goods receipt with variance tracking**
   - Match ordered vs received qty + quality notes.
7. **Stock transfer between warehouses/locations**
   - Add multi-location support with transfer approvals.
8. **Cycle count module**
   - Partial audits, discrepancy resolution, and adjustment reasons.

### C. Business-facing analytics
9. **Gross margin + carrying cost dashboard**
   - Even estimated values massively improve business relevance.
10. **Supplier performance scorecard**
   - On-time delivery %, rejection %, lead-time variance.
11. **What-if simulator**
   - “If demand rises by 20%, which SKUs stock out first?”

---

## 5) Reliability & Scalability Upgrades

1. **Event-driven architecture with outbox pattern**
   - Persist domain events in same transaction and publish reliably.
2. **Saga/compensation for cross-service operations**
   - If order creation succeeds but stock update fails (or vice versa), guarantee consistency strategy.
3. **Distributed tracing**
   - OpenTelemetry + Jaeger/Tempo to trace requests across gateway/services.
4. **Circuit breaker/retry/bulkhead**
   - Resilience4j for Feign calls (`Inventory`, `Seller`, `Alert`).
5. **Caching strategy formalization**
   - Redis keys/TTL/versioning and cache invalidation conventions.

---

## 6) Data & API Quality Enhancements

1. **API versioning and OpenAPI docs**
   - Swagger/OpenAPI with examples and auth schemes.
2. **Audit trails**
   - `createdBy`, `updatedBy`, change history for stock edits.
3. **Soft delete and archival**
   - Keep data lineage for compliance and analytics.
4. **Pagination/filter/sort for list endpoints**
   - Essential for realistic dataset handling.
5. **Database indexing review**
   - Index `productId`, `expiryDate`, `status`, `sellerId`, and order date fields.

---

## 7) Testing & DevOps (Major interview differentiator)

1. **Unit + integration tests with Testcontainers**
   - DB + Redis + Kafka integration in CI.
2. **Contract testing**
   - Producer/consumer contracts between services.
3. **Load testing**
   - k6/Gatling for order placement and inventory reads.
4. **CI/CD pipeline**
   - Lint, test, build images, security scan, deploy to staging.
5. **Docker Compose for full local stack**
   - One-command bootstrap for recruiters/interviewers.

---

## 8) UI/UX Enhancements That Improve Perceived Maturity

1. **Real-time alert panel** via WebSocket/SSE.
2. **Role-based UI views** (read-only vs manager controls).
3. **Saved filters and exports** (CSV/PDF).
4. **Actionable notifications** (“Reorder now”, “Mark batch for discount”).
5. **Dark mode + responsive optimization** for demos.

---

## 9) Suggested Prioritized Roadmap

### Phase 1 (1–2 weeks): “Stability + Correctness”
- Fix frontend API parameter bugs.
- Add DTO validation + global exception handling.
- Normalize endpoint naming.
- Add basic integration tests for order + stock deduction.

### Phase 2 (2–4 weeks): “Production posture”
- JWT auth at gateway + RBAC.
- OpenAPI docs + pagination/filtering.
- Resilience4j + tracing + structured logging.
- CI pipeline + docker-compose.

### Phase 3 (4–8 weeks): “Differentiator features”
- Forecasting + auto reorder suggestions.
- Supplier scorecards + multi-location transfer.
- Cycle counting + purchase order lifecycle.

---

## 10) Resume/Interview Packaging Tips (to convert work into shortlist value)

1. **Quantify outcomes**
   - “Reduced stockout incidents by X% in simulation dataset.”
   - “Handled Y requests/sec at p95 latency Z ms.”
2. **Show architecture diagram + sequence diagrams**
   - Order flow, alert flow, and failure handling flow.
3. **Publish demo + API docs**
   - Deployed frontend + Swagger link + test credentials.
4. **Highlight reliability patterns explicitly**
   - “Implemented idempotency keys, optimistic locking, and outbox events.”
5. **Include one technical deep-dive writeup**
   - Blog/README section on FEFO stock deduction and concurrency controls.

---

## 11) Top 10 Changes to Start This Week (Practical list)
1. Fix `inventoryApi.js` undefined parameters.
2. Add `@Valid` and validation annotations in all request DTOs.
3. Add global exception handler in each service.
4. Add optimistic locking in inventory batch/summary updates.
5. Introduce idempotency key in order placement endpoint.
6. Add JWT verification at gateway and role checks.
7. Add Swagger/OpenAPI across services.
8. Add integration tests for `placeOrder -> reduceStock` flow.
9. Add docker-compose to run full microservice stack.
10. Create a polished root README with architecture, setup, screenshots, and metrics.

If you execute even the first 4–5 items strongly, your project will look like a production-minded system rather than a college CRUD app.
