package com.knoldus.vehicleapplication.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FireStoreConfig {

    /**
     * The location of the Google Cloud credentials file.
     */
    @Value("${google.application.credentials}")
    private String credentialsLocation;

    /**
     * Creates a Firestore instance using the specified
     * Google Cloud service account credentials.
     *
     * @return Firestore instance
     * @throws IOException if an error occurs while reading the credentials file
     */
    @Bean
    public Firestore firestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(
                credentialsLocation);

        FirestoreOptions firestoreOptions = FirestoreOptions
                .getDefaultInstance().toBuilder()
                .setCredentials(GoogleCredentials
                        .fromStream(serviceAccount))
                .build();

        return firestoreOptions.getService();
    }

    @Bean
    public CollectionReference carCollection(Firestore firestore) {
        return firestore.collection("vehicle");
    }
}
