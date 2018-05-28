package com.netstore.api.react.controller;

import com.netstore.model.API.react.HashTableUni;
import com.netstore.model.API.react.HashTableModel;
import com.netstore.model.API.react.HashTableSearchModel;
import com.netstore.model.API.react.HashTableUniSearch;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/react/main/hashTable")
public class HashTable {


    @RequestMapping(value = "/hashMod", method = RequestMethod.POST)
    public ResponseEntity<String[]> hashMod(@RequestBody HashTableModel hashTableModel) {
        String data = hashTableModel.getData();
        int countW = hashTableModel.getCountW();
        String[] tab = new String[countW];
        int size = tab.length;
        int hf=0;
        int done =0;
        for(int i= 0;i<data.length();i++){
            if(data.charAt(i) !=' '& data.charAt(i) !='.')
            {
                hf += data.charAt(i);
            }
            else {
                hf=hf % size;
                int temp=hf;
                if(tab[hf]!=null& done!=size)  {
                    Boolean test = true;
                    while(test==true){
                        if(hf!=size-1)hf++;
                        else hf=0;
                        if (tab[hf]==null||hf==temp) test = false;
                    }}
                tab[hf]=(data.substring(0,i)).replaceAll("\\s+","");
                data= (data.substring(i));
                hf=0;
                i=0;
                done++;
            }

        }
        return new ResponseEntity<>(tab, HttpStatus.OK);
    }
    @RequestMapping(value = "/hashMultiply", method = RequestMethod.POST)
    public ResponseEntity<String[]> hashMultiply(@RequestBody HashTableModel hashTableModel) {
        String data = hashTableModel.getData();
        int countW = hashTableModel.getCountW();
        String[] tab = new String[countW];
        int size = tab.length;
        int hf=0;
        int done =0;
        ///kod hashowanie modularne
        for(int i= 0;i<data.length();i++){
            if(data.charAt(i) !=' '& data.charAt(i) !='.')
            {
                hf += data.charAt(i);
            }
            else {
                hf= (int) Math.floor(size*((hf*0.6180339887) % 1));
                int temp=hf;
                if(tab[hf]!=null& done!=size)  {
                    Boolean test = true;
                    while(test==true){
                        if(hf!=size-1)hf++;
                        else hf=0;
                        if (tab[hf]==null||hf==temp) test = false;
                    }}
                tab[hf]=(data.substring(0,i)).replaceAll("\\s+","");
                data= (data.substring(i));
                hf=0;
                i=0;
                done++;
            }

        }
        return new ResponseEntity<>(tab,HttpStatus.OK);
    }
    @RequestMapping(value = "/searchMod", method = RequestMethod.POST)
    public ResponseEntity<Integer> searchMod(@RequestBody HashTableSearchModel hashTableSearchModel) {
        String search= hashTableSearchModel.getSearch();
        String[] tab = hashTableSearchModel.getTab();
        int temp, mod_search = 0;
        int answer = 0;
        int size = tab.length;
        for(int i=0;i<search.length();i++){
            temp = search.charAt(i);
            mod_search += temp;
        }
        mod_search=mod_search%size;
        temp= mod_search;
        if(tab[mod_search]==""||tab[mod_search]==null)answer=-1;
        else {
            if(tab[mod_search].equals(search))answer=mod_search;
            else{
                while (!tab[mod_search].equals(search) && temp==mod_search){
                    mod_search++;
                    if(mod_search==size) {
                        mod_search = 0;
                    }
                }
                if(tab[mod_search].equals(search))
                {
                    answer=mod_search;
                }
                else {
                    answer= (-1);
                }
            }
        }

        return new ResponseEntity<>(answer,HttpStatus.OK);
    }
    @RequestMapping(value = "/searchMultiply", method = RequestMethod.POST)
    public ResponseEntity<Integer> searchMultiply(@RequestBody HashTableSearchModel hashTableSearchModel) {
        String search= hashTableSearchModel.getSearch();
        String[] tab = hashTableSearchModel.getTab();
        int temp, mod_search = 0;
        int answer = 0;
        int size = tab.length;
        for(int i=0;i<search.length();i++){
            temp = search.charAt(i);
            mod_search += temp;
        }
        mod_search= (int) Math.floor(size*((mod_search*0.6180339887) % 1));
        temp= mod_search-1;
        if(tab[mod_search]==""||tab[mod_search]==null)answer=-1;
        else {
            if(tab[mod_search].equals(search))answer=mod_search;
            else{
                while (!tab[mod_search].equals(search)&temp!=mod_search){
                    mod_search++;
                    if(mod_search==size)mod_search=0;
                }
                if(tab[mod_search].equals(search))answer=mod_search;else answer=-1;
            }
        }

        return new ResponseEntity<>( answer,HttpStatus.OK);
    }
    @RequestMapping(value = "/hashUni", method = RequestMethod.POST)
    public ResponseEntity<String[]> hashUni(@RequestBody HashTableUni hashTableUni) {
        String data =hashTableUni.getData();
        int countW =hashTableUni.getCountW();
        int k=hashTableUni.getK();
        String[] tab = new String[countW];
        int size = tab.length;
        int hf = 0;
        int done = 0;
        int a = 0;
        for(int i= 0;i<data.length();i++){
            if(data.charAt(i) !=' '& data.charAt(i) !='.')
            {
                hf += data.charAt(i)*(k*(size-a));
                a++;
            }
            else {a=0;
                hf=hf % size;
                int temp=hf;
                if(tab[hf]!=null& done!=size)  {
                    Boolean test = true;
                    while(test==true){
                        if(hf!=size-1)hf++;
                        else hf=0;
                        if (tab[hf]==null||hf==temp) test = false;
                    }}
                tab[hf]=(data.substring(0,i)).replaceAll("\\s+","");
                data= (data.substring(i));
                hf=0;
                i=0;
                done++;
            }
        }
        return new ResponseEntity<>(tab,HttpStatus.OK);
    }
    @RequestMapping(value = "/searchUni", method = RequestMethod.POST)
    public ResponseEntity<Integer> searchUni(@RequestBody HashTableUniSearch hashTableUniSearch) {
        String search= hashTableUniSearch.getSearch();
        String[] tab= hashTableUniSearch.getTab();
        int k = hashTableUniSearch.getK();
        int temp, mod_search = 0;
        int answer = 0;
        int size = tab.length;
        for(int i=0;i<search.length();i++){
            temp = search.charAt(i);
            mod_search += temp*(k*(size-i));
        }
        mod_search=mod_search%size;
        temp= mod_search;
        if(tab[mod_search]==""||tab[mod_search]==null)answer=-1;
        else {
            if(tab[mod_search].equals(search))answer=mod_search;
            else{
                while (!tab[mod_search].equals(search)&temp!=mod_search){
                    mod_search++;
                    if(mod_search==size-1)mod_search=0;
                }
                if(tab[mod_search].equals(search))answer=mod_search;else answer=-1;
            }
        }
        return new ResponseEntity<>( answer,HttpStatus.OK);
    }

