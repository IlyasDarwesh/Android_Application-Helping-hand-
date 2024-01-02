package com.example.abnormal_person_app;

import android.content.Intent;
import org.opencv.android.Utils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.imgproc.Imgproc;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Fetch_image extends AppCompatActivity {
    private ImageView imageView;
    private Button uploadImageButton;
    private TextView userInfoTextView;
    private Bitmap selectedImageBitmap;

    private static final int PICK_IMAGE_REQUEST_CODE = 1;

    public void onBackPressed() {
        // Disable the back button by not calling super.onBackPressed()
        // You can add any custom logic here if needed
        // For example, show a message to the user or do nothing
        // To re-enable the back button, simply remove this method override

        Intent intent=new Intent(getApplicationContext(),Dashboard.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_image);

        imageView = findViewById(R.id.imageView);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        userInfoTextView = findViewById(R.id.userInfoTextView);

        // Initialize OpenCV (if not already initialized)
        if (!OpenCVLoader.initDebug()) {
            // Handle OpenCV initialization failure
            userInfoTextView.setText("OpenCV initialization failed.");
        }

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();

            try {
                // Convert the selected image URI to a Bitmap
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);

                // Display the selected image
                imageView.setImageBitmap(selectedImageBitmap);

                // Compare the selected image with images in the Firebase Realtime Database
                compareImageWithDatabase(selectedImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void compareImageWithDatabase(final Bitmap selectedImage) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String matchedUserId = null;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String imageUrl = userSnapshot.child("imageUrl").getValue(String.class);

                    // Compare the selected image with the current user's image URL
                    String userId = performImageComparison(selectedImage, imageUrl);

                    if (userId != null) {
                        // A match is found
                        matchedUserId = userId;
                        break;
                    }
                }

                if (matchedUserId != null) {
                    // Fetch user information based on the matched user ID
                    String userInfo = dataSnapshot.child(matchedUserId).child("userId").getValue(String.class);
                    userInfoTextView.setText(userInfo);
                } else {
                    // No record found
                    userInfoTextView.setText("No record found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error, if any
                userInfoTextView.setText("Database error: " + databaseError.getMessage());
            }
        });
    }


    private String performImageComparison(Bitmap selectedImage, String imageUrl) {
        // Load the image from the URL using Firebase or any other method
        Bitmap databaseImage = loadImageFromUrl(imageUrl);

        if (databaseImage == null) {
            return null; // Image loading failed
        }

        // Convert Bitmaps to Mats (OpenCV format)
        Mat matSelected = new Mat(selectedImage.getHeight(), selectedImage.getWidth(), CvType.CV_8UC3);
        Utils.bitmapToMat(selectedImage, matSelected);

        Mat matDatabase = new Mat(databaseImage.getHeight(), databaseImage.getWidth(), CvType.CV_8UC3);
        Utils.bitmapToMat(databaseImage, matDatabase);

        // Perform image comparison (use OpenCV feature matching or other techniques)
        boolean isMatch = featureMatching(matSelected, matDatabase);

        if (isMatch) {
            // Return the user ID or a unique identifier when a match is found
            return "userId"; // Replace with the actual user identifier
        } else {
            return null; // No match found
        }
    }


    private Bitmap loadImageFromUrl(String imageUrl) {
        try {
            // Create a URL object from the imageUrl
            URL url = new URL(imageUrl);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();

            // Read the image data from the connection
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Close the input stream and disconnect the connection
            inputStream.close();
            connection.disconnect();

            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null; // Return null by default (handle this case in your calling code)
    }

    private boolean featureMatching(Mat matSelected, Mat matDatabase) {
        // Implement feature matching using OpenCV
        // Replace this with your actual feature matching code
        // For simplicity, we'll use ORB feature matching as a placeholder

        // Convert images to grayscale
        Mat graySelected = new Mat();
        Mat grayDatabase = new Mat();
        Imgproc.cvtColor(matSelected, graySelected, Imgproc.COLOR_BGR2GRAY);
        Imgproc.cvtColor(matDatabase, grayDatabase, Imgproc.COLOR_BGR2GRAY);

        // Initialize ORB detector and descriptor
        ORB orb = ORB.create();
        MatOfKeyPoint keypointsSelected = new MatOfKeyPoint();
        MatOfKeyPoint keypointsDatabase = new MatOfKeyPoint();
        Mat descriptorsSelected = new Mat();
        Mat descriptorsDatabase = new Mat();

        orb.detectAndCompute(graySelected, new Mat(), keypointsSelected, descriptorsSelected);
        orb.detectAndCompute(grayDatabase, new Mat(), keypointsDatabase, descriptorsDatabase);

        // Match descriptors using a Brute Force Matcher
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptorsSelected, descriptorsDatabase, matches);

        // Calculate the match score based on the number of matches
        double matchScore = (double) matches.rows() / (double) keypointsSelected.rows();

        // Set a threshold for considering it a match
        double threshold = 0.2; // Adjust as needed

        // Return true if the match score is above the threshold
        return matchScore > threshold;
    }

    private String getUserInformation(String userId) {
        // Implement your code to retrieve user information from the database
        // based on the provided user ID and return it as a formatted string
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve and format user information
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String contact = dataSnapshot.child("contact").getValue(String.class);
                    String age = dataSnapshot.child("age").getValue(String.class);

                    // Create a formatted string
                    String userInfo = "User Information:\nName: " + name + "\nContact: " + contact + "\nAge: " + age;

                    // Update the UI with the user information
                    userInfoTextView.setText(userInfo);
                } else {
                    // User not found
                    userInfoTextView.setText("User not found.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error, if any
                userInfoTextView.setText("Database error: " + databaseError.getMessage());
            }
        });

        return ""; // This return value is not used since the information is retrieved asynchronously
    }
}