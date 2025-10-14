This is mircroservice based case study(JAVA 17 new features+postgresql)
endpoints-
http://localhost:8081/swagger-ui/index.html( user-service )
http://localhost:8082/swagger-ui/index.html( ticket-service)


SCENARIOS-
customer can create ticket,view his own tickets,view history of tickets.
admin can update tickets and view all tickets and assign to himself and agents but can not assign to customers,can view by id and username.
agents can assign tickets to themselves, can view all tickets,can view by id and username, can not udpate priority only can change status.


API GATEWAY SCENARIO:
SWAGGER-->
HIT --> http://localhost:8083/swagger-ui.html will open swagger ui with user service and ticket service in dropdown.

Postman->
authfilter of gateway will work for it
urls will strart with http://localhost:8083 and include the endpoints with BearerToken as authorization in hte postman client UI.
Ex-http://localhost:8083/api/users/username/satyam222
http://localhost:8083/api/users/5
http://localhost:8083/api/tickets/cus
http://localhost:8083/api/tickets/1/history
http://localhost:8083/api/auth/login
http://localhost:8083/api/auth/register
http://localhost:8083/api/tickets/4

Dummy response-
http://localhost:8083/api/auth/login
{
  "username": "satyam",
  "password": "123456"
}
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0-----------------------------------Q9rRKxqDwx8fqabEzEW5A8jFrz_3vNDzZRCyxSQInA",
    "username": "satyam",
    "role": "ADMIN"
}



Noitification Service(RabbitMQ-pulled image in docker desktop)
commnad-docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 -v rabbitmq_data:/var/lib/rabbitmq rabbitmq:3.13-management
response will be available on notification service-
scenarios-Ticket creation and updation.
ex-Notification Sent: Ticket Updated with ID: 7 Status: CLOSED


