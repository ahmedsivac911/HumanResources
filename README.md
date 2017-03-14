# humanresources

This project is my Java Practice project that uses Oracle DB HR user and its' tables. 

Important models (classes):

  DBUtil:
    - Uses Properties object to load connection information from XML file and then creates pooling connections.
    
  Employees:
    - This class serves as a communicator to EMPLOYEES table in HR database. It can accept objects of type Employee and store them, 
      update them, or read them from the database. Some interesting logic used with updating because some triggers that come with
      HR database are only allowed to be used once a day (I presume), and those are associated with JOB_ID and DEPARTMENT_ID. Thus,
      I needed to allow updates regardles, but different implementation based on wether we updated the employee once already.
     
  Employee:
    - This class serves to instantiate objects which represent rows from EMPLOYEES table in HR database. Alongside getters, setters and 
    overloaded constructors, it allows Employee to store itself to database in a simple way by implicitly using Employees class.
    
  Jobs:
     - Contains nested class Job which is used to instantiate and represent rows from JOBS table in HR database.
     Jobs class serves to communicate to JOBS table in HR database. It can get job by either JOB_TITLE or JOB_ID (both in the same method
     so you don't need to worry which one you're passing), as well as by an instance of Employee. Can also get Set of instances of the nested
     class Job by MIN_SALARY, MAX_SALARY or by the combination of those two.
    
Further updates incoming....
