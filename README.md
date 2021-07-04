# MessageApp
A chatting/Messaging application using Java programming and Spring framework with a layer of security for the user.
This project will develop a messaging application for a company where the employees can communicate over a forum.
The app is based on Java coding and Spring framework.
It uses MVC pattern to actually implement the application.
The app has a security added. For using the application, user has to register with their username and password.
The registration details are stored in the database at the backend.(NOTE-This app uses H2 database).Then user has to login.
If credentials match with that stored in database then it is a successful LOGIN otherwise the screen shows INVALID CREDENTIALS and user has to try again.
On successfull Login,a text box appears to type the message.User type the message and click SUBMIT.
Th message is stored in the database.
All the messages are fetched from database and viewed in a page (VIEW MESSAGES).
The message appears with name of the person on top of the message.



