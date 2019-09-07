package com.allever.allsample.vodeotest.model;

import java.util.List;
import java.util.Map;

/**
 * Created by allever on 17-10-11.
 */
public class MapData {
    private PackageInfo packageInfo;
    private LanguageDictionary languageDictionary;
    //private List<Source> sourceList;
    private Map<Integer, Source> sourceMap; //<sourceId, Source>
    //private List<Atom> atomList;
    private Map<Integer,Atom> atomMap; //<AtomId, Atom>
    private Playlist playlist;


    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public LanguageDictionary getLanguageDictionary() {
        return languageDictionary;
    }

    public void setLanguageDictionary(LanguageDictionary languageDictionary) {
        this.languageDictionary = languageDictionary;
    }

/*    public List<Source> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<Source> sourceList) {
        this.sourceList = sourceList;
    }*/

    public Map<Integer, Source> getSourceMap() {
        return sourceMap;
    }

    public void setSourceMap(Map<Integer, Source> sourceMap) {
        this.sourceMap = sourceMap;
    }

    /*    public List<Atom> getAtomList() {
        return atomList;
    }

    public void setAtomList(List<Atom> atomList) {
        this.atomList = atomList;
    }*/

    public Map<Integer, Atom> getAtomMap() {
        return atomMap;
    }

    public void setAtomMap(Map<Integer, Atom> atomMap) {
        this.atomMap = atomMap;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }


}