    public static boolean isPrimeNumber(int liczba) {
        if (liczba < 2) {
            return false;
        }
        for (int i = 2; i <= liczba / 2; i++) {
            if (liczba % i == 0) {
                return false;
            }
        }
        return true;
    }

    @RequestMapping(value = "/addMod", method = RequestMethod.POST)
    public  ResponseEntity<String[]> addMod(@RequestBody HashTableSearchModel hashTable){
        int temp,mod=0;
        String addString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int size = tab.length;
        for(int i=0;i<addString.length();i++){
            temp = addString.charAt(i);
            mod += temp;
        }
        mod=mod%size;
        System.out.println(mod);
        if(tab[mod]==""||tab[mod]==null){
            tab[mod]=addString;
            return new ResponseEntity<>( tab,HttpStatus.OK);
        }
        else {

            String[] tempTab= new String[tab.length+1];

            for(int i=0;i<tab.length;i++)
                tempTab[i]=tab[i];
            tempTab[tab.length]=addString;
            return new ResponseEntity<>( tempTab,HttpStatus.OK);


        }

    }
    @RequestMapping(value = "/addMultiply", method = RequestMethod.POST)
    public  ResponseEntity<String[]> addMultiply(@RequestBody HashTableSearchModel hashTable){
        int temp,mod=0;
        String addString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int size = tab.length;
        for(int i=0;i<addString.length();i++){
            temp = addString.charAt(i);
            mod += temp;
        }
        mod=(int) Math.floor(size*((mod*0.6180339887) % 1));
        System.out.println(mod);
        if(tab[mod]==""||tab[mod]==null){
            tab[mod]=addString;
            return new ResponseEntity<>( tab,HttpStatus.OK);
        }
        else {

            String[] tempTab= new String[tab.length+1];

            for(int i=0;i<tab.length;i++)
                tempTab[i]=tab[i];
            tempTab[tab.length]=addString;
            return new ResponseEntity<>( tempTab,HttpStatus.OK);


        }

    }
    @RequestMapping(value = "/addUni", method = RequestMethod.POST)
    public ResponseEntity<String[]> addUni(@RequestBody HashTableUniSearch hashTable){
        int temp,mod=0;
        String addString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int k = hashTable.getK();
        int size = tab.length;
        for(int i=0;i<addString.length();i++){
            temp = addString.charAt(i);
            mod +=  temp*(k*(size-i));
        }
        mod=mod%size;
        System.out.println(mod);
        if(tab[mod]==""||tab[mod]==null){
            tab[mod]=addString;
            return new ResponseEntity<>( tab,HttpStatus.OK);
        }
        else {

            String[] tempTab= new String[tab.length+1];

            for(int i=0;i<tab.length;i++)
                tempTab[i]=tab[i];
            tempTab[tab.length]=addString;
            return new ResponseEntity<>( tempTab,HttpStatus.OK);


        }

    }
    @RequestMapping(value = "/delMod", method = RequestMethod.POST)
    public  ResponseEntity<String[]> delMod(@RequestBody HashTableSearchModel hashTable){
        int temp,mod=0;
        String delString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int size = tab.length;
        for(int i=0;i<delString.length();i++){
            temp = delString.charAt(i);
            mod += temp;
        }
        mod=mod%size;
        System.out.println(mod);
        if(tab[mod].equals(delString)){
            tab[mod]="";
        }
        else {
            for(int i=0;i<tab.length;i++)
                if(tab[mod]==delString){
                    tab[mod]="";
                }
        }
        return new ResponseEntity<>( tab,HttpStatus.OK);
    }
    @RequestMapping(value = "/delMultiply", method = RequestMethod.POST)
    public ResponseEntity<String[]> delMultiply(@RequestBody HashTableSearchModel hashTable){
        int temp,mod=0;
        String delString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int size = tab.length;
        for(int i=0;i<delString.length();i++){
            temp = delString.charAt(i);
            mod += temp;
        }
        mod=(int) Math.floor(size*((mod*0.6180339887) % 1));
        if(tab[mod].equals(delString)){
            tab[mod]="";
        }
        else {
            for(int i=0;i<tab.length;i++)
                if(tab[mod]==delString){
                    tab[mod]="";
                }
        }
        return new ResponseEntity<>( tab,HttpStatus.OK);
    }
    @RequestMapping(value = "/delUni", method = RequestMethod.POST)
    public ResponseEntity<String[]> delUni(@RequestBody HashTableUniSearch hashTable){
        int temp,mod=0;
        String delString = hashTable.getSearch();
        String[] tab = hashTable.getTab();
        int k = hashTable.getK();
        int size = tab.length;
        for(int i=0;i<delString.length();i++){
            temp = delString.charAt(i);
            mod +=  temp*(k*(size-i));
        }
        mod=mod%size;
        if(tab[mod].equals(delString)){
            tab[mod]="";
        }
        else {
            for(int i=0;i<tab.length;i++)
                if(tab[mod]==delString){
                    tab[mod]="";
                }
        }
        return new ResponseEntity<>( tab,HttpStatus.OK);
    }
}
