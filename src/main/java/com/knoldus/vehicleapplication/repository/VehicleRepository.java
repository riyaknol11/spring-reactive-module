package com.knoldus.vehicleapplication.repository;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.knoldus.vehicleapplication.dto.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Repository
public class VehicleRepository {

    private final CollectionReference collection;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

@Autowired
    public VehicleRepository(CollectionReference collection) {
        this.collection = collection;
    }

    public Flux<Vehicle> findByBrand(String brand) {
        return Flux.create(emitter -> {
            ApiFuture<QuerySnapshot> future = collection.whereEqualTo("brand", brand).get();
            future.addListener(() -> {
                try {
                    QuerySnapshot snapshot = future.get();
                    snapshot.getDocuments().forEach(document -> {
                        emitter.next(mapDocumentToCar(document));
                    });
                    emitter.complete();
                        } catch (Exception e) {
                            emitter.error(e);
                        }

                    }, executorService);
        }, FluxSink.OverflowStrategy.BUFFER);
    }
    private Vehicle mapDocumentToCar(QueryDocumentSnapshot document) {
        Vehicle car = new Vehicle();
        car.setBrand(document.getId());
        car.setCarModel(document.getString("model"));
        car.setLocation(document.getString("location"));
        car.setYear(document.getLong("year").intValue());
        return car;
    }

    public Flux<String> getAllBrands() {
        return Flux.create(emitter -> {
            ApiFuture<QuerySnapshot> future = collection.get();
            future.addListener(
                    () -> {
                        try {
                            QuerySnapshot snapshot = future.get();
                            for (QueryDocumentSnapshot document : snapshot.getDocuments()) {
                                String brand = document.getString("brand");
                                if (brand != null) {
                                    emitter.next(brand);
                                }
                            }
                            emitter.complete();
                        } catch (Exception e) {
                            emitter.error(e);
                        }
                    }, executorService);
        }, FluxSink.OverflowStrategy.BUFFER);
    }
}
