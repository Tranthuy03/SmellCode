import java.util.*;

// Message class - Focuses only on storing message data
class Message {
    private final String content;
    private final String sender;
    private final String recipient;

    public Message(String content, String sender, String recipient) {
        this.content = content;
        this.sender = sender;
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getRecipient() {
        return recipient;
    }
}

// Interface for Message Storage - Allows flexibility for future changes (e.g., database, cloud storage)
interface MessageStorage {
    void saveMessage(Message message);
    List<Message> getMessagesForRecipient(String recipient);
}

// Concrete implementation using HashMap
class InMemoryMessageStorage implements MessageStorage {
    private final Map<String, List<Message>> inbox = new HashMap<>();

    @Override
    public void saveMessage(Message message) {
        inbox.computeIfAbsent(message.getRecipient(), k -> new ArrayList<>()).add(message);
    }

    @Override
    public List<Message> getMessagesForRecipient(String recipient) {
        return inbox.getOrDefault(recipient, new ArrayList<>());
    }
}

// Service class - Handles messaging logic
class MessagingService {
    private final MessageStorage storage;

    public MessagingService(MessageStorage storage) {
        this.storage = storage;
    }

    public void sendMessage(String content, String sender, String recipient) {
        Message message = new Message(content, sender, recipient);
        storage.saveMessage(message);
    }

    public List<Message> getMessagesForRecipient(String recipient) {
        return storage.getMessagesForRecipient(recipient);
    }
}

// Separate class for Printing - Keeps Message class clean
class MessagePrinter {
    public static void printMessages(List<Message> messages) {
        if (messages.isEmpty()) {
            System.out.println("No messages found.");
            return;
        }
        for (Message message : messages) {
            System.out.println("From: " + message.getSender() + " -> To: " + message.getRecipient());
            System.out.println("Content: " + message.getContent());
            System.out.println("-------------------------");
        }
    }
}

// Main class
public class CommunicationManager_V2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MessageStorage storage = new InMemoryMessageStorage();
        MessagingService messagingService = new MessagingService(storage);

        while (true) {
            System.out.println("\n===== Messaging System =====");
            System.out.println("1. Send a Message");
            System.out.println("2. View Messages by Recipient");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter sender name: ");
                    String sender = scanner.nextLine();

                    System.out.print("Enter recipient name: ");
                    String recipient = scanner.nextLine();

                    System.out.print("Enter message content: ");
                    String content = scanner.nextLine();

                    messagingService.sendMessage(content, sender, recipient);
                    System.out.println("Message sent successfully!");
                    break;

                case 2:
                    System.out.print("Enter recipient name to view messages: ");
                    String searchRecipient = scanner.nextLine();

                    List<Message> messages = messagingService.getMessagesForRecipient(searchRecipient);
                    System.out.println("\nMessages for " + searchRecipient + ":");
                    MessagePrinter.printMessages(messages);
                    break;

                case 3:
                    System.out.println("Exiting messaging system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
