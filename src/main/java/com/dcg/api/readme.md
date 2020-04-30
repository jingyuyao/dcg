This directory contains the client/server api messages. All fields in server messages should be
either primitive types or reference other api messages. Server messages should not directly expose
any implementation classes. If the server message is a wrapper for an implementation class then
a `View` suffix is added to the implementation class name.
