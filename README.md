# realTimeChatSample
This is a chat UI Library with a Firebase Firestore as backend.
Firebase Firestore is a highly scalable backend for chat type applications.
The maintanence of gRPC socket and connection request is done by Firebase and 

**Note**: Without firebase this implementation can be done with a No Sql type databse(MONGO) and a push service like (FCM)

The following functionalities are implemented:
-Register is implemented with First Name, Last Name and UserName
-Login using just Username
-Home screen contains chat List with the most recent chats at the top
-User list to start with an existing user or a new user

**Note**: The reason i chose not do a full authentication is simplicity (most of the people might actually have implemented an authentication protocol) 

The schema for the firebase Backend:
- A User table which holds all the users (Lets call them N) information
- N-1 tables for implementing chat between 2 users (A user doesnt need to chat with himself)


The code uses the following libraries to adhere to pure MVVM

-Navigation library
-LiveData
-ViewModel

**Note**: MVVM is best way to maintain separation of concerns

### TODO
This is list of things that are in the works for this library:
-Home screen is not yet implemented for realtime
-Logout and profile is not implemented



