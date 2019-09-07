package com.allever.mysimple.FirstAndroid.chapter9Network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by allever on 17-4-13.
 */

public class SaxParseHandler extends DefaultHandler {
    private static final String TAG = "SaxParseHandler";
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private String nodeName;

    private Context context;

    public SaxParseHandler(Context context){
        this.context = context;
    }

    @Override
    public void startDocument() throws SAXException {
        //super.startDocument();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //super.startElement(uri, localName, qName, attributes);
        Log.d(TAG, "startElement: localeName = " + localName);
        Log.d(TAG, "startElement: qName = " + qName);
        nodeName = localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //super.characters(ch, start, length);
        Log.d(TAG, "characters: " + new StringBuffer().append(ch, start,length));
        if (nodeName.equals("id")){
            id.append(ch,start,length);
        }else if (nodeName.equals("name")){
            name.append(ch,start,length);
        }else if (nodeName.equals("version")){
            version.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);
        if (nodeName.equals("app")){
            Log.d(TAG, "id = " + id.toString().trim());
            Log.d(TAG, "name = " + name.toString().trim());
            Log.d(TAG, "version = " + version.toString().trim());
            //Toast.makeText(context,"id = " + id.toString().trim() + "\n" + "name = " + name.toString().trim() + "\n" + "version = " + version.toString().trim(),Toast.LENGTH_SHORT).show();
            //清空StringBuilder
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
