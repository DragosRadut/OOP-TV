# OOP TV

## Description
The entire project stands under the copyright of University Politehnica of Bucharest, Object Oriented Programming 2022, being a graded assignment.
This is a backed implementation of a simple movie streaming platform. Implementation targets the search and acces method, designing the platform as a file tree.

## Structure
The project is structure similar to a command design pattern.
### Specific packets
Classes are divided into 2 packets:
* input.data -> input data specific
* page.data -> overrides for page specific methods
### Pages
Each page implements (using Singleton design pattern) the interface Page containing 2 methods:
* changePange -> gets as param the page on which user wants to navigate and returns a string ("err" = eroare, "page name" = succes);
* action -> handler for "on page" actions; returns a PageResponse object containing data to be modified;
### Interpreter
An interpreter object is used as an intermediar between input, the antion itself and resulting output.
This object will determine the page object that the user is currently using.
After that, one of two specific methods will be called: changePage/action.
#### changePage
From each page the user can navigate to a predefined set of pages.
Some actions may be executed when "loading" the new page.
### action
Responses from handler can be errors that will be interpreted or specific mesages for different success output.
Each Page object will return a string indicating specific modifications:
* loginUser -> login specific actions will be made
* registerUser -> register specific actions will be made
* errLogin -> user will be redirected to initial page 
* setMovies -> current list of movies will be modified
* updateUser -> current user data will be modified
* updateUserMovies -> user's movie list will be modified
