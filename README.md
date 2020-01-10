# Project Scheduler
Basic usage for the Project Scheduler, a REST Web Service created with Spring Boot.
  
Use Postman (https://www.getpostman.com/) to easily view/send API requests 

# Run via Maven
1. Go to the project-scheduler directory
2. mvn clean install
3. Go to the project-scheduler-server directory
3. Run the application (mvn spring-boot:run)

# Viewing a project
GET http://localhost:8181/projects

```json
[
    {
        "id": "103",
        "name": "Old Project"
    }
]
```

# Creaing a new project
POST http://localhost:8181/projects

Sample request
```json
[
    {
        "id": "104",
        "name": "New Project"
    }
]
```

# Viewing all tasks in a project. 

This will display all the tasks (with its dependencies)

GET localhost:8181/projects/{projectId}/task

```json
[
    {
        "id": "TASK_1",
        "name": "SIT",
        "duration": 8,
        "taskDependencies": [
            {
                "id": "TASK_6",
                "name": "Review",
                "duration": 8,
                "taskDependencies": []
            }
        ]
    },
    {
        "id": "TASK_2",
        "name": "Create new project plan",
        "duration": 4,
        "taskDependencies": [
            {
                "id": "TASK_3",
                "name": "Retro",
                "duration": 3,
                "taskDependencies": []
            },
            {
                "id": "TASK_4",
                "name": "Start new sprint",
                "duration": 8,
                "taskDependencies": [
                    {
                        "id": "TASK_5",
                        "name": "Planning",
                        "duration": 8,
                        "taskDependencies": []
                    }
                ]
            }
        ]
    },
    {
        "id": "TASK_11",
        "name": "Unit Test",
        "duration": 5,
        "taskDependencies": []
    }
]
```

# Adding a new task to a project
POST localhost:8181/projects/{projectId}/task

```json
{
	"id" : "TASK_5",
	"name" : "Retrospective Meeting",
	"duration" : "8"
}
```

# Adding a task dependency to a task

Supply an array of task ids.

POST localhost:8181/projects/{projectId}/task/{taskId}/dependencies

```json
["TASK_3", "TASK_4"]
```

# Generating a schedule for the project
GET localhost:8181/projects/{projectId}/schedule


```json
[
    {
        "id": "TASK_1",
        "name": "SIT",
        "duration": 8,
        "taskDependencies": [
            {
                "id": "TASK_6",
                "name": "Review",
                "duration": 8,
                "taskDependencies": [],
                "startDate": "Jan-11-2020",
                "endDate": "Jan-19-2020"
            }
        ],
        "startDate": "Jan-19-2020",
        "endDate": "Jan-27-2020"
    },
    {
        "id": "TASK_2",
        "name": "Create new project plan",
        "duration": 4,
        "taskDependencies": [
            {
                "id": "TASK_3",
                "name": "Retro",
                "duration": 3,
                "taskDependencies": [],
                "startDate": "Jan-27-2020",
                "endDate": "Jan-30-2020"
            },
            {
                "id": "TASK_4",
                "name": "Start new sprint",
                "duration": 8,
                "taskDependencies": [
                    {
                        "id": "TASK_5",
                        "name": "Planning",
                        "duration": 8,
                        "taskDependencies": [],
                        "startDate": "Jan-30-2020",
                        "endDate": "Feb-07-2020"
                    }
                ],
                "startDate": "Feb-07-2020",
                "endDate": "Feb-15-2020"
            }
        ],
        "startDate": "Feb-15-2020",
        "endDate": "Feb-19-2020"
    },
    {
        "id": "TASK_11",
        "name": "Unit Test",
        "duration": 5,
        "taskDependencies": [],
        "startDate": "Feb-19-2020",
        "endDate": "Feb-24-2020"
    }
]
```
