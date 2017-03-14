# humanresources

This project is my Java Practice project that uses Oracle DB HR user and its' tables. 

Important models (classes):

  DBUtil:
    - Uses Properties object to load connection information and then creates pooling connection
    
  Employees:
    - This class serves as a communicator to EMPLOYEES table in HR database. It can accept objects of type Employee and store them, 
      update them, or read them from the database. Some interesting logic used with updating because some triggers that come with
      HR database are only allowed to be used once a day (I presume), and those are associated with JOB_ID and DEPARTMENT_ID. Thus,
      I needed to allow updates regardles, but different implementation based on wether we updated the employee once already.
     
  Employee:
    - This class serves to instantiate objects which represent rows from EMPLOYEES table in HR database. Alongside getters, setters and 
    overloaded constructors, it allows Employee to store itself to database in a simple way by implicitly using Employees class.
    
Further updates incoming....
