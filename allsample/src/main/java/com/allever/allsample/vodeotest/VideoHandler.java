package com.allever.allsample.vodeotest;


import com.allever.allsample.vodeotest.model.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Created by allever on 17-10-11.
 * SAX解析xml
 */
public class VideoHandler extends DefaultHandler {

    private final String LANGUAGE_EN_US = "en-us";
    private final String LANGUAGE_AR = "ar";
    private final String LANGUAGE_ZH_CN = "zh-cn";
    private final String LANGUAGE_ZH_TW = "zh-tw";
    private final String LANGUAGE_NL = "nl";
    private final String LANGUAGE_ES = "es";
    private final String LANGUAGE_FI = "fi";
    private final String LANGUAGE_DT = "dt";
    private final String LANGUAGE_JA = "ja";
    private final String LANGUAGE_KO = "ko";
    private final String LANGUAGE_PT = "pt";
    private final String LANGUAGE_RU = "ru";
    private final String LANGUAGE_SW = "sw";
    private final String LANGUAGE_FR = "fr";
    private final String LANGUAGE_IT = "it";
    private final String LANGUAGE_TR = "tr";
    private final String LANGUAGE_PL = "pl";
    private final String LANGUAGE_WE = "we";
    private final String LANGUAGE_BA = "ba";
    private final String LANGUAGE_VI = "vi";
    private final String LANGUAGE_SO = "so";
    private final String LANGUAGE_DA = "da";
    private final String LANGUAGE_MS = "ms";
    private final String LANGUAGE_TH = "th";

    private XMLParserListener mXMLParserListener;

    private MapData mMapData = null;
    private Map<Integer, Source> mSourceMap;
    private Playlist mPlayList;
    private Map<Integer, Atom> mAtomMap;
    private LanguageDictionary mLanguageDictionary;
    private PackageInfo mPackageInfo;

    private List<PlayElement> mPlayElement;
    private Map<Integer, Level> mLevelMap;
    private Map<Integer, Event> mEventMap;
    private Map<Integer, DescString> mDescStringMap;
    private Map<LanguageType, String> mDescContentMap;

    private int mAtomId = -1;
    private int mLevelId = -1;
    private int mTime = -1;
    private int mStringId = -1;

    private String mQName = "";

    private StringBuilder mDescContentBuilder;

