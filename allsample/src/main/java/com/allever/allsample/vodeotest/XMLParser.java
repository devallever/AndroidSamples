package com.allever.allsample.vodeotest;

import com.allever.allsample.vodeotest.model.*;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by allever on 17-10-11.
 * Test
 */
public class XMLParser {

    private static VideoHandler.XMLParserListener xmlParserListener = new VideoHandler.XMLParserListener() {
        @Override
        public void finished(MapData mapData) {
            System.out.println();
            System.out.println("地图包信息");
            PackageInfo packageInfo = mapData.getPackageInfo();
            System.out.println("version = " + packageInfo.getVersion());
            System.out.println("title = " + packageInfo.getTitle());
            System.out.println("isFlat = " + packageInfo.isFlat());
            System.out.println("isCoached = " + packageInfo.isCoached());
            System.out.println();

            System.out.println("字符串资源,其中一个id的内容");
            int stringId = 1;
            Map<Integer,DescString> descStringMap = mapData.getLanguageDictionary().getDescStringMap();
            DescString descString = descStringMap.get(stringId);
            Map<LanguageType, String> descContentMap =  descString.getDescMap();
            for (Map.Entry<LanguageType,String> entry:descContentMap.entrySet()){
                System.out.println(entry.getKey()+": " + entry.getValue());
            }
            System.out.println();

            StringBuilder stringBuilder = new StringBuilder();
            Map<Integer,Source> sourceMap = mapData.getSourceMap();
            //String sourceName = "";
            System.out.println("资源列表");
            Source source;
            for (int i=1; i<sourceMap.size(); i++){
                source = sourceMap.get(i);
                //sourceName = sourceMap.get(i).getSource();
                stringBuilder.append("id = " + source.getId() + "\t type = " + source.getType());
                if (source.getSource() != "") stringBuilder.append( "\t source = " + source.getSource());
                if (source.getStringId() != -1) stringBuilder.append("\t stringid = " + source.getStringId());
                stringBuilder.append("\n");
            }
            System.out.println(stringBuilder.toString());
            stringBuilder.setLength(0);

            System.out.println();
            System.out.println("播放列表");
            List<PlayElement> playElementList = mapData.getPlaylist().getPlayElementList();
            Map<Integer, Atom> atomMap = mapData.getAtomMap();
            for (PlayElement playElement: playElementList){
                System.out.println(sourceMap.get(atomMap.get(playElement.getAtomId()).getVideoSourceId()).getSource());
            }

            System.out.println();
            System.out.println("AtomId = 1, levelId = 1 时的事件列表");
            int levelId = 1;
            int atomId = 1;
            Atom atom = atomMap.get(atomId);
            Map<Integer, Level> levelMap = atom.getLevelMap();
            Map<Integer, Event> eventMap = levelMap.get(levelId).getEventMap();
            //遍历map
            Event event;
            for (int i = 0; i<=sourceMap.get(atomMap.get(atomId).getVideoSourceId()).getDuration();i++){
                event = eventMap.get(i);
                if (event ==null) continue;
                stringBuilder.append("time = " + event.getTime() + "\t resistance = " + event.getResistance() + "\t incline = " + event.getIncline());
                if (event.getEventSource() != null){
                    stringBuilder.append("\t sourceId = " + event.getEventSource().getSourceId());
                }
                System.out.println(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
        }
    };

    public static void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                        /*
                        XMLReader xmlReader = factory.newSAXParser().getXMLReader();
                        VideoHandler videoHandler = new VideoHandler();
                        xmlReader.setContentHandler(videoHandler);
                        xmlReader.parse(xmlData);
                        */
                    SAXParser parser=factory.newSAXParser();
                    parser.parse(new File("res/settings.xml"),new VideoHandler(xmlParserListener));
                }catch (SAXException saxE){
                    saxE.printStackTrace();
                }catch (ParserConfigurationException pE){
                    pE.printStackTrace();
                }catch (IOException ioE){
                    ioE.printStackTrace();
                }

            }
        }).start();

    }
}
