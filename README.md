
| Http method | API                | Request                  | Response                     | Role    |
|-------------|--------------------|--------------------------|------------------------------|---------|
| GET         | /appintments       |                          | List of FullAppointmentInfo  | Doctor  |
| POST        | /open-appintments  | CreateAppointmentRequest | List of BasicAppointmentInfo | Doctor  |
| DELETE      | /open-appintments  | {appointmnetId}          |                              | Doctor  |
| GET         | /open-appintments  |                          | List of BasicAppointmentInfo | PATIENT |
| PUT         | /open-appintments  | TakeAppointmentRequest   |                              | PATIENT |
| GET         | /taken-appointment |                          | List of BasicAppointmentInfo | PATIENT |
