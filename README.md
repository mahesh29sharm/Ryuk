# Ryuk
It is a chatbot which can answer to users automatically according to their questions
Ryuk is a user friendly interactive chatbot created by The Shinigamis as a Project in ABHIKALPAN 2K18 , IIITDM JABALPUR . 
Ryuk is based on IBM Watson , Ryuk works on android platform and uses IBM Conversation for giving the result asked by the user . 
it also uses MySQL Database to store the previous queries to enhance the quality of chat in future.
Ryuk works as an customer care friendly bot made to answer different questions that are common to a person new to IBM Watson 
and it's made to understand slang words used by our generation. 
Questions Tried on Watson : 
1. I am having problems logging in Watson ?
2. What is Watson ?
3. Who created You ?
4. Can I trust you ?
5. Where to learn Watson ?
6. How Watson Work? 
7. I lost my password ? 
Working : 
We have used IBM Conversation API to genrate the response and android studio for front end (user interface) . We generated strings through android studio followed by sending them to the IBM conversation API which returns
the corresponding result of closest match which we have predefined in our workspace 
In our IBM conversation workspace we have defined some intents Ex - Login , Password , Learn , How etc and we have used system entities 
as well as our self defined entities , Finally the logic goes in the Dialog section where we combine intents and entites to genrate the best possible result 
regarding any question asked by user , finally the response sent to the android app through the IBM API in the form of JSON. We extract useful information from the 
receieved JSON response and show it to user in this way this application interacts with the user and tries to make their answer easier and user friendly .
