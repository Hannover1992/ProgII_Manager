# Task Tracker README

## Overview
This Java program is designed to help users efficiently track and manage their tasks, with the goal of completing each task in under 20 minutes. It features prioritization of tasks based on the frequency of attempts, focusing on those that are least frequently performed. It also tracks progress through serialization.

## Features
- **Task Prioritization**: Tasks are automatically prioritized based on the frequency of attempts, prioritizing those least attempted.
- **Performance Timing**: Each task is timed, aiming for completion within 20 minutes.
- **Progress Tracking**: Progress for each task is serialized and saved, allowing users to resume where they left off.

## Getting Started
Ensure you have Java installed on your system. Navigate to the directory containing the program files and compile the Java classes using the command:
```bash
javac *.java
```
Then, start the application:
```bash
java Main
```

## Directory Structure
Tasks are organized within the `Assignments` directory. The list of tasks is generated from the second level of folders under this directory.

## Task Data
The task data, including the number of attempts and best times, is serialized and saved in a file (`assignments.ser`). This file helps the program remember the state of each task between sessions.

## Usage
Upon running the main program, you will be presented with several options to manage tasks:
- **View all tasks**: Displays all available tasks with their current status.
- **Select a task**: Choose a specific task to work on.
- **Random task**: Assigns a random task that has the lowest number of attempts.

Each task can be started, paused, resumed, or reset based on user input commands during the session.

## Note
This program builds on the task collection framework initially created by Falk, which is not included here. The full version can be found in the FINF repository.

## Networking
Connect on LinkedIn:
[Patryk Krzyzanski](https://www.linkedin.com/in/patryk-krzyzanski-53450a84/)

## Open Source
This code is open source, which means everyone is welcome to contribute to its improvement. Contributions can be made through:
- **Forking the repository**: Make a copy of the project to your GitHub account.
- **Pushing changes**: After making your improvements, push the changes to your fork.
- **Creating Pull Requests**: Submit a pull request to the original project, proposing your changes to be merged.

Contributors are encouraged to discuss their proposed changes through issues and pull requests to enhance the application collaboratively.
[GitHub Repository](https://github.com/Hannover1992/ProgII_Manager)
