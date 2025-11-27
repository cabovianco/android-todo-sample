# Android Todo Sample

A clean, modern, and offline ToDo application built with Kotlin and Jetpack Compose.

Designed as a simple yet complete example of a task management app, this project demonstrates best practices in modern Android development, including declarative UI, state management, persistence, and dependency injection.

Ideal for learning, teaching, or using as a starting point for your own apps.

Stay organized with a lightweight and privacy-focused experience.

## Features

- Create, edit, and delete tasks.
- Mark tasks as completed.
- Separate views for pending and completed tasks.
- Works fully offline — all data is stored locally.
- Clean and minimalistic interface powered by Jetpack Compose.

## Installation

### From Source Code

1. Clone the repository:

   ```bash
   git clone https://github.com/cabovianco/android-todo-sample.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your device or emulator.

## Technologies

- **Platform & Language:** Android, Kotlin.
- **Architecture:** MVVM, Clean Architecture.
- **UI:** Jetpack Compose, Material Design.
- **Data & Concurrency:** Room, Kotlin Coroutines, Kotlin StateFlow.
- **Dependency Injection:** Dagger Hilt.
- **Navigation:** Navigation Compose.

## Tests

This project includes a comprehensive test suite covering all architectural layers—**data**, **domain**, and **presentation**—ensuring reliability, correctness, and predictable behavior across the entire app.

### Data Layer Tests (DAO + Repository)

#### DAO Tests (Room)

DAO tests use an **in-memory Room database** (`Room.inMemoryDatabaseBuilder`) executed with **AndroidJUnit4**.
These tests validate:

* Insertion, update, and deletion of tasks.
* Fetching tasks by ID.
* Stream correctness using `Flow` and `first()`.
* Persistence logic functioning independently from the UI.

A fresh database is created before each test and fully isolated.

#### Repository Tests (Fake DAO)

The repository implementation is tested using a custom **FakeTaskDao** to ensure:

* Correct mapping between `TaskEntity` and domain `Task`.
* Consistent behavior of insert/update/delete operations.
* Accurate `Flow` emissions.

These tests run as pure JVM tests without Android dependencies.

---
### Domain Layer Tests (Use Cases)

Domain tests cover all business rules using **fake in-memory repositories**.

These tests ensure:

* Task creation/editing/deletion logic behaves correctly.
* Completed/active task rules remain consistent.
* Use cases emit or return the expected data.
* All domain logic remains UI-agnostic and framework-independent.

Each use case is tested in isolation with `runTest`.

---
### Presentation Layer Tests (ViewModels)

ViewModels are tested using:

* **MainDispatcherRule** to control coroutines and `Dispatchers.Main`.
* **FakeTaskRepository** for isolated state testing.
* `StateFlow` assertions.

These tests verify:

* UI state updates correctly after interactions.
* Valid/invalid task creation.
* StateFlow emits correct lists and values.
* No real database or Android framework usage.

Each ViewModel is validated independently to ensure predictable and stable UI behavior.

---
### Running Tests

To run all tests, use the following command in the project root:

```bash
./gradlew test
```

## Contributing

Contributions are welcome!
If you'd like to improve this project:

1. Fork the repository.
2. Create a new branch:

   ```bash
   git checkout -b feature/new-feature
   ```
3. Commit your changes:

   ```bash
   git commit -m 'Add new feature'
   ```
4. Push your branch:

   ```bash
   git push origin feature/new-feature
   ```
5. Open a Pull Request.

Please ensure your code follows the existing style and includes tests when appropriate.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
