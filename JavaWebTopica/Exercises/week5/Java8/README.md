# `Problem with old date and time`:
#### - Java Date Time classes are not defined consistently, we have Date Class in both java.util as well as java.sql packages. Again formatting and parsing classes are defined in java.text package.
#### - java.util.Date contains both date and time, whereas java.sql.Date contains only date. Having this in java.sql package doesn’t make sense. Also both the classes have same name, that is a very bad design itself.
#### - There are no clearly defined classes for time, timestamp, formatting and parsing. We have java.text.DateFormat abstract class for parsing and formatting need. Usually SimpleDateFormat class is used for parsing and formatting.
#### - All the Date classes are mutable, so they are not thread safe. It’s one of the biggest problem with Java Date and Calendar classes.
#### - Date class doesn’t provide internationalization, there is no timezone support. So java.util.Calendar and java.util.TimeZone classes were introduced, but they also have all the problems listed above.

# `new Date Time API`:
#### - Immutability: All the classes in the new Date Time API are immutable and good for multithreaded environments.
#### - Separation of Concerns: The new API separates clearly between human readable date time and machine time (unix timestamp). It defines separate classes for Date, Time, DateTime, Timestamp, Timezone etc.
#### - Clarity: The methods are clearly defined and perform the same action in all the classes. For example, to get the current instance we have now() method. There are format() and parse() methods defined in all these classes rather than having a separate class for them.
#### - All the classes use Factory Pattern and Strategy Pattern for better handling. Once you have used the methods in one of the class, working with other classes won’t be hard.
#### - Utility operations: All the new Date Time API classes comes with methods to perform common tasks, such as plus, minus, format, parsing, getting separate part in date/time etc.
#### - Extendable: The new Date Time API works on ISO-8601 calendar system but we can use it with other non ISO calendars as well.