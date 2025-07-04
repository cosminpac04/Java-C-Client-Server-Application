//package motorcycle;
//
//import motorcycle.network.NotificationServer;
//import motorcycle.util.HibernateUtil;
//
//public class Main {
//    public static void main(String[] args) {
//        // Initialize Hibernate
//        System.out.println("Initializing database connection...");
//        HibernateUtil.getSessionFactory();
//
//        // Start the notification server
//        System.out.println("Starting notification server...");
//        NotificationServer.start();
//    }
//}
//
//


package motorcycle;

import motorcycle.network.NotificationServer;
import motorcycle.rest.AppConfig;
import motorcycle.util.HibernateUtil;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class Main {
    private static final String BASE_URI = "http://0.0.0.0:8080/";

    public static void main(String[] args) throws IOException {
        boolean startNotificationServer = true;
        boolean startRestServer = true;
        
        // Process command line arguments
        if (args.length > 0) {
            for (String arg : args) {
                if (arg.equals("--no-notification")) {
                    startNotificationServer = false;
                    System.out.println("Notification server disabled via command line");
                } else if (arg.equals("--notification-only")) {
                    startRestServer = false;
                    System.out.println("Running notification server only");
                } else if (arg.equals("--rest-only")) {
                    startNotificationServer = false;
                    System.out.println("Running REST server only");
                } else if (arg.equals("--help")) {
                    printHelp();
                    return;
                }
            }
        }

        try {
            // Initialize Hibernate
            System.out.println("Initializing Hibernate...");
            HibernateUtil.getSessionFactory();
            System.out.println("Hibernate initialized successfully");

            // Start Notification Server if enabled
            if (startNotificationServer) {
                System.out.println("Starting Notification Server...");
                NotificationServer.start();
                System.out.println("Notification Server started successfully on port 12345");
            }

            // Start REST Server if enabled
            if (startRestServer) {
                System.out.println("\n=== Starting REST API ===");
                
                try {
                    // Specifically try to create AppConfig to identify any issues
                    System.out.println("Step 0: About to create AppConfig...");
                    motorcycle.rest.AppConfig appConfig = null;
                    try {
                        appConfig = new motorcycle.rest.AppConfig();
                        System.out.println("Step 1: AppConfig created successfully");
                    } catch (Exception e) {
                        System.err.println("ERROR creating AppConfig: " + e.getClass().getName() + ": " + e.getMessage());
                        e.printStackTrace();
                        return;
                    }
                    
                    System.out.println("Step 2: Creating URI object...");
                    URI serverUri = URI.create(BASE_URI);
                    System.out.println("Step 3: URI created: " + serverUri);

                    System.out.println("Step 4: Creating HTTP server with Grizzly...");
                    System.out.println("  - Using URI: " + serverUri);
                    System.out.println("  - Using AppConfig: " + appConfig.getClass().getName());
                    
                    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                            serverUri,
                            appConfig,
                            true  // Start the server
                    );
                    System.out.println("Step 5: Server object created");

                    if (server == null) {
                        System.err.println("ERROR: Server object is null after creation");
                        return;
                    }
                    System.out.println("Step 6: Server object is not null");

                    if (!server.isStarted()) {
                        System.err.println("ERROR: Server failed to start - isStarted() returned false");
                        return;
                    }
                    System.out.println("Step 7: Server is started");

                    System.out.println("\n=== REST Server Started Successfully! ===");
                    System.out.println("Server details:");
                    System.out.println("- Base URI: " + BASE_URI);
                    System.out.println("- Server status: " + (server.isStarted() ? "Running" : "Not running"));
                    System.out.println("- Server listeners: " + server.getListeners().size() + " active listener(s)");
                    System.out.println("- Server class: " + server.getClass().getName());
                    System.out.println("\nTry accessing these endpoints:");
                    System.out.println("- http://localhost:8080/api/races/ping");
                    System.out.println("- http://localhost:8080/api/races/test");
                    System.out.println("- http://localhost:8080/api/races");

                    // Add a shutdown hook
                    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                        System.out.println("\nShutting down servers...");
                        server.shutdownNow();
                        System.out.println("Servers shut down successfully");
                    }));

                    // Keep the server running
                    System.out.println("\nServer is running. Press Ctrl+C to stop...");
                    Thread.currentThread().join();

                } catch (Exception e) {
                    System.err.println("\n=== Error starting REST server ===");
                    System.err.println("Error type: " + e.getClass().getName());
                    System.err.println("Error message: " + e.getMessage());
                    System.err.println("Stack trace:");
                    e.printStackTrace();
                    System.err.println("=== End of error details ===\n");
                    return;
                }
            } else if (startNotificationServer) {
                // If only notification server is running, keep the main thread alive
                System.out.println("\nNotification Server is running. Press Ctrl+C to stop...");
                try {
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    System.err.println("Main thread interrupted: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error in main: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void printHelp() {
        System.out.println("Motorcycle Contest Application");
        System.out.println("Available command line arguments:");
        System.out.println("  --no-notification    Start without the notification server");
        System.out.println("  --notification-only  Start only the notification server (no REST API)");
        System.out.println("  --rest-only          Start only the REST API (no notification server)");
        System.out.println("  --help               Display this help message");
        System.out.println("\nDefault behavior (no args): Start both servers");
    }
}

//public class Main {
//    public static final String BASE_URI = "http://localhost:8080/api/";
//
//    public static void main(String[] args) {
//        System.out.println("Initializing Hibernate...");
//        HibernateUtil.getSessionFactory();
//        System.out.println("Hibernate initialized.");
//
//        System.out.println("Starting REST API...");
//        final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
//                URI.create(BASE_URI),
//                new AppConfig()
//        );
//        System.out.println("Server started at: " + BASE_URI);
//        try {
//            Thread.currentThread().join();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//    }
//}


