package com.example.deepakrattan.xmlparsing;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String LOG_TAG = "XML";
    private int UpdateFlag = 0;
    private Horoscope horoscope;
    private ArrayList<Horoscope> horoscopeArrayList;
    private String text;
    private ListView lv;
    private HoroscopeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //lv = (ListView) findViewById(R.id.lv);

        // horoscope = new Horoscope();
        horoscopeArrayList = new ArrayList<>();
        new GetXMLFromServer().execute();

       // adapter = new HoroscopeAdapter(this,horoscopeArrayList);
       /* for (Horoscope h : horoscopeArrayList) {
            String name = h.getName();
            Log.d("name123", name);
            String range = h.getRange();
            Log.d("range123", range);
        }
        lv.setAdapter(adapter);*/


/*
        for (Horoscope h : horoscopeArrayList) {
            String text = h.getText();
            Log.d("Text123", text);
        }*/
    }

    //Parsing XML
    public ArrayList<Horoscope> parseXML(String xmlString) {

        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();

            factory.setNamespaceAware(true);
            xmlPullParser.setInput(new StringReader(xmlString));
            int eventType = xmlPullParser.getEventType();

          /*  while (eventType != XmlPullParser.END_DOCUMENT) {

                if (eventType == XmlPullParser.START_TAG) {

                    String name = xmlPullParser.getName();
                    if (name.equals("Date")) {
                        if (xmlPullParser.next() == XmlPullParser.TEXT) {
                            String dat = xmlPullParser.getText();
                            Log.d(LOG_TAG, "date: " + dat);
                        }
                    }


                    if (name.equals("UpdateFlag")) {

                        String ref = xmlPullParser.getAttributeValue(null, "ref");
                        Log.d(LOG_TAG, "ref:" + ref);

                        if (xmlPullParser.next() == XmlPullParser.TEXT) {
                            String UpdateFlag = xmlPullParser.getText();
                            Log.d(LOG_TAG, "UpdateFlag:" + UpdateFlag);
                        }


                    } else if (name.equals("Name")) {

                        if (xmlPullParser.next() == XmlPullParser.TEXT) {
                            String Name = xmlPullParser.getText();
                            Log.d(LOG_TAG, "Name:" + Name);
                        }
                    } else if (name.equals("Range")) {

                        if (xmlPullParser.next() == XmlPullParser.TEXT) {
                            String Range = xmlPullParser.getText();
                            Log.d(LOG_TAG, "Range:" + Range);
                        }
                    }


                } else if (eventType == XmlPullParser.END_TAG) {


                }
                eventType = xmlPullParser.next();

            }*/
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = xmlPullParser.getName();
                switch (eventType) {
                    //At start of document
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    //At start of a TAG
                    case XmlPullParser.START_TAG:
                        //Get Tag Name

                        if (tagName.equals("Horoscope")) {
                            //Create a new Instance of Horoscope
                            horoscope = new Horoscope();
                        }
                        /*if (tagName.equals("Name")) {
                            String name = xmlPullParser.nextText();
                            Log.d("Name", name);
                            horoscope.setName(name);

                        }
                        if (tagName.equals("Range")) {
                            String range = xmlPullParser.nextText();
                            Log.d("Range", range);
                            horoscope.setName(range);

                        }
                        if (tagName.equals("Text")) {
                            String text = xmlPullParser.nextText();
                            Log.d("Text", text);
                            horoscope.setText(text);

                        }

*/
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equals("Horoscope")) {
                            //Add horoscope object to List
                            horoscopeArrayList.add(horoscope);
                        } else if (tagName.equals("Name")) {
                            horoscope.setName(text);
                        } else if (tagName.equals("Range")) {
                            horoscope.setRange(text);
                        } else if (tagName.equals("Text")) {
                            horoscope.setText(text);
                        }

                    default:
                        break;
                }

                //Jump to next event
                eventType = xmlPullParser.next();

            }


        } catch (Exception e) {
            Log.d(LOG_TAG, "Error in ParseXML()", e);
        }

        return horoscopeArrayList;

    }

    //Getting XML from server

    private class GetXMLFromServer extends AsyncTask<String, Void, String> {

        HttpHandler httpHandler;

        @Override
        protected String doInBackground(String... strings) {

            String URL = "http://androidpala.com/tutorial/horoscope.xml";
            String res = "";
            httpHandler = new HttpHandler();
            InputStream is = httpHandler.CallServer(URL);
            if (is != null) {

                res = httpHandler.StreamToString(is);

            } else {
                res = "NotConnected";
            }

            return res;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result.equals("NotConnected")) {

                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_SHORT).show();

            } else {
                horoscopeArrayList = parseXML(result);
                lv = (ListView) findViewById(R.id.lv);
                adapter = new HoroscopeAdapter(MainActivity.this,horoscopeArrayList);
                lv.setAdapter(adapter);
                /*for (Horoscope h : horoscopeArrayList) {
                    String name = h.getName();
                    Log.d("name123", name);
                    String range = h.getRange();
                    Log.d("range123", range);
                }*/

            }
        }


    }

}
