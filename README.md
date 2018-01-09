<h2>Configuration</h2>
Before you begin, download or clone the repository and open MySQL Workbench.
Afterwards, Please run the schema.sql script, and then the gen-acc.sql script.
This will supply your database with the relevant data.

<h2>Login</h2>

After you have configured the database, you will have to login to the website with the details provided, there are three different accounts which have access to 
different dashbaords, in this case, we will only be using the Customer Account

The url to the login page of the website is: http://localhost:8080/login

Customer Account

Email - customer@gmail.com

Password - 123

Company Account

Email - company@gmail.com

Password - 123

Assessor Account

Email - assessor@gmail.com

Password - 123

<h2>Selecting a Questionnaire</h2>

When you have logged into the website, you will be redirected to the dashboard, here is the link: http://localhost:8080/dashboard

Once on the dashboard, there will be a list of questionnaires to choose from, since you are a customer, you will only need to answer the client questionnaire
all the other questionnaires will be grayed out for you. You can proceed by clicking on the "Client" questionnaire.

<h2>Answering a Questionnaire</h2>

Now you will be on the questionnaire, there will be a progress bar at the top of the page to indicate how much progress you have made with the questionnaire,
then underneath, you will see the question you're being asked, and beneath that, will be 2 lines of checkboxes that signify how important that question is to you and if the quality meets a certain standard.

Keep note that 2 checkboxes must ticked from 1-5, and if you do tick a checkbox on either 1 or 5 then you will have to comment on why you gave them such a high or low rating.

If you do not follow the rules above, then you will not be able to proceed to the next question and will be prompted to answer correctly.

When you have ticked 2 checkboxes and filled in a comment (or not), then you will be able to proceed to the next question, this can be done by clicking the "Next" button at the bottom right of the webpage.

When you finish a questionnaire, you will be redirected to your dashboard where you will be able to logout.

<h2>Settings</h2>
To get to the settings page, please login with the instructions above. 

Afterwards, if you take a look at the navigation bar at the top of the webpage, to the right there will be a dropdown labelled account, please click on settings.

<h2>Changing your password</h2>
To change your password, please following the settings instructions above, when on the settings page there will be two input boxes in the middle of the screen, one in which you have to enter your current password and the other will be your new password.
 
 To be able to change your password, you must pass a small security check of confirming your password and the new password must be over 4 characters long, otherwise you will be prompted.
 
As you're logged in as the customer, please enter 123 into the current password section and 12345 into the new password section, you will know your password has been updated when green text appears saying so. 
 
 




