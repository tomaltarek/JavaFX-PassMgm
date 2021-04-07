This is a JavaFX CRUD application. 

The application manages passwords.

The application uses an embeded database 'tomaldb' (a derby database), anyone wish to compile these
source code needs 'tomaldb' in project folder. The database can be found in the followign .zip file 
https://github.com/tomaltarek/Applications/blob/master/PasswordManagement/PassMgmt.zip 

I have tried usign MVC model structure in this project. 

The following tools have been used: 
Eclipse IDE (with JavaFx plug in) 
Scene Builder

The binary of this application can be downloaded from: 
https://github.com/tomaltarek/Applications/blob/master/PasswordManagement/PassMgmt.zip 

Note: 
In order to access the db manually -

step 1: Derby db has to be on the computer?
step 2: start a comman prompt in the directory where tomaldb database is located. 
step 3: type ij (this is the sql interface on the cmd propmt for derby db)
step 4: CONNECT 'jdbc:derby:tomaltdb';
step 5: show tables, there are sys and app table, look for app table
step 5: made a query on the tabels. 
