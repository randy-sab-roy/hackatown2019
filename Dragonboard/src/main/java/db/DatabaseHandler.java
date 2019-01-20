package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import line.RowConfig;
import utils.Constants;
import utils.RowMode;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.EventListener;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class DatabaseHandler {
   private static DatabaseHandler ourInstance = new DatabaseHandler();
   private final Firestore firestore;
   private CollectionReference linesReference;
   private EventListener<QuerySnapshot> querySnapshotEventListener;

   public static DatabaseHandler getInstance() {
      return ourInstance;
   }

   private DatabaseHandler() {
      try {
         FileInputStream serviceAccount = new FileInputStream(ClassLoader.getSystemClassLoader().getResource(Constants.AUTH_CONFIG).getFile());
         FirebaseOptions options = new FirebaseOptions.Builder()
               .setCredentials(GoogleCredentials.fromStream(serviceAccount))
               .setDatabaseUrl(Constants.FIRESTORE_URL)
               .build();
         FirebaseApp.initializeApp(options);
      } catch (IOException e) {
         e.printStackTrace();
      }
      firestore = FirestoreClient.getFirestore();
      linesReference = firestore.collection(Constants.BOARDS_KEY)
            .document(Constants.BOARD_ID)
            .collection(Constants.LINES_KEY);
      initListener();
   }

   private void initListener() {
      linesReference.addSnapshotListener((value, error) -> {
         if (querySnapshotEventListener != null)
            querySnapshotEventListener.onEvent(value, error);
      });
   }

   public List<RowConfig> getRowsConfig() {

      final ApiFuture<QuerySnapshot> linesQuery = linesReference.get();
      try {
         return linesQuery.get()
               .getDocuments()
               .stream()
               .map(document -> new RowConfig(Integer.parseInt(document.getId()), RowMode.getRowMode(document.get(Constants.SERVICE_KEY).toString())))
               .collect(Collectors.toList());
      } catch (InterruptedException | ExecutionException e) {
         e.printStackTrace();
         return new ArrayList<>();
      }
   }

   public void setRowConfigChangeEventListener(EventListener<QuerySnapshot> eventListener) {
      this.querySnapshotEventListener = eventListener;
   }
}
