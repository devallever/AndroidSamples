package com.allever.mysimple.FirstAndroid.chapter9Network;

import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.allever.mysimple.FirstAndroid.chapter2.FirstAndroidBaseActivity;
import com.allever.mysimple.R;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by allever on 17-4-13.
 */

public class XMLParseActivity extends FirstAndroidBaseActivity implements View.OnClickListener {

    private static final String TAG = "XMLParseActivity";
    
    private Button btn_pull;
    private Button btn_sax;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml_parse_activity_layout);
        
        btn_pull = (Button)findViewById(R.id.id_xml_parse_pull);
        btn_pull.setOnClickListener(this);

        btn_sax = (Button)findViewById(R.id.id_xml_parse_sax);
        btn_sax.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String result = getXMLdata();
        Toast.makeText(this,result,Toast.LENGTH_SHORT).show();
        if (id == R.id.id_xml_parse_pull) {
            Log.d(TAG, result);
            parseXMLWithPull(result);
        } else if (id == R.id.id_xml_parse_sax) {
            parseXMLWithSax(result);
        }
    }

    private void parseXMLWithSax(String xmlData){
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            XMLReader xmlReader = saxParserFactory.newSAXParser().getXMLReader();
            SaxParseHandler handler = new SaxParseHandler(this);
            xmlReader.setContentHandler(handler);
            //开始解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        }catch (SAXException saxe){
            saxe.printStackTrace();
        }catch (ParserConfigurationException pe){
            pe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType = xmlPullParser.getEventType();
            Log.d(TAG, "eventType = " +eventType);
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT){ ///1
                String nodeName = xmlPullParser.getName();
                Log.d(TAG, "nodeName = " + nodeName);
                Log.d(TAG, "eventType = " + eventType);
                switch (eventType){
                    case XmlPullParser.START_TAG: //2
                        if (nodeName.equals("id")) {
                            id = xmlPullParser.nextText();
                        }else if (nodeName.equals("name")){
                            name = xmlPullParser.nextText();
                        }else if (nodeName.equals("version")){
                            version = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG: //3
                        if (nodeName.equals("app")){
                            Log.d(TAG, "id = " + id);
                            Log.d(TAG, "name = " + name);
                            Log.d(TAG, "version = " + version);
                            Toast.makeText(this,"id = " + id + "\n" + "name = " + name + "\n" +"version = " + version,Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
                eventType = xmlPullParser.next();
            }
        }catch (XmlPullParserException xmle){
            xmle.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
    
    
    private String getXMLdata(){
        String result = null;
        StringBuilder stringBuilder = new StringBuilder();
        
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(Environment.getExternalStorageDirectory() + "/xmlData.xml")));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n");
            }
            
        }catch (FileNotFoundException fnfe){
            fnfe.printStackTrace();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
            
        }
        return stringBuilder.toString();
    }
    
}
