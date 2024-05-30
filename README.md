## TaxiTracker

**TaxiTracker** is an application written in Kotlin that allows real-time tracking of taxi locations. The application receives data from an AWS SQS queue, saves the locations to a MongoDB database, and provides a REST API to retrieve the positions of a selected taxi within a specified time range.

### Features

- **Location Reception**: The application receives taxi location data from an AWS SQS queue.
- **Database Storage**: Locations are stored in a MongoDB database.
- **REST API**: Allows retrieving the positions of a selected taxi within a specified time range.

### Technologies

- **Programming Language**: Kotlin
- **Message Queue**: AWS SQS
- **Database**: MongoDB
- **Framework**: Spring Boot

### Requirements

- Kotlin 1.9+
- MongoDB
- AWS account with access to SQS

### REST API

- **GET /api/taxi-locations?taxi-id={id}&start-time={start_time}&end-time={end_time}**
    - Retrieves the positions of the taxi with the specified ID within the given time range.
    - **Parameters:**
        - `taxi-id` (String) – Taxi ID
        - `start-time` (String) – Start time (in ISO 8601 format)
        - `end-time` (String) – End time (in ISO 8601 format)
    - **Example:**
      ```
      GET /api/taxi-locations?taxi-id=abc123&start-time=2023-05-01T00:00:00Z&end-time=2023-05-01T23:59:59Z
      ```

### License

This project is licensed under the MIT License. See the `LICENSE` file for details.
