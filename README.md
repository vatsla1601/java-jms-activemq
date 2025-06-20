# java-jms-activemq
A beginner-friendly Java JMS demo using ActiveMQ to send and receive messages via queues using multithreading.

# JMS Message Demo

A basic Java application demonstrating the use of JMS (Java Message Service) with Apache ActiveMQ using multithreading.

## 🔧 Technologies Used
- Java 11
- Apache ActiveMQ
- Maven
- IntelliJ IDEA

## 📁 Project Structure
- `App.java` — Main runner that starts multiple producer and consumer threads.
- `HelloWorldProducer.java` — Sends messages to the queue.
- `HelloWorldConsumer.java` — Listens and receives messages from the queue.

## 💡 What You'll Learn
- Creating a JMS connection, session, and queue
- Using `MessageProducer` and `MessageConsumer`
- Managing multithreaded message sending and receiving
- How timeout and queue mismatches behave
- Best practices for resource cleanup

## 🧠 Doubts and Clarifications
JMS Code Walkthrough – Doubts and Clarifications
📌 Initial Code Overview
You implemented a basic JMS project using the App.java file that had HelloWorldProducer and HelloWorldConsumer classes, both implementing Runnable. Threads were started from the main() method.
________________________________________
❓ Why implements Runnable?
Your Query: What does implements Runnable mean?
Answer: It allows the class to define a run() method, which is the entry point for code when a new thread is started using that object. So, new HelloWorldProducer() passed into thread(...) runs HelloWorldProducer.run() in a new thread.
________________________________________
❓ How do producer.send() and consumer.receive() work?
Your Query: When I call send, how does receive know?
Answer: Both connect to the same queue name (TEST.FOO) via the same JMS broker. The broker manages delivery. The producer sends a message into the queue, and the consumer pulls it from the queue.
________________________________________
❓ Logical flow confusion: Connection → Session → Destination → Consumer
Your Query: The nesting of connection, session, destination confused me.
Answer: Think of it as setting up a call:
•	Connection = open the line
•	Session = workspace to send/receive
•	Destination = the target (queue/topic)
•	Producer/Consumer = send or listen to messages
________________________________________
❓ Why are my thread names the same?
Your Query: Sent and Received both say Thread-0
Answer: Threads are created in sequence. If the first thread started is a producer, and the next is a consumer, they may still be called Thread-0, Thread-1, etc., depending on thread scheduling.
________________________________________
❓ Why Destination destination = session.createQueue(...) and not Queue destination = ...?
Answer: Destination is the interface for both Queue and Topic. It keeps code generic. If you change from queue to topic, no changes in code logic needed.
________________________________________
❓ What is the 1000 in consumer.receive(1000)?
Answer: It’s a timeout in milliseconds. The consumer waits up to 1 second for a message. If no message arrives, it returns null.
________________________________________
❓ Why do I need to call close() on consumer/session/connection?
Answer: To release resources:
•	consumer.close() – closes message listener
•	session.close() – releases JMS session resources
•	connection.close() – closes network connection
Producer is not closed explicitly in your code, but you should do it:
producer.close();
To prevent memory/resource leaks.
________________________________________
❓ Does closing the connection also close the session?
Answer: Not automatically. It invalidates the session, but doesn’t clean it up properly. Always explicitly close the session before the connection.
________________________________________
❓ What does “closing connection ends the physical network link” mean?
Answer: It’s like hanging up a call. It cuts off the socket/pipe to the JMS broker. No more communication.
________________________________________
❓ Example of closing connection but not session?
Answer:
Connection conn = factory.createConnection();
Session session = conn.createSession(...);
conn.close();
// Now session is unusable
session.createConsumer(...); // ❌ Throws IllegalStateException
________________________________________
❓ Why HelloWorldConsumer implements ExceptionListener?
Answer: JMS uses this to notify your app asynchronously if something goes wrong (e.g., broker failure). It doesn’t catch exceptions from receive() – it’s for background issues.
connection.setExceptionListener(this);
________________________________________
❓ Added a second message (HAHAHA) but not received?
Answer: You only called receive(1000) once. So only one message was pulled. Add a loop to receive all:
Message message;
while ((message = consumer.receive(1000)) != null) {
   // process
}
________________________________________
❓ Producer and Consumer queues are different — no error?
Answer: Correct. JMS doesn’t throw an error — the consumer just listens to a different box and gets no messages.
________________________________________
❓ Why didn’t HAHAHA message print the actual text?
Answer: You printed the TextMessage object:
System.out.println(dummyMessage);
This gives metadata. Use:
System.out.println(dummyMessage.getText());
To print actual message.
________________________________________


## 📜 License
This project is for learning/demo purposes. No license attached.
