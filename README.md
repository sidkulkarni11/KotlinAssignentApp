# Kotlin Android Assignment — Field Agent Directory

A lightweight, offline-first Android application built in **Kotlin** for field agents operating in low-connectivity environments.

This app allows agents to:
- Browse and search other agents
- View agent profiles and recent posts
- Operate smoothly on low-end devices
- Continue functioning when offline
- Control behavior via app settings

---

## 🚀 Features

### 1. Agent Directory (Home Screen)
- Fast, smooth scrolling using **RecyclerView + Paging 3**
- Instant search with debounce
- Pull-to-refresh support
- Offline access using Room caching
- Graceful handling of poor or no network

### 2. Agent Profile
- Displays agent details
- Loads recent posts lazily
- Cached for faster revisit
- Smooth UI even on low-end devices

### 3. Settings
- Offline-only mode
- Background auto-refresh toggle
- Network-safe refresh behavior
- Last refresh timestamp
- Preferences stored using DataStore

---

## 🧱 Architecture

The app follows **Clean Architecture with MVVM**, ensuring scalability and testability.

ui/
├── agentlist
├── agentprofile
├── settings

domain/
├── model
├── repository
├── usecase
├── mapper

data/
├── remote (Retrofit APIs)
├── local (Room DB, DAOs)
├── paging (PagingSources)
├── repository (RepositoryImpl)
├── preferences (DataStore)
├── worker (WorkManager


### Key Principles
- UI is lifecycle-aware
- Business logic lives in use cases
- Repository abstracts data sources
- Room is the single source of truth
- Paging handles memory and performance

---

## 🗄️ Data Layer

### Remote
- **DummyJSON API**
    - `/users`
    - `/users/search`
    - `/posts/user/{id}`

### Local
- **Room Database**
    - AgentEntity
    - PostEntity
    - PagingSource from Room for offline-first behavior

---

## 🔄 Paging & Search

- Paging 3 handles large datasets efficiently
- Search switches between:
    - Paged list (default)
    - Search result stream
- Debounced search input prevents redundant calls
- Paging invalidation used for pull-to-refresh

---

## 📴 Offline-First Strategy

- Cached data shown when offline
- Network errors fallback to Room
- Offline-only mode enforces local data usage
- No UI freezes or crashes without connectivity

---

## 🔋 Battery Optimization

- Background refresh via **WorkManager**
- Auto-refresh pauses when:
    - App is backgrounded
    - Offline-only mode is enabled
- No unnecessary network calls

---

## 🧰 Tech Stack

- **Kotlin**
- **MVVM + Clean Architecture**
- **RecyclerView**
- **Paging 3**
- **Room**
- **Retrofit**
- **Coroutines & Flow**
- **DataStore**
- **WorkManager**
- **Material 3**
- **SwipeRefreshLayout**

---

## ⚙️ Setup Instructions

1. Clone the repository
2. Open in Android Studio (Giraffe or newer)
3. Sync Gradle
4. Run on an emulator or physical device (API 28+)

---

## 🧪 Tested Scenarios

- Online → Offline transition
- Pull-to-refresh
- Search with poor network
- Backgrounding app
- Device rotation
- Low-end device performance

---

## 🧠 Design Decisions

- Used XML layouts instead of Compose for broader interview acceptance
- Avoided Navigation Component to keep logic explicit and readable
- Paging used instead of manual pagination
- Room chosen for aggressive offline caching
- DataStore preferred over SharedPreferences

---

## 📌 Assumptions

- DummyJSON search endpoint is not paginated
- Background refresh scope kept minimal to save battery
- App designed as an internal tool, not consumer-facing

---

## 👤 Author

**Sid Kulkarni**  
Android Developer  
Kotlin • Clean Architecture • Offline-First Apps

---

## ✅ Assignment Status

✔ Completed within scope  
✔ Senior-level architecture  
✔ Offline-first & performant  
✔ Production-ready patterns  

