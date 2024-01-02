package com.example.abnormal_person_app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class Add_data_Form extends AppCompatActivity {

    EditText name;
    EditText age;
    EditText contact_number;

    ImageView imageView;
    Button img_btn;
    Button sub_btn;
    private DatabaseReference mDatabase;
    private Uri imageUri; // Store the selected image URI

    private static final int PICK_IMAGE_REQUEST = 1;
    private String userId; // Store the user ID

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
        setContentView(R.layout.activity_add_data_form);

        // Initialize Firebase


        name = findViewById(R.id.fm_name);
        age = findViewById(R.id.fm_age);
        contact_number = findViewById(R.id.Contact_no);
        img_btn = findViewById(R.id.btnSfmImage);
        sub_btn = findViewById(R.id.btnSubmit);
        imageView=findViewById(R.id.fmSelectedImage);
        FirebaseApp.initializeApp(this);

        // Create a reference to the "users" node in Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the gallery to select an image
                openGallery();
            }
        });

        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get user input
                String userName = name.getText().toString();
                String ageText = age.getText().toString(); // Fixed variable name
                String userContact = contact_number.getText().toString();

                // Check if any field is empty or if an image is not selected
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(Add_data_Form.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(ageText)) {
                    Toast.makeText(Add_data_Form.this, "Please enter an age", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userContact)) {
                    Toast.makeText(Add_data_Form.this, "Please enter a contact number", Toast.LENGTH_SHORT).show();
                } else if (imageUri == null) {
                    Toast.makeText(Add_data_Form.this, "Please select an image", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        // Parse age as an int
                        int userAge = Integer.parseInt(ageText); // Fixed variable name

                        // Generate a unique key for the new user
                        userId = databaseReference.push().getKey(); // Store the user ID

                        // Create a User object with the input data, including imageUrl
                        User user = new User(userName, userAge, userContact, ""); // Initialize imageUrl as an empty string

                        // Save the user data to the database under the generated key
                        if (userId != null) {
                            databaseReference.child(userId).setValue(user);
                            Toast.makeText(Add_data_Form.this, "User data saved", Toast.LENGTH_SHORT).show();

                            // Now, you can add code to navigate to the next activity or perform other actions.
                            // For example, you can navigate back to the main activity.
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Add_data_Form.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where the age cannot be parsed as an integer
                        Toast.makeText(Add_data_Form.this, "Invalid age format", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            String imagePath = imageUri.toString();
            // Now you have the URI of the selected image (imageUri).
            // You can use it for further processing, such as uploading it to Firebase Cloud Storage.
            uploadImageToStorage(); // Call the method to upload the image
        }
    }

    private void uploadImageToStorage() {
        // Upload the image to Firebase Cloud Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("profile_images/" + userId + ".jpg"); // Use a unique file name per user

        imageRef.putFile(imageUri) // Replace imageUri with the Uri of the selected image
                .addOnSuccessListener(taskSnapshot -> {
                    // Image uploaded successfully, get the download URL
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();

                        // Now you have the imageUrl, update the Realtime Database
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");
                        userRef.child(userId).child("imageUrl").setValue(imageUrl);
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle image upload failure
                    Toast.makeText(Add_data_Form.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                });
    }
}
