package br.com.abracowebmanagement.util;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ContractUtil {

	/*
	 * Class level
	 * Example:
	 * 
	 * Spanish:	Espanhol 1 (A1); Espanhol 1 (A2); Espanhol 3 (B1.1);
	 * 			Espanhol 4 (B1.2); Espanhol 5 (B1.3); Conversação (B1/B2);
	 * 
	 * French:	Francês 1 (A1.1); Francês 2 (A1.2); Francês 3 (A2.1);
	 *  		Francês 4 (A2.2); Francês 5 (B1.1); Francês 6 (B1.2);
	 *  		Francês 7 (B1.3); Conversação (B1/B2);
	 *  
	 * English:	Inglês 1 (A1.1); Inglês 2 (A1.2); Inglês 3 (A2.1);
	 * 			Inglês 4 (A2.2); Inglês 5 (B1.1); Inglês 6 (B1.2);
	 * 			Conversação (B1/B2);
	 * 
	 * Arab:	Arábe 1 (AR1); Arábe 2 (AR2); Arábe 3 (AR3); Arábe 4 (AR4);
	 */	

	
	
	public String getLanguageFullDescription(String description){
		String languageValue = "";
		if(description.equals("AR")){
			languageValue = "ARÁBE";
		} else if(description.equals("ES")){
			languageValue = "ESPANHOL";
		} else if(description.equals("FR")){
			languageValue = "FRANCÊS";
		} else if(description.equals("EN")){
			languageValue = "INGLÊS";
		} else if(description.equals("BR")){
			languageValue = "PORTUGUÊS";
		}
		return languageValue;
	}	
	
		
	public TreeMap<String, String> getLevelComboList(String description){
		TreeMap<String, String> languageList = new TreeMap<String, String>();
		
		if(description.equals("AR")){
			languageList.put("Arábe 1", "AR1");
			languageList.put("Arábe 2", "AR2");
			languageList.put("Arábe 3", "AR3");
			languageList.put("Arábe 4", "AR4");
		} else if(description.equals("ES")){
			languageList.put("Espanhol 1 (A1)", "ES1");
			languageList.put("Espanhol 2 (A2)", "ES2");
			languageList.put("Espanhol 3 (B1.1)", "ES3");
			languageList.put("Espanhol 4 (B1.2)", "ES4");
			languageList.put("Espanhol 5 (B1.3)", "ES5");
			languageList.put("Espanhol Conv.(B1/B2)", "ESconv");	
		} else if(description.equals("EN")){
			languageList.put("Inglês 1 (A1.1)", "EN1");
			languageList.put("Inglês 2 (A1.2)", "EN2");
			languageList.put("Inglês 3 (A2.1)", "EN3");
			languageList.put("Inglês 4 (A2.2)", "EN4");
			languageList.put("Inglês 5 (B1.1)", "EN5");
			languageList.put("Inglês 6 (A1.2)", "EN6");
			languageList.put("Inglês Conv.(B1/B2)", "ENconv");
		} else if(description.equals("FR")){
			languageList.put("Francês 1 (A1.1)", "FR1");
			languageList.put("Francês 2 (A1.2)", "FR2");
			languageList.put("Francês 3 (A2.1)", "FR3");
			languageList.put("Francês 4 (A2.2)", "FR4");
			languageList.put("Francês 5 (B1.1)", "FR5");
			languageList.put("Francês 6 (B1.2)", "FR6");
			languageList.put("Francês 7 (B1.3)", "FR7");
			languageList.put("Francês Conv.(B1/B2)", "FRconv");
		}

		return languageList;
	}
	
	public String getKey(Map<String, String> level, String description){
		String keyValues = "";
        Set<String> keys = level.keySet();
        for(String key: keys){
    		if(level.get(key).equals(description)){
    			keyValues = key;
    			break;
    		}
        }
		return keyValues;		
	}
	
	public String getTypeClassFullDescription(String description){
		String typeValue = "";
		if(description.equals("")){
			typeValue = "EXTENSIVO";
		} else if(description.equals("EXT")){
			typeValue = "INTENSIVO";
		} else if(description.equals("INT")){
			typeValue = "PARTICULAR";
		} else if(description.equals("PAR")){
			typeValue = "INGLÊS";
		} else if(description.equals("INC")){
			typeValue = "INCOMPANY";
		}
		return typeValue;
	}
	
	public String getPlaceFullDescription(String description){
		String placeValue = "";
		
		if(description.equals("PINH")){
			placeValue = "PINHEIROS";
		} else if(description.equals("TATU")){
			placeValue = "TATUAPÉ";
		}
		return placeValue;
	}

	public TreeMap<String, String> getClassDayComboList(){
		TreeMap<String, String> classDayList = new TreeMap<String, String>();
		
		classDayList.put("SEG", "SEG");
		classDayList.put("TER", "TER");
		classDayList.put("QUA", "QUA");
		classDayList.put("QUI", "QUI");
		classDayList.put("SEX", "SEX");
		classDayList.put("SAB", "SAB");
		
		return classDayList;
	}
	
	
	public String getClassDayFullDescription(String description){
		String classDayValue = "";
		if(description.equals("SEG")){
			classDayValue = "Segunda";
		} else if(description.equals("TER")){
			classDayValue = "Terça";
		} else if(description.equals("QUA")){
			classDayValue = "Quarta";
		} else if(description.equals("QUI")){
			classDayValue = "Quinta";
		} else if(description.equals("SEX")){
			classDayValue = "Sexta";
		} else if(description.equals("SAB")){
			classDayValue = "Sábado";
		}
		return classDayValue;
	}

}
