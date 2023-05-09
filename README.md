# ssodemo
Hey, this is a demo to connect a sso frontend to a backend with an azure ad identity provider

## Azure Config
Add an Azure Application with SPA capacity and add a redirect uri with port 5173, add a new api with a random path you will use later (see placeholder).

## Frontend
This Frontend is written by vue.js and uses the quickstart source code by azure. This is in javascript, so you should create types when using it. But for sure you could ignore warnings ;-)
To start this, you have to create an `.env`-file with that content
``
VITE_APP_BASE_URL=http://localhost:5173/api/
VITE_APP_CLIENTID=<CLIENTID>
VITE_APP_AUTHORITY=https://login.microsoftonline.com/<TENANTID>
VITE_APP_REDIRECT_URL=http://localhost:5173/
VITE_APP_API_SCOPE=api://<APISCOPE>/<APIPATH>
``

## Backend
This Backend is written by Java to validate a JWT. You can use that when having a Spring Boot Application. For other languages there are proper adapations.
You will need to configure this app by using the application.properties
``
tenantId=<TENANTID>
clientId=<CLIENTID>
audience=api://<APISCOPE>
``