    public VideoHandler(XMLParserListener xmlParserListener){
        mSourceMap = new HashMap<>();
        mMapData = new MapData();
        mPlayList = new Playlist();
        mPlayElement = new ArrayList<>();
        mAtomMap = new HashMap<>();
        mLevelMap = new HashMap<>();
        mEventMap = new HashMap<>();
        mLanguageDictionary = new LanguageDictionary();
        mDescStringMap = new HashMap<>();
        mXMLParserListener = xmlParserListener;
        mDescContentBuilder = new StringBuilder();
        mPackageInfo = new PackageInfo();
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        //System.out.println();
        //System.out.println("startElement");

        mQName = qName;
        String value = "";
        if (qName.equalsIgnoreCase("PackageInfo")){
            for (int i=0; i<attributes.getLength();i++){
                value = attributes.getValue(i);
                switch (attributes.getQName(i)){
                    case "version":
                        mPackageInfo.setVersion(Float.valueOf(value));
                        break;
                    case "title":
                        mPackageInfo.setTitle(value);
                        break;
                    case "isflat":
                        mPackageInfo.setFlat(Boolean.valueOf(value));
                        break;
                    case "iscoached":
                        mPackageInfo.setCoached(Boolean.valueOf(value));
                        break;
                }
            }
        }

        value = "";
        if (qName.equalsIgnoreCase("String")){
            //System.out.println("开始解析String");
            mDescContentMap = new HashMap<>();
            DescString descString = new DescString();
            for (int i=0; i<attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                switch (attributes.getQName(i)){
                    case "id":
                        mStringId = Integer.valueOf(value);
                        descString.setId(mStringId);
                        break;
                    case "type":
                        descString.setType(value);
                        break;
                }
            }
            if (mStringId != -1) mDescStringMap.put(mStringId,descString);
        }

        if (qName.equalsIgnoreCase("Sources")){
            //System.out.println("开始Sources");
        }

        value = "";
        if (qName.equalsIgnoreCase("Source")){
            //System.out.println();
            Source source = new Source();
            int sourceId = -1;
            for (int i=0; i<attributes.getLength();i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                switch (attributes.getQName(i)){
                    case "type":
                        source.setType(value);
                        break;
                    case "source":
                        source.setSource(value);
                        break;
                    case "stringid":
                        source.setStringId(Integer.valueOf(value));
                        break;
                    case "duration":
                        source.setDuration(Integer.valueOf(value));
                        break;
                    case "id":
                        sourceId = Integer.valueOf(value);
                        source.setId(sourceId);
                        break;
                }
            }
            if (sourceId != -1){
                mSourceMap.put(sourceId,source);
            }
        }

        value = "";
        if (qName.equalsIgnoreCase("Playlist")){
            //System.out.println("开始解析Playlist");
            for (int i=0; i < attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                switch (attributes.getQName(i)){
                    case "namestringid":
                        mPlayList.setNameStringId(Integer.valueOf(value));
                        break;
                    case "locationstringid":
                        mPlayList.setLocationStringId(Integer.valueOf(value));
                        break;
                    case "locationdescriptionstringid":
                        mPlayList.setLocationDescriptionStringId(Integer.valueOf(value));
                        break;
                    case "imagesourceid":
                        mPlayList.setImageSourceId(Integer.valueOf(value));
                        break;
                }
            }
        }

        value = "";
        if (qName.equalsIgnoreCase("PlayElement")){
            PlayElement playElement = new PlayElement();
            //System.out.println("开始解析PlayElement");
            for (int i=0; i < attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                if (attributes.getQName(i).equalsIgnoreCase("atomid")){
                    playElement.setAtomId(Integer.valueOf(value));
                }
            }
            mPlayElement.add(playElement);
        }

        value = "";
        if (qName.equalsIgnoreCase("Atoms")){
            //System.out.println("开始解析Atoms");
            for (int i=0; i < attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
            }
        }

        value = "";
        if (qName.equalsIgnoreCase("Atom")){
            //System.out.println("开始解析Atom");
            mLevelMap = new HashMap<>();
            Atom atom = new Atom();
            for (int i=0; i < attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                if (attributes.getQName(i).equalsIgnoreCase("id")) {
                    mAtomId = Integer.valueOf(value);
                    atom.setId(mAtomId);
                }
                if (attributes.getQName(i).equalsIgnoreCase("videosourceid")) atom.setVideoSourceId(Integer.valueOf(value));
            }
            if (mAtomId != -1)
                mAtomMap.put(mAtomId,atom);
        }

        value = "";
        if (qName.equalsIgnoreCase("Level")){
            //System.out.println("atom id = " + mAtomId);
            //System.out.println("开始解析Level");
            Level level = new Level();
            mEventMap = new HashMap<>();
            for (int i=0; i<attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                if (attributes.getQName(i).equalsIgnoreCase("id")) {
                    mLevelId= Integer.valueOf(value);
                    level.setId(mLevelId);
                }
            }
            if (mLevelId != -1)
                mLevelMap.put(mLevelId,level);
        }

        value = "";
        if (qName.equalsIgnoreCase("Event")){
            //System.out.println("levelId = " + mLevelId);
            //System.out.println("开始解析Event");cd
            Event event = new Event();
            for (int i=0; i<attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                switch (attributes.getQName(i)){
                    case "time":
                        mTime = Integer.valueOf(value);
                        event.setTime(mTime);
                        break;
                    case "resistance":
                        event.setResistance(Integer.valueOf(value));
                        break;
                    case "incline":
                        event.setIncline(Float.valueOf(value));
                        break;

                }
            }
            if (mTime != -1)
                mEventMap.put(mTime,event);
        }

        value = "";
        if (qName.equalsIgnoreCase("EventSource")){
            //System.out.println("开始解析EventSource");
            //System.out.println("time = " + mTime);
            EventSource eventSource = new EventSource();
            for (int i=0; i<attributes.getLength(); i++){
                //System.out.println(attributes.getQName(i) + ": " + attributes.getValue(i));
                value = attributes.getValue(i);
                if (attributes.getQName(i).equalsIgnoreCase("sourceid")) {
                    eventSource.setSourceId(Integer.valueOf(value));
                }
            }
            Event event = mEventMap.get(mTime);
            event.setEventSource(eventSource);
            mEventMap.put(event.getTime(),event);
        }



        //System.out.println(mSourceMap.get(1).getSource());



    }

