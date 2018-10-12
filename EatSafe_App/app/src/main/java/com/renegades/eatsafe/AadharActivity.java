package com.renegades.eatsafe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class AadharActivity extends AppCompatActivity {

    private Button btScan;
    SurfaceView cameraPreview;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 100;
    TextView tvInfo,tvResult;
    String uid,name,gender,yob,house,street,co,lm,vtc,po,dist,subdist,state,pc,dob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhar);

        name="";
        gender="";
        state="";
        dob="";
        yob="";

        btScan = findViewById(R.id.btScan);

        final Activity activity = this;

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(false);
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()==null){
                Toast.makeText(this, "Scan cancelled", Toast.LENGTH_SHORT).show();
            }else{
                String info = result.getContents();
                xmlparsing(info);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void xmlparsing(String info) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            assert db != null;
            doc = db.parse(new ByteArrayInputStream(info.getBytes("utf-8")));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert doc != null;
        NodeList nlRecords = doc.getElementsByTagName("PrintLetterBarcodeData");
        int num = nlRecords.getLength();
        for (int i = 0; i < num; i++) {
            Element node = (Element) nlRecords.item(i);

            // get a map containing the attributes of this node
            NamedNodeMap attributes = node.getAttributes();

            // get the number of nodes in this map
            int numAttrs = attributes.getLength();

            for (int j = 0; j < numAttrs; j++) {
                Attr attr = (Attr) attributes.item(j);

                String attrName = attr.getNodeName();
                String attrValue = attr.getNodeValue();

                // Do your stuff here
                if (attrName.contains("uid")) {
                    uid = attrValue;
                    Log.d("UID",uid);
                } else if (attrName.contains("name")) {
                    name = attrValue;
                } else if (attrName.contains("gender")) {
                    gender = attrValue;
                } else if (attrName.contains("yob")) {
                    yob = attrValue;
                } else if (attrName.contains("co")) {
                    co = attrValue;
                } else if (attrName.contains("house")) {
                    house = attrValue;
                } else if (attrName.contains("street")) {
                    street = attrValue;
                } else if (attrName.contains("lm")) {
                    lm = attrValue;
                } else if (attrName.contains("vtc")) {
                    vtc = attrValue;
                } else if (attrName.contains("po")) {
                    po = attrValue;
                } else if (attrName.contains("dist")) {
                    dist = attrValue;
                } else if (attrName.contains("subdist")) {
                    subdist = attrValue;
                } else if (attrName.contains("state")) {
                    state = attrValue;
                } else if (attrName.contains("pc")) {
                    pc = attrValue;
                } else if (attrName.contains("dob")) {
                    dob = attrValue;
                }

                if (name != null && dob != null && state != null && gender != null) {
                    Intent intent = new Intent(AadharActivity.this, InfoActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("yob", yob);
                    intent.putExtra("state", state);
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                }
            }

        }



    }
}
