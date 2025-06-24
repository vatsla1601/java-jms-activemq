📨 Java JMS ActiveMQ Demo
This project demonstrates a simple Producer-Consumer messaging system using Java, ActiveMQ, and JMS API.

📁 Project Structure
arduino
Copy
Edit
org.example.jmsdemo
├── config         // For connection factory or broker-related setup
├── producer       // For producer logic
├── consumer       // For consumer logic
├── model          // If you have any custom object/message types
├── util           // For reusable utility classes (e.g., logging, helper methods)
✅ Why Use Logger Instead of System.out.println()?
➕ Benefits:
Better formatting

Built-in log levels: info, warning, severe

Easy to redirect to files or monitoring systems later

📌 Example:
java
Copy
Edit
Logger logger = Logger.getLogger(HelloWorldConsumer.class.getName());
logger.info("Message received!");
Console Output:

yaml
Copy
Edit
Jun 23, 2025 4:42:12 PM org.example.jmsDemo.consumer.HelloWorldConsumer onMessage
INFO: Message received!
🔍 What does this line do?
java
Copy
Edit
Logger logger = Logger.getLogger(HelloWorldConsumer.class.getName());
Associates a logger with the fully qualified class name.

Helps group, filter, and format logs in a structured way.

✅ Interface Implementation in Java
Q: What happens when a class implements an interface?
A: You must provide concrete implementations for all interface methods unless the class is declared as abstract.

✅ Exception Handling
☑️ Checked Exceptions (like JMSException)
Must be handled using try-catch or declared with throws

Compile-time enforced

java
Copy
Edit
try {
    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
} catch (JMSException e) {
    e.printStackTrace();
}
❌ Unchecked Exceptions (like NullPointerException)
Not required to handle

Runtime errors

🔴 Log Levels in java.util.logging.Logger
Log Level	Description
FINEST	Most detailed debug info
FINER	Detailed tracing
FINE	Basic debug info
CONFIG	Configuration details
INFO	General informational messages
WARNING	Something went wrong, but not critical
SEVERE	Serious failure or critical issue

Use logger.severe("Something bad happened"); for errors.

❓ Why not logger.error()?
Because java.util.logging doesn’t use error. Instead:

Use logger.severe(...) for critical errors

In frameworks like Log4j or SLF4J:

You'd use logger.error(...)

🧠 Common Questions
❓Q: Why was logger.info("abc") showing error?
A: It was outside a method or static block. Logging statements should be inside a method, like run() or main().

❓Q: Why did TextMessage.getText() require try-catch?
A: Because getText() throws a JMSException, which is a checked exception.

❓Q: Why wasn't the consumer receiving messages?
A: Producer and Consumer were running in different JVMs with vm://localhost URI, each starting its own embedded broker.

✅ Fix: Use an External Broker (like tcp://localhost:61616)
🔧 How It Helps:
Both producer and consumer connect to the same shared broker

Messages are persisted and visible between them

Avoids slave mode error (lock conflict)

✅ Final Output When Working
When you run the consumer, then the producer, the logs should be:

bash
Copy
Edit
INFO: Recieved.
INFO: text: Message 0
INFO: text: Message 1
INFO: text: Message 2
INFO: text: Message 3
INFO: text: Message 4
This means:

run() log shows consumer is listening

onMessage() logs show messages received from producer

✅ Next Steps You Can Add
 🔁 Retry logic

 📧 Email sending on message received

 📂 Log to a file

 💾 Use message persistence

 📦 Deploy broker externally via Docker or installed service
