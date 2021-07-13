package sg.edu.rp.c346.id19020125.demofilereadwriting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnRead, btnWrite;
    TextView tv;

    String folderLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        btnRead = findViewById(R.id.btnRead);
        btnWrite = findViewById(R.id.btnWrite);
        tv = findViewById(R.id.tv);

        //Folder creation
//        folderLocation = getFilesDir().getAbsolutePath() + "/MyFolder";
        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyFolder";
        Log.i("File location", folderLocation);
        File folder = new File(folderLocation);
        if(folder.exists() == false) {
            boolean result = folder.mkdir(); //true if the directory was created, false otherwise
            if(result) {
                Log.i("File read/write", "Folder created");

            }
            else {
                Log.i("File read/write", "Folder not created");
            }
        }

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String folder = getFilesDir().getAbsolutePath()+"/MyFolder";
                String folder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyFolder";
                File targetFile = new File(folder, "data.txt");
                if(targetFile.exists() == true) {
                    String data = "";
                    try{
                        FileReader reader = new FileReader(targetFile);
                        BufferedReader br = new BufferedReader(reader);

                        String line = br.readLine();
                        while (line != null) {
                            data += line + "\n";
                            line = br.readLine();
                        }
                        tv.setText(data);
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Failed to read", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                    Log.d("Content",data);
                }

            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
//                    String folder = getFilesDir().getAbsolutePath()+"/MyFolder";
                    String folder = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MyFolder";
                    File targetFile = new File(folder, "data.txt");
                    FileWriter writer = new FileWriter(targetFile, true);
                    writer.write("test data" + "\n");
                    writer.flush();
                    writer.close();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


    }

    private boolean checkPermission() {
        int permissionCheck_write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheck_read = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck_write == PermissionChecker.PERMISSION_GRANTED || permissionCheck_read == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            return false;
        }
    }
}