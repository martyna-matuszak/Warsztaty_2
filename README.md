# Workshops 2 - JDBC

In order for the project to work properly user must:

1. Add the jBCrypt dependency from here: http://www.mindrot.org/projects/jBCrypt/,
2. Add the schema workshops_2 to the local database,
3. Run the script from file *data.sql* in order to have any data to view and work with,
4. Add the mySQL Connector dependency from here: https://dev.mysql.com/downloads/connector/j/

Generally the project gives access to two different consoles, both connected to the database. 

Admin console allows the user to access all the data about *users*, *exercises* and *user groups*. 
Admin can add entries to those tables, edit data stored in them or delete entries. Table *solutions* is a little bit different.
Admin is allowed add entries without any restrictions, but can edit and delete entries only until the user adds a solution to them. 
Additionally admin cannot add solutions to the commisioned exercises - this is a function available only to users.

The user console on the other hand provides access only to the tasks commisioned to this particular user.
First user must log in to an existing account. Then he will have access to viewing all of his exercises
and to adding a solution to an exercise which haven't been solved before. If user has no exercises left to solve, 
the console will inform about it and won't allow anything except viewing solutions.
