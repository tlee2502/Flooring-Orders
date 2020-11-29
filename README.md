Application (CRUD) which deals with flooring orders for a company. 
This project follows the MVC pattern, Spring framework dependency injection, extensive JUnit testing, regex for allowing commas even when we are reading/writing commas separated values.

Briefing is as follows:
Orders should contain ask for customer name, area of floor, state, product. State and product are read in from external files and are used in calculating the final cost of the floor for that order.
Order files should be written out by date, two orders in the same date will share the same file. The file name should be "Order_<DATE>", in the Orders/ directory.
Allow the user to back up all data to a file, writing out all information fields for each order, along with the date.
Users should be able to:
1) Display an order - prompt for date, display all orders and subsequent information for that date.
2) Add an order - prompt for a FUTURE date, add an order for that date. Assign a UNIQUE order id number.
3) Edit an order -prompt for date, display customer name, prompt user for input to edit, if they enter blank string, don't edit previous field. Repeat for state, product, area.
4) Remove an order - prompt for order number and date, remove if order exists for that date.
5) Export all data - back up all data to one file.
