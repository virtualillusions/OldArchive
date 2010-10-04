/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vza.app.util.SAX;

import com.vza.director.model.AttackKey;
import com.vza.director.model.Style;
import java.util.ArrayList;
import java.util.Arrays;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Kyle Williams
 */
public class EzHandler extends DefaultHandler{
    public Object getStuff(){return null;}


    /**
     * This is the SAX extension of defualt Handler that will assist in searching the characterList for characterNames
     * to accuratley find the characters location in the next step of the procedure
     * @return eZ Handler
     */
    public EzHandler getHandler1(){
        return new EzHandler(){
             private ArrayList<String> r = new ArrayList<String>();
             @Override
             public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {  if(qName.equals("character"))r.add(attributes.getValue("name"));}
             @Override
             public Object getStuff() {return r.toArray();}
            };
    }
    /**
     * This is the SAX extenstion defualt that will take all information from a characters xml file
     * and find all available styles which will be the last step of the procedure
     * @return eZHandler
     */
    public EzHandler getHandler2(final String cName){
        return new EzHandler(){
            private String bio;
            private String balanceType;
            private ArrayList<String> styles = new ArrayList<String>();
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
                    if(qName.equals("name")){
                        if((!attributes.getValue("name").equals(cName))){
                          //Sends an exception if the searched name is different than the one in this xml
                            throw new SAXException("The name found in characterList.xml is different than the one found in this one \n");
                        }
                    } else if (qName.equals("bio")){
                            bio = attributes.getValue("bio");
                    }else if (qName.equals("style")){
                            styles.add(attributes.getValue("styleName"));
                    }else if(qName.equals("balanceType")){
                            balanceType=attributes.getValue("type");
                    }
                }
             @Override
            public Object getStuff() {return new Object[]{balanceType,bio,styles.toArray()};}
        };
    }
    /**
     * This is the SAX extension defualt that will take all information from character Style xml files
     * @param cName
     * @return eZHandler()
     *************************************TODO: add end element to create more of a heirarchy **********************
     */
    public EzHandler getHandler3(final String cName){
        return new EzHandler(){
            private Style style;
            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException{
                if(qName.equals("info")){
                    if(!attributes.getValue("characterName").equals(cName))
                        throw new SAXException("The name "+cName+" found in the searcher xml is differnt than the one found in this one " +attributes.getValue("characterName")+"\n");
                    style = new Style(attributes.getValue("styleName"));
                    style.setInfo(attributes.getValue("translationName"),attributes.getValue("ArcheType"));
                } else if(qName.equals("attackKey")){
                    ArrayList<String> sequence =new ArrayList<String>();
                    sequence.addAll(Arrays.asList(attributes.getValue("button").split(",")));
                    AttackKey attk = new AttackKey(sequence,attributes.getValue("animation"));
                    if(attributes.getValue("prevAni") != null)attk.setPrevAni(attributes.getValue("prevAni"));
                    if(checker(attributes,"isLast"))attk.setIsLast();
                    if(checker(attributes,"block"))attk.setBlock();
                    if(checker(attributes,"blockBreaker"))attk.setBlockBreaker();

                    style.put(attk.getButton(), attk);                
                }
            }
         public boolean checker(Attributes attributes,String check){
            if(attributes.getValue(check) != null && attributes.getValue(check).equals("true"))
                return true;
             return false;
         }

        @Override
        public void endElement (String uri, String localName, String qName)
        throws SAXException{
	//END ATTACK KEY AND ADD IT TO STYLE
        }
            @Override
            public Object getStuff() {return style;}
        };
    }
}
