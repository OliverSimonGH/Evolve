<h2>SETUP</h2>
Clone the repository by opening a GitBash prompt in a directory
where you want the project. Find the SSH key of the project at
https://gitlab.cs.cf.ac.uk/c1633899/evolvestuff and type
git clone git@gitlab.cs.cf.ac.uk:c1633899/evolvestuff.git
The project will now be cloned to that directory
<br>
<h2>CONFIGURATION</h2>
Open IntelliJ and import the project from existing sources, go to
the directory of the cloned project and select the build.gradle file,
select auto import and create directories for empty content roots
automatically, now IntelliJ is configured to run the application.
(Make sure to checkout to the after branch in gitbash with "git checkout -f after")
Open the gradle tool bar select tasks -> build and there you can
clean and build the project, the gradle tool bar lets you run the application
by going to tasks -> application and selecting bootRun.
<br>

Open MySQLWorkbench and run the evolve-new-schema-data.sql (located in database folder) file
in there, this will create the schema and provide data for the
database.
<br>
<h2>ACCOUNTS</h2>
There are the accounts that can be used to authenticate to the website with:
<br>
Company Account Details:
<ul>
<li>Username: company@gmail.com</li>
<li>Password: 123</li>
</ul>
<br>
Assessor Account Details:
<ul>
<li>Username: assessor@gmail.com</li>
<li>Password: 123</li>
</ul>
<br>
Customer Account Details:
<ul>
<li>Username: customer@gmail.com</li>
<li>Password: 123</li>
</ul>

<h2>Attack</h2>
The attacks directory contains the files that were used to attack the
application when it was secured

<h2>Application</h2>
The application can be accessed at the domain localhost, port 8181 and HTTPS protocol,
https://localhost:8181