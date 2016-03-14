package com.example.ostopd.uploadfiletoserver;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ProfilePhoto extends AppCompatActivity {

    private static int PICK_IMAGE_REQUEST = 1;
    Button uploadImage, submit;
    ImageView imgView;
    public Bitmap bitmap;
    static String authToken;
    String picturePath;
    private Uri filePath;
    String selectedPath = "";
    private String KEY_IMAGE = "photo-0";
    private String KEY_NAME = "name";
    public String UPLOAD_URL = "http://52.70.236.212:8080/service/user/photo/add";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_photo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgView = (ImageView) findViewById(R.id.imgView);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Upload upload = new Upload();
                upload.execute();
                Bitmap image = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
            }
        });

        uploadImage = (Button) findViewById(R.id.addFiles);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                Uri selectedImageUri = data.getData();
                selectedPath = getPath(selectedImageUri);
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imgView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    class Upload extends AsyncTask<Void, Void, String> {
        String response;

        /*@Override
        protected String doInBackground(Void... params) {

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            String responseFromServer = "";
            String urlString = "http://52.70.236.212:8080/service/user/photo/add";
            try {
                //------------------ CLIENT REQUEST
                Log.d("selectedPath",selectedPath);
                FileInputStream fileInputStream = new FileInputStream(new File(selectedPath));
                // open a URL connection to the Servlet
                URL url = new URL(urlString);
                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Use a post method.
                conn.setRequestMethod("POST");
                conn.setRequestProperty("authToken", "3a744bd0-05a4-473d-b370-01ccf6983309");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name:\"photo-4\";filename=\"" + selectedPath + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
//                conn.connect();
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                Log.d("bytesAvailable", String.valueOf(bytesAvailable));
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                Log.d("bufferSize", String.valueOf(bufferSize));
                buffer = new byte[bufferSize];

                // read file and write it into form...
                Log.d("fileInputStream.read)", String.valueOf(fileInputStream.read(buffer, 0, bufferSize)));
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                bytesRead = bytesAvailable;
                Log.d("bytesRead", String.valueOf(bytesRead));

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    Log.d("bytesRead1", String.valueOf(bytesRead));
                    bytesAvailable = fileInputStream.available();
                    Log.d("bytesAvailable1", String.valueOf(bytesAvailable));
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    Log.d("bufferSize1", String.valueOf(bufferSize));
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.d("bytesRead", String.valueOf(bytesRead));
                }

                *//*buffer = new byte[fileInputStream.available()];
                Log.d("fileInputStream.read(b", String.valueOf(fileInputStream.read(buffer)));
                bytesRead = 0;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    dos.write(buffer, 0, bytesRead);
                }*//*
                // send multipart form data necesssary after file data...
                Log.d("lineEnd",lineEnd);
                Log.d("asdsa",twoHyphens + boundary + twoHyphens + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // close streams
                Log.e("Debug", "File is written");
                fileInputStream.close();
                dos.flush();
                dos.close();

               *//* InputStream inputStream = conn.getInputStream();
                StringBuffer buffer1 = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    buffer1.append(line + "\n");
                }*//*
            } catch (MalformedURLException ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (IOException ioe) {
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            }
            try {
                inStream = new DataInputStream ( conn.getInputStream() );
                String str;

                while (( str = inStream.readLine()) != null)
                {
                    Log.e("Debug","Server Response "+str);
                }
                inStream.close();

            }
            catch (IOException ioex){
                Log.e("Debug", "error: " + ioex.getMessage(), ioex);
            }

            return response;
        }*/

        @Override
        protected String doInBackground(Void... params) {
            String BOUNDRY = "==================================";
            HttpURLConnection conn = null;

            try {

                // These strings are sent in the request body. They provide information about the file being uploaded
                String contentDisposition = "Content-Disposition: form-data; name=\"photo-7\"; filename=\"" +
                        (new File(selectedPath)).getName() + "\"";
                String contentType = "Content-Type: application/octet-stream";

                // This is the standard format for a multipart request
                StringBuffer requestBody = new StringBuffer();
                requestBody.append("--");
                requestBody.append(BOUNDRY);
                requestBody.append('\n');
                requestBody.append(contentDisposition);
                requestBody.append('\n');
                requestBody.append(contentType);
                requestBody.append('\n');
                requestBody.append('\n');
                requestBody.append(new String(getBytesFromFile(new File(selectedPath))));
                requestBody.append("--");
                requestBody.append(BOUNDRY);
                requestBody.append("--");

                // Make a connect to the server
                URL url = new URL(UPLOAD_URL);
                conn = (HttpURLConnection) url.openConnection();

                conn.setRequestProperty("authToken", "0dd5a304-43cd-4889-aa6f-f2e9725dd553");


                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDRY);

                // Send the body
                DataOutputStream dataOS = new DataOutputStream(conn.getOutputStream());
                dataOS.writeBytes(requestBody.toString());
                dataOS.flush();
                dataOS.close();

                // Ensure we got the HTTP 200 response code
                int responseCode = conn.getResponseCode();
                Log.d("responseCode ", String.valueOf(responseCode));
                if (responseCode != 200) {
                    throw new Exception(String.format("Received the response code %d from the URL %s", responseCode, url));
                }

                // Read the response
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(bytes)) != -1) {
                    baos.write(bytes, 0, bytesRead);
                }
                byte[] bytesReceived = baos.toByteArray();
                baos.close();

                is.close();
                response = new String(bytesReceived);

                // TODO: Do something here to handle the 'response' string

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
            Log.d("response",response);

            return response;
        }

        public byte[] getBytesFromFile(File file) throws IOException {
            InputStream is = new FileInputStream(file);

            // Get the size of the file
            long length = file.length();

            // You cannot create an array using a long type.
            // It needs to be an int type.
            // Before converting to an int type, check
            // to ensure that file is not larger than Integer.MAX_VALUE.
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }

            // Create the byte array to hold the data
            byte[] bytes = new byte[(int)length];

            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, Math.min(bytes.length - offset, 1024*1024))) >= 0) {
                offset += numRead;
                Log.d("offset", String.valueOf(offset));
            }


            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "+file.getName());
            }

            // Close the input stream and return bytes
            is.close();
            return bytes;
        }
    }

}

