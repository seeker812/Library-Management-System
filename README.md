
````markdown
# Library Management System

A modular, object-oriented Library Management System implemented in Java, demonstrating core OOP concepts, SOLID principles, and clean package-based design. This system helps manage books, branches, patrons, lending, reservations, and inventory across multiple library locations.

## 📚 Features

- Manage books and multiple copies
- Track branches and transfer books
- Handle lending and returns with exception handling
- Support reservations and patron management
- Organize book inventory with copy pools

## 🛠️ Technologies Used

- Java 8+
- Maven (build tool)
- IntelliJ IDEA (recommended IDE)

## 📂 Project Structure

```plaintext
Library_Management_System/
├── src/
│   └── main/
│       ├── java/
│       │   └── org.example/
│       │       ├── book/               # Book, Copy, PaperBook, BookCopy
│       │       ├── branch/             # Branch, BranchManagement, TransferManager
│       │       ├── inventory/          # BookInventoryManagement, CopyPool
│       │       ├── lending/            # LendingManager, Exceptions
│       │       ├── patron/             # Patron, PatronManagement
│       │       ├── reservation/        # ReservationManager
│       │       └── Main.java           # Entry point
│       └── resources/
├── test/                               # (Optional) Unit tests
├── pom.xml                             # Maven configuration
└── .gitignore
````

## 🚀 Getting Started

### Prerequisites

* Java 8 or higher
* Maven

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/seeker812/Library-Management-System.git
   cd Library-Management-System
   ```

2. Build the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn exec:java -Dexec.mainClass="org.example.Main"
   ```

## 🧠 Design Highlights

* **SOLID Principles**: Separation of concerns across packages.
* **Scalability**: Supports multiple branches and book copies.
* **Robustness**: Custom exceptions like `BookNotFoundException`, `NotAvailableCopyException`.

## 🤝 Contributing

Pull requests are welcome. For major changes, open an issue first to discuss your ideas.
