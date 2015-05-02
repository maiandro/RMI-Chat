# RMI-Chat
This is a Chat between a Client and a Atendent using RMI (Remote Method Invocation).

A Client call the Server remote object requesting a Atendent.
If there's a Atendent, his remote object reference is returned
otherwise the Client wait.

The Client send the first Message to a Atendent with his own
remote reference. Then, both Client and Atendent can talk
without the Server.

A Atendent have to register his remote object into the Server.

When some of the sides leave the chat, a Message with a SIGNAL
is sent to both sides to finish the chat and then the Atendent
is asked if he wants to keep on or not.