    //解析内容时候调用
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        //System.out.println("stringid = " + mStringId);
        if (mStringId != -1){
            //解析string 里面的内容
            if (mDescContentMap == null) return;//结束标签，直接返回
            String value = mDescContentBuilder.append(ch,start,length).toString().trim();
            if (value.length() == 0) return;
            mQName = mQName.toLowerCase();
            switch (mQName){
                case LANGUAGE_EN_US:
                    mDescContentMap.put(LanguageType.EN_US,value);
                    break;
                case LANGUAGE_AR:
                    mDescContentMap.put(LanguageType.AR,value);
                    break;
                case LANGUAGE_ZH_CN:
                    mDescContentMap.put(LanguageType.ZH_CN,value);
                    break;
                case LANGUAGE_ZH_TW:
                    mDescContentMap.put(LanguageType.ZH_TW,value);
                    break;
                case LANGUAGE_NL:
                    mDescContentMap.put(LanguageType.NL,value);
                    break;
                case LANGUAGE_ES:
                    mDescContentMap.put(LanguageType.ES,value);
                    break;
                case LANGUAGE_FI:
                    mDescContentMap.put(LanguageType.FI,value);
                    break;
                case LANGUAGE_DT:
                    mDescContentMap.put(LanguageType.DT,value);
                    break;
                case LANGUAGE_JA:
                    mDescContentMap.put(LanguageType.JA,value);
                    break;
                case LANGUAGE_KO:
                    mDescContentMap.put(LanguageType.KO,value);
                    break;
                case LANGUAGE_PT:
                    mDescContentMap.put(LanguageType.PT,value);
                    break;
                case LANGUAGE_RU:
                    mDescContentMap.put(LanguageType.RU,value);
                    break;
                case LANGUAGE_SW:
                    mDescContentMap.put(LanguageType.SW,value);
                    break;
                case LANGUAGE_FR:
                    mDescContentMap.put(LanguageType.FR,value);
                    break;
                case LANGUAGE_IT:
                    mDescContentMap.put(LanguageType.IT,value);
                    break;
                case LANGUAGE_TR:
                    mDescContentMap.put(LanguageType.TR,value);
                    break;
                case LANGUAGE_PL:
                    mDescContentMap.put(LanguageType.PL,value);
                    break;
                case LANGUAGE_WE:
                    mDescContentMap.put(LanguageType.WE,value);
                    break;
                case LANGUAGE_BA:
                    mDescContentMap.put(LanguageType.BA,value);
                    break;
                case LANGUAGE_VI:
                    mDescContentMap.put(LanguageType.VI,value);
                    break;
                case LANGUAGE_SO:
                    mDescContentMap.put(LanguageType.SO,value);
                    break;
                case LANGUAGE_DA:
                    mDescContentMap.put(LanguageType.DA,value);
                    break;
                case LANGUAGE_MS:
                    mDescContentMap.put(LanguageType.MS,value);
                    break;
                case LANGUAGE_TH:
                    mDescContentMap.put(LanguageType.TH,value);
                    break;
            }
            mDescContentBuilder.setLength(0);
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        if (qName.equalsIgnoreCase("PackageInfo")){
            System.out.println("结束解析PackageInfo");
        }

        if (qName.equalsIgnoreCase("LanguageDictionary")){
            mLanguageDictionary.setDescStringMap(mDescStringMap);
        }

        if (qName.equalsIgnoreCase("String")){
            //System.out.println("结束解析string");
            //System.out.println("string id = " + mStringId);
            DescString descString = mDescStringMap.get(mStringId);
            descString.setDescMap(mDescContentMap);
            mDescStringMap.put(descString.getId(),descString);
            mStringId = -1;
            mDescContentMap = null;
        }

        if (qName.equalsIgnoreCase("Sources")){
            //System.out.println("结束解析Sources");
        }


        if (qName.equalsIgnoreCase("Playlist")){
            //System.out.println("结束解析Playlist");
            mPlayList.setPlayElementList(mPlayElement);
        }
        if (qName.equalsIgnoreCase("Atoms")){
            //System.out.println("结束解析Atoms");
        }
        if (qName.equalsIgnoreCase("Atom")){
            //System.out.println("结束解析Atom atomid = -1");
            Atom atom = mAtomMap.get(mAtomId);
            atom.setLevelMap(mLevelMap);
            mAtomMap.put(atom.getId(),atom);
            mAtomId = -1;
            mLevelMap = null;
        }

        if (qName.equalsIgnoreCase("Level")){
            //System.out.println("结束解析Level levelid = -1");
            Level level = mLevelMap.get(mLevelId);
            level.setEventMap(mEventMap);
            mLevelMap.put(level.getId(),level);
            mLevelId = -1;
            mEventMap = null;//
        }

        if (qName.equalsIgnoreCase("Event")){
            //System.out.println("结束解析Event");
        }

    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        mMapData.setPackageInfo(mPackageInfo);
        mMapData.setLanguageDictionary(mLanguageDictionary);
        mMapData.setSourceMap(mSourceMap);
        mMapData.setPlaylist(mPlayList);
        mMapData.setAtomMap(mAtomMap);

        if (mXMLParserListener != null) {
            mXMLParserListener.finished(mMapData);
        }
        System.out.println();
    }


    private void parserSource(Attributes attributes){
        Source source;
        for (int i=0;i<attributes.getLength();i++){
            source = new Source();
            if (attributes.getQName(i).equalsIgnoreCase("type")) source.setType(attributes.getValue(i));
            if (attributes.getQName(i).equalsIgnoreCase("source")) source.setSource(attributes.getValue(i));
            if (attributes.getQName(i).equalsIgnoreCase("id")) {
                source.setId(Integer.valueOf(attributes.getValue(i)));
                mSourceMap.put(source.getId(),source);
            }
        }
    }

    public interface XMLParserListener{
        void finished(MapData mapData);
    }
}
