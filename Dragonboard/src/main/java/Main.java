import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import metro.MetroStatus;
import serial.SerialMessageHandler;
import serial.serial_messages.BusMessage;
import serial.serial_messages.ClearMessage;
import serial.serial_messages.MetroMessage;
import serial.serial_messages.TrafficMessage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class Main {


   public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
      final SerialMessageHandler serialMessageHandler = SerialMessageHandler.getInstance();
      serialMessageHandler.setCommPort("/dev/cu.wchusbserial1460");
      final Scanner scanner = new Scanner(System.in);

      Thread.sleep(3000);

      serialMessageHandler.sendMessage(new MetroMessage(0, new MetroStatus(true, false, false, true)));
      serialMessageHandler.sendMessage(new MetroMessage(1, new MetroStatus(true, true, true, true)));
      scanner.next();
      serialMessageHandler.sendMessage(new ClearMessage(0));
      serialMessageHandler.sendMessage(new ClearMessage(1));

//      serialMessageHandler.sendMessage(new TrafficMessage(0, 1));
//      scanner.next();
//      serialMessageHandler.sendMessage(new BusMessage(0, 0.5));
//      scanner.next();
//      serialMessageHandler.sendMessage(new ClearMessage(0));
//      serialMessageHandler.sendMessage(new ClearMessage(0));


//      // Write a message to the database
//      FileInputStream serviceAccount = new FileInputStream(Main.class.getClassLoader().getResource("ulate-6625b-ad396ad2785a.json").getFile());
//
//      FirebaseOptions options = new FirebaseOptions.Builder()
//            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
////            .setCredentials(GoogleCredentials.getApplicationDefault())
//            .setDatabaseUrl("https://ulate-6625b.firebaseio.com/")
//            .build();
//      FirebaseApp.initializeApp(options);
//
//      Firestore database = FirestoreClient.getFirestore();
//
////      database.getCollections().forEach(ref -> System.out.println(ref.getId()));
//
//      database.collection("boards")
//            .document("7qOUSBuRUMwagevZd8ch")
//            .collection("lines")
//            .addSnapshotListener((value, error) -> {
//               for (QueryDocumentSnapshot document : value.getDocuments()) {
//                  System.out.println(document.getData());
//               }
//            });
//
//      myRef.setValue("Hello, World!");
   }

}
