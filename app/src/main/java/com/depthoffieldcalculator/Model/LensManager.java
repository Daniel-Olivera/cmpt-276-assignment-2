package com.depthoffieldcalculator.Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LensManager implements Iterable<Lens> {
    public List<Lens> lenses = new ArrayList<>();
    private static LensManager instance;

    private LensManager(){
        //Private to prevent anyone else from instantiating
    }
    //Creates the singleton instance
    public static LensManager getInstance(){
        if(instance == null){
           instance = new LensManager();
        }
        return instance;
    }

    public void add(Lens lens){
        lenses.add(lens);
    }

    public Lens get(int i){
        return lenses.get(i);
    }

    @Override
    public Iterator<Lens> iterator() {
        return lenses.iterator();
    }
}
