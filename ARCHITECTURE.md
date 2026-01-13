# Architecture — Field Agent Directory App

This document explains the architecture, design decisions, and implementation details of the **Kotlin Android Field Agent Directory** application.

The goal of the architecture is to ensure:
- Offline-first behavior
- High performance on low-end devices
- Clear separation of concerns
- Scalability and maintainability
- Predictable lifecycle behavior

---

## 🧱 Architectural Pattern

The app follows **Clean Architecture with MVVM**.

UI Layer
↓
Domain Layer
↓
Data Layer


Each layer depends only on the layer below it.

---

## 📱 UI Layer

**Responsibilities**
- Render UI
- Handle user interactions
- Observe state from ViewModels
- Never perform business logic

**Components**
- Fragments (XML-based)
- ViewModels
- RecyclerView + PagingAdapter
- SwipeRefreshLayout

**Why XML instead of Compose?**
- Broader interview acceptance
- Predictable view lifecycle
- Clear separation from business logic

---

## 🧠 ViewModel Layer

**Responsibilities**
- Hold UI state
- Expose `Flow<PagingData<>>`
- Handle search debouncing
- Coordinate use cases
- Be lifecycle-aware

**Key Principles**
- No Android Context
- No direct data source access
- Uses `viewModelScope` for coroutines
- Cancelled automatically on lifecycle end

---

## 🧩 Domain Layer

**Responsibilities**
- Define business logic
- Represent core models
- Enforce application rules

**Components**
- `Agent`, `Post` domain models
- `AgentRepository` interface
- Use cases (`GetAgentsUseCase`)
- Mappers (DTO → Domain → UI)

**Why Use Cases?**
- Keeps ViewModels thin
- Makes business logic reusable
- Improves testability

---

## 💾 Data Layer

### 1. Remote Data Source
- Retrofit-based API client
- DummyJSON endpoints:
    - `/users`
    - `/users/search`
    - `/posts/user/{id}`

### 2. Local Data Source
- Room database
- DAO-based access
- PagingSource from Room for offline-first lists

### 3. Repository Implementation
- Acts as the **single source of truth**
- Decides when to fetch from network or local cache
- Handles mapping and error conversion
- Exposes `Flow<PagingData<>>`

---

## 🔄 Paging Architecture

Paging 3 is used to:
- Load data incrementally
- Minimize memory usage
- Handle large datasets smoothly

**Flow**
RecyclerView
↓
PagingAdapter
↓
PagingData Flow
↓
PagingSource / Room


**Refresh Behavior**
- Pull-to-refresh triggers `PagingAdapter.refresh()`
- Paging invalidates sources
- Room is updated automatically
- UI re-renders efficiently

---

## 🔍 Search Architecture

- Search input is debounced in ViewModel
- Query changes switch data source:
    - Empty query → paged list
    - Non-empty query → search results
- Avoids redundant network calls
- No blocking UI operations

---

## 📴 Offline-First Strategy

- Room is the primary data source
- Network is used opportunistically
- Cached data shown when offline
- Offline-only mode enforces local usage
- No UI freezes or crashes on network loss

---

## ⚙️ Settings & Preferences

**Storage**
- Jetpack DataStore (Preferences)

**Settings**
- Offline-only mode
- Background auto-refresh
- Last refresh timestamp

**Scope**
- App-wide
- Lifecycle-safe
- Non-blocking

---

## 🔋 Background Refresh Strategy

- Implemented using WorkManager
- Refresh jobs:
    - Respect battery constraints
    - Pause when offline-only is enabled
    - Avoid redundant network calls
- Timestamp updated on successful refresh

---

## 🧪 Error Handling & Resilience

- All network calls wrapped safely
- Errors converted to UI-friendly states
- Paging retry mechanisms
- Fallback to cached data

---

## 🎯 Performance Optimizations

- Paging 3 instead of manual pagination
- DiffUtil for RecyclerView
- No heavy work on main thread
- Coroutine-based concurrency
- Cached profiles load faster on revisit

---

## 🚫 Intentional Exclusions

These were intentionally avoided for clarity and scope:
- Navigation Component
- SafeArgs
- Dependency Injection frameworks
- Compose UI
- Overly complex background syncing

---

## 📌 Design Trade-offs

| Decision | Reason |
|------|------|
XML UI | Interview-friendly |
No Nav Component | Simpler navigation |
Manual DI | Transparent setup |
Paging 3 | Memory efficient |
Room | Offline-first |

---

## ✅ Summary

This architecture:
- Handles low-connectivity environments gracefully
- Scales well for larger datasets
- Separates concerns cleanly
- Uses industry-standard Android patterns
- Meets senior-level expectations for performance and reliability

---

## 👤 Author

**Sid Kulkarni**  
Android Developer  
Clean Architecture • Kotlin • Offline-First Systems



**Refresh Behavior**
- Pull-to-refresh triggers `PagingAdapter.refresh()`
- Paging invalidates sources
- Room is updated automatically
- UI re-renders efficiently

---

## 🔍 Search Architecture

- Search input is debounced in ViewModel
- Query changes switch data source:
    - Empty query → paged list
    - Non-empty query → search results
- Avoids redundant network calls
- No blocking UI operations

---

## 📴 Offline-First Strategy

- Room is the primary data source
- Network is used opportunistically
- Cached data shown when offline
- Offline-only mode enforces local usage
- No UI freezes or crashes on network loss

---

## ⚙️ Settings & Preferences

**Storage**
- Jetpack DataStore (Preferences)

**Settings**
- Offline-only mode
- Background auto-refresh
- Last refresh timestamp

**Scope**
- App-wide
- Lifecycle-safe
- Non-blocking

---

## 🔋 Background Refresh Strategy

- Implemented using WorkManager
- Refresh jobs:
    - Respect battery constraints
    - Pause when offline-only is enabled
    - Avoid redundant network calls
- Timestamp updated on successful refresh

---

## 🧪 Error Handling & Resilience

- All network calls wrapped safely
- Errors converted to UI-friendly states
- Paging retry mechanisms
- Fallback to cached data

---

## 🎯 Performance Optimizations

- Paging 3 instead of manual pagination
- DiffUtil for RecyclerView
- No heavy work on main thread
- Coroutine-based concurrency
- Cached profiles load faster on revisit

---

## 🚫 Intentional Exclusions

These were intentionally avoided for clarity and scope:
- Navigation Component
- SafeArgs
- Dependency Injection frameworks
- Compose UI
- Overly complex background syncing

---

## 📌 Design Trade-offs

| Decision | Reason |
|------|------|
XML UI | Interview-friendly |
No Nav Component | Simpler navigation |
Manual DI | Transparent setup |
Paging 3 | Memory efficient |
Room | Offline-first |

---

## ✅ Summary

This architecture:
- Handles low-connectivity environments gracefully
- Scales well for larger datasets
- Separates concerns cleanly
- Uses industry-standard Android patterns
- Meets senior-level expectations for performance and reliability

---

## 👤 Author

**Sid Kulkarni**  
Android Developer  
Clean Architecture • Kotlin • Offline-First Systems
