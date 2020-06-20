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

	public String getShortLanguageDescription(String description){
		String languageValue = "";
		if(description.equals("ARÁBE")){
			languageValue = "AR";
		} else if(description.equals("ESPANHOL")){
			languageValue = "ES";
		} else if(description.equals("FRANCÊS")){
			languageValue = "FR";
		} else if(description.equals("INGLÊS")){
			languageValue = "EN";
		} else if(description.equals("PORTUGUÊS")){
			languageValue = "PT";
		}
		return languageValue;
	}
	
	
	public String getFullLanguageDescription(String description){
		String languageValue = "";
		if(description.equals("AR")){
			languageValue = "ARÁBE";
		} else if(description.equals("ES")){
			languageValue = "ESPANHOL";
		} else if(description.equals("FR")){
			languageValue = "FRANCÊS";
		} else if(description.equals("EN")){
			languageValue = "INGLÊS";
		} else if(description.equals("PT")){
			languageValue = "PORTUGUÊS";
		}
		return languageValue;
	}	
	
	
	/*
	 * get Short Level description
	 */
	public String getShortDescriptionLevel(String description, String languageDescription){
		String levelDescription = "";
		
		if(languageDescription.equals("ARÁBE")){
			
			if(description.equals("Arábe 1")){
				levelDescription = "AR1";
			} else if(description.equals("Arábe 2")){
				levelDescription = "AR2";
			} else if(description.equals("Arábe 3")){
				levelDescription = "AR3";
			} else if(description.equals("Arábe 4")){
				levelDescription = "AR4";
			}
		} else if(languageDescription.equals("ESPANHOL")){
			
			if(description.equals("Espanhol 1 (A1)")){
				levelDescription = "ES1";
			} else if(description.equals("Espanhol 2 (A1)")){
				levelDescription = "ES2";
			} else if(description.equals("Espanhol 3 (B1.1)")){
				levelDescription = "ES3";
			} else if(description.equals("Espanhol 4 (B1.2)")){
				levelDescription = "ES4";
			} else if(description.equals("Espanhol 5 (B1.3)")){
				levelDescription = "ES5";
			} else if(description.equals("Espanhol Conv.(B1/B2)")){
				levelDescription = "ESconv";
			}	
		} else if(languageDescription.equals("FRANCÊS")){

			if(description.equals("Francês 1 (A1.1)")){
				levelDescription = "FR1";
			} else if(description.equals("Francês 2 (A1.2)")){
				levelDescription = "FR2";
			} else if(description.equals("Francês 3 (A2.1)")){
				levelDescription = "FR3";
			} else if(description.equals("Francês 4 (A2.2)")){
				levelDescription = "FR4";
			} else if(description.equals("Francês 5 (B1.1)")){
				levelDescription = "FR5";
			} else if(description.equals("Francês 6 (B1.2)")){
				levelDescription = "FR6";
			} else if(description.equals("Francês 7 (B1.3)")){
				levelDescription = "FR7";
			} else if(description.equals("Francês Conv.(B1/B2)")){
				levelDescription = "FRconv";
			}
		} else if(languageDescription.equals("INGLÊS")){

			if(description.equals("Inglês 1 (A1.1)")){
				levelDescription = "EN1";
			} else if(description.equals("Inglês 2 (A1.2)")){
				levelDescription = "EN2";
			} else if(description.equals("Inglês 3 (A2.1)")){
				levelDescription = "EN3";
			} else if(description.equals("Inglês 4 (A2.2)")){
				levelDescription = "EN4";
			} else if(description.equals("Inglês 5 (B1.1)")){
				levelDescription = "EN5";
			} else if(description.equals("Inglês 6 (B1.2)")){
				levelDescription = "EN6";
			} else if(description.equals("Inglês Conv.(B1/B2)")){
				levelDescription = "ENconv";
			}
		} else if(languageDescription.equals("PORTUGUÊS")){
			
			if(description.equals("Português")){
				levelDescription = "PT";
			}
		}

		return levelDescription;
	}

	
	
	
	public TreeMap<String, String> getFullDescriptionLevelComboList(String description){
		TreeMap<String, String> languageList = new TreeMap<String, String>();
		
		if(description.equals("ARÁBE")){
			languageList.put("Arábe 1", "Arábe 1");
			languageList.put("Arábe 2", "Arábe 2");
			languageList.put("Arábe 3", "Arábe 3");
			languageList.put("Arábe 4", "Arábe 4");
		} else if(description.equals("ESPANHOL")){
			languageList.put("Espanhol 1 (A1)", "Espanhol 1 (A1)");
			languageList.put("Espanhol 2 (A2)", "Espanhol 2 (A2)");
			languageList.put("Espanhol 3 (B1.1)", "Espanhol 3 (B1.1)");
			languageList.put("Espanhol 4 (B1.2)", "Espanhol 4 (B1.2)");
			languageList.put("Espanhol 5 (B1.3)", "Espanhol 5 (B1.3)");
			languageList.put("Espanhol Conv.(B1/B2)", "Espanhol Conv.(B1/B2)");	
		} else if(description.equals("FRANCÊS")){
			languageList.put("Francês 1 (A1.1)", "Francês 1 (A1.1)");
			languageList.put("Francês 2 (A1.2)", "Francês 2 (A1.2)");
			languageList.put("Francês 3 (A2.1)", "Francês 3 (A2.1)");
			languageList.put("Francês 4 (A2.2)", "Francês 4 (A2.2)");
			languageList.put("Francês 5 (B1.1)", "Francês 5 (B1.1)");
			languageList.put("Francês 6 (B1.2)", "Francês 6 (B1.2)");
			languageList.put("Francês 7 (B1.3)", "Francês 7 (B1.3)");
			languageList.put("Francês Conv.(B1/B2)", "Francês Conv.(B1/B2)");
		} else if(description.equals("INGLÊS")){
			languageList.put("Inglês 1 (A1.1)", "Inglês 1 (A1.1)");
			languageList.put("Inglês 2 (A1.2)", "Inglês 2 (A1.2)");
			languageList.put("Inglês 3 (A2.1)", "Inglês 3 (A2.1)");
			languageList.put("Inglês 4 (A2.2)", "Inglês 4 (A2.2)");
			languageList.put("Inglês 5 (B1.1)", "Inglês 5 (B1.1)");
			languageList.put("Inglês 6 (B1.2)", "Inglês 6 (B1.2)");
			languageList.put("Inglês Conv.(B1/B2)", "Inglês Conv.(B1/B2)");
		} else if(description.equals("PORTUGUÊS")){
			languageList.put("Português", "Português");

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
	
	/**
	 * Class Module
	 * @param description
	 * @return
	 */
	public String getShortModuleClassDescription(String description){
		String typeValue = "";
		if(description.equals("EXTENSIVO")){
			typeValue = "EXTR";
		} else if(description.equals("INTENSIVO")){
			typeValue = "INTS";
		} else if(description.equals("PARTICULAR")){
			typeValue = "PART";
		}else if(description.equals("INCOMPANY")){
			typeValue = "COMP";
		}
		
		return typeValue;
	}
	
	public String getPlaceFullDescription(String description){
		String placeValue = "";
		
		if(description.equals("PINH")){
			placeValue = "PINHEIROS";
		} else if(description.equals("TATU")){
			placeValue = "TATUAPÉ";
		} else if(description.equals("INCOMPANY")){
			placeValue = "COMP";
		} else if(description.equals("EXTR")){
			placeValue = "EXTERIOR";
		} else if(description.equals("ONLN")){
			placeValue = "ONLINE";
		}
		return placeValue;
	}
	
	public String getShortPlaceDescription(String description){
		String placeValue = "";
		
		if(description.equals("PINHEIROS")){
			placeValue = "PINH";
		} else if(description.equals("TATUAPÉ")){
			placeValue = "TATU";
		} else if(description.equals("INCOMPANY")){
			placeValue = "COMP";			
		} else if(description.equals("EXTERIOR")){
			placeValue = "EXTR";
		} else if(description.equals("ONLINE")){
			placeValue = "ONLN";
		}
		return placeValue;
	}

/*	public TreeMap<String, String> getClassDayComboList(){
		TreeMap<String, String> classDayList = new TreeMap<String, String>();
		
		classDayList.put("SEG", "SEG");
		classDayList.put("TER", "TER");
		classDayList.put("QUA", "QUA");
		classDayList.put("QUI", "QUI");
		classDayList.put("SEX", "SEX");
		classDayList.put("SAB", "SAB");
		
		return classDayList;
	}*/
	
	
	public TreeMap<String, String> getFullClassDayComboList(){
		TreeMap<String, String> classDayList = new TreeMap<String, String>();
		
		classDayList.put("SEGUNDAS", "SEGUNDAS");
		classDayList.put("TERÇAS", "TERÇAS");
		classDayList.put("QUARTAS", "QUARTAS");
		classDayList.put("QUINTAS", "QUINTAS");
		classDayList.put("SEXTAS", "SEXTAS");
		classDayList.put("SÁBADOS", "SÁBADOS");
		
		return classDayList;
	}
	
	
	public String getShortClassDayDescription(String description){
		String classDayValue = "";
		if(description.equals("SEGUNDAS")){
			classDayValue = "SEG";
		} else if(description.equals("TERÇAS")){
			classDayValue = "TER";
		} else if(description.equals("QUARTAS")){
			classDayValue = "QUA";
		} else if(description.equals("QUINTAS")){
			classDayValue = "QUI";
		} else if(description.equals("SEXTAS")){
			classDayValue = "SEX";
		} else if(description.equals("SÁBADOS")){
			classDayValue = "SAB";
		}
		return classDayValue;
	}

	
	public TreeMap<String, String> getFullPaymentTypeComboList(){
		TreeMap<String, String> paymentTypeList = new TreeMap<String, String>();
		
		paymentTypeList.put("PagSeguro/Crédito", "PagSeguro/Crédito");
		paymentTypeList.put("Cartão de Débito", "Cartão de Débito");
		paymentTypeList.put("Boleto", "Boleto");
		paymentTypeList.put("Depósito", "Depósito");
		paymentTypeList.put("Dinheiro", "Dinheiro");
		paymentTypeList.put("Gratuito", "Gratuito");
		
		return paymentTypeList;
	}
}
